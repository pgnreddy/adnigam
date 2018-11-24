<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/custom" %> 
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!-- page wapper-->
<div class="columns-container">
<div class="container">
<!-- breadcrumb -->
<div class="breadcrumb clearfix">
<a class="home" href="<spring:url value="/"/>" title="Return to Home">Home</a>
<span class="navigation-pipe">&nbsp;</span>
<a href="<spring:url value="/cart/"/>"><span class="navigation_page">Your shopping cart</span></a>
</div>
<!-- ./breadcrumb -->
<!-- page heading-->
<h2 class="page-heading no-line">
<span class="page-heading-title2">Cart Address</span>
</h2>
<!-- ../page heading-->
<div class="page-content page-order">
<ul class="step">
<li><span>01. Sign in</span></li>
<li class="current-step"><span>02. Address</span></li>
<c:choose>
<c:when test="${not empty sessionScope.CART.shippingAddress}">
<li><a href="<spring:url value="/checkout/summary"/>"><span>03. Summary</span></a></li>
</c:when>
<c:otherwise>
<li><span>03. Summary</span></li>
</c:otherwise>
</c:choose>                
<li><span>04. Payment</span></li>
</ul>
<%--<div class="heading-counter warning">Your shopping cart contains:
<span>1 Product</span>
</div>--%>

<div class="row" style="margin-top:20px;">
<c:if test="${not empty addresses}">

<div class="col-sm-6 col-md-6 col-md-6">
<div class="cart-block-content">
<h3>Address Book Entries</h3>
<hr/>
<c:forEach var="selectedType" begin="1" end="2" step="1">
<c:forEach var="address" items="${addresses}">
<c:set var="showAddress" value="false"/> 
<c:choose>
<c:when test="${selectedType==1}">
<c:if test="${not empty sessionScope.CART.shippingAddress && (sessionScope.CART.shippingAddress.addressBookId==address.addressBookId)}">
<c:set var="showAddress" value="true"/> 

</c:if>
</c:when>
<c:otherwise>
<c:if test="${empty sessionScope.CART.shippingAddress || (sessionScope.CART.shippingAddress.addressBookId!=address.addressBookId)}">
<c:set var="showAddress" value="true"/> 

</c:if>
</c:otherwise>
</c:choose>

<c:if test="${showAddress}">
<address class="addressborder" <c:if test="${selectedType==1}">class="alert-success"</c:if>>
<strong>${address.entryFirstname} &nbsp; ${address.entryLastname}</strong><br/>
<p>${address.entryStreetAddress}</p>
<p>${address.entryCity}</p>
<p>${address.entryState}</p>
<p>${address.entryPostcode}</p>
<p>
<br/>
<c:choose>
<c:when test="${selectedType==1}">
<a class="button" href="<spring:url value="/checkout/shipping?selectedAddress=${address.addressBookId}"/>">Selected</a>
</c:when>
<c:otherwise>
<a class="button" href="<spring:url value="/checkout/shipping?selectedAddress=${address.addressBookId}"/>">Select</a>
</c:otherwise>
</c:choose>
</p>
</address>
<br/>
</c:if>
</c:forEach>  
</c:forEach>
</div>
</div>
</c:if>
<div class="col-sm-6 col-md-6 col-md-6">

<h3>Shipping Address</h3>
<hr/>                                       
<form id="addressForm" action="<spring:url value="/checkout/shipping"/>" method="post">
<div id="error" class="alert-danger" style="display:none"></div>

<div class="row">

<div class="col-md-12">
<div class="form-group">
<label for="first_name">First name</label>
<input id="first_name" type="text" name="entryFirstname" class="form-control"/>                                           
</div>
</div>

<div class="col-md-12">
<label for="last_name">Last name</label>
<input id="last_name" type="text" name="entryLastname" class="form-control"/>
<div class="form-group">
</div>
</div>

<div class="col-md-12">
<div class="form-group">
<label for="address">Address</label>
<textarea id="address" name="entryStreetAddress" class="form-control"></textarea>
</div>
</div>

<div class="col-md-12">
<div class="form-group">
<label for="city_name">City</label>
<input id="city_name" name="entryCity" type="text" class="form-control"/>
</div>
</div>


<div class="col-md-12">
<div class="form-group">
<label for="state_name">State</label>
<input id="state_name" name="entryState" type="text" class="form-control"/>
</div>
</div>


<div class="col-md-12">
<div class="form-group">
<label for="postal_code">Postal Code</label>
<input id="postal_code" name="entryPostcode" type="text" class="form-control"/>
</div>
</div>


<div class="col-md-12">
<div class="form-group">
<button class="button"><i class="fa fa-user"></i> Save &amp; continue </button>
</div>
</div>


</div>

</form>

</div>

</address>

</div>

<div class="row">
<div class="col-md-12">
<div class="cart_navigation">
<a class="btn btn-custom-order small  btn-req" href="<spring:url value="/"/>">Continue shopping</a>                                    
</div>
</div>
</div>

</div>
</div>
</div>
                                <!-- ./page wapper-->
                                <script type="text/javascript" src="<spring:url value="/resources/lib/jquery/jquery-1.11.2.min.js"/>"></script>    
                                <script>
                                    $(document).ready(function() {
                                        var addressForm = $("#addressForm");
                                        addressForm.find("button").click(function(e) {
                                            e.preventDefault();

//                                            var errors = "";
//
//                                            var firstName = addressForm.find("#first_name").val();
//                                            if ($.trim(firstName) == "") {
//                                                errors += "Please enter first name<br/>";
//                                            }
//
//                                            var address = addressForm.find("#address").val();
//                                            if ($.trim(address) == "") {
//                                                errors += "Please enter address<br/>";
//                                            }

//                                            if (errors.length > 0) {
//                                                var errorDiv = addressForm.find("#error");
//                                                errorDiv.html(errors);
//                                                errorDiv.show();
//                                                return false;
//                                            } else {
//                                            }
                                                addressForm.submit();




                                        });
                                    });
                                </script>