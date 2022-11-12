<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ct" tagdir="/WEB-INF/tags" %>
<%@ page import="com.periodicals.entity.enums.UserRole" %>

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
            <p><fmt:message key="new_periodical.topic"/>:
                <span>${topicName == null ? 'no topic' : topicName}</span>
            </p>
            <p><fmt:message key="new_periodical.publishing_country"/>:
                <span>${requestScope.periodical.getTranslation().values().iterator().next().getCountry()}</span>
            </p>
            <p><fmt:message key="new_periodical.publishing_language"/>:
                <span>${requestScope.periodical.getTranslation().values().iterator().next().getLanguage()}</span>
            </p>
            <p><fmt:message key="new_periodical.frequency"/>:
                <span>
                    ${requestScope.periodical.getFrequency().get("amount")}
                    <fmt:message key="new_periodical.per"/>
                        <fmt:message key="new_periodical.frequency_${requestScope.periodical.getFrequency().get('period')}"/>
                </span>
            </p>
            <p class="text_block_price">
                <c:if test="${requestScope.isLessThanMonth == false}">
                    <fmt:message key="show_periodical.per_copy"/>:
                </c:if>
                <c:if test="${requestScope.isLessThanMonth == true}">
                    <fmt:message key="show_periodical.per_month"/>:
                </c:if>
                <span id="price_per_one">
                    ${requestScope.price}
                    ${sessionScope.locale.getCurrency()}</span>
            </p>
            <p><fmt:message key="new_periodical.publishing_description"/>:
                <br>
                <span class="description_text">
                    ${requestScope.periodical.getTranslation().values().iterator().next().getDescription()}
                </span>
            </p>
        </div>
    </div>
    <div class="alert_block success" id="alert_block_success">
        <span><fmt:message key="show_periodical.success"/></span>
    </div>
    <div class="alert_block warning" id="alert_block_warning">
        <span id="try_later"><fmt:message key="new_periodical.alert.try_later"/></span>
        <span id="only_registered"><fmt:message key="show_periodical.only_registered"/></span>
        <span id="select_month"><fmt:message key="show_periodical.please_select_months"/></span>
        <span id="top_up_balance"><fmt:message key="show_periodical.top_up"/></span>
    </div>
    <div class="body_bottom_block">
        <c:if test="${requestScope.periodical.getReleaseCalendar().size() < 1}">
            <div class="not_valid_subscription">
                <p style="width: 100%;"><fmt:message key="show_periodical.no_longer_valid"/></p>
                <c:if test="${sessionScope.user.getRole() == UserRole.ADMIN}">
                    <a href="controller?cmd=EDIT_PERIODICAL_PAGE&id=${requestScope.periodical.getId()}">
                        <button class="edit_btn_not_valid"><fmt:message key="edit_periodical.edit_periodical"/></button>
                    </a>
                </c:if>
            </div>
        </c:if>
        <c:if test="${requestScope.periodical.getReleaseCalendar().size() > 0}">
            <div class="left_block">
                <p class="select_month_text"><fmt:message key="show_periodical.select_month"/></p>
                <div class="month_selector_block">
                    <ct:monthSelectorSubscribe calendar="${requestScope.periodical.getReleaseCalendar()}"/>
                </div>
            </div>
            <div class="right_block">
                <p class="total_price_text"><fmt:message key="show_periodical.total_price"/></p>
                <p class="total_price_value"><span id="total_price_field">0</span> ${sessionScope.locale.getCurrency()}</p>
                <c:if test="${sessionScope.user.getRole() != UserRole.ADMIN}">
                    <button class="subscribe_btn"
                            onclick="tryToSubscribe(${requestScope.periodical.getId()})">
                        <fmt:message key="show_periodical.subscribe"/>
                    </button>
                </c:if>
                <c:if test="${sessionScope.user.getRole() == UserRole.ADMIN}">
                    <a href="controller?cmd=EDIT_PERIODICAL_PAGE&id=${requestScope.periodical.getId()}">
                        <button class="subscribe_btn"><fmt:message key="edit_periodical.edit_periodical"/></button>
                    </a>
                </c:if>
            </div>
        </c:if>
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/show_periodical.js"></script>
</body>
</html>