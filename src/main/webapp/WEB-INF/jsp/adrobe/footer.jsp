<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

 <!-- our clients Slider -->
  
  <div class="our-clients">
    <div class="container">
      <div class="slider-items-products">
        <div id="our-clients-slider" class="product-flexslider hidden-buttons">
          <div class="slider-items slider-width-col6"> 
            
            <!-- Item -->
            <div class="item"> <a href="#"><img src="<spring:url value="/resources/images/brand1.png" />" alt="Image" class="grayscale"></a> </div>
            <!-- End Item --> 
            
            <!-- Item -->
            <div class="item"> <a href="#"><img src="<spring:url value="/resources/images/brand2.png" />" alt="Image" class="grayscale"></a> </div>
            <!-- End Item --> 
            
            <!-- Item -->
            <div class="item"> <a href="#"><img src="<spring:url value="/resources/images/brand3.png"/>" alt="Image" class="grayscale"></a> </div>
            <!-- End Item --> 
            
            <!-- Item -->
            <div class="item"> <a href="#"><img src="<spring:url value="/resources/images/brand4.png" />" alt="Image" class="grayscale"></a> </div>
            <!-- End Item --> 
            <!-- Item -->
            <div class="item"> <a href="#"><img src="<spring:url value="/resources/images/brand5.png"/>"  alt="Image" class="grayscale"></a> </div>
            <!-- End Item --> 
            <!-- Item -->
            <div class="item"> <a href="#"><img src="<spring:url value="/resources/images/brand6.png"/>"  alt="Image" class="grayscale"></a> </div>
            <!-- End Item --> 
            <!-- Item -->
            <div class="item"> <a href="#"><img src="<spring:url value="/resources/images/brand7.png" />" alt="Image" class="grayscale"></a> </div>
            <!-- End Item --> 
            
          </div>
        </div>
      </div>
    </div>
  </div>






<!-- Footer -->
  

<footer>
    <div class="container">
      <div class="row">
        <div class="footer-newsletter">
          <div class="container">
            <div class="row">
              <div class="col-md-3 col-sm-5">
                <h3 class="">Sign up for newsletter</h3>
                <span>Get the latest deals and special offers</span></div>
              <div class="col-md-5 col-sm-7">
                <form id="newsletter-validate-detail" action="#">
                  <div class="newsletter-inner">
                    <input class="newsletter-email" name='Email' placeholder='Enter Your Email'/>
                    <button class="button subscribe" type="submit" title="Subscribe">Subscribe</button>
                  </div>
                </form>
              </div>
              <div class="col-md-4 col-sm-12">
                <div class="social">
                  <ul class="inline-mode">
                    <li class="social-network fb"><a title="Connect us on Facebook" target="_blank" href="https://www.facebook.com/adnigam.ecommerce"><i class="fa fa-facebook"></i></a></li>
                    <li class="social-network googleplus"><a title="Connect us on Google+" target="_blank" href="https://plus.google.com/u/0/b/110548629830419909285/110548629830419909285"><i class="fa fa-google-plus"></i></a></li>
                    <li class="social-network tw"><a title="Connect us on Twitter" target="_blank" href="https://twitter.com/Adnigam_PvtLtd"><i class="fa fa-twitter"></i></a></li>
<!--                    <li class="social-network linkedin"><a title="Connect us on Linkedin" target="_blank" href="https://www.pinterest.com/"><i class="fa fa-linkedin"></i></a></li>
                    <li class="social-network rss"><a title="Connect us on Instagram" target="_blank" href="#"><i class="fa fa-rss"></i></a></li>
                    <li class="social-network instagram"><a title="Connect us on Instagram" target="_blank" href="https://instagram.com/"><i class="fa fa-instagram"></i></a></li>-->
                  </ul>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="col-sm-6 col-md-4 col-xs-12 col-lg-3">
            <div class="footer-logo"><a href="index.html"><img src="<spring:url value="/resources/images/footer_logo2.png" />" alt="fotter logo"></a> </div>
          <div class="footer-content">
            <div class="email"> <i class="fa fa-envelope"></i>
              <p>info@adnigam.com</p>
            </div>
            <div class="phone"> <i class="fa fa-phone"></i>
              <p>040-48516780</p>
            </div>
            <div class="address"> <i class="fa fa-map-marker"></i>
              <p>SCB #3-14-022,1st Floor
SP Road, Begumpet, Secunderabad,
Hyderabad, Telangana - 500003.</p>
            </div>
          </div>
        </div>
        <div class="col-sm-6 col-md-3 col-xs-12 col-lg-3 collapsed-block">
          <div class="footer-links">
            <h3 class="links-title">Company<a class="expander visible-xs" href="#TabBlock-1">+</a></h3>
            <div class="tabBlock" id="TabBlock-1">
              <ul class="list-links list-unstyled">
                  <li><a href="<spring:url value="/static/aboutus"/>">About Us</a></li>
                  <li><a href="<spring:url value="/static/privacy_policy"/>">Privacy Policy</a></li>
                  <li><a href="<spring:url value="/static/returnPolicy"/>">Return policy</a></li>
                <li><a href="<spring:url value="/static/terms_conditions"/>">Terms & Conditions</a></li>
                
              </ul>
            </div>
          </div>
        </div>
        <div class="col-sm-6 col-md-3 col-xs-12 col-lg-3 collapsed-block">
          <div class="footer-links">
            <h3 class="links-title">VENDOR<a class="expander visible-xs" href="#TabBlock-3">+</a></h3>
            <div class="tabBlock" id="TabBlock-3">
              <ul class="list-links list-unstyled">
                <li> <a href="<spring:url value="/static/packages"/>"> Packages</a> </li>
                <li> <a href="<spring:url value="/static/faq"/>">FAQ</a> </li>
                <li> <a href="<spring:url value="/static/sellwithus"/>">Sell With US</a> </li>
               
              </ul>
            </div>
          </div>
        </div>
        <div class="col-sm-6 col-md-2 col-xs-12 col-lg-3 collapsed-block">
          <div class="footer-links">
            <h3 class="links-title">SUPPORT<a class="expander visible-xs" href="#TabBlock-4">+</a></h3>
            <div class="tabBlock" id="TabBlock-4">
              <ul class="list-links list-unstyled">
                <li> <a href="<spring:url value="http://www.adnigam.biz/"/>">Order groceries</a> </li>
                <li> <a href="<spring:url value="/static/terms_conditions"/>">Terms & Conditions</a> </li>
                <li> <a href="<spring:url value="/static/contactus"/>">Contact Us</a> </li>
               
              </ul>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="footer-coppyright">
      <div class="container">
        <div class="row">
          <div class="col-sm-6 col-xs-12 coppyright"> Copyright © 2017 <a href="javascript:void(0)"> Adnigam </a>. All Rights Reserved. </div>
          <div class="col-sm-6 col-xs-12">
            <div class="payment">
              <ul>
                  <li><a href="javascript:void(0)"><img title="Visa" alt="Visa"  src="<spring:url value="/resources/images/visa.png"/>"></a></li>
                  <li><a href="javascript:void(0)"><img title="Paypal" alt="Paypal"  src="<spring:url value="/resources/images/paypal.png"/>"></a></li>
                  <li><a href="javascript:void(0)"><img title="Discover" alt="Discover"  src="<spring:url value="/resources/images/discover.png"/>"></a></li>
                  <li><a href="javascript:void(0)"><img title="Master Card" alt="Master Card"  src="<spring:url value="/resources/images/master-card.png"/>"></a></li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </div>
  </footer>
   <!--min ADDCART declared modal view base  -->           
   <div id="minAddCart">
 <jsp:include page="miniCart.jsp"/>
   </div>
   <!--end min ADDCART-->
  <!--Newsletter Popup Start -->
<!--  <div id="myModal" class="modal fade">
    <div class="modal-dialog newsletter-popup">
      <div class="modal-content">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <div class="modal-body">
          <h2 class="modal-title">Top Fashion</h2>
          <form id="newsletter-form" method="post" action="#">
            <div class="content-subscribe">
              <div class="form-subscribe-header">
                <label>Register now to get updates on discount & coupons</label>
              </div>
              <div class="input-box">
                <input type="text" class="input-text newsletter-subscribe" title="Sign up for our newsletter" name="email" placeholder="Enter your email address">
              </div>
              <div class="actions">
                <button class="button-subscribe" title="Subscribe" type="submit">Subscribe</button>
              </div>
            </div>
          </form>
          <div class="subscribe-bottom">
            <input name="notshowpopup" id="notshowpopup" type="checkbox">
            Don’t show this popup again </div>
        </div>
      </div>
    </div>
  </div>-->
  <!--End of Newsletter Popup-->
  <!--QUICKVIEW-->
  <div id="quick_view_id">
      <jsp:include page="quickView.jsp"/>
  </div>
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
<script>
        methodUrl={
    baseUrl:'${pageContext.request.contextPath}/'
            };  
</script>