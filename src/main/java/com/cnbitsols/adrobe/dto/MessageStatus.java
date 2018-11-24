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
public enum MessageStatus {
    PENDING("payment",0),
    SUBMITTED("submitted",1),
    FAILED("failed",2) ;
    
    private final String title;   // in kilograms
    private final int status; // in meters
    
    MessageStatus(String title, int status) {
        this.title = title;
        this.status = status;
    }
    public String title() { return title; }
    public int status() { return status; }
   
}
