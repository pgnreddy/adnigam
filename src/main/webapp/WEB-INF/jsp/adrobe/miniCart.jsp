<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/custom" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--<custom:setting var="imagesPath" key="IMAGES_PREFIX_PATH" />--%>
<c:set var="imagesPath" value="https://s3-us-west-2.amazonaws.com/adrobe.in.assets/" />
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<c:set var="itemsMap" value="${sessionScope.CART.itemsMap}"/> 
                  
   <div id="modal-cart" class="modal modal-right fade" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
          <h4 class="modal-title">${fn:length(itemsMap)} items in your cart</h4>
        </div>
        <div class="modal-body"> 
          
          <!-- Begin shopping cart content -->
          <div class="cart-content">
            <ul class="cart-product-list">
                <c:if test="${not empty itemsMap}">
                <c:forEach var="entry" items="${itemsMap}">
                <c:set var="orderProductItem" value="${entry.value}"/>
              <li> 
                <!-- Begin shopping cart product -->
                <div class="cart-product"> <a href="#" class="cart-pr-thumb bg-image"><img src="<spring:url value="${imagesPath}${orderProductItem.product.image}"/>" alt="Lorem ipsum dolor" width="65"></a>
                  <div class="cart-pr-info"> <a href="#" class="cart-pr-title">${orderProductItem.product.title}</a>
                    <div class="cart-pr-price"><fmt:formatNumber value="${orderProductItem.offerPrice}" type="CURRENCY" pattern="Rs ###,##0"/></div>
                    <div class="cart-pr-quantity">Quantity: <span>${orderProductItem.quantity}</span></div>
                  </div>
                  <a href="#0" class="cart-pr-remove" title="Remove from cart" pid="${orderProductItem.product.offerID}" reserve="${orderProductItem.reserved}">×</a> </div>
                <!-- End shopping cart product --> 
              </li>
                    </c:forEach>
                </c:if>
          
            </ul>
          </div>
          <!-- End shopping cart content --> 
          
        </div>
        <div class="modal-footer padding-vertical-0">
          <div class="cart-total"> <strong>Subtotal:</strong> <span><fmt:formatNumber value="${sessionScope.CART.totalprice}" type="CURRENCY" pattern="Rs ###,##0"/></span> </div>
          <div class="row">
              <div class="col-xs-6 no-padding"> <a href="<spring:url value="/cart.htm"/>" class="view-cart no-margin">View Cart</a> </div>
            <!-- /.col -->
            
            <div class="col-xs-6 no-padding"> <a href="<spring:url value="/checkout/"/>" class="btn-checkout no-margin">Checkout</a> </div>
            <!-- /.col --> 
          </div>
          <!-- /.row --> 
        </div>
      </div>
      <!-- /.modal-content --> 
    </div>
    <!-- /.modal-dialog --> 
  </div>
    <script>
  $(document).ready(function(){
  $('#cart_num').html('<i class="fa fa-shopping-bag"></i> <span class="cart-num">${fn:length(itemsMap)}</span>');
   });
    </script>