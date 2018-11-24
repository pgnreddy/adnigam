/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cnbitsols.adrobe.utils;

/**
 *
 * @author rkumar
 */
public class PinUtil {
    
    
    
    public static String generatePIN() {
        int pin = (int) (Math.random() * 9000) + 1000;
        return String.valueOf(pin);
    }
}
