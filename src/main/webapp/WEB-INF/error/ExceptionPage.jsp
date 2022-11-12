<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String message = pageContext.getException().getMessage();
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>PressReader | Error</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}css/error.css">
    </head>
    <body>
        <h1>Oops! Something went wrong</h1>
        <p>Error occurred while processing your request</p>
        <p>Message: <%=message%></p>
        <br>
        <p>Please inform our support service: mdudnyk.sps@gmail.com</p>
    </body>
</html>