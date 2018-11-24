/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cnbitsols.adrobe.controllers;

import com.cnbitsols.adrobe.dto.EditKeywordsWrapper;
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
import com.cnbitsols.adrobe.services.AdrobeService;
import com.cnbitsols.adrobe.services.MessagingService;
import com.cnbitsols.adrobe.utils.RequestUtil;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

/**
 *
 * @author santosh.r
 */
@Controller
@RequestMapping("/admin")
@SessionAttributes("user")
public class AdminController {

    private static Logger log = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private AdminService service = null;
    @Autowired
    private AdrobeService adrobeService = null;

    @Autowired
    private MessagingService messagingService = null;

    @RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
    public String login() {
        return "/admin/login";
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.POST)
    public @ResponseBody
    String processlogin(Model model, @RequestParam("username") String username, @RequestParam("password") String password) {
        log.debug("userName:" + username + " password:" + password);
        Admin admin = null;

        if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
            try {
                admin = service.findAdminByEmail(username);
            } catch (Exception e) {
                log.error("Error while validating admin login ", e);
            }
        }
        if (admin != null) {
            if (admin.getPassword().equals(password)) {
                model.addAttribute("user", admin);
                return "success";
            }
        }

        return "failed";
    }

    @RequestMapping("/orders")
    public String ordersMain(@ModelAttribute("user") Admin admin, Model model) {
        log.debug("admin : " + admin + "param:");
        model.addAttribute("orderStatCodes", adrobeService.getOrderStatusCodes());
        model.addAttribute("category", adrobeService.listCategories());
        return "admin.orders";
    }
//

    @RequestMapping("/vendorlist")
    public String vendorsList(@ModelAttribute("user") Admin admin, Model model) {
        log.debug("admin : " + admin);
        model.addAttribute("locations", adrobeService.listLocations());
        model.addAttribute("category", adrobeService.listCategories());
        return "admin.vendors";
    }

    @RequestMapping("/premiumoffers")
    public String premiumoffersMenu(@ModelAttribute("user") Admin admin, Model model) {
        log.debug("admin : " + admin);
        model.addAttribute("category", adrobeService.listCategories());
        model.addAttribute("locations", adrobeService.listLocations());
        model.addAttribute("vendors", service.listVendors(0, 50, "", "", "", ""));
        return "admin.offersPremium";
    }

    @RequestMapping("/exclusiveoffers")
    public String exclusiveoffersMenu(@ModelAttribute("user") Admin admin, Model model) {
        log.debug("admin : " + admin);
        model.addAttribute("category", adrobeService.listCategories());
        model.addAttribute("locations", adrobeService.listLocations());
        model.addAttribute("vendors", service.listVendors(0, 50, "", "", "", ""));
        return "admin.offersExclusive";
    }

    @RequestMapping("/todayOffers")
    public String todayOffersMenu(@ModelAttribute("user") Admin admin, Model model) {
        log.debug("admin : " + admin);
        model.addAttribute("category", adrobeService.listCategories());
        model.addAttribute("locations", adrobeService.listLocations());
        model.addAttribute("vendors", service.listVendors(0, 50, "", "", "", ""));
        return "admin.offersTodays";
    }

    @RequestMapping("/pendingApprovals")
    public String pendingOfferApprovals(@ModelAttribute("user") Admin admin, Model model) {
        log.debug("admin : " + admin);
        model.addAttribute("category", adrobeService.listCategories());
        model.addAttribute("vendors", service.listVendors(0, 50, "", "", "", ""));
        return "admin.offersUnapproved";
    }

    @RequestMapping("/keywordsEdit")
    public String editOfferKeywords(@ModelAttribute("user") Admin admin, Model model) {
        log.debug("admin : " + admin);
        model.addAttribute("category", adrobeService.listCategories());
        model.addAttribute("vendors", service.listVendors(0, 50, "", "", "", ""));
        return "admin.editKeywords";
    }

    @RequestMapping("/categories")
    public String categorieMenu(@ModelAttribute("user") Admin admin, Model model) {
        log.debug("admin : " + admin);
        model.addAttribute("locations", adrobeService.listLocations());
        model.addAttribute("category", adrobeService.listCategories());
        return "admin.categories";
    }

    @RequestMapping("/cancelordermenu")
    public String cancelOrderMenu(@ModelAttribute("user") Admin admin, Model model) {
        return "admin.cancelordermenu";
    }

    @RequestMapping(value = "/ordersGrid", method = RequestMethod.POST)
    @ResponseBody
    String ordersGrid(@ModelAttribute("user") Admin admin,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "rows", required = false, defaultValue = "10") int limit,
            @RequestParam(value = "orderid", required = false, defaultValue = "") String orderid,
            @RequestParam(value = "custormername", required = false, defaultValue = "") String custormername,
            @RequestParam(value = "city", required = false, defaultValue = "") String city
    ) {
        log.debug("admin : vendorsGrid" + admin);
        HashMap gridMap = new HashMap();
        int start = 0;
        start = (page - 1) * limit;
        log.debug("admin : {}start, max", start + "," + limit);
        orderid = orderid != null ? orderid.trim() : "";
        custormername = custormername != null ? custormername.trim() : "";
        custormername = custormername != null ? custormername.trim() : "";
        List<Orders> orders = service.listOrders(start, limit, orderid, custormername, city);
        int totalCount = service.getOrderCount(orderid, custormername, city);
        log.debug("admin : {}vendorsGrid", orders);
        gridMap.put("rows", orders);
        gridMap.put("total", totalCount);
        gridMap.put("page", page);
        ObjectMapper mapper = new ObjectMapper();
        String orderStr = null;
        try {
            orderStr = mapper.writeValueAsString(gridMap);
        } catch (IOException ex) {
            log.error("Exception while converting vendorlist to json", ex);
            orderStr = "[]";
        }
        return orderStr;
    }

    @RequestMapping(value = "/cancelorders", method = RequestMethod.POST)
    @ResponseBody
    String cancelOrdersData(@ModelAttribute("user") Admin admin,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "rows", required = false, defaultValue = "10") int limit,
            @RequestParam(value = "orderid", required = false, defaultValue = "") String orderid
    ) {
        log.debug("admin : cancelOrdersData" + admin);
        HashMap gridMap = new HashMap();
        int start = 0;
        start = (page - 1) * limit;
        log.debug("admin : {}start, max", start + "," + limit);
        orderid = orderid != null ? orderid.trim() : "";
        List<OrderCancellation> orders = service.listCancelOrders(start, limit, orderid);
        int totalCount = service.getCancelOrderCount(orderid);
        log.debug("admin : {}vendorsGrid", orders);
        gridMap.put("rows", orders);
        gridMap.put("total", totalCount);
        gridMap.put("page", page);
        ObjectMapper mapper = new ObjectMapper();
        String orderStr = null;
        try {
            orderStr = mapper.writeValueAsString(gridMap);
        } catch (IOException ex) {
            log.error("Exception while converting vendorlist to json", ex);
            orderStr = "[]";
        }
        return orderStr;
    }

    @RequestMapping(value = "/vendorgrid", method = RequestMethod.POST)
    @ResponseBody
    String vendorsGrid(@ModelAttribute("user") Admin admin, Model model,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "rows", required = false, defaultValue = "10") int limit,
            @RequestParam(value = "srch_name", required = false, defaultValue = "") String name,
            @RequestParam(value = "srch_mobile", required = false, defaultValue = "") String mobile,
            @RequestParam(value = "srch_emil", required = false, defaultValue = "") String email,
            @RequestParam(value = "srch_cat", required = false, defaultValue = "") String category
    ) {
        //var data="srch_name="+$("#srch_name").val()+"&srch_mobile="+$("#srch_mobile").val()+"&srch_emil="+$("#srch_emil").val();
        log.debug("admin : vendorsGrid" + admin);
        HashMap gridMap = new HashMap();
        int start = 0;
        start = (page - 1) * limit;
        log.debug("admin : {}start, max", start + "," + limit);
        category = category.equals("all") ? "" : category;
        List<Vendor> vendor = service.listVendors(start, limit, name, email, mobile, category);
        int totalCount = service.getVendorCount(name, email, mobile);
        log.debug("admin : {}vendorsGrid", vendor);
        //  public SearchResult(String total, int page, int records, List<T> rows);
        gridMap.put("rows", vendor);
        gridMap.put("total", totalCount);
        gridMap.put("page", page);
        gridMap.put("page", page);
        gridMap.put("existVendorsList", service.listVendors(0, 1000, "", "", "", ""));
        ObjectMapper mapper = new ObjectMapper();
        String vendorsStr = null;
        try {
            vendorsStr = mapper.writeValueAsString(gridMap);
        } catch (IOException ex) {
            log.error("Exception while converting vendorlist to json", ex);
            vendorsStr = "[]";
        }
        return vendorsStr;
    }
//vendorname=gopal&email=glt3n%40gmail.com&mobile=7894561237&password=open&location=&
    //      latitude=0.457&longitude=0.456&category=&zipcode=502032&vendorId=2

    @RequestMapping(value = {"/addVendor"}, method = RequestMethod.POST)
    public @ResponseBody
    String addVendor(@ModelAttribute("user") Admin admin,
            Vendor vendor,
            @RequestParam(value = "vendorID", required = false, defaultValue = "0") int vendorId,
            @RequestParam("vendorname") String vendorname,
            @RequestParam("email") String email,
            @RequestParam("mobile") String mobile,
            @RequestParam("password") String password,
            @RequestParam("location") int location,
            @RequestParam("latitude") double latitude,
            @RequestParam("longitude") double longitude,
            @RequestParam("category") int category,
            @RequestParam("address") String address,
            @RequestParam("zipcode") String zipcode,
            @RequestParam(value = "sortOrder", required = false, defaultValue = "999") int sortOrder
    ) {
        log.debug("inside saveOffer : " + admin);
        if (vendorId > 0) {
            vendor.setVendorID(vendorId);
        }
        vendor.setName(vendorname);
        vendor.setPhone(mobile);
        vendor.setPassword(password);
        vendor.setZipCode(zipcode);
        vendor.setEmail(email);
        vendor.setLatitude(latitude);
        vendor.setLongitude(longitude);
        vendor.setCategoryID(category);
        vendor.setLocation(address);
        vendor.setLocationId(location);
        vendor.setStatus(1);
        vendor.setSortOrder(sortOrder);
        try {
            service.addVendor(admin, vendor);
            messagingService.sendRegistrationDetailsToVendor(vendor);
        } catch (Exception ex) {
            log.error("Error while adding vendor", ex);
            return "{\"respmsg\":\"failed\"}";
        }
        return "{\"respmsg\":\"success\"}";
    }

    @RequestMapping(value = "/todaysOffGrid", method = RequestMethod.POST)
    @ResponseBody
    String offersTodays(@ModelAttribute("user") Admin admin,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "rows", required = false, defaultValue = "10") int limit,
            @RequestParam(value = "title", required = false, defaultValue = "") String title
    ) {
        log.debug("admin : vendorsGrid" + admin);
        HashMap gridMap = new HashMap();
        int start = 0;
        start = (page - 1) * limit;
        int state = 2;
        log.debug("admin : {}start, max", start + "," + limit);
        List<Offers> offers = service.getOffers(state, start, limit, title);
        int totalCount = service.getOffersCount(state, title);
        log.debug("admin : {}vendorsGrid", offers);
        gridMap.put("rows", offers);
        gridMap.put("total", totalCount);
        gridMap.put("page", page);
        ObjectMapper mapper = new ObjectMapper();
        String offerStr = null;
        try {
            offerStr = mapper.writeValueAsString(gridMap);
        } catch (IOException ex) {
            log.error("Exception while converting vendorlist to json", ex);
            offerStr = "[]";
        }
        return offerStr;
    }

    @RequestMapping(value = "/unapprovedOffGrid", method = RequestMethod.POST)
    @ResponseBody
    String unapprovedOffers(@ModelAttribute("user") Admin admin,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "rows", required = false, defaultValue = "10") int limit,
            @RequestParam(value = "title", required = false, defaultValue = "") String title
    ) {
        log.debug("admin : unapprovedOffGrid");
        HashMap gridMap = new HashMap();
        int start = 0;
        start = (page - 1) * limit;
        log.debug("admin : {}start, max", start + "," + limit);
        List<Offers> unapprovedOffers = service.getUnapprovedOffers(start, limit, title);
        int totalCount = service.getPendingOffersCount();
        gridMap.put("rows", unapprovedOffers);
        gridMap.put("total", totalCount);
        gridMap.put("page", page);
        ObjectMapper mapper = new ObjectMapper();
        String unapprovedOfferStr = null;

        try {
            unapprovedOfferStr = mapper.writeValueAsString(gridMap);
        } catch (IOException ex) {
            log.error("Exception while converting unapprovedOfferList to json", ex);
            unapprovedOfferStr = "[]";
        }
        return unapprovedOfferStr;
    }

    @RequestMapping(value = "/allOffGrid", method = RequestMethod.POST)
    public @ResponseBody
    String allOffersGrid(@ModelAttribute("user") Admin admin,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "rows", required = false, defaultValue = "10") int limit,
            @RequestParam(value = "seo_title", required = false, defaultValue = "") String seo_title) {
        log.debug("admin : allOffGrid");
        HashMap gridMap = new HashMap();
        int start = 0;
        start = (page - 1) * limit;
        log.debug("admin : {}start, max", start + "," + limit);
        List<Offers> allOffers = service.getAllApprovedOffers(start, limit, seo_title);
        int totalCount = service.getAllApprovedOffersCount();
        gridMap.put("rows", allOffers);
        gridMap.put("total", totalCount);
        gridMap.put("page", page);
        ObjectMapper mapper = new ObjectMapper();
        String allOffersStr;

        try {
            allOffersStr = mapper.writeValueAsString(gridMap);
        } catch (IOException ex) {
            log.error("Exception while converting unapprovedOfferList to json", ex);
            allOffersStr = "[]";
        }
        return allOffersStr;
    }

    @RequestMapping(value = "/adminSearchSeoOffer", method = RequestMethod.GET)
    public @ResponseBody
    String searchSeoOffer(@ModelAttribute("user") Admin admin,
            @RequestParam(value = "seo_title", required = true) String seo_title) {
        String offersStr = "";
        if (StringUtils.isNotBlank(seo_title)) {
            Offers offer = service.getOfferBySeoTitle(seo_title);
            ObjectMapper mapper = new ObjectMapper();
            try {
                offersStr = mapper.writeValueAsString(offer);
            } catch (IOException ex) {
                log.error("Exception while converting unapprovedOfferList to json", ex);
            }
        }

        return offersStr;
    }

    @RequestMapping(value = "/adminUpdateKeywords", method = RequestMethod.POST)
    public @ResponseBody
    String updateKeywords(@ModelAttribute("user") Admin admin,
            @RequestBody EditKeywordsWrapper requestWrapper) {
        Offers offer = service.getOfferBySeoTitle(requestWrapper.getSeo_title());

        try {
            offer.setKeywords(requestWrapper.getKeywords());
            service.saveOffer(offer);
        } catch (Exception ex) {
            log.error("error updating keywords of offer", ex);
            return "{\"respmsg\":\"failure\"}";
        }

        return "{\"respmsg\":\"success\"}";
    }

    @RequestMapping(value = "/categoryGrid", method = RequestMethod.POST)
    @ResponseBody
    String categoryGrid(@ModelAttribute("user") Admin admin,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "rows", required = false, defaultValue = "10") int limit
    ) {
        log.debug("admin : categoryGrid" + admin);
        HashMap gridMap = new HashMap();
        int start = 0;
        start = (page - 1) * limit;
        log.debug("admin : {}start, max", start + "," + limit);
        List<Categories> categories = service.getCategories(start, limit);
        int totalCount = service.getCategoryCount();
        log.debug("admin : {}categoryGrid", categories);
        gridMap.put("rows", categories);
        gridMap.put("total", totalCount);
        gridMap.put("page", page);
        ObjectMapper mapper = new ObjectMapper();
        String categoryStr = null;
        try {
            categoryStr = mapper.writeValueAsString(gridMap);
        } catch (IOException ex) {
            log.error("Exception while converting vendorlist to json", ex);
            categoryStr = "[]";
        }
        return categoryStr;
    }

    @RequestMapping(value = "/exclusiveOffGrd", method = RequestMethod.POST)
    @ResponseBody
    String exclusiveOffGrd(@ModelAttribute("user") Admin admin,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "rows", required = false, defaultValue = "10") int limit,
            @RequestParam(value = "title", required = false, defaultValue = "") String title
    ) {
        log.debug("admin : vendorsGrid" + admin);
        HashMap gridMap = new HashMap();
        int status = 1;
        int start = 0;
        start = (page - 1) * limit;
        log.debug("admin : {}start, max", start + "," + limit);
        List<Offers> offers = service.getOffers(status, start, limit, title);
        int totalCount = service.getOffersCount(status, title);
        log.debug("admin : {}vendorsGrid", offers);
        gridMap.put("rows", offers);
        gridMap.put("total", totalCount);
        gridMap.put("page", page);
        ObjectMapper mapper = new ObjectMapper();
        String offerStr = null;
        try {
            offerStr = mapper.writeValueAsString(gridMap);
        } catch (IOException ex) {
            log.error("Exception while converting vendorlist to json", ex);
            offerStr = "[]";
        }
        return offerStr;
    }

    @RequestMapping(value = "/premiumOffGrd", method = RequestMethod.POST)
    @ResponseBody
    String offersPremium(@ModelAttribute("user") Admin admin,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "rows", required = false, defaultValue = "10") int limit,
            @RequestParam(value = "title", required = false, defaultValue = "") String title
    ) {
        log.debug("admin : vendorsGrid" + admin);
        HashMap gridMap = new HashMap();
        int state = 1;
        int start = 0;
        start = (page - 1) * limit;
        log.debug("admin : {}start, max", start + "," + limit);
        List<PremiumOffers> offers = service.getPremiumOffers(state, start, limit, title);
        int totalCount = service.getPremiumCount(state, title);
        log.debug("admin : {}vendorsGrid", offers);
        gridMap.put("rows", offers);
        gridMap.put("total", totalCount);
        gridMap.put("page", page);
        ObjectMapper mapper = new ObjectMapper();
        String offerStr = null;
        try {
            offerStr = mapper.writeValueAsString(gridMap);
        } catch (IOException ex) {
            log.error("Exception while converting vendorlist to json", ex);
            offerStr = "[]";
        }
        return offerStr;
    }

    @RequestMapping(value = {"/adminOffersAdd"}, method = RequestMethod.POST)
    public @ResponseBody
    String saveOffer(@ModelAttribute("user") Admin admin, Offers offer, BindingResult result) {
        log.debug("inside saveOffer : " + admin);
        //Today-0,Exclusive-1, Premium-2 , offers
        Offers savedOffer = null;
        try {
            offer.setStatus(1);
            DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
            String nowString = formatter.format(new Date());
            if (offer.getEndDate() != null) {

                Date now = formatter.parse(nowString);

                Date startDate = formatter.parse(offer.getStartDate());
                Date endDate = formatter.parse(offer.getEndDate());
                if (startDate.after(now) && endDate.before(now)) {
                    offer.setStatus(0);
                    offer.setScheduleStatus(1); // yet to be scheduled
                } else if (startDate.before(now) && endDate.after(now)) {
                    offer.setScheduleStatus(2); // already scheduled; need to be unscheduled
                }
            } else {
                offer.setStartDate(nowString);
                offer.setStatus(1);
            }

            savedOffer = service.saveOffer(offer);
        } catch (Exception ex) {
            log.error("Error while saveOffer", ex);
            return "{\"respmsg\":\"failed\"}";
        }
        return "{\"respmsg\":\"success\"}";
    }

    @RequestMapping(value = {"/adminOffersApprove"}, method = RequestMethod.POST)
    public @ResponseBody
    String approveOffer(@ModelAttribute("user") Admin admin, @RequestParam("offerId") int offerId, BindingResult result) {
        log.debug("inside approveOffer : {}" + admin, offerId);
        try {
            service.approveOffer(offerId);
        } catch (Exception ex) {
            log.error("Error while approving offer", ex);
            return "{\"respmsg\":\"failed\"}";
        }

        return "{\"respmsg\":\"success\"}";
    }

    @RequestMapping(value = {"/adminDeleteUnapprovedOff"}, method = RequestMethod.POST)
    public @ResponseBody
    String deleteUnapprovedOffer(@ModelAttribute("user") Admin admin, @RequestParam("offerId") int offerId, BindingResult result) {
        log.debug("inside delete unapproved offer : {}", offerId);
        try {
            service.deleteUnapprovedOffer(offerId);
        } catch (Exception ex) {
            log.error("Error while deleting unapproved offer", ex);
            return "{\"respmsg\":\"failed\"}";
        }
        return "{\"respmsg\":\"success\"}";
    }

    @RequestMapping(value = {"/premiumOfferAdd"}, method = RequestMethod.POST)
    public @ResponseBody
    String premiumOfferAdd(@ModelAttribute("user") Admin admin, PremiumOffers offer, BindingResult result) {
        log.debug("inside saveOffer : " + admin);
        //Today-0,Exclusive-1, Premium-2 , offers
        PremiumOffers savedOffer = null;
        try {
            if (offer.getEndDate() != null) {
                DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
                String nowString = formatter.format(new Date());
                Date now = formatter.parse(nowString);

                Date startDate = formatter.parse(offer.getStartDate());
                Date endDate = formatter.parse(offer.getEndDate());
                if (startDate.after(now) && endDate.before(now)) {
                    offer.setStatus(0);
                    offer.setScheduleStatus(1); // yet to be scheduled
                } else if (startDate.before(now) && endDate.after(now)) {
                    offer.setScheduleStatus(2); // already scheduled; need to be unscheduled
                }
            } else {
                offer.setStatus(1);
            }

            savedOffer = service.savePremiumOffer(offer);
        } catch (Exception ex) {
            log.error("Error while premiumOfferAdd", ex);
            return "{\"respmsg\":\"failed\"}";
        }
        return "{\"respmsg\":\"success\"}";
    }

    @RequestMapping(value = {"/addCategory"}, method = RequestMethod.POST)
    public @ResponseBody
    String addCategory(@ModelAttribute("user") Admin admin, Categories category, BindingResult result) {
        log.debug("inside saveOffer : " + admin);
        Categories savedCatgory = null;
        try {
            savedCatgory = service.saveCategory(category);
            adrobeService.removeCategoriesCaches();
        } catch (Exception ex) {
            log.error("Error while addCategory", ex);
            return "{\"respmsg\":\"failed\"}";
        }
        return "{\"respmsg\":\"success\"}";
    }

    @RequestMapping("/adminmenu")
    public String adminMenu(@ModelAttribute("user") Admin admin, Model model) {
        log.debug("admin : " + admin);
        return "admin.adminmenu";
    }

    @RequestMapping(value = "/admingrid", method = RequestMethod.POST)
    @ResponseBody
    String adminGrid(@ModelAttribute("user") Admin admin,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "rows", required = false, defaultValue = "10") int limit
    ) {
        log.debug("admin : admingrid" + admin);
        HashMap gridMap = new HashMap();
        int start = 0;
        start = (page - 1) * limit;
        log.debug("admin : {}start, max", start + "," + limit);
        List<Admin> categories = service.getAdminList(start, limit);
        int totalCount = service.getAdminCount();
        log.debug("admin : {}admingrid", categories);
        gridMap.put("rows", categories);
        gridMap.put("total", totalCount);
        gridMap.put("page", page);
        ObjectMapper mapper = new ObjectMapper();
        String categoryStr = null;
        try {
            categoryStr = mapper.writeValueAsString(gridMap);
        } catch (IOException ex) {
            log.error("Exception while converting adminList to json", ex);
            categoryStr = "[]";
        }
        return categoryStr;
    }

    @RequestMapping("/locationMenu")
    public String locationMenu(@ModelAttribute("user") Admin admin, Model model) {
        log.debug("admin : " + admin);
        return "admin.locationmenu";
    }

    @RequestMapping(value = "/locationGrid", method = RequestMethod.POST)
    @ResponseBody
    String locationGrid(@ModelAttribute("user") Admin admin,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "rows", required = false, defaultValue = "10") int limit,
            @RequestParam(value = "name", required = false, defaultValue = "") String name,
            @RequestParam(value = "zipCode", required = false, defaultValue = "") String zipCode
    ) {
        log.debug("admin : admingrid" + admin);
        HashMap gridMap = new HashMap();
        int start = start = (page - 1) * limit;
        log.debug("admin : {}start, max", start + "," + limit);
        List<Admin> categories = service.getAdminList(start, limit);
        List<Location> locations = service.getLocationList(start, limit, name, zipCode);
        int totalCount = service.getLocationCount(name, zipCode);
        log.debug("admin : {}admingrid", locations);
        gridMap.put("rows", locations);
        gridMap.put("total", totalCount);
        gridMap.put("page", page);
        ObjectMapper mapper = new ObjectMapper();
        String locationStr = null;
        try {
            locationStr = mapper.writeValueAsString(gridMap);
        } catch (IOException ex) {
            log.error("Exception while converting Location to json", ex);
            locationStr = "[]";
        }
        return locationStr;
    }

    @RequestMapping(value = {"/addLocation"}, method = RequestMethod.POST)
    public @ResponseBody
    String addLocation(@ModelAttribute("user") Admin admin, Location location, BindingResult result) {
        log.debug("inside saveOffer : " + admin);
        Location locationstr = null;
        try {
            locationstr = service.saveLocation(location);
        } catch (Exception ex) {
            log.error("Error while addLocation", ex);
            return "{\"respmsg\":\"failed\"}";
        }
        return "{\"respmsg\":\"success\"}";
    }

    @RequestMapping("/viewOrder")
    public String viewOrder(@ModelAttribute("user") Admin admin, Model model,
            @RequestParam(value = "orderId", required = true, defaultValue = "0") int orderId) {
        log.debug("admin : " + admin + "param:");
        Orders order = adrobeService.getOrder(orderId);
        order.setRead(1);
        adrobeService.save(order);
        model.addAttribute("orders", order);
        model.addAttribute("orderedProducts", adrobeService.getOrderProducts(orderId));
        model.addAttribute("orderHistory", adrobeService.getOrderedHistory(orderId));
        //adrobeService.getOrder(orderId).get
        model.addAttribute("orderStatCodes", adrobeService.getOrderStatusCodes());
        model.addAttribute("orderTotalList", adrobeService.getOrderTotals(orderId));

        model.addAttribute("vendorNames", service.getVendorNames());
        return "admin.viewOrder";
    }

    @RequestMapping(value = {"/deleteVendor"})
    public @ResponseBody
    String deleteVendor(@ModelAttribute("user") Admin admin, @RequestParam("vendorId") int vendorId) {
        log.debug("inside deleteVendor : " + vendorId);
        try {
            service.deleteVendor(admin.getId(), vendorId);
        } catch (Exception e) {
            log.error("Error while deleting vendor", e);
            return "{\"respmsg\":\"failed\"}";
        }
        return "{\"respmsg\":\"success\"}";
    }

    @RequestMapping(value = {"/deleteOffer"})
    public @ResponseBody
    String deleteOffer(@ModelAttribute("user") Admin admin, @RequestParam("offerId") int offerId) {
        log.debug("inside deleteOffer : " + offerId);
        try {
            service.deleteOffer(admin.getId(), offerId);
        } catch (Exception e) {
            log.error("Error while deleting offer", e);
            return "{\"respmsg\":\"failed\"}";
        }
        return "{\"respmsg\":\"success\"}";
    }

    @RequestMapping(value = {"/deletePreOffer"})
    public @ResponseBody
    String deletePreOffer(@ModelAttribute("user") Admin admin, @RequestParam("preOfferId") int preOfferId) {
        log.debug("inside deleteOffer : " + preOfferId);
        try {
            service.deletePremiumOffer(admin.getId(), preOfferId);
        } catch (Exception e) {
            log.error("Error while deleting offer", e);
            return "{\"respmsg\":\"failed\"}";
        }
        return "{\"respmsg\":\"success\"}";
    }

    @RequestMapping(value = {"/deleteCategory"})
    public @ResponseBody
    String deleteCategory(@ModelAttribute("user") Admin admin, @RequestParam("catId") int catId) {
        log.debug("inside deleteOffer : " + catId);
        try {
            service.deleteCategory(admin.getId(), catId);
        } catch (Exception e) {
            log.error("Error while deleting offer", e);
            return "{\"respmsg\":\"failed\"}";
        }
        return "{\"respmsg\":\"success\"}";
    }

    @RequestMapping(value = {"/changeorderstatus"}, method = RequestMethod.POST)
    public @ResponseBody
    String changeOrderStatus(@ModelAttribute("user") Admin admin,
            Vendor vendor,
            @RequestParam(value = "orderId", required = false, defaultValue = "0") int orderId,
            @RequestParam("changeStatus") String changeStatus,
            @RequestParam("comments") String comments
    ) {//
        log.debug("inside changeOrderStatus : " + admin);
        try {
            adrobeService.changeOrderStatus(orderId, comments, changeStatus);
        } catch (Exception ex) {
            log.error("Error while changeOrderStatus", ex);
            return "{\"respmsg\":\"fail\"}";
        }
        return "{\"respmsg\":\"success\"}";
    }

    @RequestMapping(value = {"/deleteLocId"})
    public @ResponseBody
    String deleteLocation(@ModelAttribute("user") Admin admin, @RequestParam("locId") int locId) {
        log.debug("inside deleteOffer : " + locId);
        try {
            service.deleteLocation(admin.getId(), locId);
        } catch (Exception e) {
            log.error("Error while deleting offer", e);
            return "{\"respmsg\":\"failed\"}";
        }
        return "{\"respmsg\":\"success\"}";
    }

    @RequestMapping(value = {"/deleteCustomer"})
    public @ResponseBody
    String deleteCustomer(@ModelAttribute("user") Admin admin, @RequestParam("customerId") int customerId) {
        log.debug("inside deleteOffer : " + customerId);
        try {
            service.deleteCustomer(admin.getId(), customerId);
        } catch (Exception e) {
            log.error("Error while deleting offer", e);
            return "{\"respmsg\":\"failed\"}";
        }
        return "{\"respmsg\":\"success\"}";
    }

    @RequestMapping("/customersmenu")
    public String customersMenu(@ModelAttribute("user") Admin admin, Model model) {
        return "admin.customersmenu";
    }

    @RequestMapping(value = "/customersGrid", method = RequestMethod.POST)
    @ResponseBody
    String customersGrid(@ModelAttribute("user") Admin admin,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "rows", required = false, defaultValue = "10") int limit,
            @RequestParam(value = "srch_name", required = false, defaultValue = "") String custname,
            @RequestParam(value = "srch_emil", required = false, defaultValue = "") String custemail,
            @RequestParam(value = "srch_mobile", required = false, defaultValue = "") String custphone
    ) {
        log.debug("admin : customersGrid" + admin);
        HashMap gridMap = new HashMap();
        int start = 0;
        start = (page - 1) * limit;
        log.debug("admin : {}start, max", start + "," + limit);
        custname = custname != null ? custname.trim() : "";
        custemail = custemail != null ? custemail.trim() : "";
        custemail = custemail != null ? custemail.trim() : "";
        List<Customers> customers = service.listCustomers(start, limit, custname, custemail, custphone);
        int totalCount = service.getCustomersCount(custname, custemail, custphone);
        log.debug("admin : {}vendorsGrid", customers);
        gridMap.put("rows", customers);
        gridMap.put("total", totalCount);
        gridMap.put("page", page);
        ObjectMapper mapper = new ObjectMapper();
        String orderStr = null;
        try {
            orderStr = mapper.writeValueAsString(gridMap);
        } catch (IOException ex) {
            log.error("Exception while converting customerlist to json", ex);
            orderStr = "[]";
        }
        return orderStr;
    }

    @RequestMapping(value = {"/logout"})
    public String logout(HttpSession session, SessionStatus status) {
        log.debug("logout");
        status.setComplete();
        session.invalidate();
        return "redirect:/admin/login";
    }

    @RequestMapping(value = {"/coupons"})
    public String coupons(HttpServletRequest request, Model model)
            throws Exception {
        int page = NumberUtils.toInt(request.getParameter("page"), 1);
        int couponId = NumberUtils.toInt(request.getParameter("cid"), -1);
        String searchType = request.getParameter("searchType");
        String searchTerm = request.getParameter("searchTerm");
        int count = 20;
        String operation = request.getParameter("operation");
        String forward = "admin.coupons";
        if (couponId > 0 && ("edit".equalsIgnoreCase(operation) || "delete".equalsIgnoreCase(operation) || "view".equalsIgnoreCase(operation))) {
            Coupons coupons = service.getCoupon(couponId);
            model.addAttribute("selectedCoupon", coupons);

            if ("edit".equalsIgnoreCase(operation)) {
                forward = "admin.addcoupon";
            } else {
                forward = "admin/couponsDetails";
            }
        } else if ("add".equalsIgnoreCase(operation)) {
            forward = "admin.addcoupon";
        } else {
            if ("update".equalsIgnoreCase(operation) || "insert".equalsIgnoreCase(operation)) {
                Coupons coupons = new Coupons();
                RequestUtil.populate(coupons, request);

                //we are storing in uppercase all the coupons code
                if (StringUtils.isNotBlank(coupons.getCode())) {
                    coupons.setCode(coupons.getCode().toUpperCase());
                }

                String start_date_str = request.getParameter("start_date_str");
                String end_date_str = request.getParameter("end_date_str");

                if (StringUtils.isNotBlank(start_date_str)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    coupons.setStart_date(sdf.parse(start_date_str));
                }

                if (StringUtils.isNotBlank(end_date_str)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    Calendar c = Calendar.getInstance();
                    c.setTime(sdf.parse(end_date_str));
                    c.set(Calendar.HOUR_OF_DAY, 23);
                    c.set(Calendar.MINUTE, 59);
                    c.set(Calendar.SECOND, 59);
                    coupons.setEnd_date(c.getTime());
                }

                String error = null;
                Coupons coupon = (Coupons) service.getObject(Coupons.class, new String[]{"code"}, new String[]{coupons.getCode()});

                if (coupon != null && !(coupon.getId().equals(coupons.getId()))) {
                    error = "Coupon code exists";
                }

                if (StringUtils.isNotBlank(error)) {
                    model.addAttribute("error", error);
                    model.addAttribute("selectedCoupon", coupons);
                    forward = "admin.addcoupon";
                } else {
                    if ("insert".equalsIgnoreCase(operation)) {
                        coupons.setCreated_date(new Date());
                    } else {
                        coupons.setUpdated_date(new Date());
                    }
                    service.saveCoupon(coupons);
                }

            } else if ("confirmDelete".equalsIgnoreCase(operation) && couponId > 0) {
                service.deleteCoupon(couponId);
            }
            SearchObj searchObj = new SearchObj();
            searchObj.page = page;
            searchObj.limit = count;
            List<Coupons> couponsList = service.getCoupons(searchObj, searchType, searchTerm);
            model.addAttribute("couponsList", couponsList);
            model.addAttribute("searchObj", searchObj);
        }

        return forward;
    }

    @RequestMapping(value = {"/updateCustomer"}, method = RequestMethod.POST)
    public @ResponseBody
    String updateCustomer(@ModelAttribute("user") Admin admin, Customers customer, BindingResult result) {
        log.debug("inside update Customer: " + admin);
        //Today-0,Exclusive-1, Premium-2 , offers
        Customers updateCust = null;
        try {
            updateCust = service.updateCustomer(customer);
        } catch (Exception ex) {
            log.error("Error while update Customers", ex);
            return "{\"respmsg\":\"failed\"}";
        }
        return "{\"respmsg\":\"success\"}";
    }

    @RequestMapping(value = {"/reviews"})
    public String reviews(HttpServletRequest request, Model model) {

        int page = NumberUtils.toInt(request.getParameter("page"), 1);
        int reviewId = NumberUtils.toInt(request.getParameter("rid"), -1);
        int count = 20;
        String operation = request.getParameter("operation");
        String forward = "admin.reviews";
        if (reviewId > 0 && "edit".equalsIgnoreCase(operation) || "delete".equalsIgnoreCase(operation) || "view".equalsIgnoreCase(operation)) {
            Reviews reviews = service.getReview(reviewId);
            model.addAttribute("selectedReviews", reviews);

            if (reviews != null && "view".equalsIgnoreCase(operation)) {
                double avgRating = service.getProductAverageRating(reviews.getProductsId());
                model.addAttribute("averageRating", avgRating);
            }
            if ("edit".equalsIgnoreCase(operation)) {
                forward = "admin.addreview";
            } else {
                forward = "admin/reviewDetails";
            }
        } else {
            if ("update".equalsIgnoreCase(operation)) {
                Reviews reviews = service.getReview(reviewId);
                String reviewsRating = request.getParameter("reviews_rating");
                String reviewsText = request.getParameter("reviews_text");
                reviews.setReviewsRating(NumberUtils.toInt(reviewsRating));
                reviews.setReviewsText(reviewsText);
                service.saveReviews(reviews);

            } else if ("confirmDelete".equalsIgnoreCase(operation) && reviewId > 0) {
                service.deleteReviews(reviewId);
            } else if ("approve".equalsIgnoreCase(operation) && reviewId > 0) {
                Reviews reviews = service.getReview(reviewId);
                if (reviews != null) {
                    reviews.setApproved((short) 1);
                    service.saveReviews(reviews);
                }
            } else if ("reject".equalsIgnoreCase(operation) && reviewId > 0) {
                Reviews reviews = service.getReview(reviewId);
                if (reviews != null) {
                    reviews.setApproved((short) 0);
                    service.saveReviews(reviews);
                }
            }
            SearchObj searchObj = new SearchObj();
            searchObj.page = page;
            searchObj.count = count;
            List<Reviews> reviewsList = service.getReviews(searchObj);
            model.addAttribute("reviewsList", reviewsList);
            model.addAttribute("searchObj", searchObj);
        }

        return forward;
    }

    @RequestMapping(value = {"/reports"})
    public String reportsView(HttpServletRequest request, Model model) {
        return "admin.reports";
    }

    @RequestMapping(value = {"/reports.xls"})
    public String reports(HttpServletRequest request, Model model) {
        Map<String, String> requestMap = RequestUtil.getRequestMap(request, false);
        Map<String, Object> dataMap = service.getReports(requestMap);
        model.addAllAttributes(dataMap);
        return "excelReports";
    }

}
