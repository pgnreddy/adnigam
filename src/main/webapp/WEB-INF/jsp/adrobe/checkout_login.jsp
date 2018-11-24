<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/custom" %> 

<!-- page wapper-->
<div class="columns-container">
    <div class="container">
        <!-- breadcrumb -->
        <div class="breadcrumb clearfix">
            <a class="home" href="<spring:url value="/"/>" title="Return to Home">Home</a>
            <span class="navigation-pipe">&nbsp;</span>
            <a href="<spring:url value="/cart/"/>"><span class="navigation_page">Your shopping cart</span></a>
        </div>
        <!-- ./breadcrumb -->
        <!-- page heading-->
        <h2 class="page-heading no-line">
            <span class="page-heading-title2">Cart Signin</span>
        </h2>
        <!-- ../page heading-->
        <div class="page-content page-order">
            <ul class="step">                
                <li class="current-step"><span>01. Sign in</span></li>
                <li><span>02. Address</span></li>
                <li><span>03. Summary</span></li>
                <li><span>04. Payment</span></li>
            </ul>
            <%--<div class="heading-counter warning">Your shopping cart contains:
                <span>1 Product</span>
            </div>--%>
            <br/>
            <div class="col-sm-6 col-md-6 cart_sign">
                <div class="box-authentication" style="height:auto !important; margin-left:-18px !important;">
                    <h3>Already registered?</h3>
                    <a id="loginErrors_s" class="alert "> </a>
                    <form id="loginForm_s">                    
                        <label for="email_login">Email address</label>
                        <input id="email_login" type="text" name="userName" class="form-control" >
                        <label for="password_login">Password</label>
                        <input id="password_login" type="password" name="password" class="form-control" >

                        <button id="loginsubmit_s"  type="submit" class="button"><i class="fa fa-lock"></i> Login</button>
                         
                      
                    </form>
                            <br/>    <br/>  
                        <div><a href="<spring:url value="/signup.htm"/>"> create account</a></div>
                    
                       
                        
                       
                </div>

<!--                <div class="box-authentication" style="height:auto !important; margin-left:-18px !important;" id="adrobeForgotPass">
                    <h3>Forgot password?</h3>
                    <div id="message" class="alert alert-danger"></div>
                    <form id="adrobeForgotPassForm" class="form-horizontal" action="${dataSecureURL}forgotPassword" target="new-loginsignup-iframe">
                        <label for="email_register">Email address</label>
                        <input id="email_register" name="email" type="text" class="form-control" >                    

                        <button class="button"><i class="fa fa-user"></i> Send</button>
                    </form>
                </div
            </div>>-->
<!--            <div class="col-sm-6" id="adrobeSignup">
                <div class="box-authentication" style="height:auto !important; margin-left:-18px !important;">
                    <h3>Create an account</h3>
                    <div id="message" class="alert alert-danger"></div>                
                    <form class="form-horizontal" action="${dataSecureURL}signup" target="new-loginsignup-iframe" id="createaccount">

                        <label for="firstName">First name</label>
                        <input id="firstName" type="text" placeholder="First name" name="firstname" class="form-control" > 
                        <label for="lastName">Last name</label>
                        <input id="lastName" type="text"  placeholder="Last name" name="lastname" class="form-control" >


                        <label for="email_register">Email address</label>
                        <input id="email_register" name="emailAddress" placeholder="example@example.com" type="text" class="form-control" >

                        <label for="telephone_number">Telephone Number</label>
                        <input id="telephone_number" placeholder="Mobile" name="telephone" type="tel" class="form-control" >

                        <label for="password_register">Password</label>
                        <input id="password" placeholder="Enter password" name="password" type="password" class="form-control" >

                        <label for="confirm_register">Confirm Password</label>
                        <input id="confirm_register" placeholder="Confirm password" name="confirm" type="password" class="form-control" >                   



                        <button class="button"><i class="fa fa-user"></i> Create </button>
                    </form>-->
                </div>
            </div>
            <div class="cart_navigation">
                <a class="prev-btn" href="<spring:url value="/"/>">Continue shopping</a>
                <%--<a class="next-btn" href="cart_address.html">Proceed to checkout</a>--%>
            </div>
        </div>
    </div>
</div>
</div>
<!-- ./page wapper-->
<!--<iframe style="display:none" class="hidden" width="0" height="0" id="new-login-signup-iframe" name="new-loginsignup-iframe" ></iframe>--> 
<!--<script type="text/javascript" src="<spring:url value="/resources/assets_admin/lib/jquery/jquery-1.11.2.min.js"/>"></script>-->    
<!--jquery validater-->
<script type="text/javascript" src="<spring:url value="/resources/js/jquery.validate.min.js"/>"></script>
<script>
  //  var acceptDomain = '${applicationDomain}';


    $(document).ready(function() {
              //var loginForm = $("#loginForm");
          $("#loginForm_s").validate({
         debug: true,
                  rules: {
                userName: {required: true,email:true},
                password: {required: true}
                
            },
            messages: {
                userName: "Please enter email",
                password: "Please enter password"
            },
            errorPlacement: function (error, element) {
                if(element.attr("name")=="userName"){
                error.insertBefore($("#email_login"));
                }
               if(element.attr('name')=='password'){
                   error.insertBefore($("#password_login"));
               }
            }
            
                
            });
  
       $("#loginsubmit_s").on('click',function() {
           // e.preventDefault();
            console.log("success test");
             
             $.ajax({
                url: '<spring:url value="/login_api"/>',
                dataType: 'json',
                async:false,
                type: 'GET',
                data: $("#loginForm_s").serialize(),
                success: function (data) {
                      console.log("success call back not valid"+JSON.stringify(data));
                      if(data.status==="0000"){
                          window.location.reload();
                      }else{
                          $("#loginErrors_s").html(data.msg);
                          
                      }
                }
            });
             
  
        });



});


        //forgot password

   

</script>
<!-- Google Code for conversion rate Conversion Page -->
<script type="text/javascript">
/* <![CDATA[ */
var google_conversion_id = 926847200;
var google_conversion_language = "en";
var google_conversion_format = "3";
var google_conversion_color = "ffffff";
var google_conversion_label = "KtLzCJnF6WcQ4KH6uQM";
var google_remarketing_only = false;
/* ]]> */
</script>
<script type="text/javascript" src="//www.googleadservices.com/pagead/conversion.js">
</script>
<noscript>
<div style="display:inline;">
<img height="1" width="1" style="border-style:none;" alt="" src="//www.googleadservices.com/pagead/conversion/926847200/?label=KtLzCJnF6WcQ4KH6uQM&amp;guid=ON&amp;script=0"/>
</div>
</noscript>