<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link type="text/css" href="<c:url value="/resources/assets_admin/lib/jquery-ui/jquery-ui.css"/>" rel="stylesheet" />

<c:set var="leftMenuSelectedTab" value="catalog"/>
<script>
    function changeToUpper(obj){
        obj.value=obj.value.toUpperCase();
    }    
        
</script>
<!-- body //-->
<section id="main-content" class="white">
     <div class="row mt">
        <div class="container">
            <h3 class="page-header" style="margin-left:7px;"></h3>
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
    
<table border="0" width="100%" cellspacing="2" cellpadding="2">
    <tr>
        
        <!-- body_text //-->
        <td width="100%" valign="top">
            <form action="<c:url value="/admin/coupons.do"/>" method="post" >
            
                <table border="0" width="100%" cellspacing="0" cellpadding="2">
                 
                 <c:if test="${not empty requestScope.error}">
                   <tr><td> <table border="0" width="100%" cellspacing="0" cellpadding="2">
                        <tbody><tr class="messageStackError">
                                <td class="messageStackError"><img src="<c:url value="/resources/assets_admin/images/error.gif"/>" border="0" alt="Error" title=" Error ">&nbsp; - ${requestScope.error}</td>
                            </tr>  
                </tbody></table></td></tr>
                </c:if>

                    <tr>
                        <td><img src="<c:url value="/resources/assets_admin/images/pixel_trans.gif"/>" border="0" alt="" width="1" height="10">
                            <c:if test="${param.operation=='add' || param.operation=='insert' }">
                                <input type="hidden" name="operation" value="insert"/>
                            </c:if>
                            <c:if test="${param.operation=='edit' || param.operation=='update' }">
                                <input type="hidden" name="operation" value="update"/>
                                <input type="hidden" name="id" value="${requestScope.selectedCoupon.id}"/>
                            </c:if>
                            </td>
                    </tr>
                    <tr>
                        <td><table border="0" cellspacing="0" cellpadding="2">
                                <tr>
                                    <td class="main">Coupon Status:</td>
                                    <td class="main"><img src="<c:url value="/resources/assets_admin/images/pixel_trans.gif"/>" border="0" alt="" width="24" height="15">&nbsp;
                                    
                                    <input type="radio"  name="status" value="1" <c:if test="${empty requestScope.selectedCoupon || requestScope.selectedCoupon.status==1}">checked="checked"</c:if> />&nbsp;Active&nbsp;
                                        <input type="radio"  name="status" value="0"  <c:if test="${requestScope.selectedCoupon.status==0}">checked="checked"</c:if>/>&nbsp;InActive&nbsp;
                                   </td>
                                </tr>
                                <tr>
                                    <td colspan="2"><img src="<c:url value="/resources/assets_admin/images/pixel_trans.gif"/>" border="0" alt="" width="1" height="10"></td>
                                </tr>
                                 <tr>
                                    <td class="main">Coupon Code:</td>
                                    <td class="main"><img src="<c:url value="/resources/assets_admin/images/pixel_trans.gif"/>" border="0" alt="" width="24" height="15">&nbsp;
                                    
                                        <input type="text"  name="code"  value="${requestScope.selectedCoupon.code}" onKeyUp="changeToUpper(this)"/>(Required)
                                        
                                   </td>
                                </tr>
                              
                                <tr>
                                    <td colspan="2"><img src="<c:url value="/resources/assets_admin/images/pixel_trans.gif"/>" border="0" alt="" width="1" height="10"></td>
                                </tr>
                                <tr>
                                    <td class="main">Coupon Title:</td>
                                    <td class="main"><img src="<c:url value="/resources/assets_admin/images/pixel_trans.gif"/>" border="0" alt="" width="24" height="15">&nbsp;
                                    
                                        <input type="text"  name="title"   value="${requestScope.selectedCoupon.title}"/>(Required)
                                        
                                   </td>
                                </tr>
                                <tr>
                                    <td colspan="2"><img src="<c:url value="/resources/assets_admin/images/pixel_trans.gif"/>" border="0" alt="" width="1" height="10"></td>
                                </tr>
                                <tr>
                                    <td class="main">Coupon Description:</td>
                                    <td class="main"><img src="<c:url value="/resources/assets_admin/images/pixel_trans.gif"/>" border="0" alt="" width="24" height="15">&nbsp;
                                    
                                    <textarea name="description" cols="50" >${requestScope.selectedCoupon.description}</textarea>(Optional)
                                        
                                   </td>
                                </tr>
                                
                                 <tr>
                                    <td colspan="2"><img src="<c:url value="/resources/assets_admin/images/pixel_trans.gif"/>" border="0" alt="" width="1" height="10"></td>
                                </tr>
                                <tr>
                                    <td class="main">Discount</td>
                                    <td class="main"><img src="<c:url value="/resources/assets_admin/images/pixel_trans.gif"/>" border="0" alt="" width="24" height="15">&nbsp;
                                       <input type="text"  name="discount_value"  value="${requestScope.selectedCoupon.discount_value}"   />
                                       <select name="discount_type">
                                           <option value="0" <c:if test="${requestScope.selectedCoupon.discount_type==0}">selected="selected"</c:if> >%</option>
                                           <option value="1" <c:if test="${requestScope.selectedCoupon.discount_type==1}">selected="selected"</c:if> >Flat</option>
                                       </select>
                                   </td>
                                </tr>
                                
                                 <tr>
                                    <td colspan="2"><img src="<c:url value="/resources/assets_admin/images/pixel_trans.gif"/>" border="0" alt="" width="1" height="10"></td>
                                </tr>
                                <tr>
                                    <td class="main">Applies:</td>
                                    <td class="main"><img src="<c:url value="/resources/assets_admin/images/pixel_trans.gif"/>" border="0" alt="" width="24" height="15">&nbsp;                                       
                                        <select name="usage_type">
                                           <option value="0" <c:if test="${requestScope.selectedCoupon.usage_type==0}">selected="selected"</c:if>>Once</option>
                                           <option value="1" <c:if test="${requestScope.selectedCoupon.usage_type==1}">selected="selected"</c:if>>Multiple</option>
                                           <option value="2" <c:if test="${requestScope.selectedCoupon.usage_type==2}">selected="selected"</c:if>>Unlimited</option>
                                       </select>
                                        
                                   </td>
                                </tr>
                                
                                 <tr>
                                    <td colspan="2"><img src="<c:url value="/resources/assets_admin/images/pixel_trans.gif"/>" border="0" alt="" width="1" height="10"></td>
                                </tr>
                                <tr>
                                    <td class="main">Max Count:</td>
                                    <td class="main"><img src="<c:url value="/resources/assets_admin/images/pixel_trans.gif"/>" border="0" alt="" width="24" height="15">&nbsp;
                                    
                                        <input type="text"  name="max_redeem_count"  value="${requestScope.selectedCoupon.max_redeem_count}" />
                                        (Required only when Applies is multiple)
                                   </td>
                                </tr>
                                
                                 <tr>
                                    <td colspan="2"><img src="<c:url value="/resources/assets_admin/images/pixel_trans.gif"/>" border="0" alt="" width="1" height="10"></td>
                                </tr>
                                <tr>
                                    <td class="main">Number of times coupon should be used by user:</td>
                                    <td class="main"><img src="<c:url value="/resources/assets_admin/images/pixel_trans.gif"/>" border="0" alt="" width="24" height="15">&nbsp;
                                    
                                        <input type="text"  name="number_of_times_to_use"  value="${requestScope.selectedCoupon.number_of_times_to_use}" />
                                        (Required only when discount type is flat)
                                   </td>
                                </tr>
                                
                                 <tr>
                                    <td colspan="2"><img src="<c:url value="/resources/assets_admin/images/pixel_trans.gif"/>" border="0" alt="" width="1" height="10"></td>
                                </tr>
                                <tr>
                                    <td class="main">Allow to use multiple times by user:</td>
                                    <td class="main"><img src="<c:url value="/resources/assets_admin/images/pixel_trans.gif"/>" border="0" alt="" width="24" height="15">&nbsp;
                                    
                                    <input type="radio"  name="allow_multiple_redeems_by_same_user"  value="0" <c:if test="${empty requestScope.selectedCoupon || requestScope.selectedCoupon.allow_multiple_redeems_by_same_user==0}">checked='checked'</c:if>/>Not Allow
                                        <input type="radio"  name="allow_multiple_redeems_by_same_user"  value="1" <c:if test="${requestScope.selectedCoupon.allow_multiple_redeems_by_same_user==1}">checked='checked'</c:if>/>Allow
                                        (Required only when Applies is once and number of times coupon be used is 1)
                                   </td>
                                </tr>
                                
                                 <tr>
                                    <td colspan="2"><img src="<c:url value="/resources/assets_admin/images/pixel_trans.gif"/>" border="0" alt="" width="1" height="10"></td>
                                </tr>
                                <tr>
                                    <td class="main">Minimum Amount:</td>
                                    <td class="main"><img src="<c:url value="/resources/assets_admin/images/pixel_trans.gif"/>" border="0" alt="" width="24" height="15">&nbsp;
                                    
                                        <input type="text"  name="min_amount"  value="${requestScope.selectedCoupon.min_amount}"   />(Optional)
                                        
                                   </td>
                                </tr>
                                
                                 <tr>
                                    <td colspan="2"><img src="<c:url value="/resources/assets_admin/images/pixel_trans.gif"/>" border="0" alt="" width="1" height="10"></td>
                                </tr>
                                <tr>
                                    <td class="main">Start Date:</td>
                                    <td class="main"><img src="<c:url value="/resources/assets_admin/images/pixel_trans.gif"/>" border="0" alt="" width="24" height="15">&nbsp;
                                    
                                        <input type="text"  id="start_date" readonly="true" name="start_date_str" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${requestScope.selectedCoupon.start_date}" />"   />(Optional)
                                        
                                   </td>
                                </tr>
                                
                                 <tr>
                                    <td colspan="2"><img src="<c:url value="/resources/assets_admin/images/pixel_trans.gif"/>" border="0" alt="" width="1" height="10"></td>
                                </tr>
                                <tr>
                                    <td class="main">End Date:</td>
                                    <td class="main"><img src="<c:url value="/resources/assets_admin/images/pixel_trans.gif"/>" border="0" alt="" width="24" height="15">&nbsp;
                                    
                                        <input type="text"  id="end_date" readonly="true" name="end_date_str"  value="<fmt:formatDate pattern="dd/MM/yyyy" value="${requestScope.selectedCoupon.end_date}" />"  />(Optional)
                                        
                                   </td>
                                </tr>
                                
                                
                         <tr>      
                        <td class="main" align="right">
                            <c:choose>
                                <c:when test="${param.operation=='edit' || param.operation=='update' }">
                                <input type="image" src="<c:url value="/resources/assets_admin/images/button_update.gif"/>" border="0" alt="Update" title=" Update " >&nbsp;&nbsp; 
                            </c:when>
                            <c:otherwise>
                              <input type="image" src="<c:url value="/resources/assets_admin/images/button_insert.gif"/>" border="0" alt="Insert" title=" Insert " >&nbsp;&nbsp;   
                            </c:otherwise>
                            </c:choose>
                            
                            <!--<input type="image" src="button_preview.gif" border="0" alt="Preview" title=" Preview ">&nbsp;&nbsp;-->
                            <a href="<c:url value="/admin/coupons.do"/>">
                                <img src="<c:url value="/resources/assets_admin/images/button_cancel.gif"/>" border="0" alt="Cancel" title=" Cancel ">
                            </a>      
                        </td>
                    </tr>
                   
                </table>
            </form>
        </td>
        <!-- body_text_eof //-->
    </tr>
</table>

                            <script type="text/javascript" src="<c:url value="/resources/assets_admin/js/jquery-1.8.3.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/assets_admin/js/jquery-ui-1.9.2.custom.min.js"/>"></script>
<script>
    $(function() {
		//$.datepicker.setDefaults($.datepicker.regional['']);

		$( "#start_date" ).datepicker({showOn: "button",
			buttonImage: "<c:url value="/resources/assets_admin/images/calendar.gif"/>",
			buttonImageOnly: true,
			changeMonth: true,
			changeYear: true,
			yearRange: '1900:2050',
			dateFormat:'dd/mm/yy',
			closeText:'X'
			});
			
			
		$( "#end_date" ).datepicker({showOn: "button",
			buttonImage: "<c:url value="/resources/assets_admin/images/calendar.gif"/>",
			buttonImageOnly: true,
			changeMonth: true,
			changeYear: true,
			yearRange: '1900:2050',
			dateFormat:'dd/mm/yy',
			closeText:'X'
			});
	});
	
</script>

<!-- body_eof //-->
</section>
                       