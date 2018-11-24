<%-- 
    Document   : productCoupons
    Created on : Feb 11, 2016, 5:47:03 PM
    Author     : rkumar
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<section id="main-content">
    <section class="wrapper">
        <h3><i class="fa fa-angle-right"></i> Product Coupons</h3>
        <div class="row">

            <div class="col-md-12">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-xs-8 pull-left">
                                <form class="form-inline" id="productCouponForm" action="<%=request.getContextPath()%>/vendor/productCoupon">
                                    <label for="productCouponCode">Coupon Code</label>
                                    <input type="text" class="form-control" name="productCouponCode" id="productCouponCode" value="${param.productCouponCode}"/>
                                    <button class="btn"  type="submit">Submit</button>
                                </form>
                            </div>  

                        </div>
                        <br/>
                        <br/>

                        <c:set var="productCouponsList" value="${searchObj.data}"/>
                        <c:choose>
                            <c:when test="${not empty productCouponsList}">
                                <table class="table table-bordered table-condensed table-striped" style="border-collapse:collapse;">

                                    <thead>
                                        <tr><th>OrderId</th>
                                            <th>Product Name</th>
                                            <th>Coupon Code</th>
                                            <th>Quantity</th>
                                            <th>Action</th>
                                        </tr>
                                    </thead>

                                    <tbody>                               
                                        <c:forEach var="coupon" items="${productCouponsList}"> 
                                            <tr>
                                                <td>${coupon.orderID}</td>
                                                <td>${coupon.productName}</td>
                                                <td>${coupon.productCouponCode}</td>
                                                <td>${coupon.quantity}</td>
                                                <td>
                                                    <a href="#" class="edit" pid="${coupon.productCouponCode}" >Sell</a>&nbsp;
                                                </td>

                                            </tr>
                                        </c:forEach>


                                    </tbody>
                                </table>
                            </c:when>
                            <c:otherwise>
                                No records found.
                            </c:otherwise>
                        </c:choose>

                        <c:if test="${not empty productCouponsList}">
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
        <script src="<spring:url value="/resources/assets_admin/js/bootstrap.min.js"/>"></script>
        


<script type="text/javascript">
    
    
    $(document).ready(function(){
        
        var myModal = $("#myModal");
       myModal.modal('hide');
         var paginationCallback = function(pageNumber,event){
            event.preventDefault();
            //console.log(pageNumber);
            var url = "<%=request.getContextPath()%>/vendor/productCouponCode?limit=${searchObj.limit}&page="+pageNumber+"&orderID=${param.orderID}&productName=${param.productName}&productCouponCode=${param.productCouponCode}";
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
        
       
            
    $("#productCouponForm").submit(function(e){
            e.preventDefault();
            var couponCode = $(this).find("#productCouponCode").val();
            var url = "<%=request.getContextPath()%>/vendor/productCoupons";
            
            if (couponCode) {
                url = url + '?productCouponCode='+ couponCode;
            }
            //console.log(url);
            window.location.href=url;
    });
    
    $(document).delegate(".edit","click",function(e){
        $this = $(this);
        var pid = $this.attr("pid");
         $.ajax({
                    type: "GET",
                    url: "<spring:url value="/vendor/sellProductCoupon"/>",
                    data: "type=edit&productCouponCode="+pid,
                    cache: false, 
                    dataType: 'json',                   
                    success: function(data) {
                        if (data && data.status && data.status == 'success') {
                            window.location.reload();
                        }
                    }
                    
                });
    });  
    });

</script>