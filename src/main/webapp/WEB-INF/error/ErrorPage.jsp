<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale.getShortNameId()}"/>
<fmt:setBundle basename="messages"/>

<!DOCTYPE html>
<head>
    <title>PressReader | Error</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/error.css">
    <meta http-equiv="refresh" content="6; url=<%=request.getHeader("referer")%>">
</head>
<body>
    <h1><fmt:message key="error.oops"/></h1>
    <p><fmt:message key="error.will_redirect"/>
        <span class="seconds" id="demo">5</span><fmt:message key="error.sec"/>
    </p>
    <script>
        let periodInSeconds = 4;
        let x = setInterval(function() {
            document.getElementById("demo").innerHTML = periodInSeconds.toString();
            periodInSeconds--;
            if (periodInSeconds < 0) {
                clearInterval(x);
                document.getElementById("demo").innerHTML = "0";
            }
        }, 1000);
    </script>
</body>