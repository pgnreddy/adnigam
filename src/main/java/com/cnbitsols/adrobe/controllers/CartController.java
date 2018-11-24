/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cnbitsols.adrobe.controllers;

import com.cnbitsols.adrobe.dto.Cart;
import com.cnbitsols.adrobe.dto.OrderProductItem;
import com.cnbitsols.adrobe.entities.Coupons;
import com.cnbitsols.adrobe.entities.Customers;
import com.cnbitsols.adrobe.entities.Offers;
import com.cnbitsols.adrobe.services.AdrobeService;
import com.cnbitsols.adrobe.utils.RequestUtil;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author santosh.r
 */
@Controller
@RequestMapping(value = {"/cart"})
public class CartController {

    private static Logger log = LoggerFactory.getLogger(CartController.class);
    public static final String reserveKeyword = "_reserve";
    public static final String productCouponKeyword = "_productCoupon";

    @Autowired
    private AdrobeService service = null;

    @Autowired
    private MessageSource messageSource = null;

    public void addHeaderData(Model model) {
        model.addAttribute("categories", service.listMenuCategories());
        model.addAttribute("locations", service.listLocations());
    }

    @RequestMapping(value = {"/addItem"})
    public String addItemToCart(Model model, HttpServletRequest request, @RequestParam(value = "p", defaultValue = "-1") int offerId, @RequestParam(value = "reserve", defaultValue = "false", required = false) boolean reserve, @RequestParam(value = "coupon", defaultValue = "false", required = false) boolean coupon) {
        log.debug("Inside addItemToCart of Cart Controller");

        if (offerId > 0) {
            Offers product = service.getOffer(offerId);
            Integer customerId = RequestUtil.getCustomerId(request);
            int count = 0;
            if (product != null) {

                //get all the items for the item id ie reserved and non reserve,so sum of quantity should be calculated
                //ie without reserve qunaity+reserve quantity should not be greated than products quantity
                OrderProductItem orderProductItem = getCurrentCartItem(offerId, request, reserve);
                OrderProductItem orderProductItemOther = getCurrentCartItem(offerId, request, !reserve);
                OrderProductItem orderProductItemCoupon = getCurrentCartItemForProductCoupon(offerId, request);

                if (orderProductItem != null) {
                    count += orderProductItem.getQuantity();
                }
                if (orderProductItemOther != null) {
                    count += orderProductItemOther.getQuantity();
                }
                if (orderProductItemCoupon != null) {
                    count += orderProductItemCoupon.getQuantity();
                }

                if ((count + 1) <= product.getQuantity()) {
                    if (orderProductItem == null) {
                        addCartItemToCustomer(offerId, 1, customerId, product, request, reserve, coupon);
                    } else {
                        updateCartItemToCustomer(offerId, orderProductItem.getQuantity() + 1, null, customerId, request, reserve, coupon);
                    }
                } else {
                    model.addAttribute("error", messageSource.getMessage("product.outOfStock", null, Locale.getDefault()));
                }
            }

        }

        return "/adrobe/miniCart";
    }

    @RequestMapping("/removeItem")
    public String deleteItemFromCart(HttpServletRequest request, Model model, @RequestParam(value = "p", defaultValue = "-1") int offerId, @RequestParam(value = "cP", defaultValue = "false") boolean fromCartPopUP, @RequestParam(value = "reserve", defaultValue = "false", required = false) boolean reserve, @RequestParam(value = "coupon", defaultValue = "false", required = false) boolean coupon) {

        Integer customerId = RequestUtil.getCustomerId(request);

        if (offerId > 0) {
            OrderProductItem orderProductItem;
            if (!coupon) {
                orderProductItem = getCurrentCartItem(offerId, request, reserve);
            } else {
                orderProductItem = getCurrentCartItemForProductCoupon(offerId, request);
            }
            if (orderProductItem != null) {
                deleteCartItemForCustomer(offerId, customerId, request, reserve, coupon);
            }

        }

        if (fromCartPopUP) {
            return "/adrobe/miniCart";
        } else {
            return "redirect:/cart";
        }

    }

    @RequestMapping("/update")
    public String updateCartItemQuantity(Model model, @RequestParam(value = "p", defaultValue = "-1") int offerId, @RequestParam(value = "userRequest", required = false) String userRequest, @RequestParam(value = "q", defaultValue = "0", required = false) int quantity, HttpServletRequest request, @RequestParam(value = "reserve", defaultValue = "false", required = false) boolean reserve, @RequestParam(value = "coupon", defaultValue = "false", required = false) boolean coupon) {

        log.debug("Inside updateCartItemQuantity of cart controller");

        Integer customerId = RequestUtil.getCustomerId(request);
        boolean requiredQuantityNotAvaiable = false;
        if (offerId > 0) {
            OrderProductItem orderProductItem;
            if (!coupon) {
                orderProductItem = getCurrentCartItem(offerId, request, reserve);
            } else {
                orderProductItem = getCurrentCartItemForProductCoupon(offerId, request);
            }

            if (orderProductItem != null) {
                if (!coupon) {
                    if (quantity > 0) { //update quantity requested
                        Offers product = service.getOffer(offerId);
                        OrderProductItem orderProductItemOther = getCurrentCartItem(offerId, request, !reserve);
                        OrderProductItem orderProductItemCoupon = getCurrentCartItemForProductCoupon(offerId, request);
                        if (product != null) {
                            int otherQuantity = 0;
                            if (orderProductItemOther != null) {
                                otherQuantity = orderProductItemOther.getQuantity();
                            }
                            if (orderProductItemCoupon != null) {
                                otherQuantity += orderProductItemCoupon.getQuantity();
                            }
                            if (quantity + otherQuantity > product.getQuantity()) {
                                requiredQuantityNotAvaiable = true;
                                quantity = product.getQuantity() - otherQuantity;
                            }
                        } else {
                            requiredQuantityNotAvaiable = true;
                            quantity = 0;
                        }
                    }
                } else // in case the request is to get product coupon
                {
                    if (quantity > 0) {
                        Offers product = service.getOffer(offerId);
                        OrderProductItem orderProductItemReserved = getCurrentCartItem(offerId, request, true);
                        OrderProductItem orderProductItemUnreserved = getCurrentCartItem(offerId, request, false);

                        if (product != null) {
                            int otherQuantity = 0;
                            if (orderProductItemReserved != null) {
                                otherQuantity += orderProductItemReserved.getQuantity();
                            }
                            if (orderProductItemUnreserved != null) {
                                otherQuantity += orderProductItemUnreserved.getQuantity();
                            }
                            if (quantity + otherQuantity > product.getQuantity()) {
                                requiredQuantityNotAvaiable = true;
                                quantity = product.getQuantity() - otherQuantity;
                            }
                        } else {
                            requiredQuantityNotAvaiable = true;
                            quantity = 0;
                        }

                    }
                }
                if (requiredQuantityNotAvaiable) {
                    model.addAttribute("error", messageSource.getMessage("product.outOfStock", null, Locale.getDefault()));
                }
                updateCartItemToCustomer(offerId, quantity, userRequest, customerId, request, reserve, coupon);
            }
        }
        return "redirect:/cart";

    }

    @RequestMapping(value = {"", "/", "/view"})
    public String viewCart(HttpServletRequest request, Model model) {

        log.debug("Inside viewCart of Cart Controller");

        Cart cart = RequestUtil.getCart(request);
        Customers customer = RequestUtil.getCurrentCustomer(request);

        Map<String, OrderProductItem> itemsMap = cart.getItemsMap();
        if (itemsMap != null && !itemsMap.isEmpty()) {
            Set<String> productsIdsStr = itemsMap.keySet();
            Set<Integer> productsIds = new HashSet<Integer>();
            for (String key : productsIdsStr) {
                //for reserved key is productId_reserve,so removing reserve keyword;
                key = key.replace(reserveKeyword, "");
                key = key.replace(productCouponKeyword, "");
                productsIds.add(new Integer(key));
            }

            Map<Integer, Offers> productsMap = service.getProducts(productsIds);

            for (Integer productId : productsIds) {
                Offers product = productsMap.get(productId);

                OrderProductItem item = itemsMap.get(productId + "");
                OrderProductItem itemReserve = itemsMap.get(productId + reserveKeyword);
                OrderProductItem itemCoupon = itemsMap.get(productId + productCouponKeyword);

                if (product == null) {
                    if (item != null) {
                        item.setQuantity(0);
                    }
                    if (itemReserve != null) {
                        itemReserve.setQuantity(0);
                    }
                    if (itemCoupon != null) {
                        itemReserve.setQuantity(0);
                    }
                    //itemsMap.remove(productId);
                } else {
                    if (item != null) {
                        item.setProduct(product);
                    }
                    if (itemReserve != null) {
                        itemReserve.setProduct(product);
                    }
                    if (itemCoupon != null) {
                        itemCoupon.setProduct(product);
                    }
                }
            }
        }

        addHeaderData(model);

        Coupons coupon = cart.getCoupon();
        if (coupon != null) {
            int customerId = customer == null ? 0 : customer.getCustomersId();
            coupon = service.validateCouponCode(coupon.getCode(), cart.getActualTotalprice(), customerId);
            if (coupon == null) {
                cart.setCoupon(null);
            } else {
                cart.setCoupon(coupon);
            }
        }
        //update end

        /* Object error = request.getSession().getAttribute("error");
         if (error != null) {
         request.getSession().removeAttribute("error");
         request.setAttribute("error", error);
         }*/
        Object couponError = request.getSession().getAttribute("couponError");
        if (couponError != null) {
            request.getSession().removeAttribute("couponError");
            model.addAttribute("couponError", couponError);
        }

        return "adrobe.cart";
    }

    @RequestMapping("/addCoupon")
    public String addCoupon(HttpServletRequest request, Model model) {
        String couponCode = request.getParameter("couponCode");
        Coupons coupon = null;
        Cart cart = RequestUtil.getCart(request);
        Customers customer = RequestUtil.getCurrentCustomer(request);
        if (StringUtils.isNotBlank(couponCode)) {
            int customerId = customer == null ? 0 : customer.getCustomersId();
            coupon = service.validateCouponCode(couponCode, cart.getActualTotalprice(), customerId);
        }

        String response = "success";
        if (coupon == null) {
            //request.getSession().setAttribute("couponError",true);
            response = "invalid";
        } else {
            cart.setCoupon(coupon);
        }

        model.addAttribute("status", response);
        return "adrobe/couponResponse";
    }

    @RequestMapping("/deleteCoupon")
    public String removeCoupon(HttpServletRequest request, Model model) {
        Cart cart = RequestUtil.getCart(request);
        cart.setCoupon(null);

        model.addAttribute("status", "success");
        return "adrobe/couponResponse";
    }

    private OrderProductItem getCurrentCartItem(int productId, HttpServletRequest request, boolean reserve) {
        Map<String, OrderProductItem> itemsMap = getItemsMap(request);
        String key = getKey(productId, reserve);
        return itemsMap.get(key);
    }

    private OrderProductItem getCurrentCartItemForProductCoupon(int productId, HttpServletRequest request) {
        Map<String, OrderProductItem> itemsMap = getItemsMap(request);
        String key = getProductCouponKey(productId);
        return itemsMap.get(key);
    }

    private Map<String, OrderProductItem> getItemsMap(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Cart cart = RequestUtil.getCart(request);
        Map<String, OrderProductItem> itemsMap = null;
        itemsMap = cart.getItemsMap();
        if (itemsMap == null) {
            itemsMap = new LinkedHashMap<String, OrderProductItem>();
            cart.setItemsMap(itemsMap);
        }

        return itemsMap;

    }

    private void addCartItemToCustomer(int productId, int quantity, Integer customerId, Offers product, HttpServletRequest request, boolean reserve, boolean coupon) {
        if (product != null) {
            OrderProductItem orderProductItem = new OrderProductItem();
            orderProductItem.setQuantity(quantity);
            orderProductItem.setProduct(product);
            orderProductItem.setReserved(reserve);
            orderProductItem.setCoupon(coupon);

            boolean added = true;//service.addItemToCart(customers_id, productId, quantity);
            if (added) {
                Map<String, OrderProductItem> itemsMap = getItemsMap(request);
                String key;
                if (!coupon) {
                    key = getKey(productId, reserve);
                } else {
                    key = getProductCouponKey(productId);
                }
                itemsMap.put(key, orderProductItem);
            }

        }

    }

    private void deleteCartItemForCustomer(int offerId, Integer customersId, HttpServletRequest request, boolean reserve, boolean coupon) {
        OrderProductItem orderProductItem;
        if (!coupon) {
            orderProductItem = getCurrentCartItem(offerId, request, reserve);
        } else {
            orderProductItem = getCurrentCartItemForProductCoupon(offerId, request);
        }
        if (orderProductItem != null) {
            //StoreService service = new StoreService();
            boolean deleted = true;//service.deleteItemInCart(customers_id, productId);
            if (deleted) {
                Map<String, OrderProductItem> itemsMap = getItemsMap(request);
                String key;
                if (!coupon)
                    key = getKey(offerId, reserve);
                else
                    key = getProductCouponKey(offerId);
                itemsMap.remove(key);
            }
        }
    }

    private void updateCartItemToCustomer(int offerId, int quantity, String userRequest, Integer customersId, HttpServletRequest request, boolean reserve, boolean coupon) {
        OrderProductItem orderProductItem = coupon ? getCurrentCartItemForProductCoupon(offerId, request) : getCurrentCartItem(offerId, request, reserve);
        if (orderProductItem != null) {

            boolean updated = true;//service.updateItemInCart(customers_id, productId, quantity);
            if (updated) {
                if (StringUtils.isNotBlank(userRequest)) {
                    orderProductItem.setUserRequest(userRequest);
                } else {
                    orderProductItem.setQuantity(quantity);
                }

            }

        }
    }

    private String getKey(Integer productId, boolean reserve) {
        return service.getKey(productId, reserve);
    }

    private String getProductCouponKey(Integer productId) {
        return service.getProductCouponKey(productId);
    }

}
