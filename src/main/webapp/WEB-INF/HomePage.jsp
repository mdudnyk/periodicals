<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="ct" tagdir="/WEB-INF/tags" %>

<html lang="${sessionScope.locale.getShortNameId()}">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>PressReader | Home</title>
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/img/tab-icon.ico">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
</head>
<body>
<%@ include file="fragment/Header.jspf" %>
    <div class="body_container">
        <div class="home_top_block">
            <div>
                <h1 class="home_topic_select_text"><fmt:message key="home.topics"/></h1>
                <div class="show_all_topics_field" onclick="open_topic_modal()">
                <c:if test="${requestScope.topics.size() > 0}">
                    <span id="show_all_topics_text"><fmt:message key="home.show_all"/></span>
                    <span id="select_one_topic_text" style="display: none;"><fmt:message key="home.choose_one"/></span>
                </c:if>
                <c:if test="${requestScope.topics.size() < 1}">
                    <span id="no_topics_to_select"><fmt:message key="home.no_topics_to_show"/></span>
                </c:if>
                    <i class="material-icons topic" id="topic_modal_icon">expand_more</i>
                </div>
                <c:if test="${requestScope.topics.size() > 0}">
                <div class="topic_modal" id="topic_modal">
                    <ul>
                        <a href="${pageContext.request.contextPath}/controller?cmd=SHOW_PUBLICATIONS&topicID=featured">
                            <li>
                                <fmt:message key="home.most_populars"/>
                            </li>
                        </a>
                        <c:forEach var="topic" items="${requestScope.topics}">
                            <a href="${pageContext.request.contextPath}/controller?cmd=SHOW_PUBLICATIONS&topicID=${topic.getId()}">
                                <li>
                                    <c:out value="${topic.getAllTranslates().values().iterator().next().getName()}" />
                                </li>
                            </a>
                        </c:forEach>
                    </ul>
                </div>
                </c:if>
            </div>
            <div>
                <span class="home_top_block_stat"><fmt:message key="home.total_publications"/>:
<%--                    <span class="home_top_block_stat_count">--%>
<%--                        24--%>
<%--                    </span>--%>
                </span>
            </div>
        </div>
        <c:if test="${requestScope.topics.size() > 0}">
        <div class="topic_row">
            <a href="${pageContext.request.contextPath}/controller?cmd=SHOW_PUBLICATIONS&topicID=featured">
                <h1><fmt:message key="home.most_populars"/></h1>
            </a>
            <div class="periodical_row">
                <a class="periodical_container">
                    <img class="periodical_title_img" src="${pageContext.request.contextPath}/img/publications/111.jpg">
                    <div class="info_footer">
                        <span class="periodical_title">FAST COMPANY</span>
                        <span class="periodical_price">120,00 uah</span>
                    </div>
                </a>
                <a class="periodical_container">
                    <img class="periodical_title_img" src="${pageContext.request.contextPath}/img/publications/222.png">
                    <div class="info_footer">
                        <span class="periodical_title">BAZAR</span>
                        <span class="periodical_price">137,60 uah</span>
                    </div>
                </a>
                <a class="periodical_container">
                    <img class="periodical_title_img" src="${pageContext.request.contextPath}/img/publications/333.png">
                    <div class="info_footer">
                        <span class="periodical_title">TOP GEAR</span>
                        <span class="periodical_price">190,20 uah</span>
                    </div>
                </a>
                <a class="periodical_container">
                    <img class="periodical_title_img" src="${pageContext.request.contextPath}/img/publications/444.png">
                    <div class="info_footer">
                        <span class="periodical_title">THE GUARDIAN</span>
                        <span class="periodical_price">60,70 uah</span>
                    </div>
                </a>
            </div>
        </div>
            <c:forEach var="topic" items="${requestScope.topics}">
                <c:if test="${requestScope.periodicals.get(topic.getId()).size() > 0}">
                <div class="topic_row">
                    <a href="${pageContext.request.contextPath}/controller?cmd=SHOW_PUBLICATIONS&topicID=${topic.getId()}">
                        <h1><c:out value="${topic.getAllTranslates().values().iterator().next().getName()}"/></h1>
                    </a>
                    <div class="periodical_row">
                        <c:forEach var="periodical" items="${requestScope.periodicals.get(topic.getId())}">
                        <a class="periodical_container"
                           href="${pageContext.request.contextPath}/controller?cmd=SHOW_PERIODICAL&id=${periodical.getId()}">
                            <img class="periodical_title_img"
                                 src="<ct:imageEncoderBase64 image_name="${periodical.getTitleImgName()}"/>">
                            <div class="info_footer">
                                <span class="periodical_title">${periodical.getTitle()}</span>
                                <span class="periodical_price">${periodical.getPrice()}
                                    <fmt:message key="currency_name"/></span>
                            </div>
                        </a>
                        </c:forEach>
                    </div>
                </div>
                </c:if>
            </c:forEach>
        </c:if>
        <c:if test="${requestScope.topics.size() < 1}">
            <br><br>
            <h1><fmt:message key="home.dont_have_publications"/></h1>
        </c:if>
    </div>
    <script src="${pageContext.request.contextPath}/js/home.js"></script>
</body>
</html>
