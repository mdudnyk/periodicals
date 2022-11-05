<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="${sessionScope.locale.getShortNameId()}">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>PressReader | Top Up</title>
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/img/tab-icon.ico">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/top_up.css">
</head>
<body>
<%@ include file="fragment/Header.jspf" %>
<div class="content">
    <div class="content_main">
        <div class="content_main_top_text"><fmt:message key="topup.header"/></div>
        <div class="periodical_info_container">
            <div class="periodical_info_block">
                <div class="info_block_header"><fmt:message key="topup.select_amount"/></div>
                <a href="${pageContext.request.contextPath}/controller?cmd=TOP_UP_BALANCE&amount=100">
                    <button class="top_up_button">100 <fmt:message key="currency_name"/></button>
                </a>
                <a href="${pageContext.request.contextPath}/controller?cmd=TOP_UP_BALANCE&amount=200">
                    <button class="top_up_button">200 <fmt:message key="currency_name"/></button>
                </a>
                <a href="${pageContext.request.contextPath}/controller?cmd=TOP_UP_BALANCE&amount=500">
                    <button class="top_up_button">500 <fmt:message key="currency_name"/></button>
                </a>
                <a href="${pageContext.request.contextPath}/controller?cmd=TOP_UP_BALANCE&amount=1000">
                    <button class="top_up_button">1000 <fmt:message key="currency_name"/></button>
                </a>
            </div>
            <div class="periodical_info_block">
                <div class="info_block_header"><fmt:message key="topup.set_amount"/>
                    <fmt:message key="currency_name"/></div>
                <form action="${pageContext.request.contextPath}/controller">
                    <input type="hidden" name="cmd" value="TOP_UP_BALANCE">
                    <input type="text" name="amount" placeholder="109,99" autocomplete="off">
                    <button class="top_up_button" type="submit"><fmt:message key="topup.topup"/></button>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>