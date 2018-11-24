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


  <!-- Main Container -->
  <section class="main-container col1-layout">
    <div class="main container">
      <div class="col-main">
        <div class="cart">
          
          <div class="page-content page-order"><div class="page-title">
            <h2>Shopping Cart Contains:<span>${fn:length(itemsMap)} Product</span></h2>
          </div>
          <c:if test="${not empty itemsMap}">
            <div class="order-detail-content">
              <div class="table-responsive">
                <table class="table table-bordered cart_summary">
                  <thead>
                    <tr>
                      <th class="cart_product">Product</th>
                      <th>Product Name</th>
                      <th class="text-center">Availability</th>
                      <th class="text-center">Unit price</th>
                      <th class="text-center">Qty</th>
                      <th class="text-center">Total</th> 
                      <th class="action"><i class="fa fa-trash-o"></i></th>
                    </tr>
                  </thead>
                  <tbody>
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
                    <tr>
                      <td class="cart_product"><a href="#"><img src="<spring:url value="${imagesPath}${orderProductItem.product.image}"/>" alt="Product"></a></td>
                      <td class="cart_description"><p class="product-name"><a href="#">${orderProductItem.product.title} </a></p>
                       </td>
                      <td class="availability in-stock"><span class="label">In stock</span></td>
                      <td class="price"><span><fmt:formatNumber value="${orderProductItem.offerPrice}" type="CURRENCY" pattern="Rs ###,##0"/></span></td>
                      <td class="qty"><input id="qty_${status.count}" pid="${orderProductItem.product.offerID}" reserve="${orderProductItem.reserved}" coupon="${orderProductItem.coupon}" class="form-control input-sm" type="text" value="${orderProductItem.quantity}" pre_value="${orderProductItem.quantity}"><br/>
                      <button type="submit" name="update_cart_action" value="update_qty" title="Update Cart" class="save_quantity button btn" index="${status.count}"><span>save</span></button>
                      </td>
                      <td class="price"><span><fmt:formatNumber value="${orderProductItem.subprice}" type="CURRENCY" pattern="Rs ###,##0"/></span></td>
                      <td class="action"><a href="<spring:url value="/cart/removeItem?p=${orderProductItem.product.offerID}${reserveQuery}${couponQuery}"/>"><i class="icon-close"></i></a></td>
                    </tr>
                    </c:forEach>
                  </tbody>
             <tfoot>
                    <tr class="first last">
                        <td colspan="50" class="a-right last"><button type="button" title="Continue Shopping" class="button btn-empty" onclick="window.location.href='<spring:url value="/index.htm"/>'"><span>Continue Shopping</span></button>
                      </td>
                    </tr>
                  </tfoot>
                </table>
              </div>
            </div>
            <div class="cart-collaterals row">
            <div class="col-sm-4">
                <c:if test="${not empty itemsMap}">
              <div class="discount" id="couponContainer">
                <h3>Discount Codes</h3>
                  <label for="coupon_code">Enter your coupon code if you have one.</label>
                  <p id="couponMessage" class="error" style="color:red"></p>
                  <input id="coupon_code" type="text" class="input-text fullwidth"  name="coupon_code" value="">
                  <br/>
                  <button type="button" title="Apply Coupon" class="button coupon " id="couponSubmit" index="1" value="Apply Coupon"><span>Apply Coupon</span></button>
               
              </div>
                </c:if>
            </div>
            <div class="col-sm-4"></div>
            <div class="totals col-sm-4">
              <h3>Shopping Cart Total</h3>
              <div class="inner">
                <table id="shopping-cart-totals-table" class="table shopping-cart-table-total">
                  <colgroup>
                  <col>
                  <col width="1">
                  </colgroup>
                  <tfoot>
                    <div id="totals_container">
                    <jsp:include page="couponResponse.jsp"/>
                     </div>
                  </tfoot>
                </table>
                <ul class="checkout">
                  <li>
                      <button type="button" title="Proceed to Checkout" class="button btn-proceed-checkout" onclick="window.location.href='<spring:url value="/checkout.htm"/>'" ><span>Proceed to Checkout</span></button>
                  </li>
                  <br>
                </ul>
              </div>
              <!--inner--> 
              
            </div>
          </div>
          </c:if>
            <c:if test="${empty itemsMap}">
                <div class="inner">
                  <ul class="checkout">
                  <li>
                      <button type="button" title="Continue shopping" onclick="window.location.href='<spring:url value="/index.htm"/>'" class="button btn-proceed-checkout"><span>Continue shopping</span></button>
                  </li>
                </ul>
                </div>
            </c:if>
          </div>
        </div>
      </div>
    </div>
  </section>
  





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
            var couponCode = $("#couponContainer #coupon_code").val();
                    console.log("couponsubmit code:"+couponCode);
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