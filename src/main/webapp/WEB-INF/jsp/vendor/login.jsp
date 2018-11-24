<%-- 
    Document   : login
    Created on : Jul 29, 2015, 10:20:42 PM
    Author     : santosh.r
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>

<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <title><spring:message code="vendor.page.title" text="Vendor"/></title>

        <!-- Bootstrap CSS -->
        <link href="<spring:url value="/resources/assets_admin/css/bootstrap.css"/>" rel="stylesheet">
        <!--external css-->
        <link href="<spring:url value="/resources/assets_admin/font-awesome/css/font-awesome.css"/>" rel="stylesheet" />

        <!-- styles -->
        <link href="<spring:url value="/resources/assets_admin/css/style_v.css"/>" rel="stylesheet">
        <link href="<spring:url value="/resources/assets_admin/css/style-responsive.css"/>" rel="stylesheet">
        <link href="<spring:url value="/resources/assets_admin/css/fileinput.min.css"/>" rel="stylesheet">

        <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!--[if lt IE 9]>
          <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
          <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->
    </head>

    <body>

        <!-- MAIN CONTENT -->

        <div id="login-page">
            <div class="container">

                <div class="form-login">
                    <h2 class="form-login-heading">sign in now</h2>

                    <!--<select class="login-select-category" onchange="location = this.options[this.selectedIndex].value;">
                        <option value="#">Select Category</option>
                        <option value="">Fashion</option>
                        <option value="#">Mobiles &amp; Computers</option>
                        <option value="#">Electronics</option>
                        <option value="#">Food &amp; Beverages</option>
                        <option value="#">FootWare</option>
                        <option value="#">Jewellery</option>
                        <option value="#">Sports &amp; Wellness</option>
                        <option value="#">Health</option>
                        <option value="#">Real Estate</option>
                        <option value="#">Tours &amp; Travels</option>
                        <option value="#">Groceries</option>
                        <option value="#">Education</option>
                        <option value="#">Big Retails</option>
                        <option value="#">Others &amp; Services</option>
                        <option value="#">Auto-mobiles</option>
                    </select>-->
                    
                    <div class="login-wrap" >
                        <form id="sign-in">
                        <div id="error" class="login_error"></div>
                        <input id="username" type="text" class="form-control" placeholder="Mobile number" autofocus>
                        <br>
                        <input id="password" type="password" class="form-control" placeholder="Password">
                        <label class="checkbox">
                            <span class="pull-right">
                                <a data-toggle="modal" href="login.html#myModal"> Forgot Password?</a>

                            </span>
                        </label>
                        <button id="login-btn" class="btn btn-theme btn-block" href="progile.html" type="submit"><i class="fa fa-lock"></i> SIGN IN</button>
                        </form>
                    </div>

                    <!-- Modal -->
                    <div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog" tabindex="-1" id="myModal" class="modal fade">
                        <div class="modal-dialog">
                            <div id="forgotPasswordForm" class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                    <h4 class="modal-title">Forgot Password ?</h4>
                                </div>
                                <div class="modal-body">
                                    <p id="message" class="alert alert-info"></p>
                                    <p>Enter your mobile number to receive password.</p>
                                    <input id="mobilenumber" type="text" name="email" placeholder="Mobile number" autocomplete="off" class="form-control placeholder-no-fix">

                                </div>
                                <div class="modal-footer">
                                    <button data-dismiss="modal" class="btn btn-default" type="button">Cancel</button>
                                    <button id="submit-btn" class="btn btn-theme" type="button">Submit</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- modal -->
                </div>	

            </div>
        </div>

        <!-- js -->
        <script src="<spring:url value="/resources/assets_admin/js/jquery.js"/>"></script>
        <script src="<spring:url value="/resources/assets_admin/js/bootstrap.min.js"/>"></script>
        <script src="<spring:url value="/resources/assets_admin/js/jquery-ui-1.9.2.custom.min.js"/>"></script>
        <script src="<spring:url value="/resources/assets_admin/js/jquery.ui.touch-punch.min.js"/>"></script>
        <script class="include" type="text/javascript" src="<spring:url value="/resources/assets_admin/js/jquery.dcjqaccordion.2.7.js"/>"></script>
        <script src="<spring:url value="/resources/assets_admin/js/jquery.scrollTo.min.js"/>"></script>
        <script src="<spring:url value="/resources/assets_admin/js/jquery.nicescroll.js"/>" type="text/javascript"></script>
        <script src="<spring:url value="/resources/assets_admin/js/fileinput.min.js"/>" type="text/javascript"></script>


        <!--common script-->
        <script src="<spring:url value="/resources/assets_admin/js/common-scripts.js"/>"></script>
        <script src="<spring:url value="/resources/assets_admin/js/vendor/login.js"/>"></script>

        <!--script for this page-->

        <script>
            //custom select box

           

        </script>

        <script>
            $("#file-3").fileinput({
                showCaption: false,
                browseClass: "btn btn-primary btn-lg",
                fileType: "any"
            });
        </script>

        <!--BACKSTRETCH-->
        <script type="text/javascript" src="<spring:url value="/resources/assets_admin/js/jquery.backstretch.min.js"/>"></script>
        <script>
            $.backstretch("<spring:url value="/resources/assets_admin/images/login-bg.jpg"/>", {speed: 500});



            $(document).ready(function() {
                $("#message").hide();

                $(document).delegate("#forgotPasswordForm #submit-btn","click",function(e){
                    e.preventDefault();
                    var forgotForm =  $('#forgotPasswordForm');;
                    var mobile = forgotForm.find("#mobilenumber").val();
                    var messagebox = forgotForm.find("#message");
                    
                    $.ajax({
                        type: "POST",
                        url: "<%=request.getContextPath()%>/vendor/forgotPassword?mobile="+mobile,                        
                        cache: false,
                        success: function(data) {
                            if (data)
                            {
                               if(data.msg){
                                    messagebox.html(data.msg);
                                    messagebox.show();
                                }
                                
                            }
                        }
                    });
                });
                /*forgotForm.find("#submit-btn").click(function() {
                    alert(1);
                    var mobile = forgotForm.find("#mobilenumber").val();
                    var messagebox = forgotForm.find("#message");
                    $.ajax({
                        type: "POST",
                        url: "<%=request.getContextPath()%>/vendor/forgotPassword?mobile="+mobile,                        
                        cache: false,
                        success: function(data) {
                            if (data)
                            {
                               if(data.message){
                                    messagebox.html(data.message);
                                }
                                
                            }
                        }
                    });
                });*/
            });
        </script>

    </body>
</html>
