/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cnbitsols.adrobe.utils;

//import com.ccavenue.security.AesCryptUtil;
import com.cnbitsols.adrobe.common.StoreConstants;
import com.cnbitsols.adrobe.dto.Cart;
import com.cnbitsols.adrobe.entities.Customers;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.beanutils.BeanUtils;

/**
 *
 * @author santosh
 */
public class RequestUtil {

    private static Integer defaultCurrencyId = 3;

    public static Customers getCurrentCustomer(HttpServletRequest request) {
        return (Customers) request.getSession().getAttribute(StoreConstants.USER);
    }

    public static Integer getCustomerId(HttpServletRequest request) {
        return getCustomerId(getCurrentCustomer(request));
    }

    public static Integer getCustomerId(Customers customer) {
        if (customer != null) {
            return customer.getCustomersId();
        }

        return null;
    }

    public static void populate(Object bean, HttpServletRequest request) {
        HashMap map = new HashMap();
        Enumeration names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            map.put(name, request.getParameterValues(name));
        }
        try {
            BeanUtils.populate(bean, map);
        } catch (Exception e) {
        }
    }

    public static Cart getCart(HttpServletRequest request) {
        Cart cart = null;

        cart = (Cart) request.getSession().getAttribute(StoreConstants.CART);
        if (cart == null) {
            cart = new Cart();
            request.getSession().setAttribute(StoreConstants.CART, cart);
        }

        return cart;
    }

    public static Map<String, String> getRequestMap(HttpServletRequest request, boolean ccAvenue) {
        Enumeration paramNames = request.getParameterNames();
        Map<String, String> params = new HashMap<>();

        if (ccAvenue) {
            String workingKey = SettingsUtil.getSettings("MODULE_PAYMENT_CCAVENUE_WORKING_KEY");
            //AesCryptUtil aesUtil = new AesCryptUtil(workingKey);
            String decResp = "";//aesUtil.decrypt(request.getParameter("encResp"));
            StringTokenizer tokenizer = new StringTokenizer(decResp, "&");
            String pair, pname, pvalue;

            while (tokenizer.hasMoreTokens()) {
                pair = (String) tokenizer.nextToken();
                if (pair != null) {
                    StringTokenizer strTok = new StringTokenizer(pair, "=");
                    pname = "";
                    pvalue = "";
                    if (strTok.hasMoreTokens()) {
                        pname = (String) strTok.nextToken();
                        if (strTok.hasMoreTokens()) {
                            pvalue = (String) strTok.nextToken();
                        }
                        params.put(pname, pvalue);
                    }
                }
            }
        } else {
            while (paramNames.hasMoreElements()) {
                String paramName = (String) paramNames.nextElement();

                String paramValue = request.getParameter(paramName);

                params.put(paramName, paramValue);
            }

        }

        return params;
    }
}
