<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="${sessionScope.locale.getShortNameId()}">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>PressReader | Edit topic</title>
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
            <fmt:message key="edit_topic.edit_topic"/>
        </a>
    </div>
    <div class="content_main">
        <div class="content_main_top_text">
            <fmt:message key="edit_topic.make_changes"/>
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
                    <span class="new_topic_alerts" id="no_changes">
                        <fmt:message key="edit_topic.alert.make_changes"/>
                    </span>
                    <span class="new_topic_alerts" id="dont_have_topic">
                        <fmt:message key="edit_topic.alert.not_existed_topic"/>
                    </span>
                </div>
            </div>
            <div class="common_msq_block success" id="success_block">
                <i class="material-icons">check_circle</i>
                <div class="new_topic_alert_msgs">
                    <span class="new_topic_alerts" id="success">
                        <fmt:message key="edit_topic.success.topic_edited"/>
                    </span>
                </div>
            </div>
        </div>
        <c:if test="${requestScope.topic != null}">
            <div class="row" style="width: 400px;">
                <form class="col s12" id="new_topic_input">
                    <span id="topic_id" style="display: none;">${requestScope.topic.getId()}</span>
                    <c:forEach var="locale" items="${applicationScope.locales.values()}">
                        <div class="row">
                            <div class="input-field col s12">
                                <input id="input_${locale.getShortNameId()}"
                                       lang="${locale.getShortNameId()}" type="text"
                                       <c:set var="translation"
                                              scope="request"
                                              value="${requestScope.topic.getTranslate(locale.getShortNameId())}"/>
                                       <c:if test="${translation != null}">
                                            value="${translation.getName()}"
                                            topicName="${translation.getName()}"
                                       </c:if>
                                        <c:if test="${translation == null}">
                                            value=""
                                            topicName=""
                                        </c:if>
                                       style="font-size: 16pt;" spellcheck="false" autocomplete="off">
                                <label for="input_${locale.getShortNameId()}" style="color: rgb(141, 141, 141); font-size: 14pt; font-weight: 300;">
                                    <fmt:message key="edit_topic.topic_name"/>
                                    <span style="font-weight: 700; text-transform: lowercase">
                                            ${locale.getLangNameOriginal()}
                                    </span>
                                </label>
                            </div>
                        </div>
                    </c:forEach>
                </form>
            </div>
        </c:if>
        <button class="create_topic_button" onclick="editTopic()">
            <fmt:message key="edit_topic.make_corrections"/>
        </button>
    </div>
</div>
<script src="${pageContext.request.contextPath}/js/edit_topic.js"></script>
<script src="${pageContext.request.contextPath}/materialize/js/materialize.min.js"></script>
</body>
</html>