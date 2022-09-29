<%
    String message = pageContext.getException().getMessage();
    String exception = pageContext.getException().getClass().toString();
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Critical error</title>
    </head>
    <body>
        <h2>Exception occurred while processing the request</h2>
        <p>Type: <%= exception%></p>
        <p>Message: <%= message %></p>
        <p>Please inform our support service: mdudnyk.sps@gmail.com</p>
    </body>
</html>