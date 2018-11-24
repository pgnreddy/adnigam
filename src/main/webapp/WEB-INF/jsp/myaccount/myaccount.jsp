<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" type="text/css" href="<spring:url value="/resources/assets_admin/lib/bootstrap/css/bootstrap.min.css"/>" />
        <link rel="stylesheet" type="text/css" href="<spring:url value="/resources/assets_admin/lib/font-awesome/css/font-awesome.min.css"/>" />
        <link rel="stylesheet" type="text/css" href="<spring:url value="/resources/assets_admin/lib/select2/css/select2.min.css"/>" />
        <link rel="stylesheet" type="text/css" href="<spring:url value="/resources/assets_admin/lib/jquery.bxslider/jquery.bxslider.css"/>" />
        <link rel="stylesheet" type="text/css" href="<spring:url value="/resources/assets_admin/lib/owl.carousel/owl.carousel.css"/>" />
        <link rel="stylesheet" type="text/css" href="<spring:url value="/resources/assets_admin/lib/jquery-ui/jquery-ui.css"/>" />
        <link rel="stylesheet" type="text/css" href="<spring:url value="/resources/assets_admin/css/animate.css"/>" />
        <link rel="stylesheet" type="text/css" href="<spring:url value="/resources/assets_admin/css/reset.css"/>" />
        <link rel="stylesheet" type="text/css" href="<spring:url value="/resources/assets_admin/css/style.css"/>" />
        <link rel="stylesheet" type="text/css" href="<spring:url value="/resources/assets_admin/css/responsive.css"/>" />

        <link rel="shortcut icon" href="<spring:url value="/resources/assets_admin/images/favicon.ico"/>" type="image/x-icon">
        <link rel="icon" href="<spring:url value="/resources/assets_admin/images/favicon.ico"/>" type="image/x-icon">


        <title><spring:message code="adrobe.page.title" text="Adrobe"/></title>
    </head>
    <body class="category-page">

        <tiles:insertAttribute name="header" />
        <div class="columns-container">
            <div class="container">
                <!-- breadcrumb -->

                <!-- block category -->
                <div class="breadcrumb clearfix">
                    <a class="home" href="<spring:url value="/"/>" title="Return to Home">Home</a>
                    <span class="navigation-pipe">&nbsp;</span>
                    <span class="navigation_page">My Account</span>
                </div>
                <div class="row">
                    <tiles:insertAttribute name="menu"/>

                    <tiles:insertAttribute name="body" />

                </div>

            </div>
        </div>
        <div id="content-wrap">
            <div class="container">
                <hr/>
            </div> <!-- /.container -->
        </div>

        <tiles:insertAttribute name="footer" />

        <a href="#" class="scroll_top" title="Scroll to Top" style="display: inline;">Scroll</a>
        <!-- Script-->
    
        <script type="text/javascript" src="<spring:url value="/resources/assets_admin/lib/jquery/jquery-1.11.2.min.js"/>"></script>
        <script type="text/javascript" src="<spring:url value="/resources/assets_admin/js/jquery.validate.min.js"/>"></script>
        <script type="text/javascript" src="<spring:url value="/resources/assets_admin/lib/bootstrap/js/bootstrap.min.js"/>"></script>
        <script type="text/javascript" src="<spring:url value="/resources/assets_admin/lib/select2/js/select2.min.js"/>"></script>
        <script type="text/javascript" src="<spring:url value="/resources/assets_admin/lib/jquery.bxslider/jquery.bxslider.min.js"/>"></script>
        <script type="text/javascript" src="<spring:url value="/resources/assets_admin/lib/owl.carousel/owl.carousel.min.js"/>"></script>
        <script type="text/javascript" src="<spring:url value="/resources/assets_admin/lib/jquery.countdown/jquery.countdown.min.js"/>"></script>
        <script type="text/javascript" src="<spring:url value="/resources/assets_admin/js/jquery.actual.min.js"/>"></script>
        <script type="text/javascript" src="<spring:url value="/resources/assets_admin/js/theme-script.js"/>"></script>
        <script type="text/javascript" src="<spring:url value="/resources/assets_admin/js/adrb.js"/>"></script>
        <script src="<spring:url value="/resources/assets_admin/js/jquery-ui-1.9.2.custom.min.js"/>"></script>
    </body>
</html>