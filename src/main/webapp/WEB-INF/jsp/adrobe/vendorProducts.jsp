 <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/custom" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--<custom:setting var="imagesPath" key="IMAGES_PREFIX_PATH" />--%>
<c:set var="imagesPath" value="https://s3-us-west-2.amazonaws.com/adrobe.in.assets/" />
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<!-- VENDOR IMAGE -->
  <div class="main-slider-area box-hover ">
    <div class="container">
      <div class="row">
        <div class="col-md-12 col-xs-12"> 
          
            <div > <img style="border-color: red;" src="<spring:url value="${imagesPath}${vendor.coverImage}"/>" alt="${vendor.name}" title="${vendor.name}"/></div>
            
          </div>
          
        </div>
      </div>
    </div>
   
  <!-- Main Container -->
  <div class="main-container col1-layout">
    <div class="container">
      <div class="row">
        <div class="col-main col-sm-12 col-xs-12">
          <div class="shop-inner">
            <div class="page-title ">
                
              <h2 class="breadcrumbs box-hover" style="color: red"> ${vendor.name}
               <a href="https://www.google.com/maps?q=${vendor.latitude},${vendor.longitude}" class="pull-right" target="_blank"><span  style="font-size: 1.2em;color: red" class="glyphicon glyphicon-map-marker"></span></a>
                        <a href="mailto:${vendor.email}" class="pull-right" target="_top"><span  style="font-size: 1.2em; color: red" class="glyphicon glyphicon-envelope"><span style="padding-left:10px;"><%--${vendor.email}--%></span></span> &nbsp;</a>
            </h2>   
            </div>
            <div class="product-grid-area">
              <ul class="products-grid">
                 <c:forEach var="product" items="${productsList}" varStatus="status">
                <li class="item col-lg-3 col-md-4 col-sm-6 col-xs-12">
                  <div class="product-item">
                    <div class="item-inner">
                      <div class="product-thumbnail">
                        <div class="box-hover">
                      <div class="btn-quickview"> <a href="javascript:void(0)" class="quik_view_htm"  title="${product.seo_title}"><i class="fa fa-search-plus" aria-hidden="true"></i> Quick View</a> </div>
                      <div class="add-to-links" data-role="add-to-links"> <a href="javascript:void(0)" pid="${product.offerID}" class="action add-to-wishlist" title="Add to Wishlist"></a> <a href="<spring:url value="/p/${product.vendorID}/${product.seo_title}"/>" class="action add-to-compare" title="Add to Compare"></a> </div>
                    </div>
                          <a href="<spring:url value="/pd/${product.seo_title}"/>" class="product-item-photo"> <img class="product-image-photo" src="${imagesPath}${product.image}" alt="${product.title}"></a> </div>
                      <div class="pro-box-info">
                        <div class="item-info">
                          <div class="info-inner">
                            <div class="item-title"> <h4><a title="${product.title}" href="<spring:url value="/pd/${product.seo_title}"/>">${product.title} </a></h4> </div>
                            <div class="item-content">
                              <!--<div class="rating"> <i class="fa fa-star"></i> <i class="fa fa-star-o"></i> <i class="fa fa-star-o"></i> <i class="fa fa-star-o"></i> <i class="fa fa-star-o"></i> </div>-->
                              <div class="item-price">
                                <div class="price-box">
                                    <p class="special-price"> <span class="price-label">Special Price</span> <span class="price"> <c:if test="${product.offerPrice>0}"><fmt:formatNumber value="${product.offerPrice}" type="CURRENCY" pattern="Rs ###,##0" /></c:if></span> </p>
                                  <p class="old-price"> <span class="price-label">Regular Price:</span> <span class="price"><c:if test="${product.price>0}"><fmt:formatNumber value="${product.price}" type="CURRENCY" pattern="Rs ###,##0"/></c:if></span> </p>
                                </div>
                              </div>
                            </div>
                          </div>
                        </div>
                        <div class="box-hover">
                          <div class="product-item-actions">
                            <div class="pro-actions">
                              
                             <c:if test="${product.status==1}">
                            <button pid="${product.offerID}" class="action add-to-cart" type="button" title="Add to Cart"> <span>Add to Cart</span> </button>
                            </c:if>
                            <c:if test="${product.status==0}">
                                <button pid="${product.offerID}" style="cursor:not-allowed" disabled="true"class="action add-to-cart" type="button" title="out of stock"> <span>Add to Cart</span> </button>
                           </c:if>
                            </div>
                            
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </li>
                 </c:forEach>
               
            
              </ul>
            </div>
          
          </div>
        </div>
      </div>
    </div>
  </div>
  <!-- Main Container End --> 
   <script>
        $(document).ready(function () {
            <c:if test="${not empty vendor.pageTitle}" >
                document.title="${vendor.pageTitle}";
                
            </c:if>
            $('head').append('${vendor.metaTags}');
        });
        <!-- main js --> 

    </script>