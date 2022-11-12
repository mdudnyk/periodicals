<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ct" tagdir="/WEB-INF/tags" %>

<html lang="${sessionScope.locale.getShortNameId()}">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>PressReader | Details</title>
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/img/tab-icon.ico">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/check_box.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/subscription_details.css">
</head>
<body>
<%@ include file="fragment/Header.jspf" %>
    <div class="body_container">
        <div class="content_topper">
        <a href="controller?cmd=MY_SUBSCRIPTIONS" class="parent_page_link">
            <fmt:message key="subscription_details.my_subscriptions"/>
        </a>
        <i class="small material-icons" style="margin: 0 10px; opacity: 70%;">navigate_next</i>
        <a style="color: #27c18a; font-weight: 500;">
            <fmt:message key="subscription_details.details"/>
        </a>
    </div>
        <div class="body_top_block_wrapper">
        <div class="body_top_block">
            <div class="top_block_img_container">
                <img class="img_container_title" id="title_img"
                     src="<ct:imageEncoderBase64 image_name="${requestScope.subscriptionDetails.getTitleImgLink()}"/>">
            </div>
            <div class="top_block_text_block">
                <h1 class="text_block_title">
                    ${requestScope.subscriptionDetails.getPeriodicalTitle()}
                </h1>
                <p><fmt:message key="new_periodical.topic"/>:
                    <c:if test="${requestScope.subscriptionDetails.getTopicName() != null}">
                        <span>${requestScope.subscriptionDetails.getTopicName()}</span>
                    </c:if>
                    <c:if test="${requestScope.subscriptionDetails.getTopicName() == null}">
                        <span><fmt:message key="subscription_details.no_info"/></span>
                    </c:if>
                </p>
                <p><fmt:message key="new_periodical.publishing_country"/>:
                    <c:if test="${requestScope.subscriptionDetails.getOriginCountry() != null}">
                        <span>${requestScope.subscriptionDetails.getOriginCountry()}</span>
                    </c:if>
                    <c:if test="${requestScope.subscriptionDetails.getOriginCountry() == null}">
                        <span><fmt:message key="subscription_details.no_info"/></span>
                    </c:if>
                </p>
                <p><fmt:message key="new_periodical.publishing_language"/>:
                    <c:if test="${requestScope.subscriptionDetails.getPublishingLanguage() != null}">
                        <span>${requestScope.subscriptionDetails.getPublishingLanguage()}</span>
                    </c:if>
                    <c:if test="${requestScope.subscriptionDetails.getPublishingLanguage() == null}">
                        <span><fmt:message key="subscription_details.no_info"/></span>
                    </c:if>
                </p>
                <p style="padding-bottom: 20px; border-bottom: 1px solid rgba(0, 0, 0, 0.1);"><fmt:message key="new_periodical.frequency"/>:
                    <c:if test="${requestScope.subscriptionDetails.getPublishingFrequency() != null}">
                        <span>
                            ${requestScope.subscriptionDetails
                                    .getPublishingFrequency()
                                    .get('amount')}
                            <fmt:message key="new_periodical.per"/>
                            <fmt:message key="new_periodical.frequency_${requestScope
                                    .subscriptionDetails
                                    .getPublishingFrequency()
                                    .get('period')}"/>
                        </span>
                    </c:if>
                    <c:if test="${requestScope.subscriptionDetails.getPublishingLanguage() == null}">
                        <span><fmt:message key="subscription_details.no_info"/></span>
                    </c:if>
                </p>
                <p><fmt:message key="subscription_details.calendar"/>:</p>
                <div class="calendar_block">
                    <div class="month_selector">
                    <span class="month_selector_year" style="margin-right: 10px;">2022</span>
                    <form action="#" class="month_form" style="display: flex;" year="2022">
                        <p class="checkbox_block">
                            Jan
                            <label class="checkbox_label">
                                <input type="checkbox" class="filled-in checkbox-green" onclick="return false" checked disabled/>
                                <span></span>
                            </label>
                        </p>
                        <p class="checkbox_block">
                            Feb
                            <label class="checkbox_label">
                                <input type="checkbox" class="filled-in checkbox-green" onclick="return false" disabled/>
                                <span></span>
                            </label>
                        </p>
                        <p class="checkbox_block">
                            Mar
                            <label class="checkbox_label">
                                <input type="checkbox" class="filled-in checkbox-green" onclick="return false" checked disabled/>
                                <span></span>
                            </label>
                        </p>
                        <p class="checkbox_block">
                            Apr
                            <label class="checkbox_label">
                                <input type="checkbox" class="filled-in checkbox-green" onclick="return false" disabled/>
                                <span></span>
                            </label>
                        </p>
                        <p class="checkbox_block">
                            May
                            <label class="checkbox_label">
                                <input type="checkbox" class="filled-in checkbox-green" onclick="return false" checked disabled/>
                                <span></span>
                            </label>
                        </p>
                        <p class="checkbox_block">
                            Jun
                            <label class="checkbox_label">
                                <input type="checkbox" class="filled-in checkbox-green" onclick="return false" disabled/>
                                <span></span>
                            </label>
                        </p>
                        <p class="checkbox_block">
                            Jul
                            <label class="checkbox_label">
                                <input type="checkbox" class="filled-in checkbox-green" onclick="return false" checked disabled/>
                                <span></span>
                            </label>
                        </p>
                        <p class="checkbox_block">
                            Aug
                            <label class="checkbox_label">
                                <input type="checkbox" class="filled-in checkbox-green" onclick="return false" disabled/>
                                <span></span>
                            </label>
                        </p>
                        <p class="checkbox_block">
                            Sep
                            <label class="checkbox_label">
                                <input type="checkbox" class="filled-in checkbox-green" onclick="return false" checked disabled/>
                                <span></span>
                            </label>
                        </p>
                        <p class="checkbox_block">
                            Oct
                            <label class="checkbox_label">
                                <input type="checkbox" class="filled-in checkbox-green" onclick="return false" disabled/>
                                <span></span>
                            </label>
                        </p>
                        <p class="checkbox_block">
                            Nov
                            <label class="checkbox_label">
                                <input type="checkbox" class="filled-in checkbox-green" onclick="return false" checked disabled/>
                                <span></span>
                            </label>
                        </p>
                        <p class="checkbox_block">
                            Dec
                            <label class="checkbox_label">
                                <input type="checkbox" class="filled-in checkbox-green" onclick="return false" disabled/>
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
                                <input type="checkbox" class="filled-in checkbox-green" onclick="return false" checked/>
                                <span></span>
                            </label>
                        </p>
                        <p class="checkbox_block">
                            Feb
                            <label class="checkbox_label">
                                <input type="checkbox" class="filled-in checkbox-green" onclick="return false"/>
                                <span></span>
                            </label>
                        </p>
                        <p class="checkbox_block">
                            Mar
                            <label class="checkbox_label">
                                <input type="checkbox" class="filled-in checkbox-green" onclick="return false" checked/>
                                <span></span>
                            </label>
                        </p>
                        <p class="checkbox_block">
                            Apr
                            <label class="checkbox_label">
                                <input type="checkbox" class="filled-in checkbox-green" onclick="return false"/>
                                <span></span>
                            </label>
                        </p>
                        <p class="checkbox_block">
                            May
                            <label class="checkbox_label">
                                <input type="checkbox" class="filled-in checkbox-green" onclick="return false" checked/>
                                <span></span>
                            </label>
                        </p>
                        <p class="checkbox_block">
                            Jun
                            <label class="checkbox_label">
                                <input type="checkbox" class="filled-in checkbox-green" onclick="return false"/>
                                <span></span>
                            </label>
                        </p>
                        <p class="checkbox_block">
                            Jul
                            <label class="checkbox_label">
                                <input type="checkbox" class="filled-in checkbox-green" onclick="return false" checked/>
                                <span></span>
                            </label>
                        </p>
                        <p class="checkbox_block">
                            Aug
                            <label class="checkbox_label">
                                <input type="checkbox" class="filled-in checkbox-green" onclick="return false"/>
                                <span></span>
                            </label>
                        </p>
                        <p class="checkbox_block">
                            Sep
                            <label class="checkbox_label">
                                <input type="checkbox" class="filled-in checkbox-green" onclick="return false" checked/>
                                <span></span>
                            </label>
                        </p>
                        <p class="checkbox_block">
                            Oct
                            <label class="checkbox_label">
                                <input type="checkbox" class="filled-in checkbox-green" onclick="return false"/>
                                <span></span>
                            </label>
                        </p>
                        <p class="checkbox_block">
                            Nov
                            <label class="checkbox_label">
                                <input type="checkbox" class="filled-in checkbox-green" onclick="return false" checked/>
                                <span></span>
                            </label>
                        </p>
                        <p class="checkbox_block">
                            Dec
                            <label class="checkbox_label">
                                <input type="checkbox" class="filled-in checkbox-green" onclick="return false"/>
                                <span></span>
                            </label>
                        </p>
                    </form>
                </div>
                </div>
            </div>
        </div>
    </div>
    </div>
</body>
</html>
