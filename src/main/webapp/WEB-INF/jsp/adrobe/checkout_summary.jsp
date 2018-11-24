<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/custom" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--<custom:setting var="imagesPath" key="IMAGES_PREFIX_PATH" />--%>
<c:set var="imagesPath" value="https://s3-us-west-2.amazonaws.com/adrobe.in.assets/" />

<!DOCTYPE html>
<!-- page wapper-->
<div class="columns-container">
    <div class="container" id="columns">
        <!-- breadcrumb -->
        <div class="breadcrumb clearfix">
            <a class="home" href="<spring:url value="/"/>" title="Return to Home">Home</a>
            <span class="navigation-pipe">&nbsp;</span>
            <a href="<spring:url value="/cart/"/>"><span class="navigation_page">Your shopping cart</span></a>
        </div>
        <!-- ./breadcrumb -->
        <!-- page heading-->
        <h2 class="page-heading no-line">
            <span class="page-heading-title2">Cart Summary</span>
        </h2>
        <!-- ../page heading-->
        <div class="page-content page-order">
            <ul class="step">
                <li><span>01. Sign in</span></li>
                <li><a href="<spring:url value="/checkout/"/>"><span>02. Address</span></a></li>
                <li class="current-step"><span>03. Summary</span></li>
                <li><span>04. Payment</span></li>
</ul>
<div class="container shiippingaddrss"> 
<div class="row">           

<div class="col-sm-6 col-md-6 col-md-4">
<div class="cart-block-content1">
<c:set var="productCouponPresent" value="0"/>
<c:set var="address" value="${sessionScope.CART.shippingAddress}"/>
<address>
<h3>Shipping Address</h3>
<br/>
<strong>${address.entryFirstname} &nbsp; ${address.entryLastname}</strong><br/>
<p>${address.entryStreetAddress}</p>
<p>${address.entryCity}</p>
<p>${address.entryState}</p>
<p>${address.entryPostcode}</p>
</address>
</div>
</div>

</div>
</div>

<div class="order-detail-content container">
<c:set var="itemsMap" value="${sessionScope.CART.itemsMap}"/> 
<c:if test="${not empty itemsMap}">
<c:forEach var="entry" items="${itemsMap}" varStatus="status">
<c:set var="orderProductItem" value="${entry.value}"/>
<c:choose>
<c:when test="${orderProductItem.coupon==true}">
<c:set var="productCouponPresent" value="1" />
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
<div class="col-md-2 col-xs-12 cartproname"><a href="#">${orderProductItem.product.title}</a></div>
<div class="col-md-1">
<div class="cart_avail">
<c:if test="${orderProductItem.coupon==true}">
<span class="label label-success">Coupon</span>
</c:if>
</div>
</div>
<div class="col-md-2 col-xs-12">${orderProductItem.userRequest}</div>
<div class="col-md-2 col-xs-12 margtopcart"><fmt:formatNumber value="${orderProductItem.offerPrice}" type="CURRENCY" pattern="Rs ###,##0"/></div>
<div class="col-md-1 col-xs-4 cartrsmargtop">
<span style="font-weight:bold;">Qty</span>
</div>
<div class="col-md-1 col-xs-12">${orderProductItem.quantity}</div>
<div class="col-md-1 col-xs-4 cartrsmargtop">
<span style="font-weight:bold;"><fmt:formatNumber value="${orderProductItem.subprice}" type="CURRENCY" pattern="Rs ###,##0"/></span>
</div>

</div>                                

</c:forEach>                                


<div class="row">
<c:choose>
<c:when test="${not empty sessionScope.CART && not empty sessionScope.CART.coupon}">

<div class="col-md-4 col-xs-4">
<strong>Sub Total : <fmt:formatNumber value="${sessionScope.CART.actualTotalprice}" type="CURRENCY" pattern="Rs ###,##0.00"/></strong>
</div>
<div class="col-md-4 col-xs-4">
Coupon (${sessionScope.CART.coupon.code}) <a href="javascript:void(0)" class="removeCoupon">remove</a>
<strong>-<fmt:formatNumber value="${sessionScope.CART.couponDiscountPrice}" type="CURRENCY" pattern="Rs ###,##0.00"/> </strong>
</div>
<div class="col-md-4 col-xs-4">
<strong>Total : <fmt:formatNumber value="${sessionScope.CART.totalprice}" type="CURRENCY" pattern="Rs ###,##0.00"/> </strong>
</div>

</c:when>
<c:otherwise>

<div class="container shiippingaddrss">
<div class="row">
<div class="col-md-12 col-xs-12 text-right">
<strong>Total : <fmt:formatNumber value="${sessionScope.CART.totalprice}" type="CURRENCY" pattern="Rs ###,##0.00"/> </strong>
</div>
</div>
</div>

</c:otherwise>
</c:choose>


<c:if test="${sessionScope.CART.totalprice>0}">
<div class="col-sm-12 well">
<fieldset>
<p style="color:red;">
Choose payment Method Below & Click Proceed for payment Button
</p>
<div class="checkbox checkbox-info checkbox-circle">
<c:choose>
<c:when test="${not empty itemsMap}">
<c:choose>
<c:when test="${productCouponPresent eq '1'}">
<input id="COD" name="pType" class="payment_method styled" type="radio"  value="cod" disabled> 
</c:when>
<c:otherwise>
<input id="COD" name="pType" class="payment_method styled" type="radio"  value="cod">    
</c:otherwise>
</c:choose>
<label for="COD">
Cash on Delivery
</label>
</c:when>
<c:otherwise>
<input id="COD" name="pType" class="payment_method styled" type="radio"  value="cod">
<label for="COD">
Cash on Delivery
</label>
</c:otherwise>
</c:choose>

</div>
<div class="checkbox checkbox-info checkbox-circle">
<input id="CDN" name="pType" class="payment_method styled" type="radio" checked="checked" value="ccav">
<label for="CDN">
Credit Card / Debit card / Net Banking
</label>
</div>
</fieldset>
</div>
</c:if>

</div>
</c:if>



<div class="row">
<div class="cart_navigation">

<div class="col-md-6 col-xs-6 text-right">
<a class="btn btn-custom-order small  btn-req" href="<spring:url value="/"/>">Continue shopping</a>
</div>

<div class="col-md-6 col-xs-6 text-left">
<a id="paymentButton" class="btn btn-custom-order small  btn-req" href="<spring:url value="/checkout/payment"/>">Proceed to payment</a>
</div> 
               
</div>
</div>


</div>
</div>
</div>
</div>
<!-- ./page wapper-->
<script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','https://www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-72951558-3', 'auto');
  ga('send', 'pageview');

</script>
<script>
    $(document).ready(function(){
        
        <c:if test="${sessionScope.CART.totalprice>0}">
        var cod= $("#COD");
        var cdn= $("#CDN");
        var selectedPType = "ccav";
        $(".payment_method").click(function(){
            var id = $(this).attr("id");            
            if("COD"==id){
                cdn.attr("checked",false);
                selectedPType = "cod";
            }else{
                cod.attr("checked",false);
                selectedPType = "ccav";
            }
        });
        
        $("#paymentButton").click(function(e){
            e.preventDefault();
            window.location.href="<%=request.getContextPath()%>/checkout/payment?pType="+selectedPType;
        });
        
        </c:if> 
    });
    </script>
