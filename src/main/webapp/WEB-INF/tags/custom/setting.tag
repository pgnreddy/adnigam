<jsp:directive.tag language="java" trimDirectiveWhitespaces="true" />
<%@tag  body-content="empty" pageEncoding="UTF-8"%>
<%@tag import="com.cnbitsols.adrobe.utils.SettingsUtil" %>
<%@attribute name="key" required="true"%>
<%@attribute name="var" required="false"%>
<%
        String value ="";
        if(key!=null && ""!=key.trim()){
            value = SettingsUtil.getSettings(key);
        }
        
        if(var!=null){
            request.setAttribute(var, value);
        }else{
            out.print(value);
        }
        

%>