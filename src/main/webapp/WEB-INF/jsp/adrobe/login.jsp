<%-- 
    Document   : login
    Created on : Jul 26, 2015, 9:40:00 AM
    Author     : venkey.meesala
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<style>
    .error{
        color: red;
    }
   .input-forgot-email {
    border: 1px solid #ddd;
    height: 50px;
    line-height: 50px;
    margin: 0 0 5px;
    padding-left: 15px;
    width: 95%;
    border-radius: 50px;
    color: #666;
    font-size: 14px;
    }
    .button-forgot-email {
    background-color: #000;
    border: medium none;
    color: #fff;
    font-size: 16px;
    line-height: 40px;
    padding: 4px 20px;
    text-transform: uppercase;
    font-weight: 600;
    letter-spacing: 1px;
    margin-top: 15px;
    border-radius: 50px;

}
</style>
  <div class="breadcrumbs">
    <div class="container">
      <div class="row">
        <div class="col-xs-12">
          <ul>
            <li class="home"> <a title="Go to Home Page" href="index.html">Home</a><span>&raquo;</span></li>
            <li><strong>My Account</strong></li>
          </ul>
        </div>
      </div>
    </div>
  </div>
  <!-- Breadcrumbs End --> 
  
  <!-- Main Container -->
  <section class="main-container col1-layout">
    <div class="main container">
       
        <div class="page-content">
        <div class="account-login">
          <div class="box-authentication">
              <form id="loginForm" action="<spring:url value="/login.htm"/>" method="post" novalidate="" >
            <h4>Log in</h4>
            <p class="before-login-text"> Sign in to your account</p>
            <label for="userName"> Your email  <span class="required">*</span></label>
            <input id="userName" name="userName" type="text" class="form-control" required>
            <label for="password">Your password <span class="required">*</span></label>
            <input id="password" name="password" type="password" class="form-control" required>
            <p class="forgot-pass"><a href="javascript:void(0)" id="forgot_pass" >Lost your password?</a></p>
            <button type="submit" class="button"><i class="fa fa-lock"></i>&nbsp; <span>Login</span></button>
            <label class="inline" for="rememberme">
              <input type="checkbox" value="forever" id="rememberme" name="rememberme">
              Remember me </label>
             </form>
          </div>
         </div>
       </div>
       
    </div>
  </section>
   <div id="modal_forgot" class="modal fade" role="dialog" >
       <div class="modal-dialog" >
       <div class="modal-body">
           <button type="button" class="close myclose" data-dismiss="modal">×</button>
          <h2 class="modal-title">Forgot Password</h2>
          
            <label id="email_response" class="error"> </label>
          <form id="forgot_password" novalidate="novalidate">
            
            <div class="content-subscribe">
              <div class="form-subscribe-header">
                  <label for="forg_email"> </label>
              </div>
              <div class="input-box">
                <input type="text" class="input-forgot-email" title="Sign up for our newsletter" id="forg_email" name="email" placeholder="Enter your email address">
              </div>
              <div class="actions">
                <input  class="button-forgot-email" title="submit" type="submit" value="submit"/>        
              </div>
            </div>
          </form>
          
        </div>
      </div>
    </div>

            <script>
                $(document).ready(function(){
                     console.log("Sucess");
                    $('#forgot_pass').on('click',function (){
                        $("#forg_email").val('');
                        $('#modal_forgot').modal("show");
                    });
                    $('button.myclose').on('click',function(){
                        console.log("Sucess");
                        $('#modal_forgot').modal("hide");
                    });
                    
       $("#forgot_password").submit(function(e){
        e.preventDefault();
       $("#forgot_password").validate({
        rules: {
            email: {required: true, email: true}
        },
        messages: {
            email: "Please enter a valid email address"
        },
         submitHandler: function(form) {
             var formData=$("#forgot_password").serialize();
              $.ajax({
                   url: methodUrl.baseUrl+"forgotPassword",
                   method: 'POST',
                   data:formData,
                    success:function(data){
                      $('#email_response').html(data);
                   }
              });
              return;
         },
      invalidHandler: function(event, validator) {
        var errors = validator.numberOfInvalids();
      if (errors) {
       console.log("Errors"+errors); 
       }     
    }
   });        
  });
 });
 
            </script>           