/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cnbitsols.adrobe.services.impl;

import com.cnbitsols.adrobe.dto.Cart;
import com.cnbitsols.adrobe.dto.Payment;
import com.cnbitsols.adrobe.entities.Orders;
import com.cnbitsols.adrobe.services.PaymentInterface;
import com.cnbitsols.adrobe.utils.SettingsUtil;
import com.cnbitsols.adrobe.utils.Util;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author santosh.r
 */
public class Pay4uPaymentService implements PaymentInterface {

    private static final Logger LOG = LoggerFactory.getLogger(Pay4uPaymentService.class);
    public static final String ID = "pay4u";

    @Override
    public Payment getPaymentDetails(Cart cart, HttpServletRequest request, boolean app) {

        Payment payment = new Payment();
        payment.setId(ID);
        payment.setModuleName("Pay4u");
        payment.setTitle("e-Payments(in INR)");

        return payment;
    }

    public boolean empty(String s) {
        return s == null || s.trim().equals("");
    }

    public String hashCal(String type, String str) {
        byte[] hashseq = str.getBytes();
        StringBuilder hexString = new StringBuilder();
        try {
            MessageDigest algorithm = MessageDigest.getInstance(type);
            algorithm.reset();
            algorithm.update(hashseq);
            byte messageDigest[] = algorithm.digest();

            for (int i = 0; i < messageDigest.length; i++) {
                String hex = Integer.toHexString(0xFF & messageDigest[i]);
                if (hex.length() == 1) {
                    hexString.append("0");
                }
                hexString.append(hex);
            }

        } catch (NoSuchAlgorithmException nsae) {
        }

        return hexString.toString();

    }

    @Override
    public Payment getPaymentGatewayDetails(Cart cart, Orders order, HttpServletRequest request, boolean app) {

        Payment payment = getPaymentDetails(cart, request, app);

        HashMap map = new HashMap();
        map.put("key", SettingsUtil.getSettings("PAY4U_MERCHANT_KEY"));
        map.put("txnid", String.valueOf(cart.getOrderId()));
        map.put("service_provider", "payu_paisa");
        map.put("udf2", String.valueOf(cart.getOrderId()));
        map.put("amount", Util.getRoundPrice(cart.getTotalprice()));
        map.put("firstname", order.getCustomersName());
        map.put("email", order.getCustomersEmailAddress());
        map.put("phone", order.getCustomersTelephone());
        map.put("productinfo", "productinfo");

        String domain = request.getRequestURL().toString();
        String contextpath = request.getContextPath();
        String requestURI = request.getRequestURI();
        requestURI = requestURI.substring(requestURI.indexOf(contextpath) + contextpath.length());
        String baseurl = domain.substring(0, domain.indexOf(requestURI));
        //baseurl = baseurl + contextpath;

        String callback_url = baseurl + "/checkout/paymentConfirmation";
        
        if(app){
            callback_url = baseurl + "/api/consumer/paymentConfirmation";
        }
        map.put("surl", callback_url);
        map.put("furl", callback_url);

        map.put("address1", order.getCustomersStreetAddress());
        map.put("address2", order.getCustomersSuburb());
        map.put("city", order.getCustomersCity());
        map.put("state", order.getCustomersState());
        map.put("country", order.getCustomersCountry());
        map.put("zipcode", order.getCustomersPostcode());

        String hashSequence = "key|txnid|amount|productinfo|firstname|email|udf1|udf2|udf3|udf4|udf5|udf6|udf7|udf8|udf9|udf10";

        String[] hashVarSeq = hashSequence.split("\\|");
        String hashString = "";
        for (String part : hashVarSeq) {
            hashString = (empty((String) map.get(part))) ? hashString.concat("") : hashString.concat((String) map.get(part));
            hashString = hashString.concat("|");
        }
        hashString = hashString.concat(SettingsUtil.getSettings("PAY4U_MERCHANT_SALT"));

        String hash = hashCal("SHA-512", hashString);

        map.put("hash", hash);

        payment.setDetails(map);
        payment.setRedirectUrl(SettingsUtil.getSettings("PAY4U_GATEWAY_URL"));
        return payment;
    }

    @Override
    public Map<String, Object> parsePaymentResponse(Orders order, Cart cart, Map<String, String> requestMap, HttpServletRequest request) {
       
        Payment payment = cart.getPayment();
        HashMap map = payment.getDetails();

        Map<String, Object> paymentResponeMap = new HashMap<>();

        paymentResponeMap.put("orderId", order.getOrdersId());
        paymentResponeMap.put("timestamp", new Date());
        paymentResponeMap.put("transactionId", requestMap.get("mihpayid"));
        paymentResponeMap.put("status", "success");
        paymentResponeMap.put("reason", "");
        paymentResponeMap.put("request", requestMap);

        if (map != null) {

            if (!"success".equalsIgnoreCase(requestMap.get("status"))) {
                paymentResponeMap.put("status", "failed");
                paymentResponeMap.put("reason", requestMap.get("Error"));
            } else if (!(order.getOrdersId() + "").equals(requestMap.get("txnid"))) {
                paymentResponeMap.put("status", "failed");
                paymentResponeMap.put("reason", "TransactionIds does not match");
            } else if (cart.getTotalprice()!=Double.parseDouble(requestMap.get("amount"))) {
                paymentResponeMap.put("status", "failed");
                paymentResponeMap.put("reason", "Amounts does not match");
            }else {
                map.put("amount",requestMap.get("amount"));

                String hashSequence = "udf10|udf9|udf8|udf7|udf6|udf5|udf4|udf3|udf2|udf1|email|firstname|productinfo|amount|txnid|key";

                String[] hashVarSeq = hashSequence.split("\\|");
                String hashString = SettingsUtil.getSettings("PAY4U_MERCHANT_SALT").concat("|").concat(requestMap.get("status"));
                for (String part : hashVarSeq) {
                    hashString = hashString.concat("|");
                    hashString = (empty((String) map.get(part))) ? hashString.concat("") : hashString.concat((String) map.get(part));

                }

                String generatedhash = hashCal("SHA-512", hashString);
                String requesthash = requestMap.get("hash");

                if (!generatedhash.equals(requesthash)) {
                    LOG.warn("Invalid payment response : hash value doesnot match");
                    paymentResponeMap.put("status", "failed");
                    paymentResponeMap.put("reason", "Invalid payment response : hash value doesnot match");

                }

            }

        } else {
            paymentResponeMap.put("status", "failed");
            paymentResponeMap.put("reason", "Invalid request : payment details missing");
        }

        return paymentResponeMap;
    }

    
}
