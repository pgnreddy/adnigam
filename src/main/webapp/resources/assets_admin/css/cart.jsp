<!-- page wapper-->
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/custom" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--<custom:setting var="imagesPath" key="IMAGES_PREFIX_PATH" />--%>
<c:set var="imagesPath" value="https://s3-us-west-2.amazonaws.com/adrobe.in.assets/" />
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<c:set var="itemsMap" value="${sessionScope.CART.itemsMap}"/> 
<div class="columns-container">
    <div class="container" id="columns">
        <!-- breadcrumb -->
        <div class="breadcrumb clearfix">
            <a class="home" href="<spring:url value="/"/>" title="Return to Home">Home</a>
            <span class="navigation-pipe">&nbsp;</span>
            <span class="navigation_page">Your shopping cart</span>
        </div>
        <!-- ./breadcrumb -->
        <!-- page heading-->
        <h2 class="page-heading no-line">
            <span class="page-heading-title2">Shopping Cart Summary</span>
        </h2>
        <!-- ../page heading-->
        <div class="page-content page-order">            
            <div class="heading-counter warning">Your shopping cart contains:
                <span>${fn:length(itemsMap)} Product</span>
            </div>
            <c:if test="${not empty param.error}">
                <div class="heading-counter alert-danger">  
                    ${param.error}
                </div>
            </c:if>
<div class="order-detail-content">
<c:if test="${not empty itemsMap}">
<c:forEach var="entry" items="${itemsMap}" varStatus="status">
<c:set var="orderProductItem" value="${entry.value}"/>
<c:choose>
<c:when test="${orderProductItem.reserved==true}">
<c:set var="reserveQuery" value="&reserve=true"/>
</c:when>
<c:otherwise>
<c:set var="reserveQuery" value=""/>
</c:otherwise>
</c:choose>
<c:choose>
<c:when test="${orderProductItem.coupon==true}">
<c:set var="couponQuery" value="&coupon=true"/>
</c:when>
<c:otherwise>
<c:set var="couponQuery" value=""/>
</c:otherwise>
</c:choose>
<div class="row cartrow">


<div class="col-md-1 col-xs-12">
<a href="#"><img src="<spring:url value="${imagesPath}${orderProductItem.product.image}"/>" alt="Product"></a>
</div>
<div class="col-md-2 col-xs-12 cartproname">
<p class="product-name"><a href="#">${orderProductItem.product.title}</a></p>
</div>
<div class="col-md-1">
<div class="cart_avail">
<c:if test="${orderProductItem.coupon==true}">
<span class="label label-success">Coupon</span>
</c:if>
</div>
</div>
<div class="col-md-3 col-xs-12">
<form index="${status.count}" action="<spring:url value="/cart/update"/>" method="post">
<input type="hidden" name="p" value="${orderProductItem.product.offerID}"/>
<input type="hidden" name="reserve" value="${orderProductItem.reserved}"/>
<textarea class="form-control cartdecform" name="userRequest" rows="1" cols="15" id="user-request" placeholder="Enter your request" >${orderProductItem.userRequest}</textarea>
<a href="#" type="submit" class="save_userRequest btn btn-custom-order small pull-right btn-req">OK</a>
</form>
</div>
<div class="col-md-2 col-xs-12 margtopcart">
<input id="qty_${status.count}" pid="${orderProductItem.product.offerID}" reserve="${orderProductItem.reserved}" coupon="${orderProductItem.coupon}" class="form-control secondinputcart" type="text" value="${orderProductItem.quantity}" pre_value="${orderProductItem.quantity}">
<a href="#" index="${status.count}" class="save_quantity btn btn-custom-order small pull-right btn-req">Save</a>
</div>
<div class="col-md-1 col-xs-4 cartrsmargtop">
<span style="font-weight:bold;"><fmt:formatNumber value="${orderProductItem.offerPrice}" type="CURRENCY" pattern="Rs ###,##0"/></span>
</div>

<div class="col-md-1 col-xs-4 cartrsmargtop">
<span style="font-weight:bold;"><fmt:formatNumber value="${orderProductItem.subprice}" type="CURRENCY" pattern="Rs ###,##0"/></span>
</div>
<div class="col-md-1 col-xs-4 cartdelmargtop text-right">
<a class="btn btn-custom-order btn-req" href="<spring:url value="/cart/removeItem?p=${orderProductItem.product.offerID}${reserveQuery}${couponQuery}"/>"><i class="fa fa-trash-o"></i></a>
</div>
</div>
</c:forEach>


<div class="row totalcostandcoupn">
<div class="col-md-6 col-xs-12"><c:if test="${not empty itemsMap}">
<div id="couponContainer" class="cart_navigation">
<strong class="title">Coupon </strong>
<p class="text">If you have a coupon, add here.</p>
<p id="couponMessage" class="error"></p>
<input id="couponCode" name="couponCode" class="form-control input-sm" type="text" placeholder="Enter coupon code"/>
<a href="#" id="couponSubmit" index="1" class="btn btn-custom-order small  btn-req">Submit</a>
</div>
</c:if></div>
<div class="col-md-6 col-xs-12 text-right totalcartbill"><div id="totals_container">
<jsp:include page="couponResponse.jsp"/>
</div></div>
</div>










</c:if>


</div>
            
<div class="row">
<div class="cart_navigation">
<div class="col-md-6 col-xs-6 text-right">
<a class="btn btn-custom-order small  btn-req" href="<spring:url value="/"/>">Continue shopping</a>
</div>
<div class="col-md-6 col-xs-6 text-left">
<c:if test="${not empty itemsMap}">
<a class="btn btn-custom-order small  btn-req" href="<spring:url value="/checkout/"/>">Proceed to checkout</a>
</c:if>
</div>                
    
    
    
</div>
</div>            
            
        </div>
    </div>
</div>
<!-- ./page wapper-->

<script>
    $(document).ready(function() {

        $('[data-toggle="popover"]').popover({
        });

        $(document).delegate(".order-detail-content form a", "click", function(e) {
            e.preventDefault();
            var $this = $(this).parent();
            var index = $this.attr("index");
            var textbox = $this.find("textarea");

            if (textbox) {
                var val = $.trim(textbox.val());
                if (val != "") {
                    $this.submit();
                } else {
                    return false;
                }
            }

        });


        $(document).delegate(".save_quantity", "click", function(e) {
            e.preventDefault();
            var $this = $(this);
            var index = $this.attr("index");
            var textbox = $("#qty_" + index);

            if (textbox) {
                var isReserve = textbox.attr("reserve");
                var isCoupon = textbox.attr("coupon");
                var pid = textbox.attr("pid");
                var val = $.trim(textbox.val());
                var pre_val = textbox.attr("pre_value");

                if (!isNaN(val) && parseInt(val) > 0) {
                    var url = "<%=request.getContextPath()%>/cart/update?p=" + pid + "&q=" + val + (isReserve == "true" ? "&reserve=true" : "") + (isCoupon == "true" ? "&coupon=true" : "");
                    document.location.href = url;
                } else {
                    textbox.val(pre_val);
                }
            }

        });

        var couponMessage = $("#couponMessage");
        var totals_container = $("#totals_container");
        $(document).delegate("#couponContainer #couponSubmit", "click", function(e) {
            e.preventDefault();
            couponMessage.html("");
            var couponCode = $("#couponContainer #couponCode").val();

            if ($.trim(couponCode) != '') {
                var url = "<%=request.getContextPath()%>/cart/addCoupon?couponCode=" + couponCode;

                $.ajax({
                    url: url,
                    success: function(data) {
                        if (data) {
                            data = $.trim(data);
                            if (data.indexOf("failed") != -1) {
                                couponMessage.html("Invalid coupon");
                            } else {
                                couponMessage.html("");
                                totals_container.html(data);
                            }
                        }
                    }
                });
            }
            return false;

        });


        $(document).delegate("#totals_container .removeCoupon", "click", function(e) {
            e.preventDefault();
            couponMessage.html("");
            if ($.trim(couponCode) != '') {
                var url = "<%=request.getContextPath()%>/cart/deleteCoupon";

                $.ajax({
                    url: url,
                    success: function(data) {
                        if (data) {
                            data = $.trim(data);
                            totals_container.html(data);
                        }
                    }
                });
            }
            return false;

        });


    });

</script>