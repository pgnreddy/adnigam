/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cnbitsols.adrobe.services;

import com.cnbitsols.adrobe.entities.Customers;
import com.cnbitsols.adrobe.entities.Orders;
import com.cnbitsols.adrobe.entities.OrdersProducts;
import com.cnbitsols.adrobe.entities.OrdersProductsStatusHistory;
import com.cnbitsols.adrobe.entities.UserRequests;
import com.cnbitsols.adrobe.entities.Vendor;
import java.util.Map;

/**
 *
 * @author santosh.r
 */
public interface MessagingService {
    public void sendRegistrationMessage(Customers customer);
    public boolean sendForgotPasswordMessage(Customers customer);
    public void sendOrderConfirmationMessage(Customers customer, Orders order, String status);

    public void sendOrderAcceptedMessage();

    public void sendUserRequestMessage(Vendor v, UserRequests u, OrdersProducts orp, Customers customer);

    public void sendRegistrationDetailsToVendor(Vendor vendor);

    public void sendVendorOrderNotification(Orders order, OrdersProducts op,Vendor vendor);
    
    public boolean sendVendorForgotPasswordMessage(Vendor vendor) ;

    public void sendOrdersProductStatusChangeMessage(Orders orders,OrdersProducts op, OrdersProductsStatusHistory history);

    public void sendVendorAdditionRequest(Map inputdata);

    public void sendVendorMessage(Vendor vendor, String message);
    
    public void sendCouponVerificationPIN(Customers customer, String pin);
}
