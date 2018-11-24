<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/custom" %> 

<!-- page wapper-->
<script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','https://www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-72951558-3', 'auto');
  ga('send', 'pageview');

</script>
<div class="columns-container">
    <div class="container">
        <!-- breadcrumb -->
        <div class="breadcrumb clearfix">
            <a class="home" href="<spring:url value="/"/>" title="Return to Home">Home</a>            
        </div>
        <!-- ./breadcrumb -->
        <!-- page heading-->
        <h2 class="page-heading no-line">
            <span class="page-heading-title2">Payment details</span>
        </h2>
        <!-- ../page heading-->
        <div class="page-content page-order">
            
            <br/>
            <div class="col-sm-6 col-md-6 cart_sign">
                <div class="box-authentication">
                    
                    <c:set var="orderId" value="${order.ordersId}"/>
                    <c:forEach var="orderTotal" items="${order.orderTotalList}">
                        <c:if test="${orderTotal.class1=='ot_total'}">
                            <c:set var="orderTotalValue" value="${orderTotal.text}"/>
                        </c:if>
                    </c:forEach>
                    <c:choose>
                        <c:when test="${status=='success'}">
                            Your payment for amount ${orderTotalValue} towards order #${orderId} is successful.
                            <c:if test="${not empty productCoupons}">
                                <br>
                                <table class="table table-bordered table-responsive cart_summary">
                                    <thead>
                                    <th>Product Name</th>
                                    <th>Product Coupon Code</th>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="pc" items="${productCoupons}">
                                        <tr>
                                            <td><c:out value="${pc.productName}"/> </td>
                                            <td><c:out value="${pc.productCouponCode}" /></td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                                <p>The product coupons are valid for 7 days from now.</p>
                            </c:if>
                        </c:when>
                        <c:otherwise>
                            Your payment for amount ${orderTotalValue} towards order #${orderId} is failed.
                        </c:otherwise>
                    </c:choose>                   
                </div>

              
            <div class="cart_navigation">
                <a class="prev-btn" href="<spring:url value="/"/>">Continue shopping</a>
                <%--<a class="next-btn" href="cart_address.html">Proceed to checkout</a>--%>
            </div>
        </div>
    </div>
</div>
</div>
