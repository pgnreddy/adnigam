<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/custom" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--<custom:setting var="imagesPath" key="IMAGES_PREFIX_PATH" />--%>
<c:set var="imagesPath" value="https://s3-us-west-2.amazonaws.com/adrobe.in.assets/" />
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<c:set var="showVendorPage" value="false" />
<!-- Breadcrumbs -->
  
  <div class="breadcrumbs">
    <div class="container">
      <div class="row">
        <div class="col-xs-12">
          <ul>
              <li class="home"> <a title="Go to Home Page" href="<spring:url value="/index.htm"/>">Home</a><span>&raquo;</span></li>
              <c:set var="hasVendorDetails" value="true"/> 
             <c:choose>
                <c:when test="${'all' eq categoryName}">
                    <c:set var="selectedCategoryTitle" value="All Categories"/>
              <li class=""> <a title="all categories" href="javascript:void(0)"><strong>All Categories</strong></a></li>                  
                </c:when>
                  <c:when test="${'todayOffers' eq categoryName}">
                    <c:set var="selectedCategoryTitle" value="Today's Offers"/>
              <li class=""> <a title="Today's Offers" href="javascript:void(0)"><strong>Today's Offers</strong></a></li>                  
                    <c:set var="hasVendorDetails" value="false"/>
                </c:when>
                <c:otherwise>
                    <c:set var="selectedCategoryTitle" value="${category.name}"/>
                    <c:set var="showVendorPage" value="true" />
              <li class=""> <a title="${category.name}" href="javascript:void(0)"><strong>${category.name}</strong></a></li>                  
                </c:otherwise>
            </c:choose>
                     <c:if test="${not empty param.search}">
                <c:set var="hasVendorDetails" value="true"/>
                <span class="navigation-pipe">&nbsp;</span>
<!--                <span class="navigation_page">Search &quot;${param.search}&quot;</span>-->
                 <li class=""> <a title="search" href="javascript:void(0)">Search &quot;${param.search}&quot;</li>                  
            </c:if>
          </ul>
        </div>
      </div>
    </div>
  </div>
  <!-- Breadcrumbs End --> 
  <!-- Main Container -->
  <div class="main-container col1-layout">
    <div class="container">
      <div class="row">
        <div class="col-main col-sm-12 col-xs-12">
             <c:choose>
            <c:when test="${empty productsList}">
                <div class="row">
                    <span class="page-heading-title">No offers found</span>
                </div>
            </c:when>
         <c:otherwise>
          <div class="shop-inner">
            <div class="page-title">
              <h2 class="breadcrumbs"> ${selectedCategoryTitle}</h2>
            </div>
    
            <div class="product-grid-area">
              <ul class="products-grid" id="product-list-container">
                    <c:forEach var="productObj" items="${productsList}">
                                    <c:choose>
                                        <c:when test="${hasVendorDetails==true}">
                                            <c:set var="product" value="${productObj[0]}" />
                                            <c:set var="vendor" value="${productObj[1]}" />
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="product" value="${productObj}" />
                                        </c:otherwise>
                                    </c:choose>
                <li class="item col-lg-3 col-md-4 col-sm-6 col-xs-12">
                  <div class="product-item">
                    <div class="item-inner">
                      <div class="product-thumbnail">
                          <div><a data-toggle="tooltip" title="Modern Shopping Centre">${vendor.name}</a>
                          </div>
                        <div class="box-hover">
                      <div class="btn-quickview"> <a  href="javascript:void(0)" class="quik_view_htm"  title="${product.seo_title}"><i class="fa fa-search-plus" aria-hidden="true"></i> Quick View</a> </div>
                      <div class="add-to-links" data-role="add-to-links"> <a pid="${product.offerID}" class="action add-to-wishlist" title="Add to Wishlist"></a> <a href="<spring:url value="/p/${product.vendorID}/${product.seo_title}"/>" class="action add-to-compare" title="Add to Compare"></a> </div>
                    </div>
                                                 <spring:url var="productUrl" value="/pd/${product.seo_title}"/>                                                
                                                <c:if test="${showVendorPage == true}">
                                                    <spring:url var="productUrl" value="/pd/${product.seo_title}"/>
                                                </c:if>
                        <a href="${productUrl}" class="product-item-photo"> <img class="product-image-photo" src="${imagesPath}${product.image}" alt="product"></a> </div>
                      <div class="pro-box-info">
                        <div class="item-info">
                          <div class="info-inner">
                            <div class="item-title"> <h4><a title="${product.title}" href="${productUrl}">${product.title} </a></h4> </div>
                            <div class="item-content">
                              <!--<div class="rating"> <i class="fa fa-star"></i> <i class="fa fa-star-o"></i> <i class="fa fa-star-o"></i> <i class="fa fa-star-o"></i> <i class="fa fa-star-o"></i> </div>-->
                              <div class="item-price">
                                <div class="price-box">
                                  <p class="special-price"> <span class="price-label">Special Price</span> <span class="price"> <c:if test="${product.offerPrice>0}"><fmt:formatNumber value="${product.offerPrice}" type="CURRENCY" pattern="Rs ###,##0"/> </c:if></span> </p>
                                  <p class="old-price"> <span class="price-label">Regular Price:</span> <span class="price"> <c:if test="${product.price>0}"><fmt:formatNumber value="${product.price}" type="CURRENCY" pattern="Rs ###,##0"/></c:if> </span> </p>
                                  
                                </div>
                              </div>
                            </div>
                          </div>
                        </div>
                        <div class="box-hover">
                          <div class="product-item-actions">
                            <div class="pro-actions">
                              <c:if test="${product.status==0}">
                            <button pid="${product.offerID}" class="action add-to-cart" type="button" title="Add to Cart"> <span>Add to Cart</span> </button>
                            </c:if>
                            <c:if test="${product.status==1}">
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
            <div class="pagination-area ">
              
                <button id="more">Load More <i class="fa fa-angle-right"></i></button>
                
            </div>
          </div>
        </c:otherwise>
      </c:choose>    
        </div>
      </div>
    </div>
  </div>





<script src="<spring:url value="/resources/js/handlebars-v3.0.3.js"/>"></script>
<script>

    $(document).ready(function() {
        var page = 1;
        var more = $("#more");
        var source = $("#product-template").html();
        var template = Handlebars.compile(source);
        var container = $("#product-list-container");
        var hasVendorDetails = '${hasVendorDetails}';
        var successCallback = function(data) {

            if (data && data.length > 0) {
                for (var i = 0; i < data.length; i++) {
                    var product = null;
                    if(hasVendorDetails==='true'){
                         var product = data[i][0];
                          var vendor = data[i][1];
                          product.vendor = vendor;
                    }else{
                        product = data[i];
                    }
                    
                    if(product.price>0){
                           product.showPrice = true;
                        }else{
                             product.showPrice = false;
                        }
                        
                        if(product.offerPrice>0){
                            product.showOfferPrice = true;
                        }else{
                            product.showOfferPrice = false;
                        }
                        
                    product.price=accounting.formatMoney(product.price, "Rs ", 0);
                    product.offerPrice=accounting.formatMoney(product.offerPrice, "Rs ", 0);
                        
                    var html = template(product);
                    container.append(html);
                }
                more.show();
            } else {
                more.hide();
            }

        };


        more.click(function(e) {
            console.log("sucess ");
            e.preventDefault();
            ++page;
            more.hide();
            var categoryName = '${categoryName}';
            var searchCondition="";
            var prRangeCondition="";
            var drRangeCondition="";
            
                    <c:if test="${not empty param.search}">
                        searchCondition="&search=${param.search}";
                    </c:if>
                        <c:if test="${not empty param.prRange}">
                        prRangeCondition="&prRange=${param.prRange}";
                    </c:if>
                        <c:if test="${not empty param.dsRange}">
                        drRangeCondition="&dsRange=${param.dsRange}";
                    </c:if>
            var url = "<%=request.getContextPath()%>/products/" + categoryName + "?c=1&page=" + page+searchCondition+prRangeCondition+drRangeCondition;

            $.ajax({
                url: url,
                success: successCallback,
                dataType: "json"
            });

        });
        
        setTooltips($('body'));
        
            <c:if test="${'todayOffers' eq categoryName}">
                document.title='Todays offer upto 60% off on daily home needs || ADROBE';
                $('head').append('<meta name="robots" content="index, follow"/> ');
            </c:if>
    });
    
    function setTooltips(container){
            //$(container).find('[data-toggle="tooltip"]').tooltip({'placement': 'right'}); 
        }
    
    Handlebars.registerHelper('if_eq', function(a, b) {
    if(a === b) // Or === depending on your needs
        return true;
    else
        return false;
});
    


</script>

<script id="product-template" type="text/x-handlebars-template">
    <li class="item col-lg-3 col-md-4 col-sm-6 col-xs-12">
    <div class="product-item">
     <div class="item-inner">
     
     <div class="product-thumbnail">
     <c:if test="${hasVendorDetails}">
                          <div><a data-toggle="tooltip" title="{{vendor.name}}" href="<spring:url value="/p/"/>{{vendorID}}/{{seo_title}}">{{vendor.name}}</a>
                          </div>
         </c:if>
                        <div class="box-hover">
                      <div class="btn-quickview"> <a   href="javascript:void(0)" class="quik_view_htm"  title="{{seo_title}}"><i class="fa fa-search-plus" aria-hidden="true"></i> Quick View</a> </div>
                      <div class="add-to-links" data-role="add-to-links"> <a href="javascript:void(0)" pid="{{offerID}}" class="action add-to-wishlist" title="Add to Wishlist"></a> <a href="<spring:url value="/p/{{vendorID}}/{{seo_title}}"/>" class="action add-to-compare" title="Add to Compare"></a> </div>
                    </div>                 
                        <a href="<spring:url value="/pd/"/>{{seo_title}}" class="product-item-photo"> <img class="product-image-photo" src="<spring:url value="${imagesPath}"/>{{image}}" alt="product"></a> 
                        </div>
              <div class="pro-box-info">
                        <div class="item-info">
                          <div class="info-inner">
                            <div class="item-title"> <h4><a title="{{title}}" href="<spring:url value="/pd/"/>{{seo_title}}">{{title}}</a></h4> </div>
                            <div class="item-content">
                              <!--<div class="rating"> <i class="fa fa-star"></i> <i class="fa fa-star-o"></i> <i class="fa fa-star-o"></i> <i class="fa fa-star-o"></i> <i class="fa fa-star-o"></i> </div>-->
                              <div class="item-price">
                                <div class="price-box">
                                  <p class="special-price"> <span class="price-label">Special Price</span> <span class="price">{{#if showOfferPrice}}{{offerPrice}} {{/if}}</span> </p>
                                  <p class="old-price"> {{#if showPrice}}<span class="price old-price">{{price}}</span>{{/if}}</p>
                                  
                                </div>
                              </div>
                            </div>
                          </div>
                        </div>
                        <div class="box-hover">
                          <div class="product-item-actions">
                            <div class="pro-actions">
                                                      
                            {{#if  status }}
                            <button pid="{{offerID}}" class="action add-to-cart" type="button" title="Add to Cart"> <span>Add to Cart</span> </button>
                            {{else}}
                                  
                            
                                <button pid="{{offerID}}" style="cursor:not-allowed" disabled="true"class="action add-to-cart" type="button" title="out of stock"> <span>Add to Cart</span> </button>
                                {{/if}}
                            </div>
                            
                          </div>
                        </div>
                      </div>
     
    </div>
    </div>
    </li>  
</script>
