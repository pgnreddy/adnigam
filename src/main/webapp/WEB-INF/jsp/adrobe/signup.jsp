<%-- 
    Document   : signup
    Created on : Aug 16, 2017, 3:37:09 PM
    Author     : venkat
--%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/custom" %> 


<!DOCTYPE html>
<style>
    .error{
        color: red;
    }
 </style>
  <!-- Breadcrumbs -->
  <div class="breadcrumbs">
    <div class="container">
      <div class="row">
        <div class="col-xs-12">
          <ul>
              <li class="home"> <a title="Go to Home Page" href="<spring:url value="/index.html"/>">Home</a><span>&raquo;</span></li>
            <li><strong>Create Account</strong></li>
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
                <c:if test="${not empty message}">
                 <div class="alert alert-danger" style=" padding:0px ">
                <strong>Warning!::</strong>${message}.!
                 </div>
              </c:if>
              <form id="createaccount" action="<spring:url value="/signup.htm"/>" method="post">
            <h4>Sign up </h4>
            <p class="before-login-text">Welcome back! Sign in to your account</p>
            <label for="firstName">First Name <span class="required">*</span></label>
            <input id="firstName" type="text"  name="firstname" class="form-control"> 
            <label for="firstName">Last Name <span class="required">*</span></label>
            <input id="lastName" type="text" name="lastname" class="form-control">
            <label for="email_register">Email address <span class="required">*</span></label>
            <input id="email_register" name="emailAddress"  type="text" class="form-control"> 
            <label for="telephone_number">Telephone Number <span class="required">*</span></label>
            <input id="telephone_number"  name="telephone" type="tel" class="form-control">
            <label for="password">Password <span class="required">*</span></label>
            <input id="password"  name="password" type="password" class="form-control">
            <label for="confirm_register">Confirm Password <span class="required">*</span></label>
            <input id="confirm_register"  name="confirm" type="password" class="form-control"> 
            <button class="button"><i class="fa fa-lock"></i>&nbsp; <span>Sign up</span></button>
            <label class="inline" for="rememberme">
              <input type="checkbox" value="forever" id="rememberme" name="rememberme">
              Remember me </label>
              </form>
          </div>
        </div>
      </div>
    </div>
  </section>
            
  <!-- Main Container End --> 
 