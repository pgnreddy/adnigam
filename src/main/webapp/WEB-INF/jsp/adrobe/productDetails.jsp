<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/custom" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<%--<custom:setting var="imagesPath" key="IMAGES_PREFIX_PATH" />--%>
<c:set var="imagesPath" value="https://s3-us-west-2.amazonaws.com/adrobe.in.assets/" />
<%@page contentType="text/html" pageEncoding="UTF-8"%>

  <!-- Breadcrumbs -->
  
  <div class="breadcrumbs">
    <div class="container">
      <div class="row">
        <div class="col-xs-12">
<!--          <ul>
            <li class="home"> <a title="Go to Home Page" href="index.html">Home</a><span>&raquo;</span></li>
            <li class=""> <a title="Go to Home Page" href="shop_grid.html">Watches</a><span>&raquo;</span></li>
            <li><strong>Lorem Ipsum is simply</strong></li>
          </ul>-->
        </div>
      </div>
    </div>
  </div>
  <!-- Breadcrumbs End --> 
  <!-- Main Container -->
  <div class="main-container col1-layout">
    <div class="container">
      <div class="row">
        <div class="col-main">
          <div class="product-view-area">
            <div class="product-big-image col-xs-12 col-sm-5 col-lg-5 col-md-5">
             
              <div class="large-image"> <a href="<spring:url value="${imagesPath}${product.image_original}"/>" class="cloud-zoom" id="zoom1" rel="useWrapper: false, adjustY:0, adjustX:20"> <img class="zoom-img" src="<spring:url value="${imagesPath}${product.image_original}"/>" alt="${product.title}"> </a> </div>

              <!--   THE CONTENT IS MULTI IMAGE         <div class="slider-items-products col-md-12">
              <div id="thumbnail-slider" class="product-flexslider hidden-buttons product-thumbnail">
                <div class="slider-items slider-width-col3">
                    <div class="thumbnail-items"><a href='<spring:url value="${imagesPath}${product.image}"/>' class='cloud-zoom-gallery' rel="useZoom: 'zoom1', smallImage: '${imagesPath}${product.image}' "><img src="<spring:url value="${imagesPath}${product.image}"/>" alt = "Thumbnail 2"/></a></div>
                  <div class="thumbnail-items"><a href='<spring:url value="${imagesPath}${product.image}"/>' class='cloud-zoom-gallery' rel="useZoom: 'zoom1', smallImage: 'images/products/img01.jpg' "><img src='<spring:url value="${imagesPath}${product.image}"/>' alt = "Thumbnail 1"/></a></div>
                  <div class="thumbnail-items"><a href='<spring:url value="/resources/images/products/img01.jpg"/>' class='cloud-zoom-gallery' rel="useZoom: 'zoom1', smallImage: 'images/products/img01.jpg' "><img src='<spring:url value="/resources/images/products/img01.jpg"/>'alt = "Thumbnail 1"/></a></div>
                  <div class="thumbnail-items"><a href='<spring:url value="/resources/images/products/img01.jpg"/>' class='cloud-zoom-gallery' rel="useZoom: 'zoom1', smallImage: 'images/products/img01.jpg' "><img src='<spring:url value="/resources/images/products/img01.jpg"/>' alt = "Thumbnail 2"/></a></div>
                  <div class="thumbnail-items"><a href='<spring:url value="/resources/images/products/img01.jpg"/>' class='cloud-zoom-gallery' rel="useZoom: 'zoom1', smallImage: 'images/products/img01.jpg' "><img src='<spring:url value="/resources/images/products/img01.jpg"/>'alt = "Thumbnail 2"/></a></div>
                </div>
              </div>
            </div>-->
              
              
              <!-- end: more-images --> 
              
            </div>
            <div class="col-xs-12 col-sm-7 col-lg-7 col-md-7 product-details-area">
              <div class="product-name">
                <h1>${product.title}</h1>
              </div>
              <div class="price-box">
                <p class="special-price"> <span class="price-label">Special Price</span> <span class="price"><c:if test="${product.offerPrice>0}"><fmt:formatNumber value="${product.offerPrice}" type="CURRENCY"  pattern="Rs ###,##0" /></c:if></span> </p>
                <p class="old-price"> <span class="price-label">Regular Price:</span> <span class="price"> <c:if test="${product.price>0}"><fmt:formatNumber value="${product.price}" type="CURRENCY" pattern="Rs ###,##0" /></c:if></span> </p>
              </div>
              <div class="ratings">
                <div class="rating"> <i class="fa fa-star"></i> <i class="fa fa-star"></i> <i class="fa fa-star"></i> <i class="fa fa-star-o"></i> <i class="fa fa-star-o"></i> </div>
                <p class="rating-links"> <a href="#reviews"> Review(s)</a> <span class="separator">|</span> <a href="#">Add Your Review</a> </p>
              
                 <c:if test="${product.status == 1}">
              <p class="availability in-stock pull-right">Availability: <span>In Stock</span></p>
              </c:if>
            <c:if test="${product.status==0}">
                 <p class="availability out-of-stock pull-right">Availability: <span>Out of Stock</span></p>
            </c:if>
              </div>
              <div class="short-description">
                <h4> Overview</h4>
                <p>${product.title}</p>
                <p> ${product.description}</p>
              </div>
<!--              <div class="product-color-size-area">
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
                  <!--<button class="button pro-add-to-cart add-to-cart" title="Add to Cart" pid="${product.offerID}" type="button"><span><i class="fa fa-shopping-cart"></i> Add to Cart</span></button>-->
                <c:if test="${product.status==1}">
                            <button pid="${product.offerID}" class="button pro-add-to-cart add-to-cart" type="button" title="Add to Cart"> <span>Add to Cart</span> </button>
                            </c:if>
                            <c:if test="${product.status==0}">
                                <button pid="${product.offerID}" style="cursor:not-allowed" disabled="true"class="button pro-add-to-cart add-to-cart" type="button" title="out of stock"> <span>Add to Cart</span> </button>
                           </c:if>
                
                </form>
              </div>
              <div class="product-cart-option">
                <ul>
                  <li><a href="javascript:void(0)" pid="${product.offerID}" class="add-to-wishlist"><i class="fa fa-heart"></i><span>Add to Wishlist</span></a></li>
                  <li><a href="https://www.facebook.com/sharer/sharer.php?u=http://www.adnigam.com/pd/${product.seo_title}" target="_blank"><i class="fa fa-facebook-square"></i><span>Share on Facebook</span></a></li>
                  <li><a href="https://plus.google.com/share?url=http://www.adnigam.com/pd/${product.seo_title}" target="_blank"><i class="fa fa-google-plus-square"></i><span>Share on goople+</span></a></li>
                </ul>
              </div>
            </div>
          </div>
        </div>
        <div class="product-overview-tab">
          <div class="container">
            <div class="row">
              <div class="col-xs-12">
                <div class="product-tab-inner">
                  <ul id="product-detail-tab" class="nav nav-tabs product-tabs">
                    <li class="active"> <a href="#description" data-toggle="tab"> Description </a> </li>
                    <li> <a href="#reviews" data-toggle="tab">Reviews</a> </li>
                  </ul>
                  <div id="productTabContent" class="tab-content">
                    <div class="tab-pane fade in active" id="description">
                      <div class="std">
                       ${product.description}  
                      </div>
                    </div>
                    <div id="reviews" class="tab-pane fade">
                      <div class="col-sm-5 col-lg-5 col-md-5">
                        <div class="reviews-content-left">
                          <h2>Customer Reviews</h2>
                          <div class="review-ratting">
                            <p><a href="#">Amazing</a> Review by Company</p>
                            <table>
                              <tbody>
                                <tr>
                                  <th>Price</th>
                                  <td><div class="rating"> <i class="fa fa-star"></i> <i class="fa fa-star"></i> <i class="fa fa-star-o"></i> <i class="fa fa-star-o"></i> <i class="fa fa-star-o"></i> </div></td>
                                </tr>
                                <tr>
                                  <th>Value</th>
                                  <td><div class="rating"> <i class="fa fa-star"></i> <i class="fa fa-star"></i> <i class="fa fa-star"></i> <i class="fa fa-star-o"></i> <i class="fa fa-star-o"></i> </div></td>
                                </tr>
                                <tr>
                                  <th>Quality</th>
                                  <td><div class="rating"> <i class="fa fa-star"></i> <i class="fa fa-star"></i> <i class="fa fa-star"></i> <i class="fa fa-star-o"></i> <i class="fa fa-star-o"></i> </div></td>
                                </tr>
                              </tbody>
                            </table>
                            <p class="author"> Angela Mack<small> (Posted on 16/12/2015)</small> </p>
                          </div>
                          <div class="review-ratting">
                            <p><a href="#">Good!!!!!</a> Review by Company</p>
                            <table>
                              <tbody>
                                <tr>
                                  <th>Price</th>
                                  <td><div class="rating"> <i class="fa fa-star"></i> <i class="fa fa-star"></i> <i class="fa fa-star-o"></i> <i class="fa fa-star-o"></i> <i class="fa fa-star-o"></i> </div></td>
                                </tr>
                                <tr>
                                  <th>Value</th>
                                  <td><div class="rating"> <i class="fa fa-star"></i> <i class="fa fa-star"></i> <i class="fa fa-star"></i> <i class="fa fa-star-o"></i> <i class="fa fa-star-o"></i> </div></td>
                                </tr>
                                <tr>
                                  <th>Quality</th>
                                  <td><div class="rating"> <i class="fa fa-star"></i> <i class="fa fa-star"></i> <i class="fa fa-star"></i> <i class="fa fa-star-o"></i> <i class="fa fa-star-o"></i> </div></td>
                                </tr>
                              </tbody>
                            </table>
                            <p class="author"> Lifestyle<small> (Posted on 20/12/2015)</small> </p>
                          </div>
                          <div class="review-ratting">
                            <p><a href="#">Excellent</a> Review by Company</p>
                            <table>
                              <tbody>
                                <tr>
                                  <th>Price</th>
                                  <td><div class="rating"> <i class="fa fa-star"></i> <i class="fa fa-star"></i> <i class="fa fa-star-o"></i> <i class="fa fa-star-o"></i> <i class="fa fa-star-o"></i> </div></td>
                                </tr>
                                <tr>
                                  <th>Value</th>
                                  <td><div class="rating"> <i class="fa fa-star"></i> <i class="fa fa-star"></i> <i class="fa fa-star"></i> <i class="fa fa-star-o"></i> <i class="fa fa-star-o"></i> </div></td>
                                </tr>
                                <tr>
                                  <th>Quality</th>
                                  <td><div class="rating"> <i class="fa fa-star"></i> <i class="fa fa-star"></i> <i class="fa fa-star"></i> <i class="fa fa-star-o"></i> <i class="fa fa-star-o"></i> </div></td>
                                </tr>
                              </tbody>
                            </table>
                            <p class="author"> Jone Deo<small> (Posted on 25/12/2015)</small> </p>
                          </div>
                        </div>
                      </div>
                      <div class="col-sm-7 col-lg-7 col-md-7">
                        <div class="reviews-content-right">
                          <h2>Write Your Own Review</h2>
                          <form>
                            <h3>You're reviewing: <span>Donec Ac Tempus</span></h3>
                            <h4>How do you rate this product?<em>*</em></h4>
                            <div class="table-responsive reviews-table">
                              <table>
                                <tbody>
                                  <tr>
                                    <th></th>
                                    <th>1 star</th>
                                    <th>2 stars</th>
                                    <th>3 stars</th>
                                    <th>4 stars</th>
                                    <th>5 stars</th>
                                  </tr>
                                  <tr>
                                    <td>Quality</td>
                                    <td><input type="radio"></td>
                                    <td><input type="radio"></td>
                                    <td><input type="radio"></td>
                                    <td><input type="radio"></td>
                                    <td><input type="radio"></td>
                                  </tr>
                                  <tr>
                                    <td>Price</td>
                                    <td><input type="radio"></td>
                                    <td><input type="radio"></td>
                                    <td><input type="radio"></td>
                                    <td><input type="radio"></td>
                                    <td><input type="radio"></td>
                                  </tr>
                                  <tr>
                                    <td>Value</td>
                                    <td><input type="radio"></td>
                                    <td><input type="radio"></td>
                                    <td><input type="radio"></td>
                                    <td><input type="radio"></td>
                                    <td><input type="radio"></td>
                                  </tr>
                                </tbody>
                              </table>
                            </div>
                            <div class="form-area">
                              <div class="form-element">
                                <label>Nickname <em>*</em></label>
                                <input type="text">
                              </div>
                              <div class="form-element">
                                <label>Summary of Your Review <em>*</em></label>
                                <input type="text">
                              </div>
                              <div class="form-element">
                                <label>Review <em>*</em></label>
                                <textarea></textarea>
                              </div>
                              <div class="buttons-set">
                                <button class="button submit" title="Submit Review" type="submit"><span><i class="fa fa-thumbs-up"></i> &nbsp;Review</span></button>
                              </div>
                            </div>
                          </form>
                        </div>
                      </div>
                    </div>
                    
                
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  
  <!-- Main Container End --> 
  
  <!-- Related Product Slider-->
  
 <c:if test="${not empty productsList}">
  
  <div class="container">
    <div class="row">
      <div class="col-xs-12">
        <div class="related-product-area">
          <div class="title_block">
            <h3 class="products_title">Related Vendor Products</h3>
            <h3><a class="products_title" href="<spring:url value="/p/${product.vendorID}/${product.seo_title}"/>">view all</a></h3>
          </div>
          <div class="related-products-pro">
            <div class="slider-items-products">
              <div id="related-product-slider" class="product-flexslider hidden-buttons">
                <div class="slider-items slider-width-col4"> 
                   <c:forEach var="product_s" begin="0" end="7" items="${productsList}" varStatus="status">
                  <div class="product-item">
                    <div class="item-inner">
                      <div class="product-thumbnail">
                        <div class="icon-sale-label sale-left">Sale</div>
                        <div class="box-hover">
                      <div class="btn-quickview"> <a href="javascript:void(0)" class="quik_view_htm" title="${product_s.seo_title}" ><i class="fa fa-search-plus" aria-hidden="true"></i> Quick View</a> </div>
                      <div class="add-to-links" data-role="add-to-links"> <a href="javascript:void(0)" class="action add-to-wishlist" title="Add to Wishlist" pid="${product_s.offerID}"></a> <a href="<spring:url value="/p/${product_s.vendorID}/${product_s.seo_title}"/>" class="action add-to-compare" title="Add to Compare"></a> </div>
                    </div>
                        <a href="<spring:url value="/pd/${product_s.seo_title}"/>" class="product-item-photo"> <img class="product-image-photo" src="<spring:url value="${imagesPath}${product_s.image}"/> " alt=""></a> </div>
                      <div class="pro-box-info">
                        <div class="item-info">
                          <div class="info-inner">
                              <div class="item-title"> <h4><a title="${product_s.seo_title}" href="<spring:url value="/pd/${product_s.seo_title}"/>">${product_s.title}</a></h4> </div>
                            <div class="item-content">
                              <!--<div class="rating"> <i class="fa fa-star"></i> <i class="fa fa-star"></i> <i class="fa fa-star-o"></i> <i class="fa fa-star-o"></i> <i class="fa fa-star-o"></i> </div>-->
                              <div class="item-price">
                                <div class="price-box"> <span class="regular-price"> <span class="price"><c:if test="${product_s.offerPrice>0}"><fmt:formatNumber value="${product_s.offerPrice}" type="CURRENCY" pattern="Rs ###,##0"/> </c:if></span> </span>
                                
                                 <p class="old-price"> <span class="price-label">Regular Price:</span> <span class="price"> <c:if test="${product_s.price>0}"><fmt:formatNumber value="${product_s.price}" type="CURRENCY" pattern="Rs ###,##0" /></c:if></span> </p>
              
                                </div>
                              </div>
                            </div>
                          </div>
                        </div>
                        <div class="box-hover">
                          <div class="product-item-actions">
                            <div class="pro-actions">
                              <button class="action add-to-cart"  pid="${product_s.offerID}"type="button" title="Add to Cart"> <span>Add to Cart</span> </button>
                            </div>
                            
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                  </c:forEach>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
      </c:if>
 <!--Related Product Slider End --> 
  
 

<div class="container"> 
    <!-- service section -->
    <div class="jtv-service-area">
      <div class="row">
        <div class="col col-md-4 col-sm-4 col-xs-12 no-padding">
          <div class="block-wrapper ship">
            <div class="text-des"> <i class="icon-rocket"></i>
              <h3>FREE SHIPPING & RETURN</h3>
             
            </div>
          </div>
        </div>
        <div class="col col-md-4 col-sm-4 col-xs-12 no-padding">
          <div class="block-wrapper return">
            <div class="text-des"> <i class="fa fa-inr"></i>
              <h3>MONEY BACK GUARANTEE</h3>
             
            </div>
          </div>
        </div>
        <div class="col col-md-4 col-sm-4 col-xs-12 no-padding">
          <div class="block-wrapper support">
            <div class="text-des"> <i class="fa fa-headphones"></i>
              <h3>ONLINE SUPPORT 24/7</h3>
           
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
 
 