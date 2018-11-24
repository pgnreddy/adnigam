/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cnbitsols.adrobe.controllers;

import com.cnbitsols.adrobe.common.StoreConstants;
import com.cnbitsols.adrobe.dto.ApiResponse;
import com.cnbitsols.adrobe.dto.Cart;
import com.cnbitsols.adrobe.dto.OrderProductItem;
import com.cnbitsols.adrobe.dto.Payment;
import com.cnbitsols.adrobe.entities.AddressBook;
import com.cnbitsols.adrobe.entities.Categories;
import com.cnbitsols.adrobe.entities.Coupons;
import com.cnbitsols.adrobe.entities.Customers;
import com.cnbitsols.adrobe.entities.Location;
import com.cnbitsols.adrobe.entities.Offers;
import com.cnbitsols.adrobe.entities.Orders;
import com.cnbitsols.adrobe.entities.OrdersProducts;
import com.cnbitsols.adrobe.entities.OrdersTotal;
import com.cnbitsols.adrobe.entities.PremiumOffers;
import com.cnbitsols.adrobe.entities.ProductCoupon;
import com.cnbitsols.adrobe.entities.Reviews;
import com.cnbitsols.adrobe.entities.Vendor;
import com.cnbitsols.adrobe.exceptions.APIException;
import com.cnbitsols.adrobe.services.AdrobeService;
import com.cnbitsols.adrobe.services.PaymentService;
import com.cnbitsols.adrobe.services.impl.AdrobeS3DAOImpl;
import com.cnbitsols.adrobe.utils.RequestUtil;
import com.cnbitsols.adrobe.utils.SettingsUtil;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author santosh.r
 */
@Controller
@RequestMapping("/api/consumer")
public class ConsumerApiController {

    private static Logger log = LoggerFactory.getLogger(ConsumerApiController.class);

    @Autowired
    private AdrobeService service = null;

    @Autowired
    private MessageSource messageSource = null;

    @RequestMapping(value = "/categories", produces = "application/json")
    public @ResponseBody
    List<Categories> categories() {
        return service.listMenuCategories();
    }

    @RequestMapping(value = "/locations", produces = "application/json")
    public @ResponseBody
    List<Location> locations() {
        return service.listLocations();
    }

    @RequestMapping(value = "/premiumOffers", produces = "application/json")
    public @ResponseBody
    List<PremiumOffers> listPremiumOffers() {
        return service.listPremiumOffers(0);
    }

    @RequestMapping(value = "/todayOffers", produces = "application/json")
    public @ResponseBody
    List<Offers> listTodaysOffers() {
        return service.getTodaysOffers(0, -1);
    }

    @RequestMapping(value = "/exclusiveOffers", produces = "application/json")
    public @ResponseBody
    List<Offers> listExclusiveOffers() {
        return service.getExclusiveOffers(0);
    }

    @RequestMapping(value = "/offers", produces = "application/json")
    public @ResponseBody
    List<Object[]> categoryOffers(@RequestParam("category") String category, @RequestParam(value = "page", required = false, defaultValue = "0") int page, @RequestParam(value = "limit", required = false, defaultValue = "-1") int limit, @RequestParam(value = "loc", required = false, defaultValue = "-1") int locationId) {
        if (limit == -1 || limit > 30) {
            limit = NumberUtils.toInt(SettingsUtil.getSettings("CATEGORIES_PRODUCTS_COUNT"), 12);
        }

        return service.listProducts(category, locationId, page, limit, null);

    }
    
    @RequestMapping(value = "/filter", produces = "application/json")
    public @ResponseBody
    List<Object[]> sortOffers(@RequestParam(value = "category", required = false) String category, @RequestParam(value = "page", required = false, defaultValue = "0") int page, @RequestParam(value = "limit", required = false, defaultValue = "-1") int limit, @RequestParam(value = "pRange", required = false, defaultValue = "") String priceRange,@RequestParam(value = "dRange", required = false, defaultValue = "") String discountRange) {
        if (limit == -1 || limit > 30) {
            limit = 20;
        }
        return service.searchProductsByPrice(category, page, limit, priceRange, discountRange);
    }
    @RequestMapping(value = "/nearbyoffers", produces = "application/json")
    public @ResponseBody
    List<Object[]> nearByOffers(@RequestParam("latitude") double latitude, @RequestParam("longitude") double longitude,@RequestParam(value = "page", required = false, defaultValue = "0") int page, @RequestParam(value = "limit", required = false, defaultValue = "-1") int limit, @RequestParam("distance") int distance) {
        if (limit == -1 || limit > 30) {
            limit = 20;
        }
        return service.listNearByProducts(latitude, longitude, page, limit, distance);
    }
    
    

    @RequestMapping(value = "/vendorOffers", produces = "application/json")
    public @ResponseBody
    Object[] vendorOffers(@RequestParam("vendor") int vendorId) {
        Vendor vendor = service.getVendor(vendorId);
        List<Offers> offersList = service.listVendorProducts(vendorId);

        Object[] data = new Object[]{vendor, offersList};
        return data;
    }

    @RequestMapping(value = "/search", produces = "application/json")
    public @ResponseBody
    List<Object[]> search(@RequestParam(value = "category", required = false) String category, @RequestParam(value = "page", required = false, defaultValue = "0") int page, @RequestParam(value = "limit", required = false, defaultValue = "-1") int limit, @RequestParam(value = "search") String searchTerm) {
        if (limit == -1 || limit > 30) {
            limit = 20;
        }

        return service.searchProducts(category, page, limit, searchTerm);

    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    ApiResponse login(@RequestParam(value = "userName", required = false) String userName, @RequestParam(value = "password", required = false) String password) {

        String message = null;
        Customers customer = null;
        if (StringUtils.isBlank(userName) || StringUtils.isBlank(password)) {
            message = messageSource.getMessage("invalidDetails", null, Locale.getDefault());
        }

        if (message == null) {
            customer = service.getCustomer(userName);
            if (customer == null) {
                message = messageSource.getMessage("emailNotRegistered", null, Locale.getDefault());
            } else {
                if (!password.equals(customer.getPassword())) {
                    message = messageSource.getMessage("wrongCredentials", null, Locale.getDefault());
                }
            }
        }

        ApiResponse res = null;
        if (message == null) { //valid customer
            res = new ApiResponse("success", customer);
        } else {
            res = new ApiResponse("failed", message);
        }

        return res;
    }

    @RequestMapping(value = "forgotPassword", produces = "application/json")
    public @ResponseBody
    ApiResponse forgotPassword(@RequestParam(value = "email", required = false) String email) {

        String message = null;
        Customers customer = null;
        if (StringUtils.isBlank(email)) {
            message = messageSource.getMessage("invalid.email", null, Locale.getDefault());
        }

        if (message == null) {
            customer = service.getCustomer(email);
            if (customer == null) {
                message = messageSource.getMessage("emailNotRegistered", null, Locale.getDefault());
            }
        }

        if (message == null) { //valid customer
            boolean sent = service.sendForgotPassword(customer);
            if (sent) {
                message = messageSource.getMessage("forgotPassword.sent.success", null, Locale.getDefault());
            } else {
                message = messageSource.getMessage("forgotPassword.sent.failed", null, Locale.getDefault());
            }
        }

        log.debug("message : " + message);
        return new ApiResponse("success", message);
    }

    @RequestMapping(value = "signup", produces = "application/json")
    public @ResponseBody
    ApiResponse signup(Customers customer, BindingResult result) {
        String message = null;

        String email = customer.getEmailAddress();
        if (StringUtils.isBlank(email)) {
            message = messageSource.getMessage("invalid.email", null, Locale.getDefault());
        }

        if (message == null) {
            Customers existCustomer = service.getCustomer(email);
            if (existCustomer != null) {
                message = messageSource.getMessage("account.exists", null, Locale.getDefault());
            }
        }

        if (message == null) { //valid customer
            boolean added = service.addNewCustomer(customer);
            if (!added) {
                message = messageSource.getMessage("request.failed ", null, Locale.getDefault());
            }
        }

        if (message != null) {
            return new ApiResponse("failed", message);
        } else {
            return new ApiResponse("success", customer);
        }

    }

    @RequestMapping(value = "addressbook", produces = "application/json")
    public @ResponseBody
    List<AddressBook> listAddresses(@RequestParam(value = "consumerId") int consumerId) {
        return service.getCustomerAddresses(consumerId);
    }

    @RequestMapping(value = {"/updateAddressBook"}, method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    ApiResponse updateAddress(AddressBook book, BindingResult result) {
        service.save(book);
        return new ApiResponse("success", book);
    }

    @RequestMapping(value = {"/deleteAddress"}, method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    ApiResponse deleteAddress(@RequestParam(value = "addressBookId") int addressBookId) {

        AddressBook book = new AddressBook();
        book.setAddressBookId(addressBookId);

        service.delete(book);
        return new ApiResponse("success");
    }

    @RequestMapping(value = {"/orders"}, method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    List<Map> orders(@RequestParam(value = "consumerId") int consumerId, @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "limit", required = false, defaultValue = "10") int limit) {

        List<Map> orders = service.getOrdersSummary(consumerId, page, limit);

        return orders;
    }

    @RequestMapping(value = {"/order/{orderId}"}, method = RequestMethod.GET)
    public @ResponseBody
    Map getOrderinfo(@RequestParam(value = "consumerId") int consumerId, @PathVariable("orderId") Integer orderId) {

        Map map = new HashMap();
        Map<Integer, String> couponCodeMap = new HashMap();
        Orders order = service.getCustomerOrder(orderId, consumerId);
        map.put("order", order);
        if (order != null && order.getOrdersProducts() != null) {
            Set<Integer> productsIds = new HashSet<Integer>();
            for (OrdersProducts op : order.getOrdersProducts()) {
                productsIds.add(op.getOrdersId());
                if (op.getCoupon() == 1) {
                    ProductCoupon pc = service.getProductCouponsByOrderIdAndProductId(op.getOrdersId(), op.getOrdersProductsId());
                    couponCodeMap.put(op.getOrdersProductsId(), pc.getProductCouponCode());
                }
            }
            map.put("productCoupons", couponCodeMap);
            Map<Integer, Offers> productsMap = service.getProducts(productsIds);
            map.put("productsMap", productsMap);

        }

        return map;

    }

    @RequestMapping(value = {"/cancelOrder"}, method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    ApiResponse cancelOrder(@RequestParam(value = "consumerId") int consumerId, @RequestParam("orderId") Integer orderId, @RequestParam("comments") String comments) {

        service.cancelCustomerOrder(consumerId, orderId, comments);
        return new ApiResponse("success");
    }

    @RequestMapping(value = {"/accountprofile"}, method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    ApiResponse submitAccountProfile(HttpServletRequest request, @RequestParam(value = "consumerId") int consumerId) {
        String gender = request.getParameter("gender");
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String dob = request.getParameter("dob");
        String emailaddress = request.getParameter("emailaddress");
        String telephone = request.getParameter("telephone");

        Customers customers = service.getCustomerById(consumerId);
        if (customers != null) {
            customers.setTelephone(telephone);
            customers.setEmailAddress(emailaddress);
            customers.setDob(dob);
            customers.setLastname(lastname);
            customers.setFirstname(firstname);
            if (gender != null && gender.length() == 1) {
                customers.setGender(gender);
            }
            service.save(customers);
        } else {
            return new ApiResponse("failed");
        }
        return new ApiResponse("success");

    }

    @RequestMapping(value = {"/changepassword"}, method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    ApiResponse changePassword(HttpServletRequest request, @RequestParam(value = "consumerId") int consumerId) {
        log.info("list orders called...");
        Customers customers = service.getCustomerById(consumerId);

        String status = null;
        if (customers != null) {
            String currentpwd = customers.getPassword();
            String currentPassword = request.getParameter("currentpassword");
            if (!currentpwd.equals(currentPassword)) {
                status = "failed";
            }

            if (status == null) {
                String newpassword = request.getParameter("newpassword");
                try {
                    customers.setPassword(newpassword);
                    service.save(customers);
                } catch (Exception e) {
                    log.error("exception occured while changing pwd :" + ExceptionUtils.getFullStackTrace(e));
                }
            }

        }

        if (status == null) {
            status = "success";
        }

        return new ApiResponse(status);

    }

    /*
    
     {
     "customerId":1,
     "addressId":1,
     "coupon":"couponcode",
     "pType":"cod | pay4u",
     "cart":[
     {
     "productId":1,
     "qty":2,
     "reserved":1,
     "userRequest":"test"
     },
     {
     "productId":2,
     "qty":1,
     "reserved":0,
     "userRequest":"test2"
     }
     ]
     }
     */
    @RequestMapping(value = {"/checkout"}, method = RequestMethod.POST)
    public 
    String checkout(@RequestBody String input, HttpServletRequest request, Model model) {
        log.info("checkout called...");

        JSONObject requestJson = null;
        int customerId = 0;
        int addressId = 0;
        JSONArray cartArray = null;
        Cart cart = null;
        Customers customer = null;
        AddressBook addressbook = null;

        String nextUrl = null;
        try {
            try {
                requestJson = new JSONObject(input);
                customerId = requestJson.getInt("customerId");
                addressId = requestJson.getInt("addressId");
                cartArray = requestJson.getJSONArray("cart");
            } catch (Exception e) {
                log.debug("error while parsing checkout input json : ", e);
                throw new APIException(new ApiResponse("failed", "Invalid input"));
            }

            customer = service.getCustomerById(customerId);
            if (customer == null) {
                throw new APIException(new ApiResponse("failed", "customer not found"));
            }

            addressbook = service.getAddressById(addressId, customerId);
            if (addressbook == null) {
                throw new APIException(new ApiResponse("failed", "Address not found"));
            }

            if (cartArray == null || cartArray.length() == 0) {
                throw new APIException(new ApiResponse("failed", "cart is empty"));
            }

            int cartLength = cartArray.length();
            Map<Integer, Integer> productsNotAvailable = new HashMap<Integer, Integer>();
            cart = new Cart();
            Map<String, OrderProductItem> itemsMap = new LinkedHashMap<String, OrderProductItem>();
            cart.setItemsMap(itemsMap);
            cart.setShippingAddress(addressbook);

            for (int i = 0; i < cartLength; i++) {
                JSONObject cartItem = cartArray.getJSONObject(i);
                int productId = cartItem.getInt("productId");
                int qty = cartItem.optInt("qty");
                if (qty == 0) {
                    qty = 1;
                }
                int reservedInt = cartItem.optInt("reserved");
                boolean reserved = reservedInt == 1 ? true : false;
                String userRequest = cartItem.optString("userRequest");

                Offers product = service.getOffer(productId);
                if (product == null) {
                    productsNotAvailable.put(productId, 0);
                    continue;
                }

                //loop through cart and get same product so we can calculate total quantity request and available.
                int productqty = qty;
                for (int j = 0; j < cartLength; j++) {

                    if (i != j) {
                        JSONObject cartItemj = cartArray.getJSONObject(j);
                        if (cartItemj.getInt("productId") == productId) {
                            productqty += cartItemj.getInt("qty");
                            break;
                        }
                    }
                }

                if (productqty > product.getQuantity()) {
                    productsNotAvailable.put(productId, product.getQuantity());
                    continue;
                }

                    //add to cart.
                OrderProductItem orderProductItem = new OrderProductItem();
                orderProductItem.setQuantity(qty);
                orderProductItem.setProduct(product);
                orderProductItem.setReserved(reserved);
                orderProductItem.setUserRequest(userRequest);

                String key = service.getKey(productId, reserved);
                itemsMap.put(key, orderProductItem);
            }

            if (productsNotAvailable.size() > 0) {
                throw new APIException(new ApiResponse("failed", "out of stock", productsNotAvailable));
            }
            
            //Get coupon details
            String couponCode = requestJson.optString("coupon");
            if(StringUtils.isNotBlank(couponCode)){
                Coupons coupon = service.validateCouponCode(couponCode, cart.getActualTotalprice(), customerId);
                if(coupon!=null){
                    cart.setCoupon(coupon);
                }
            }

            PaymentService paymentService = new PaymentService();

            String paymentMethod = requestJson.optString("pType");

            if(StringUtils.isNotBlank(paymentMethod) && "cod".equalsIgnoreCase(paymentMethod)){
                paymentMethod="cod";
            }else if (StringUtils.isNotBlank(paymentMethod) && "ccav".equalsIgnoreCase(paymentMethod)) {
                paymentMethod="ccav";
            } else {
                paymentMethod = "pay4u";
            }

            if(cart.getTotalprice()<=0){
                 paymentMethod="cod";
            } 
        
            /*if (cart.getTotalprice() > 0) {
                String pay4uEnabled = SettingsUtil.getSettings("PAY4U_Enabled");
                if ("true".equalsIgnoreCase(pay4uEnabled)) {
                    paymentMethod = "pay4u";
                }
            }*/

            Payment payment = paymentService.getPayment(paymentMethod, cart, request, true);
            cart.setPayment(payment);
            //save the order as paymentPending status
            Orders order = service.createOrder(cart, customer, payment);
            cart.setOrderId(order.getOrdersId());

            if ("cod".equals(paymentMethod)) {
                Map<String, Object> paymentResponeMap = new HashMap<String, Object>();
                paymentResponeMap.put("orderId", order.getOrdersId());
                paymentResponeMap.put("timestamp", new Date());
                paymentResponeMap.put("status", "success");
                paymentResponeMap.put("reason", "success");
                paymentResponeMap.put("transactionId", order.getOrdersId());
                boolean status = processConfirmOrder(cart, customer, order, paymentResponeMap);

                Integer orderId = order.getOrdersId();
                String orderTotal = null;
                for (OrdersTotal total : order.getOrderTotalList()) {
                    if (total.getClass1().equalsIgnoreCase("ot_total")) {
                        orderTotal = total.getText();
                    }
                }
                String message = null;
                if (status == true) {
                    message = "Your payment for amount " + orderTotal + " towards order " + orderId + " is successful.";
                    Map res = new HashMap();
                    res.put("orderId", orderId);
                    res.put("orderTotal", orderTotal);
                    throw new APIException(new ApiResponse("success", message, res));
                }

                message = "Your payment for amount " + orderTotal + "  towards order " + orderId + " is failed";
                throw new APIException(new ApiResponse("failed", message));
            } else {
                payment = paymentService.getPaymentGatewayDetails(paymentMethod, cart, order, request, true);
                cart.setPayment(payment);
                HttpSession session = request.getSession();
                session.setAttribute("order", order);
                session.setAttribute(StoreConstants.CART, cart);
                session.setAttribute(StoreConstants.USER, customer);
                if (paymentMethod.equalsIgnoreCase("ccav")) {
                    nextUrl = "/adrobe/ccavenue_paymentRedirect";
                } else {
                    nextUrl = "/adrobe/paymentRedirect";
                }
            }
        } catch (APIException e) {
            ApiResponse res = e.getApiResponse();
            ObjectMapper mapper = new ObjectMapper();
            String apiStr = null;
            try {
                apiStr = mapper.writeValueAsString(res);
            } catch (IOException ex) {
                log.error("Exception while converting offers to json", ex);
                apiStr = "{}";
            }
            model.addAttribute("response", apiStr);
            nextUrl = "redirect:/api/consumer/paymentSummary";
        }

        return nextUrl;
    }

    /*
     Called by third party payment gateway after payment 
    
     */
    @RequestMapping(value = {"paymentConfirmation"})
    public String paymentConfirmation(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession();
        try {

            Customers customer = RequestUtil.getCurrentCustomer(request);
            if (customer == null) {
                throw new APIException(new ApiResponse("failed", "customer not found"));
            }

            Orders order = (Orders) session.getAttribute("order");

            Cart cart = RequestUtil.getCart(request);
            if (cart.getItemsMap() == null || cart.getItemsMap().isEmpty()) {
                throw new APIException(new ApiResponse("failed", "cart is empty"));
            }

            Map<String, String> requestMap = RequestUtil.getRequestMap(request, true);
            log.info("PaymentGateway Response : " + requestMap);
            PaymentService paymentService = new PaymentService();
            Map<String, Object> paymentResponeMap = paymentService.parsePaymentResponse(order, cart, requestMap, request);

            boolean status = processConfirmOrder(cart, customer, order, paymentResponeMap);

            Integer orderId = order.getOrdersId();
            String orderTotal = null;
            for (OrdersTotal total : order.getOrderTotalList()) {
                if (total.getClass1().equalsIgnoreCase("ot_total")) {
                    orderTotal = total.getText();
                }
            }
            String message = null;
            if (status == true) {
                message = "Your payment for amount " + orderTotal + " towards order " + orderId + " is successful.";
                Map res = new HashMap();
                res.put("orderId", orderId);
                res.put("orderTotal", orderTotal);
                throw new APIException(new ApiResponse("success", message, res));
            }

            message = "Your payment for amount " + orderTotal + "  towards order " + orderId + " is failed";
            throw new APIException(new ApiResponse("failed", message));

        } catch (APIException e) {
            ApiResponse res = e.getApiResponse();
            ObjectMapper mapper = new ObjectMapper();
            String apiStr = null;
            try {
                apiStr = mapper.writeValueAsString(res);
            } catch (IOException ex) {
                log.error("Exception while converting offers to json", ex);
                apiStr = "{}";
            }
            model.addAttribute("response", apiStr);

        } finally {
            session.removeAttribute("order");
            session.removeAttribute(StoreConstants.CART);
            session.removeAttribute(StoreConstants.USER);
        }

        return "redirect:/api/consumer/paymentSummary";
    }

    /*@RequestMapping(value = {"/checkout"}, method = RequestMethod.POST,produces = "application/json")
     public @ResponseBody
     ApiResponse checkout(@RequestBody String input,HttpServletRequest request) {
     log.info("checkout called...");
                
     JSONObject requestJson = null;
     int customerId = 0;
     int addressId = 0;
     JSONArray cartArray = null;
     Cart cart = null;
     Customers customer = null;
     AddressBook addressbook = null;
     try{
     requestJson = new JSONObject(input);
     customerId = requestJson.getInt("customerId");
     addressId = requestJson.getInt("addressId");
     cartArray = requestJson.getJSONArray("cart");
     }catch(Exception e){
     log.debug("error while parsing checkout input json : ",e);
     return new ApiResponse("failed","Invalid input");
     }
               
     customer = service.getCustomerById(customerId);
     if(customer==null){
     return new ApiResponse("failed","customer not found");
     }
        
     addressbook = service.getAddressById(addressId, customerId);
     if(addressbook==null){
     return new ApiResponse("failed","Address not found");
     }
        
     if(cartArray==null || cartArray.length()==0){
     return new ApiResponse("failed","cart is empty");
     }
        
        
     int cartLength = cartArray.length();
     Map<Integer,Integer> productsNotAvailable = new HashMap<Integer,Integer>();        
     cart = new Cart();
     Map<String, OrderProductItem> itemsMap = new LinkedHashMap<String, OrderProductItem>();
     cart.setItemsMap(itemsMap);
     cart.setShippingAddress(addressbook);
        
     for(int i=0;i<cartLength;i++){
     JSONObject cartItem = cartArray.getJSONObject(i);
     int productId = cartItem.getInt("productId");
     int qty = cartItem.optInt("qty");
     if(qty ==0){
     qty =1;
     }
     int reservedInt = cartItem.optInt("reserved");
     boolean reserved = reservedInt==1?true:false ;
     String userRequest = cartItem.optString("userRequest");
            
            
     Offers product = service.getOffer(productId);
     if(product==null){
     productsNotAvailable.put(productId, 0);
     continue;
     }
            
            
     //loop through cart and get same product so we can calculate total quantity request and available.
     int productqty = qty;
     for(int j=0;j<cartLength;j++){
                
     if(i!=j){
     JSONObject cartItemj = cartArray.getJSONObject(j);
     if(cartItemj.getInt("productId")==productId){
     productqty+=cartItemj.getInt("qty");
     break;
     }
     }
     }
            
     if(productqty > product.getQuantity()){
     productsNotAvailable.put(productId, product.getQuantity());
     continue;
     }            
            
     //add to cart.
            
     OrderProductItem orderProductItem = new OrderProductItem();
     orderProductItem.setQuantity(qty);
     orderProductItem.setProduct(product);
     orderProductItem.setReserved(reserved);
     orderProductItem.setUserRequest(userRequest);
            
     String key = service.getKey(productId, reserved);
     itemsMap.put(key, orderProductItem);
     }
        
        
        
     if (productsNotAvailable.size()>0) {
     return new ApiResponse("failed", "out of stock", productsNotAvailable);
     }

     PaymentService paymentService = new PaymentService();

     String paymentMethod = "cod";
     Payment payment = paymentService.getPayment(paymentMethod, cart,request, false);
     cart.setPayment(payment);
     //save the order as paymentPending status
     Orders order = service.createOrder(cart, customer, payment);
     cart.setOrderId(order.getOrdersId());

     String response = null;
     if ("cod".equals(paymentMethod)) {
     Map<String, Object> paymentResponeMap = new HashMap<String, Object>();
     paymentResponeMap.put("orderId", order.getOrdersId());
     paymentResponeMap.put("timestamp", new Date());
     paymentResponeMap.put("status", "success");
     paymentResponeMap.put("reason", "success");
     paymentResponeMap.put("transactionId", order.getOrdersId());
     boolean status = processConfirmOrder(cart,customer,order,paymentResponeMap);
            
     Integer orderId = order.getOrdersId();
     String orderTotal = null;
     for(OrdersTotal total : order.getOrderTotalList()){
     if(total.getClass1().equalsIgnoreCase("ot_total")){
     orderTotal = total.getText();
     }
     }
     String message = null;
     if(status==true){
     message = "Your payment for amount "+orderTotal+" towards order "+orderId+" is successful.";
     Map res = new HashMap();
     res.put("orderId",orderId);
     res.put("orderTotal",orderTotal);
     return new ApiResponse("success",message,res);
     }
            
     message = "Your payment for amount "+orderTotal+"  towards order "+orderId+" is failed";            
     return new ApiResponse("failed",message);
     } else {
     //payment gateway integration.
     //nextUrl = "payment";
     return new ApiResponse("success");
     }

     }*/
    private boolean processConfirmOrder(Cart cart, Customers customer, Orders order, Map<String, Object> paymentResponeMap) {

        Orders updatedOrder = service.updateOrderPayment(cart, order, customer, paymentResponeMap);
        if (updatedOrder != null) {
            String status = (String) paymentResponeMap.get("status");

            if ("success".equals(status)) {
                order.setUserRequests(updatedOrder.getUserRequests());
                service.sendOrderConfirmationMessage(customer, order, status);
                service.sendUserRequestsToVendor(customer, order);
                
                return true;
            }
            
        }

        return false;
    }

    // Adding APIs for Reviews , Favourites and Coupons
    @RequestMapping(value = "reviews", produces = "application/json")
    public @ResponseBody
    List<Reviews> getReviews(@RequestParam(value = "productId") int productId) {
        return service.getReviews(productId);
    }

    @RequestMapping(value = {"/addreview"}, method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    ApiResponse AddReview(HttpServletRequest request, @RequestParam(value = "consumerId") int consumerId) {
        log.info("list orders called...");
        Customers customers = service.getCustomerById(consumerId);

        String status = null;
        if (customers != null) {
            // int productId, int rating, String description
            String productId = request.getParameter("productId");
            String rating = request.getParameter("rating");
            String description = request.getParameter("description");
            boolean result = service.addCustomerReview(customers, Integer.parseInt(productId), Integer.parseInt(rating), description);
            if (result) {
                status = "success";
            } else {
                status = "fail";
            }
        }
        return new ApiResponse(status);
    }

    @RequestMapping(value = "favourites", produces = "application/json")
    public @ResponseBody
    List<Offers> getFavourites(@RequestParam(value = "consumerId") int consumerId) {
        return service.getFavouriteProducts(consumerId);
    }

    
     @RequestMapping(value = "addpushdetails", produces = "application/json")
    public @ResponseBody
    ApiResponse addPushDetails(@RequestParam(value = "userid") int consumerId, @RequestParam(value = "deviceos") String deviceos,@RequestParam(value = "pushkey") String pushkey,@RequestParam(value = "status") int status) {
        String status1 = "fail";
        Integer in = new Integer(consumerId);
        boolean result = service.addUserPushDetails(in, pushkey, deviceos, status);
        status1 = result ? "success" : "fail";

        return new ApiResponse(status1);
    }
    
    
    @RequestMapping(value = "sendpush", produces = "application/json")
    public @ResponseBody
    ApiResponse sendPush(@RequestParam(value = "message") String message,@RequestParam(value = "offerid") int offerid) {
        String status1 = "fail";
    
        boolean result = service.sendPush(offerid, message);
        status1 = result ? "success" : "fail";

        return new ApiResponse(status1);
    }
    
    @RequestMapping(value = "addfavourite", produces = "application/json")
    public @ResponseBody
    ApiResponse addFavourites(@RequestParam(value = "consumerId") int consumerId, @RequestParam(value = "productId") int productId) {
        String status = "fail";
        boolean result = service.addCustomerFavourite(consumerId, productId);
        status = result ? "success" : "fail";

        return new ApiResponse(status);
    }

    @RequestMapping(value = "deletefavourite", produces = "application/json")
    public @ResponseBody
    ApiResponse deleteFavourites(@RequestParam(value = "consumerId") int consumerId, @RequestParam(value = "productId") int productId) {
        String status = "fail";
        boolean result = service.deleteFavouriteProducts(consumerId, productId);
        status = result ? "success" : "fail";

        return new ApiResponse(status);
    }

    @RequestMapping(value = "usercoupons", produces = "application/json")
    public @ResponseBody
    List<Object[]> getuserCoupons(@RequestParam(value = "consumerId") int consumerId) {
        return service.listCustomerCoupons(consumerId);
    }

    @RequestMapping(value = "getdiscountprice", produces = "application/json")
    public @ResponseBody
    ApiResponse getdiscount(@RequestParam(value = "couponcode") String couponcode, @RequestParam(value = "consumerId") int counsumerid, @RequestParam(value = "actualprice") double price) {
        String result = "fail";
        Coupons coupon = service.validateCouponCode(couponcode, price, counsumerid);
        double couponPrice = 0;
        if (coupon != null) {
            if (coupon.getDiscount_type() == 1) {//flat
                if (coupon.getNumber_of_times_to_use() > 1) {
                    couponPrice = coupon.getDiscount_value() / coupon.getNumber_of_times_to_use();
                } else {
                    couponPrice = coupon.getDiscount_value();
                }
            } else {
                couponPrice = (price * (coupon.getDiscount_value() / 100d));
            }
        }

        result = "" + Math.floor(couponPrice);
        return new ApiResponse(result);
    }
    
    @RequestMapping(value = "image/{imgName}")
    public @ResponseBody
    byte[] getImage(@PathVariable String imgName){
        String bucketName = SettingsUtil.getSettings("S3_BUCKET");
        AdrobeS3DAOImpl s3Service = new AdrobeS3DAOImpl();
        if (!imgName.endsWith(".jpg"))
            imgName += ".jpg";
        try {
            InputStream in = s3Service.download(bucketName, imgName);
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            int nRead;
            byte[] buffer = new byte[1024];
            while ((nRead = in.read(buffer, 0, buffer.length)) > 0) {
                outStream.write(buffer);
            }
            outStream.flush();
            return outStream.toByteArray();
        } catch (Exception ex) {
            return null;
        }
    }
}
