<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/custom" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<custom:setting var="imagesPath" key="IMAGES_PREFIX_PATH" />
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<div class="columns-container">
    <div class="container" id="columns">
        
        <!-- row -->
        <div class="row">
            <!-- Center colunm-->
            <div class=" col-xs-12 col-sm-9 col-md-12">
            <div class="row vendor-address-address">
            	<!-- promo-slide -->
                <div class="col-xs-12 col-sm-12 col-md-12">
                <img class="img-responsive promo-img" src="<spring:url value="${imagesPath}${vendor.coverImage}"/>" alt="Vendor-promo">
                </div>
                
                <%--<div class="col-md-3">
                    <div class="">
                        <div id="address-list">
                            <div class="tit-name">Address:</div>
                            <div class="tit-contain">${vendor.location},${vendor.city},${vendor.state}</div>
                            <div class="tit-name">Phone:</div>
                            <div class="tit-contain">${vendor.phone}</div>
                            <div class="tit-name">Email:</div>
                            <div class="tit-contain">${vendor.email}</div>
                        </div>
                        <span><a href="https://www.google.com/maps?q=${vendor.latitude},${vendor.longitude}" target="_blank">View location on map</a></span>
                    </div> 
                </div>--%>
            </div>
                
                
                <!-- view-product-list-->
                <div id="view-product-list" class="view-product-list">
                    <h2 class="page-heading">
                        <span class="page-heading-title">${vendor.name}</span>
                    </h2>
                    
                    <!-- PRODUCT LIST -->
                    <ul class="product-list grid">
                       <c:forEach var="product" items="${productsList}" varStatus="status">
                        <li class="col-xs-12 col-md-15 col-sm-3">
                            <div class="product-container">
                                <div class="left-block">
                                    <a href="#" class="product-detail-flip" index="${status.count}">
                                        <img class="img-responsive" alt="product" src="<spring:url value="${imagesPath}${product.image}"/>" />
                                    </a>
                                </div>
                                <div class="right-block">
                                    <h5 class="product-name"><a href="product_details.html">${product.title}</a></h5>
                                   
                                    <div class="content_price">
                                        <span class="price product-price"><fmt:formatNumber value="${product.offerPrice}" type="CURRENCY" pattern="Rs ###,##0" /></span>
                                       
                                        <span class="price old-price"><fmt:formatNumber value="${product.price}" type="CURRENCY" pattern="Rs ###,##0"/></span>
                                    </div>
                                </div>
                                
                            </div>
                            <div class="group-button-header reserve-cart-btn-set">
                                <c:choose>
                                    <c:when test="${product.quantity>0}">
                                  <a href="#" class="addToCart reserve btn btn-default btn-reserve" pid="${product.offerID}" type="button">Reserve</a>
                                  <a href="#" class="addToCart btn btn-default btn-add-to-cart" pid="${product.offerID}" type="button">Add to Cart</a>
                                    </c:when>
                                    <c:otherwise>
                                       
                                        <span class="btn btn-default alert-danger">Out of stock</span>
                                    </c:otherwise>
                                </c:choose>
                                </div>
                        </li>                       
                       
                        </c:forEach>
                    </ul>
                    <!-- ./PRODUCT LIST -->
                </div>
                
                <!--  ./Product Details -->
                 <div class="row">
                     <c:forEach var="product" items="${productsList}" varStatus="status">
                    <div class="col-xs-12 col-sm-12 col-md-12 product-detail-panel" index="${status.count}">
                        	<h2 class="header-banner bdr-btm">${product.title}</h2>
                       
                                <span class=" price old-price"><fmt:formatNumber value="${product.price}" type="CURRENCY" pattern="Rs ###,##0" /><br/>
                        <span class="product-new-price"><fmt:formatNumber value="${product.offerPrice}" type="CURRENCY" pattern="Rs ###,##0" /></span>
                                <ul>
                            <%--<li><span>Brand Name :</span> Apple</li>  --%>
                            <li>${product.description}</li>
                        </ul>
                        
                     </div>                  
                    </c:forEach>
                </div>
               <!--  ./Product Details -->         
                
        </div>
        <!-- ./row-->
    </div>
</div>

    <script type="text/javascript" src="<spring:url value="/resources/lib/jquery/jquery-1.11.2.min.js"/>"></script>    
    
    <script>
        
        $(document).ready(function(){
            var $product_detail_flip = $(".product-detail-flip");
            var $product_detail_panel = $(".product-detail-panel");
            
            $product_detail_flip.click(function(e){
                e.preventDefault();
                var index = $(this).attr("index");
                
                $product_detail_panel.each(function(item){
                    var $this = $(this);
                    if($this.attr("index")==index){
                        $this.slideToggle("slow");
                    }else{
                        $this.hide("slow");
                    }
                });
            });
        });
    </script>