<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<custom:setting var="RESERVE_AMOUNT_FRACTION" key="RESERVE_AMOUNT_FRACTION" />
<c:set var="menuNavigation" value="userRequests"/>
<!--main content start-->
<section id="main-content">
    <section class="wrapper">
        <h3><i class="fa fa-angle-right"></i> Orders</h3>
        <div class="row">

            <div class="col-md-12">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div class="row">
                         <div class="col-xs-8 pull-left">
                             <form class="form-inline" id="orderForm" action="<%=request.getContextPath()%>/vendor/orders">
                                 <label for="orderId">Order Id</label>
                                 <input type="text" class="form-control" name="orderId" id="orderId" value="${param.orderId}"/>
                                 <button class="btn"  type="submit">Submit</button>
                             </form>
                                    </div>   
                         <div class="col-xs-2 pull-right">
                                     <select id="status" class="form-control ">
                                                        <option value="0">All</option>
                                                        <option value="2">Processing</option>
                                                        <option value="3">Dispatched</option>
                                                        <option value="4">Cancel Request</option>
                                                        <option value="5">Cancel Approved</option>
                                                        <option value="6">Delivered</option>
                                                        <option value="7">Payment Success</option>
                                                    </select>
                                    </div>
                        </div>
                        
                         <c:set var="ordersList" value="${searchObj.data}"/>
                                    <c:choose>
                                        <c:when test="${not empty ordersList}">
                        <table class="table table-condensed" style="border-collapse:collapse;">

                            <thead>
                                <tr><th>&nbsp;</th>
                                    <th></th>
                                    <th></th>
                                    <th></th>
                                    <th></th>
                                    <th></th>
                                </tr>
                            </thead>

                            <tbody>                               
                                <c:forEach var="objArray" items="${ordersList}" varStatus="status">
                                    <c:set var="orderProduct" value="${objArray[0]}"/>
                                    <c:set var="order" value="${objArray[1]}"/>
                                    <c:set var="userReq" value="${objArray[3]}"/>
                                    <c:set var="product" value="${objArray[2]}"/>
                                    <c:set var="ordersStatus" value="${objArray[4]}"/>
                                    <!--Toggle starts here--> <tr data-toggle="collapse" data-target="#user${status.count}" class="accordion-toggle">
                                        <td>
                                            <button class="btn btn-default btn-xs">
                                                <span class="glyphicon glyphicon-user"></span>
                                            </button>
                                        </td>
                                        <td style="padding-right:50px;">Order#${order.ordersId}</td>
                                        <td style="padding-right:50px;">${orderProduct.productsQuantity} X ${orderProduct.productsName}</td>
                                        <td style="padding-right:50px;"></td>

                                    </tr>
                                    <tr>
                                        <td colspan="12" class="hiddenRow">
                                            <div class="accordian-body collapse" id="user${status.count}"> 
                                                <table class="table table-striped">
                                                    <tbody>
                                                        <tr>
                                                            <td> <address>

                                                                    <strong>${order.deliveryName}</strong><br/>
                                                                    Email : ${order.customersEmailAddress}<br/>
                                                                    Phone : ${order.customersTelephone}<br/>

                                                                    <p>${order.deliveryStreetAddress}<br/>                                
                                                                        ${order.deliveryState}<br/>
                                                                        ${order.deliveryPostcode}<br/>
                                                                        ${order.deliveryCountry}</p>
                                                                </address></td>
                                                            <td></td>
                                                            <td>
                                                                <p class="request">
                                                                    Date : ${order.datePurchased}<br/>
                                                                    Status : ${ordersStatus.ordersStatusName}
                                                                </p>
                                                            </td>                                                           
                                                        </tr>


                                                        <tr>
                                                            <td colspan="12" >
                                                                <table class="table-bordered" style="width:100%">
                                                                    <tr >
                                                                    <th>Title</th>
                                                                    <th>Quantity</th>
                                                                    <th>Price</th>
                                                                    <th>Offer Price</th>
                                                                    <th>Reserved</th>
                                                                    <th>Reserved Price</th>
                                                                    <th>Total</th>
                                                                    </tr>
                                                                     <tr>
                                                                    <td>${orderProduct.productsName}</td>
                                                                    <td>${orderProduct.productsQuantity}</td>
                                                                    <td><fmt:formatNumber value="${orderProduct.productsPrice}" type="CURRENCY" pattern="Rs ###,##0" /></td>
                                                                    <td><fmt:formatNumber value="${orderProduct.finalPrice}" type="CURRENCY" pattern="Rs ###,##0" /></td>
                                                                    <td><c:choose><c:when test="${orderProduct.reserved==1}">Yes</c:when><c:otherwise>No</c:otherwise></c:choose></td>
                                                                    <td>
                                                                        <c:if test="${orderProduct.reserved==1}">
                                                                        <fmt:formatNumber value="${orderProduct.reservedPrice}" type="CURRENCY" pattern="Rs ###,##0" />
                                                                        </c:if>
                                                                    </td>
                                                                    <td>
                                                                        <c:choose>
                                                                            <c:when test="${orderProduct.reserved==1}">
                                                                                <fmt:formatNumber value="${orderProduct.reservedPrice*orderProduct.productsQuantity}" type="CURRENCY" pattern="Rs ###,##0" />
                                                                            </c:when>
                                                                            <c:otherwise>
                                                                                <fmt:formatNumber value="${orderProduct.finalPrice*orderProduct.productsQuantity}" type="CURRENCY" pattern="Rs ###,##0" /></c:otherwise>
                                                                        </c:choose>
                                                                        
                                                                    </td>
                                                                    </tr>
                                                                </table>
                                                            </td>                                                           
                                                        </tr>
 <tr>
     <td colspan="12" ><strong>User Request : </strong><br/>
     ${userReq.request}
     </td>
 </tr>

                                                    </tbody>
                                                </table>

                                            </div> </td>
                                    </tr><!--Toggle ends here-->

                                </c:forEach>
                                   

                            </tbody>
                        </table>
                         </c:when>
                                    <c:otherwise>
                                        No records found.
                                    </c:otherwise>
                        </c:choose>
                                        
                                         <c:if test="${not empty ordersList}">
                                   <span class="pagination" id="pagination">                                    
                                    </span>
                                    <span>Total : ${searchObj.count}</span>
                                            </c:if>
                    </div>

                </div> 


            </div>
        </div>
    </section>
</section>
<script src="<spring:url value="/resources/assets_admin/js/jquery.js"/>"></script>
<script type="text/javascript">
 $(document).ready(function(){
        
         var paginationCallback = function(pageNumber,event){
            event.preventDefault();
            //console.log(pageNumber);
            var url = "<%=request.getContextPath()%>/vendor/orders?limit=${searchObj.limit}&status=${param.status}&page="+pageNumber+"&orderId=${param.orderId}";
            //console.log(url);        
            window.location.href=url;
        };   
        
         $("#pagination").pagination({
            items: ${searchObj.count},
            itemsOnPage: ${searchObj.limit},
            currentPage:${searchObj.page},
            cssStyle: 'light-theme',
            onPageClick:paginationCallback
        });
        
        var statusControl = $('#status');
        var statusVal = '0';
        
        <c:if test="${not empty param.status }">statusVal='${param.status}';</c:if>
          
        statusControl.val(statusVal);
       
        
        statusControl.change(function(e){
            e.preventDefault();
            var status = $(this).val();
            var url = "<%=request.getContextPath()%>/vendor/orders?status="+status;
            //console.log(url);
            window.location.href=url;
    });
    
    $("#orderForm").submit(function(e){
            e.preventDefault();
            var orderId = $(this).find("#orderId").val();
            if(isNaN(orderId)){
                return false;       
            }
            var url = "<%=request.getContextPath()%>/vendor/orders?orderId="+orderId;
            //console.log(url);
            window.location.href=url;
    });
    
    $(document).delegate(".readButton","click",function(e){
        $this = $(this);
        var uid = $this.attr("uid");
        var url = '<%=request.getContextPath()%>/vendor/updateUserRequest?uid='+uid;
        $.get(url,function(data){
            if(data.status=='success'){
                $this.remove();
            }
        });
    });
        
    });

</script>

