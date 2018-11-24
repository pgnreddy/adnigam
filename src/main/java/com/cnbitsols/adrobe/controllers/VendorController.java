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
import com.cnbitsols.adrobe.entities.Vendor;
import com.cnbitsols.adrobe.services.VendorService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author santosh.r
 */
@Controller
@RequestMapping("/vendor")
@SessionAttributes("user")
public class VendorController {

    private static Logger log = LoggerFactory.getLogger(VendorController.class);

    @Autowired
    private VendorService service = null;

    @Autowired
    private MessageSource messageSource = null;

    @RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
    public String login() {
        return "/vendor/login";
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.POST)
    public @ResponseBody
    String processlogin(Model model, @RequestParam("username") String username, @RequestParam("password") String password) {
        log.debug("userName:" + username + " password:" + password);
        Vendor vendor = null;

        if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
            try {
                vendor = service.vendorLogin(username, password);
            } catch (Exception e) {
                log.error("Error while validating vendor login ", e);
            }
        }

        if (vendor != null) {
            if (vendor.getPassword().equals(password)) {
                if (vendor.getStatus() == 1) {
                    model.addAttribute("user", vendor);
                    return "success";
                }
            }
        }

        return "failed";
    }

    /*@RequestMapping("/offers")    
    public String listOffers(@ModelAttribute("user") Vendor vendor,Model model){
        log.debug("vendor : "+vendor); 
        List<Offers> offers = service.listVendorOffers(vendor.getVendorID());
        log.debug("offers : {}",offers);
        
        ObjectMapper mapper = new ObjectMapper();
        String offersStr = null;
        try {
            offersStr = mapper.writeValueAsString(offers);
        } catch (IOException ex) {
         log.error("Exception while converting offers to json",ex);
            offersStr = "[]";
        }
        model.addAttribute("offers", offersStr);
        return "vendor.offers";
    }*/
    @RequestMapping("/offers")
    public String listOffers(@ModelAttribute("user") Vendor vendor, Model model, @RequestParam(value = "page", required = false, defaultValue = "1") int page, @RequestParam(value = "limit", required = false, defaultValue = "10") int limit, @RequestParam(value = "productName", required = false) String productName, @RequestParam(value = "offerID", required = false, defaultValue = "0") int offerID) {

        SearchObj obj = service.listVendorOffers(vendor.getVendorID(), page, limit, productName, offerID);
        model.addAttribute("searchObj", obj);
        return "vendor.offers";
    }

    @RequestMapping("/productCoupons")
    public String listProductCoupons(@ModelAttribute("user") Vendor vendor, Model model, @RequestParam(value = "page", required = false, defaultValue = "1") int page, @RequestParam(value = "limit", required = false, defaultValue = "10") int limit, @RequestParam(value = "productCouponCode", required = false) String productCouponCode, @RequestParam(value = "orderID", required = false, defaultValue = "0") int orderID) {
        SearchObj obj;
        obj = service.listProductCoupons(vendor.getVendorID(), page, limit, orderID, productCouponCode);
        model.addAttribute("searchObj", obj);
        return "vendor.productCoupons";
    }

    @RequestMapping("/sellProductCoupon")
    public @ResponseBody
    ApiResponse sellProductCoupon(@ModelAttribute("user") Vendor vendor, Model model, @RequestParam(value = "type", required = false, defaultValue = "") String type, @RequestParam(value = "productCouponCode", required = false) String productCouponCode) {
        if (type.equals("edit")) {
            try {
                service.sellProductCoupon(vendor, productCouponCode);
            } catch (Exception ex) {
                return new ApiResponse("error");
            }
        }
        return new ApiResponse("success");
    }

    @RequestMapping("/offerForm")
    public String offerForm(@ModelAttribute("user") Vendor vendor, Model model, @RequestParam(value = "type") String type, @RequestParam(value = "offerID", required = false, defaultValue = "0") int offerID) {

        if (!"add".equals(type)) {
            Offers offer = service.getVendorOffer(vendor.getVendorID(), offerID);
            model.addAttribute("offer", offer);
        }
        //SearchObj obj = service.listVendorOffers(vendor.getVendorID(),page,limit,productName,offerID);       

        return "/vendor/offerForm";
    }

    @RequestMapping(value = {"/saveOffer"}, method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    Offers  saveOffer(@ModelAttribute("user") Vendor vendor, Offers offer, BindingResult result) {
        log.debug("inside saveOffer : " + vendor.toString()+":::OFFER TITLE:::"+offer.getTitle());
     
        Offers savedOffer = null;

        try {
            String colorsdb = "";
//            String[] clrs = offer.getColorstemp();
//            for (int i = 0; i < clrs.length; i++) {
//                if (i == 0) {
//                    colorsdb = colorsdb + "" + clrs[i];
//                } else {
//                    colorsdb = colorsdb + "|" + clrs[i];
//                }
//            }
//            offer.setColors(colorsdb);
            savedOffer = service.saveOffer(vendor, offer);
        } catch (Exception ex) {
            log.error("Error while saving offer", ex);
            savedOffer = new Offers();
        }
        System.out.println("com.cnbitsols.adrobe.controllers.VendorController.saveOffer()"+savedOffer.toString());
        return savedOffer;
    }

    @RequestMapping(value = {"/deleteOffer"})
    public @ResponseBody
    String deleteOffer(@ModelAttribute("user") Vendor vendor, @RequestParam("offerID") int offerID) {
        log.debug("inside deleteOffer : " + offerID);
        try {
            service.deleteOffer(vendor.getVendorID(), offerID);
        } catch (Exception e) {
            log.error("Error while deleting offer", e);
            return "{\"error\":\"error deleting offer\"}";
        }
        return "{\"offerID\":" + offerID + "}";
    }

    @RequestMapping(value = {"/logout"})
    public String logout(HttpSession session, SessionStatus status) {
        log.debug("logout");
        status.setComplete();
        session.invalidate();
        return "redirect:/vendor/login";
    }

    @RequestMapping("/profile")
    public String viewProfile(@ModelAttribute("user") Vendor vendor) {

        return "vendor.profile";
    }

    @RequestMapping("/updateProfile")
    public @ResponseBody
    String viewProfile(@ModelAttribute("user") Vendor vendor, @RequestParam("password") String password) {

        vendor.setPassword(password);
        service.updateVendorProfile(vendor);

        return "success";
    }

    @RequestMapping(value = "/uploadCoverImage", method = RequestMethod.POST)
    public @ResponseBody
    String uploadCoverImage(@ModelAttribute("user") Vendor vendor, @RequestParam("coverFile") MultipartFile file, BindingResult result) {

        try {
            service.uploadCoverImage(vendor, file);
        } catch (Exception ex) {
            log.error("error while updating cover image", ex);
            return "{}";
        }

        return "{\"url\":\"" + vendor.getCoverImage() + "\"}";
    }

    @RequestMapping(value = "/uploadVendorPromo", method = RequestMethod.POST)
    public @ResponseBody
    String uploadVendorPromo(@ModelAttribute("user") Vendor vendor, @RequestParam("vendorPromo") MultipartFile file, BindingResult result) {
        try {
            service.uploadVendorPromo(vendor, file);
        } catch (Exception ex) {
            log.error("error while uploading vendor promo");
            return "";
        }

        return "{\"status\":\"success\"}";
    }

    @RequestMapping(value = {"/setIsHot"})
    public @ResponseBody
    ApiResponse setIsHot(@ModelAttribute("user") Vendor vendor, @RequestParam("offerID") int offerID) {
        log.debug("inside setIsHot : " + offerID);
        try {
            service.setIsHot(vendor.getVendorID(), offerID);
        } catch (Exception e) {
            log.error("Error while deleting offer", e);
            return new ApiResponse("error");
        }
        return new ApiResponse("success");
    }

    @RequestMapping("/requests")
    public String listUserRequests(@ModelAttribute("user") Vendor vendor, Model model, @RequestParam(value = "page", required = false, defaultValue = "1") int page, @RequestParam(value = "limit", required = false, defaultValue = "10") int limit, @RequestParam(value = "status", required = false, defaultValue = "0") int status) {
        log.debug("vendor : " + vendor);
        SearchObj obj = service.listVendorRequests(vendor.getVendorID(), page, limit, status);

        model.addAttribute("searchObj", obj);
        return "vendor.userRequests";
    }

    @RequestMapping("/updateUserRequest")
    public @ResponseBody
    ApiResponse updateUserRequest(@ModelAttribute("user") Vendor vendor, Model model, @RequestParam(value = "uid") int requestId) {
        int status = 1;
        boolean success = service.updateUserRequest(vendor.getVendorID(), requestId, status);

        if (success) {
            return new ApiResponse("success");
        } else {
            return new ApiResponse("error");
        }

    }

    @RequestMapping("/orders")
    public String listOrders(@ModelAttribute("user") Vendor vendor, Model model, @RequestParam(value = "page", required = false, defaultValue = "1") int page, @RequestParam(value = "limit", required = false, defaultValue = "10") int limit, @RequestParam(value = "status", required = false, defaultValue = "-1") int status, @RequestParam(value = "orderId", required = false, defaultValue = "0") int orderId) {
        //List<Object[]> list = service.listVendorOrders(vendor.getVendorID(),page,limit);

        SearchObj obj = service.listVendorOrders(vendor.getVendorID(), page, limit, status, orderId);

        model.addAttribute("searchObj", obj);

        //model.addAttribute("ordersList", list);
        return "vendor.orders";
    }

    @RequestMapping(value = "/forgotPassword")
    public @ResponseBody
    ApiResponse forgotPassword(Model model, @RequestParam(value = "mobile", required = false) String mobile) {

        String message = null;
        Vendor vendor = null;
        if (StringUtils.isBlank(mobile)) {
            message = messageSource.getMessage("invalid.mobile", null, Locale.getDefault());
        }

        if (message == null) {
            vendor = service.getVendorByMobile(mobile);
            if (vendor == null) {
                message = messageSource.getMessage("vendorNotRegistered", null, Locale.getDefault());
            }
        }

        if (message == null) { //valid customer
            boolean sent = service.sendForgotPassword(vendor);
            if (sent) {
                message = messageSource.getMessage("forgotPassword.mobile.sent.success", null, Locale.getDefault());
            } else {
                message = messageSource.getMessage("forgotPassword.sent.failed", null, Locale.getDefault());
            }
        }

        log.debug("message : " + message);

        return new ApiResponse("success", message);
    }

    @RequestMapping("/updateOrderProductStatus")
    public @ResponseBody
    ApiResponse updateOrderProductStatus(@ModelAttribute("user") Vendor vendor, @RequestParam(value = "status") int status, @RequestParam(value = "orderId") int orderProductId, @RequestParam(value = "comment", required = false) String comment) {
        service.updateOrderProductStatus(vendor.getVendorID(), orderProductId, status, comment);
        return new ApiResponse("success");
    }

    @RequestMapping("/message")
    public String getMessagePage(@ModelAttribute("user") Vendor vendor) {

        return "vendor.message";
    }

    @RequestMapping("/sendMessage")
    public @ResponseBody
    ApiResponse sendMessage(@ModelAttribute("user") Vendor vendor, @RequestParam(value = "message") String message) {
        boolean success = service.sendVendorMessage(vendor, message);
        return new ApiResponse("success");
    }

}
