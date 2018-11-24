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
        <td width="100%" valign="top"><table border="0" width="100%" cellspacing="0" cellpadding="2">
        <tr>
            <td width="100%"><table border="0" width="100%" cellspacing="0" cellpadding="0">
                    <tr>
                        <td class="pageHeading"><h3>Reviews</h3></td>
                        <td class="pageHeading" align="right"><img src="<c:url value="/resources/assets_admin/images/pixel_trans.gif"/>" border="0" alt="" width="165" height="1"></td>
                    </tr>
            </table></td>
        </tr>
        <tr><form name="review" action="<c:url value="/admin/reviews"/>" method="post">     
        <td><table border="0" width="100%" cellspacing="0" cellpadding="0">
                    <tr>
                        <td class="main" valign="top"><b>Product:</b>  
                        ${selectedReviews.products.description}
                            <br><b>From:</b> ${selectedReviews.customersName}<br><br><b>Date:</b> <fmt:formatDate pattern="dd/MM/yyyy" value="${selectedReviews.dateAdded}" /></td>
                        <td class="main" align="right" valign="top"><img src="<c:url value="https://s3-us-west-2.amazonaws.com/adrobe.in.assets/${selectedReviews.products.image}"/>" border="0" alt="" title="" width="190" height="254" hspace="5" vspace="5"></td>
                    </tr>
            </table></td>
        </tr>
        <tr>
            <td><table witdh="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                         
                            
                        <td class="main" valign="top"><b>Review:</b><br><br>
                            <textarea name="reviews_text" wrap="soft" cols="60" rows="15">${selectedReviews.reviewsText}</textarea></td>
                    </tr>
                    <tr>
                        <td class="smallText" align="right"><small><font color="#ff0000"><b>NOTE:</b></font></small>&nbsp;HTML is not translated!&nbsp;</td>
                    </tr>
            </table></td>
        </tr>
        <tr>
            <td><img src="<c:url value="/resources/assets_admin/images/pixel_trans.gif"/>" border="0" alt="" width="1" height="10"></td>
        </tr>
        <tr>
            <td class="main"><b>Rating:</b>&nbsp;<small><font color="#ff0000"><b>BAD</b></font></small>&nbsp;
                <input type="radio" name="reviews_rating" value="1" <c:if test="${selectedReviews.reviewsRating==1 || selectedReviews.reviewsRating==0}">checked="checked"</c:if>>&nbsp;
            <input type="radio" name="reviews_rating" value="2"  <c:if test="${selectedReviews.reviewsRating==2}">checked="checked"</c:if>>&nbsp;
            <input type="radio" name="reviews_rating" value="3"  <c:if test="${selectedReviews.reviewsRating==3}">checked="checked"</c:if>>&nbsp;
            <input type="radio" name="reviews_rating" value="4"  <c:if test="${selectedReviews.reviewsRating==4}">checked="checked"</c:if>>&nbsp;
            <input type="radio" name="reviews_rating" value="5"  <c:if test="${selectedReviews.reviewsRating==5}">checked="checked"</c:if>>&nbsp;
            <small><font color="#ff0000"><b>GOOD</b></font></small></td>
        </tr>
        <tr>
            <td><img src="<c:url value="/resources/assets_admin/images/pixel_trans.gif"/>" border="0" alt="" width="1" height="10"></td>
        </tr>
        <tr>
            <td align="right" class="main">
                
                <input type="hidden" name="rid" value="${selectedReviews.reviewsId}">
                <input type="hidden" name="page" value="${param.page}">
                <input type="hidden" name="operation" value="update">
               
               <input type="image" src="<c:url value="/resources/assets_admin/images/button_update.gif"/>" border="0" alt="Update" title=" Update ">                   
              
                <a href="<c:url value="/admin/reviews?page=${param.page}&rid=${selectedReviews.reviewsId}"/>">
                    <img src="<c:url value="/resources/assets_admin/images/button_cancel.gif"/>" border="0" alt="Cancel" title=" Cancel ">
                </a>
            </td>
        </form></tr>
    </table></td>
        <!-- body_text_eof //-->
    </tr>
</table>

<!-- body_eof //-->
</section>
                       