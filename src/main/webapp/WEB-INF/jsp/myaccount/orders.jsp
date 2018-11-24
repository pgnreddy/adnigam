<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/custom" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script src="<spring:url value="/resources/assets_admin/js/jquery.js"/>"></script>
<div class="center_column col-xs-12 col-sm-9">
    <h3 class="page-heading head_block">Your Orders</h3>
    <!-- ../page heading-->
    <div class="page-content page-order">
        <c:choose >
            <c:when test="${empty orders}">
                <div class="row">

                    <span class="page-heading-title">No Orders found</span>

                </div>
            </c:when>
            <c:otherwise>
                <div class="order-detail-content">
                    <table class="table table-bordered table-responsive cart_summary">
                        <thead>
                            <tr>                                
                                <th>Order Number</th>
                                <th>Order Status</th>
                                <th>Order Date</th>
                                <th>Products</th>                                
                                <th>Orders Cost</th>
                                <th  class="action"><i class="fa fa-eye"></i></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="order" items="${orders}">
                                <tr>                                  
                                    <td class="orderNumber">${order['orderId']}</td>
                                    <td class="orderStatus">${order['status']}</td>
                                    <td class="cart_avail"><fmt:formatDate timeZone="Asia/Calcutta" pattern="EEEE dd MMMM,yyyy hh:mm a" value="${order['date']}"/></td>                                    
                                    <td class="qty">${order['count']}</td>
                                    <td class="price">
                                        <span><fmt:formatNumber value="${order['amount']}" type="CURRENCY" pattern="Rs ###,##0.00" /></span>
                                    </td>
                                    <td class="view">
                                        <a href="<spring:url value="/myaccount/order/${order['orderId']}"/>">View</a>
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
