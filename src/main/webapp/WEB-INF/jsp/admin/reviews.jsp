
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script src="<spring:url value="/resources/assets_admin/js/jquery.js"/>"></script>
<c:set var="leftMenuSelectedTab" value="reviews"/>
<!-- body //-->

<script>   
    
    
    var selectedReviewsId = 0;
    <c:if test="${not empty requestScope.reviewsList}">
    selectedReviewsId = ${requestScope.reviewsList[0].reviewsId};
    </c:if>
    <c:if test="${not empty param.rid}">
    selectedReviewsId = ${param.rid};
    </c:if>
    
    var processOperation = function(operation,rid) {
        
        var url = null;
        var reviewsBaseurl ="<c:url value="/admin/reviews?"/>";       
        if(operation=='viewReview'){            
            url=reviewsBaseurl+"operation=view&page=${param.page}&rid="+rid;
        }else if(operation=='editReview'){
        url=reviewsBaseurl+"operation=edit&page=${param.page}&rid="+rid;
        }else if(operation=='deleteReview'){  
            url=reviewsBaseurl+"operation=delete&page=${param.page}&rid="+rid;
        }else if(operation=='newReview'){
        url=reviewsBaseurl+"operation=add&page=${param.page}&rid="+selectedReviewsId;
        }else if(operation=='cancelReview'){
        url=reviewsBaseurl+"operation=view&page=${param.page}&rid="+rid;
        }


if(url!=null){
    $('#reviewsPanel').load(url);
}

updateRowStyle(rid);     
return false;

}


var updateRowStyle = function(rid){
    var previousSelectedTrElement = $('tr[rid="'+selectedReviewsId+'"]');
    var currentSelectedTrElement = $('tr[rid="'+rid+'"]');
    $(previousSelectedTrElement).removeClass('dataTableRowSelected dataTableRowOver').addClass('dataTableRow');
    $(previousSelectedTrElement).find('img.infoIcon').attr('src','<c:url value="/resources/assets_admin/images/icon_info.gif"/>');
    
    $(currentSelectedTrElement).removeClass('dataTableRow dataTableRowOver').addClass('dataTableRowSelected');
    $(currentSelectedTrElement).find('img.infoIcon').attr('src','<c:url value="/resources/assets_admin/images/icon_arrow_right.gif"/>');
    selectedReviewsId = rid;  
    
}
</script>
<c:set var="selectedReview" value="${requestScope.reviewsList[0]}"/>
<c:if test="${not empty param.rid}">
    <c:forEach var="review" items="${requestScope.reviewsList}">
        <c:if test="${param.rid == review.reviewsId}">
            <c:set var="selectedReview" value="${review}"/>
        </c:if>    
    </c:forEach>
</c:if>
<c:if test="${not empty requestScope.error}">
    <table border="0" width="100%" cellspacing="0" cellpadding="2">
        <tbody><tr class="messageStackError">
                <td class="messageStackError"><img src="error.gif" border="0" alt="Error" title=" Error ">&nbsp; - ${requestScope.error}</td>
            </tr>  
    </tbody></table>
</c:if>
<section id="main-content">
    <div class="row mt">
        <div class="container">
            <h3 class="page-header" style="margin-left:7px;">Reviews</h3>
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
    
<c:if test="${not empty requestScope.error}">
    <table  border="0" width="100%" cellspacing="0" cellpadding="2">
        <tbody><tr class="messageStackError">
                <td class="messageStackError"><img src="error.gif" border="0" alt="Error" title=" Error ">&nbsp; - ${requestScope.error}</td>
            </tr>  
    </tbody></table>
</c:if>
<table class="contentTable" border="0" width="100%" cellspacing="2" cellpadding="2">
    <tr>
      
        <!-- body_text //-->
        <td width="100%" valign="top"><table border="0" width="100%" cellspacing="0" cellpadding="2">
                
                <tr>
                    <td><table border="0" width="100%" cellspacing="0" cellpadding="0">
                            <tr>
                                <td valign="top"><table border="0" width="100%" cellspacing="0" cellpadding="2">
                                        <tr class="dataTableHeadingRow">
                                        <td class="dataTableHeadingContent">Products</td>
                                        <td class="dataTableHeadingContent" align="right">Rating</td>
                                        <td class="dataTableHeadingContent" align="right">Date Added</td>
                                        <td class="dataTableHeadingContent" align="center">Approved</td>
                                        <td class="dataTableHeadingContent" align="right">Action&nbsp;</td>
                                      </tr>
                                        
                                        <c:if test="${not empty requestScope.reviewsList}">
                                            <c:forEach var="review" items="${requestScope.reviewsList}" varStatus="varstatus">
                                                <c:choose>                                                             
                                                    <c:when test="${selectedReview.reviewsId == review.reviewsId}">
                                                        <c:set var="trclass" value="dataTableRowSelected"/>
                                                         <c:url var="infoImage" value="/resources/assets_admin/images/icon_arrow_right.gif"/>
                                                    </c:when>
                                                    <c:otherwise>  
                                                        <c:set var="trclass" value="dataTableRow"/>
                                                        <c:url var="infoImage" value="/resources/assets_admin/images/icon_info.gif"/>
                                                    </c:otherwise>
                                                </c:choose>
                                                <tr class="${trclass}" onmouseover="rowOverEffect(this)" onmouseout="rowOutEffect(this)" rid="${review.reviewsId}">
                                                
                                                
                                                <td class="dataTableContent">
                                                        
                                                    ${review.products.description}
                                                        </td>
                                                <td class="dataTableContent" align="right">                                                   
                                                    <img src="<c:url value="/resources/assets_admin/images/stars_${review.reviewsRating}.gif"/>" border="0" alt=""/>
                                                  </td>
                                                  
                                                <td class="dataTableContent" align="right"><fmt:formatDate pattern="dd/MM/yyyy" value="${review.dateAdded}" /></td>
                                                <td class="dataTableContent" align="center">
                                                    <c:choose>
                                                        <c:when test="${review.approved==1}">
                                                            <img src="<c:url  value="/resources/assets_admin/images/icon_status_green.gif"/>" border="0" alt="Active" title=" Active " width="10" height="10">
                                                            &nbsp;&nbsp;
                                                            
                                                            <img src="<c:url  value="/resources/assets_admin/images/icon_status_red_light.gif"/>" border="0" alt="Set Inactive" title=" Set Inactive " width="10" height="10">
                                                            
                                                        </c:when>
                                                        <c:otherwise>
                                                            
                                                            <img src="<c:url  value="/resources/assets_admin/images/icon_status_green_light.gif"/>" border="0" alt="Set Active" title=" Set Active " width="10" height="10">
                                                          
                                                            &nbsp;&nbsp;
                                                            <img src="<c:url  value="/resources/assets_admin/images/icon_status_red.gif"/>" border="0" alt="Inactive" title=" Inactive " width="10" height="10">

                                                        </c:otherwise>
                                                    </c:choose>
                                                    <td class="dataTableContent" align="right"><a href="#" onclick="return processOperation('viewReview',${review.reviewsId})"><img class="infoIcon" src="${infoImage}" alt="Info" title=" Info " border="0"></a>&nbsp;</td>
                                                
                                                </tr>
                                            </c:forEach>
                                        </c:if>                                        
                                        
                                        
                                        <c:url var="paginationUrl" value="/admin/reviews?status=1"/>
                                        <c:set var="paginationItem" value="reviews"/>
                                        <%@include file="pagination.jsp" %>
                                        
                                       
                                </table></td>
                                <td width="25%" valign="top">
                                    <div id="reviewsPanel">
                                       <table border="0" width="100%" cellspacing="0" cellpadding="2">
                                            <tbody><tr class="infoBoxHeading">
                                                    <td class="infoBoxHeading"><b></b></td>
                                                </tr>
                                        </tbody></table>
                                        <table border="0" width="100%" cellspacing="0" cellpadding="2">
                                            <tbody><tr>
                                                    <td align="center" class="infoBoxContent">
                                                        Loading ....
                                                    </td>
                                                </tr>                                                                       
                                        </tbody></table>
                                        <script>
                                           
                                        </script> 
                                    </div>
                                </td>
                            </tr>
                    </table></td>
                </tr>
        </table></td>
        <!-- body_text_eof //-->
    </tr>
</table>
<!-- body_eof //-->

</section>
                                        
                                        <script>
                                            
                                            $(document).ready(function(){
                                                 
                                                $("#nav-accordion").find("li").removeClass("active");
                                               // $(".active_order").removeClass("active");
                                                $(".active_reviews").addClass("active");
                                                
                                                processOperation('viewReview',${selectedReview.reviewsId});
                                            });
                                            </script>