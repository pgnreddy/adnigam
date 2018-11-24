/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cnbitsols.adrobe.services.impl;

import com.cnbitsols.adrobe.dao.AdminHibernateDAO;
import com.cnbitsols.adrobe.dao.AdrobeHibernateDAO;
import com.cnbitsols.adrobe.dao.impl.AdminHibernateDAOImpl;
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
import com.cnbitsols.adrobe.services.AdminService;
import com.cnbitsols.adrobe.utils.SettingsUtil;
import com.cnbitsols.adrobe.utils.TranscoderUtils;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author gopal
 */
@Service
public class AdminServiceImpl implements AdminService {

    private static Logger log = LoggerFactory.getLogger(AdminHibernateDAOImpl.class);

    @Autowired
    private AdminHibernateDAO hdao = null;
    
    @Autowired
    private AdrobeHibernateDAO adao = null;

    @Override
    public Admin findAdminByEmail(String userName) {
        return hdao.findAdminByEmail(userName);
    }

    @Override
    public List<Vendor> listVendors(int start, int max, String name, String Email, String Phone, String category) {
        return hdao.listVendors(start, max, name, Email, Phone, category);
    }

    @Override
    public List<Orders> listOrders(int start, int max, String orderid, String custormername, String city) {
        return hdao.listOrders(start, max, orderid, custormername, city);
    }

    @Override
    public List<OrderCancellation> listCancelOrders(int start, int max, String orderid) {
        return hdao.listCancelOrders(start, max, orderid);
    }

    @Override
    public List<Offers> getOffers(int state, int start, int max, String title) {
        return hdao.getOffers(state, start, max, title);
    }

    @Override
    public List<PremiumOffers> getPremiumOffers(int state, int start, int max, String title) {
        return hdao.getPremiumOffers(state, start, max, title);
    }

    @Override
    public void addVendor(Admin admin, Vendor vendor) {
        hdao.addVendor(admin, vendor);
    }

    @Override
    public void updateVendorProfile(Vendor vendor) {
        hdao.updateVendorProfile(vendor);
    }

    @Override
    public int getVendorCount(String name, String Email, String Phone) {
        return hdao.getVendorCount(name, Email, Phone);
    }

    @Override
    public int getOrderCount(String orderid, String custormername, String city) {
        return hdao.getOrderCount(orderid, custormername, city);
    }

    @Override
    public int getCancelOrderCount(String orderid) {
        return hdao.getCancelOrderCount(orderid);
    }

    @Override//SELECT o FROM Offers o WHERE  o.status=:status order by offerID desc
    public int getOffersCount(int state, String title) {//o.status=:status order by offerID desc
        return hdao.getOffersCount(state, title);
    }

    @Override//SELECT o FROM Offers o WHERE  o.status=:status order by offerID desc
    public int getPremiumCount(int state, String title) {//o.status=:status order by offerID desc
        return hdao.getPremiumCount(state, title);
    }

    @Override
    public List<Categories> getCategories(int start, int limit) {
        return hdao.getCategories(start, limit);
    }

    @Override
    public List<Admin> getAdminList(int start, int max) {
        return hdao.getAdminList(start, max);
    }

    @Override
    public int getAdminCount() {
        return hdao.getAdminCount();
    }

    @Override
    public int getCategoryCount() {
        return hdao.getCategoryCount();
    }

    @Override
    public List<Location> getLocationList(int start, int max,String name, String zipCode) {
        return hdao.getLocationList(start, max,name, zipCode);
    }

    @Override
    public int getLocationCount(String name, String zipCode) {
        return hdao.getLocationCount(name, zipCode);
    }

    @Override
    public Offers saveOffer(Offers offer) throws Exception {

        MultipartFile uploadImage = offer.getUploadImage();
        if (uploadImage != null) {
            AdrobeS3DAOImpl s3Service = new AdrobeS3DAOImpl();
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
        return hdao.saveOffer(offer);
    }

    @Override
    public PremiumOffers savePremiumOffer(PremiumOffers preOffer) throws Exception {

        MultipartFile uploadImage = preOffer.getUploadImage();
        if (uploadImage != null) {
            String assetsPath = SettingsUtil.getSettings("IMAGES_PREMIUM_PATH");
            String fileName = System.currentTimeMillis() + ".jpg";
            uploadImage.transferTo(new File(assetsPath + fileName));
            preOffer.setImage(fileName);
        }
        return hdao.savePremiumOffer(preOffer);
    }

    @Override
    public Categories saveCategory(Categories category) throws Exception {

        MultipartFile uploadImage = category.getUploadImage();
        if (uploadImage != null) {
            String assetsPath = SettingsUtil.getSettings("IMAGES_CATEGORY_PATH");
            String fileName = System.currentTimeMillis() + ".jpg";
            uploadImage.transferTo(new File(assetsPath + fileName));
            category.setCategoryIcon(fileName);
        }
        return hdao.saveCategory(category);
    }

    @Override
    public Location saveLocation(Location location) throws Exception {
        return hdao.saveLocation(location);
    }

    @Override
    public void deleteVendor(Integer adminID, int vendorID) {
        hdao.deleteVendor(adminID, vendorID);
    }

    @Override
    public void deleteOffer(Integer adminID, int offerID) {
        hdao.deleteOffer(adminID, offerID);
    }

    @Override
    public void deletePremiumOffer(Integer adminID, int preOfferID) {
        hdao.deletePremiumOffer(adminID, preOfferID);
    }

    @Override
    public void deleteCategory(Integer adminID, int catId) {
        hdao.deleteCategory(adminID, catId);
    }

    @Override
    public void deleteLocation(Integer adminID, int locId) {
        hdao.deleteLocation(adminID, locId);
    }
    @Override
    public void deleteCustomer(Integer adminID, int customerId) {
        hdao.deleteCustomer(adminID, customerId);
    }
    @Override
     public Map<Integer, String> getVendorNames() {
       return hdao.getVendorNames();
    }     
     
     
      @Override
    public List<Customers> listCustomers(int start, int max, String name, String Email, String Phone) {
        return hdao.listCustomers(start, max, name, Email, Phone);
    }
    
        @Override
    public int getCustomersCount(String name, String Email, String Phone) {
        return hdao.getCustomersCount(name, Email, Phone);
    }

    @Override
    public Coupons getCoupon(int couponId) {
        return (Coupons)adao.getById(couponId, Coupons.class);
    }

    @Override
    public void saveCoupon(Coupons coupons) {
        adao.save(coupons);
    }

    @Override
    public void deleteCoupon(int couponId) {
        hdao.deleteCoupon(couponId);
    }

    @Override
    public List<Coupons> getCoupons(SearchObj searchObj, String searchType, String searchTerm) {
        return hdao.getCoupons(searchObj, searchType, searchTerm);
    }

    @Override
    public Object getObject(Class entityClass, String[] fields, Object[] values) {
        return adao.getObject(entityClass,fields,values);
    }
    @Override
    public  Customers updateCustomer(Customers custmers) {
        return hdao.updateCustomer(custmers);
    }

    @Override
    public Reviews getReview(int reviewId) {
        Reviews r =  (Reviews)adao.getById(reviewId, Reviews.class);
        if(r!=null){
            Offers o = (Offers)adao.getById(r.getProductsId(), Offers.class);
            r.setProducts(o);
        }
        
        return r;
    }

    @Override
    public void saveReviews(Reviews reviews) {
        adao.save(reviews);
    }

    @Override
    public void deleteReviews(int reviewId) {
        adao.delete(getReview(reviewId));
    }

    @Override
    public double getProductAverageRating(int productsId) {
        return hdao.getProductAverageRating(productsId);
    }

    @Override
    public List<Reviews> getReviews(SearchObj searchObj) {
        return hdao.getReviews(searchObj);
    }

    @Override
    public Map<String, Object> getReports(Map<String, String> requestMap) {
        return hdao.getReports(requestMap);
    }

    @Override
    public List<Offers> getUnapprovedOffers(int start, int limit, String title) {
        return hdao.getPendingOffers(start, limit, title);
    }

    @Override
    public int getPendingOffersCount() {
        return hdao.getPendingOffersCount();
    }

    @Override
    public void approveOffer(int offerId) {
        hdao.approveOffer(offerId);
    }

    @Override
    public void deleteUnapprovedOffer(int offerId) {
        hdao.deleteUnapprovedOffer(offerId);
    }

    @Override
    public List<Offers> getAllApprovedOffers(int start, int max, String seo_title) {
        return hdao.getAllApprovedOffers(start, max, seo_title);
    }

    @Override
    public int getAllApprovedOffersCount() {
        return hdao.getAllApprovedOffersCount();
    }

    @Override
    public Offers getOfferBySeoTitle(String seo_title) {
        return hdao.getOfferBySeoTitle(seo_title);
    }
    
}
