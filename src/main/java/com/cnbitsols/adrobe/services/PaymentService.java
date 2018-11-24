/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cnbitsols.adrobe.services;

import com.cnbitsols.adrobe.dto.Cart;
import com.cnbitsols.adrobe.dto.Payment;
import com.cnbitsols.adrobe.entities.Orders;
import com.cnbitsols.adrobe.services.impl.CavenuePaymentService;
import com.cnbitsols.adrobe.services.impl.Pay4uPaymentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author santosh
 */
public class PaymentService {
    
    private static final Log LOGGER = LogFactory.getLog(PaymentService.class);

    
    public PaymentService(){}
    
    public List<Payment> getAvailableServices(){
        ArrayList<Payment> paymentModules = new ArrayList<Payment>();
        
        Payment payment = new Payment();
        payment.setId("ccav");
        payment.setModuleName("CCAvenue Credit Card");
        payment.setTitle("e-Payments(in INR)");
        paymentModules.add(payment);    
        payment = new Payment();
        payment.setId("cod");
        payment.setModuleName("Cash On Delivery");
        payment.setTitle("Cash On Delivery");
        paymentModules.add(payment);  
        return paymentModules;
    }
    
    public Payment getPayment(String id,Cart cart,HttpServletRequest request,boolean app){
        LOGGER.debug("inside getPayment...*****" + id);
        if(id.equalsIgnoreCase("cod"))
        {
            Payment payment = new Payment();
            payment.setId("cod");
            //payment.setModuleName("Cash On Delivery");
            payment.setTitle("Cash On Delivery");
            return payment;
        }else if(id.equalsIgnoreCase("ccav")){
            LOGGER.debug("Inside CCAV case....****");
            PaymentInterface service = new CavenuePaymentService();
            Payment payment = service.getPaymentDetails(cart,request,app);
            LOGGER.debug("Inside CCAV case after....****");
            return payment;
        }else if(id.equalsIgnoreCase("pay4u")){
            PaymentInterface service = new Pay4uPaymentService();
            Payment payment = service.getPaymentDetails(cart,request,app);      
            return payment;
        }
        
        return null;
        
    }

   

    public Payment getPaymentGatewayDetails(String paymentMethod, Cart cart, Orders order, HttpServletRequest request, boolean app) {
        LOGGER.debug(" getPaymentGatewayDetails - paymentMethod " + paymentMethod);
        if(paymentMethod.equalsIgnoreCase("cod"))
        {
            Payment payment = new Payment();
            payment.setId("cod");
            //payment.setModuleName("Cash On Delivery");
            payment.setTitle("Cash On Delivery");
            return payment;
        }else if(paymentMethod.equalsIgnoreCase("ccav")){
            PaymentInterface service = new CavenuePaymentService();
            Payment payment = service.getPaymentGatewayDetails(cart,order,request,app);      
            return payment;
        }else if(paymentMethod.equalsIgnoreCase("pay4u")){
            PaymentInterface service = new Pay4uPaymentService();
            Payment payment = service.getPaymentGatewayDetails(cart,order,request,app);      
            return payment;
        }
        return null;
    }

    public Map<String, Object> parsePaymentResponse(Orders order, Cart cart, Map<String, String> requestMap, HttpServletRequest request) {
         Map<String, Object> output = null;
         Payment payment = cart.getPayment();
        if(payment.getId().equalsIgnoreCase(Pay4uPaymentService.ID)){
            PaymentInterface service = new Pay4uPaymentService();
            output = service.parsePaymentResponse(order,cart,requestMap,request);      
        } else if(payment.getId().equalsIgnoreCase(CavenuePaymentService.ID)) {
            PaymentInterface service = new CavenuePaymentService();
            output = service.parsePaymentResponse(order, cart, requestMap, request);
        }
        
        return output;
    }
}
