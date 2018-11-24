<%-- 
    Document   : vendorBase
    Created on : Aug 1, 2015, 12:22:15 PM
    Author     : santosh.r
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <title><spring:message code="admin.page.title" text="Vendor"/></title>

        <!-- Bootstrap core CSS -->
        <link href="<spring:url value="/resources/assets_admin/css/bootstrap.css"/>" rel="stylesheet">
        <!--datetimepicker css-->
        <link href="<spring:url value="/resources/assets_admin/css/admin/bootstrap-datetimepicker.min.css"/>" rel="stylesheet">
        <!--external css-->
        <link href="<spring:url value="/resources/assets_admin/font-awesome/css/font-awesome.css"/>" rel="stylesheet" />

        <!--styles -->
        <link href="<spring:url value="/resources/assets_admin/css/style_v.css"/>" rel="stylesheet">
        <link href="<spring:url value="/resources/assets_admin/css/style-responsive.css"/>" rel="stylesheet">

        <link href="<spring:url value="/resources/assets_admin/css/admin/custom.css"/>" rel="stylesheet">
          <link rel="shortcut icon" href="<spring:url value="/resources/assets_admin/images/favicon.ico"/>" type="image/x-icon">
        <link rel="icon" href="<spring:url value="/resources/assets_admin/images/favicon.ico"/>" type="image/x-icon">
        <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!--[if lt IE 9]>
          <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
          <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->
    </head>

    <body>

        <section id="container" >
            <!--TOP BAR CONTENT & NOTIFICATIONS-->
            <!--header start-->
            <tiles:insertAttribute name="header" />
            <!--header end-->

            <!--MAIN SIDEBAR MENU -->
            <!--sidebar start-->
            <tiles:insertAttribute name="menu" />
            <!--sidebar end-->

            <!--MAIN CONTENT-->  
            <!--main content start-->
            <tiles:insertAttribute name="body" />
            <!--footer start-->
            <tiles:insertAttribute name="footer" />
            <!--footer end-->
        </section>

        <!-- js -->
        <script src="<spring:url value="/resources/assets_admin/js/jquery-1.8.3.min.js"/>"></script>
        <script src="<spring:url value="/resources/assets_admin/js/moment.min.js"/>"></script>
        <script src="<spring:url value="/resources/assets_admin/js/bootstrap.min.js"/>"></script>
        <script src="<spring:url value="/resources/assets_admin/js/bootstrap-datetimepicker.min.js"/>"></script>
        <script src="<spring:url value="/resources/assets_admin/js/jquery-ui-1.9.2.custom.min.js"/>"></script>
        <script src="<spring:url value="/resources/assets_admin/js/jquery.ui.touch-punch.min.js"/>"></script>
        <script src="<spring:url value="/resources/assets_admin/js/jquery.dcjqaccordion.2.7.js"/>"></script>
        <script src="<spring:url value="/resources/assets_admin/js/jquery.scrollTo.min.js"/>"></script>
        <script src="<spring:url value="/resources/assets_admin/js/jquery.nicescroll.js"/>" type="text/javascript"></script>
 
        <script src="<spring:url value="/resources/assets_admin/js/admin/jquery.easyui.min.js"/>"></script>
        <script src="<spring:url value="/resources/assets_admin/js/mine1dd.js"/>" type="text/javascript"></script>
        <script src="<spring:url value="/resources/assets_admin/js/html5imageupload.min1412.js"/>" type="text/javascript"></script>
        <script src="<spring:url value="/resources/assets_admin/js/jquery.form.js"/>" type="text/javascript"></script>
        <script src="<spring:url value="/resources/assets_admin/js/admin/general.js"/>" type="text/javascript"></script>


        <!--common script-->
        <script src="<spring:url value="/resources/assets_admin/js/common-scripts.js"/>"></script>
        <script src="<spring:url value="/resources/assets_admin/js/handlebars-v3.0.3.js"/>"></script>

        <!--script for this page-->

       

        <script>
            $(document).on('change', '.btn-file :file', function() {
                var input = $(this),
                        numFiles = input.get(0).files ? input.get(0).files.length : 1,
                        label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
                input.trigger('fileselect', [numFiles, label]);
            });
        </script>
        <script>
            $(document).ready(function() {
               
                $('.btn-file :file').on('fileselect', function(event, numFiles, label) {

                    var input = $(this).parents('.input-group').find(':text'),
                            log = numFiles > 1 ? numFiles + ' files selected' : label;

                    if (input.length) {
                        input.val(log);
                    } else {
                        
                            
                    }

                });
            });
        </script>


        

    </body>
</html>
