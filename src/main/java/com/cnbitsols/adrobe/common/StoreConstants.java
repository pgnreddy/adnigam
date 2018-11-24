/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cnbitsols.adrobe.common;

/**
 *
 * @author santosh.r
 */
public class StoreConstants {
    public static final String CART = "CART";
    public static final String CUSTOMER = "CUSTOMER";
    public static final String CURRENCYID ="CURRENCYID";
    public static final String USER ="USER";
    
    public enum ApiErrorCodes {
       SUCCESS("0000", "SUCCESS"), 
        //Information codes 1000-1049
	    INVALID_CREDENTIALS("1000","INVALID CREDENTIALS"),
	    EMAIL_ALREADY_REGISTER("1010","email Is already existed"),
	    USER_ALREADY_EXISTED_WITH_MOBILE("1001","MOBILE IS  ALREADY EXISTED"),
	    REGISTRATION_FAIL("1011","Registration Fail"),
	    VERIFY_EMAILADDRESS("1100"," PLEASE VERIFY YOUR EMAIL. "),
	    VERIFY_MOBILENUMBER("1111","PLEASE VERIFY YOUR MOBILE BY OTP. "),
	    PRODUCT_ALREDY_REGISTER("1002","PRODUCTID  is  already existed"),
	    No_RECORDS_FOUND("1020","NO RECORDS FOUND.."),
	    USER_VERIFICATION_FAIL("1021","VERIFICATION FAILED"),
	    
	    //Exception Codes or Error codes
	    DATABASE_QUERY_ERROR("001","DATABASE QUERY PROBLEM"),
	    INTERNEL_PROBLEM("111","INTERNAL Problem");
	    
	    public String status;
	    public String desc;

	    private ApiErrorCodes(String status, String desc) {
	        this.status = status;
	        this.desc = desc;
	    }

	    public String getStatus() {
	        return this.status;
	    }

	    public String getDesc() {
	        return this.desc;
	    }
    }
}
