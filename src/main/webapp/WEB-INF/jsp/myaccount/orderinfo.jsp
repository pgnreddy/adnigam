<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/custom" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<custom:setting var="RESERVE_AMOUNT_FRACTION" key="RESERVE_AMOUNT_FRACTION" />
<script src="<spring:url value="/resources/assets_admin/js/jquery.js"/>"></script>
<div class="center_column col-xs-12 col-sm-9">
    <h3 class="page-heading head_block">MyAccount Information</h3>
    <div class="page-content page-order">        
        <div class="heading-counter">Order #${order.ordersId} <br/>
            Order Status: (${order.orderStatus.ordersStatusName})<br/>
            Order Date: <fmt:formatDate pattern="EEEE dd MMMMM,yyyy hh:mm a" value="${order.datePurchased}" />             
        </div>


        <div class="order-detail-content">
            <table class="table table-bordered table-responsive cart_summary">
                <thead>
                    <tr>
                        <th class="cart_product">Delivery Address</th>
                        <th>Product Information</th>
                        <th>Billing Information</th>                   
                        <th>Payment Method</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td class="cart_address">

                            <address>

                                <strong>${order.deliveryName}</strong><br/>
                                <p>${order.deliveryStreetAddress}<br/>                                
                                    ${order.deliveryState}<br/>
                                    ${order.deliveryPostcode}<br/>
                                    ${order.deliveryCountry}</p>
                            </address>
                        </td>
                        <td class="productInformation">
                            <c:forEach var="product" items="${order.orderProducts}">
                                ${product.productsQuantity} X ${product.productsName} <c:if test="${product.coupon==1}">(Coupon)</c:if> 
                                ::<c:set var="finalPrice" value="${product.finalPrice}"/>
                                <c:if test="${product.reserved==1}"><c:set var="finalPrice" value="${product.reservedPrice}"/></c:if>
                                <fmt:formatNumber value="${finalPrice}" type="CURRENCY" pattern="Rs ###,##0.00" /><br/>
                                <c:if test="${product.coupon==1}"><b><c:forEach var="pc" items="${productCoupons}"><c:if test="${pc.productName eq product.productsName}">${pc.productCouponCode}</c:if></c:forEach></b></c:if>
                            </c:forEach>
                        </td>
                        <td class="billingInformation">

                            <c:forEach var="ordertotal" items="${order.orderTotalList}">    
                                <span><strong>${ordertotal.title}</strong> <fmt:formatNumber value="${ordertotal.value}" type="CURRENCY" pattern="Rs ###,##0.00" /></span><br/>                                                    
                            </c:forEach> 

                            <%--<td class="">Flat Rate (Best Way)</td>
                            <td class="cart_address">

                            <address>
                                <strong>Home</strong><br/>
                                <strong>${order.customersName}</strong><br/>
                                <p>${order.customersStreetAddressHome}</p><br/>
                                <p>${order.customersSuburb},${order.customersPostcode}</p><br/>
                                <p>${order.customersState}</p><br/>
                                <p>${order.customersCountry}</p>
                            </address>
                        </td>--%>
                        <td class="">${order.paymentMethod}</td>
                    </tr>

                </tbody>    
            </table>
        </div>
        <c:if test="${order.ordersStatus == 7}">
            <div>
                Cancel order :

                <form id="cancelForm" action="<spring:url value="/myaccount/cancelOrder"/>" method="POST">
                    <input type="hidden" value="${order.ordersId}" name="orderId"/>
                    <textarea class="input" name="comments" id="comments"></textarea>
                    <br/><button class="button">Cancel Order</button>
                </form>

            </div>
        </c:if>
    </div>
</div>
<script>
    //$(".tree-menu").find("li").removeClass("active");
   // $(".orders_active").addClass("active");

    $(document).ready(function() {
        var cancelForm = $("#cancelForm");
        if (cancelForm) {
            cancelForm.find("button").click(function() {
                var comments = cancelForm.find("#comments").val();
                comments = $.trim(comments);
                if ("" != comments) {
                    cancelForm.submit();
                } else {
                    return false;
                }
            });
        }
    });
</script>