/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cnbitsols.adrobe.dao;

import com.cnbitsols.adrobe.entities.AddressBook;
import com.cnbitsols.adrobe.entities.Categories;
import com.cnbitsols.adrobe.entities.Customers;
import com.cnbitsols.adrobe.entities.EmailQueue;
import com.cnbitsols.adrobe.entities.Location;
import com.cnbitsols.adrobe.entities.Offers;
import com.cnbitsols.adrobe.entities.Orders;
import com.cnbitsols.adrobe.entities.OrdersProducts;
import com.cnbitsols.adrobe.entities.OrdersStatusHistory;
import com.cnbitsols.adrobe.entities.OrdersTotal;
import com.cnbitsols.adrobe.entities.PremiumOffers;
import com.cnbitsols.adrobe.entities.ProductCoupon;
import com.cnbitsols.adrobe.entities.Reviews;
import com.cnbitsols.adrobe.entities.SmsQueue;
import com.cnbitsols.adrobe.entities.Vendor;
import com.cnbitsols.adrobe.entities.VendorNoftificationQueue;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.hibernate.type.OrderedSetType;

/**
 *
 * @author santosh.r
 */
public interface AdrobeHibernateDAO {

    public List getConfiguration();

    public void incrementRefCount(String adRef);

    public List<Categories> listCategories();

    public List<Location> listLocations();

    public List<PremiumOffers> listPremiumOffers(int locationId);

    public List<Object[]> listProducts(String category, List<Integer> subCategoriesIds, int locationId, int page, int limit, String searchTerm);

    public List<Object[]> listNearByProducts(double latitude, double longitude, int page, int limit, int distance);

    public Categories getCategoryBySeoName(String category);

    public Vendor getVendor(int vendorId);

    public List<Offers> listVendorProducts(int vendorId);

    public Offers getOffer(int offerId);

    public Orders getOrder(int orderId);

    public Map<Integer, Offers> getProducts(Set<Integer> productsIds);

    public List<Offers> getTodaysOffers(int locationId, int limit);

    public Customers getCustomer(String userName);

    public Customers getCustomerByPhone(String phoneNumber);

    public void addNewCustomer(Customers customer);

    public List<AddressBook> getCustomerAddresses(Integer customersId);

    public void save(Object obj);

    public Object getById(Serializable key, Class clazz);

    public AddressBook getAddressById(int selectedAddress, Integer customersId);

    public List<Offers> getExclusiveOffers(int locationId);

    public List<EmailQueue> getPendingEmails();

    public List<SmsQueue> getPendingSMSs();

    public Location getLocationByZip(String zip);

    public Location getDefaultLocation();

    public Orders updateOrderPayment(Map<String, Object> paymentResponeMap);

    public List<Orders> getOrders(Integer customersId, int start, int max);

    public int getOrderCountByCustomerId(Integer customersId);

    public Orders getOrderById(Integer orderId);

    public OrdersProducts getProductByOrderId(Integer orderId);

    public List<OrdersProducts> getOrderProducts(int orderId);

    public List<OrdersStatusHistory> getOrderedHistory(int orderId);

    public List<Map> getOrdersSummary(Integer customersId, int page, int limit);

    public Orders getCustomerOrder(Integer orderId, Integer customersId);

    public void cancelCustomerOrder(Integer customersId, Integer orderId, String comments);

    public List<VendorNoftificationQueue> getVendorNotifications();

    public void delete(Object object);

    public Map getOrderStatusCodes();

    public Map<Integer, Long> categoryOffersCount(int locationId);

    public List<Object[]> searchProducts(String category, List<Integer> subCategoriesIds, int page, int limit, String searchTerm);

    public Offers getProduct(String productName);

    public void changeOrderStatus(int orderId, String comments, String changeStatus);

    public Customers getCustomerById(int consumerId);

    public Object getObject(Class entityClass, String[] fields, Object[] values);

    public List<Object[]> listCustomerCoupons(Integer customersId);

    public List<Offers> getFavouriteProducts(Integer customersId);

    public boolean deleteFavouriteProducts(Integer customersId, int favProductId);

    public Object[] getCoupons(String couponCode, Integer customerId);

    public List<OrdersTotal> getOrdersTotals(int orderId);

    public boolean addCustomerFavourite(Integer customersId, int productId);

    public boolean addCustomerReview(Customers customers, int productId, int rating, String description);

    public List<Reviews> getReviews(int productId);

    public boolean addUserPushDetails(Integer customersId, String pushkey, String deviceos, int status);

    public List searchProductsByPrice(String category, List<Integer> subCategoriesIds, int page, int limit, String priceRange, String discountRange);

    public boolean sendPush(int offerId, String message);

    public void addProductCoupon(ProductCoupon productCoupon);

    public ProductCoupon getProductCouponsByOrderId(Integer orderID, Integer productID);

    public boolean isSocialUserExist(String accountkey, String sitename, String email);

    public Customers getSocialUser(String accountkey, String sitename, String email);

    public boolean insertSocialUser(Customers cmr);
}
