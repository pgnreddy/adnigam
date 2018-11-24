/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cnbitsols.adrobe.dto;

import java.util.HashMap;


public class Payment {
     private String id;
     private String moduleName;
     private String title;
     private String description;
     private double cost;
     private String merchantId;
     private String checkSum;
     private String redirectUrl;
     private String testUrl;
     private String accessCode;
     private String workingKey;
     private String encRequest;
    private HashMap details;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getModuleName() {
        return moduleName;
    }

    public String getCheckSum() {
        return checkSum;
    }

    public void setCheckSum(String checkSum) {
        this.checkSum = checkSum;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

   

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getTestUrl() {
        return testUrl;
    }

    public void setTestUrl(String testUrl) {
        this.testUrl = testUrl;
    }

    public void setDetails(HashMap map) {
        this.details = map;
    }

    public HashMap getDetails() {
        return details;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }

    public String getWorkingKey() {
        return workingKey;
    }

    public void setWorkingKey(String workingKey) {
        this.workingKey = workingKey;
    }

    public String getEncRequest() {
        return encRequest;
    }

    public void setEncRequest(String encRequest) {
        this.encRequest = encRequest;
    }

}
