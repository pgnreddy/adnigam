/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cnbitsols.adrobe.controllers;

import com.cnbitsols.adrobe.common.StoreConstants;
import com.cnbitsols.adrobe.dto.Cart;
import com.cnbitsols.adrobe.dto.OrderProductItem;
import com.cnbitsols.adrobe.dto.Payment;
import com.cnbitsols.adrobe.entities.AddressBook;
import com.cnbitsols.adrobe.entities.Coupons;
import com.cnbitsols.adrobe.entities.Customers;
import com.cnbitsols.adrobe.entities.Offers;
import com.cnbitsols.adrobe.entities.Orders;
import com.cnbitsols.adrobe.entities.OrdersProducts;
import com.cnbitsols.adrobe.entities.ProductCoupon;
import com.cnbitsols.adrobe.services.AdrobeService;
import com.cnbitsols.adrobe.services.PaymentInterface;
import com.cnbitsols.adrobe.services.PaymentService;
import com.cnbitsols.adrobe.utils.RequestUtil;
import com.cnbitsols.adrobe.utils.SettingsUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author santosh.r
 */
@Controller
public class CheckoutController {

    private static Logger log = LoggerFactory.getLogger(CheckoutController.class);

    @Autowired
    private AdrobeService service = null;

    @Autowired
    private MessageSource messageSource = null;

    @RequestMapping(value = {"checkout"})
    public String checkout(HttpServletRequest request, Model model) {
        Customers customer = RequestUtil.getCurrentCustomer(request);
        System.out.println("com.cnbitsols.adrobe.controllers.CheckoutController.checkout()");
        if (customer == null) {
                      return "adrobe.checkout.login";
        }
            System.out.println("com.cnbitsols.adrobe.controllers.CheckoutController.checkout() 2222");
        List<AddressBook> addresses = service.getCustomerAddresses(customer.getCustomersId());
        model.addAttribute("addresses", addresses);
        return "adrobe.checkout.shippingAddress";
    }

    @RequestMapping(value = {"checkout/shipping"})
    public String shippingAddress(HttpServletRequest request, AddressBook addressbook, @RequestParam(value = "selectedAddress", defaultValue = "-1", required = false) int selectedAddress) {
        Customers customer = RequestUtil.getCurrentCustomer(request);
        if (customer == null) {
            return "redirect:/checkout";
        }

        if (selectedAddress > 0) {
            addressbook = service.getAddressById(selectedAddress, customer.getCustomersId());
        } else {
            addressbook.setCustomersId(customer.getCustomersId());
            service.save(addressbook);
        }

        if (addressbook == null) {
            return "redirect:/checkout";
        }

        Cart cart = RequestUtil.getCart(request);
        cart.setShippingAddress(addressbook);

        return "redirect:/checkout/summary";
    }

    @RequestMapping(value = {"checkout/summary"})
    public String summary(HttpServletRequest request) {
        Customers customer = RequestUtil.getCurrentCustomer(request);
        if (customer == null) {
            return "redirect:/checkout";
        }

        Cart cart = RequestUtil.getCart(request);
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

        return "adrobe.checkout.summary";
    }

    @RequestMapping(value = {"checkout/payment"})
    public String payment(Model model, HttpServletRequest request) {
        Customers customer = RequestUtil.getCurrentCustomer(request);
        if (customer == null) {
            return "redirect:/checkout";
        }

        Cart cart = RequestUtil.getCart(request);
        if (cart.getItemsMap() == null || cart.getItemsMap().isEmpty()) {
            return "redirect:/cart";
        }

        if (cart.getShippingAddress() == null) {
            return "redirect:/checkout";
        }

        //check for the quantity ,if less than redirect to cart page.
        Map<String, OrderProductItem> itemsMap = cart.getItemsMap();
        boolean outOfStock = false;
        if (itemsMap != null && !itemsMap.isEmpty()) {

            Set<String> productsIdsStr = itemsMap.keySet();
            Set<Integer> productsIds = new HashSet<Integer>();
            for (String key : productsIdsStr) {
                //for reserved key is productId_reserve,so removing reserve keyword;
                key = key.replace(CartController.reserveKeyword, "");
                key = key.replace(CartController.productCouponKeyword, "");
                productsIds.add(new Integer(key));
            }

            Map<Integer, Offers> productsMap = service.getProducts(productsIds);

            for (Integer productId : productsIds) {
                OrderProductItem item = itemsMap.get(productId + "");
                OrderProductItem itemOther = itemsMap.get(productId + CartController.reserveKeyword);
                OrderProductItem itemCoupon = itemsMap.get(productId + CartController.productCouponKeyword);
                Offers product = productsMap.get(productId);
                if (product == null) {
                    item.setQuantity(0);
                    outOfStock = true;
                    //itemsMap.remove(productId);
                } else {
                    int count = 0;
                    if (item != null) {
                        count += item.getQuantity();
                        item.setProduct(product);
                    }

                    if (itemOther != null) {
                        count += itemOther.getQuantity();
                        itemOther.setProduct(product);
                    }

                    if (itemCoupon != null) {
                        count += itemCoupon.getQuantity();
                        itemCoupon.setProduct(product);
                    }

                    if (count > product.getQuantity()) {
                        outOfStock = true;
                        if (item != null) {
                            item.setQuantity(product.getQuantity());
                        } else if (itemOther != null) {
                            item.setQuantity(product.getQuantity());
                        }
                    }

                }
            }
        }

        if (outOfStock) {
            model.addAttribute("error", messageSource.getMessage("product.outOfStock", null, Locale.getDefault()));
            return "redirect:/cart";
        }

        PaymentService paymentService = new PaymentService();

        String paymentMethod = request.getParameter("pType");
        if (StringUtils.isNotBlank(paymentMethod) && "cod".equalsIgnoreCase(paymentMethod)) {
            paymentMethod = "cod";
        } else if (StringUtils.isNotBlank(paymentMethod) && "ccav".equalsIgnoreCase(paymentMethod)) {
            paymentMethod = "ccav";
        } else {
            paymentMethod = "pay4u";
        }

        if (cart.getTotalprice() <= 0) {
            paymentMethod = "cod";
        }
        /*if(cart.getTotalprice()>0){
            String pay4uEnabled =  SettingsUtil.getSettings("PAY4U_Enabled");
            if("true".equalsIgnoreCase(pay4uEnabled)){
                paymentMethod="pay4u";
            }
        }else{
            paymentMethod="cod";
        }*/

        log.debug("paymentMethod: {}", paymentMethod);
        Payment payment = paymentService.getPayment(paymentMethod, cart, request, false);
        cart.setPayment(payment);
        //save the order as paymentPending status
        
        Orders order = service.createOrder(cart, customer, payment);
        cart.setOrderId(order.getOrdersId());
        
        String nextUrl = null;
        if ("cod".equals(paymentMethod)) {
            Map<String, Object> paymentResponeMap = new HashMap<String, Object>();
            paymentResponeMap.put("orderId", order.getOrdersId());
            paymentResponeMap.put("timestamp", new Date());
            paymentResponeMap.put("status", "success");
            paymentResponeMap.put("reason", "success");
            paymentResponeMap.put("transactionId", order.getOrdersId());
            boolean status = processConfirmOrder(cart, order, paymentResponeMap, request);
            deleteCartFromSession(request);
            nextUrl = "redirect:/checkout/paymentSummary";
        } else {
            payment = paymentService.getPaymentGatewayDetails(paymentMethod, cart, order, request, false);
            cart.setPayment(payment);
            request.getSession().setAttribute("CART", cart);
            request.getSession().setAttribute("order", order);
            if (paymentMethod.equalsIgnoreCase("ccav")) {
                nextUrl = "/adrobe/ccavenue_paymentRedirect";
            } else {
                nextUrl = "/adrobe/paymentRedirect";
            }
        }
        log.debug("nextUrl: {}", nextUrl);
        return nextUrl;
    }

    /*
     Called by third party payment gateway after payment 
    
     */
    @RequestMapping(value = {"checkout/paymentConfirmation"})
    public String paymentConfirmation(HttpServletRequest request) {
        Customers customer = RequestUtil.getCurrentCustomer(request);
        if (customer == null) {
            return "redirect:/checkout";
        }

        HttpSession session = request.getSession();
        Orders order = (Orders) session.getAttribute("order");

        Cart cart = RequestUtil.getCart(request);
        if (cart.getItemsMap() == null || cart.getItemsMap().isEmpty()) {
            return "redirect:/cart";
        }

        Map<String, String> requestMap = RequestUtil.getRequestMap(request, true);
        log.info("PaymentGateway Response : " + requestMap);
        PaymentService paymentService = new PaymentService();
        Map<String, Object> paymentResponeMap = paymentService.parsePaymentResponse(order, cart, requestMap, request);

        boolean status = processConfirmOrder(cart, order, paymentResponeMap, request);
        log.debug("process confirm order status {}", status);

        String resStatus = (String) paymentResponeMap.get("status");
        log.debug("paymentResponse order_status {}", resStatus);
        if (resStatus.equalsIgnoreCase("success")) {
            deleteCartFromSession(request);
        } else {
            cart.setOrderId(null);
        }

        return "redirect:/checkout/paymentSummary";
    }

    private void deleteCartFromSession(HttpServletRequest request) {
        request.getSession().removeAttribute(StoreConstants.CART);
    }

    @RequestMapping(value = {"checkout/paymentSummary"})
    public String paymentSummary(HttpServletRequest request, Model model) {
//        Customers customer = RequestUtil.getCurrentCustomer(request);
//        if (customer == null) {
//            return "redirect:/";
//        }

        HttpSession session = request.getSession();

        Orders order = (Orders) session.getAttribute("order");
        List<OrdersProducts> orderProducts = order.getOrderProducts();
        List<ProductCoupon> productCoupons = new ArrayList<>();

        for (OrdersProducts op : orderProducts) {
            if (op.getCoupon() == 1) {
                productCoupons.add(service.getProductCouponsByOrderIdAndProductId(op.getOrdersId(), op.getOrdersProductsId()));
            }
        }

        model.addAttribute("order", order);
        model.addAttribute("status", session.getAttribute("status"));
        model.addAttribute("productCoupons", productCoupons);

        session.removeAttribute("order");
        session.removeAttribute("status");

        return "adrobe.payment.summary";
    }

    private boolean processConfirmOrder(Cart cart, Orders order, Map<String, Object> paymentResponeMap, HttpServletRequest request) {
        Customers customer = RequestUtil.getCurrentCustomer(request);
        Orders updatedOrder = service.updateOrderPayment(cart, order, customer, paymentResponeMap);
        try {
            if (updatedOrder != null) {
                request.getSession().setAttribute("order", order);
                String status = (String) paymentResponeMap.get("status");
                request.getSession().setAttribute("status", status);

                if ("success".equalsIgnoreCase(status)) {
                    order.setUserRequests(updatedOrder.getUserRequests());
                    service.sendOrderConfirmationMessage(customer, order, status);
                    service.sendUserRequestsToVendor(customer, order);
                    
                    return true;
                }
                
            }
        } catch (Exception ex) {

        }

        return false;
    }
}
