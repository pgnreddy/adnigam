<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="store" tagdir="/WEB-INF/tags/storeCustomTags"%>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:choose>
    <c:when test="${param.operation=='view'}">            
    
        <div id="viewCountryPanel">
        
        <table border="0" width="100%" cellspacing="0" cellpadding="2">
            <tbody><tr class="infoBoxHeading">
                    <td class="infoBoxHeading"><b>
                            ${selectedReviews.products.description}
                    </b></td>
                </tr>
        </tbody></table>
        <table border="0" width="100%" cellspacing="0" cellpadding="2">
            <tbody><tr>
                    <td align="center" class="infoBoxContent">
                        <a href="<c:url value="/admin/reviews?page=${param.page}&operation=edit&rid=${selectedReviews.reviewsId}"/>"><img src="<c:url value="/resources/assets_admin/images/button_edit.gif"/>" alt="Edit" title=" Edit " border="0"></a> 
                        <a href="#" onclick="return processOperation('deleteReview',${selectedReviews.reviewsId})"><img src="<c:url value="/resources/assets_admin/images/button_delete.gif"/>" alt="Delete" title=" Delete " border="0"></a>
                     </td>                    
                </tr>
                <tr>
                    <td class="infoBoxContent"><br>Date Added: <fmt:formatDate pattern="dd/MM/yyyy" value="${selectedReviews.dateAdded}" /></td>
                </tr>
                <tr>
                    <td class="infoBoxContent"><br><img src="<spring:url value="https://s3-us-west-2.amazonaws.com/adrobe.in.assets/${selectedReviews.products.image}"/>" border="0" alt="" title="" width="190" height="254"></td>
                </tr>
                <tr>
                    <td class="infoBoxContent"><br>Author: ${selectedReviews.customersName}</td>
                </tr>
                <tr>
                    <td class="infoBoxContent">Rating: <img src="<c:url value="/resources/assets_admin/images/stars_${selectedReviews.reviewsRating}.gif"/>" border="0" alt=""></td>
                </tr>
               <%-- <tr>
                    <td class="infoBoxContent">Read: ${selectedReviews.reviewsRead}</td>
                </tr>   --%>     
               
                <tr>
                    <td class="infoBoxContent">Comment : ${selectedReviews.reviewsText}</td>
                </tr>
                <tr>
                    <td class="infoBoxContent"><br>Average Rating: ${requestScope.averageRating}%</td>
                </tr>
                <tr>
                    <td align="left" class="infoBoxContent"><br>&nbsp;Approved: 
                        <c:choose>
                            <c:when test="${selectedReviews.approved==1}">
                                Yes
                            </c:when>
                            <c:otherwise>
                                No
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
                <tr>
                    <td align="center" class="infoBoxContent">
                        
                        <c:choose>
                            <c:when test="${selectedReviews.approved==1}">
                                <a href="<c:url value="/admin/reviews?page=${param.page}&operation=reject&rid=${selectedReviews.reviewsId}"/>"><img src="<c:url value="/resources/assets_admin/images/review_reject.gif"/>" border="0" alt="Reject" title=" Reject "></a>
                            </c:when>
                            <c:otherwise>
                                <a href="<c:url value="/admin/reviews?page=${param.page}&operation=approve&rid=${selectedReviews.reviewsId}"/>"><img src="<c:url value="/resources/assets_admin/images/review_approve.gif"/>" border="0" alt="Approve" title=" Approve "></a>
                            </c:otherwise>
                        </c:choose>
                        </td>
                </tr>
        </tbody></table>
        
    </c:when>    
    <c:when test="${param.operation=='delete'}">
        <div id="deletePanel">
            
            
            <table border="0" width="100%" cellspacing="0" cellpadding="2">
                <tbody><tr class="infoBoxHeading">
                        <td class="infoBoxHeading"><b>Delete Review</b></td>
                    </tr>
            </tbody></table>
            <form name="zones" action="<c:url value="/admin/reviews"/>" method="post">
                <table border="0" width="100%" cellspacing="0" cellpadding="2">
                    <tbody><tr>
                            <td class="infoBoxContent">Are you sure you want to delete this review?</td>
                        </tr>
                        <tr>
                            <td class="infoBoxContent"><br><b>
                               ${selectedReviews.products.description}
                                </b>
                                <input type="hidden" name="operation" value="confirmDelete"/>
                                <input type="hidden" name="page" value="${param.page}"/>
                                <input type="hidden" name="rid" value="${selectedReviews.reviewsId}"/>
                                
                            </td>
                        </tr>
                        <tr>
                            <td align="center" class="infoBoxContent"><br>
                                <input type="image" src="<c:url value="/resources/assets_admin/images/button_delete.gif"/>" border="0" alt="Update" title=" Update ">&nbsp;
                                <a href="#" onclick="return processOperation('cancelReview',${selectedReviews.reviewsId})">
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
    