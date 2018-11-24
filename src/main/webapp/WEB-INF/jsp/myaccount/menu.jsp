<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/custom" %> 
  
<%
String path = (String)request.getAttribute("javax.servlet.forward.request_uri");
pageContext.setAttribute("path",path);
%>

        <div class="column col-xs-12 col-sm-3">
    <div class="block left-module">
        <p class="title_block">My Account</p>
        <div class="block_content">
            <!-- layered -->
            <div class="layered layered-category">
                <div class="layered-content">
                    <ul class="tree-menu">
                        <li <c:if test="${fn:containsIgnoreCase(path, 'accountprofile')== true }">class="active"</c:if>><a href="<spring:url value="/myaccount/accountprofile"/>" >Account Profile</a></li>
                        <li <c:if test="${fn:containsIgnoreCase(path, 'addressbook')== true }">class="active"</c:if>><a href="<spring:url value="/myaccount/addressbook"/>" >Address Book</a></li>
                        <li <c:if test="${fn:containsIgnoreCase(path, 'order')== true }">class="active"</c:if>><a  href="<spring:url value="/myaccount/orders"/>" >My Orders</a></li>
                        <li <c:if test="${fn:containsIgnoreCase(path, 'coupons')== true }">class="active"</c:if>><a  href="<spring:url value="/myaccount/coupons"/>" >My Coupons</a></li>
                        <li <c:if test="${fn:containsIgnoreCase(path, 'favourites')== true }">class="active"</c:if>><a  href="<spring:url value="/myaccount/favourites"/>" >My Wishlist</a></li>
                        <li <c:if test="${fn:containsIgnoreCase(path, 'changepassword')== true }">class="active"</c:if>><a href="<spring:url value="/myaccount/changepassword"/>" >Change Password</a></li>
                        <%--<li class="referfriend_active"><a href="<spring:url value="/myaccount/referfriend"/>" >Refer a friend</a></li>--%>
                        <%--<li class="feedback_active"><a href="<spring:url value="/myaccount/feedback"/>" >Feedback</a></li>--%>
                        <li><a href="<spring:url value="/logout"/>">Logout</a></li>

                    </ul>
                </div>
            </div>
            <!-- ./layered -->
        </div>
    </div>
</div>