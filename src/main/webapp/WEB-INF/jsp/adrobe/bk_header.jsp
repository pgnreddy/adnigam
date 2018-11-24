<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/custom" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<custom:setting var="categoryPath" key="CATEGORIES_IMAGES_PREFIX_PATH" />
<custom:setting var="premiumPath" key="PREMIUM_IMAGES_PREFIX_PATH" />
<custom:setting var="imagesPath" key="IMAGES_PREFIX_PATH" />
<custom:setting var="applicationDomain" key="APPLICATION_DOMAIN" /> 
<custom:setting var="dataSecureURL" key="DATA_SECURE_URL" /> 

<c:set var="selectedLocation" value="${cookie.loc.value}"/>
<!-- HEADER -->
<div id="header" class="header">
    <div class="top-header">
        <div class="container">
            <div class="nav-top-links">
                <a class="first-item" href="#"><img alt="phone" src="<spring:url value="/resources/images/phone.png"/>" /><spring:message code="adrobe.phone"/></a>
                <a href="mailto:<spring:message code="adrobe.email"/>"><img alt="email" src="<spring:url value="/resources/images/email.png"/>" />Contact us today! </a>
            </div>
            <div class="city ">
                <div class="dropdown z-index">

                    <c:forEach var="loc" items="${locations}" >                        
                        <c:if test="${loc.id eq selectedLocation}">
                            <a class="current-open" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" href="#">${loc.name}</a>
                        </c:if>                             

                    </c:forEach> 
                    <c:if test="${empty selectedLocation || selectedLocation==-1}">
                        <a class="current-open" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" href="#">All</a>
                    </c:if>    

                    <ul class="dropdown-menu" role="menu" style="max-height:500px; overflow-x:scroll;">
                        <c:if test="${not empty selectedLocation}">
                            <li><a href="<spring:url value="/updateLocation?loc=-1"/>">All</a></li>
                            </c:if>
                            <c:forEach var="loc" items="${locations}" >
                                <c:if test="${loc.id ne selectedLocation}">
                                <li><a href="<spring:url value="/updateLocation?loc=${loc.id}"/>">${loc.name}</a></li>
                                </c:if>
                            </c:forEach>                                               
                    </ul>
                </div>
            </div>
            <!--
<div class="language ">
    <div class="dropdown">
          <a class="current-open" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" href="#">
          <img alt="email" src="assets/images/en.jpg" />English
          
          </a>
          <ul class="dropdown-menu" role="menu">
            <li><a href="#"><img alt="email" src="assets/images/en.jpg" />English</a></li>
        </ul>
    </div>
</div>
            -->


            <div class="support-link">
                <c:if test="${empty sessionScope.USER}">
                    <a href="#" data-toggle="modal" data-target="#adrobeLogin" >Login</a>
                    <a href="#" data-toggle="modal" data-target="#adrobeSignup">Signup</a>
                </c:if>

            </div>

            <c:if test="${not empty sessionScope.USER}">
                <div id="user-info-top" class="user-info pull-right">
                    <div class="dropdown">
                        <a class="current-open" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" href="#"><span>${sessionScope.USER.firstname} ${sessionScope.USER.lastname}</span></a>
                        <ul class="dropdown-menu mega_dropdown" role="menu">
                            <li><a href="<spring:url value="/myaccount/"/>">My Account</a></li>
                            <li><a href="<spring:url value="/myaccount/favourites"/>">My Wishlist</a></li>
                            <li><a href="<spring:url value="/logout"/>">Logout</a></li>
                        </ul>
                    </div>
                </div>
            </c:if>

        </div>
    </div>
    <!--/.top-header -->
    <!-- MAIN HEADER -->
    <div class="container main-header">
        <div class="row">
            <div class="col-xs-12 col-sm-3 logo">
                <a href="<spring:url value="/"/>"><img alt="Adrobe Shop" src="<spring:url value="/resources/images/logo-1.png"/>" /></a>
            </div>
            <div class="col-xs-6 col-sm-5 header-search-box">
                <form id="searchForm" class="form-inline">
                    <div class="form-group form-category">
                        <select class="select-category">
                            <option value="all">All Categories</option>
                            <c:forEach var="category" items="${allCategories}" varStatus="status">                               
                                <option value="${category.seo_title}" <c:if test="${category.seo_title eq categoryName}">selected</c:if>>${category.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group input-serach">
                        <input type="text" id="search" name="search" value="${param.search}" placeholder="Keyword here...">
                    </div>
                    <button class="pull-right btn-search"></button>
                </form>
            </div>

            <%--<div id="cart-block" class="col-xs-5 col-sm-2 shopping-cart-box">
                <a class="cart-link" href="order.html">
                    <span class="title">Shopping cart</span>
                    <span class="total">0 items - 0 &#8377;</span>
                    <span class="notify notify-left">0</span>
                </a>               
            </div>--%>

            <!-- Advanced Search -->
            <div class="col-xs-12 col-sm-12 col-md-2 advancedSearch">

                <ul class="list-inline">
                    <li>
                        <a class='advSearchBtn btn btn-default' data-placement='bottom' title="Price Range" href='#'><span class="glyphicon glyphicon-filter gi-2x brandColor"></span></a>
                        <div id="popover_content_wrapper" class="col-xs-5" style="display: none">
                            <div>
                                <div class="container">
                                    <h4>Price Range</h4>
                                    <br/>
                                    <form class="form-inline" role="form" id="priceSearch">
                                        <div class="form-group">
                                            <label for="priceStarts">Price:</label>
                                            <input type="number" class="form-control" id="priceMin" min="0"  value="${fn:split(param.prRange,"-")[0]}"/>                                
                                        </div>
                                        <div class="form-group">
                                            <label for="priceEnds">To:</label>
                                            <input type="number" class="form-control" id="priceMax" min="0" value="${fn:split(param.prRange,"-")[1]}"/>   
                                        </div>
                                        <br/>
                                        <button type="submit" class="btn btn-default advbtnSearch">Search</button>
                                    </form>
                                    <hr/>
                                    <h4>Offer Range(%)</h4>
                                    <br/>
                                    <form class="form-inline" role="form" id="discSearch">
                                        <div class="form-group">
                                            <label for="priceStarts">Offer:</label>
                                            <input type="number" class="form-control" id="discMin" min="0" max="100" value="${fn:split(param.dsRange,"-")[0]}"/>  
                                        </div>
                                        <div class="form-group">
                                            <label for="priceEnds">To:</label>
                                            <input type="number" class="form-control" id="discMax" min="0" max="100" value="${fn:split(param.dsRange,"-")[1]}"/>  
                                        </div>
                                        <br/>
                                        <button type="submit" class="btn btn-default advbtnSearch">Search</button>
                                    </form>
                                </div>

                            </div>
                        </div>
                    </li>
                </ul>

            </div>

            <!-- ./Advanced Search -->
            <div id="cart-block" class="col-xs-5 col-sm-2 shopping-cart-box">
                <jsp:include page="miniCart.jsp"/>
            </div>
        </div>



    </div>
    <!-- END MANIN HEADER -->
    <div id="nav-top-menu" class="nav-top-menu">
        <div class="container">
            <div class="row">
                <div class="col-sm-3" id="box-vertical-megamenus">
                    <div class="box-vertical-megamenus">
                        <h4 class="title">
                            <span class="title-menu">Categories</span>
                            <span class="btn-open-mobile pull-right home-page"><i class="fa fa-bars"></i></span>
                        </h4>
                        <c:set var="categoryDisplay" value=""/>
                        <c:if test="${empty premiumOffers}">
                            <c:set var="categoryDisplay" value="display: none;"/>
                        </c:if>
                        <div class="vertical-menu-content is-home" style="${categoryDisplay}">
                            <ul class="vertical-menu-list">
                                <c:forEach var="category" items="${categories}" varStatus="status">
                                    <li <c:if test="${status.index>10}">class="cat-link-orther"</c:if>>
                                        <a class="parent" href="<spring:url value="/ps/${category.seo_title}"/>"><img class="icon-menu" alt="${category.name}" src="<spring:url value="${categoryPath}${category.categoryIcon}"/>">${category.name}</a>
                                            <c:if test="${not empty category.subCategories}">
                                                <c:set var="subCategoriesSize" value="${fn:length(category.subCategories)}"/>
                                                <c:choose>
                                                    <c:when test="${subCategoriesSize gt 12}">
                                                        <c:set var="cols" value="3"/> 
                                                    </c:when>
                                                    <c:when test="${subCategoriesSize le 6}">
                                                     <c:set var="cols" value="1"/> 
                                                    </c:when>
                                                    <c:otherwise>
                                                    <c:set var="cols" value="2"/> 
                                                    </c:otherwise>
                                                </c:choose>
                                            <c:set var="colsSize" value="${subCategoriesSize/cols}"/>
                                        
                                            <c:if test="${subCategoriesSize%cols !=0}">
                                             <c:set var="colsSize" value="${colsSize+1}"/>
                                            </c:if>
                                        
                                            <div class="vertical-dropdown-menu">
                                                <div class="vertical-groups col-sm-12">
                                                    <c:forEach var="colIndex" begin="0" end="${cols-1}" >
                                                    <div class="mega-group col-sm-4">
                                                        <%--<h4 class="mega-group-header"><span>SubMenu Head</span></h4>--%>
                                                        <ul class="group-link-default">
                                                            <c:forEach var="subCat" begin="${colIndex*(colsSize)}" end="${colIndex*(colsSize)+colsSize-1}" items="${category.subCategories}">
                                                                <li><a href="<spring:url value="/ps/${subCat.seo_title}"/>">${subCat.name}</a></li>  
                                                            </c:forEach>
                                                        </ul>
                                                    </div> 
                                                    </c:forEach>
                                                   
                                                </div>
                                            </div>
                                        </c:if>
                                    </li>							
                                </c:forEach>


                            </ul>
                            <div class="all-category"><span class="open-cate">All Categories</span></div>
                        </div>
                        <%--</c:if>--%>
                    </div>
                </div>
                <div id="main-menu" class="col-sm-9 main-menu">
                    <nav class="navbar navbar-default">
                        <div class="container-fluid">
                            <div class="navbar-header">
                                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                                    <i class="fa fa-bars"></i>
                                </button>
                                <a class="navbar-brand" href="#">MENU</a>
                            </div>
                            <div id="navbar" class="navbar-collapse collapse">
                                <%--<ul class="nav navbar-nav">
                                    <li class="active"><a href="index.html">Home</a></li>
                                    <li><a href="fashion.html">Fashion</a></li>
                                    <li><a href="jewellery.html">Jewellery</a></li>
                                    <li><a href="electronics.html">Electronics</a></li>
                                    <li><a href="mobile_computers.html">Mobiles & Computers</a></li>
                                    <li><a href="real_estates.html">Real Estate</a></li>
                                    <li><a href="tours_travels.html">Travel</a></li>
                                </ul>--%>

                                <ul class="nav navbar-nav">
                                    <li class="active"><a href="<spring:url value="/index"/>">Home</a></li>
                                        <c:forEach var="category" items="${categories}" varStatus="status" end="4">
                                        <li>
                                            <a href="<spring:url value="/ps/${category.seo_title}"/>">${category.name}</a>
                                        </li>							
                                    </c:forEach>   
                                </ul>

                            </div><!--/.nav-collapse -->
                        </div>
                    </nav>
                </div>
            </div>
            <!-- userinfo on top-->
            <div id="form-search-opntop">
            </div>
            <!-- userinfo on top-->
            <div id="user-info-opntop">
            </div>
            <!-- CART ICON ON MMENU -->
            <div id="shopping-cart-box-ontop">
                <i class="fa fa-shopping-cart"></i>
                <div class="shopping-cart-box-ontop-content"></div>
            </div>
        </div>
    </div>
</div>
<!-- end header -->

<!-- Login -->
<div class="modal fade" id="adrobeLogin" role="dialog">
    <div class="modal-dialog">
        <!-- Modal content-->
        <div class="">
            <div class="authentication axis">
                <a href="#" data-dismiss="modal" class="pull-right"><span class="fa fa-close"></span></a>
                <h3>LOGIN</h3>
                <div id="loginError" class="alert alert-danger"></div>
                <form id="loginForm" class="form-horizontal" action="${dataSecureURL}login" target="new-loginsignup-iframe">                    
                    <label for="email_login">Email address</label>
                    <input id="email_login" type="text" name="userName" class="form-control">
                    <label for="password_login">Password</label>
                    <input id="password_login" type="password" name="password" class="form-control">
                    <div class="list-inline">
                        <li class="signup"><a href="#adrobeSignup" data-dismiss="modal" data-toggle="modal" data-target="">Signup</a> </li>
                        <li class="forgot-pass"><a href="#adrobeForgotPass" data-dismiss="modal" data-toggle="modal" data-target="">Forgot your password?</a></li>
                    </div>
                    <button id="loginsubmit" class="button"><i class="fa fa-lock"></i> Login</button>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- Signup -->
<div class="modal fade" id="adrobeSignup" role="dialog">
    <div class="modal-dialog">
        <!-- Modal content-->
        <div class="authentication">
            <a href="#" data-dismiss="modal" class="pull-right"><span class="fa fa-close"></span></a>
            <h3>SIGNUP</h3>
            <div id="message" class="alert alert-danger"></div>                
            <form class="form-horizontal" action="${dataSecureURL}signup" target="new-loginsignup-iframe" id="createaccount">

                <label for="firstName">Name</label>
                <input id="firstName" type="text" placeholder="First name" name="firstname" class="form-control"> 
                <input id="lastName" type="text"  placeholder="Last name" name="lastname" class="form-control">


                <label for="email_register">Email address</label>
                <input id="email_register" name="emailAddress" placeholder="example@example.com" type="text" class="form-control">

                <label for="telephone_number">Telephone Number</label>
                <input id="telephone_number" placeholder="Mobile" name="telephone" type="tel" class="form-control">

                <label for="password_register">Password</label>
                <input id="password" placeholder="Enter password" name="password" type="password" class="form-control">

                <label for="confirm_register">Confirm Password</label>
                <input id="confirm_register" placeholder="Confirm password" name="confirm" type="password" class="form-control">                   


                <p><a href="#adrobeLogin" data-dismiss="modal" data-toggle="modal" data-target="">Login</a>
                    <a href="#adrobeForgotPass" data-dismiss="modal" data-toggle="modal" data-target="">Forgot your password?</a></p>
                <button class="button"><i class="fa fa-user"></i> Create </button>
            </form>
        </div>
    </div>
</div>

<!-- Forgot Password -->
<div class="modal fade" id="adrobeForgotPass" role="dialog">
    <div class="modal-dialog">
        <!-- Modal content-->
        <div class="authentication">
            <a href="#" data-dismiss="modal" class="pull-right"><span class="fa fa-close"></span></a>
            <h3>Forgot Password?</h3>
            <div id="message" class="alert alert-danger"></div>
            <form id="adrobeForgotPassForm" class="form-horizontal" action="${dataSecureURL}forgotPassword" target="new-loginsignup-iframe">
                <label for="email_register">Email address</label>
                <input id="email_register" name="email" type="text" class="form-control">                    

                <button class="button"><i class="fa fa-user"></i> Send</button>
            </form>
        </div>
    </div>
</div>
<!-- Zipcode Area -->
<div id="zipAreaModal" class="modal fade" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <a href="#" data-dismiss="modal" class="pull-right"><span class="fa fa-close"></span></a>
                <h4 class="modal-title">Select your location</h4>
            </div>
            <div class="modal-body">
                <form>
                    <div class="form-group col-md-4">
                        <input id="zipCode" type="text" maxlength="6" class="form-control" placeholder="Zip Code">                        
                    </div>

                    <div class="form-group col-md-4">

                        <select class="form-control" id="selectArea">
                            <option value="-1">Select Area</option>
                            <c:forEach var="loc" items="${locations}" >
                                <option value="${loc.id}">${loc.name}</option>
                            </c:forEach> 

                        </select>
                    </div>
                    <button type="submit" class="btn btn-warning">Go</button>
                </form>
            </div>
        </div>
    </div>
</div>
<iframe style="display:none" class="hidden" width="0" height="0" id="new-login-signup-iframe" name="new-loginsignup-iframe" ></iframe> 

<script type="text/javascript" src="<spring:url value="/resources/lib/jquery/jquery-1.11.2.min.js"/>"></script>    
<script>
    var acceptDomain = '${applicationDomain}';
    var selectedLocation = '${selectedLocation}';
    var showLocationPopUp = '${showLocationPopUp}';

    $(document).ready(function() {

        var cart_block = $('#cart-block');
        var successCallback = function(html) {
            cart_block.html(html);
            cart_block.find(".cart-block").addClass("hover");

        };

        cart_block.delegate(".remove_link", "click", function(e) {
            e.preventDefault();
            var $this = $(this);
            var offerId = $this.attr("pid");
            var isReserve = $this.attr("reserve");
            var url = '<spring:url value="/cart/removeItem"/>' + "?cP=true&p=" + offerId + (isReserve == "true" ? "&reserve=true" : "");

            $.ajax({
                url: url,
                success: successCallback
            });

        });

        var loginForm = $("#loginForm");

        var loginCallback = function(data) {
            data = $.parseJSON(data);
            if (data.status && data.status == "success") {
                document.location.reload(true);
            } else {
                $("#loginError").html(data.msg).show();

            }

            return false;
        }


        loginForm.find("#loginsubmit").click(function(e) {
            e.preventDefault();
            callback = loginCallback;
            loginForm.submit();
        });

        $('#adrobeLogin').on('show.bs.modal', function(event) {
            $(this).find("#loginError").hide();
            loginForm[0].reset();
        });

        //forgot password

        var forgotCallback = function(data) {
            data = $.parseJSON(data);
            if (data.msg) {
                forgotModel.find("#message").html(data.msg).show();
            }

            return false;
        }

        var forgotModel = $("#adrobeForgotPass");
        forgotModel.find("button").click(function(e) {
            e.preventDefault();
            callback = forgotCallback;
            forgotModel.find("form").submit();
        });

        forgotModel.on('show.bs.modal', function(event) {
            $(this).find("#message").hide();
            $(this).find("form").get(0).reset();

        });


        //signup


        var signupCallback = function(data) {
            data = $.parseJSON(data);

            if (data.status && data.status == "success") {
                document.location.reload(true);
            } else {
                signupModel.find("#message").html(data.msg).show();

            }


            return false;
        }

        var signupModel = $("#adrobeSignup");
        signupModel.find("button").click(function(e) {
            e.preventDefault();
            callback = signupCallback;
            signupModel.find("form").submit();
        });

        signupModel.on('show.bs.modal', function(event) {
            $(this).find("#message").hide();
            $(this).find("form").get(0).reset();

        });


        var searchForm = $("#searchForm");
        var searchFormCategory = searchForm.find("select");
        var searchFormText = searchForm.find("#search");

        searchForm.find("button").click(function(e) {
            e.preventDefault();
            var selectedCategory = searchFormCategory.val();
            var searchText = $.trim(searchFormText.val());
            if (searchText.length > 2) {
                var url = "<%=request.getContextPath()%>/ps/" + selectedCategory + "?search=" + searchText;
                document.location.href = url;

            }

            return false;
        });

        if (showLocationPopUp == 'true' && (!selectedLocation || selectedLocation < 0)) {
            $("#zipAreaModal").modal('show');
        }

        var zipcodeModal = $("#zipAreaModal");

        zipcodeModal.find("button").click(function(e) {
            e.preventDefault();
            var zipcode = $.trim(zipcodeModal.find("#zipCode").val());
            var location = zipcodeModal.find("#selectArea").val();
            if (location < 0 && (zipcode.length != 6 || isNaN(zipcode))) {
                return false;
            }

            document.location.href = "<%=request.getContextPath()%>/updateLocation?loc=" + location + "&zip=" + zipcode;
        });

        $(document).delegate("#priceSearch", "submit", function(e) {
            e.preventDefault();
            var $this = $(this);
            var priceMin = $this.find("#priceMin").val();
            var priceMax = $this.find("#priceMax").val();

            if ($.trim(priceMin) == '' || $.trim(priceMax) == '') {
                return false;
            }
            if (isNaN(priceMin) || isNaN(priceMax)) {
                return false;
            }

            var url = "<%=request.getContextPath()%>/ps/all?prRange=" + priceMin + "-" + priceMax;
            document.location.href = url;

        });


        $(document).delegate("#discSearch", "submit", function(e) {
            e.preventDefault();
            var $this = $(this);
            var priceMin = $this.find("#discMin").val();
            var priceMax = $this.find("#discMax").val();

            if ($.trim(priceMin) == '' || $.trim(priceMax) == '') {
                return false;
            }
            if (isNaN(priceMin) || isNaN(priceMax)) {
                return false;
            }

            var url = "<%=request.getContextPath()%>/ps/all?dsRange=" + priceMin + "-" + priceMax;
            document.location.href = url;

        });



    });


</script>
<script>
    $(document).ready(function() {
        $('[data-toggle="popover"]').popover();
    });
</script>
<script>
    $(document).ready(function() {
        $('.advSearchBtn').popover({
            html: true,
            content: function() {
                return $('#popover_content_wrapper').html();
            }
        });
    });
</script>
<script type="text/javascript">
    $('[data-toggle="popover"]').popover({
        container: 'body'
    });
</script>