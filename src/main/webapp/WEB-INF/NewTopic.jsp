<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="${sessionScope.locale.getShortNameId()}">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>PressReader | Home</title>
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/img/tab-icon.ico">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/materialize/css/materialize.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/new_topic.css">
</head>
<body>
<%@ include file="fragment/Header.jspf" %>
<div class="content">
    <div class="content_topper">
        <a href="controller?cmd=TOPICS_PAGE" class="topics_page_link">
            <fmt:message key="topics.topics"/>
        </a>
        <i class="small material-icons" style="margin: 0 10px; opacity: 70%;">navigate_next</i>
        <a style="color: #27c18a; font-weight: 500; text-transform: capitalize">
            <fmt:message key="topics.new_topic"/>
        </a>
    </div>
    <div class="content_main">
        <div class="content_main_top_text">
            <fmt:message key="new_topic.please_add_new"/>
        </div>
        <div class="msg_container">
            <div class="common_msq_block alert" id="alert_block">
                <i class="material-icons">error</i>
                <div class="new_topic_alert_msgs">
                    <span class="new_topic_alerts" id="try_later">
                        <fmt:message key="new_topic.alert.try_later"/>
                    </span>
                    <span class="new_topic_alerts" id="no_values">
                        <fmt:message key="new_topic.alert.no_input"/>
                    </span>
                    <span class="new_topic_alerts" id="fill_all">
                        <fmt:message key="new_topic.alert.fill_all"/>
                    </span>
                    <span class="new_topic_alerts" id="have_topic">
                        <fmt:message key="new_topic.alert.existed_topic"/>
                    </span>
                </div>
            </div>
            <div class="common_msq_block success" id="success_block">
                <i class="material-icons">check_circle</i>
                <div class="new_topic_alert_msgs">
                    <span class="new_topic_alerts" id="success">
                        <fmt:message key="new_topic.success.topic_created"/>
                    </span>
                </div>
            </div>
        </div>
        <div class="row" style="width: 400px;">
            <form class="col s12" id="new_topic_input">
                <c:forEach var="locale" items="${applicationScope.locales.values()}">
                    <div class="row">
                        <div class="input-field col s12">
                            <input id="input_${locale.getShortNameId()}"
                                   lang="${locale.getShortNameId()}" type="text" style="font-size: 16pt;" autocomplete="off">
                            <label for="input_${locale.getShortNameId()}" style="color: rgb(141, 141, 141); font-size: 14pt; font-weight: 300;">
                                <fmt:message key="new_topic.topic_name"/>
                                <span style="font-weight: 700; text-transform: lowercase">
                                        ${locale.getLangNameOriginal()}
                                </span>
                            </label>
                        </div>
                    </div>
                </c:forEach>
            </form>
        </div>
        <button class="create_topic_button" onclick="createNewTopic()">
            <fmt:message key="new_topic.create_topic"/>
        </button>
    </div>
</div>
<script src="${pageContext.request.contextPath}/js/create_topic.js"></script>
<script src="${pageContext.request.contextPath}/materialize/js/materialize.min.js"></script>
</body>
</html>