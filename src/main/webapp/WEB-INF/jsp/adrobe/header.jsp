<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/custom" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<custom:setting var="categoryPath" key="CATEGORIES_IMAGES_PREFIX_PATH" />
<custom:setting var="premiumPath" key="PREMIUM_IMAGES_PREFIX_PATH" />
<%--<custom:setting var="imagesPath" key="IMAGES_PREFIX_PATH" />--%>
<c:set var="imagesPath" value="https://s3-us-west-2.amazonaws.com/adrobe.in.assets/" />
<custom:setting var="applicationDomain" key="APPLICATION_DOMAIN" /> 
<custom:setting var="dataSecureURL" key="DATA_SECURE_URL" /> 

<c:set var="selectedLocation" value="${cookie.loc.value}"/>

  <!-- Header -->
  <header id="header" data-session-user="${not empty sessionScope.USER}">
    <div class="header-container">
      <div class="header-top">
        <div class="container">
          <div class="row">
            <div class="col-md-5 col-sm-5 col-xs-6 hidden-xs">
              <div class="slider-items-products">
                <div id="offer-slider" class="product-flexslider hidden-buttons">
                  <div class="slider-items slider-width-col4">
                    <div class="offer-content-text">
                      <p><i class="pe-7s-plane"></i>Warangal,Karimnagar,Khammam,Nizamabad,Nalgonda,Vijayawada,vizag,Kakinada &Guntur.</p>
                    </div>
                    <div class="offer-content-text">
                      <p><i class="pe-7s-like2"></i> Only the <span>Best Quality</span> and brands</p>
                    </div>
                    <div class="offer-content-text">
                      <p><i class="pe-7s-gift"></i> Free Gift after every <span>10 order</span></p>
                    </div>
                    <div class="offer-content-text">
                      <p><i class="pe-7s-refresh-2"></i> 100% Money Back <span>Guarantee</span>.</p>
                    </div>
                    <div class="offer-content-text">
                      <p><i class="pe-7s-like"></i> <span>Do You Love Us?</span> Like on Face Book! </p>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <!-- top links -->
            <div class="headerlinkmenu col-md-7 col-sm-7 col-xs-12" > 
              <!-- Default Welcome Message -->
              
              <c:choose>
                  <c:when test="${not empty sessionScope.USER}">
                       <div class="welcome-msg hidden-xs">Welcome ${sessionScope.USER.firstname} ${sessionScope.USER.lastname}.! </div>
                       <ul class="links">
                           <li><a href="<spring:url value="/cart.htm"/>">Checkout</a></li>
                            <li><a href="<spring:url value="/logout.htm"/>">Logout</a></li>
                        </ul>
                  </c:when>
                  <c:otherwise>
                      <div class="welcome-msg hidden-xs">Welcome to Adnigam.! </div>
                       <ul class="links">
                        <li><a href="<spring:url value="/signup.htm"/>">Sign Up</a></li>
                         <li><a href="<spring:url value="/login.htm"/>">Log In</a></li>
                        </ul>
                  </c:otherwise>     
              </c:choose>
<!--             
              <div class="language-currency-wrapper pull-right">
                <div class="inner-cl">
                  <div class="block block-language form-language">
                    <div class="lg-cur"> <span> <span class="lg-fr">English</span> <i class="fa fa-angle-down"></i> </span> </div>
                    <ul>
                      <li> <a href="#"><span>German</span> </a> </li>
                      <li> <a href="#"><span>Brazil</span> </a> </li>
                      <li> <a href="#"><span>Chile</span> </a> </li>
                      <li> <a href="#"><span>Spain</span> </a> </li>
                    </ul>
                  </div>
                  <div class="block block-currency">
                    <div class="item-cur"> <span>USD </span> <i class="fa fa-angle-down"></i></div>
                    <ul>
                      <li> <a href="#"><span class="cur_icon">€</span> EUR</a> </li>
                      <li> <a href="#"><span class="cur_icon">¥</span> JPY</a> </li>
                      <li> <a class="selected" href="#"><span class="cur_icon">$</span> USD</a> </li>
                    </ul>
                  </div>
                </div>
              </div>-->
            </div>
          </div>
        </div>
      </div>
      <div class="container">
        <div class="row"> 
          <!-- Header Logo -->
          <div class="col-xs-12 col-lg-5 col-md-3 col-sm-3">
              <div class="logo"><a title="e-commerce" href="<spring:url value="/index.html"/>"><img alt="e-commerce" src="<spring:url value="/resources/images/logo.png"/>"></a> </div>
          </div>
          <div class="col-xs-12 col-sm-5 col-md-6 col-md-5 top-search"> 
            <!-- Search -->
            <div id="search">
                <form action="<spring:url value="/ps/all"/>" method="get">
                <div class="input-group">
                  <select class="cate-dropdown hidden-xs hidden-sm" name="category_id">
                    <option>All Categories</option>
                    <c:forEach var="category" items="${allCategories}" varStatus="status">                               
                                <option value="${category.seo_title}" <c:if test="${category.seo_title eq categoryName}">selected</c:if>>${category.name}</option>
                     </c:forEach>
                  </select>
                  <input type="text" class="form-control" placeholder="Search.." name="search">
                  <button class="btn-search" type="submit"><i class="fa fa-search"></i></button>
                </div>
              </form>
            </div>
            
            <!-- End Search --> 
          </div>
          <div class="col-lg-2 col-sm-3 col-xs-12 top-cart"> 
            <!-- Begin shopping cart trigger  -->
            <div id="shopping-cart-trigger"> <a id="cart_num" href="javascript:void(0)" class="cart-icon" title="Shopping Cart" data-toggle="modal" data-target="#modal-cart"> <i class="fa fa-shopping-bag"></i> </a> </div>
            <!-- End shopping cart trigger --> 
           <div class="dropdown">
               <a  href="javascript:void(0)" class="top-my-account btn btn-default  dropdown-toggle" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><i class="fa fa-user"></i>  </a> 
            
     <c:choose>
         <c:when test="${not empty sessionScope.USER}">
            <ul class="dropdown-menu" style="margin: 36px -30px 0;" aria-labelledby="dropdownMenuButton">
             <li><a href="${pageContext.request.contextPath}/myaccount">My account</a></li>
             <!--<li><a href="${pageContext.request.contextPath}/favourites">WishList</a></li>-->
             <!--<li><a href="#">Something else here</a></li>-->
             <li role="separator" class="divider"></li>
             <li><a href="${pageContext.request.contextPath}/logout">logout</a></li>
           </ul>
         </c:when>
         <c:otherwise>
   <ul class="dropdown-menu" style="margin: 36px -30px 0;" aria-labelledby="dropdownMenuButton">
    <li><a href="${pageContext.request.contextPath}/login.htm">Login</a></li>
    <li><a href="${pageContext.request.contextPath}/signup.htm">signup</a></li>
   
  </ul>
         </c:otherwise>
     </c:choose>   
            
            
          
           </div>
            
            
            <a href="#" class="top-compare"><i class="fa fa-heart"></i></a> 
          
          
          
          
          </div>
        </div>
      </div>
    </div>
    <!-- End Header Logo -->
    
    <nav> 
      <!-- Start Menu Area -->
      <div class="menu-area">
        <div class="container">
          <div class="row">
            <div class="col-md-12">
              <div class="main-menu">
                <div class="mm-toggle-wrap hidden-lg hidden-md hidden-sm">
                  <div class="mm-toggle"> <i class="fa fa-align-justify"></i><span class="mm-label">Menu</span> </div>
                </div>
                <ul class="hidden-xs">
                    <li class="active custom-menu"><a href="<spring:url value="/index.html"/>">Home</a>
<!--                    <ul class="dropdown">
                      <li><a href="../index.html">Home Version 1</a></li>
                      <li><a href="index.html">Home Version 2</a></li>
                      <li><a href="../version3/index.html">Home Version 3</a></li></ul>
                  </li>-->
                  <li class="megamenu"><a href="javascript:void(0)">Categories</a>
                    <div class="mega-menu">
                      <div class="menu-block menu-block-left">
                        <div class="menu-block-1">
                            <c:forEach var="category" items="${categories}" varStatus="status">
                                 <c:set var="sublenth" value="${fn:length(category.subCategories)}"/>
                                 <c:if test="${sublenth gt 3}">
                                     <ul>
                                     <li> <a class="mega-title" href="<spring:url value="/ps/${category.seo_title}"/>">${category.name}</a></li>
                                       <c:forEach var="subCat" begin="1" end="${sublenth}" items="${category.subCategories}">
                                      <li> <a href="<spring:url value="/ps/${subCat.seo_title}"/>">${subCat.name}</a></li>
                                      </c:forEach>
                                     </ul>
                                 </c:if>
                               </c:forEach>                          
                        </div>
                        
                      </div>
                         <div class="menu-block menu-block-right">
                             <div class="menu-block">
                                 <c:forEach var="category" items="${categories}" varStatus="status">
                                 <c:set var="sublenth" value="${fn:length(category.subCategories)}"/>
                                 <c:if test="${sublenth le 3 and sublenth gt 1}">
                                     <ul>
                                     <li> <a class="mega-title" href="<spring:url value="/ps/${category.seo_title}"/>">${category.name}</a></li>
                                       <c:forEach var="subCat" begin="1" end="${sublenth}" items="${category.subCategories}">
                                      <li> <a href="<spring:url value="/ps/${subCat.seo_title}"/>">${subCat.name}</a></li>
                                      </c:forEach>
                                     </ul>
                                 </c:if>
                               </c:forEach>
                                 
                             </div></div>
                        
                        <div class="menu-block menu-block-left">
                            <div class="menu-block">
                                <c:forEach var="category" items="${categories}" varStatus="status">
                                 <c:set var="sublenth" value="${fn:length(category.subCategories)}"/>
                                 <c:if test="${sublenth le 1}">
                                     <ul>
                                     <li> <a class="mega-title" href="<spring:url value="/ps/${category.seo_title}"/>">${category.name}</a></li>
                                     
                                     </ul>
                                 </c:if>
                               </c:forEach>
                                
                            </div></div>
                    </div>
                  </li>
                  
                  <!--PRODUCT START -->
                  <li class="megamenu"><a href="http://nrihita.com/" target="_blank" >NRI SERVICE</a>
<!--                    <div class="mega-menu">
                      <div class="menu-block menu-block-center">
                        <div class="menu-block">
                          <ul>
                            <li> <a class="mega-title" href="#">Products <span class="menu-item-tag menu-item-tag-hot">new</span></a></li>
                            
                          </ul>                  
                            <div class="mega-menu-img-box"><a href="#"><img src="<spring:url value="/resources/images/menu-img1.jpg"/>" alt="Bannar 1"></a> </div>
                            <div class="mega-menu-img-box1"><a href="#"><img src="<spring:url value="/resources/images/menu-img3.jpg"/>" alt="Bannar 3"></a> </div>
                        </div>
                      </div>
                    </div>-->
                  </li>
<!--                  <li class="megamenu"><a href="shop_grid.html">Electronics</a>
                    <div class="mega-menu">
                      <div class="menu-block menu-block-center">
                        <div class="menu-block-1">
                          <ul>
                            <li class="menu-banner"><a href="#"><img src="images/e-img1.jpg" alt="menu-banner"></a></li>
                            <li> <a class="mega-title" href="#">Mobiles <span class="menu-item-tag menu-item-tag-hot">hot</span></a></li>
                            <li> <a href="shop_grid.html">Casual shirt</a></li>
                            <li> <a href="shop_grid.html">Trousers</a></li>
                            <li> <a href="shop_grid.html">Suits & Blazers </a></li>
                            <li> <a href="shop_grid.html">Sportswear </a></li>
                          </ul>
                         
                        <div class="menu-block-3 hidden-sm">
                          <div class="mega-menu-img"> <a href="shop_grid.html"><img src="images/menu-img4.jpg" alt="Bannar 1"></a> </div>
                        </div>
                      </div>
                    </div>
                  </li>-->
<!--                  <li class="megamenu"><a href="shop_grid.html">Pages <span class="menu-item-tag menu-item-tag-new">new</span></a>
                    <div class="mega-menu">
                      <div class="menu-block menu-block-center">
                        <div class="menu-block">
                          <ul>
                            <li> <a class="mega-title" href="#">Shop Pages </a></li>
                            <li> <a href="shop_grid.html"> Shop grid </a></li>
                            <li> <a href="shop_grid_right_sidebar.html"> Shop grid right sidebar</a> </li>
                            <li> <a href="shop_list.html"> Shop list </a> </li>
                            <li> <a href="shop_list_right_sidebar.html"> Shop list right sidebar</a> </li>
                            <li> <a href="shop_grid_full_width.html"> Shop Full width </a> </li>
                          </ul>
                         
                        </div>
                      </div>
                    </div>
                  </li>-->
                  <li class="custom-menu"><a href="javascript:void(0)">Blog</a>
                    <ul class="dropdown">
                      <li> <a href="https://www.facebook.com/adnigam.ecommerce" target="_blank"> Blog &ndash; FaceBook</a></li>
                      <li> <a href="https://twitter.com/Adnigam_PvtLtd" target="_blank"> Blog &ndash; Twitter</a></li>
                      <li><a href="https://plus.google.com/u/0/b/110548629830419909285/110548629830419909285" target="_blank"> Blog &ndash; Google plues</a></li>
                    </ul>
                  </li>
                  <li><a href="<spring:url value="/static/contactus"/>">Contact</a></li>
                </ul>
                <span class="phone hidden-xs hidden-sm"><i class="fa fa-phone fa-lg"></i> 040-48516780</span>  </div>
            </div>
          </div>
        </div>
      </div>
    </nav>
  </header>
  <!-- end header --> 
  
  
<!-- mobile menu -->
<div id="jtv-mobile-menu" class="jtv-mobile-menu">
  <ul>
    <li class=""><a href="index.html">Home</a>
      <ul class="sub-menu">
        <li><a href="../index.html">Home Version 1</a></li>
        <li><a href="index.html">Home Version 2</a></li>
      </ul>
    </li>
    <li><a href="shop_grid.html">Pages</a>
      <ul>
        <li><a href="shop_grid.html" class="">Shop Pages </a>
          <ul>
            <li> <a href="shop_grid.html"> Shop grid </a> </li>
            <li> <a href="shop_grid_right_sidebar.html"> Shop grid right sidebar</a> </li>
            <li> <a href="shop_list.html"> Shop list </a> </li>
            <li> <a href="shop_list_right_sidebar.html"> Shop list right sidebar</a> </li>
            <li> <a href="shop_grid_full_width.html"> Shop Full width </a> </li>
          </ul>
        </li>
        <li><a href="shop_grid.html">Ecommerce Pages </a>
          <ul>
            <li> <a href="wishlist.html"> Wishlists </a> </li>
            <li> <a href="checkout.html"> Checkout </a> </li>
            <li> <a href="compare.html"> Compare </a> </li>
            <li> <a href="shopping_cart.html"> Shopping cart </a> </li>
          </ul>
        </li>
        <li> <a href="shop_grid.html">Static Pages </a>
          <ul>
            <li> <a href="create_account_page.html"> Create Account Page </a> </li>
            <li> <a href="about_us.html"> About Us </a> </li>
            <li> <a href="contact_us.html"> Contact us </a> </li>
            <li> <a href="404error.html"> Error 404 </a> </li>
            <li> <a href="faq.html"> FAQ </a> </li>
            <li> <a href="register_page.html"> Register Page </a> </li>
          </ul>
        </li>
        <li> <a href="shop_grid.html">Single Product Pages </a>
          <ul>
            <li><a href="single_product.html"> single product </a> </li>
            <li> <a href="single_product_left_sidebar.html"> single product left sidebar</a> </li>
            <li> <a href="single_product_right_sidebar.html"> single product right sidebar</a> </li>
            <li> <a href="single_product_magnific_popup.html"> single product magnific popup</a> </li>
            <li> <a href="cat-4-col.html"> 4 Column Sidebar</a> </li>
          </ul>
        </li>
        <li> <a href="shop_grid.html"> Blog Pages </a>
          <ul>
            <li><a href="blog_right_sidebar.html">Blog – Right sidebar </a></li>
            <li><a href="blog_left_sidebar.html">Blog – Left sidebar </a></li>
            <li><a href="blog_full_width.html">Blog_ - Full width</a></li>
            <li><a href="single_post.html">Single post </a></li>
          </ul>
        </li>
      </ul>
    </li>
    <li><a href="shop_grid.html">Women</a>
      <ul class="">
        <li><a href="">Clother</a>
          <ul>
            <li><a href="shop_grid.html">Cocktail</a></li>
            <li><a href="shop_grid.html">Day</a></li>
            <li><a href="shop_grid.html">Evening</a></li>
            <li><a href="shop_grid.html">Sports</a></li>
            <li><a href="shop_grid.html">Sexy Dress</a></li>
            <li><a href="shop_grid.html">Fsshion Skirt</a></li>
            <li><a href="shop_grid.html">Evening Dress</a></li>
            <li><a href="shop_grid.html">Children's Clothing</a></li>
          </ul>
        </li>
        <li><a href="shop_grid.html">Dress and skirt</a>
          <ul>
            <li><a href="shop_grid.html">Sports</a></li>
            <li><a href="shop_grid.html">Run</a></li>
            <li><a href="shop_grid.html">Sandals</a></li>
            <li><a href="shop_grid.html">Books</a></li>
            <li><a href="shop_grid.html">A-line Dress</a></li>
            <li><a href="shop_grid.html">Lacy Looks</a></li>
            <li><a href="shop_grid.html">Shirts-T-Shirts</a></li>
          </ul>
        </li>
        <li><a href="shop_grid.html">shoes</a>
          <ul>
            <li><a href="shop_grid.html">blazers</a></li>
            <li><a href="shop_grid.html">table</a></li>
            <li><a href="shop_grid.html">coats</a></li>
            <li><a href="shop_grid.html">Sports</a></li>
            <li><a href="shop_grid.html">kids</a></li>
            <li><a href="shop_grid.html">Sweater</a></li>
            <li><a href="shop_grid.html">Coat</a></li>
          </ul>
        </li>
      </ul>
    </li>
    <li class=""><a href="shop_grid.html">Men</a>
      <ul class="">
        <li><a href="shop_grid.html">Bages</a>
          <ul>
            <li><a href="shop_grid.html">Bootes Bages</a></li>
            <li><a href="shop_grid.html">Blazers</a></li>
            <li><a href="shop_grid.html">Mermaid</a></li>
          </ul>
        </li>
        <li><a href="shop_grid.html">Clothing</a>
          <ul>
            <li><a href="shop_grid.html">coats</a></li>
            <li><a href="shop_grid.html">T-shirt</a></li>
          </ul>
        </li>
        <li><a href="shop_grid.html">lingerie</a>
          <ul>
            <li><a href="shop_grid.html">brands</a></li>
            <li><a href="shop_grid.html">furniture</a></li>
          </ul>
        </li>
      </ul>
    </li>
    <li><a href="shop_grid.html">Handbags</a>
      <ul class="">
        <li><a href="shop_grid.html">Footwear Man</a>
          <ul>
            <li><a href="shop_grid.html">Gold Rigng</a></li>
            <li><a href="shop_grid.html">paltinum Rings</a></li>
            <li><a href="shop_grid.html">Silver Ring</a></li>
            <li><a href="shop_grid.html">Tungsten Ring</a></li>
          </ul>
        </li>
        <li><a href="shop_grid.html">Footwear Womens</a>
          <ul>
            <li><a href="shop_grid.html">Brand Gold</a></li>
            <li><a href="shop_grid.html">paltinum Rings</a></li>
            <li><a href="shop_grid.html">Silver Ring</a></li>
            <li><a href="shop_grid.html">Tungsten Ring</a></li>
          </ul>
        </li>
        <li><a href="shop_grid.html">Band</a>
          <ul>
            <li><a href="shop_grid.html">Platinum Necklaces</a></li>
            <li><a href="shop_grid.html">Gold Ring</a></li>
            <li><a href="shop_grid.html">silver ring</a></li>
            <li><a href="shop_grid.html">Diamond Bracelets</a></li>
          </ul>
        </li>
      </ul>
    </li>
    <li><a href="shop_grid.html">Shoes</a>
      <ul class="">
        <li><a href="shop_grid.html">Rings</a>
          <ul>
            <li><a href="shop_grid.html">Coats & jackets</a></li>
            <li><a href="shop_grid.html">blazers</a></li>
            <li><a href="shop_grid.html">raincoats</a></li>
          </ul>
        </li>
        <li><a href="shop_grid.html">Dresses</a>
          <ul>
            <li><a href="shop_grid.html">footwear</a></li>
            <li><a href="shop_grid.html">blazers</a></li>
            <li><a href="shop_grid.html">clog sandals</a></li>
            <li><a href="shop_grid.html">combat boots</a></li>
          </ul>
        </li>
        <li><a href="shop_grid.html">Accessories</a>
          <ul>
            <li><a href="shop_grid.html">bootees Bags</a></li>
            <li><a href="shop_grid.html">blazers</a></li>
            <li><a href="shop_grid.html">jackets</a></li>
            <li><a href="shop_grid.html">kids</a></li>
            <li><a href="shop_grid.html">shoes</a></li>
          </ul>
        </li>
        <li><a href="shop_grid.html">Top</a>
          <ul>
            <li><a href="shop_grid.html">briefs</a></li>
            <li><a href="shop_grid.html">camis</a></li>
            <li><a href="shop_grid.html">nigthwear</a></li>
            <li><a href="shop_grid.html">kids</a></li>
            <li><a href="shop_grid.html">shapewer</a></li>
          </ul>
        </li>
      </ul>
    </li>
  </ul>
</div>


