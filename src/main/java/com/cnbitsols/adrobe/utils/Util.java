/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cnbitsols.adrobe.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author santosh.r
 */
public class Util {

    static Util instance = null;

    public static String toSeoString(String message) {
        if (message != null) {
            return message.replaceAll("[^\\w]", "-");
        }
        return message;
    }

    public static Util getInstance() {
        if (instance == null) {
            instance = new Util();

        }
        return instance;
    }

    public static String getFormattedPrice(double value) {
        String formatedvalue = "";

        DecimalFormatSymbols s = new DecimalFormatSymbols();
        s.setDecimalSeparator('.');
        s.setGroupingSeparator(',');
        NumberFormat f = new DecimalFormat("Rs ###,##0.00", s);
        f.setMaximumFractionDigits(2);

        formatedvalue = f.format(value);

        return formatedvalue;
    }

    public static String getFormattedPrice(BigDecimal value) {
        String formatedvalue = "";

        DecimalFormatSymbols s = new DecimalFormatSymbols();
        s.setDecimalSeparator('.');
        s.setGroupingSeparator(',');
        NumberFormat f = new DecimalFormat("Rs ###,##0.00", s);
        f.setMaximumFractionDigits(2);

        formatedvalue = f.format(value);

        return formatedvalue;
    }

    public static String convert(Date d, String format, String timezone) {
        SimpleDateFormat sf = new SimpleDateFormat(format);
        sf.setTimeZone(TimeZone.getTimeZone(timezone));
        return sf.format(d);
    }

    public static String getRoundPrice(double value) {
        String formatedvalue = "";

        DecimalFormatSymbols s = new DecimalFormatSymbols();
        s.setDecimalSeparator('.');
        s.setGroupingSeparator(',');
        NumberFormat f = new DecimalFormat("#####0.00", s);
        f.setMaximumFractionDigits(2);

        formatedvalue = f.format(value);

        return formatedvalue;
    }

    public static Date getStartDate(String start) {
        if (StringUtils.isBlank(start)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return sdf.parse(start);
        } catch (ParseException ex) {
            return null;
        }
    }

    public static Date getEndDate(String end) {
        
         if (StringUtils.isBlank(end)) {
            return null;
        }
         
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date d = sdf.parse(end);
            Calendar c = Calendar.getInstance();
            c.setTime(d);
            c.set(Calendar.HOUR_OF_DAY, 23);
            c.set(Calendar.MINUTE, 59);
            c.set(Calendar.SECOND, 59);

            return c.getTime();
        } catch (ParseException ex) {
            return null;
        }
    }
}
