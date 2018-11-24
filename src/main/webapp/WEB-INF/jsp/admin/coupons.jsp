
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="<spring:url value="/resources/assets_admin/js/jquery.js"/>"></script>
<c:set var="leftMenuSelectedTab" value="catalog"/>
<!-- body //-->

<script>   
    
    
    var selectedCouponId = 0;
    <c:if test="${not empty requestScope.couponsList}">
    selectedCouponId = ${requestScope.couponsList[0].id};
    </c:if>
    <c:if test="${not empty param.cid}">
    selectedCouponId = ${param.cid};
    </c:if>
    
    var processOperation = function(operation,cid) {
        
        var url = null;
        var couponBaseurl ="<c:url value="/admin/coupons?"/>";       
        if(operation=='viewCoupon'){            
            url=couponBaseurl+"operation=view&page=${param.page}&cid="+cid;
        }else if(operation=='editCoupon'){
        url=couponBaseurl+"operation=edit&page=${param.page}&cid="+cid;
    }else if(operation=='deleteCoupon'){  
    url=couponBaseurl+"operation=delete&page=${param.page}&cid="+cid;
}else if(operation=='newCoupon'){
url=couponBaseurl+"operation=add&page=${param.page}&cid="+selectedCouponId;
}else if(operation=='cancelCoupon'){
url=couponBaseurl+"operation=view&page=${param.page}&cid="+cid;
}


if(url!=null){
    $('#CouponPanel').load(url);
}

updateRowStyle(cid);     
return false;

}


var updateRowStyle = function(cid){
    var previousSelectedTrElement = $('tr[cid="'+selectedCouponId+'"]');
    var currentSelectedTrElement = $('tr[cid="'+cid+'"]');
    $(previousSelectedTrElement).removeClass('dataTableRowSelected dataTableRowOver').addClass('dataTableRow');
    $(previousSelectedTrElement).find('img.infoIcon').attr('src','<c:url value="/resources/assets_admin/images/icon_info.gif"/>');
    
    $(currentSelectedTrElement).removeClass('dataTableRow dataTableRowOver').addClass('dataTableRowSelected');
    $(currentSelectedTrElement).find('img.infoIcon').attr('src','<c:url value="/resources/assets_admin/images/icon_arrow_right.gif"/>');
    selectedCouponId = cid;  
    
}
</script>
<c:set var="selectedCoupon" value="${requestScope.couponsList[0]}"/>
<c:if test="${not empty param.cid}">
    <c:forEach var="coupon" items="${requestScope.couponsList}">
        <c:if test="${param.cid == coupon.id}">
            <c:set var="selectedCoupon" value="${coupon}"/>
        </c:if>    
    </c:forEach>
</c:if>
<section id="main-content">
    <div class="row mt">
        <div class="container">
            <h3 class="page-header" style="margin-left:7px;">Coupons</h3>
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
                            <tr style="background-color:#fff">
                              
                               <td colspan="4" align="" >
                                  <%-- <a href="<c:url value="/admin/couponsSample.xls"/>" style="text-decoration:underline">Download Sample</a>
                                   <a href="<c:url value="/admin/getCouponsUpload.do"/>"><input type="button" value="Upload Excel"/></a>
                                  --%>
                                            <form action="<c:url value="/admin/coupons?operation=add"/>" method="get">
                                                <input type="hidden" value="add" name="operation"/>
                                                <input type="submit" value="New Coupon" name="newcoupon"/>
                                            </form>
                                        </td>
                                 <td align="right"><table border="0" cellpadding="0" cellspacing="0" width="100%">
                                                    <tbody><tr>
                                                            <td class="smallText" align="right">
                                                                <form name="search" action="<c:url value="/admin/coupons"/>" method="get">Search: 
                                                                    <input name="searchTerm" type="text" value="${param.searchTerm}"/>
                                                                </form>              
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                           
                                                            <td class="smallText" align="right">
                                                                <form name="goto" action="<c:url value="/admin/coupons"/>" method="get">Go To: 
                                                                    <select name="searchType" onchange="this.form.submit();" value="${param.searchType}">
                                                                        <option value="">All</option>
                                                                        <option value="active"  <c:if test="${param.searchType=='active'}">selected="selected"</c:if>>Active</option>
                                                                        <option value="inactive" <c:if test="${param.searchType=='inactive'}">selected="selected"</c:if>>Inactive</option>
                                                                        <option value="expiredCount" <c:if test="${param.searchType=='expiredCount'}">selected="selected"</c:if>>ExpiredCount</option>
                                                                        <option value="expiredDate" <c:if test="${param.searchType=='expiredDate'}">selected="selected"</c:if>>ExpiredDate</option>
                                                                        <option value="once" <c:if test="${param.searchType=='once'}">selected="selected"</c:if>>Once</option>
                                                                        <option value="multiple" <c:if test="${param.searchType=='multiple'}">selected="selected"</c:if>>Multiple</option>
                                                                        <option value="unlimited" <c:if test="${param.searchType=='unlimited'}">selected="selected"</c:if>>Unlimited</option>
                                                                        
                                                                    </select>
                                                                </form>              
                                                            </td>
                                                        </tr>
                                            </tbody></table></td>
                            </tr>
                    </table></td>
                </tr>
                <tr>
                    <td><table border="0" width="100%" cellspacing="0" cellpadding="0">
                            <tr>
                                <td valign="top"><table border="0" width="100%" cellspacing="0" cellpadding="2">
                                        <tr class="dataTableHeadingRow">
                                            <td class="dataTableHeadingContent">Coupon Code</td>
                                            <td class="dataTableHeadingContent" align="center" colspan="2">Coupon Title</td>
                                            <td class="dataTableHeadingContent" align="right">Action&nbsp;</td>
                                        </tr>
                                        
                                        <c:if test="${not empty requestScope.couponsList}">
                                            <c:forEach var="coupon" items="${requestScope.couponsList}" varStatus="varstatus">
                                                <c:choose>                                                             
                                                    <c:when test="${selectedCoupon.id == coupon.id}">
                                                        <c:set var="trclass" value="dataTableRowSelected"/>
                                                        <c:url var="infoImage" value="/resources/assets_admin/images/icon_arrow_right.gif"/>
                                                    </c:when>
                                                    <c:otherwise>  
                                                        <c:set var="trclass" value="dataTableRow"/>
                                                        <c:url var="infoImage" value="/resources/assets_admin/images/icon_info.gif"/>
                                                    </c:otherwise>
                                                </c:choose>
                                                <tr class="${trclass}" onmouseover="rowOverEffect(this)" onmouseout="rowOutEffect(this)" cid="${coupon.id}">
                                                    <td class="dataTableContent">${coupon.code}</td>
                                                    <td class="dataTableContent" align="center" colspan="2">${coupon.title}</td> 
                                                    <td class="dataTableContent" align="right"><a href="#" onclick="return processOperation('viewCoupon',${coupon.id})"><img class="infoIcon" src="${infoImage}" alt="Info" title=" Info " border="0"></a>&nbsp;</td>
                                                </tr>
                                            </c:forEach>
                                        </c:if>                                        
                                        
                                        
                                        <c:url var="paginationUrl" value="/admin/coupons?searchType=${param.searchType}"/>
                                        <c:set var="paginationItem" value="coupons"/>
                                        <tr >
                                                    <td colspan="3"></td>
                                                    </tr>
                                        <%@include file="pagination.jsp" %>
                                      
                                </table></td>
                                <td width="25%" valign="top">
                                    <div id="CouponPanel">
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
                                                $(".active_coupons").addClass("active");
                                                
                                                processOperation('viewCoupon',${selectedCoupon.id});
                                            });
                                            </script>