/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cnbitsols.adrobe.services.impl;

import com.cnbitsols.adrobe.dao.AdrobeHibernateDAO;
import com.cnbitsols.adrobe.dto.MessageStatus;
import com.cnbitsols.adrobe.entities.Customers;
import com.cnbitsols.adrobe.entities.EmailQueue;
import com.cnbitsols.adrobe.entities.Orders;
import com.cnbitsols.adrobe.entities.OrdersProducts;
import com.cnbitsols.adrobe.entities.OrdersProductsStatusHistory;
import com.cnbitsols.adrobe.entities.ProductCoupon;
import com.cnbitsols.adrobe.entities.SmsQueue;
import com.cnbitsols.adrobe.entities.UserRequests;
import com.cnbitsols.adrobe.entities.Vendor;
import com.cnbitsols.adrobe.services.AdrobeService;
import com.cnbitsols.adrobe.services.MessagingService;
import com.cnbitsols.adrobe.utils.SettingsUtil;

import com.cnbitsols.adrobe.utils.Util;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.app.VelocityEngine;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author santosh.r
 */
@Service("messagingServiceImpl")
public class MessagingServiceImpl implements MessagingService {

    private static final Logger log = LoggerFactory.getLogger(MessagingServiceImpl.class);

    @Autowired
    private MessageSource messageSource = null;
    @Autowired
    private AdrobeHibernateDAO hdao = null;
    @Autowired
    private VelocityEngine velocityEngine = null;
    @Autowired
    private AdrobeService service = null;

    //@Autowired
    //private JavaMailSender mailSender = null;
    public MessagingServiceImpl() {
        System.out.println("**********MessagingServiceImpl constructor********");
    }

    @Override
    public void sendRegistrationMessage(Customers customer) {
        Map model = new HashMap();
        model.put("customer", customer);
        String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "Registration.vm", "UTF-8", model).trim();

        Date now = new Date();
        EmailQueue eMessage = new EmailQueue(customer.getCustomersId(), now, now, MessageStatus.PENDING.status(), SettingsUtil.getSettings("EMAIL_SENDER"), customer.getEmailAddress(), null, messageSource.getMessage("registration.email.subject", null, Locale.getDefault()), text);
        hdao.save(eMessage);

        model = new HashMap();
        model.put("customer", customer);
        model.put("type", "registration");

        text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "SMS.vm", "UTF-8", model).trim();

        SmsQueue sMessage = new SmsQueue(customer.getCustomersId(), now, now, MessageStatus.PENDING.status(), customer.getTelephone(), text);
        hdao.save(sMessage);
    }

    @Override
    public boolean sendForgotPasswordMessage(Customers customer) {
        Map model = new HashMap();
        model.put("customer", customer);
        String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "ForgotPassword.vm", "UTF-8", model).trim();

        Date now = new Date();
        EmailQueue eMessage = new EmailQueue(customer.getCustomersId(), now, now, MessageStatus.PENDING.status(), SettingsUtil.getSettings("EMAIL_SENDER"), customer.getEmailAddress(), null, messageSource.getMessage("forgotPassword.email.subject", null, Locale.getDefault()), text);
        hdao.save(eMessage);

        model = new HashMap();
        model.put("customer", customer);
        model.put("type", "forgotPassword");

        text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "SMS.vm", "UTF-8", model).trim();

        SmsQueue sMessage = new SmsQueue(customer.getCustomersId(), now, now, MessageStatus.PENDING.status(), customer.getTelephone(), text);
        hdao.save(sMessage);

        return true;
    }

    @Override
    public boolean sendVendorForgotPasswordMessage(Vendor vendor) {
        Map model = new HashMap();
        model.put("vendor", vendor);
        String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "VendorForgotPassword.vm", "UTF-8", model).trim();

        Date now = new Date();
        EmailQueue eMessage = new EmailQueue(vendor.getVendorID(), now, now, MessageStatus.PENDING.status(), SettingsUtil.getSettings("EMAIL_SENDER"), vendor.getEmail(), null, messageSource.getMessage("forgotPassword.email.subject", null, Locale.getDefault()), text);
        hdao.save(eMessage);

        model = new HashMap();
        model.put("vendor", vendor);
        model.put("type", "vendorForgotPassword");

        text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "SMS.vm", "UTF-8", model).trim();

        SmsQueue sMessage = new SmsQueue(vendor.getVendorID(), now, now, MessageStatus.PENDING.status(), vendor.getPhone(), text);
        hdao.save(sMessage);

        return true;
    }

    @Override
    public void sendOrderConfirmationMessage(Customers customer, Orders order, String status) {
        Map model = new HashMap();
        model.put("customer", customer);
        model.put("order", order);
        model.put("status", status);
        model.put("util", Util.getInstance());
        List<ProductCoupon> productCoupons = new ArrayList<>();
        
        for (OrdersProducts op : order.getOrderProducts()) {
            if (op.getCoupon() == 1) {
                ProductCoupon productCoupon = service.getProductCouponsByOrderIdAndProductId(order.getOrdersId(), op.getProductsId());
                productCoupons.add(productCoupon);
            }
        }
        
        model.put("productCoupons", productCoupons);

        String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "OrderConfirmation.vm", "UTF-8", model).trim();

        Date now = new Date();
        EmailQueue eMessage = new EmailQueue(customer.getCustomersId(), now, now, MessageStatus.PENDING.status(), SettingsUtil.getSettings("EMAIL_SENDER"), customer.getEmailAddress(), null, messageSource.getMessage("email.order.confirmation", null, Locale.getDefault()), text);
        hdao.save(eMessage);

        model = new HashMap();
        model.put("customer", customer);
        model.put("order", order);
        model.put("status", status);
        model.put("type", "orderConfirmation");
        model.put("productCoupons", productCoupons);
        
        text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "SMS.vm", "UTF-8", model).trim();

        SmsQueue sMessage = new SmsQueue(customer.getCustomersId(), now, now, MessageStatus.PENDING.status(), customer.getTelephone(), text);
        hdao.save(sMessage);

    }

    @Override
    public void sendVendorAdditionRequest(Map inputdata) {
        Map model = new HashMap();
        model.putAll(inputdata);

        String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "vendorSignupRequest.vm", "UTF-8", model).trim();
        // System.out.println(text);
        Date now = new Date();
        EmailQueue eMessage = new EmailQueue(1, now, now, MessageStatus.PENDING.status(), SettingsUtil.getSettings("EMAIL_SENDER"), SettingsUtil.getSettings("EMAIL_SENDER"), null, messageSource.getMessage("email.vendor.signup", null, Locale.getDefault()), text);
        hdao.save(eMessage);
    }

    @Override
    public void sendVendorMessage(Vendor vendor, String message) {
        Map model = new HashMap();
        model.put("vendor", vendor);
        model.put("message", message);

        String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "vendorMessageToAdmin.vm", "UTF-8", model).trim();
        //System.out.println(text);
        Date now = new Date();
        String subject = messageSource.getMessage("email.vendor.message", new Object[]{vendor.getName()}, Locale.getDefault());

        EmailQueue eMessage = new EmailQueue(vendor.getVendorID(), now, now, MessageStatus.PENDING.status(), SettingsUtil.getSettings("EMAIL_SENDER"), SettingsUtil.getSettings("EMAIL_SENDER"), null, subject, text);
        hdao.save(eMessage);
    }

    @Override
    public void sendUserRequestMessage(Vendor v, UserRequests u, OrdersProducts orp, Customers customer) {
        Map model = new HashMap();
        model.put("customer", customer);
        model.put("vendor", v);
        model.put("userRequest", u);
        model.put("orderProduct", orp);
        model.put("type", "userRequest");
        String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "SMS.vm", "UTF-8", model).trim();

        Date now = new Date();
        SmsQueue sMessage = new SmsQueue(v.getVendorID(), now, now, MessageStatus.PENDING.status(), v.getPhone(), text);
        hdao.save(sMessage);
    }

    @Override
    public void sendRegistrationDetailsToVendor(Vendor vendor) {
        Map model = new HashMap();
        model.put("vendor", vendor);;
        model.put("type", "vendorRegistration");
        String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "SMS.vm", "UTF-8", model).trim();

        Date now = new Date();
        SmsQueue sMessage = new SmsQueue(vendor.getVendorID(), now, now, MessageStatus.PENDING.status(), vendor.getPhone(), text);
        hdao.save(sMessage);
    }

    @Override
    public void sendVendorOrderNotification(Orders order, OrdersProducts op, Vendor vendor) {
        Map model = null;

        Date now = new Date();
        if (StringUtils.isNotBlank(vendor.getEmail())) {
            model = new HashMap();
            model.put("orderProduct", op);
            model.put("order", order);
            model.put("vendor", vendor);
            model.put("util", Util.getInstance());

            String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "VendorOrderNotification.vm", "UTF-8", model).trim();
            log.debug(text);
            EmailQueue eMessage = new EmailQueue(vendor.getVendorID(), now, now, MessageStatus.PENDING.status(), SettingsUtil.getSettings("EMAIL_SENDER"), vendor.getEmail(), null, messageSource.getMessage("email.order.vendor.notification", null, Locale.getDefault()), text);
            hdao.save(eMessage);
        }

        if (StringUtils.isNotBlank(vendor.getPhone())) {
            model = new HashMap();
            model.put("orderProduct", op);
            model.put("order", order);
            model.put("vendor", vendor);
            model.put("util", Util.getInstance());
            model.put("type", "vendorOrderNotification");

            String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "SMS.vm", "UTF-8", model).trim();

            SmsQueue sMessage = new SmsQueue(vendor.getVendorID(), now, now, MessageStatus.PENDING.status(), vendor.getPhone(), text);
            hdao.save(sMessage);
        }
    }

    @Override
    public void sendOrdersProductStatusChangeMessage(Orders orders, OrdersProducts op, OrdersProductsStatusHistory history) {
        /*Map model = null;

        Date now = new Date();
        if (StringUtils.isNotBlank(vendor.getEmail())) {
            model = new HashMap();
            model.put("orderProduct", op);
            model.put("order", order);
            model.put("vendor", vendor);
            model.put("util",Util.getInstance());

            
            String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "VendorOrderNotification.vm", "UTF-8", model).trim();
            log.debug(text);
            EmailQueue eMessage = new EmailQueue(vendor.getVendorID(), now, now, MessageStatus.PENDING.status(), SettingsUtil.getSettings("EMAIL_SENDER"), vendor.getEmail(), null, messageSource.getMessage("email.order.vendor.notification", null, Locale.getDefault()), text);
            hdao.save(eMessage);
        }

        if (StringUtils.isNotBlank(vendor.getPhone())) {
            model = new HashMap();
            model.put("orderProduct", op);
            model.put("order", order);
            model.put("vendor", vendor);
            model.put("util",Util.getInstance());
            model.put("type", "vendorOrderNotification");

            String text =  VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "SMS.vm", "UTF-8", model).trim();

            SmsQueue sMessage = new SmsQueue(vendor.getVendorID(), now, now, MessageStatus.PENDING.status(), vendor.getPhone(), text);
            hdao.save(sMessage);
        }
         */
    }

    @Override
    public void sendOrderAcceptedMessage() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public synchronized void processEmailMessagesQueue() {
        log.debug("Email processing job...started");
        List<EmailQueue> emails = null;
        do {
            emails = hdao.getPendingEmails();
            log.debug("inside do while email");
            if (emails != null && emails.size() > 0) {
                for (EmailQueue email : emails) {
                    sendEmail(email);
                }
            }
        } while (emails != null && emails.size() > 0);
        log.debug("Email processing job...end");

    }

    public synchronized void processSMSMessagesQueue() {
        log.debug("sms processing job...started");
        List<SmsQueue> messages = null;
        do {
            messages = hdao.getPendingSMSs();
            log.debug("inside do while sms");
            if (messages != null && messages.size() > 0) {
                for (SmsQueue sms : messages) {
                    sendSMS(sms);
                }
            }
        } while (messages != null && messages.size() > 0);

        log.debug("sms processing job...started");
    }

    /*private void sendEmail(final EmailQueue email) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            @SuppressWarnings({"rawtypes", "unchecked"})
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                message.setTo(email.getTo());
                if (StringUtils.isNotBlank(email.getBcc())) {
                    message.setBcc("adrianmatei@gmail.com");
                }
                message.setFrom(new InternetAddress(email.getFrom()));
                message.setSubject(email.getSubject());
                //message.setSentDate(new Date());
                message.setText(email.getMessage(), true);
            }
        };

        int status = MessageStatus.SUBMITTED.status();
        try {
            mailSender.send(preparator);
        } catch (MailException e) {
            log.error("Error sending email", e);
            status = MessageStatus.FAILED.status();
        }

        email.setStatus(status);
        email.setUpdatedDate(new Date());
        hdao.save(email);
    }*/
    private void sendEmail(final EmailQueue email) {

        final String uri =SettingsUtil.getSettings("EMAIL_URL");
        String payloadstring ="";        
        
        //old:username=skybellmailapi&password=sky12345&mailfrom=info@adnigam.com&filepath=&filename=&replyto=info@adnigam.com&name=ADNIGAM&templateid=
       //new:http://skybellemail.skybelltech.in/send-email-json-otom/1286d3b0-0026-44d0-a8ce-3536b6209e97/1007/
			//        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		//        params.add("receiver", email.getTo());
		//        params.add("subject", email.getSubject());
		//        params.add("message", email.getMessage());
		//        RestTemplate restTemplate = new RestTemplate();
		//
		//        int status = MessageStatus.SUBMITTED.status();
		//        String result = "";
		//        try {
		//            result = restTemplate.postForObject(uri, params, String.class);
		//        } catch (RestClientException e) {
		//            log.error("Error sending email :", e);
		//            status = MessageStatus.FAILED.status();
		//        }
		//
		//        email.setResponse(result);
		//        email.setStatus(status);
		//        email.setUpdatedDate(new Date());
		//        hdao.save(email);
		JSONArray recepientsArray =new JSONArray();
		JSONObject recepient0 = new JSONObject();
		recepient0.put("email_id", email.getTo());
		//recepient0.put("name", "customerName");
		recepientsArray.put(recepient0);
		JSONObject payloadbody = new JSONObject();
		payloadbody.put("html_content",email.getMessage());
		payloadbody.put( "subject", email.getSubject());
		payloadbody.put( "from_name","Adnigam" );
		payloadbody.put( "from_mail",email.getFrom());
		payloadbody.put( "reply_to",email.getFrom());
		payloadbody.put( "to_recipients",recepientsArray);
		JSONObject payload = new JSONObject();
		payload.put( "message",payloadbody );
		JSONObject payloadContainer = new JSONObject(); 
		payloadContainer.put( "mail_datas",payload );
		payloadstring = payloadContainer.toString();

		int status = MessageStatus.SUBMITTED.status();
        String result = "";
        try {
            result = sendPostRequest(uri, payloadstring);
        } catch (RestClientException e) {
            log.error("Error sending email :", e);
            status = MessageStatus.FAILED.status();
        }

        email.setResponse(result);
        email.setStatus(status);
        email.setUpdatedDate(new Date());
        hdao.save(email);


    }

    private void sendSMS(SmsQueue sms) {
        final String uri = SettingsUtil.getSettings("SMS_URL");
        Map<String, String> params = new HashMap<>();
        params.put("to", sms.getMsisdn());
        params.put("message", sms.getMessage());
        RestTemplate restTemplate = new RestTemplate();

        int status = MessageStatus.SUBMITTED.status();
        String result = "";
        try {
            result = restTemplate.getForObject(uri, String.class, params);
        } catch (Exception e) {
            log.error("Error sending sms :", e);
            status = MessageStatus.FAILED.status();
        }

        sms.setResponse(result);
        sms.setStatus(status);
        sms.setUpdatedDate(new Date());
        hdao.save(sms);
    }

    @Override
    public void sendCouponVerificationPIN(Customers customer, String pin) {
        Map<String, Object> model = new HashMap<>();
        model.put("type", "sendPin");
        model.put("pin", pin);
        String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "SMS.vm", "UTF-8", model).trim();
        Date now = new Date();
        SmsQueue sMessage = new SmsQueue(customer.getCustomersId(), now, now, MessageStatus.PENDING.status(), customer.getTelephone(), text);
        hdao.save(sMessage);
    }

public  String sendPostRequest(String requestUrl, String payload) {
StringBuffer jsonString = new StringBuffer();
try
{
URL url = new URL(requestUrl);
HttpURLConnection connection = (HttpURLConnection) url.openConnection();
connection.setDoInput(true);
connection.setDoOutput(true);
connection.setRequestMethod("GET");
connection.setRequestProperty("Accept", "application/json");
connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
writer.write(payload);
writer.close();
BufferedReader br = new BufferedReader(new
InputStreamReader(connection.getInputStream()));
String line;
while ((line = br.readLine()) != null) {
jsonString.append(line);
}
br.close();
connection.disconnect();
} catch (Exception e) {
      log.error("error"+e);
throw new RuntimeException(e.getMessage());
}
return jsonString.toString();
}
    
}
