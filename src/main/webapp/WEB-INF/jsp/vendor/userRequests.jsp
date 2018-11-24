<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:set var="menuNavigation" value="userRequests"/>
<!--main content start-->
      	<section id="main-content">
          <section class="wrapper">
          	<h3><i class="fa fa-angle-right"></i> User request's</h3>
				<div class="row">
				
	                  <div class="col-md-12">
                                
                                <div class="panel panel-default">
                                <div class="panel-body ">
                                    <div class="col-xs-2 pull-right">
                                     <select id="status" class="form-control ">
                                                        <option value="0">UnRead</option>
                                                        <option value="1">Read</option>
                                                    </select>
                                    </div>
                                  <c:set var="userRequestsList" value="${searchObj.data}"/>
                                    <c:choose>
                                        <c:when test="${not empty userRequestsList}">
                                    <table class="table table-condensed" style="border-collapse:collapse;">
                                    
                                        <thead>
                                            <tr><th>&nbsp;</th>
                                                <th>Name</th>
                                                <th></th>
                                                <th></th>
                                                <th></th>
                                                <th>
                                                   
                                                </th>
                                                    
                                            </tr>
                                        </thead>
                                    
                                        <tbody>
                                            
                                            <c:forEach var="objArray" items="${userRequestsList}" varStatus="status">
                                                <c:set var="userReq" value="${objArray[0]}"/>
                                                <c:set var="orderProduct" value="${objArray[1]}"/>
                                                <c:set var="order" value="${objArray[2]}"/>
              <!--Toggle starts here--> <tr data-toggle="collapse" data-target="#user${status.count}" class="accordion-toggle">
                                          	<td>
                                                <button class="btn btn-default btn-xs">
                                                <span class="glyphicon glyphicon-user"></span>
                                                </button>
                                            </td>
                                                <td style="padding-right:50px;">${order.customersName}</td>
                                                <td style="padding-right:50px;">${order.customersTelephone}</td>
                                                <td style="padding-right:50px;">${orderProduct.productsName}</td>
                                                <td style="padding-right:50px;"></td>
                                                <td style="padding-right:50px;"></td>
                                                <td style="padding-right:50px;"></td>
                                    
                                            </tr>
                                            <tr>
                                             <td colspan="12" class="hiddenRow">
                                                <div class="accordian-body collapse" id="user${status.count}"> 
                                                  <table class="table table-striped">
                                                     <tbody>
                                                        <tr>
                                                        <td></td>
                                                        <td></td>
                                                           <td>
                                                            <p class="request">
                                                                ${userReq.request}
                                                            </p>
                                                            </td>
                                                            <td></td>
                                                            <td></td>
                                                            <td></td>
                                                            <td>
                                                            <!--<a href="#" class="btn btn-info btn-sm">Pending</a>-->
                                                              </td>
                                                              <td>
                                                                  <c:if test="${userReq.read==0}">
                                                              <a href="#" class="btn btn-success btn-sm readButton" uid="${userReq.id}">Read</a>
                                                                  </c:if>
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
                                            No records found
                                        </c:otherwise>
                                        </c:choose>
                                            <c:if test="${not empty userRequestsList}">
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
            var url = "<%=request.getContextPath()%>/vendor/requests?limit=${searchObj.limit}&status=${param.status}&page="+(pageNumber);
            //console.log(url);
            window.location.href=url;
        }   
        
         $("#pagination").pagination({
            items: ${searchObj.count},
            itemsOnPage: ${searchObj.limit},
            currentPage:${searchObj.page},
            cssStyle: 'light-theme',
            onPageClick:paginationCallback
        });
        
        var statusControl = $('#status');
        var statusVal = "0";
        
            <c:if test="${not empty param.status and param.status=='1'}">statusVal=1;</c:if>
        statusControl.val(statusVal);
        
        statusControl.change(function(e){
            e.preventDefault();
            var status = $(this).val();
            var url = "<%=request.getContextPath()%>/vendor/requests?status="+status;
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
    
    