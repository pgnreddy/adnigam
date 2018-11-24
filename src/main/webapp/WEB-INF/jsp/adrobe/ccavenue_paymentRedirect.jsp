<%-- 
    Document   : ccavenue_paymentRedirect
    Created on : Apr 27, 2016, 10:58:20 AM
    Author     : rajeshk
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>

<c:set var="payment" value="${sessionScope.CART.payment}"/>

<html>
    <head>
        <script>
            function submitCCAvForm() {
                var ccAvForm = document.forms.ccAvForm;
                ccAvForm.submit();
            }
        </script>
    </head>
    <body onload="submitCCAvForm();">
        
        <form method="post" name="ccAvForm" action="${sessionScope.CART.payment.redirectUrl}"> 
		<input type="hidden" id="encRequest" name="encRequest" value="${sessionScope.CART.payment.encRequest}">
		<input type="hidden" name="access_code" id="access_code" value="${sessionScope.CART.payment.accessCode}">
	</form>
        
        
    </body>
</html>
