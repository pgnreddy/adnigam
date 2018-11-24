/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cnbitsols.adrobe.exceptions;

import com.cnbitsols.adrobe.dto.ApiResponse;

/**
 *
 * @author santosh.r
 */
public class APIException extends Exception{
    private final ApiResponse apiResponse;
    
    public APIException(ApiResponse res){
        this.apiResponse = res;
    }

    public ApiResponse getApiResponse() {
        return apiResponse;
    }
    
    
}
