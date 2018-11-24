<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<aside>
    <div id="sidebar"  class="nav-collapse " style="min-height: 100px;overflow-y: auto;">
        <!-- sidebar menu start-->
        <ul class="sidebar-menu" id="nav-accordion" style="min-height: 500px;">

            <a href="javascript:void(0)"><p class="centered">
                <img src='<spring:url value="/resources/assets_admin/images/customer_male.png"/>' class="img-circle" width="60"></p>
                <h5 class="centered">${sessionScope.user.name}</h5>
            </a>

            <li class="sub-menu">
                <a class="active_order" href="orders" >
                    <i class="fa fa-list-alt"></i>
                    <span>Orders</span>
                </a>
            </li>
            <li class="sub-menu">
                <a class="active_cancelorder" href="cancelordermenu" >
                    <i class="fa fa-list-alt"></i>
                    <span>Order Cancellations</span>
                </a>
            </li>
            <li class="sub-menu">
                <a class="active_vendor" href="vendorlist" >
                    <i class="fa fa-list-alt"></i>
                    <span>Vendors</span>
                </a>
            </li>

            <li class="sub-menu">
                <a class="active_preoff" href="premiumoffers" >
                    <i class="fa fa-list-alt"></i>
                    <span>Premium offers</span>
                </a>
            </li>

            <li class="sub-menu">
                <a class="active_exoff" href="exclusiveoffers" >
                    <i class="fa fa-list-alt"></i>
                    <span>Exclusive offers</span>
                </a>
            </li>

            <li  class="sub-menu">
                <a class="active_todoff" href="todayOffers" >
                    <i class="fa fa-list-alt"></i>
                    <span>Today's offers</span>
                </a>
            </li>
            
            <li class="sub-menu">
                <a class="active_pendOff" href="pendingApprovals" >
                    <i class="fa fa-list-alt"></i>
                    <span>Pending Offer Approvals</span>
                </a>
            </li>
            
            <li class="sub-menu">
                <a class="active_seoOff" href="keywordsEdit" >
                    <i class="fa fa-list-alt"></i>
                    <span>Edit Offer Keywords</span>
                </a>
            </li>
            
            <li class="sub-menu">
                <a class="active_cat" href="categories" >
                    <i class="fa fa-list-alt"></i>
                    <span>Categories</span>
                </a>
            </li>
            

            <li class="sub-menu">
                <a class="active_admin" href="adminmenu" >
                    <i class="fa fa-list-alt"></i>
                    <span>Admin</span>
                </a>
            </li>

            <li class="sub-menu">
                <a class="active_locmen" href="locationMenu" >
                    <i class="fa fa-list-alt"></i>
                    <span>Locations</span>
                </a>
            </li>
            <li class="sub-menu">
                <a class="active_custmen" href="customersmenu" >
                    <i class="fa fa-list-alt"></i>
                    <span>Customers</span>
                </a>
            </li>
            <li class="sub-menu">
                <a class="active_coupons" href="coupons" >
                    <i class="fa fa-list-alt"></i>
                    <span>Coupons</span>
                </a>
            </li>
             <li class="sub-menu">
                <a class="active_reviews" href="reviews" >
                    <i class="fa fa-list-alt"></i>
                    <span>Reviews</span>
                </a>
            </li>
            <li class="sub-menu">
                <a class="active_reports" href="reports" >
                    <i class="fa fa-list-alt"></i>
                    <span>Reports</span>
                </a>
            </li>

        </ul>
        <!-- sidebar menu end -->
    </div>
</aside>
