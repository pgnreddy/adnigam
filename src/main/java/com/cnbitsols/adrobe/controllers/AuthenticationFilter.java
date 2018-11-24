/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cnbitsols.adrobe.controllers;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author santosh.r
 */
public class AuthenticationFilter implements Filter {

    private static Logger log = LoggerFactory.getLogger(AuthenticationFilter.class);

    private static final boolean debug = true;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;

    public AuthenticationFilter() {
    }

    /**
     *
     * @param request1 The servlet request we are processing
     * @param response1 The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    @Override
    public void doFilter(ServletRequest request1, ServletResponse response1,
            FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) request1;
       // log.debug("request to ::" + request.getRequestURI());
        // log.debug("servletpath to ::" + request.getServletPath());
        HttpServletResponse response = (HttpServletResponse) response1;
        
//        response.addHeader("Content-Security-Policy", "default-src localhost:8080 adrobe.in; script-src 'self' sha256-LlL87NwZmPErOwJzkLs+8HGwPj2H5NSUuOatgK/dLMY= sha256-wTbQgnQ0YgVtVhmyrbVnbnvLXNrxoAc9DUeAVAe0fOI= sha256-dqtMAXF3re0WTQNKg9aW2U9t/m6cuG49kA2UGRrav3g= sha256-Tv4H92zUmLOVGb9F0Loao766D6oa2vK+L1DwryM/M98= sha256-36A745AW8M+1F+wm7R2UNQ40SHr2JINRrXE7iWgUwWY= sha256-PD48lwugPFck7MT7Nd3b5LQmGW5ZvJ8JrvC0TzHhHPE= www.google-analytics.com ajax.googleapis.com www.springframework.org java.sun.com; style-src 'self' 'unsafe-inline'; img-src 'self' *.amazonaws.com www.ccavenue.com; frame-src 'self'");
//        response.addHeader("X-Content-Security-Policy", "default-src localhost:8080 adrobe.in; script-src 'self' 'sha256-LlL87NwZmPErOwJzkLs+8HGwPj2H5NSUuOatgK/dLMY=‘ 'sha256-wTbQgnQ0YgVtVhmyrbVnbnvLXNrxoAc9DUeAVAe0fOI=‘ 'sha256-dqtMAXF3re0WTQNKg9aW2U9t/m6cuG49kA2UGRrav3g=‘ 'sha256-Tv4H92zUmLOVGb9F0Loao766D6oa2vK+L1DwryM/M98=‘ 'sha256-36A745AW8M+1F+wm7R2UNQ40SHr2JINRrXE7iWgUwWY=‘ 'sha256-PD48lwugPFck7MT7Nd3b5LQmGW5ZvJ8JrvC0TzHhHPE=' www.google-analytics.com ajax.googleapis.com www.springframework.org java.sun.com; style-src 'self' 'unsafe-inline'; img-src 'self' *.amazonaws.com www.ccavenue.com; frame-src 'self'");
//        response.addHeader("X-WebKit-CSP", "default-src localhost:8080 adrobe.in; script-src 'self' 'sha256-LlL87NwZmPErOwJzkLs+8HGwPj2H5NSUuOatgK/dLMY=‘ 'sha256-wTbQgnQ0YgVtVhmyrbVnbnvLXNrxoAc9DUeAVAe0fOI=‘ 'sha256-dqtMAXF3re0WTQNKg9aW2U9t/m6cuG49kA2UGRrav3g=‘ 'sha256-Tv4H92zUmLOVGb9F0Loao766D6oa2vK+L1DwryM/M98=‘ 'sha256-36A745AW8M+1F+wm7R2UNQ40SHr2JINRrXE7iWgUwWY=‘ 'sha256-PD48lwugPFck7MT7Nd3b5LQmGW5ZvJ8JrvC0TzHhHPE=' www.google-analytics.com ajax.googleapis.com www.springframework.org java.sun.com; style-src 'self' 'unsafe-inline'; img-src 'self' *.amazonaws.com www.ccavenue.com; frame-src 'self'");
//        response.addHeader("X-XSS-Protection", "1");
        response.addHeader("X-Frame-Options", "SAMEORIGIN");
        
        String requestURI = request.getRequestURI();
        if (!requestURI.contains("/resources/") && !requestURI.contains("/api/")) {
            if (requestURI.contains("/vendor/") || requestURI.contains("/admin/")) {
                if (!requestURI.equals(request.getContextPath() + "/vendor/login") && !requestURI.equals(request.getContextPath() + "/vendor/forgotPassword") && !requestURI.equals(request.getContextPath() + "/admin/login")) {
                    Object user = request.getSession().getAttribute("user");
                    //log.debug("user : "+user);
                    if (user == null) {
                        String redirectURI = null;
                        if (requestURI.contains("/vendor/")) {
                            redirectURI = "/vendor/login";
                        } else if (requestURI.contains("/admin/")) {
                            redirectURI = "/admin/login";
                        } else {
                            redirectURI = "/login";
                        }
                        response.sendRedirect(request.getContextPath() + redirectURI);
                        return;
                    }
                }
            }
        }
        
        chain.doFilter(request, response1);
    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {
                log("AuthenticationFilter:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("AuthenticationFilter()");
        }
        StringBuffer sb = new StringBuffer("AuthenticationFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }

    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);

        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
                pw.print(stackTrace);
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }

    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }
    
    public static class RequestWrapper extends HttpServletRequestWrapper {
        
        public RequestWrapper(HttpServletRequest request) {
            super(request);
        }

        @Override
        public String[] getParameterValues(String name) {
            String[] values = super.getParameterValues(name);
            
            if (values == null) {
                return values;
            }
            
            String[] encodedValues = new String[values.length];
            for (int i=0;i<values.length;i++) {
                encodedValues[i] = StringEscapeUtils.escapeHtml(StringEscapeUtils.escapeJavaScript(values[i]));
            }
            
            return encodedValues;
        }

        @Override
        public String getParameter(String name) {
            String value = super.getParameter(name);
            if (value == null) {
                return null;
            }
            return StringEscapeUtils.escapeHtml(StringEscapeUtils.escapeJavaScript(value));
        }

        @Override
        public String getHeader(String name) {
            String header = super.getHeader(name);
            
            if (header == null) {
                return null;
            }
            
            return StringEscapeUtils.escapeHtml(StringEscapeUtils.escapeJavaScript(header));
        }
        
        
    }

}
