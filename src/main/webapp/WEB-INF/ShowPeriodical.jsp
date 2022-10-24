<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ct" tagdir="/WEB-INF/tags" %>

<html lang="${sessionScope.locale.getShortNameId()}">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>PressReader | Periodical</title>
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/img/tab-icon.ico">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/check_box.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/show_periodical.css">
</head>
<body>
<%@ include file="fragment/Header.jspf" %>
<div class="body_container">
    <div class="body_top_block">
        <div class="top_block_img_container">
            <img class="img_container_title" id="title_img" alt=""
                 src="<ct:imageEncoderBase64 image_name="${requestScope.periodical.getTitleImgLink()}"/>">
        </div>
        <div class="top_block_text_block">
            <h1 class="text_block_title">${requestScope.periodical.getTitle()}</h1>
            <c:set var="topicName" scope="request" value="${requestScope.topicTranslate.getName()}"/>
            <p>Topic:
                <span>${topicName == null ? 'no topic' : topicName}</span>
            </p>
            <p>Origin country:
                <span>${requestScope.periodical.getTranslation().values().iterator().next().getCountry()}</span>
            </p>
            <p>Language:
                <span>${requestScope.periodical.getTranslation().values().iterator().next().getLanguage()}</span>
            </p>
            <p>Publishing frequency:
                <span>
                    ${requestScope.periodical.getFrequency().get("amount")}
                    <fmt:message key="new_periodical.per"/>
                        <fmt:message key="new_periodical.frequency_${requestScope.periodical.getFrequency().get('period')}"/>
                </span>
            </p>
            <p class="text_block_price">Price for one copy:
                <span id="price_per_one">
                    ${MoneyFormatter.toHumanReadable(requestScope.periodical.getPrice())}
                    ${sessionScope.locale.getCurrency()}</span>
            </p>
            <p>Description:
                <br>
                <span class="description_text">
                    ${requestScope.periodical.getTranslation().values().iterator().next().getDescription()}
                </span>
            </p>
        </div>
    </div>
    <div class="alert_block success" id="alert_block_success">
        <span>You have been successfully subscribed!</span>
    </div>
    <div class="alert_block warning" id="alert_block_warning">
        <span id="try_later">Oops! Something went wrong, try again later</span>
        <span id="only_registered">Only registered users can subscribe! Please sign in</span>
        <span id="select_month">Please, select months before you subscribing</span>
        <span id="top_up_balance">You don't have enough money! Top up you balance</span>
    </div>
    <div class="body_bottom_block">
        <!-- <div class="not_valid_subscription">
            <p style="width: 100%;">The subscription for this periodical is no longer valid</p>
            <a href="controller?cmd=EDIT_PERIODICAL_PAGE&id=1">
                <button class="edit_btn_not_valid">Edit</button>
            </a>
        </div> -->
        <div class="left_block">
            <p class="select_month_text">Select months in which you would like to receive this periodical</p>
            <div class="month_selector_block">
                <ct:monthSelectorSubscribe calendar="${requestScope.periodical.getReleaseCalendar()}"/>
            </div>
        </div>
        <div class="right_block">
            <p class="total_price_text">Total price</p>
            <p class="total_price_value"><span id="total_price_field">0</span> ${sessionScope.locale.getCurrency()}</p>
            <button class="subscribe_btn" onclick="tryToSubscribe(1)">Subscribe</button>
            <!-- <a href="controller?cmd=EDIT_PERIODICAL_PAGE&id=1">
                <button class="subscribe_btn">Edit</button>
            </a> -->
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/show_periodical.js"></script>
</body>
</html>