/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cnbitsols.adrobe.services;

import com.cnbitsols.adrobe.dto.Cart;
import com.cnbitsols.adrobe.dto.Payment;
import com.cnbitsols.adrobe.entities.Orders;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author santosh
 */
public interface PaymentInterface {
    public static final String successResponse ="success";
    public Payment getPaymentDetails(Cart cart,HttpServletRequest request,boolean app);

   
    public Payment getPaymentGatewayDetails(Cart cart, Orders order, HttpServletRequest request, boolean app);

    public Map<String, Object> parsePaymentResponse(Orders order, Cart cart, Map<String, String> requestMap, HttpServletRequest request);
}
