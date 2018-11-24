<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/custom" %> 

<%--<custom:setting var="imagesPath" key="IMAGES_PREFIX_PATH" />--%>
<c:set var="imagesPath" value="https://s3-us-west-2.amazonaws.com/adrobe.in.assets/" />
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div class="columns-container">
    <div class="container" id="columns">

        <!-- row -->
        <div class="row">
            <!-- Center colunm-->
            <div class=" col-xs-12 col-sm-9 col-md-12">
                <!-- promo-slide -->
                <img src="<spring:url value="${imagesPath}${vendor.coverImage}"/>" alt="Vendor-promo">
                <!-- ./promo-slider -->

                <!-- view-product-list-->
                <div id="view-product-list" class="view-product-list">
                    <h2 class="page-heading">
                        <span class="page-heading-title">${vendor.name}</span>
                    </h2>

                    <!-- PRODUCT LIST -->
                    <ul class="product-list grid">
                        <c:forEach var="product" items="${productsList}" varStatus="status">
                            <li class="col-xs-12 col-sm-4 col-md-2 <c:if test="${status.index==0}">col-md-offset-1</c:if>">
                                    <div class="product-container">
                                        <div class="left-block">
                                            <a href="summary.html">
                                                <img class="img-responsive" alt="product" src="<spring:url value="${imagesPath}${product.image}"/>" />
                                        </a>

                                        <div class="add-to-cart" >
                                            <a title="Add to Cart" class="addToCart" pid="${product.offerID}">Add to Cart</a>
                                        </div>
                                    </div>
                                    <div class="right-block">
                                        <h5 class="product-name"><a href="#">${product.title}</a></h5>

                                        <div class="content_price">
                                            <span class="price product-price">&#8377;${product.offerPrice}</span>
                                            <span class="price old-price">&#8377;${product.price}</span>
                                        </div>

                                    </div>
                                </div>
                            </li>
                        </c:forEach>

                    </ul>
                    <!-- ./PRODUCT LIST -->
                </div>

            </div>
            <!-- ./row-->
        </div>
    </div>

    <script type="text/javascript" src="<spring:url value="/resources/lib/jquery/jquery-1.11.2.min.js"/>"></script>    
    