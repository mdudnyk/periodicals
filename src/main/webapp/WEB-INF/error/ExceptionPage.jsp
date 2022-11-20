<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale.getShortNameId()}"/>
<fmt:setBundle basename="messages"/>

<%
    String message = pageContext.getException().getMessage();
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>PressReader | Error</title>
        <link rel="stylesheet" href="css/error.css">
    </head>
    <body>
        <h1><fmt:message key="error.oops"/></h1>
        <p><fmt:message key="error.error"/></p>
        <p><fmt:message key="error.message"/><%=message%></p>
        <br>
        <p><fmt:message key="error.inform_support"/></p>
    </body>
</html>