<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/custom" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script src="<spring:url value="/resources/assets_admin/js/jquery.js"/>"></script>
<div class="center_column col-xs-12 col-sm-9">
    <h3 class="page-heading head_block">Your Coupons</h3>
    <!-- ../page heading-->
    <div class="page-content page-order">
        <c:choose >
            <c:when test="${empty couponsList}">
                <div class="row">

                    <span class="page-heading-title">You do not have any valid coupons.</span>

                </div>
            </c:when>
            <c:otherwise>
                <div class="order-detail-content">
                    <table class="table table-bordered table-responsive cart_summary">
                        <thead>
                            <tr>                                
                                <th>Coupon Code</th>
                                <th>Initial Amount</th>
                                <th>Amount Available</th>
                                <th>Times it can be used</th>                                
                                <th>Validity</th>
                                
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="couponItem" items="${couponsList}">
                                <c:set var="coupon" value="${couponItem[0]}"/>
                                <c:set var="customercoupon" value="${couponItem[1]}"/>
                                <tr>                                  
                                    <td class="orderNumber">${coupon.code}</td>
                                    <td class="orderStatus">${coupon.discount_value} <c:if test="${coupon.discount_type==0}">%</c:if> </td>
                                    <td class="cart_avail">
                                        <c:choose>
                                                    <c:when test="${coupon.number_of_times_to_use>1}">
                                                        <c:set var="amountpertrans" value="${coupon.discount_value/coupon.number_of_times_to_use}"/>
                                                        ${coupon.discount_value-(amountpertrans*customercoupon.numberOfTimesUsed)}
                                                    </c:when>
                                                    <c:otherwise>-</c:otherwise>
                                                </c:choose>
                                    </td> 
                                     <jsp:useBean id="now" class="java.util.Date"/>
                                                 <c:choose>
                                                    <c:when test="${coupon.number_of_times_to_use<=1}">                                                        
                                                       <c:set var="expired" value="true"/>
                                                    </c:when>
                                                     <c:when test="${coupon.number_of_times_to_use>1 && customercoupon.numberOfTimesUsed >=coupon.number_of_times_to_use }">                                                        
                                                       <c:set var="expired" value="true"/>
                                                    </c:when>
                                                    <c:when test="${not empty coupon.end_date && coupon.end_date lt now }">                                                        
                                                       <c:set var="expired" value="true"/>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:set var="expired" value="false"/>
                                                    </c:otherwise>
                                                </c:choose>
                                                 <td class="qty">
                                                      <c:choose>
                                                    <c:when test="${expired == true}">0
                                                    </c:when>
                                                    <c:when test="${coupon.number_of_times_to_use>1}">                                                        
                                                        ${coupon.number_of_times_to_use-customercoupon.numberOfTimesUsed}
                                                    </c:when>
                                                    <c:otherwise>0</c:otherwise>
                                                </c:choose>
                                                 </td>
                                    <td class="price">
                                          <c:choose>
                                                    <c:when test="${expired==true}">                                                        
                                                       Expired
                                                    </c:when>                                                   
                                                    <c:otherwise>-</c:otherwise>
                                                </c:choose>
                                    </td>
                                    
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>
<script>
    $(document).ready(function () {
       // $(".tree-menu").find("li").removeClass("active");
       // $(".orders_active").addClass("active");
    });




</script>
