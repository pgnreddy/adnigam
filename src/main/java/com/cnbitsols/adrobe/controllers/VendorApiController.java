/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cnbitsols.adrobe.controllers;

import com.cnbitsols.adrobe.dto.ApiResponse;
import com.cnbitsols.adrobe.dto.SearchObj;
import com.cnbitsols.adrobe.entities.Customers;
import com.cnbitsols.adrobe.entities.Offers;
import com.cnbitsols.adrobe.entities.ProductCoupon;
import com.cnbitsols.adrobe.entities.Vendor;
import com.cnbitsols.adrobe.services.AdrobeService;
import com.cnbitsols.adrobe.services.MessagingService;
import com.cnbitsols.adrobe.services.VendorService;
import com.cnbitsols.adrobe.services.impl.AdrobeS3DAOImpl;
import com.cnbitsols.adrobe.utils.PinUtil;
import com.cnbitsols.adrobe.utils.SettingsUtil;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import javax.ws.rs.QueryParam;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author santosh.r
 */
@Controller
@RequestMapping("/api/vendor")
public class VendorApiController {

    private static Logger log = LoggerFactory.getLogger(VendorApiController.class);

    @Autowired
    private VendorService service = null;
    @Autowired
    private AdrobeService adrobeService = null;
    @Autowired
    private MessagingService messagingService = null;

    @RequestMapping(value = {"/login"}, method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    ApiResponse processlogin(@RequestParam("username") String username, @RequestParam("password") String password) {
        log.debug("userName:" + username + " password:" + password);
        Vendor vendor = null;
        String status = "failed";
        if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
            try {
                vendor = service.vendorLogin(username, password);
            } catch (Exception e) {
                log.error("Error while validating vendor login ", e);
            }
        }

        if (vendor != null) {
            if (vendor.getPassword().equals(password) && vendor.getStatus() == 1) {
                status = "success";
            } else {
                vendor = null;
            }
        }

        ApiResponse res = new ApiResponse(status, vendor);

        return res;
    }

    @RequestMapping(value = "/offers", produces = "application/json")
    public @ResponseBody
    List<Offers> listOffers(@RequestParam("vendorId") Integer vendorId) {
        log.debug("vendor : " + vendorId);
        SearchObj obj = service.listVendorOffers(vendorId, 1, -1, null, 0);
        List<Offers> offers = (List<Offers>) obj.data;
        log.debug("offers : {}", offers);
        return offers;
    }

    @RequestMapping(value = {"/saveOffer"}, method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    Offers saveOffer(Offers offer, BindingResult result) {
        log.debug("inside saveOffer : ");

        Offers savedOffer = null;
        Vendor vendor = null;
        try {
            vendor = (Vendor) adrobeService.getById(offer.getVendorID(), Vendor.class);
            savedOffer = service.saveOffer(vendor, offer);
        } catch (Exception ex) {
            log.error("Error while saving offer", ex);
            savedOffer = new Offers();
        }
        savedOffer.setUploadImage(null);
        return savedOffer;
    }

    @RequestMapping(value = {"/deleteOffer"}, produces = "application/json")
    public @ResponseBody
    String deleteOffer(@RequestParam("vendorId") int vendorId, @RequestParam("offerID") int offerID) {
        log.debug("inside deleteOffer : " + offerID);
        try {
            service.deleteOffer(vendorId, offerID);
        } catch (Exception e) {
            log.error("Error while deleting offer", e);
            return "{\"error\":\"error deleting offer\"}";
        }
        return "{\"offerID\":" + offerID + "}";
    }

    @RequestMapping(value = "/profile", produces = "application/json")
    public @ResponseBody
    Vendor viewProfile(@RequestParam("vendorId") int vendorId) {
        Vendor vendor = (Vendor) adrobeService.getById(vendorId, Vendor.class);
        return vendor;
    }

    @RequestMapping(value = "/updateProfile", produces = "application/json")
    public @ResponseBody
    ApiResponse updateProfile(@RequestParam("vendorId") int vendorId, @RequestParam("password") String password) {

        Vendor vendor = (Vendor) adrobeService.getById(vendorId, Vendor.class);
        vendor.setPassword(password);
        service.updateVendorProfile(vendor);

        return new ApiResponse("success");
    }

    @RequestMapping(value = "/uploadCoverImage", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    String uploadCoverImage(@RequestParam("vendorId") int vendorId, @RequestParam("coverFile") MultipartFile file) {
        Vendor vendor = (Vendor) adrobeService.getById(vendorId, Vendor.class);
        try {
            service.uploadCoverImage(vendor, file);
        } catch (Exception ex) {
            log.error("error while updating cover image", ex);
            return "{}";
        }

        return "{\"url\":\"" + vendor.getCoverImage() + "\"}";
    }

    @RequestMapping(value = "/configuration", produces = "application/json")
    public @ResponseBody
    Map configuration() {
        Map map = SettingsUtil.getConfigMap();
        return map;
    }

    @RequestMapping(value = "/userRequests", produces = "application/json")
    public @ResponseBody
    List<Object[]> listUserRequests(@RequestParam("vendorId") Integer vendorId, @RequestParam(value = "page", required = false, defaultValue = "0") int page, @RequestParam(value = "limit", required = false, defaultValue = "-1") int limit, @RequestParam(value = "status", required = false, defaultValue = "0") int status) {
        log.debug("vendor : " + vendorId);
        SearchObj obj = service.listVendorRequests(vendorId, page, limit, status);
        log.debug("offers : {}", obj.data);
        List<Object[]> list = (List<Object[]>) obj.data;
        return list;
    }

    @RequestMapping(value = "/orders", produces = "application/json")
    public @ResponseBody
    List<Object[]> listVendorOrders(@RequestParam("vendorId") Integer vendorId, @RequestParam(value = "page", required = false, defaultValue = "1") int page, @RequestParam(value = "limit", required = false, defaultValue = "10") int limit) {
        log.debug("vendor : " + vendorId);

        //List<Object[]> list = service.listVendorOrders(vendorId,page,limit);
        SearchObj obj = service.listVendorOrders(vendorId, page, limit, 0, 0);
        List<Object[]> list = (List<Object[]>) obj.data;
        return list;
    }

    @RequestMapping(value = "image/{imgName}")
    public @ResponseBody
    byte[] getImage(@PathVariable String imgName) {
        String bucketName = SettingsUtil.getSettings("S3_BUCKET");
        AdrobeS3DAOImpl s3Service = new AdrobeS3DAOImpl();
        if (!imgName.endsWith(".jpg")) {
            imgName += ".jpg";
        }
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

    @RequestMapping("/getProductDetails")
    public @ResponseBody
    String getProductDetails(@QueryParam("mobilenumber") String mobileNumber, @QueryParam("vendorID") Integer vendorID) {
        JSONArray productJSONArray = new JSONArray();
        try {
            List<ProductCoupon> list = service.getProductCouponsForCustomerByVendor(mobileNumber, vendorID);
            for (ProductCoupon pc : list) {
                JSONObject obj = new JSONObject();
                obj.put("productname", pc.getProductName());
                obj.put("quantity", pc.getQuantity());
                obj.put("couponcode", pc.getProductCouponCode());
                productJSONArray.put(obj);
            }
        } catch (Exception ex) {

        }
        return productJSONArray.toString();
    }

    @RequestMapping("/validateCoupon")
    public @ResponseBody
    boolean isCouponValid(@QueryParam("vendorID") int vendorId, @QueryParam("couponcode") String productCouponCode) {
        try {
            return service.validateProductCoupon(vendorId, productCouponCode);
        } catch (Exception ex) {
            log.error("Exception validating product coupon ::" + ex.getMessage());
        }
        return false;
    }
    
    @RequestMapping("/verifyCoupon")
    public @ResponseBody
    String verifyCoupon(@QueryParam("vendorID") Integer vendorId, @QueryParam("mobilenumber") String mobileNumber, @QueryParam("couponcode") String couponCode) {
        String res= "{\"error\":\"invalid coupon\"}";
        
        try {
            if (service.validateProductCoupon(vendorId, couponCode)){
                String pin = PinUtil.generatePIN();
                Customers customer = adrobeService.gtCustomerByTelephone(mobileNumber);
                messagingService.sendCouponVerificationPIN(customer, pin);
                res = "{\"pin\":\""+ pin +"\"}";
            }
        } catch (Exception ex) {
            log.error("Error validating product coupon :: " + ex.getMessage());
            res = "{\"error\":\""+ ex.getMessage() + "\"}";
        }
        
        return res;
    }
}
