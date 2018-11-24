
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="payment" value="${sessionScope.CART.payment}"/>
<c:set var="detailsMap" value="${payment.details}"/> 
<html>
    <head>
<script>
    function submitPayuForm() {
      var payuForm = document.forms.payuForm;
      payuForm.submit();
    }
</script>
</head>
<body onload="submitPayuForm();">
    <form action="${payment.redirectUrl}" method="post" name="payuForm">
        <c:forEach var="item" items="${detailsMap}">
        <input type="hidden" name="${item.key}" value="${item.value}" />
        </c:forEach>
        <%--<input type="submit" value="Submit" />--%>
    </form>     
 </body>
</html>