/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cnbitsols.adrobe.dao;

import com.cnbitsols.adrobe.dto.SearchObj;
import com.cnbitsols.adrobe.entities.Offers;
import com.cnbitsols.adrobe.entities.OrdersProductsStatusHistory;
import com.cnbitsols.adrobe.entities.ProductCoupon;
import com.cnbitsols.adrobe.entities.Vendor;
import java.util.List;

/**
 *
 * @author santosh.r
 */
public interface VendorHibernateDAO {

    public Vendor findVendorByEmail(String userName);

    public SearchObj listVendorOffers(Integer vendorID,int page,int limit,String productName,int offerID);

    public Offers saveOffer(Vendor vendor, Offers offer);

    public void deleteOffer(Integer vendorID, int offerID);

    public void updateVendorProfile(Vendor vendor);

    public void setIsHot(Integer vendorID, int offerID);

    public SearchObj listVendorRequests(Integer vendorID,int page,int limit,int status);

    public SearchObj listVendorOrders(Integer vendorID, int page, int limit,int status,int orderId);

    public boolean updateUserRequest(Integer vendorID, int requestId, int status);

    public Offers getVendorOffer(Integer vendorID, int offerID);

    public Object[] updateOrderProductStatus(Integer vendorID, int orderProductId, int status, String comment);
    
    public SearchObj listProductCouponCode(Integer vendorID, int page, int limit, int orderID, String productCouponCode);
    
    public void sellProductCoupon(Vendor vendor, String productCouponCode);
    
    public boolean validateProductCoupon(Integer vendorID, String productCouponCode);
    
    public List<ProductCoupon> getProductCouponsForCustomerByVendor(String mobileNumber, Integer vendorID);
    
}
