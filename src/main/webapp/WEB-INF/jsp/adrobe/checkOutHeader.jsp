<!-- HEADER -->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/custom" %> 
<custom:setting var="applicationDomain" key="APPLICATION_DOMAIN" /> 
<custom:setting var="dataSecureURL" key="DATA_SECURE_URL" /> 
<script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','https://www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-72951558-3', 'auto');
  ga('send', 'pageview');

</script>
<div id="nav-top-menu" class="nav-top-menu top-header">
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
                                <%--<li class="active"><a href="index.html">Home</a></li>
                                <li><a href="fashion.html">Fashion</a></li>
                                <li><a href="jewellery.html">Jewellery</a></li>
                                <li><a href="electronics.html">Electronics</a></li>
                                <li><a href="mobile_computers.html">Mobiles & Computers</a></li>
                                <li><a href="real_estates.html">Real Estate</a></li>
                                <li><a href="tours_travels.html">Travel</a></li>--%>
                                <li></li>
                            </ul>
                            <c:if test="${not empty sessionScope.USER}">
                                <div id="user-info-top" class="user-info pull-right">
                                    <div class="dropdown">
                                        <a class="current-open" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" href="#"><span>${sessionScope.USER.firstname} ${sessionScope.USER.lastname}</span></a>
                                        <ul class="dropdown-menu mega_dropdown" role="menu">
                                            <li><a href="<spring:url value="/myaccount"/>" data-toggle="modal" data-target="#adrobeLogin">My Account</a></li>
                                            <li><a href="<spring:url value="/logout"/>">Logout</a></li>
                                        </ul>
                                    </div>
                                </div>
                            </c:if>
                        </div><!--/.nav-collapse -->
                    </div>
                </nav>
            </div>
        </div>
        <!-- CART ICON ON MMENU -->
        <div id="shopping-cart-box-ontop">
            <a href="<spring:url value="/cart/"/>"> <i class="fa fa-shopping-cart"></i></a>
            <div class="shopping-cart-box-ontop-content"></div>
        </div>
    </div>
</div>
<!-- end header -->