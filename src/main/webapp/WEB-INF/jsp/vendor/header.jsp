<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

    <header class="header black-bg">
              <div class="sidebar-toggle-box">
                  <div class="fa fa-bars tooltips" data-placement="right" data-original-title="Toggle Navigation"></div>
              </div>
            <!--logo start-->
            <a href="addoffers.html" class="logo"><b>ADNIGAM</b></a>
            <!--logo end-->
           <%-- <div class="nav notify-row" id="top_menu">
                <!--  notification start -->
                <ul class="nav top-menu">
					<!-- profile dropdown start-->
                    <li id="header_inbox_bar" class="dropdown">
                        <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                            <i class="fa fa-envelope-o"></i>
                            <span class="badge bg-theme">5</span>
                        </a>
                        <ul class="dropdown-menu extended inbox">
                            <div class="notify-arrow notify-arrow-green"></div>
                            <li>
                                <p class="green">You have 5 new messages</p>
                            </li>
                            <li>
                                <a href="#">
                                    <span class="photo"><img alt="avatar" src="images/customer_male.png"></span>
                                    <span class="subject">
                                    <span class="from">Giri</span>
                                    <span class="time">Just now</span>
                                    </span>
                                    <span class="message">
                                        Hi vendor, how is everything?
                                    </span>
                                </a>
                            </li>                        
                            <li>
                                <a href="#">See all messages</a>
                            </li>
                        </ul>
                    </li>
                    <!-- profile dropdown end -->
					
                    <!-- inbox dropdown start-->
                    <li id="header_inbox_bar" class="dropdown">
                        <a data-toggle="dropdown" class="dropdown-toggle" href="index.html#">
                            <i class="fa fa-envelope-o"></i>
                            <span class="badge bg-theme">5</span>
                        </a>
                        <ul class="dropdown-menu extended inbox">
                            <div class="notify-arrow notify-arrow-green"></div>
                            <li>
                                <p class="green">You have 5 new messages</p>
                            </li>
                            <li>
                                <a href="#">
                                    <span class="photo"><img alt="avatar" src="images/customer_male.png"></span>
                                    <span class="subject">
                                    <span class="from">Giri</span>
                                    <span class="time">Just now</span>
                                    </span>
                                    <span class="message">
                                        Hi vendor, how is everything?
                                    </span>
                                </a>
                            </li>                           
                            <li>
                                <a href="#">See all messages</a>
                            </li>
                        </ul>
                    </li>
                    <!-- inbox dropdown end -->
					
					
                </ul>
                <!--  notification end -->
            </div>--%>
            <div class="top-menu">
            	<ul class="nav pull-right top-menu">
                    <li><a class="logout" href="<spring:url value="/vendor/logout"/>">Logout</a></li>
            	</ul>
            </div>
        </header>
