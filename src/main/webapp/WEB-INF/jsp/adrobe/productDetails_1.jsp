<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/custom" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--<custom:setting var="imagesPath" key="IMAGES_PREFIX_PATH" />--%>
<c:set var="imagesPath" value="https://s3-us-west-2.amazonaws.com/adrobe.in.assets/" />
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div class="columns-container">
    <div class="container product-details" id="columns">
        
        <!-- row -->
        <div class="row">
            <!-- Center colunm-->
            <div class=" col-xs-12 col-sm-12 col-md-3">
                <!-- promo-slide -->
				<%--<div class="wm-zoom-container my-zoom-1">
					<div class="wm-zoom-box">
						<img src="<spring:url value="${imagesPath}${product.image}"/>" class="wm-zoom-default-img detail-img" data-hight-src="<spring:url value="${imagesPath}${product.image}"/>" data-loader-src="<spring:url value="/resources/images/loader.gif"/>" alt="Product">
					</div>
				</div>--%>
                               <%-- <img id="zoom_01" src="<spring:url value="${imagesPath}${product.image}"/>" data-zoom-image="<spring:url value="${imagesPath}${product.image_original}"/>"/>
                               --%>
                               
                               <a href="<spring:url value="${imagesPath}${product.image_original}"/>" class="zoom" data-lighter><img src="<spring:url value="${imagesPath}${product.image}"/>" alt="Adrobe"></a>
                
        	</div>
        	<!-- view-product-details-->
			<div class="col-xs-12 col-sm-12 col-md-6">
            	<h2>${product.title}</h2>
                <hr/>
                <ul>
                	
					<li><span>Description:</span> <p>
						${product.description}
						
					</p>
					</li>
                </ul>
                                                <span class="old-price"><c:if test="${product.price>0}"><fmt:formatNumber value="${product.price}" type="CURRENCY" pattern="Rs ###,##0" /></c:if><br/></span>
                <span class="product-new-price"><c:if test="${product.offerPrice>0}"><fmt:formatNumber value="${product.offerPrice}" type="CURRENCY" pattern="Rs ###,##0" /></c:if></span>
                <div class="group-button-header reserve-cart-btn-set">
                     <c:choose>
                                    <c:when test="${product.quantity>0}">
                                        <c:if test="${product.offerPrice>0 || product.price>0}">
                                  <a href="#" class="addToCart reserve btn btn-default btn-reserve" pid="${product.offerID}" type="button">Reserve</a>
                                        </c:if>
                                  <a href="#" class="addToCart btn btn-default btn-add-to-cart" pid="${product.offerID}" type="button">Add to Cart</a>
                                    </c:when>
                                    <c:otherwise>
                                       
                                        <span class="btn btn-default alert-danger">Out of stock</span>
                                    </c:otherwise>
                                </c:choose>
                 </div>
                </div>
				<div class=" col-xs-12 col-sm-12 col-md-3">
                <!-- promo-slide -->
               <!-- <img src="assets/data/product.jpg" alt="Product">-->
        	</div>
            
        <!-- ./row-->
    </div>
</div>
                                        

 <%--<script type="text/javascript" src="<spring:url value="/resources/js/jquery.wm-zoom-1.0.min.js"/>"></script>
 <script type="text/javascript">
			$(document).ready(function(){
				$('.my-zoom-1').WMZoom();				
			});
		</script>  

 --%>
 <%--<script type="text/javascript" src="<spring:url value="/resources/js/jquery.elevatezoom.js"/>"></script>--%>
 <script type="text/javascript" src="<spring:url value="/resources/lib/zoom/easyzoom.min.js"/>"></script>
 <script type="text/javascript" src="<spring:url value="/resources/lib/zoom/jquery.lighter.js"/>"></script>
 <script>
   <%-- $('#zoom_01').elevateZoom({
    zoomType: "inner",
cursor: "crosshair",
zoomWindowFadeIn: 500,
zoomWindowFadeOut: 750
   }); 
   
   --%>
  
    jQuery(function($){
	
	$('a.zoom').easyZoom();

});

       
</script>