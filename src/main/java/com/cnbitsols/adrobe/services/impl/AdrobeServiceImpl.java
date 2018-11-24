/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cnbitsols.adrobe.services.impl;

import com.cnbitsols.adrobe.controllers.CartController;
import static com.cnbitsols.adrobe.controllers.CartController.reserveKeyword;
import com.cnbitsols.adrobe.dao.AdrobeHibernateDAO;
import com.cnbitsols.adrobe.dao.AdrobeJdbcDAO;
import com.cnbitsols.adrobe.dto.Cart;
import com.cnbitsols.adrobe.dto.OrderProductItem;
import com.cnbitsols.adrobe.dto.OrderStatus;
import com.cnbitsols.adrobe.dto.Payment;
import com.cnbitsols.adrobe.entities.AddressBook;
import com.cnbitsols.adrobe.entities.Categories;
import com.cnbitsols.adrobe.entities.Coupons;
import com.cnbitsols.adrobe.entities.CustomerCoupons;
import com.cnbitsols.adrobe.entities.Customers;
import com.cnbitsols.adrobe.entities.Location;
import com.cnbitsols.adrobe.entities.Offers;
import com.cnbitsols.adrobe.entities.Orders;
import com.cnbitsols.adrobe.entities.OrdersProducts;
import com.cnbitsols.adrobe.entities.OrdersStatusHistory;
import com.cnbitsols.adrobe.entities.OrdersTotal;
import com.cnbitsols.adrobe.entities.PremiumOffers;
import com.cnbitsols.adrobe.entities.ProductCoupon;
import com.cnbitsols.adrobe.entities.Reviews;
import com.cnbitsols.adrobe.entities.UserRequests;
import com.cnbitsols.adrobe.entities.Vendor;
import com.cnbitsols.adrobe.entities.VendorNoftificationQueue;
import com.cnbitsols.adrobe.services.AdrobeService;
import com.cnbitsols.adrobe.services.MessagingService;
import com.cnbitsols.adrobe.utils.SettingsUtil;
import com.cnbitsols.adrobe.utils.Util;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author santosh.r
 */
@Service
public class AdrobeServiceImpl implements AdrobeService {

    private static final Logger log = LoggerFactory.getLogger(AdrobeServiceImpl.class);

    @Autowired
    private MessageSource messageSource = null;
    @Autowired
    private AdrobeHibernateDAO hdao = null;
    @Autowired
    private MessagingService messagingService = null;

    @Autowired
    private AdrobeJdbcDAO dao = null;

    public AdrobeServiceImpl() {
        System.out.println("**********AdrobeServiceImpl constructor********");
    }

    @Override
    public boolean login(String userName, String password) {
        return true;
    }

    @Override
    public void incrementRefCount(String adRef) {
        hdao.incrementRefCount(adRef);
    }

     @Override
    @Cacheable(value = "cache60Min", key = "#root.methodName")
    public List<Categories> listMenuCategories() {
        List<Categories> allCategories = listCategoriesWithSubCategories();
        List<Categories> list = new ArrayList<Categories>();

        for (Categories c : allCategories) {
            if (c.getParentID() == 0) {
                list.add(c);
            }
        }

        return list;
    }

    @Override
    @Cacheable(value = "cache60Min", key = "#root.methodName")
    public List<Categories> listCategoriesWithSubCategories() {
        List<Categories> categories = listCategories();

        for (Categories sub : categories) {
            //System.out.println(sub.getCategoryID()+"-"+sub.getName()+"-"+sub.getParentID());
            for (Categories parent : categories) {
                if (parent.getCategoryID() == sub.getParentID()) {
                    List<Categories> subCategories = parent.getSubCategories();
                    if (subCategories == null) {
                        subCategories = new ArrayList<Categories>();
                        parent.setSubCategories(subCategories);
                    }
                    subCategories.add(sub);
                    break;
                }
            }
        }

        return categories;
    }

    @Override
    @Cacheable(value = "cache60Min", key = "#root.methodName")
    public List<Categories> listCategories() {
        return hdao.listCategories();
    }

    @Override
    public List<Location> listLocations() {
        log.debug("Inside listLocations...*************");
        return hdao.listLocations();
    }

    @Override
    public List<PremiumOffers> listPremiumOffers(int locationId) {
        return hdao.listPremiumOffers(locationId);
    }

    @Override
    @Cacheable(value = "cache60Min", key = "#root.methodName")
    public String getCategoriesJson() {
        List<Categories> categories = listMenuCategories();
        ObjectMapper mapper = new ObjectMapper();
        String categoriesStr = null;
        try {
            categoriesStr = mapper.writeValueAsString(categories);
        } catch (IOException ex) {
            log.error("Exception while converting offers to json", ex);
            categoriesStr = "[]";
        }

        return categoriesStr;
    }

    @Override
    @Cacheable(value = "cache5Min")
    public List<Object[]> listProducts(String category, int locationId, int page, int limit, String searchTerm) {
        List<Integer> subCategoriesIds = getSubCategoryIds(category);
        return hdao.listProducts(category, subCategoriesIds, locationId, page, limit, searchTerm);
    }

    @Override
    public List<Object[]> listNearByProducts(double latitude, double longitude, int page, int limit, int distance) {
        return hdao.listNearByProducts(latitude, longitude, page, limit, distance);
    }

    @Override
    public List<Object[]> searchProducts(String category, int page, int limit, String searchTerm) {
        List<Integer> subCategoriesIds = getSubCategoryIds(category);
        return hdao.searchProducts(category, subCategoriesIds, page, limit, searchTerm);
    }

    @Override
    public List searchProductsByPrice(String category, int page, int limit, String priceRange, String discountRange) {
        List<Integer> subCategoriesIds = getSubCategoryIds(category);
        return hdao.searchProductsByPrice(category, subCategoriesIds, page, limit, priceRange, discountRange);
    }

    @Cacheable(value = "cache60Min")
    public List<Integer> getSubCategoryIds(String category) {

        if (StringUtils.isBlank(category)) {
            return null;
        }

        Categories selectedCategory = null;
        List<Categories> list = listCategoriesWithSubCategories();

        for (Categories c : list) {
            if (category.equals(c.getSeo_title())) {
                selectedCategory = c;
                break;
            }
        }

        if (selectedCategory == null) {
            return null;
        }

        List<Integer> subCatIds = new ArrayList<Integer>();
        addSubCategoriesIds(subCatIds, selectedCategory);

        return subCatIds;
    }

    private void addSubCategoriesIds(List<Integer> subCatIds, Categories selectedCategory) {
        if (selectedCategory == null) {
            return;
        }

        subCatIds.add(selectedCategory.getCategoryID());
        List<Categories> subCategories = selectedCategory.getSubCategories();
        if (subCategories != null) {
            for (Categories sub : subCategories) {
                addSubCategoriesIds(subCatIds, sub);
            }
        }
    }

    @Override
    public Offers getProduct(String productName) {
        return hdao.getProduct(productName);
    }

    @Override
    @Cacheable(value = "cache15Min")
    public Categories getCategoryBySeoName(String category) {
        return hdao.getCategoryBySeoName(category);
    }

    @Override
    @Cacheable(value = "cache5Min")
    public Vendor getVendor(int vendorId) {
        return hdao.getVendor(vendorId);
    }

    @Override
    public List<Offers> listVendorProducts(int vendorId) {
        return hdao.listVendorProducts(vendorId);
    }

    @Override
    public Offers getOffer(int offerId) {
        return hdao.getOffer(offerId);
    }

    @Override
    public Orders getOrder(int orderId) {
        return hdao.getOrder(orderId);
    }

    @Override
    public Map<Integer, Offers> getProducts(Set<Integer> productsIds) {
        return hdao.getProducts(productsIds);
    }

    @Override
    public List<Offers> getTodaysOffers(int locationId, int limit) {
        return hdao.getTodaysOffers(locationId, limit);
    }

    @Override
    public Customers getCustomer(String userName) {
        return hdao.getCustomer(userName);
    }

    @Override
    public Customers gtCustomerByTelephone(String phoneNumber) {
        return hdao.getCustomerByPhone(phoneNumber);
    }

    @Override
    public boolean sendForgotPassword(Customers customer) {
        log.debug("sending forgot password email");
        messagingService.sendForgotPasswordMessage(customer);
        return true;
    }

    @Override
    public boolean addNewCustomer(Customers customer) {
        hdao.addNewCustomer(customer);
        messagingService.sendRegistrationMessage(customer);
        return true;
    }

    @Override
    public List<AddressBook> getCustomerAddresses(Integer customersId) {
        return hdao.getCustomerAddresses(customersId);
    }

    @Override
    public void save(Object obj) {
        hdao.save(obj);
    }

    @Override
    public Object getById(Serializable key, Class clazz) {
        return hdao.getById(key, clazz);
    }

    @Override
    public AddressBook getAddressById(int selectedAddress, Integer customersId) {
        return hdao.getAddressById(selectedAddress, customersId);
    }

    @Override
    @Transactional
    public Orders createOrder(Cart cart, Customers customer, Payment payment) {
        log.debug("Inside createOrder...*******");
        Orders order = new Orders();
        order.setCustomersId(customer.getCustomersId());
        order.setCustomersName(customer.getFirstname() + " " + customer.getLastname());
        AddressBook address = cart.getShippingAddress();
        order.setCustomersCompany(address.getEntryCompany());
        order.setCustomersStreetAddress(address.getEntryStreetAddress());
        order.setCustomersSuburb(address.getEntrySuburb());
        order.setCustomersCity(address.getEntryCity());
        order.setCustomersPostcode(address.getEntryPostcode());
        order.setCustomersState(address.getEntryState());
        order.setCustomersCountry("India");
        order.setCustomersTelephone(customer.getTelephone());
        order.setCustomersEmailAddress(customer.getEmailAddress());
        order.setCustomersAddressFormatId(1);

        order.setDeliveryName(address.getEntryFirstname() + " " + address.getEntryLastname());
        order.setDeliveryCompany(address.getEntryCompany());
        order.setDeliveryStreetAddress(address.getEntryStreetAddress());
        order.setDeliverySuburb(address.getEntrySuburb());
        order.setDeliveryCity(address.getEntryCity());
        order.setDeliveryPostcode(address.getEntryPostcode());
        order.setDeliveryState(address.getEntryState());
        order.setDeliveryCountry("India");
        order.setDeliveryAddressFormatId(1);

        order.setBillingName(address.getEntryFirstname() + " " + address.getEntryLastname());
        order.setBillingCompany(address.getEntryCompany());
        order.setBillingStreetAddress(address.getEntryStreetAddress());
        order.setBillingSuburb(address.getEntrySuburb());
        order.setBillingCity(address.getEntryCity());
        order.setBillingPostcode(address.getEntryPostcode());
        order.setBillingState(address.getEntryState());
        order.setBillingCountry("India");
        order.setBillingAddressFormatId(1);

        if (StringUtils.isBlank(payment.getModuleName())) {
            order.setPaymentMethod(payment.getTitle());
        } else {
            order.setPaymentMethod(payment.getModuleName() + " :: " + payment.getTitle());
        }

        Date d = new Date();

        order.setLastModified(d);
        order.setDatePurchased(d);
        order.setOrdersStatus(OrderStatus.PAYMENT_PENDING.status());

        Coupons coupon = cart.getCoupon();
        if (coupon != null) {
            order.setCouponId(coupon.getId());
        }
        hdao.save(order);

        insertOrderTotals(order, cart);
        //insertOrderStatusHistory(order,cart);
        insertOrderProducts(order, cart);
        //insertUserRequest(order,cart);
        //updateProductsInfo(order,cart);
        //insertVendorNotificationQueue(order);
        return order;
    }

    @Transactional
    private void insertOrderTotals(Orders order, Cart cart) {
        ArrayList<OrdersTotal> orderTotalList = new ArrayList<OrdersTotal>();

        OrdersTotal subtotal = new OrdersTotal(order.getOrdersId(), messageSource.getMessage("MODULE_ORDER_TOTAL_SUBTOTAL_TITLE", null, Locale.getDefault()) + ":", Util.getFormattedPrice(cart.getActualTotalprice()), new BigDecimal(cart.getActualTotalprice()), "ot_subtotal", 1);
        OrdersTotal discount = null;

        if (cart.getCoupon() != null) {
            Coupons coupon = cart.getCoupon();
            discount = new OrdersTotal(order.getOrdersId(), "Coupon (" + coupon.getCode() + "- " + coupon.getTitle() + ") :", "-" + Util.getFormattedPrice(cart.getCouponDiscountPrice()), new BigDecimal(-cart.getCouponDiscountPrice()), "ot_discount", 2);
        }

        //OrdersTotal shippingtotal = new OrdersTotal(generatedOrderId, order.getShipping().getModuleName()+"("+order.getShipping().getTitle()+")"+":", StoreUtils.getFormattedPrice(order.getShipping().getCostWithTax()*order.getDisplayCurrency().getValue(),order.getDisplayCurrency()), order.getShipping().getCostWithTax(), "ot_shipping", 3);                      
        // OrdersTotal shippingtotal = new OrdersTotal(generatedOrderId, order.getShipping().getTitle()+":", StoreUtils.getFormattedPrice(order.getShipping().getCostWithTax()*order.getDisplayCurrency().getValue(),order.getDisplayCurrency()), order.getShipping().getCostWithTax(), "ot_shipping", 3);                      
        OrdersTotal total = new OrdersTotal(order.getOrdersId(), messageSource.getMessage("MODULE_ORDER_TOTAL_TOTAL_TITLE", null, Locale.getDefault()) + ":", Util.getFormattedPrice(cart.getTotalprice()), new BigDecimal(cart.getTotalprice()), "ot_total", 5);

        orderTotalList.add(subtotal);
        if (discount != null) {
            orderTotalList.add(discount);
        }
        //orderTotalList.add(shippingtotal);           

        /*Map<Integer,TaxGroup> taxgroups = order.getTaxGroups();    
            if(taxgroups!=null && taxgroups.size()>0){
                for(Map.Entry<Integer,TaxGroup> entry : taxgroups.entrySet()){
                    TaxGroup group = entry.getValue();
                    if(group.getTotal()>0){
                      OrdersTotal tax = new OrdersTotal(generatedOrderId, group.getTaxRates().getTaxDescription()+":", StoreUtils.getFormattedPrice(group.getTotal()*order.getDisplayCurrency().getValue(),order.getDisplayCurrency()), group.getTotal(), "ot_tax", 4);                        
                       orderTotalList.add(tax);   
                    }
                }
            }*/
        orderTotalList.add(total);

        for (OrdersTotal ot : orderTotalList) {
            hdao.save(ot);
        }

        order.setOrderTotalList(orderTotalList);

    }

    @Transactional
    private void insertOrderStatusHistory(Orders order, Cart cart) {

        OrdersStatusHistory history = new OrdersStatusHistory();
        history.setOrdersId(order.getOrdersId());
        history.setOrdersStatusId(order.getOrdersStatus());
        history.setDateAdded(new Date());

        String sendEmails = SettingsUtil.getSettings("SEND_EMAILS");
        if (StringUtils.isNotBlank(sendEmails) && "true".equalsIgnoreCase(sendEmails.trim())) {
            history.setCustomerNotified(1);
        } else {
            history.setCustomerNotified(0);
        }
        history.setComments("");
        hdao.save(history);
    }

    @Transactional
    private void insertOrderProducts(Orders order, Cart cart) {

        Map<String, OrderProductItem> orderproductsMap = cart.getItemsMap();
        if (orderproductsMap != null && orderproductsMap.size() > 0) {
            List<OrdersProducts> orderProducts = new ArrayList<OrdersProducts>();
            for (OrderProductItem op : orderproductsMap.values()) {
                Offers offer = op.getProduct();
                OrdersProducts prod = new OrdersProducts();
                prod.setOrdersId(order.getOrdersId());
                prod.setProductsId(offer.getOfferID());
                prod.setProductsName(offer.getTitle());
                prod.setProductsPrice(offer.getPrice());
                prod.setFinalPrice(offer.getOfferPrice());
                prod.setProductsTax(new BigDecimal(0));
                prod.setProductsQuantity(op.getQuantity());
                prod.setReservedPrice(new BigDecimal(op.getOfferPrice()));
                prod.setVendorId(offer.getVendorID());
                if (op.isReserved()) {
                    prod.setReserved(1);
                } else {
                    prod.setReserved(0);
                }
                if (op.isCoupon()) {
                    prod.setCoupon(1);
                } else {
                    prod.setCoupon(0);
                }
                hdao.save(prod);
                op.setOrderProductId(prod.getOrdersProductsId());
                orderProducts.add(prod);
            }
            order.setOrdersProducts(orderProducts);
        }

    }

    @Transactional
    private void insertUserRequest(Orders order, Cart cart) {

        Map<String, OrderProductItem> orderproductsMap = cart.getItemsMap();
        if (orderproductsMap != null && orderproductsMap.size() > 0) {
            List<UserRequests> userRequests = new ArrayList<UserRequests>();
            for (OrderProductItem op : orderproductsMap.values()) {
                if (StringUtils.isNotBlank(op.getUserRequest())) {
                    Offers offer = op.getProduct();
                    UserRequests userRequest = new UserRequests();
                    userRequest.setVendorId(offer.getVendorID());
                    userRequest.setRequest(op.getUserRequest());
                    userRequest.setOrderProductId(op.getOrderProductId());
                    userRequest.setRead(0);
                    userRequest.setUpdatedDate(new Date());
                    hdao.save(userRequest);
                    userRequests.add(userRequest);
                }
            }

            order.setUserRequests(userRequests);
        }

    }

    @Transactional
    private void updateProductsInfo(Orders order, Cart cart) {
        Map<String, OrderProductItem> orderproductsMap = cart.getItemsMap();
        if (orderproductsMap != null && orderproductsMap.size() > 0) {
            for (OrderProductItem op : orderproductsMap.values()) {
                Offers offer = op.getProduct();
                Offers latestOffers = (Offers) hdao.getById(offer.getOfferID(), Offers.class);
                if (latestOffers != null) {
                    int cQuantity = latestOffers.getQuantity() - op.getQuantity();
                    cQuantity = cQuantity < 0 ? 0 : cQuantity;
                    latestOffers.setQuantity(cQuantity);
                    hdao.save(latestOffers);
                }

            }
        }
    }

    @Override
    public List<Offers> getExclusiveOffers(int locationId) {
        return hdao.getExclusiveOffers(locationId);
    }

    @Override
    @Cacheable(value = "cache15Min")
    public Location getLocationByZip(String zip) {
        return hdao.getLocationByZip(zip);
    }

    @Override
    public Orders updateOrderPayment(Cart cart, Orders orderold, Customers customer, Map<String, Object> paymentResponeMap) {
        Orders order = hdao.updateOrderPayment(paymentResponeMap);

        String status = (String) paymentResponeMap.get("status");
        if ("success".equalsIgnoreCase(status)) {
            insertOrderStatusHistory(order, cart);
            insertUserRequest(order, cart);
            updateProductsInfo(order, cart);
            insertVendorNotificationQueue(order);
            insertProductCouponDetails(order, cart);
        }

        return order;
    }

    @Override
    public void sendOrderConfirmationMessage(Customers customer, Orders order, String status) {
        messagingService.sendOrderConfirmationMessage(customer, order, status);

    }

    @Override
    public void sendUserRequestsToVendor(Customers customer, Orders order) {
        List<UserRequests> userRequests = order.getUserRequests();
        List<OrdersProducts> ordersProducts = order.getOrdersProducts();
        if (userRequests != null) {
            for (UserRequests u : userRequests) {
                OrdersProducts op = null;

                for (OrdersProducts orp : ordersProducts) {
                    if (orp.getOrdersProductsId() == u.getOrderProductId()) {
                        op = orp;
                        Integer vendorId = u.getVendorId();
                        Vendor v = hdao.getVendor(vendorId);
                        if (v != null) {
                            messagingService.sendUserRequestMessage(v, u, orp, customer);
                        }
                        break;
                    }
                }
            }
        }
    }

    @Override
    public List<Orders> getOrdersByCustomerId(Integer customersId, int start, int max) {
        return hdao.getOrders(customersId, start, max);

    }

    @Override
    public int getOrderCountByCustomerId(Integer customersId) {
        return hdao.getOrderCountByCustomerId(customersId);
    }

    @Override
    public Orders getOrderById(Integer orderId) {
        return hdao.getOrderById(orderId);
    }

    @Override
    public OrdersProducts getProductByOrderId(Integer orderId) {
        return hdao.getProductByOrderId(orderId);
    }

    @Override
    public Orders getCustomerOrder(Integer orderId, Integer customersId) {
        return hdao.getCustomerOrder(orderId, customersId);
    }

    @Override
    public List<OrdersProducts> getOrderProducts(int orderId) {
        return hdao.getOrderProducts(orderId);
    }

    @Override
    public List<OrdersStatusHistory> getOrderedHistory(int orderId) {
        return hdao.getOrderedHistory(orderId);
    }

    @Override
    public List<Map> getOrdersSummary(Integer customersId, int page, int limit) {
        return hdao.getOrdersSummary(customersId, page, limit);
    }

    @Override
    public void cancelCustomerOrder(Integer customersId, Integer orderId, String comments) {
        hdao.cancelCustomerOrder(customersId, orderId, comments);
    }

    private void insertVendorNotificationQueue(Orders order) {
        VendorNoftificationQueue queue = new VendorNoftificationQueue();
        queue.setOrderId(new BigInteger(order.getOrdersId().toString()));
        hdao.save(queue);

    }

    @Override
    public Map<Integer, String> getOrderStatusCodes() {
        return hdao.getOrderStatusCodes();
    }

    @Override
    public void delete(Object obj) {
        hdao.delete(obj);
    }

    @Override
    @Cacheable("cache5Min")
    public Map<Integer, Long> categoryOffersCount(int locationId) {
        log.debug("Inside categoryOffersCount cache ..***************");
        return hdao.categoryOffersCount(locationId);
    }

    @Override
    @CacheEvict(allEntries = true, value = {"cache15Min", "cache10Min", "cache5Min", "cache60Min"})
    public void removeCaches() {
        log.debug("Removing caches");
    }

    @Override
    @CacheEvict(allEntries = true, value = {"cache60Min"})
    public void removeCategoriesCaches() {
        log.debug("Removing caches");
    }

    @Override
    public void changeOrderStatus(int orderId, String comments, String changeStatus) {
        hdao.changeOrderStatus(orderId, comments, changeStatus);
    }

    @Override
    public Customers getCustomerById(int consumerId) {
        return hdao.getCustomerById(consumerId);
    }

    @Override
    public String getKey(Integer productId, boolean reserve) {
        String key = null;
        if (reserve) {
            key = productId + reserveKeyword;
        } else {
            key = productId + "";
        }

        return key;
    }

    @Override
    public String getProductCouponKey(Integer productId) {
        return productId + CartController.productCouponKeyword;
    }

    @Override
    public List<Object[]> listCustomerCoupons(Integer customersId) {
        return hdao.listCustomerCoupons(customersId);
    }

    @Override
    public List<Offers> getFavouriteProducts(Integer customersId) {
        return hdao.getFavouriteProducts(customersId);
    }

    @Override
    public boolean deleteFavouriteProducts(Integer customersId, int favProductId) {
        return hdao.deleteFavouriteProducts(customersId, favProductId);
    }

    @Override
    public Coupons validateCouponCode(String couponCode, double totalpriceWithTax, Integer customerId) {
        Coupons coupons = null;
        CustomerCoupons customerCoupons = null;

        Object[] list = hdao.getCoupons(couponCode, customerId);
        if (list == null) {
            return null;
        }

        coupons = (Coupons) list[0];
        customerCoupons = (CustomerCoupons) list[1];

        if (totalpriceWithTax < coupons.getMin_amount()) {
            return null; //price should be greater thatn min amount
        }

        Date startDate = coupons.getStart_date();
        Date endDate = coupons.getEnd_date();
        Date now = new Date();
        if (startDate != null && now.before(startDate)) {
            return null; //coupons not yet started
        }

        if (endDate != null && now.after(endDate)) {
            return null; //coupons expired
        }

        int usageType = coupons.getUsage_type();

        if (usageType == 0 && coupons.getNumber_of_redeems() > 0) { //once -  already used
            return null;
        } else if (usageType == 1 && coupons.getNumber_of_redeems() >= coupons.getMax_redeem_count()) { //multiple 
            return null;
        }

        if (coupons.getNumber_of_times_to_use() <= 1 && coupons.getAllow_multiple_redeems_by_same_user() == 0 && customerCoupons != null) {
            return null; //since already coupon is used by user
        }

        if (customerCoupons != null && coupons.getNumber_of_times_to_use() > 1 && customerCoupons.getNumberOfTimesUsed() >= coupons.getNumber_of_times_to_use()) {
            return null; //user has used all the amount
        }
        return coupons;
    }

    @Override
    public List<OrdersTotal> getOrderTotals(Integer orderId) {
        return hdao.getOrdersTotals(orderId);
    }

    @Override
    public boolean addUserPushDetails(Integer customersId, String pushkey, String deviceos, int status) {
        return hdao.addUserPushDetails(customersId, pushkey, deviceos, status);
    }

    @Override
    public boolean addCustomerFavourite(Integer customersId, int productId) {
        return hdao.addCustomerFavourite(customersId, productId);
    }

    @Override
    public boolean sendVendorRequest(Map inputdata) {
        try {
            messagingService.sendVendorAdditionRequest(inputdata);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean addCustomerReview(Customers customers, int productId, int rating, String description) {
        return hdao.addCustomerReview(customers, productId, rating, description);
    }

    @Override
    public List<Reviews> getReviews(int productId) {
        return hdao.getReviews(productId);
    }

    @Override
    public boolean sendPush(int offerId, String message) {
        return hdao.sendPush(offerId, message);
    }

    private void insertProductCouponDetails(Orders order, Cart cart) {
        Map<String, OrderProductItem> orderproductsMap = cart.getItemsMap();

        for (OrderProductItem op : orderproductsMap.values()) {
            if (op.isCoupon()) {
                ProductCoupon productCoupon = new ProductCoupon();
                productCoupon.setOrderID(order.getOrdersId());
                productCoupon.setProductID(op.getOrderProductId());
                productCoupon.setQuantity(op.getQuantity());
                productCoupon.setVendorID(op.getProduct().getVendorID());
                productCoupon.setStartDate(Calendar.getInstance().getTime());
                productCoupon.setProductName(op.getProduct().getTitle());
                Calendar cal = new GregorianCalendar();
                cal.add(Calendar.DATE, 7);
                productCoupon.setEndDate(cal.getTime());
                hdao.addProductCoupon(productCoupon);
            }
        }
    }

    @Override
    public ProductCoupon getProductCouponsByOrderIdAndProductId(Integer orderID, Integer prodcutID) {
        return hdao.getProductCouponsByOrderId(orderID, prodcutID);
    }

    @Override
    public Location getDefaultLocation() {
        return hdao.getDefaultLocation();
    }

    @Override
    public boolean isSocialUserExist(String accountkey, String sitename, String email) {
        log.debug("Social user service");
        return hdao.isSocialUserExist(accountkey, sitename, email);
    }

    @Override
    public Customers getScoialUser(String accountkey, String sitename, String email) {
        return hdao.getSocialUser(accountkey, sitename, email);
    }

    @Override
    public boolean insertSocialUser(Customers cmr) {
        log.debug("inserting");
        return hdao.insertSocialUser(cmr);
    }

}
