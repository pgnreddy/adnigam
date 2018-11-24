/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cnbitsols.adrobe.services;

import com.cnbitsols.adrobe.dto.SearchObj;
import com.cnbitsols.adrobe.entities.Admin;
import com.cnbitsols.adrobe.entities.Categories;
import com.cnbitsols.adrobe.entities.Coupons;
import com.cnbitsols.adrobe.entities.Customers;
import com.cnbitsols.adrobe.entities.Location;
import com.cnbitsols.adrobe.entities.Offers;
import com.cnbitsols.adrobe.entities.OrderCancellation;
import com.cnbitsols.adrobe.entities.Orders;
import com.cnbitsols.adrobe.entities.PremiumOffers;
import com.cnbitsols.adrobe.entities.Reviews;
import com.cnbitsols.adrobe.entities.Vendor;
import java.util.List;
import java.util.Map;

/**
 *
 * @author santosh.r
 */
public interface AdminService {

    public Admin findAdminByEmail(String username);

    public List<Vendor> listVendors(int start, int max, String name, String Email, String Phone, String category);

    public List<Orders> listOrders(int start, int max, String orderid, String custormername, String city);

    public List<OrderCancellation> listCancelOrders(int start, int max, String orderid);

    public List<Offers> getOffers(int state, int start, int max, String title);
    
    public List<Offers> getAllApprovedOffers(int start, int max, String seo_title);
    
    public Offers getOfferBySeoTitle(String seo_title);

    public List<PremiumOffers> getPremiumOffers(int state, int start, int max, String title);

    public List<Categories> getCategories(int start, int limit);

    public List<Admin> getAdminList(int start, int limit);

    public List<Location> getLocationList(int start, int limit, String name, String zipCode);

    public int getVendorCount(String name, String Email, String Phone);

    public int getOrderCount(String orderid, String custormername, String city);

    public int getCancelOrderCount(String orderid);

    public int getOffersCount(int state, String title);

    public int getPremiumCount(int state, String title);

    public int getCategoryCount();

    public int getAdminCount();

    public int getLocationCount(String name, String zipCode);

    public void addVendor(Admin admin, Vendor vendor);

    public Offers saveOffer(Offers offer) throws Exception;

    public PremiumOffers savePremiumOffer(PremiumOffers preOffer) throws Exception;

    public Categories saveCategory(Categories category) throws Exception;

    public Location saveLocation(Location location) throws Exception;

    public void updateVendorProfile(Vendor vendor);

    public void deleteVendor(Integer adminID, int vendorID);

    public void deleteOffer(Integer adminID, int offerId);

    public void deletePremiumOffer(Integer adminID, int preOfferId);

    public void deleteCategory(Integer adminID, int catId);

    public void deleteLocation(Integer adminID, int locId);

    public void deleteCustomer(Integer adminID, int customerId);

    public Map<Integer, String> getVendorNames();

    public List<Customers> listCustomers(int start, int max, String name, String Email, String Phone);

    public int getCustomersCount(String name, String Email, String Phone);

    public Coupons getCoupon(int couponId);

    public void saveCoupon(Coupons coupons);

    public void deleteCoupon(int couponId);

    public List<Coupons> getCoupons(SearchObj searchObj, String searchType, String searchTerm);

    public Object getObject(Class entityClass, String[] fields, Object[] values);

    public Customers updateCustomer(Customers custmers);

    public Reviews getReview(int reviewId);

    public void saveReviews(Reviews reviews);

    public void deleteReviews(int reviewId);

    public double getProductAverageRating(int productsId);

    public List<Reviews> getReviews(SearchObj searchObj);

    public Map<String, Object> getReports(Map<String, String> requestMap);
    
    public List<Offers> getUnapprovedOffers(int start, int limit, String title);
    
    public int getPendingOffersCount();
    
    public int getAllApprovedOffersCount();
    
    public void approveOffer(int offerId);
    
    public void deleteUnapprovedOffer(int offerId);
}
