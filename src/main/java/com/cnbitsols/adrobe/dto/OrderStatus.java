/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cnbitsols.adrobe.dto;

/**
 *
 * @author santosh.r
 */
public enum OrderStatus {
    PAYMENT_PENDING("payment pending",1),
    PROCESSING("Processing",2),
    DISPATCHED("Dispatched",3),
    CANCEL_REQUEST("Cancel Request",4),
    CANCEL_APPROVED("Cancel Approved",5),
    DELIVERED("Delivered",6),
    ORDER_PLACED("Order placed",7),
    PAYMENT_FAILED("Payment Failed",8);
    
    private final String title;   // in kilograms
    private final int status; // in meters
    
    OrderStatus(String title, int status) {
        this.title = title;
        this.status = status;
    }
    public String title() { return title; }
    public int status() { return status; }
   
}
