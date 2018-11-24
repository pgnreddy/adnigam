/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cnbitsols.adrobe.services;

import com.cnbitsols.adrobe.dto.Cart;
import com.cnbitsols.adrobe.dto.Payment;
import com.cnbitsols.adrobe.entities.AddressBook;
import com.cnbitsols.adrobe.entities.Categories;
import com.cnbitsols.adrobe.entities.Coupons;
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
import com.cnbitsols.adrobe.entities.Vendor;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author santosh.r
 */
public interface AdrobeService {

    public boolean login(String userName, String password);

    public List<Categories> listCategories();

    public void incrementRefCount(String adRef);

    public List<Categories> listMenuCategories();

    public List<Categories> listCategoriesWithSubCategories();

    public List<Location> listLocations();

    public List<PremiumOffers> listPremiumOffers(int locationId);

    public String getCategoriesJson();

    public List<Object[]> listProducts(String category, int locationId, int page, int limit, String searchTerm);

    public List<Object[]> listNearByProducts(double latitude, double longitude, int page, int limit, int distance);

    public Categories getCategoryBySeoName(String category);

    public Vendor getVendor(int vendorId);

    public List<Offers> listVendorProducts(int vendorId);

    public Offers getOffer(int offerId);

    public Map<Integer, Offers> getProducts(Set<Integer> productsIds);

    public List<Offers> getTodaysOffers(int locationId, int limit);

    public Customers getCustomer(String userName);

    public Customers gtCustomerByTelephone(String phoneNumber);

    public boolean sendForgotPassword(Customers customer);

    public boolean addNewCustomer(Customers customer);

    public List<AddressBook> getCustomerAddresses(Integer customersId);

    public void save(Object obj);

    public Object getById(Serializable key, Class clazz);

    public AddressBook getAddressById(int selectedAddress, Integer customersId);

    public Orders createOrder(Cart cart, Customers customer, Payment payment);

    public List<Offers> getExclusiveOffers(int locationId);

    public Location getLocationByZip(String zip);

    public Location getDefaultLocation();

    public Orders updateOrderPayment(Cart cart, Orders order, Customers customer, Map<String, Object> paymentResponeMap);

    public void sendOrderConfirmationMessage(Customers customer, Orders order, String status);

    public void sendUserRequestsToVendor(Customers customer, Orders order);

    public List<Orders> getOrdersByCustomerId(Integer customersId, int start, int limit);

    public int getOrderCountByCustomerId(Integer customersId);

    public Orders getOrderById(Integer orderId);

    public OrdersProducts getProductByOrderId(Integer orderId);

    public List<OrdersStatusHistory> getOrderedHistory(int orderId);

    public Orders getOrder(int orderId);

    public List<OrdersProducts> getOrderProducts(int orderId);

    public List<Map> getOrdersSummary(Integer customersId, int page, int limit);

    public Orders getCustomerOrder(Integer orderId, Integer customersId);

    public void cancelCustomerOrder(Integer customersId, Integer orderId, String comments);

    public Map<Integer, String> getOrderStatusCodes();

    public void delete(Object obj);

    public Map<Integer, Long> categoryOffersCount(int locationId);

    public List<Object[]> searchProducts(String category, int page, int limit, String searchTerm);

    public Offers getProduct(String productName);

    public void removeCaches();

    public void removeCategoriesCaches();

    public void changeOrderStatus(int orderId, String comments, String changeStatus);

    public Customers getCustomerById(int consumerId);

    public String getKey(Integer productId, boolean reserve);

    public String getProductCouponKey(Integer productId);

    public List<Object[]> listCustomerCoupons(Integer customersId);

    public List<Offers> getFavouriteProducts(Integer customersId);

    public boolean deleteFavouriteProducts(Integer customersId, int favProductId);

    public Coupons validateCouponCode(String code, double actualTotalprice, Integer customersId);

    public List<OrdersTotal> getOrderTotals(Integer orderId);

    public boolean addUserPushDetails(Integer customersId, String pushkey, String deviceos, int status);

    public boolean addCustomerFavourite(Integer customersId, int productId);

    public boolean sendVendorRequest(Map inputdata);

    public boolean addCustomerReview(Customers ustomers, int productId, int rating, String description);

    public List<Reviews> getReviews(int productId);

    public List searchProductsByPrice(String category, int page, int limit, String priceRange, String discountRange);

    public boolean sendPush(int offerId, String message);

    public ProductCoupon getProductCouponsByOrderIdAndProductId(Integer orderID, Integer prodcutId);

    public boolean isSocialUserExist(String accountkey, String sitename, String email);

    public Customers getScoialUser(String accountkey, String sitename, String email);

    public boolean insertSocialUser(Customers cmr);

}
