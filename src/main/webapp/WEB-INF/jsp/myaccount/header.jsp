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
<!-- HEADER -->
	
    <div id="nav-top-menu" class="nav-top-menu">
        <div class="container">
            <div class="row">
                <div class="col-sm-3">
                  <a href="<spring:url value="/"/>"><img class="small_logo" alt="Logo" src="<spring:url value="/resources/assets_admin/images/small_logo.png"/>" /></a>
            </div>
                <div id="main-menu" class="col-sm-9 main-menu">
                    <nav class="navbar navbar-default">
                        <div class="container-fluid">
                            <div class="navbar-header">
                                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                                    <i class="fa fa-bars"></i>
                                </button>
                                <a class="navbar-brand" href="#">MENU</a>
                            </div>
                            <div id="navbar" class="navbar-collapse collapse">
                                <ul class="nav navbar-nav">
                                    <li class="active"><a href="<spring:url value="/index"/>">Home</a></li>
                                        <c:forEach var="category" items="${categories}" varStatus="status" end="4">
                                        <li>
                                            <a href="<spring:url value="/ps/${category.seo_title}"/>">${category.name}</a>
                                        </li>							
                                    </c:forEach>   
                                </ul>
                            </div><!--/.nav-collapse -->                          
                            
                        </div>
                    </nav>
                </div>
            </div>
            <!-- CART ICON ON MMENU -->
            <div id="shopping-cart-box-ontop">
                <i class="fa fa-shopping-cart"></i>
                <div class="shopping-cart-box-ontop-content"></div>
            </div>
        </div>
    </div>
<!-- end header -->
