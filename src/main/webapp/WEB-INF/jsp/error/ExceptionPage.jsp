<%
    String message = pageContext.getException().getMessage();
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Critical error</title>
    </head>
    <body>
        <h2>Error occurred while processing the request</h2>
        <p>Message: <%= message %></p>
        <p>Please inform our support service: mdudnyk.sps@gmail.com</p>
    </body>
</html>