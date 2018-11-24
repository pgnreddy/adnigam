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
public class ApiResponse {
 private String status;
 private String msg;
 private Object data;
public ApiResponse(){}
    public ApiResponse(String status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public ApiResponse(String status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public ApiResponse(String status) {
        this.status = status;
    }

    public ApiResponse(String status, Object data) {
        this.status = status;
        this.data = data;
    }

 
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
    
    
 
}
