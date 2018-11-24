<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/custom" %> 

<custom:setting var="categoryPath" key="CATEGORIES_IMAGES_PREFIX_PATH" />
<custom:setting var="premiumPath" key="PREMIUM_IMAGES_PREFIX_PATH" />
<%--<custom:setting var="imagesPath" key="IMAGES_PREFIX_PATH" />--%>
<c:set var="imagesPath" value="https://s3-us-west-2.amazonaws.com/adrobe.in.assets/" />
<custom:setting var="applicationDomain" key="APPLICATION_DOMAIN" /> 
<custom:setting var="dataSecureURL" key="DATA_SECURE_URL" /> 

<c:set var="selectedLocation" value="${cookie.loc.value}"/>
<!-- HEADER -->
<div id="header" class="header">
    <div class="top-header">
        <div class="container">
            <div class="nav-top-links">
                <a class="first-item" href="#"><img alt="phone" src="<spring:url value="/resources/assets_admin/images/phone.png"/>" />00-12-345-678</a>
                <a href="#"><img alt="email" src="<spring:url value="/resources/assets_admin/images/email.png"/>" />Contact us today! </a>
            </div>
            <div class="city ">
                <div class="dropdown">

                    <c:forEach var="loc" items="${locations}" >                        
                        <c:if test="${loc.id eq selectedLocation}">
                            <a class="current-open" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" href="#">${loc.name}</a>
                        </c:if>                             

                    </c:forEach> 
                    <c:if test="${empty selectedLocation || selectedLocation==-1}">
                        <a class="current-open" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" href="#">All</a>
                    </c:if>    

                    <ul class="dropdown-menu" role="menu">
                        <c:if test="${not empty selectedLocation}">
                            <li><a href="<spring:url value="/updateLocation?loc=-1"/>">All</a></li>
                            </c:if>
                            <c:forEach var="loc" items="${locations}" >
                                <c:if test="${loc.id ne selectedLocation}">
                                <li><a href="<spring:url value="/updateLocation?loc=${loc.id}"/>">${loc.name}</a></li>
                                </c:if>
                            </c:forEach>                                               
                    </ul>
                </div>
            </div>
            <!--
<div class="language ">
    <div class="dropdown">
          <a class="current-open" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" href="#">
          <img alt="email" src="assets/images/en.jpg" />English
          
          </a>
          <ul class="dropdown-menu" role="menu">
            <li><a href="#"><img alt="email" src="assets/images/en.jpg" />English</a></li>
        </ul>
    </div>
</div>
            -->


            <div class="support-link">
                <c:if test="${empty sessionScope.USER}">
                    <a href="#" data-toggle="modal" data-target="#adrobeLogin" >Login</a>
                    <a href="#" data-toggle="modal" data-target="#adrobeSignup">Signup</a>
                </c:if>
                <a href="#">Services</a>
                <a href="#">Support</a>
            </div>

            <c:if test="${not empty sessionScope.USER}">
                <div id="user-info-top" class="user-info pull-right">
                    <div class="dropdown">
                        <a class="current-open" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" href="#"><span>${sessionScope.USER.firstname} ${sessionScope.USER.lastname}</span></a>
                        <ul class="dropdown-menu mega_dropdown" role="menu">
                            <li><a href="<spring:url value="/myaccount"/>" data-toggle="modal">My Account</a></li>
                            <li><a href="#" data-toggle="modal" data-target="#adrobeLogin">Wishlists</a></li>
                        </ul>
                    </div>
                </div>
            </c:if>

        </div>
    </div>
    <!-- MAIN HEADER -->
    <div class="container main-header">
        <div class="row">
            <div class="col-xs-12 col-sm-3 logo">
                <a href="index.html"><img alt="Kute Shop" src="<spring:url value="/resources/assets_admin/images/logo-1.png"/>" /></a>
            </div>
        </div>
    </div>
    <div id="nav-top-menu" class="nav-top-menu">
        <div class="container">
            <div class="row">

                <div class="col-sm-3" id="box-vertical-megamenus">
                    <div class="box-vertical-megamenus">
                        <h4 class="title">
                            <span class="title-menu">Categories</span>
                            <span class="btn-open-mobiles pull-right home-page"><i class="fa fa-bars"></i></span>
                        </h4>
                        <c:if test="${not empty premiumOffers}">
                            <div class="vertical-menu-content is-home">
                                <ul class="vertical-menu-list">
                                    <c:forEach var="category" items="${categories}" varStatus="status">
                                        <li <c:if test="${status.index>10}">class="cat-link-orther"</c:if>>
                                            <a href="<spring:url value="/ps/${category.seo_title}"/>"><img class="icon-menu" alt="${category.name}" src="<spring:url value="${categoryPath}${category.categoryIcon}"/>">${category.name}</a>
                                        </li>							
                                    </c:forEach>


                                </ul>
<!--                                <div class="all-category"><span class="open-cate">All Categories</span></div>-->
                            </div>
                        </c:if>
                    </div>
                </div>            


            </div>

        </div>
    </div>
</div>

