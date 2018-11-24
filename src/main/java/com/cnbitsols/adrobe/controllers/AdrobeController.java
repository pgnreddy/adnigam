/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cnbitsols.adrobe.controllers;

import com.cnbitsols.adrobe.entities.*;
import com.cnbitsols.adrobe.common.*;
import com.cnbitsols.adrobe.dto.ApiResponse;
import com.cnbitsols.adrobe.entities.Offers;
import com.cnbitsols.adrobe.entities.Vendor;
import com.cnbitsols.adrobe.services.AdrobeService;
import com.cnbitsols.adrobe.utils.RequestUtil;
import com.cnbitsols.adrobe.utils.SettingsUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.print.attribute.standard.MediaSize;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author santosh.r
 */
@Controller
public class AdrobeController {

    private static Logger log = LoggerFactory.getLogger(AdrobeController.class);
    @Autowired
    private AdrobeService service = null;

    @Autowired
    private MessageSource messageSource = null;

    @ModelAttribute()
    public void addHeaderData(Model model) {
        model.addAttribute("categories", service.listMenuCategories());
        model.addAttribute("allCategories", service.listCategories());
        model.addAttribute("locations", service.listLocations());
        /* quick view Defult model attribute defination 
         to acheve proper  handling it is Declared in /pd/{product}
        */
         model.addAttribute("quickProduct",null);
    }

    @RequestMapping(value = {"/updateLocation"}, method = RequestMethod.GET)
    public String updateLocation(@CookieValue(value = "loc", required = false, defaultValue = "-1") int locationId, @RequestParam(value = "loc", required = false, defaultValue = "-2") int selLocationId, HttpServletResponse response, HttpServletRequest request) {

        if (selLocationId != -2) {
            locationId = selLocationId;            
        } else {
            Location defaultLocation = service.getDefaultLocation();
            locationId = defaultLocation.getId();
        }
        
        Cookie cookie = new Cookie("loc", locationId + "");
        cookie.setMaxAge(365 * 2 * 24 * 60 * 60); //2 years cookie expiry time
        cookie.setPath("/");
        response.addCookie(cookie);

        return "redirect:/";
    }

    @RequestMapping(value = {"", "/", "index"}, method = RequestMethod.GET)
    public String index(HttpServletRequest request, Model model, @CookieValue(value = "loc", required = false, defaultValue = "-1") int locationId, @RequestParam(value = "loc", required = false, defaultValue = "-1") int selLocationId, @RequestParam(value = "adRef", required = false) String adRef, HttpServletResponse response) {

        if (StringUtils.isNotEmpty(adRef)) {
            service.incrementRefCount(adRef);
        }

        if (selLocationId > 0) {
            locationId = selLocationId;
            Cookie cookie = new Cookie("loc", selLocationId + "");
            cookie.setMaxAge(365 * 2 * 24 * 60 * 60); //2 years cookie expiry time
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
        }

        if (request.getSession().getAttribute("locationVerified") == null) {
            model.addAttribute("showLocationPopUp", true);
            request.getSession().setAttribute("locationVerified", true);
        }
   
        model.addAttribute("premiumOffers", service.listPremiumOffers(locationId));
        /*Map<Integer, Long> categoryOffersCount = service.categoryOffersCount(locationId);
        model.addAttribute("categoryOffersCount", categoryOffersCount);
        
        String categoryOffersCountJSON = getcategoryOffersCountJSON(categoryOffersCount);
        model.addAttribute("categoryOffersCountJSON", categoryOffersCountJSON);
         */

        model.addAttribute("categoriesJson", service.getCategoriesJson());
        if (locationId > 0) {
            model.addAttribute("topOffers", service.getTodaysOffers(locationId, 4));
        } else {
            model.addAttribute("topOffers", service.getTodaysOffers(0, 4));//locationid set to 0,as it is not used,need for caching
        }
        model.addAttribute("exclusiveOffers", service.getExclusiveOffers(locationId));
        return "adrobe.index";
    }

    @RequestMapping(value = {"/products/{category}"})
    public @ResponseBody
    String listProductsJSON(@PathVariable("category") String category, @RequestParam(value = "page", required = false, defaultValue = "0") int page, @RequestParam(value = "limit", required = false, defaultValue = "-1") int limit, @RequestParam(value = "c", required = false) String c, @CookieValue(value = "loc", required = false, defaultValue = "-1") int locationId, @RequestParam(value = "search", required = false) String searchTerm, @RequestParam(value = "prRange", required = false) String priceRange, @RequestParam(value = "dsRange", required = false) String discountRange) {
        if (limit == -1 || limit > 30) {
            if (StringUtils.isBlank(c)) {
                limit = NumberUtils.toInt(SettingsUtil.getSettings("HOME_PAGE_PRODUCTS_COUNT"), 12);
            } else {
                limit = NumberUtils.toInt(SettingsUtil.getSettings("CATEGORIES_PRODUCTS_COUNT"), 12);
            }
        }

        List offersList = null;
        if (StringUtils.isNotBlank(searchTerm)) {//search 
            offersList = service.searchProducts(category, page, limit, searchTerm);
        } else if (StringUtils.isNotBlank(priceRange) || StringUtils.isNotBlank(discountRange)) {//search 
            offersList = service.searchProductsByPrice(category, page, limit, priceRange, discountRange);
        } else {
            offersList = service.listProducts(category, locationId, page, limit, searchTerm);
        }

        ObjectMapper mapper = new ObjectMapper();
        String offersStr = null;
        try {
            offersStr = mapper.writeValueAsString(offersList);
        } catch (IOException ex) {
            log.error("Exception while converting offers to json", ex);
            offersStr = "[]";
        }
        return offersStr;
    }

    @RequestMapping(value = {"/ps/{category}"})
    public String listProductsForCategory(Model model, @PathVariable("category") String category, @CookieValue(value = "loc", required = false, defaultValue = "-1") int locationId, @RequestParam(value = "search", required = false) String searchTerm, @RequestParam(value = "prRange", required = false) String priceRange, @RequestParam(value = "dsRange", required = false) String discountRange) {

        int limit = NumberUtils.toInt(SettingsUtil.getSettings("CATEGORIES_PRODUCTS_COUNT"), 12);
        int page = 0;

        List offersList = null;
        if ("todayOffers".equals(category)) {
            offersList = service.getTodaysOffers(locationId, -1);
            model.addAttribute("showAddtoCart", true);
        } else if (StringUtils.isNotBlank(searchTerm)) {//search 
            offersList = service.searchProducts(category, page, limit, searchTerm);
        } else if (StringUtils.isNotBlank(priceRange) || StringUtils.isNotBlank(discountRange)) {//search 
            offersList = service.searchProductsByPrice(category, page, limit, priceRange, discountRange);
        } else {
            offersList = service.listProducts(category, locationId, page, limit, searchTerm);
        }
        Categories categories = null;
        if (!"todayOffers".equals(category) && !"all".equals(category)) {
            categories = service.getCategoryBySeoName(category);
        }

        model.addAttribute("categoryName", category);

        model.addAttribute("productsList", offersList);
        model.addAttribute("category", categories);
        return "adrobe.listProductsInCategory";
    }

    @RequestMapping(value = {"/p/{vendor}/{product}"})
    public String listProductsForVendor(Model model, @PathVariable("vendor") int vendorId, @PathVariable("product") String productName) {

        Vendor vendor = service.getVendor(vendorId);
        List<Offers> offersList = service.listVendorProducts(vendorId);

        model.addAttribute("productsList", offersList);
        model.addAttribute("vendor", vendor);
        model.addAttribute("product",null);
        return "adrobe.listVendorProducts";
    }

    @RequestMapping(value = {"/pd/{product}"})
    public String productDetails(Model model, @PathVariable("product") String productName,
            @RequestParam(value="quick",defaultValue = "false",required =false) boolean quick) {

        Offers product = service.getProduct(productName);
        Vendor vendor = null;
        if (product != null) {
            vendor = service.getVendor(product.getVendorID());
            List<Offers> offersList = service.listVendorProducts(product.getVendorID());
            if(null!=offersList){
                model.addAttribute("productsList", offersList);
            }}
        model.addAttribute("product", product);
        model.addAttribute("vendor", vendor);
        if(quick){
            /**
             Quick view Declaration for and it is used in footer page quickView.jsp
             **/
         model.addAttribute("quickProduct",product);
            return "/adrobe/quickView";
         }
        return "adrobe.productDetails";
    }
    @RequestMapping(value="login",method=RequestMethod.GET)
    public String userLogin(HttpServletRequest request){
        Object user=request.getSession().getAttribute(StoreConstants.USER);
     if(user!=null){
     return "redirect:/";
     }
    return "adrobe.login";
    }
    @RequestMapping(value = "login" ,method=RequestMethod.POST)
    public String login(Model model, HttpServletRequest request, @RequestParam(value = "userName", required = false) String userName, @RequestParam(value = "password", required = false) String password) {

        String message = null;
        Customers customer = null;
        if (StringUtils.isBlank(userName) || StringUtils.isBlank(password)) {
            message = messageSource.getMessage("invalidDetails", null, Locale.getDefault());
        }

        if (message == null) {
            customer = service.getCustomer(userName);
            if (customer == null) {
                message = messageSource.getMessage("emailNotRegistered", null, Locale.getDefault());
            } else if (!password.equals(customer.getPassword())) {
                message = messageSource.getMessage("wrongCredentials", null, Locale.getDefault());
            }
        }

        if (message == null) { //valid customer
            request.getSession().setAttribute(StoreConstants.USER, customer);
            return "redirect:/";
        } else {
            model.addAttribute("message", message);
        }

        log.debug("message : " + message);
        return "adrobe.login.response";
    }
    
    @RequestMapping(value="login_api",method=RequestMethod.GET,produces = "application/json")
    @ResponseBody
     public ApiResponse login_api(HttpServletRequest request, @RequestParam(value = "userName", required = false) String userName, @RequestParam(value = "password", required = false) String password) {

      ApiResponse res=new ApiResponse();
        Customers customer = null;
        if (StringUtils.isBlank(userName) || StringUtils.isBlank(password)) {
            res.setMsg(StoreConstants.ApiErrorCodes.INVALID_CREDENTIALS.getDesc());
            res.setStatus(StoreConstants.ApiErrorCodes.INVALID_CREDENTIALS.getStatus());
            return res;
          //  message = messageSource.getMessage("invalidDetails", null, Locale.getDefault());
           }

    
            customer = service.getCustomer(userName);
            if (customer == null) {
            res.setMsg(StoreConstants.ApiErrorCodes.INVALID_CREDENTIALS.getDesc());
            res.setStatus(StoreConstants.ApiErrorCodes.INVALID_CREDENTIALS.getStatus());
            return res;
             //   message = messageSource.getMessage("emailNotRegistered", null, Locale.getDefault());
            } else if (!password.equals(customer.getPassword())) {
              //  message = messageSource.getMessage("wrongCredentials", null, Locale.getDefault());
               res.setMsg(StoreConstants.ApiErrorCodes.INVALID_CREDENTIALS.getDesc());
            res.setStatus(StoreConstants.ApiErrorCodes.INVALID_CREDENTIALS.getStatus());
            return res;
            }else{
             request.getSession().setAttribute(StoreConstants.USER, customer);
             
             res.setMsg(StoreConstants.ApiErrorCodes.SUCCESS.getDesc());
            res.setStatus(StoreConstants.ApiErrorCodes.SUCCESS.getStatus());
            return res;
            }
      

     }

    @RequestMapping(value = "forgotPassword",method=RequestMethod.POST)
    @ResponseBody
    public String forgotPassword(Model model, @RequestParam(value = "email", required = false) String email) {

        String message = null;
        Customers customer = null;
        if (StringUtils.isBlank(email)) {
            message = messageSource.getMessage("invalid.email", null, Locale.getDefault());
        }

        if (message == null) {
            customer = service.getCustomer(email);
            if (customer == null) {
                message = messageSource.getMessage("emailNotRegistered", null, Locale.getDefault());
            }
        }

        if (message == null) { //valid customer
            boolean sent = service.sendForgotPassword(customer);
            if (sent) {
                message = messageSource.getMessage("forgotPassword.sent.success", null, Locale.getDefault());
            } else {
                message = messageSource.getMessage("forgotPassword.sent.failed", null, Locale.getDefault());
            }
        }

        model.addAttribute("message", message);

        log.debug("message : " + message);
        return message;
    }
 @RequestMapping(value="signup",method=RequestMethod.GET)
 public String signup(HttpServletRequest request,HttpSession session){
     Object user=session.getAttribute(StoreConstants.USER);
     if(user!=null){
     return "redirect:/";
     }
 return "adrobe.signup";
 }
    @RequestMapping(value = "signup",method=RequestMethod.POST)
    public String signup(Model model, HttpServletRequest request, Customers customer, BindingResult result) {
        String message = null;

        String email = customer.getEmailAddress();
        if (StringUtils.isBlank(email)) {
            message = messageSource.getMessage("invalid.email", null, Locale.getDefault());
        }

        if (message == null) {
            Customers existCustomer = service.getCustomer(email);
            if (existCustomer != null) {
                message = messageSource.getMessage("account.exists", null, Locale.getDefault());
            }
        }

        if (message == null) { //valid customer
            boolean added = service.addNewCustomer(customer);
            if (!added) {
                message = messageSource.getMessage("request.failed ", null, Locale.getDefault());
            } else {
                request.getSession().setAttribute(StoreConstants.USER, customer);
//                message=messageSource.getMessage("adrobe.sucess",  null, Locale.getDefault());
                return "redirect:/";
            }
        }

        if (message != null) {
            model.addAttribute("message", message);
        }

        log.debug("message : " + message);

        return "adrobe.signup";
    }

    @RequestMapping(value = "logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

    @RequestMapping(value = {"/static/{page}"})
    public String staticpages(@PathVariable("page") String page) {
        return "adrobe.static." + page;
    }

    @RequestMapping(value = {"/removeCache"})
    @CacheEvict(allEntries = true, value = {"cache15Min", "cache10Min", "cache5Min", "cache60Min"})
    public @ResponseBody
    String removeCaches() {
        service.removeCaches();
        return "Success";
    }

    @Cacheable(value = "cache5Min")
    private String getcategoryOffersCountJSON(Map<Integer, Long> categoryOffersCount) {
        ObjectMapper mapper = new ObjectMapper();
        String categoriesStr = null;
        try {
            categoriesStr = mapper.writeValueAsString(categoryOffersCount);
        } catch (IOException ex) {
            log.error("Exception while converting offers to json", ex);
            categoriesStr = "[]";
        }

        return categoriesStr;
    }

    @RequestMapping(value = {"/addFavourite"})
    public @ResponseBody
    ApiResponse addCustomerFavourite(HttpServletRequest request)
            throws Exception {

        boolean added = false;
        try {
            Customers customer = RequestUtil.getCurrentCustomer(request);
            if (customer != null) {
                int productId = NumberUtils.toInt(request.getParameter("productId"));
                if (productId > 0) {
                    added = service.addCustomerFavourite(customer.getCustomersId(), productId);
                }

            }
        } catch (Exception e) {

        }

        String resultType = "failure";
        if (added) {
            resultType = "success";
        }

        return new ApiResponse(resultType, "addCustomerFavourite");

    }

    @RequestMapping(value = {"/deleteFavourite"})
    public @ResponseBody
    ApiResponse deleteCustomerFavourite(HttpServletRequest request)
            throws Exception {

        boolean deleted = false;
        try {
            Customers customer = RequestUtil.getCurrentCustomer(request);
            if (customer != null) {
                int productId = NumberUtils.toInt(request.getParameter("productId"));
                if (productId > 0) {
                    deleted = service.deleteFavouriteProducts(customer.getCustomersId(), productId);
                }

            }
        } catch (Exception e) {
        }

        String resultType = "failure";
        if (deleted) {
            resultType = "success";
        }
        request.setAttribute("resultType", resultType);
        return new ApiResponse(resultType, "deleteCustomerFavourite");
    }

    @RequestMapping(value = {"/sendVendorRequest"})
    public String sendVendorRequest(HttpServletRequest request, Model model)
            throws Exception {

        Enumeration e = request.getParameterNames();
        Map<String, String> requestMap = new HashMap<String, String>();
        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
            String value = request.getParameter(key);
            requestMap.put(key, value);
        }

        boolean success = service.sendVendorRequest(requestMap);
        model.addAttribute("success", success);
        return "redirect:/static/sellwithus";
    }

    @RequestMapping(value = {"/addReview"})
    public @ResponseBody
    ApiResponse addCustomerReview(HttpServletRequest request)
            throws Exception {

        boolean added = false;
        try {
            Customers customer = RequestUtil.getCurrentCustomer(request);
            if (customer != null) {
                int productId = NumberUtils.toInt(request.getParameter("productId"));
                int rating = NumberUtils.toInt(request.getParameter("rating"));
                String description = request.getParameter("description");
                if (productId > 0) {
                    added = service.addCustomerReview(customer, productId, rating, description);
                }

            }
        } catch (Exception e) {

        }

        String resultType = "failure";
        if (added) {
            resultType = "success";
        }

        return new ApiResponse(resultType, "addCustomerReview");

    }

    @RequestMapping(value = {"/listReviews"})
    public @ResponseBody
    List<Reviews> listProductReviews(HttpServletRequest request, Model model)
            throws Exception {

        int productId = NumberUtils.toInt(request.getParameter("productId"));

        List<Reviews> reviewsList = Collections.EMPTY_LIST;
        if (productId > 0) {
            reviewsList = service.getReviews(productId);
            model.addAttribute("reviewsList", reviewsList);
        }

        return reviewsList;
    }
    
    @RequestMapping(value = "sociallogin")
    public @ResponseBody
    String sociallogin(Model model, HttpServletRequest request,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "lastname", required = false) String lastname,
            @RequestParam(value = "soocialsite", required = false) String sitename,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "accountkey", required = false) String accountkey) {
        {
            if (service.isSocialUserExist(accountkey, sitename, email)) {
                Customers cmrs = service.getScoialUser(accountkey, sitename, email);
                //sign in
                // service.doSocialLogin(cmrs);
                request.getSession().setAttribute(StoreConstants.USER, cmrs);

            } else {
                Customers cmrs = new Customers();
                cmrs.setEmailAddress(email);
                cmrs.setFirstname(name);
                cmrs.setPassword("adnigam12345");
                cmrs.setTelephone("  0");
                cmrs.setLastname(lastname);
                cmrs.setSocialsite(sitename);
                cmrs.setAccountId(accountkey);

                service.save(cmrs);
                request.getSession().setAttribute(StoreConstants.USER, cmrs);

            }
            log.error("Working");
        }
               return "{\"respmsg\":\"success\"}";

    }


}
