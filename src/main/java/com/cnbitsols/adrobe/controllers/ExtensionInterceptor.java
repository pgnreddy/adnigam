/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cnbitsols.adrobe.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 *
 * @author santosh.r
 */
public class ExtensionInterceptor extends HandlerInterceptorAdapter {

    public void postHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {

        String reportName = null;
        
        String reportDate = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss").format(new Date());
       

        if (request.getServletPath().endsWith(".xls")) {
            String reportType = request.getParameter("reportType");
            reportName = reportType+"_" + reportDate + ".xls";
        }
        if (reportName != null) {
// Set "Content-Disposition" HTTP Header
// so a user gets a pretty 'Save as' address
            response.setHeader("Content-Disposition", "attachment; filename=" + reportName);
        }
    }
}
