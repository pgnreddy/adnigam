/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cnbitsols.adrobe.services;

import com.cnbitsols.adrobe.dto.SearchObj;
import com.cnbitsols.adrobe.entities.Offers;
import com.cnbitsols.adrobe.entities.ProductCoupon;
import com.cnbitsols.adrobe.entities.Vendor;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author santosh.r
 */
public interface VendorService { 

    public Vendor vendorLogin(String username, String password);

    public SearchObj listVendorOffers(Integer vendorID,int page,int limit,String productName,int offerID);

    public Offers saveOffer(Vendor vendor, Offers offer)throws Exception;

    public void deleteOffer(Integer vendorID, int offerID);

    public void updateVendorProfile(Vendor vendor);

    public void uploadCoverImage(Vendor vendor, MultipartFile file)throws Exception;
    
    public void uploadVendorPromo(Vendor vendor, MultipartFile file) throws Exception;

    public void setIsHot(Integer vendorID, int offerID);

    public SearchObj listVendorRequests(Integer vendorID,int page,int limit,int status);

    public SearchObj listVendorOrders(Integer vendorID, int page, int limit,int status,int orderId);

    public Vendor getVendorByMobile(String mobile);

    public boolean sendForgotPassword(Vendor vendor);

    public boolean updateUserRequest(Integer vendorID, int requestId, int status);

    public Offers getVendorOffer(Integer vendorID, int offerID);

    public void updateOrderProductStatus(Integer vendorID, int orderProductId, int status, String comment);

    public boolean sendVendorMessage(Vendor vendor, String message);
    
    public SearchObj listProductCoupons(Integer vendorID, int page, int limit, int orderId, String productCouponCode);
    
    public void sellProductCoupon(Vendor vendor, String productCouponCode);
    
    public boolean validateProductCoupon(Integer vendorID, String productCouponCode);
    
    public List<ProductCoupon> getProductCouponsForCustomerByVendor(String mobileNumber, Integer vendorID);
    
}