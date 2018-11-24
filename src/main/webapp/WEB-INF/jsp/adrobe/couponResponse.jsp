<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/custom" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<c:choose>
    <c:when test="${not empty status && status=='invalid'}">
        failed
    </c:when>
    <c:otherwise>
        <c:choose>
<c:when test="${not empty sessionScope.CART && not empty sessionScope.CART.coupon}">
<tr>
    <td colspan="2" rowspan="3"></td>
    <td style="" class="a-left" colspan="1">Sub Total </td>
    <td cstyle="" class="a-right"><span><fmt:formatNumber value="${sessionScope.CART.actualTotalprice}" type="CURRENCY" pattern="Rs ###,##0.00"/></span></td>   
</tr>
 <tr>
    <td colspan="3">Coupon (${sessionScope.CART.coupon.code}) <a href="javascript:void(0)" class="removeCoupon">remove</a></td>
    <td colspan="2"><strong>-<fmt:formatNumber value="${sessionScope.CART.couponDiscountPrice}" type="CURRENCY" pattern="Rs ###,##0.00"/> </strong></td>
  </tr>
  <tr>
    <td style="" class="a-left" colspan="1"><strong>Total</strong></td>
    <td style="" class="a-right"><strong><span class="price"><fmt:formatNumber value="${sessionScope.CART.totalprice}" type="CURRENCY" pattern="Rs ###,##0.00"/></span> </strong></td>
</tr>
</c:when>
    <c:otherwise>
        <tr>
     <td style="" class="a-left" colspan="1"><strong>Grand Total</strong></td>
     <td style="" class="a-right"><strong><span class="price"><fmt:formatNumber value="${sessionScope.CART.totalprice}" type="CURRENCY" pattern="Rs ###,##0.00"/> </span></strong></td>
</tr>
    </c:otherwise>
  </c:choose>
  

    </c:otherwise>
</c:choose>