<%@ page import="java.util.Arrays" %><%
    String message = pageContext.getException().getMessage();
    String stackTrace = Arrays.toString(pageContext.getException().getStackTrace());
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>PressReader | Error</title>
    </head>
    <body>
        <h2>Error occurred while processing the request</h2>
        <p>Message: <%= message %></p>
<%--        <p>Stack trace: <%= stackTrace%></p>--%>
        <br>
        <p>Please inform our support service: mdudnyk.sps@gmail.com</p>
    </body>
</html>