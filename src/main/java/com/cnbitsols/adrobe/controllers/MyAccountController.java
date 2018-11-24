/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cnbitsols.adrobe.controllers;

import com.cnbitsols.adrobe.dto.ApiResponse;
import com.cnbitsols.adrobe.entities.AddressBook;
import com.cnbitsols.adrobe.entities.Customers;
import com.cnbitsols.adrobe.entities.Feedback;
import com.cnbitsols.adrobe.entities.Offers;
import com.cnbitsols.adrobe.entities.Orders;
import com.cnbitsols.adrobe.entities.OrdersProducts;
import com.cnbitsols.adrobe.entities.ProductCoupon;
import com.cnbitsols.adrobe.services.AdrobeService;
import com.cnbitsols.adrobe.utils.RequestUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = {"/myaccount"})
public class MyAccountController {

    private static Logger log = LoggerFactory.getLogger(MyAccountController.class);
    @Autowired
    private final AdrobeService service = null;

    @ModelAttribute()
    public void addHeaderData(Model model) {
        model.addAttribute("categories", service.listMenuCategories());
        //model.addAttribute("locations", service.listLocations());
    }

    @RequestMapping(value = {"/", "", "/orders"}, method = RequestMethod.GET)
    public String getOrders(HttpServletRequest request, Model model, @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "rows", required = false, defaultValue = "10") int limit) {
        log.debug("list orders called...");
        Customers customers = RequestUtil.getCurrentCustomer(request);
        if (customers != null) {
            model.addAttribute("categories", service.listMenuCategories());

            List<Map> orders = service.getOrdersSummary(customers.getCustomersId(), page, limit);
            int totalCount = service.getOrderCountByCustomerId(customers.getCustomersId());

            model.addAttribute("orders", orders);
            model.addAttribute("total", totalCount);
            model.addAttribute("page", page);
            model.addAttribute("limit", limit);

            return "myaccount.orders";

        } else {
            return "redirect:/";
        }

    }

    @RequestMapping(value = "/ordersGrid", method = RequestMethod.POST)
    @ResponseBody
    String ordersGrid(HttpServletRequest request,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "rows", required = false, defaultValue = "10") int limit) {
        HashMap gridMap = new HashMap();

        log.debug("admin : {}start, max", page + "," + limit);
        Customers customers = RequestUtil.getCurrentCustomer(request);
        List<Map> orders = service.getOrdersSummary(customers.getCustomersId(), page, limit);
        int totalCount = service.getOrderCountByCustomerId(customers.getCustomersId());
        log.debug("myaccount : {}vendorsGrid", orders);
//        List<OrdersProducts> produts = new ArrayList<>();
//        if (orders != null && orders.size() > 0) {
//            for (Orders order : orders) {
//                produts.add(service.getProductByOrderId(order.getOrdersId()));
//            }
//
//        }
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

    @RequestMapping(value = {"/changepassword"}, method = RequestMethod.GET)
    public String getChangepwd(HttpServletRequest request, Model model) {
        log.info("list orders called...");
        Customers customers = RequestUtil.getCurrentCustomer(request);
        if (customers != null) {
            return "myaccount.changepassword";

        } else {
            return "redirect:/";
        }

    }

    @RequestMapping(value = {"/changepassword"}, method = RequestMethod.POST)
    public @ResponseBody
    String changePassword(HttpServletRequest request, Model model) {
        log.info("list orders called...");
        Customers customers = RequestUtil.getCurrentCustomer(request);
        if (customers != null) {
            String currentpwd = customers.getPassword();
            String currentPassword = request.getParameter("currentpassword");
            if (!currentpwd.equals(currentPassword)) {
                return "fail";
            }
            String newpassword = request.getParameter("newpassword");
            try {
                customers.setPassword(newpassword);
                service.save(customers);
                return "success";
            } catch (Exception e) {
                log.error("exception occured while changing pwd :" + ExceptionUtils.getFullStackTrace(e));
            }
            return "fail";

        } else {
            return "redirect:/";
        }

    }

    @RequestMapping(value = {"/feedback"}, method = RequestMethod.GET)
    public String getFeedback(HttpServletRequest request, Model model) {
        log.info("list orders called...");
        Customers customers = RequestUtil.getCurrentCustomer(request);
        if (customers != null) {
            return "myaccount.feedback";

        } else {
            return "redirect:/";
        }

    }

    @RequestMapping(value = {"/feedback"}, method = RequestMethod.POST)
    public @ResponseBody
    String sendFeedback(HttpServletRequest request, Model model) {
        log.info("list sendFeedback called...");
        try {
            Customers customers = RequestUtil.getCurrentCustomer(request);
            if (customers != null) {
                String feedback = request.getParameter("feedback");
                if (StringUtils.isBlank(feedback)) {
                    return "fail";
                }
                Feedback fback = new Feedback();
                fback.setFeedback(feedback);
                fback.setFeedbackCreated(new Date());
                fback.setEmail(customers.getEmailAddress());
                fback.setFirstname(customers.getFirstname());
                fback.setLastname(customers.getLastname());
                List<AddressBook> customerAddresses = service.getCustomerAddresses(customers.getCustomersId());
                if (customerAddresses != null && customerAddresses.size() > 0) {
                    AddressBook address = customerAddresses.get(0);
                    fback.setCity(address.getEntryCity());
                    fback.setStreetAddress(address.getEntryStreetAddress());
                    fback.setPostcode(address.getEntryPostcode());
                    fback.setCity(address.getEntryCity());
                }
                service.save(fback);
                return "success";

            } else {
                return "redirect:/";
            }
        } catch (Exception e) {
            log.error("Exception occured :: " + ExceptionUtils.getFullStackTrace(e));
        }
        return "redirect:/";

    }

    @RequestMapping(value = {"/addressbook"}, method = RequestMethod.GET)
    public String getAddressbook(HttpServletRequest request, Model model) {
        log.info("list orders called...");
        Customers customers = RequestUtil.getCurrentCustomer(request);
        if (customers != null) {
            model.addAttribute("customer", customers);
            AddressBook address = null;//service.getAddressById(customers.getDefaultAddressId(), customers.getCustomersId());
            model.addAttribute("address", address);
            List<AddressBook> customerAddresses = service.getCustomerAddresses(customers.getCustomersId());
            if (customerAddresses != null && customerAddresses.size() > 0) {
                model.addAttribute("addressEntry", customerAddresses);
            }
            return "myaccount.addressbook";

        } else {
            return "redirect:/";
        }

    }

    @RequestMapping(value = {"/addadress"}, method = RequestMethod.POST)
    public String changeAdress(HttpServletRequest request, Model model) {
        log.info("list orders called...");
        Customers customers = RequestUtil.getCurrentCustomer(request);
        if (customers != null) {
            String fullname = request.getParameter("fullname");
            String streetname = request.getParameter("streetname");
            String address = request.getParameter("address");
            String zipcode = request.getParameter("zipcode");
            String cityname = request.getParameter("cityname");
            AddressBook book = new AddressBook();
            book.setCustomersId(customers.getCustomersId());
            book.setEntryFirstname(fullname);
            book.setEntryStreetAddress(streetname);
            book.setEntryPostcode(zipcode);
            book.setEntrySuburb(address);
            book.setEntryCity(cityname);
            service.save(book);
            return "redirect:/myaccount/addressbook";

        } else {
            return "redirect:/";
        }

    }

    @RequestMapping(value = {"/deletaddress"}, method = RequestMethod.GET)
    public String deletAddress(HttpServletRequest request, Model model) {
        log.info("list orders called...");
        Customers customers = RequestUtil.getCurrentCustomer(request);
        if (customers != null) {
            int addressBookId = NumberUtils.toInt(request.getParameter("addressBookId"));

            AddressBook book = new AddressBook();
            book.setAddressBookId(addressBookId);

            service.delete(book);
        }
        return "redirect:/myaccount/addressbook";
    }

    @RequestMapping(value = {"/referfriend"}, method = RequestMethod.GET)
    public String referfriend(HttpServletRequest request, Model model) {
        log.info("list orders called...");
        Customers customers = RequestUtil.getCurrentCustomer(request);
        if (customers != null) {
            return "myaccount.referfriend";

        } else {
            return "redirect:/";
        }

    }

    @RequestMapping(value = {"/referfriend"}, method = RequestMethod.POST)
    public @ResponseBody
    String sendReferfriend(HttpServletRequest request, Model model) {
        log.info("list orders called...");
        Customers customers = RequestUtil.getCurrentCustomer(request);
        if (customers != null) {
            String mailId = request.getParameter("mailId");
            String message = request.getParameter("message");
            log.info("mailID::" + mailId + " message :: " + message);
            //what we need to do here

            return "myaccount.referfriend";

        } else {
            return "redirect:/";
        }

    }

    @RequestMapping(value = {"/order/{orderId}"}, method = RequestMethod.GET)
    public String getOrderinfo(HttpServletRequest request, Model model, @PathVariable("orderId") Integer orderId) {
        log.info("list getOrderinfo called..." + orderId);
        Customers customers = RequestUtil.getCurrentCustomer(request);
        if (customers != null) {
            Orders order = service.getCustomerOrder(orderId, customers.getCustomersId());
            model.addAttribute("order", order);

            if (order != null && order.getOrdersProducts() != null) {
                Set<Integer> productsIds = new HashSet<>();
                List<ProductCoupon> productCoupons = new ArrayList<>();
                for (OrdersProducts op : order.getOrdersProducts()) {
                    productsIds.add(op.getOrdersId());
                    if (op.getCoupon() == 1) {
                        productCoupons.add(service.getProductCouponsByOrderIdAndProductId(op.getOrdersId(), op.getOrdersProductsId()));
                    }
                }
                Map<Integer, Offers> productsMap = service.getProducts(productsIds);

                model.addAttribute("productsMap", productsMap);
                model.addAttribute("productCoupons", productCoupons);
            }
            return "myaccount.orderinfo";

        } else {
            return "redirect:/";
        }

    }

    @RequestMapping(value = {"/accountprofile"}, method = RequestMethod.GET)
    public String getMyaccountProfile(HttpServletRequest request, Model model) {
        log.info("list orders called...");
        Customers customers = RequestUtil.getCurrentCustomer(request);
        if (customers != null) {
            return "myaccount.accountprofile";

        } else {
            return "redirect:/";
        }

    }

    @RequestMapping(value = {"/accountprofile"}, method = RequestMethod.POST)
    public @ResponseBody
    String submitAccountProfile(HttpServletRequest request, Model model) {
        log.info("list submitAccountProfile called...");
        Customers customers = RequestUtil.getCurrentCustomer(request);
        if (customers != null) {
            String gender = request.getParameter("gender");
            String firstname = request.getParameter("firstname");
            String lastname = request.getParameter("lastname");
            String dob = request.getParameter("dob");
            String emailaddress = request.getParameter("emailaddress");
            String telephone = request.getParameter("telephone");
            customers.setTelephone(telephone);
            customers.setEmailAddress(emailaddress);
            customers.setDob(dob);
            customers.setLastname(lastname);
            customers.setFirstname(firstname);
            if (gender != null && gender.length() == 1) {
                customers.setGender(gender);
            }
            service.save(customers);
            return "success";

        } else {
            return "failed";
        }

    }

    @RequestMapping(value = {"/cancelOrder"}, method = RequestMethod.POST)
    public String cancelOrder(HttpServletRequest request, @RequestParam("orderId") Integer orderId, @RequestParam("comments") String comments) {

        Customers customer = RequestUtil.getCurrentCustomer(request);
        if (customer == null) {
            return "redirect:/";
        }

        service.cancelCustomerOrder(customer.getCustomersId(), orderId, comments);
        return "redirect:/myaccount/order/" + orderId;
    }

    @RequestMapping(value = {"/addressbook/{addrBookId}"}, method = RequestMethod.GET)
    public String getAddressById(HttpServletRequest request, Model model, @PathVariable("addrBookId") Integer addrBookId) {
        log.info("addrbook id..." + addrBookId);
        Customers customers = RequestUtil.getCurrentCustomer(request);
        if (customers != null) {
            AddressBook book = null;
            book = (AddressBook) service.getById(addrBookId, AddressBook.class);
            model.addAttribute("address", book);
            return "myaccount/addressinfo";

        } else {
            return "redirect:/";
        }

    }

    @RequestMapping(value = {"/updateAddressBook"}, method = RequestMethod.POST)
    public String updateAddress(HttpServletRequest request, Model model) {
        log.info("updateAddress called...");
        Customers customers = RequestUtil.getCurrentCustomer(request);
        if (customers != null) {
            int addressBookId = NumberUtils.toInt(request.getParameter("addressBookId"));

            String fullname = request.getParameter("fullname");
            String streetname = request.getParameter("streetname");
            String address = request.getParameter("address");
            String zipcode = request.getParameter("zipcode");
            String cityname = request.getParameter("cityname");

            AddressBook book = new AddressBook();
            book.setCustomersId(customers.getCustomersId());
            book.setAddressBookId(addressBookId);
            book.setEntryFirstname(fullname);
            book.setEntryStreetAddress(streetname);
            book.setEntryPostcode(zipcode);
            book.setEntrySuburb(address);
            book.setEntryCity(cityname);

            service.save(book);
            return "redirect:/myaccount/addressbook";
        } else {
            return "redirect:/";
        }

    }
    
    @RequestMapping(value = {"/coupons"})
     public String myCoupons(HttpServletRequest request,Model model){
      
        Customers customer = RequestUtil.getCurrentCustomer(request);
         if (customer == null) {
            return "redirect:/";
        }
                
        List<Object[]> couponsList = service.listCustomerCoupons(customer.getCustomersId());
        model.addAttribute("couponsList",couponsList);
        return "myaccount.coupons";
    }
     
     @RequestMapping(value = {"/favourites"})
     public String myAccountFavourites(HttpServletRequest request,Model model){      
      
        Customers customer = RequestUtil.getCurrentCustomer(request);
         if (customer == null) {
            return "redirect:/";
        }
         
        
        List<Offers> offers = service.getFavouriteProducts(customer.getCustomersId());
        model.addAttribute("offersList",offers);               
        
        return "myaccount.favourites";
    }
    
     @RequestMapping(value = {"/deleteFavourite"})
     public @ResponseBody ApiResponse deleteMyAccountFavourites(HttpServletRequest request){          
      
        String resultType = "failure";
       
         try{
            Customers customer = RequestUtil.getCurrentCustomer(request);
            if(customer != null){                  
                  int favProductId = NumberUtils.toInt(request.getParameter("productId"));                 
                  if(favProductId<=0){
                      resultType = "invalidProduct";
                  }else{
                         boolean deleted = service.deleteFavouriteProducts(customer.getCustomersId(), favProductId);
                         if(deleted){
                             resultType="success";                             
                         }else{
                             resultType="failure";
                         }
                     }                                                 

            }else{
                resultType="login";
            }              
        }catch(Exception e){
            resultType="failure";
        }        
       
        return new ApiResponse(resultType, "deleteFavProduct", request.getParameter("productId")); 
    }
}
