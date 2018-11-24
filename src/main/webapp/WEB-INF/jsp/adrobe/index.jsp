<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/custom" %> 

<custom:setting var="categoryPath" key="CATEGORIES_IMAGES_PREFIX_PATH" />
<custom:setting var="premiumPath" key="PREMIUM_IMAGES_PREFIX_PATH" />
<%--<custom:setting var="imagesPath" key="IMAGES_PREFIX_PATH" />--%>
<c:set var="imagesPath" value="https://s3-us-west-2.amazonaws.com/adrobe.in.assets/" />
<!DOCTYPE html>
<head>
<title>Search Engine For Local Offers|Online Shopping-ADNIGAM</title>
<meta content='index, follow' name='Googlebot'/>
<meta name="DC.title" content="Search Engine For Local Offers|Online Shopping-ADNIGAM"/> <meta name="Description" content="ADNIGAM:India's 1st AD-commerce company.Search engine for online shopping with local offers in hyderabad."/>
 <meta name="Keywords" content="Search Engine for local offers,Local offers in hyderabad,search engine for online shopping,adnigam,online shopping in hyderabad,hyderabad online shopping,local online shopping,my city shopping,Best deals in India,ADCOMMERCE,Ad-commerce,Ad commerce,Best Offers in Hyderabad,City Shopping App,best online shopping in hyderabad, buy local save local,purchase online,Electronic Devices,Same day delivery in Hyderabad, Online shopping in warangal,Online shopping in Karimnagar,Online shopping in Khammam,Online shopping in Nizamabad,Online shopping in Kakinada,Online shopping in Vizag Online shopping in Vijayawada,Online shopping in Guntur,Online shopping in Nalgonda, Best online shopping in hyderabad,buy local save local,purchase online,shop local save local,salmon fish, summer deals on electronics, special offers on water purifiers, special discounts on frozen foods,Hyderabad Biryani,Dr.Copper water bottle,mi note 4,redmi note 4,big sale,best advertising and ad promotions online"/>
<meta name='robots' content='index, follow'>
<meta name="google-site-verification" content="UA0pemseykFmw5uPJYJr1c1yLSUgBKIJ_kxgJSlqe0E" />
<meta name="google-site-verification" content="hECuilLa4yTwIupcfHV5W2VyXNpTIafkn8qI9e8Wg0o" />
<meta name="msvalidate.01" content="DB16814C6F7BEDC6D34F06B6A7EC8F74" />
<!--ADCOMMERCE,search engine for local offers,Best Offers in Hyderabad,HYDERABAD ONLINE SHOPPING with best discounts -->

</head>

<!-- Main Slider Area -->
  <div class="main-slider-area">
    <div class="container">
      <div class="row">
        <div class="col-md-12 col-xs-12"> 
          <!-- Main Slider -->
          <!-- premium offers Declared here  -->
          <div class="main-slider">
            <div class="slider">
                 
                <div id="mainSlider" class="nivoSlider slider-image">
                 <c:forEach var="premiumOffer" items="${premiumOffers}" varStatus="status">
                     <a href="${premiumOffer.url}"> <img src="<spring:url value="${premiumPath}${premiumOffer.image}"/>" alt="${premiumOffer.title}" title="#${premiumOffer.id}"/> 
                     </a> </c:forEach>
                  </div>
          <c:forEach var="premiumOffer" items="${premiumOffers}" varStatus="status">
            <c:choose>
           <c:when test='${status.getIndex()%2 eq 0}'>
              <!-- Slider Caption 1 -->
              <div id="${premiumOffer.id}" class="nivo-html-caption slider-caption-2">
                <div class="slider-progress"></div>
                <div class="slide-text">
                  <div class="middle-text">
                    <div class="cap-dec" style="text-align:center">
                      <!--<h6 class="cap-dec wow fadeInUp animated" data-wow-duration="1.1s" data-wow-delay="0s">End's In:${premiumOffer.endDate}</h6>-->
                      <h5 class="cap-dec wow fadeInUp animated" data-wow-duration="2.3s" data-wow-delay="0s">${premiumOffer.title}</h5>
                      <!--<p class="cap-dec wow lightSpeedIn hidden-xs" data-wow-duration="1.5s" data-wow-delay="0s"> ${premiumOffer.description}</p>-->
                    </div>
                    <div class="cap-readmore wow zoomInRight" data-wow-duration=".9s" data-wow-delay=".5s" style="text-align:center"> <a href="${premiumOffer.url}">Shop Now</a> </div>
                  </div>
                </div>
              </div>
           </c:when>
              <c:otherwise>
              <!-- Slider Caption 2 -->
              <div id="${premiumOffer.id}" class="nivo-html-caption slider-caption-2">
                <div class="slider-progress"></div>
                <div class="slide-text slide-text-2">
                  <div class="middle-text">
                    <div class="cap-dec" style="text-align:center">
                      <!--<h2 class="cap-dec wow fadeInUp" data-wow-duration="1.1s" data-wow-delay="0s">End's In:${premiumOffer.endDate} </h2>-->
                      <h5 class="cap-dec wow fadeInUp" data-wow-duration="2.3s" data-wow-delay="0s"><span>20%</span> ${premiumOffer.title}</h5>
                      <!--<p class="cap-dec wow lightSpeedIn hidden-xs" data-wow-duration="1.5s" data-wow-delay="0s">${premiumOffer.description}</p>-->
                    </div>
                    <div class="cap-readmore wow zoomInUp" data-wow-duration="1.3s" data-wow-delay=".3s" style="text-align:center"> <a href="${premiumOffer.url}">Shop Now</a> </div>
                  </div>
                </div>
              </div>
            </c:otherwise>
            </c:choose>
           
            </c:forEach>
            </div>
          </div>
        
  
   
          <!-- End Main Slider --> 
          
        </div>
      </div>
    </div>
  </div>
  <!-- End Main Slider Area -->
  <div class="container"> 
    <!-- service section -->
    <div class="jtv-service-area">
      <div class="row">
        <div class="col col-md-4 col-sm-4 col-xs-12 no-padding">
          <div class="block-wrapper ship">
            <div class="text-des"> <i class="icon-rocket"></i>
              <h3>FREE SHIPPING & RETURN</h3>
<!--              <p>Lorem ipsum dolor sit amet.</p>-->
            </div>
          </div>
        </div>
        <div class="col col-md-4 col-sm-4 col-xs-12 no-padding">
          <div class="block-wrapper return">
            <div class="text-des"> <i class="fa fa-rupee"></i>
              <h3>MONEY BACK GUARANTEE</h3>
<!--              <p>Lorem ipsum dolor sit amet.</p>-->
            </div>
          </div>
        </div>
        <div class="col col-md-4 col-sm-4 col-xs-12 no-padding">
          <div class="block-wrapper support">
            <div class="text-des"> <i class="fa fa-headphones"></i>
              <h3>ONLINE SUPPORT 24/7</h3>
<!--              <p>Lorem ipsum dolor sit amet. </p>-->
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  
  <!-- Best selling -->
  <div class="container">
    <div class="row">
      <div class="best-selling-slider col-sm-12 col-md-6">
        <div class="title_block">
          <h3 class="products_title">Exclusive Offers</h3>
        </div>
        <div class="slider-items-products">
          <div id="best-selling-slider" class="product-flexslider hidden-buttons">
            <div class="slider-items slider-width-col4">
                <c:forEach var="offer" items="${exclusiveOffers}">
              <div class="product-item">
                <div class="item-inner">
                  <div class="product-thumbnail">
                    <div class="icon-new-label new-right">Sale</div>
                    <div class="box-hover">
                      <div class="btn-quickview"> <a href="javascript:void(0)" class="quik_view_htm" title="${offer.seo_title}" ><i class="fa fa-search-plus" aria-hidden="true"></i> Quick View</a> </div>
                      <div class="add-to-links" data-role="add-to-links"> <a href="javascript:void(0)"  pid="${offer.offerID}" class="action add-to-wishlist" title="Add to Wishlist"></a> <a href="<spring:url value="/p/${offer.vendorID}/${offer.seo_title}"/>" class="action add-to-compare" title="Add to Compare"></a> </div>
                    </div>
                    <a href="<spring:url value="/pd/${offer.seo_title}"/>" class="product-item-photo"> <img class="product-image-photo" src="<spring:url value="${imagesPath}${offer.image}"/>" alt=""></a> </div>
                  <div class="pro-box-info">
                    <div class="item-info">
                      <div class="info-inner">
                        <div class="item-title">
                          <h4> <a title="Product Title Here" href="<spring:url value="/pd/${offer.seo_title}"/>">${offer.title}</a></h4>
                        </div>
                        <div class="item-content">
                          <!--<div class="rating"> <i class="fa fa-star"></i> <i class="fa fa-star"></i> <i class="fa fa-star-o"></i> <i class="fa fa-star-o"></i> <i class="fa fa-star-o"></i> </div>-->
                          <div class="item-price">
                            <div class="price-box"> 
                              <p class="special-price"> 
                                  <span class="price-label">Special Price</span> 
                                  <span class="price"> 
                                      <c:if test="${offer.offerPrice>0}">
                                      <fmt:formatNumber value="${offer.offerPrice}" type="CURRENCY" pattern="Rs ###,##0" />
                                      </c:if>
                                  </span> </p>
                              <p class="old-price"> 
                                  <span class="price-label">Regular Price:</span>
                                  <span class="price">
                                       <c:if test="${offer.price>0}">
                                       <fmt:formatNumber value="${offer.price}" type="CURRENCY" pattern="Rs ###,##0" />
                                       </c:if>
                                  </span> 
                              </p>
<!--     percentage of offer  <p class="price"> 
                                <c:if test="${offer.offerPrice>0}">
                                <fmt:formatNumber value="${1-offer.offerPrice/offer.price}"  type="PERCENT"  />
                                </c:if>
                             </p>-->
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                    <div class="box-hover">
                      <div class="product-item-actions">
                        <div class="pro-actions">
                            <c:if test="${offer.status==1}">
                            <button pid="${offer.offerID}" class="action add-to-cart" type="button" title="Add to Cart"> <span>Add to Cart</span> </button>
                            </c:if>
                            <c:if test="${offer.status==0}">
                                <button pid="${offer.offerID}" style="cursor:not-allowed" disabled="true"class="action add-to-cart" type="button" title="out of stock"> <span>Add to Cart</span> </button>
                           </c:if>
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
      
      <!-- Featured Products -->
      
      <div class="featured-products-slider col-sm-12 col-md-6">
        <div class="title_block">
          <h3 class="products_title">Top Today Offers</h3>
        </div>
        <div class="slider-items-products">
          <div id="featured-products-slider" class="product-flexslider hidden-buttons">
            <div class="slider-items slider-width-col4">
          <c:forEach var="offer" items="${topOffers}" varStatus="status">
              <div class="product-item">
                <div class="item-inner">
                  <div class="product-thumbnail">
                   <div class="icon-sale-label sale-left"><a href="<spring:url value="/ps/todayOffers"/>" style="color:white">View.All</a></div>
                    <div class="box-hover">
                      <div class="btn-quickview"> <a href="javascript:void(0)" class="quik_view_htm"  title="${offer.seo_title}"><i class="fa fa-search-plus" aria-hidden="true"></i> Quick View</a> </div>
                      <div class="add-to-links" data-role="add-to-links"> <a href="javascript:void(0)"  pid="${offer.offerID}" class="action add-to-wishlist" title="Add to Wishlist"></a> <a href="<spring:url value="/p/${product.vendorID}/${product.seo_title}"/>" class="action add-to-compare" title="Add to Compare"></a> </div>
                    </div>
                      <a href="<spring:url value="/pd/${offer.seo_title}"/>" class="product-item-photo"> <img class="product-image-photo" src="${imagesPath}${offer.image}" alt=""></a> </div>
                  <div class="pro-box-info">
                    <div class="item-info">
                      <div class="info-inner">
                        <div class="item-title">
                          <h4> <a title="${offer.title}" href="<spring:url value="/pd/${offer.seo_title}"/>">${offer.title} </a></h4>
                        </div>
                        <div class="item-content">
                          <!--<div class="rating"> <i class="fa fa-star"></i> <i class="fa fa-star"></i> <i class="fa fa-star-o"></i> <i class="fa fa-star-o"></i> <i class="fa fa-star-o"></i> </div>-->
                          <div class="item-price">
                            <div class="price-box">
                                 <p class="special-price"> <span class="price-label">Special Price</span>
                                   <span class="price"> 
                                       <fmt:formatNumber value="${offer.offerPrice}" type="CURRENCY" pattern="Rs ###,##0" /></span> </p>
                              <p class="old-price"> <span class="price-label">Regular Price:</span>
                                  <span class="price"><fmt:formatNumber value="${offer.price}" type="CURRENCY" pattern="Rs ###,##0" /></span> </p>
                             </div>
                          </div>
                        </div>
                      </div>
                    </div>
                    <div class="box-hover">
                      <div class="product-item-actions">
                        <div class="pro-actions">
                          <c:if test="${offer.status==1}">
                            <button pid="${offer.offerID}" class="action add-to-cart" type="button" title="Add to Cart"> <span>Add to Cart</span> </button>
                            </c:if>
                            <c:if test="${offer.status==0}">
                                <button pid="${offer.offerID}" style="cursor:not-allowed" disabled="true"class="action add-to-cart" type="button" title="out of stock"> <span>Add to Cart</span> </button>
                           </c:if>
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
      <!-- Banner block-->
<!--      <div class="container">
        <div class="row">
          <div class="jtv-banner-block">
              <div class="jtv-subbanner1 col-sm-4"><a href="#"><img class="img-respo" alt="jtv-subbanner1" src="<spring:url value="/resources/images/banner3.jpg"/>"></a>
              <div class="text-block">
                <div class="text1 wow animated fadeInUp animated"><a href="#">Favorites</a></div>
                <div class="text2 wow animated fadeInUp animated"><a href="#">Depth in detail </a></div>
                <div class="text3 wow animated fadeInUp animated"><a href="#">Shop for women</a></div>
              </div>
            </div>
              <div class="jtv-subbanner2 col-sm-4"><a href="#"><img class="img-respo" alt="jtv-subbanner2" src="<spring:url value="/resources/images/banner4.jpg"/>"></a>
              <div class="text-block">
                <div class="text1 wow animated fadeInUp animated"><a href="#">Fancy</a></div>
                <div class="text2 wow animated fadeInUp animated"><a href="#">on brand-new models </a></div>
                <div class="text3 wow animated fadeInUp animated"><a href="#">Shop Now</a></div>
              </div>
            </div>
              <div class="jtv-subbanner2 col-sm-4"><a href="#"><img class="img-respo" alt="jtv-subbanner2" src="<spring:url value="/resources/images/banner5.jpg"/>"></a>
              <div class="text-block">
                <div class="text1 wow animated fadeInUp animated"><a href="#">New Arrivals</a></div>
                <div class="text2 wow animated fadeInUp animated"><a href="#">Love These Styles</a></div>
                <div class="text3 wow animated fadeInUp animated"><a href="#">shop for girls</a></div>
              </div>
            </div>
          </div>
        </div>
      </div>-->
      <!-- main container -->
      <div class="home-tab">
        <div class="container">
          <div class="row">
            <div class="col-md-12 col-sm-12 col-xs-12"> 
              <!-- Home Tabs  -->
              
              <div class="tab-info" id="productCats">
                <!-- THIS PRODUCT CATEGORY BODY DECLARED IN adrobeBase PAGE LINE 66 -->
              
                <!-- /.nav-tabs --> 
              </div>
              <div id="productTabContent" class="tab-content">
                  <!-- This MODEL CONTENT FOR PRODUCT CATEGORIES Declared in mainjs ajax call-->
              </div>
            </div>
            
            <!-- prom bannerss to Ads-->
<!--            <div class="jtv-promotion">
              <div class="container">
                <div class="row">
                  <div class="col-md-4 col-sm-5 col-xs-12">
                      <div class="add-banner wow animated fadeInUp animated"> <a href="#"><img src="<spring:url value="/resources/images/home-banner4.jpg" />" alt="banner"></a> </div>
                  </div>
                  <div class="col-md-8 col-sm-7 col-xs-12">
                    <div class="wrap">
                      <div class="wow animated fadeInRight animated">
                        <div class="box jtv-custom">
                          <div class="box-content">
                            <div class="promotion-center">
                              <p class="text_medium">Limited Time Only</p>
                              <div class="text_large">
                                <div class="theme-color">45% off</div>
                                Flash Sale</div>
                              <p class="text_small">Fashion for all outerwear, shirt &amp; accessories</p>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>-->
            </div>
        </div>
        
  <!--  NEW SOCIAL BUZZ CONTAINER start -->
<!--        <div class="container">
          <div class="row">
            <div class="col-md-12">
              <div class="title_block">
                <h3 class="products_title">Latest Post</h3>
              </div>
            </div>
            <div class="latest-post">
              <article class="jtv-entry col-md-6">
                <div class="jtv-post-inner">
                    <div class="feature-post images-hover"> <a href="single_post.html"><img src="<spring:url value="/resources/images/blog-img1.jpg"/>"  alt="image"></a>
                    <div class="overlay"></div>
                  </div>
                  <div class="jtv-content-post">
                    <h4 class="title-post"> <a href="single_post.html">Donec massa pellentesque placerat nisl laoreet</a> </h4>
                    <ul class="meta-post">
                      <li class="day"> <a href="#">May 05, 2017 /</a> </li>
                      <li class="author"> <a href="#">Admin /</a> </li>
                      <li class="travel"> <a href="#">Men</a> </li>
                    </ul>
                    <div class="jtv-entry-post excerpt">
                      <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam nisi sapien, accumsan ut molestie.</p>
                    </div>
                    <div class="read-more"><a href="single_post.html"><i class="fa fa-caret-right"></i> Read More</a></div>
                  </div>
                </div>
              </article>
              <article class="jtv-entry col-md-6">
                <div class="jtv-post-inner">
                    <div class="feature-post images-hover"> <a href="single_post.html"><img src="<spring:url value="/resources/images/blog-img1.jpg"/>"  alt="image"></a>
                    <div class="overlay"></div>
                  </div>
                  <div class="jtv-content-post">
                    <h4 class="title-post"> <a href="single_post.html">Cras pretium arcu ex hendrerit arcu, sit amet faucibus nisl </a> </h4>
                    <ul class="meta-post">
                      <li class="day"> <a href="#">Sep 13, 2017 /</a> </li>
                      <li class="author"> <a href="#">Admin /</a> </li>
                      <li class="travel"> <a href="#">Headphone</a> </li>
                    </ul>
                    <div class="jtv-entry-post excerpt">
                      <p>Itaque earum rerum hic tenetur a sapiente delectus, ut aut reiciendis voluptatibus maiores aut find fault with </p>
                    </div>
                    <div class="read-more"><a href="single_post.html"><i class="fa fa-caret-right"></i> Read More</a></div>
                  </div>
                </div>
              </article>
              <article class="jtv-entry col-md-6">
                <div class="jtv-post-inner">
                    <div class="feature-post images-hover"> <a href="single_post.html"><img src="<spring:url value="/resources/images/blog-img10.jpg"/>" alt="image"></a>
                    <div class="overlay"></div>
                  </div>
                  <div class="jtv-content-post">
                    <h4 class="title-post"> <a href="single_post.html"> Mollis ligula in, finibus tortor. Mauris eu dui ut lectus fermentum</a> </h4>
                    <ul class="meta-post">
                      <li class="day"> <a href="#">Oct 12, 2017 /</a> </li>
                      <li class="author"> <a href="#">Admin /</a> </li>
                      <li class="travel"> <a href="#">Fashion</a> </li>
                    </ul>
                    <div class="jtv-entry-post excerpt">
                      <p>Praesent ornare tortor ac ante egestas hendrerit. Aliquam et metus pharetra, bibendum massa nec. </p>
                    </div>
                    <div class="read-more"><a href="single_post.html"><i class="fa fa-caret-right"></i> Read More</a></div>
                  </div>
                </div>
              </article>
              <article class="jtv-entry col-md-6">
                <div class="jtv-post-inner">
                    <div class="feature-post images-hover"> <a href="single_post.html"><img src="<spring:url value="/resources/images/blog-img11.jpg"/>" alt="image"></a>
                    <div class="overlay"></div>
                  </div>
                  <div class="jtv-content-post">
                    <h4 class="title-post"> <a href="single_post.html"> Lorem ipsum dolor sit amet, consect adipisicing elit egestas hendrerit </a> </h4>
                    <ul class="meta-post">
                      <li class="day"> <a href="#">Dec 12, 2017 /</a> </li>
                      <li class="author"> <a href="#">Admin /</a> </li>
                      <li class="travel"> <a href="#">Fashion</a> </li>
                    </ul>
                    <div class="jtv-entry-post excerpt">
                      <p>Praesent ornare tortor ac ante egestas hendrerit. Aliquam et metus pharetra, bibendum massa nec. </p>
                    </div>
                    <div class="read-more"><a href="single_post.html"><i class="fa fa-caret-right"></i> Read More</a></div>
                  </div>
                </div>
              </article>
            </div>
          </div>
        </div>-->
        
        <!-- Offer 4/2  banners ADS -->
<!--        <div class="container">
          <div class="row">
            <div class="offer-add">
              <div class="col-md-4 col-sm-4 col-xs-12">
                <div class="jtv-banner-box banner-inner">
                    <div class="image"> <a class="jtv-banner-opacity" href="#"><img src="<spring:url value="/resources/images/top-banner5.jpg"/>" alt=""></a> </div>
                  <div class="jtv-content-text">
                    <h3 class="title">Buy 2 items</h3>
                    <span class="sub-title">get one for free!</span><a href="#" class="button">Shop now!</a> </div>
                </div>
              </div>
              <div class="col-md-3 col-sm-3 col-xs-12">
                <div class="jtv-banner-box banner-inner">
                    <div class="image"> <a class="jtv-banner-opacity" href="#"><img src="<spring:url value="/resources/images/top-banner3.jpg"/>" alt=""></a> </div>
                  <div class="jtv-content-text hidden">
                    <h3 class="title">New Arrival</h3>
                  </div>
                </div>
                <div class="jtv-banner-box banner-inner">
                    <div class="image "> <a class="jtv-banner-opacity" href="#"><img src="<spring:url value="/resources/images/top-banner4.jpg" />" alt=""></a> </div>
                  <div class="jtv-content-text">
                    <h3 class="title">shoes</h3>
                  </div>
                </div>
              </div>
              <div class="col-md-5 col-sm-5 col-xs-12">
                <div class="jtv-banner-box">
                    <div class="image"> <a class="jtv-banner-opacity" href="#"><img src="<spring:url value="/resources/images/top-banner2.jpg" />" alt=""></a> </div>
                  <div class="jtv-content-text">
                    <h3 class="title">perfect light </h3>
                    <span class="sub-title">on brand-new models</span> <a href="#" class="button">Buy Now</a> </div>
                </div>
              </div>
            </div>
          </div>
        </div>-->
      </div>
    </div>
  </div>
 

  <script>
 var productCategory={
    setCateProducts:function(categories){
         if(categories!==null){
//            console.log("categories"+categories.length);
           var htm=('<h3 class="new-product-title pull-left">Products category</h3><ul class="nav home-nav-tabs home-product-tabs">');
            var stats=true;
            for(var i=0;i<categories.length;i++){
                
                if(categories[i]['homeDisplay']===1){
                   
                if(stats===true){htm+='<li class="active"><a href="#pro_'+i+'" data-toggle="tab" aria-expanded="false">'+categories[i]['name']+'</a></li>';
                       stats=false; }else{
                  htm+='<li> <a href="#pro_'+i+'" data-toggle="tab" aria-expanded="false">'+categories[i]['name']+'</a> </li>';
                                } //ajax call for product display
           $("#productCats").html(htm+'</ul>');
               var url = methodUrl.baseUrl+'/products/' + categories[i].seo_title;
//               console.log("url:::"+methodUrl.baseUrl);
               var out=' <div class="tab-pane" id="pro_'+i+'">';
               var successCallback = function(res) {
//                  console.log("item data::"+JSON.stringify(res));
                        for(var i=0;i<res.length;i++){
                        out+='<div class="product-item col-md-3 col-sm-6">';
                        out+='<div class="item-inner">';
                        out+='<div class="product-thumbnail">';
                        out+='<div class="box-hover">';
                        out+='<div class="btn-quickview"> <a href="javascript:void(0)" class="quik_view_htm" title="'+res[i][0]['seo_title']+'"><i class="fa fa-search-plus" aria-hidden="true"></i> Quick View</a> </div>';
                         out+=' <div class="add-to-links" data-role="add-to-links"> <a href="javascript:void(0)" pid="'+res[i][0]['offerID']+'" class="action add-to-wishlist" title="Add to Wishlist"></a> <a href="'+methodUrl.baseUrl+'p/'+res[i][0]['vendorID']+'/'+res[i][0]['seo_title']+'" class="action add-to-compare" title="Add to Compare"></a> </div>';
                         out+='</div>';
                         out+=' <a href="'+methodUrl.baseUrl+'pd/'+res[i][0]['seo_title']+'" class="product-item-photo"> <img class="product-image-photo" src="https://s3-us-west-2.amazonaws.com/adrobe.in.assets/'+res[i][0]['image']+'"  alt=""></a> </div>';
                        out+=' <div class="pro-box-info">';
                        out+=' <div class="item-info">';
                        out+=' <div class="info-inner">';
                        out+='  <div class="item-title">';
                        out+=' <h4> <a title="Product Title Here" href="'+methodUrl.baseUrl+'pd/'+res[i][0]['seo_title']+'">'+res[i][0]['title']+' </a></h4>';
                        out+='</div>';
                         out+='<div class="item-content">';
                       // out+='<div class="rating"> <i class="fa fa-star"></i> <i class="fa fa-star"></i> <i class="fa fa-star"></i> <i class="fa fa-star-o"></i> <i class="fa fa-star-o"></i> </div>';
                         out+='<div class="item-price">';
                         out+='  <div class="price-box"> ';
                         out+='<p class="special-price"> <span class="price-label">Special Price</span> <span class="price">Rs '+res[i][0]['price']+' </span> </p>';
                         out+='<p class="old-price"> <span class="price-label">Regular Price:</span> <span class="price">Rs '+res[i][0]['offerPrice']+' </span> </p></div>';
                         out+=' </div>';
                        out+='</div>';
                        out+='</div>';
                        out+=' </div>';
                        out+='<div class="box-hover">';
                        out+='<div class="product-item-actions">';
                        out+=' <div class="pro-actions">';
                        if(res[i][0]['status'] === 1){
                          out+=' <button  class="action add-to-cart" type="button" title="Add to Cart" pid="'+res[i][0]['offerID']+'"> <span>Add to Cart</span> </button>';  
                        }else{
                          out+=' <button  class="action add-to-cart"  style="cursor:not-allowed" disabled="true" type="button"  title="out of stock" pid="'+res[i][0]['offerID']+'"> <span>Add to Cart(out of stock)</span> </button>';  
                        } 
                        // out+=' <button  class="action add-to-cart" type="button" title="Add to Cart" pid="'+res[i][0]['offerID']+'"> <span>Add to Cart</span> </button>';
                        out+='</div>';
                        out+='</div>';
                        out+=' </div>';
                        out+=' </div>';
                        out+='</div>';
                        out+='</div>';
                                   }
                  $('#productTabContent').append(out);
                                  };
                $.ajax({
                url: url,
                async:false,
                success: successCallback,
                dataType: "json"
                   });
                } 
           }
          $('#pro_0').addClass('active');
       }
    }
};
  $(document).ready(function(){
    var category = ${categoriesJson}; 
    productCategory.setCateProducts(category);
    }); 
     </script>