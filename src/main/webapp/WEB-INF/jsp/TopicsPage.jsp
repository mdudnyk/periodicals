<%@ page import="com.periodicals.entity.Topic" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/topics.css">
</head>
<body>
<%@ include file="fragment/Header.jspf" %>
<%
    List<Topic> topics = (List<Topic>) request.getAttribute("topics");
%>
<div class="content">
    <div class="content_topper">
        <div class="content_topper_title"><fmt:message key="topics.topics"/></div>
        <div style="display: flex; align-items: flex-end">
            <div class="content_topper_qty"><fmt:message key="topics.total"/>: ${requestScope.topicsTotal}</div>
            <button class="add_topic_btn">
                <div class="add_btn_box">
                    <i class="material-icons add_topic">add</i>
                    <fmt:message key="topics.new_topic"/>
                </div>
            </button>
        </div>
    </div>
    <div class="content_main">
        <%
            if (topics != null && topics.size() > 0) {
        %>
        <div class="above_table_block">
            <div class="amount_manage_block" onclick="open_amount_modal()">
                <div class="amount_number">${sessionScope.topicAmountOnPage}</div>
                <i class="material-icons" id="amount_modal_icon">expand_more</i>
                <div class="amount_modal" id="amount_modal">
                    <ul>
                        <%
                            int amount = (Integer) session.getAttribute("topicAmountOnPage");
                            if (amount != 10) {
                        %>
                        <a href="${pageContext.request.contextPath}/controller?cmd=TOPICS_PAGE&amount=10">
                            <li>10</li>
                        </a>
                        <%
                            }
                            if (amount != 15) {
                        %>
                        <a href="${pageContext.request.contextPath}/controller?cmd=TOPICS_PAGE&amount=15">
                            <li>15</li>
                        </a>
                        <%
                            }
                            if (amount != 25) {
                        %>
                        <a href="${pageContext.request.contextPath}/controller?cmd=TOPICS_PAGE&amount=25">
                            <li>25</li>
                        </a>
                        <%
                            }
                        %>
                    </ul>
                </div>
            </div>
            <div class="search_block_topic">
                <form action="${pageContext.request.contextPath}/controller">
                    <input type="hidden" name="cmd" value="SEARCH_TOPIC">
                    <input type="text" name="search_string" placeholder="<fmt:message key="topics.search_topic"/>">
                    <button class="search_btn" type="submit">
                        <i class="material-icons search">search</i>
                    </button>
                </form>
            </div>
        </div>
        <table class="centered">
            <thead class="table_head">
            <tr style="border: none">
                <th style="width: 20px; padding-left: 10px;"><fmt:message key="topics.number"/></th>
                <th>
                    <div class="sortable_column"><fmt:message key="topics.topic_name"/>
                        <div class="sorting_block">
                            <%
                                if (session.getAttribute("sorting").equals("ASC")) {
                            %>
                            <a href="${pageContext.request.contextPath}/controller?cmd=TOPICS_PAGE&sorting=DESC">
                                <i class="material-icons sort">arrow_drop_up</i>
                            </a>
                            <a href="">
                                <i class="material-icons sort disabled">arrow_drop_down</i>
                            </a>
                            <%
                                } else if (session.getAttribute("sorting").equals("DESC")) {
                            %>
                            <a href="">
                                <i class="material-icons sort disabled">arrow_drop_up</i>
                            </a>
                            <a href="${pageContext.request.contextPath}/controller?cmd=TOPICS_PAGE&sorting=ASC">
                                <i class="material-icons sort">arrow_drop_down</i>
                            </a>
                            <%
                                }
                            %>
                        </div>
                    </div>
                </th>
                <th style="width: 80px;"><fmt:message key="topics.edit"/></th>
                <th style="width: 80px; padding-right: 10px;"><fmt:message key="topics.delete"/></th>
            </tr>
            </thead>
            <tbody>
            <c:set var = "number" scope="page" value = "${sessionScope.topicPageNumber * sessionScope.topicAmountOnPage - sessionScope.topicAmountOnPage}"/>
            <c:forEach var="topic" items="${requestScope.topics}">
                <c:set var = "number" scope="page" value = "${number + 1}"/>
                <tr>
                    <td><c:out value="${number}"/></td>
                    <td class="topic_name_row"><c:out value="${topic.getAllTranslates().values().iterator().next().getName()}"/></td>
                    <td>
                        <a href="${pageContext.request.contextPath}/controller?cmd=EDIT_TOPIC_PAGE&id=${topic.getId()}">
                            <i class="material-icons edit">edit</i>
                        </a>
                    </td>
                    <td><i class="material-icons delete" onclick="tryToDeleteTopic(${topic.getId()})">delete_forever</i></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <div class="under_table_block">
            <ul class="pagination">
                <li class="disabled"><a href="#!"><i class="material-icons">chevron_left</i></a></li>
                <li><a href="#!">1</a></li>
                <li><a href="#!">2</a></li>
                <li class="active green"><a href="#!">3</a></li>
                <li><a href="#!">4</a></li>
                <li><a href="#!">5</a></li>
                <li><a href="#!">6</a></li>
                <li><a href="#!">7</a></li>
                <li><a href="#!">8</a></li>
                <li class="disabled"><a href="#!">...</a></li>
                <li><a href="#!">22</a></li>
                <li><a href="#!"><i class="material-icons">chevron_right</i></a></li>
            </ul>
        </div>
        <%
            } else {
        %>
            <span class="no_topics_to_select"><fmt:message key="topics.no_publications"/></span>
        <%
            }
        %>
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/topics.js"></script>
</body>
</html>
