<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/custom" %> 

<custom:setting var="applicationBaseURL" key="APPLICATION_BASE_URL" /> 

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
              <form id="loginForm" action="<spring:url value="/login.htm"/>" method="post" novalidate="" >
            <h4>Log in</h4>
            <p class="before-login-text">Welcome back! Sign in to your account</p>
            <label for="userName"> Your email  <span class="required">*</span></label>
            <input id="userName" name="userName" type="text" class="form-control" required>
            <label for="password">Your password <span class="required">*</span></label>
            <input id="password" name="password" type="password" class="form-control" required>
            <p class="forgot-pass"><a href="#">Lost your password?</a></p>
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
       