<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
String path = (String)request.getAttribute("javax.servlet.forward.request_uri");
pageContext.setAttribute("path",path);
%>
<aside>
                <div id="sidebar"  class="nav-collapse ">
                    <!-- sidebar menu start-->
                    <ul class="sidebar-menu" id="nav-accordion">

                        <a href="profile"><p class="centered"><img src='<spring:url value="/resources/assets_admin/images/customer_male.png"/>' class="img-circle" width="60"></p>
                        <h5 class="centered">${sessionScope.user.name}</h5>
                        </a>

                        <li class="sub-menu">
                            <a <c:if test="${fn:containsIgnoreCase(path, 'offers')== true }">class="active"</c:if> href="offers" >
                                <i class="fa fa-puzzle-piece"></i>
                                <span>Add Offers</span>
                            </a>
                        </li>

                        <li class="sub-menu">
                            <a <c:if test="${fn:containsIgnoreCase(path, 'requests')== true }">class="active"</c:if> href="requests" >
                                <i class="fa fa-envelope-o"></i>
                                <span>User Request's</span>
                            </a>
                        </li>
                        
                        <li class="sub-menu">
                            <a <c:if test="${fn:containsIgnoreCase(path, 'orders')== true }">class="active"</c:if> href="orders" >
                                <i class="fa fa-envelope-o"></i>
                                <span>Orders</span>
                            </a>
                        </li>
                        
                        <li class="sub-menu">
                            <a <c:if test="${fn:containsIgnoreCase(path, 'productCoupons')== true }">class="active"</c:if>  href="productCoupons" >
                                <i class="fa fa-envelope-o"></i>
                                <span>Product Coupons</span>
                            </a>
                        </li>
                        
                         <li class="sub-menu">
                            <a <c:if test="${fn:containsIgnoreCase(path, 'message')== true }">class="active"</c:if> href="message" >
                                <i class="fa fa-envelope-o"></i>
                                <span>Send message</span>
                            </a>
                        </li>

                        <%--<li class="sub-menu">
                            <a href="reports.html" >
                                <i class="fa fa-list-alt"></i>
                                <span>Reports</span>
                            </a>
                        </li>--%>

                    </ul>
                    <!-- sidebar menu end-->
                </div>
            </aside>
