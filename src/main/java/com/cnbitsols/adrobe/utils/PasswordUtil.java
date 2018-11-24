/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cnbitsols.adrobe.utils;

/**
 *
 * @author sunil.t
 */
public class PasswordUtil {

    public static final int MIN_LENGTH = 4;
    protected static java.util.Random r = new java.util.Random();
    protected static char[] goodChar = {
        // Comment out next two lines to make upper-case-only, then
        // use String toUpper() on the user's input before validating.
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm', 'n',
        'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',};
    
     protected static char[] goodNum = {
        // Comment out next two lines to make upper-case-only, then
        // use String toUpper() on the user's input before validating.
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',};

    /*
     * Generate a Password object with a random password.
     */
    public static String getNext() {
        return getNext(MIN_LENGTH);
    }

    /*
     * Generate a Password object with a random password.
     */
    public static String getNext(int length) {
        if (length < 1) {
            throw new IllegalArgumentException(
                    "Ridiculous password length " + length);
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            sb.append(goodNum[r.nextInt(goodNum.length)]);
        }
        return sb.toString();
    }
}
