<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<link href="<spring:url value="/resources/assets_admin/css/admin/jquery-ui.css"/>" rel="stylesheet">
<link href="<spring:url value="/resources/assets_admin/css/admin/grayeasyui.css"/>" rel="stylesheet">
<link href="<spring:url value="/resources/assets_admin/css/admin/custom.css"/>" rel="stylesheet">
<script src="<spring:url value="/resources/assets_admin/js/jquery.js"/>"></script>
<script src="<spring:url value="/resources/assets_admin/js/admin/login.js"/>"></script>

<c:set var="menuNavigation" value="offers"/>
<section id="main-content">
    <div class="row mt">
        <div class="container">
            <h3 class="page-header" style="">Order details</h3>
            <div class="col-xs-7 col-sm-7 pull-right form-category">
                <form class="form-inline">
                </form>
            </div>
            <div class="row">
                <div class="clearfix" id="offersContent">
                </div>
            </div>
        </div>
    </div>


    <p ><h3>OrdersId:${orders.ordersId}</h3></p>
<hr/>
<div class="pop_container" >
    <div class="view-as-customer">    
        <p class="row1">   
            <b >Customer:</b>
            <span > 
                ${orders.customersName}<br/>
                ${orders.customersStreetAddress}
            </span>
        </p>
        <p class="row1">  
            <b >Shipping Address:</b>
            <span > 
                ${orders.customersName}<br/>
                ${orders.deliveryStreetAddress}
            </span>
        </p>
        <p class="row1">  
            <b >Billing Address:</b>
            <span> 
                ${orders.customersName}<br/>
                ${orders.billingStreetAddress}
            </span>
        </p>
    </div>
    <br/>
    <br style="clear: both;"/>
    <br style="clear: both;"/>
    <div class="pop_container">  
        <p class="row1">   
            <b>Telephone Number:</b>
            <span class="column2"> 
                ${orders.customersTelephone}<br/>
            </span>
        </p>
    </div>
    <br/>
    <br/>
    <div class="pop_container">  
        <p class="row1">  
            <b>E-mail Address:</b>
            <span class="column2"> 
                ${orders.customersEmailAddress}<br/>
            </span>
        </p>
    </div>
    <br/>
    <br/>
    <div class="pop_container">  
        <p class="row2">  
            <b>Payment Method:</b>
            <span class="column2"> 
                ${orders.paymentMethod}<br/>
            </span>
        </p>
    </div>
</div>
<br/>
<br/>
<br/>
<div class="pop_container" >
    <div class="view-as-customer">   

        <p class="row3">   
            <b>Vendor Name</b>
        </p>
        <p class="row3">   
            <b>Mobile Number</b>
        </p>

        <p class="row3">   
            <b>PID</b>
        </p>

       <p class="row4">  
            <b >Product</b>
        </p>
       <!--  <p class="row4">  
            <b >Model</b>
        </p>
        <p class="row4">  
            <b >Tax</b>
        </p>-->
        <p class="row3">  
            <b >Price</b>
        </p>
        <p class="row3">  
            <b >Final price</b>
        </p>
        <p class="row3">  
            <b >Quantity</b>
        </p>
        <p class="row3">  
            <b >Reserved</b>
        </p>
        <p class="row3">  
            <b >Reserved Price</b>
        </p>
    </div> 
    <br style="clear:both;"/>

    <c:set var="total" value="${0}"/>

    <c:forEach var="pro" items="${orderedProducts}">
        <div class="view-as-customer"> 
            <c:set var="dateParts" value="${fn:split(vendorNames[pro.vendorId], '~@~')}" />
            <p class="row3">   
                <label >${dateParts[0]}</label>
            </p>
            <p class="row3">   
                <label >${dateParts[1]}</label>
            </p>

            <p class="row3">   
                <label >${pro.productsId}</label>
            </p>

            <p class="row4">  
                <label >${pro.productsName}</label>
            </p>

<!--            <p class="row4">  
                <label >${pro.productsModel}</label>
            </p>

            <p class="row4">  
                <label >${pro.productsTax}</label>
            </p>-->

            <p class="row3">  
                <label ><fmt:formatNumber value="${pro.productsPrice}" type="CURRENCY" pattern="Rs ###,##0.00" /></label>
            </p>

            <p class="row3">  
                <label ><fmt:formatNumber value="${pro.finalPrice}" type="CURRENCY"  pattern="Rs ###,##0.00" /></label>
                <c:set var="total" value="${total + (pro.finalPrice*pro.productsQuantity)}" />
            </p>
            
            <p class="row3">  
                <label >${pro.productsQuantity}</label>
            </p>
            
            <p class="row3">  
                <label ><c:choose><c:when test="${pro.reserved==1}">Yes</c:when> <c:otherwise>No</c:otherwise></c:choose></label>
            </p>
            
            <p class="row3">  
                <label ><fmt:formatNumber value="${pro.reservedPrice}" type="CURRENCY"  pattern="Rs ###,##0.00" /></label>
            </p>
        </div><br style="clear: both;"/>
    </c:forEach>
    <hr/>
    <div class="view-as-customer" > 
        <p class="row3">   
        </p>
        <p class="row3">   
        </p>

        <p class="row3">   
        </p>

       <p class="row4">  
        </p>
       <!--  <p class="row4">  
        </p>
        <p class="row4">  
        </p>-->
        <p class="row4">  
        </p>
        <p class="row6"> 
            
            <c:forEach var="ordertotal" items="${orderTotalList}">    
                                <span><strong>${ordertotal.title}</strong> <fmt:formatNumber value="${ordertotal.value}" type="CURRENCY" pattern="Rs ###,##0" /></span><br/>                                                    
                            </c:forEach> 
                                
            <%--<b ><label >Total:</label> &nbsp;&nbsp;  <label ><fmt:formatNumber value="${total}" type="CURRENCY" pattern="Rs ###,##0" /></label></b>--%>
        </p>
    </div>
</div>
<br/>
<br style="clear:both;"/>
<br style="clear:both;"/>
<div class="pop_container" style="border: dashed" >
    <div class="view-as-customer">    
        <p class="row5">   
            <b >Date Added</b>
        </p>
        <p class="row4">  
            <b >Status</b>
        </p>
        <p class="row6">  
            <b >Comments</b>
        </p>
    </div>
    <br style="clear:both"/>
    <c:forEach var="orderHist" items="${orderHistory}">
        <div class="view-as-customer">    
            <p class="row5">  
		<label >
                    	<fmt:formatDate value="${orderHist.dateAdded}" type="both" pattern="yyyy-MM-dd HH:mm:ss" timeZone="GMT+5:30"/>
                </label> 
            </p>
            <p class="row4">  
                <label >${orderStatCodes[orderHist.ordersStatusId]}</label>
            </p>

            <p class="row7">  
                <label >${orderHist.comments}</label>
            </p>
        </div><br style="clear: both;"/>
    </c:forEach>

</div>

<br style="clear:both;"/> 
<form id="orderform" >
    <div class="pop_container" >
        <div class="view-as-customer"> 
            <p class="row1" id="resp" style="color: red;">

            </p>
            <br style="clear: both;"/>
            <p class="row1">   
                <b >Order Status:</b>
                <span > 
                    <select name="changeStatus" id="changeStatus" >
                        <option value="">--Select--</option>
                        <c:forEach var="ostatus" items="${orderStatCodes}" begin="0">
                            <option value="${ostatus.key}">${ostatus.value}</option>
                        </c:forEach>
                    </select>
                </span>
            </p>
            <br style="clear: both;"/>
            <p class="row1">  
                <b >Comments:</b>
                <span > 
                    <textarea id="comments" name="comments"></textarea>
                </span>
            </p>
            <br style="clear: both;"/>
            <input type="hidden" name="orderId" id="orderId" value="${orders.ordersId}"/>
            <p class="row1" style="padding: 6px 0px 10px 250px;">  
                <span> 
                    <input type="button" id="btnorderstatus" class="btn btn-default" style="padding: 10px 20px;background-color: #FF7F00" value="Add"/>
                </span>
            </p>
        </div>
    </div>

</form>  
<br style="clear:both;"/>

</section>
<!-- /MAIN CONTENT -->
<script>
    $(document).ready(function () {
        $("#btnorderstatus").click(function () {
            var params = $("#orderform").serialize();
            $.ajax({
                type: "POST",
                url: "changeorderstatus",
                dataType: "json",
                data: params,
                cache: false,
                success: function (data) {
                    if (data && data.respmsg == "success")
                    {
                        window.location.href = "viewOrder?orderId=" + ${orders.ordersId};
                        return;
                    } else {
                        $("#resp").html("Unable to update the Status");
                    }
                }
            });
        });
    });

</script>

