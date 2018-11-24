<%-- 
    Document   : quickView
    Created on : Aug 13, 2017, 10:57:08 AM
    Author     : Venkey
--%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/custom" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--<custom:setting var="imagesPath" key="IMAGES_PREFIX_PATH" />--%>
<c:set var="imagesPath" value="https://s3-us-west-2.amazonaws.com/adrobe.in.assets/" />



  <div id="modal-quickview" class="modal fade" role="dialog">
    <div class="modal-dialog">
      <div class="modal-body">
        <button type="button" class="close myclose" data-dismiss="modal">×</button>
        <c:if test="${null ne quickProduct}">
            
        <div class="product-view-area">
          <div class="product-big-image col-xs-12 col-sm-5 col-lg-5 col-md-5">
            <div class="icon-sale-label sale-left">Sale</div>
            <div class="slider-items-products">
              <div id="previews-list-slider" class="product-flexslider hidden-buttons">
                  <div class="slider-items slider-width-col6"> <a href="<spring:url value="${imagesPath}${quickProduct.image_original}"/>" class="cloud-zoom-gallery" id="zoom1"> <img class="zoom-img" src="<spring:url value="${imagesPath}${quickProduct.image}"/>" alt="products"> </a> </div>
              </div>
            </div>
            
            <!-- end: more-images --> 
            
          </div>
          <div class="col-xs-12 col-sm-7 col-lg-7 col-md-7 product-details-area">
            <div class="product-name">
              <h2>${quickProduct.title}</h2>
            </div>
            <div class="price-box">
              <p class="special-price"> <span class="price-label">Special Price</span> <span class="price"> <c:if test="${quickProduct.offerPrice>0}"><fmt:formatNumber value="${quickProduct.offerPrice}" type="CURRENCY"  pattern="Rs ###,##0" /></c:if> </span> </p>
              <p class="old-price"> <span class="price-label">Regular Price:</span> <span class="price">  <c:if test="${quickProduct.price>0}"><fmt:formatNumber value="${quickProduct.price}" type="CURRENCY" pattern="Rs ###,##0" /></c:if></span> </p>
            </div>
            <div class="ratings">
              <div class="rating"> <i class="fa fa-star"></i> <i class="fa fa-star"></i> <i class="fa fa-star"></i> <i class="fa fa-star-o"></i> <i class="fa fa-star-o"></i> </div>
              <p class="rating-links"> <a href="#">1 Review(s)</a> <span class="separator">|</span> <a href="#">Add Your Review</a> </p>
            <c:if test="${quickProduct.status == 1}">
              <p class="availability in-stock pull-right">Availability: <span>In Stock</span></p>
              </c:if>
            <c:if test="${quickProduct.status==0}">
                 <p class="availability out-of-stock pull-right">Availability: <span>Out of Stock</span></p>
            </c:if>
            </div>
            <div class="short-description">
              <h3>Quick Overview</h3>
               <p>${quickProduct.title}</p>
                <p> ${quickProduct.description}</p> 
            </div>
            <!--<div class="product-color-size-area">
              <div class="color-area">
                <h2 class="saider-bar-title">Color</h2>
                <div class="color">
                  <ul>
                    <li><a href="#"></a></li>
                    <li><a href="#"></a></li>
                    <li><a href="#"></a></li>
                    <li><a href="#"></a></li>
                    <li><a href="#"></a></li>
                    <li><a href="#"></a></li>
                  </ul>
                </div>
              </div>
              <div class="size-area">
                <h2 class="saider-bar-title">Size</h2>
                <div class="size">
                  <ul>
                    <li><a href="#">S</a></li>
                    <li><a href="#">L</a></li>
                    <li><a href="#">M</a></li>
                    <li><a href="#">XL</a></li>
                    <li><a href="#">XXL</a></li>
                  </ul>
                </div>
              </div>
            </div>-->
            <div class="product-variation">
              <form action="#" method="post">
                <div class="cart-plus-minus">
                  <label for="qty">Quantity:</label>
                   <div class="numbers-row">
                      <div onClick="var result = document.getElementById('qty'); var qty = result.value; if( !isNaN( qty ) &amp;&amp; qty &gt; 0 ) result.value--;return false;" class="dec qtybutton"><i class="fa fa-minus">&nbsp;</i></div>
                      <input type="text" class="qty" title="Qty" value="1" maxlength="1" id="qty" name="qty">
                      <div onClick="var result = document.getElementById('qty'); var qty = result.value; if( !isNaN( qty ) &amp;&amp; qty &lt; 10) result.value++;return false;" class="inc qtybutton"><i class="fa fa-plus">&nbsp;</i></div>
                    </div>
                </div>
                <button class="button pro-add-to-cart  add-to-cart" pid="${quickProduct.offerID}" title="Add to Cart" type="button"><span><i class="fa fa-shopping-cart"></i> Add to Cart</span></button>
              </form>
            </div>
            <div class="product-cart-option">
              <ul>
                  <li><a href="javascript:void(0)" pid="${quickProduct.offerID}" class="add-to-wishlist"><i class="fa fa-heart"></i><span>Add to Wishlist</span></a></li>
                 <li><a href="https://www.facebook.com/sharer/sharer.php?u=http://www.adnigam.com/pd/${quickProduct.seo_title}" target="_blank"><i class="fa fa-facebook-square"></i><span>Share on Facebook</span></a></li>
                  <li><a href="https://plus.google.com/share?url=http://www.adnigam.com/pd/${quickProduct.seo_title}" target="_blank"><i class="fa fa-google-plus-square"></i><span>Share on goople+</span></a></li>
                </ul>
            </div>
          </div>
   
        </div>
     </c:if>
        <c:if test="${quickProduct eq null}">
            <p>Quick view is Not possible for this Products</p>
            
        </c:if>
      </div>
      <div class="modal-footer"> <a href="#" class="btn-services-shop-now" data-dismiss="modal">Close</a> </div>
    </div>
  </div>
