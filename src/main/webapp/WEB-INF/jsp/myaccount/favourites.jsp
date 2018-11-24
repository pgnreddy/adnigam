<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/custom" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--<custom:setting var="imagesPath" key="IMAGES_PREFIX_PATH" />--%>
<c:set var="imagesPath" value="https://s3-us-west-2.amazonaws.com/adrobe.in.assets/" />

<script src="<spring:url value="/resources/assets_admin/js/jquery.js"/>"></script>
<div class="center_column col-xs-12 col-sm-9">
    <h3 class="page-heading head_block">Wishlist</h3>
    <!-- ../page heading-->
    <div class="page-content page-order">
        <c:choose >
            <c:when test="${empty offersList}">
                <div class="row">

                    <span class="page-heading-title">Wishlist is empty</span>

                </div>
            </c:when>
            <c:otherwise>
                <div class="order-detail-content">
                    <span id="mailResponseMsg" class="normalError" style="display:none"></span>
                    <table class="table table-bordered table-responsive cart_summary">
                        <%--<thead>
                            <tr>                                
                                <th>Order Number</th>
                                <th>Order Status</th>
                                <th>Order Date</th>
                                <th>Products</th>                                
                                <th>Orders Cost</th>
                                <th  class="action"><i class="fa fa-eye"></i></th>
                            </tr>
                        </thead>--%>
                        <tbody>
                            <c:forEach var="product" items="${offersList}">
                                <tr id="favItem${product.offerID}">                                  
                                    <td class="fav_image"><img class="fav_img" src="<spring:url value="${imagesPath}${product.image}"/>" alt="${product.title}"/></td>
                                    <td class="fav_title"><a href="<spring:url value="/pd/${product.seo_title}"/>"><b>${product.title}</b></a></td>
                                    <td class="fav_price"><fmt:formatNumber value="${product.offerPrice}" type="CURRENCY" pattern="Rs ###,##0" /></td>                                    
                                    <td class="qty"><a class="deleteButton" pid="${product.offerID}" href="#"><img src="<spring:url value="/resources/assets_admin/images/delete-btn.gif"/>" alt="delete"/></a></td>                                    
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

$(document).ready(function(){

$('.deleteButton').each(function(){
$(this).click(function(e){
e.preventDefault();
deleteFavourite($(this).attr('pid'));
});
});

});

var deleteFavourite = function(productId){
            /*if(!logIn){
                $('#mailResponseMsg').html("Please login").show();
                return false;
            }*/           
			
			if(!productId){
			return false;
			}
			
            $('#mailResponseMsg').html("processing...").show();	    
                        
        var url = "<spring:url value="/myaccount/deleteFavourite"/>";
            var dataToSend = {productId:productId};
            
            $.post(url,dataToSend, function(data) {                
                
                  var type=data.msg ;                
                  var result=data.status;
                  var productReturedId = data.data;
                  if(type=='deleteFavProduct'){
                      if(result=='success'){					        
			    $('#favItem'+productReturedId).remove();
                            $('#mailResponseMsg').html("Your favourite product is successfully removed").show();
                      }else if(result=='login'){
                            $('#mailResponseMsg').html("Please login").show();
                      }else if(result=='invalidProduct'){
                            $('#mailResponseMsg').html("Please select item to delete").show();
                      }else{
                           $('#mailResponseMsg').html("Problem deleting favourite product").show();                        
                      }
                  }
            });
            
           
            return false;
}
        
</script>
