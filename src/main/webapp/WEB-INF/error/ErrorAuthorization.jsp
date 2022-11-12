<!DOCTYPE html>
<head>
    <title>PressReader | Access Denied</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/error.css">
    <meta http-equiv="refresh" content="6; url=<%=request.getHeader("referer")%>">
</head>
<body>
<h1>Access is denied! You don`t have rights to visit this resource</h1>
<p>You will be redirected to the previous page in
    <span class="seconds" id="demo">5</span>s
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