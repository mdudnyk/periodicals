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
            <img class="img_container_title" id="title_img"
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
            <p>Publishing frequency: <span>1 per month</span></p>
            <p class="text_block_price">Price for one copy:
                <span id="price_per_one">${MoneyFormatter.toHumanReadable(requestScope.periodical.getPrice())} uah</span>
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
                <div class="month_selector">
                    <span class="month_selector_year" style="margin-right: 10px;">2022</span>
                    <form action="#" class="month_form" style="display: flex;" year="2022">
                        <p class="checkbox_block">
                            Jan
                            <label class="checkbox_label">
                                <input onchange="countTotalPrice()" type="checkbox" class="filled-in checkbox-green" disabled/>
                                <span></span>
                            </label>
                        </p>
                        <p class="checkbox_block">
                            Feb
                            <label class="checkbox_label">
                                <input onchange="countTotalPrice()" type="checkbox" class="filled-in checkbox-green" disabled/>
                                <span></span>
                            </label>
                        </p>
                        <p class="checkbox_block">
                            Mar
                            <label class="checkbox_label">
                                <input onchange="countTotalPrice()" type="checkbox" class="filled-in checkbox-green" disabled/>
                                <span></span>
                            </label>
                        </p>
                        <p class="checkbox_block">
                            Apr
                            <label class="checkbox_label">
                                <input onchange="countTotalPrice()" type="checkbox" class="filled-in checkbox-green" disabled/>
                                <span></span>
                            </label>
                        </p>
                        <p class="checkbox_block">
                            May
                            <label class="checkbox_label">
                                <input onchange="countTotalPrice()" type="checkbox" class="filled-in checkbox-green" disabled/>
                                <span></span>
                            </label>
                        </p>
                        <p class="checkbox_block">
                            Jun
                            <label class="checkbox_label">
                                <input onchange="countTotalPrice()" type="checkbox" class="filled-in checkbox-green" disabled/>
                                <span></span>
                            </label>
                        </p>
                        <p class="checkbox_block">
                            Jul
                            <label class="checkbox_label">
                                <input onchange="countTotalPrice()" type="checkbox" class="filled-in checkbox-green" disabled/>
                                <span></span>
                            </label>
                        </p>
                        <p class="checkbox_block">
                            Aug
                            <label class="checkbox_label">
                                <input onchange="countTotalPrice()" type="checkbox" class="filled-in checkbox-green" disabled/>
                                <span></span>
                            </label>
                        </p>
                        <p class="checkbox_block">
                            Sep
                            <label class="checkbox_label">
                                <input onchange="countTotalPrice()" type="checkbox" class="filled-in checkbox-green" disabled/>
                                <span></span>
                            </label>
                        </p>
                        <p class="checkbox_block">
                            Oct
                            <label class="checkbox_label">
                                <input onchange="countTotalPrice()" type="checkbox" class="filled-in checkbox-green" disabled/>
                                <span></span>
                            </label>
                        </p>
                        <p class="checkbox_block">
                            Nov
                            <label class="checkbox_label">
                                <input onchange="countTotalPrice()" type="checkbox" class="filled-in checkbox-green"/>
                                <span></span>
                            </label>
                        </p>
                        <p class="checkbox_block">
                            Dec
                            <label class="checkbox_label">
                                <input onchange="countTotalPrice()" type="checkbox" class="filled-in checkbox-green"/>
                                <span></span>
                            </label>
                        </p>
                    </form>
                </div>
                <div class="month_selector">
                    <span class="month_selector_year" style="margin-right: 10px;">2023</span>
                    <form action="#" class="month_form" style="display: flex;" year="2023">
                        <p class="checkbox_block">
                            Jan
                            <label class="checkbox_label">
                                <input onchange="countTotalPrice()" type="checkbox" class="filled-in checkbox-green"/>
                                <span></span>
                            </label>
                        </p>
                        <p class="checkbox_block">
                            Feb
                            <label class="checkbox_label">
                                <input onchange="countTotalPrice()" type="checkbox" class="filled-in checkbox-green"/>
                                <span></span>
                            </label>
                        </p>
                        <p class="checkbox_block">
                            Mar
                            <label class="checkbox_label">
                                <input onchange="countTotalPrice()" type="checkbox" class="filled-in checkbox-green"/>
                                <span></span>
                            </label>
                        </p>
                        <p class="checkbox_block">
                            Apr
                            <label class="checkbox_label">
                                <input onchange="countTotalPrice()" type="checkbox" class="filled-in checkbox-green"/>
                                <span></span>
                            </label>
                        </p>
                        <p class="checkbox_block">
                            May
                            <label class="checkbox_label">
                                <input onchange="countTotalPrice()" type="checkbox" class="filled-in checkbox-green"/>
                                <span></span>
                            </label>
                        </p>
                        <p class="checkbox_block">
                            Jun
                            <label class="checkbox_label">
                                <input onchange="countTotalPrice()" type="checkbox" class="filled-in checkbox-green"/>
                                <span></span>
                            </label>
                        </p>
                        <p class="checkbox_block">
                            Jul
                            <label class="checkbox_label">
                                <input onchange="countTotalPrice()" type="checkbox" class="filled-in checkbox-green"/>
                                <span></span>
                            </label>
                        </p>
                        <p class="checkbox_block">
                            Aug
                            <label class="checkbox_label">
                                <input onchange="countTotalPrice()" type="checkbox" class="filled-in checkbox-green"/>
                                <span></span>
                            </label>
                        </p>
                        <p class="checkbox_block">
                            Sep
                            <label class="checkbox_label">
                                <input onchange="countTotalPrice()" type="checkbox" class="filled-in checkbox-green"/>
                                <span></span>
                            </label>
                        </p>
                        <p class="checkbox_block">
                            Oct
                            <label class="checkbox_label">
                                <input onchange="countTotalPrice()" type="checkbox" class="filled-in checkbox-green"/>
                                <span></span>
                            </label>
                        </p>
                        <p class="checkbox_block">
                            Nov
                            <label class="checkbox_label">
                                <input onchange="countTotalPrice()" type="checkbox" class="filled-in checkbox-green"/>
                                <span></span>
                            </label>
                        </p>
                        <p class="checkbox_block">
                            Dec
                            <label class="checkbox_label">
                                <input onchange="countTotalPrice()" type="checkbox" class="filled-in checkbox-green"/>
                                <span></span>
                            </label>
                        </p>
                    </form>
                </div>
            </div>
        </div>
        <div class="right_block">
            <p class="total_price_text">Totat price</p>
            <p class="total_price_value"><span id="total_price_field">0</span> uah</p>
            <button class="subscribe_btn" onclick="tryToSubscribe(1)">Subscribe</button>
            <!-- <a href="controller?cmd=EDIT_PERIODICAL_PAGE&id=1">
                <button class="subscribe_btn">Edit</button>
            </a> -->
        </div>
    </div>
</div>



</body>

<script src="${pageContext.request.contextPath}/js/show_periodical.js"></script>
</body>
</html>