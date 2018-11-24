/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cnbitsols.adrobe.services.impl;

import com.cnbitsols.adrobe.dao.AdrobeHibernateDAO;
import com.cnbitsols.adrobe.dao.VendorHibernateDAO;
import com.cnbitsols.adrobe.dto.SearchObj;
import com.cnbitsols.adrobe.entities.Offers;
import com.cnbitsols.adrobe.entities.Orders;
import com.cnbitsols.adrobe.entities.OrdersProducts;
import com.cnbitsols.adrobe.entities.ProductCoupon;
import com.cnbitsols.adrobe.entities.Vendor;
import com.cnbitsols.adrobe.entities.VendorNoftificationQueue;
import com.cnbitsols.adrobe.services.MessagingService;
import com.cnbitsols.adrobe.services.VendorService;
import com.cnbitsols.adrobe.utils.SettingsUtil;
import com.cnbitsols.adrobe.utils.TranscoderUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author santosh.r
 */
@Service("vendorServiceImpl")
public class VendorServiceImpl implements VendorService {

    private static Logger log = LoggerFactory.getLogger(VendorServiceImpl.class);

    @Autowired
    private VendorHibernateDAO hdao = null;

    @Autowired
    private AdrobeHibernateDAO adao = null;

    @Autowired
    private MessagingService mService = null;

    @Override
    public Vendor vendorLogin(String userName, String password) {
        return hdao.findVendorByEmail(userName);
    }

    @Override
    public SearchObj listVendorOffers(Integer vendorID,int page,int limit,String productName,int offerID) {
        return hdao.listVendorOffers(vendorID,page,limit,productName,offerID);
    }

    @Override
    public Offers saveOffer(Vendor vendor, Offers offer) throws Exception {

        AdrobeS3DAOImpl s3Service = new AdrobeS3DAOImpl();
        MultipartFile uploadImage = offer.getUploadImage();
        if (uploadImage != null) {
            String assetsPath = SettingsUtil.getSettings("IMAGES_PHYSICAL_PATH");
            File file = new File(assetsPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            String bucketName = SettingsUtil.getSettings("S3_BUCKET");
            String ts = System.currentTimeMillis() + ".jpg";
            String fileName = "o_"+ts;
            File imageFile = new File(assetsPath + fileName);
            uploadImage.transferTo(imageFile);
            s3Service.upload(bucketName, fileName, new FileInputStream(imageFile));

            String smallImage = "s_"+ts;
            //ImageUtils.transcodeImage(assetsPath+fileName, assetsPath+smallImage, 268, 327);
            String dimensions = SettingsUtil.getSettings("PRODUCT_IMAGE_DIMENSIONS");
            String[] dims = dimensions.split("x");
            TranscoderUtils.transcodeImage(assetsPath+fileName, assetsPath+smallImage, NumberUtils.toDouble(dims[0],268), NumberUtils.toDouble(dims[1],327));
            File smallImageFile = new File(assetsPath+smallImage);
            s3Service.upload(bucketName, smallImage, new FileInputStream(smallImageFile));
            smallImageFile.delete();
            
            String largeImage = "l_"+ts;
            dimensions = SettingsUtil.getSettings("PRODUCT_IMAGE_DIMENSIONS_LARGE");
            dims = dimensions.split("x");
            TranscoderUtils.transcodeImage(assetsPath+fileName, assetsPath+largeImage, NumberUtils.toDouble(dims[0],800), NumberUtils.toDouble(dims[1],975));
            File largeImageFile = new File(assetsPath+largeImage);
            s3Service.upload(bucketName, largeImage, new FileInputStream(largeImageFile));
            largeImageFile.delete();
            
            offer.setImage(smallImage);
            //offer.setImage_original(fileName);
            offer.setImage_original(largeImage);
            
            imageFile.delete();
        }
        return hdao.saveOffer(vendor, offer);
    }

    @Override
    public void deleteOffer(Integer vendorID, int offerID) {
        hdao.deleteOffer(vendorID, offerID);
    }

    @Override
    public void updateVendorProfile(Vendor vendor) {
        hdao.updateVendorProfile(vendor);
    }

    @Override
    public void uploadCoverImage(Vendor vendor, MultipartFile uploadImage) throws Exception {

        if (uploadImage != null) {

//            String assetsPath = SettingsUtil.getSettings("IMAGES_PHYSICAL_PATH");
            String bucketName = SettingsUtil.getSettings("S3_BUCKET");
            String fileName = System.currentTimeMillis() + ".jpg";
            AdrobeS3DAOImpl s3Service = new AdrobeS3DAOImpl();

            s3Service.upload(bucketName, fileName, uploadImage.getInputStream());
//            uploadImage.transferTo(new File(assetsPath + fileName));

            vendor.setCoverImage(fileName);
        }
        hdao.updateVendorProfile(vendor);
    }

    @Override
    public void setIsHot(Integer vendorID, int offerID) {
        hdao.setIsHot(vendorID, offerID);
    }

    @Override
    public SearchObj listVendorRequests(Integer vendorID, int page, int limit,int status) {
        return hdao.listVendorRequests(vendorID, page, limit,status);
    }

    @Override
    public SearchObj listVendorOrders(Integer vendorID, int page, int limit,int status,int orderId) {
        return hdao.listVendorOrders(vendorID,page,limit,status,orderId);
    }

    @Override
    public Vendor getVendorByMobile(String mobile) {
        return hdao.findVendorByEmail(mobile);
    }

    @Override
    public boolean sendForgotPassword(Vendor vendor) {
        log.debug("sending forgot password email");
        mService.sendVendorForgotPasswordMessage(vendor);
        return true;
    }
        
    
    
    
    

    public synchronized void processVendorNotificationsQueue() {
        log.debug("processVendorNotificationsQueue processing job...started");
        List<VendorNoftificationQueue> notifications = null;
        do {
            notifications = adao.getVendorNotifications();
            if (notifications != null && notifications.size() > 0) {
                for (VendorNoftificationQueue notification : notifications) {
                    processVendorNotification(notification);
                }
            }
        } while (notifications != null && notifications.size() > 0);
        log.debug("processVendorNotificationsQueue job...end");

    }

    private void processVendorNotification(VendorNoftificationQueue notification) {
        int orderId = notification.getOrderId().intValue();
        Orders order = adao.getOrderById(orderId);
        List<OrdersProducts> orderProducts = adao.getOrderProducts(orderId);

        for (OrdersProducts op : orderProducts) {
            if (op.getVendorId() > 0) {
                Vendor vendor = adao.getVendor(op.getVendorId());
                if (vendor != null) {
                    mService.sendVendorOrderNotification(order, op, vendor);
                }
            }
        }

        adao.delete(notification);
    }

    @Override
    public boolean updateUserRequest(Integer vendorID, int requestId, int status) {
        return hdao.updateUserRequest(vendorID,requestId,status);
    }

    @Override
    public Offers getVendorOffer(Integer vendorID, int offerID) {
        return hdao.getVendorOffer(vendorID,offerID);
    }

    @Override
    public void updateOrderProductStatus(Integer vendorID, int orderProductId, int status, String comment) {
        Object[] res = hdao.updateOrderProductStatus(vendorID,orderProductId,status,comment);
        OrdersProducts op = (OrdersProducts)res[0];
        if(op==null){
            return;
        }
        
        Orders orders = adao.getOrder(op.getOrdersId());
        if(orders==null){
            return;
        }        
        
        //mService.sendOrdersProductStatusChangeMessage(orders,op,()res[1]);
    }

    @Override
    public boolean sendVendorMessage(Vendor vendor, String message) {
        try{
            mService.sendVendorMessage(vendor,message);
        }catch(Exception e){
            return false;
        }
        return true;
    }

    @Override
    public SearchObj listProductCoupons(Integer vendorID, int page, int limit, int orderId, String productCouponCode) {
        return hdao.listProductCouponCode(vendorID, page, limit, orderId, productCouponCode);
    }

    @Override
    public void sellProductCoupon(Vendor vendor, String productCouponCode) {
        hdao.sellProductCoupon(vendor, productCouponCode);
    }

    @Override
    public boolean validateProductCoupon(Integer vendorID, String productCouponCode) {
        return hdao.validateProductCoupon(vendorID, productCouponCode);
    }

    @Override
    public List<ProductCoupon> getProductCouponsForCustomerByVendor(String mobileNumber, Integer vendorID) {
        return hdao.getProductCouponsForCustomerByVendor(mobileNumber, vendorID);
    }

    @Override
    public void uploadVendorPromo(Vendor vendor, MultipartFile file) throws Exception {
        String path = "/apps/adrobe/binaries/adrobe/resources/vendorpromo/";
        
        File folder = new File(path);
        
        if (!folder.exists()) {
            folder.mkdirs();
        }
        
        String transferPath = path + file.getOriginalFilename();
        
        file.transferTo(new File(transferPath));
    }
    
    

}
