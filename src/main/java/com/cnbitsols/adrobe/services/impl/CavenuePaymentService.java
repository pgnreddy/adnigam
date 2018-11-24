/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cnbitsols.adrobe.services.impl;

import com.cnbitsols.adrobe.dto.Cart;
import com.cnbitsols.adrobe.dto.Payment;
import com.cnbitsols.adrobe.entities.Orders;
import com.cnbitsols.adrobe.services.PaymentInterface;
import com.cnbitsols.adrobe.utils.SettingsUtil;
import com.cnbitsols.adrobe.utils.Util;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
//import com.ccavenue.security.AesCryptUtil;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author santosh
 */
public class CavenuePaymentService implements PaymentInterface {

    private static final Log LOGGER = LogFactory.getLog(CavenuePaymentService.class);
    public static final String ID = "cavenue";

    @Override
    public Payment getPaymentDetails(Cart cart, HttpServletRequest request, boolean app) {
        LOGGER.debug("Inside getpayment details...****");
        Payment payment = new Payment();
        payment.setId(ID);
        payment.setModuleName("CCAvenue Credit Card");
        payment.setTitle("e-Payments(in INR)");
        payment.setMerchantId(SettingsUtil.getSettings("MODULE_PAYMENT_CCAVENUE_MERCHANT_ID"));
        payment.setRedirectUrl(SettingsUtil.getSettings("MODULE_PAYMENT_CCAVENUE_TEST_URL"));   // set secure url later
        payment.setAccessCode(SettingsUtil.getSettings("MODULE_PAYMENT_CCAVENUE_ACCESS_CODE"));
        payment.setWorkingKey(SettingsUtil.getSettings("MODULE_PAYMENT_CCAVENUE_WORKING_KEY"));
//        if (app) {
//            payment.setRedirectUrl(SettingsUtil.getSettings("MODULE_APP_PAYMENT_CALLBACK_URL"));
//        } else {
//            payment.setRedirectUrl(SettingsUtil.getSettings("MODULE_PAYMENT_CALLBACK_URL"));
//        }
        String checksum = null;//getChecksum(SettingsUtil.getSettings("MODULE_PAYMENT_CCAVENUE_MERCHANT_ID"), order.getCheckoutOrderId(), order.getTotalInStr(), SettingsUtil.getSettings("MODULE_PAYMENT_CCAVENUE_WORKING_KEY"), null, payment.getRedirectUrl());
        payment.setCheckSum(checksum);

        //remove this
        String testurl = null;//printCallbackurl(SettingsUtil.getSettings("MODULE_PAYMENT_CCAVENUE_MERCHANT_ID"), order.getCheckoutOrderId(), order.getTotalInStr(), SettingsUtil.getSettings("MODULE_PAYMENT_CCAVENUE_WORKING_KEY"), null, payment.getRedirectUrl());
        payment.setTestUrl(testurl);
        return payment;
    }

    @Override
    public Map<String, Object> parsePaymentResponse(Orders order, Cart cart, Map<String, String> requestMap, HttpServletRequest request) {
        Payment payment = cart.getPayment();
        HashMap map = payment.getDetails();

        Map<String, Object> paymentResponeMap = new HashMap<>();

        paymentResponeMap.put("orderId", order.getOrdersId());
        paymentResponeMap.put("timestamp", new Date());
        paymentResponeMap.put("transactionId", requestMap.get("tracking_id"));
        paymentResponeMap.put("status", "success");
        paymentResponeMap.put("reason", "");
        paymentResponeMap.put("request", requestMap);
        
        if (map != null) {

            if (!"success".equalsIgnoreCase(requestMap.get("order_status"))) {
                paymentResponeMap.put("status", "failed");
                paymentResponeMap.put("reason", requestMap.get("failure_message"));
            } else if (!(order.getOrdersId() + "").equals(requestMap.get("order_id"))) {
                paymentResponeMap.put("status", "failed");
                paymentResponeMap.put("reason", "TransactionIds does not match");
            } else if (cart.getTotalprice()!=Double.parseDouble(requestMap.get("amount"))) {
                paymentResponeMap.put("status", "failed");
                paymentResponeMap.put("reason", "Amounts does not match");
            }else {
                map.put("amount",requestMap.get("amount"));
            }

        } else {
            paymentResponeMap.put("status", "failed");
            paymentResponeMap.put("reason", "Invalid request : payment details missing");
        }

        return paymentResponeMap;
    }

    @Override
    public Payment getPaymentGatewayDetails(Cart cart, Orders order, HttpServletRequest request, boolean app) {
        Payment payment = getPaymentDetails(cart, request, app);
        HashMap<String, Object> map = new HashMap<>();
        map.put("merchant_id", payment.getMerchantId());
        map.put("order_id", cart.getOrderId());
        map.put("currency", "INR");
        map.put("amount", Util.getRoundPrice(cart.getTotalprice()));

        String domain = request.getRequestURL().toString();
        String contextpath = request.getContextPath();
        String requestURI = request.getRequestURI();
        requestURI = requestURI.substring(requestURI.indexOf(contextpath) + contextpath.length());
        String baseurl = domain.substring(0, domain.indexOf(requestURI));
        String callBackUrl;
        if (app) {
            callBackUrl =  baseurl + "/api/consumer/paymentConfirmation";
        } else {
            callBackUrl = baseurl + "/checkout/paymentConfirmation";
        }
        
        map.put("redirect_url", callBackUrl);
        map.put("cancel_url", callBackUrl);
        map.put("language", "en");
        map.put("billing_name", order.getCustomersName());
        map.put("billing_email", order.getCustomersEmailAddress());
        
        if (order.getBillingStreetAddress() != null)
            map.put("billing_address", order.getBillingStreetAddress());
        if (order.getBillingCity() != null)
            map.put("billing_city", order.getBillingCity());
        if (order.getBillingState() != null)
            map.put("billing_state", order.getBillingState());
        if (order.getBillingPostcode() != null)
            map.put("billing_zip", order.getBillingPostcode());
        if (order.getBillingCountry() != null)
            map.put("billing_country", order.getBillingCountry());
        if (order.getDeliveryName()!=null) 
            map.put("delivery_name", order.getDeliveryName());
        if (order.getDeliveryStreetAddress() != null)
            map.put("delivery_address", order.getDeliveryStreetAddress());
        if (order.getDeliveryCity()!=null)
            map.put("delivery_city", order.getDeliveryCity());
        if (order.getDeliveryState() != null)
            map.put("delivery_state", order.getDeliveryState());
        if (order.getDeliveryPostcode() != null)
            map.put("delivery_zip", order.getDeliveryPostcode());
        if (order.getDeliveryCountry() != null)
            map.put("delivery_country", order.getDeliveryCountry());
        if (order.getCustomersTelephone() != null) {
            map.put("billing_tel", order.getCustomersTelephone());
            map.put("delivery_tel", order.getCustomersTelephone());
        }
        
        String ccRequest = "", pname, pvalue;
        
        Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
        
        while (iterator.hasNext()) {
            Map.Entry<String, Object> next = iterator.next();
            pname = next.getKey();
            pvalue = next.getValue().toString();
            ccRequest += pname + "=" + pvalue + "&";
        }
        
        LOGGER.debug(ccRequest);
        //AesCryptUtil aesUtil = new AesCryptUtil(payment.getWorkingKey());
        String encRequest = "";//aesUtil.encrypt(ccRequest);
        payment.setEncRequest(encRequest);
        payment.setDetails(map);
        LOGGER.debug("encRequest: " + encRequest);

        return payment;
    }

}
