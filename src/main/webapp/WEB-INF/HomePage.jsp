<%@ page import="java.util.List" %>
<%@ page import="com.periodicals.entity.Topic" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

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
<%
    List<Topic> topics = (List<Topic>) request.getAttribute("topics");
%>
    <div class="body_container">
        <div class="home_top_block">
            <div>
                <h1 class="home_topic_select_text"><fmt:message key="home.topics"/></h1>
                <div class="show_all_topics_field" onclick="open_topic_modal()">
                    <%
                        if (topics != null && topics.size() > 0) {
                    %>
                    <span id="show_all_topics_text"><fmt:message key="home.show_all"/></span>
                    <span id="select_one_topic_text" style="display: none;"><fmt:message key="home.choose_one"/></span>
                    <%
                        } else {
                    %>
                    <span id="no_topics_to_select"><fmt:message key="home.no_topics_to_show"/></span>
                    <%
                        }
                    %>
                    <i class="material-icons topic" id="topic_modal_icon">expand_more</i>
                </div>
                <%
                    if (topics != null && topics.size() > 0) {
                %>
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
                <%
                    }
                %>
            </div>
            <div>
                <span class="home_top_block_stat"><fmt:message key="home.total_publications"/>:
                    <span class="home_top_block_stat_count">
                        24
                    </span>
                </span>
            </div>
        </div>
        <%
            if (topics != null && topics.size() > 0) {
        %>
        <div class="topic_row">
            <a href="${pageContext.request.contextPath}/controller?cmd=SHOW_PUBLICATIONS&topicID=featured">
                <h1><fmt:message key="home.most_populars"/></h1>
            </a>
            <div class="periodical_row">
                <div class="periodical_container">
                    <img class="periodical_title_img" src="${pageContext.request.contextPath}/img/publications/111.jpg">
                    <div class="info_footer">
                        <span class="periodical_title">FAST COMPANY</span>
                        <span class="periodical_price">120,00 uah</span>
                    </div>
                </div>
                <div class="periodical_container">
                    <img class="periodical_title_img" src="${pageContext.request.contextPath}/img/publications/222.png">
                    <div class="info_footer">
                        <span class="periodical_title">BAZAR</span>
                        <span class="periodical_price">137,60 uah</span>
                    </div>
                </div>
                <div class="periodical_container">
                    <img class="periodical_title_img" src="${pageContext.request.contextPath}/img/publications/333.png">
                    <div class="info_footer">
                        <span class="periodical_title">TOP GEAR</span>
                        <span class="periodical_price">190,20 uah</span>
                    </div>
                </div>
                <div class="periodical_container">
                    <img class="periodical_title_img" src="${pageContext.request.contextPath}/img/publications/444.png">
                    <div class="info_footer">
                        <span class="periodical_title">THE GUARDIAN</span>
                        <span class="periodical_price">60,70 uah</span>
                    </div>
                </div>
            </div>
        </div>

        <c:forEach var="topic" items="${requestScope.topics}">
        <div class="topic_row">
            <a href="${pageContext.request.contextPath}/controller?cmd=SHOW_PUBLICATIONS&topicID=${topic.getId()}">
                <h1><c:out value="${topic.getAllTranslates().values().iterator().next().getName()}"/></h1>
            </a>
            <div class="periodical_row">
                <div class="periodical_container">
                    <img class="periodical_title_img" src="${pageContext.request.contextPath}/img/publications/555.jpg">
                    <div class="info_footer">
                        <span class="periodical_title">THE WASHINGTON POST</span>
                        <span class="periodical_price">56,00 uah</span>
                    </div>
                </div>
                <div class="periodical_container">
                    <img class="periodical_title_img" src="${pageContext.request.contextPath}/img/publications/666.jpg">
                    <div class="info_footer">
                        <span class="periodical_title">THE GLOBE AND MAIL</span>
                        <span class="periodical_price">47,35 uah</span>
                    </div>
                </div>
                <div class="periodical_container">
                    <img class="periodical_title_img" src="${pageContext.request.contextPath}/img/publications/no_image.svg">
                    <div class="info_footer">
                        <span class="periodical_title">БУЛЬВАР ГОРДОНА</span>
                        <span class="periodical_price">22,10 uah</span>
                    </div>
                </div>
                <div class="periodical_container">
                    <img class="periodical_title_img" src="${pageContext.request.contextPath}/img/publications/777.jpg">
                    <div class="info_footer">
                        <span class="periodical_title">THE GUARDIAN</span>
                        <span class="periodical_price">60,70 uah</span>
                    </div>
                </div>
            </div>
        </div>
        </c:forEach>
        <%
            } else {
        %>
            <br><br>
            <h1><fmt:message key="home.dont_have_publications"/></h1>
        <%
            }
        %>
    </div>
    <script src="${pageContext.request.contextPath}/js/home.js"></script>
</body>
</html>
