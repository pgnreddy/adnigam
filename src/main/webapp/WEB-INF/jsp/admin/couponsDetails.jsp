<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="store" tagdir="/WEB-INF/tags/storeCustomTags"%>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html"%>

<c:choose>
    <c:when test="${param.operation=='view'}">
        <div id="viewCountryPanel">
        
        <table border="0" width="100%" cellspacing="0" cellpadding="2">
            <tr class="infoBoxHeading">
                <td class="infoBoxHeading">${selectedCoupon.id}&nbsp;&nbsp;&nbsp;<b>${selectedCoupon.code}</b></td>
            </tr>
        </table>
        <table border="0" width="100%" cellspacing="0" cellpadding="2">
            <tr>
                <td align="center" class="infoBoxContent">
                    <a href="<c:url value="/admin/coupons?operation=edit&cid=${selectedCoupon.id}"/>"  >
                        <img src="<c:url value="/resources/assets_admin/images/button_edit.gif"/>" alt="Edit" title=" Edit " border="0">
                     </a>
                    <a href="#" onclick="return processOperation('deleteCoupon',${selectedCoupon.id})"><img src="<c:url value="/resources/assets_admin/images/button_delete.gif"/>" alt="Delete" title=" Delete " border="0"></a>
                </td>
            </tr>
            <tr>
                <td class="infoBoxContent"><br>Code:${selectedCoupon.code}</td>
            </tr>
            <tr>
                <td class="infoBoxContent"><br>Title: ${selectedCoupon.title}</td>
            </tr>
            <tr>
                <td class="infoBoxContent"><br>Discount : ${selectedCoupon.discount_value}
                    <c:choose>
                        <c:when test="${selectedCoupon.discount_type==1}">
                            Flat
                        </c:when>
                        <c:otherwise>
                            %
                        </c:otherwise>
                    </c:choose>
            </td>
            </tr>
            <tr>
                <td class="infoBoxContent"><br>Applies: 
                <c:choose>
                        <c:when test="${selectedCoupon.usage_type==1}">
                            Multiple
                        </c:when>
                        <c:when test="${selectedCoupon.usage_type==2}">
                            Unlimited
                        </c:when>
                        <c:otherwise>
                            Once
                        </c:otherwise>
                    </c:choose>
               </td>
            </tr>
            <tr>
                <td class="infoBoxContent"><br>Max Count : ${selectedCoupon.max_redeem_count}</td>
            </tr>
            <tr>
                <td class="infoBoxContent"><br>Number of times coupon should be used by user :${selectedCoupon.number_of_times_to_use}</td>
            </tr>
            <tr>
                <td class="infoBoxContent"><br>Allow to use multiple times by user :
               
                    <c:choose>
                        <c:when test="${selectedCoupon.allow_multiple_redeems_by_same_user==1}">
                            Allowed
                        </c:when>
                        <c:otherwise>
                            Not Allowed
                        </c:otherwise>
                    </c:choose> 
                </td>
            </tr>
            <tr>
                <td class="infoBoxContent"><br>Number of redeems: ${selectedCoupon.number_of_redeems}</td>
            </tr>
            <tr>
                <td class="infoBoxContent"><br>Min Amount: ${selectedCoupon.min_amount}</td>
            </tr>
            <tr>
                <td class="infoBoxContent"><br>Status:
                <c:choose>
                        <c:when test="${selectedCoupon.status==1}">
                            Active
                        </c:when>
                        <c:otherwise>
                            Inactive
                        </c:otherwise>
                    </c:choose>                
                </td>
            </tr>
            <tr>
                <td class="infoBoxContent"><br>Start Date: <fmt:formatDate pattern="dd/MM/yyyy" value="${selectedCoupon.start_date}" /></td>
            </tr>
            <tr>
                <td class="infoBoxContent"><br>End Date: <fmt:formatDate pattern="dd/MM/yyyy" value="${selectedCoupon.end_date}" /></td>
            </tr>
        </table>            
        
    </c:when>    
    <c:when test="${param.operation=='delete'}">
        <div id="deletePanel">
            
            
            <table border="0" width="100%" cellspacing="0" cellpadding="2">
                <tbody><tr class="infoBoxHeading">
                        <td class="infoBoxHeading"><b>Delete Coupon</b></td>
                    </tr>
            </tbody></table>
            <form name="coupons" action="<c:url value="/admin/coupons"/>" method="post">
                <table border="0" width="100%" cellspacing="0" cellpadding="2">
                    <tbody><tr>
                            <td class="infoBoxContent">Are you sure you want to delete this Coupon?</td>
                        </tr>
                        <tr>
                            <td class="infoBoxContent"><br><b>${selectedCoupon.code}</b>
                                <input type="hidden" name="operation" value="confirmDelete"/>
                                <input type="hidden" name="page" value="${param.page}"/>
                                <input type="hidden" name="cid" value="${selectedCoupon.id}"/>
                                
                            </td>
                        </tr>
                        <tr>
                            <td align="center" class="infoBoxContent"><br>
                                <input type="image" src="<c:url value="/resources/assets_admin/images/button_delete.gif"/>" border="0" alt="Update" title=" Update ">&nbsp;
                                <a href="#" onclick="return processOperation('cancelCoupon',${param.cid})">
                                    <img src="<c:url value="/resources/assets_admin/images/button_cancel.gif"/>" border="0" alt="Cancel" title=" Cancel ">
                                </a>
                            </td>
                        </tr>
                </tbody></table>
            </form>
            
            
        </div>
    </c:when>    
   
    <c:otherwise>
        
    </c:otherwise>
    </c:choose>
    