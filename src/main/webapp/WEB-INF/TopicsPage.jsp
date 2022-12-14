<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ct" tagdir="/WEB-INF/tags" %>

<html lang="${sessionScope.locale.getShortNameId()}">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>PressReader | Topics</title>
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/img/tab-icon.ico">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/materialize/css/materialize.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/topics.css">
</head>
<body>
<%@ include file="fragment/Header.jspf" %>
<div class="delete_topic_modal_container" id="delete_modal">
    <div class="delete_modal_block">
        <div class="delete_modal_header">
            <fmt:message key="topics.deletion_warning"/>
        </div>
        <div class="delete_modal_content">
            <fmt:message key="topics.deletion_warning_msg_1"/>
            <br>
                <span class="delete_modal_topic_name" id="topic_name"></span>
            <br>
            <fmt:message key="topics.deletion_warning_msg_2"/>
        </div>
        <div class="delete_modal_buttons_block">
            <button class="topic_delete_button" onclick="confirmDeleteModal()">
                <fmt:message key="topics.modal_delete_btn"/>
            </button>
            <button class="topic_delete_cancel_button" onclick="closeDeleteModal()">
                <fmt:message key="topics.modal_cancel_btn"/>
            </button>
        </div>
    </div>
</div>
<div class="content">
    <div class="content_topper">
        <div class="content_topper_title"><fmt:message key="topics.topics"/></div>
        <div style="display: flex; align-items: flex-end">
            <div class="content_topper_qty"><fmt:message key="topics.total"/>: ${requestScope.topicsTotal}</div>
            <a href="${pageContext.request.contextPath}/controller?cmd=NEW_TOPIC_PAGE" style="margin-bottom: -4px;">
                <button class="add_topic_btn" type="submit">
                    <div class="add_btn_box">
                        <i class="material-icons add_topic">add</i>
                        <fmt:message key="topics.new_topic"/>
                    </div>
                </button>
            </a>
        </div>
    </div>
    <div class="content_main">
        <div class="above_table_block">
            <div class="amount_manage_block" onclick="open_amount_modal()">
                <c:set var="amountOnPage" scope="request" value="${sessionScope.topicsAmountOnPage}"/>
                <div class="amount_number">${amountOnPage}</div>
                <i class="material-icons" id="amount_modal_icon">expand_more</i>
                <div class="amount_modal" id="amount_modal">
                    <ul>
                        <c:if test="${amountOnPage != 5}">
                            <a href="${pageContext.request.contextPath}/controller?cmd=TOPICS_PAGE&amount=5">
                                <li>5</li>
                            </a>
                        </c:if>
                        <c:if test="${amountOnPage != 10}">
                            <a href="${pageContext.request.contextPath}/controller?cmd=TOPICS_PAGE&amount=10">
                                <li>10</li>
                            </a>
                        </c:if>
                        <c:if test="${amountOnPage != 15}">
                            <a href="${pageContext.request.contextPath}/controller?cmd=TOPICS_PAGE&amount=15">
                                <li>15</li>
                            </a>
                        </c:if>
                    </ul>
                </div>
            </div>
            <div class="close_search_block">
                <c:if test="${sessionScope.topicsSearchString.length() > 0}">
                    <form style="z-index: 1;" action="${pageContext.request.contextPath}/controller">
                        <input type="hidden" name="cmd" value="TOPICS_PAGE">
                        <input type="hidden" name="searchString" value="" spellcheck="false" autocomplete="off">
                        <button type="submit" class="close_search_btn">
                            <fmt:message key="topics.end_search"/>
                        </button>
                    </form>
                </c:if>
            </div>
            <div class="search_block_topic">
                <form action="${pageContext.request.contextPath}/controller">
                    <input type="hidden" name="cmd" value="TOPICS_PAGE">
                    <input type="text" name="searchString" value="${sessionScope.topicsSearchString}"
                           style="width: 200px;" placeholder="<fmt:message key="topics.search_topic"/>"
                           spellcheck="false" autocomplete="off">
                    <button class="search_btn" type="submit">
                        <i class="material-icons search">search</i>
                    </button>
                </form>
            </div>
        </div>
        <table class="centered">
            <thead class="table_head">
            <tr style="border: none">
                <c:set var="sortOrder" scope="request" value="${sessionScope.topicsSortOrder}"/>
                <th style="width: 70px;"><fmt:message key="topics.number"/></th>
                <th>
                    <div class="sortable_column"><fmt:message key="topics.topic_name"/>
                        <div class="sorting_block">
                            <c:if test="${sortOrder.equals('ASC')}">
                                <a href="${pageContext.request.contextPath}/controller?cmd=TOPICS_PAGE&sorting=DESC">
                                    <i class="material-icons sort">arrow_drop_up</i>
                                </a>
                                <a href="">
                                    <i class="material-icons sort disabled">arrow_drop_down</i>
                                </a>
                            </c:if>
                            <c:if test="${sortOrder.equals('DESC')}">
                                <a href="">
                                    <i class="material-icons sort disabled">arrow_drop_up</i>
                                </a>
                                <a href="${pageContext.request.contextPath}/controller?cmd=TOPICS_PAGE&sorting=ASC">
                                    <i class="material-icons sort">arrow_drop_down</i>
                                </a>
                            </c:if>
                        </div>
                    </div>
                </th>
                <th style="width: 80px;"><fmt:message key="topics.edit"/></th>
                <th style="width: 80px; padding-right: 10px;"><fmt:message key="topics.delete"/></th>
            </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${requestScope.topics != null && requestScope.topics.size() > 0}">
                        <c:set var="pageNumber" scope="request" value="${sessionScope.topicsPageNumber}"/>
                        <c:set var="rowIndex" scope="request" value="${pageNumber * amountOnPage - amountOnPage}"/>
                        <c:forEach var="topic" items="${requestScope.topics}">
                            <c:set var="rowIndex" scope="request" value="${rowIndex + 1}"/>
                            <tr>
                                <td><c:out value="${rowIndex}"/></td>
                                <td class="topic_name_row">
                                    <c:set var="topicName"
                                        value="${topic.getAllTranslates().values().iterator().next().getName()}"
                                        scope="request"/>
                                    <c:out value="${topicName}"/>
                                </td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/controller?cmd=EDIT_TOPIC_PAGE&id=${topic.getId()}">
                                        <i class="material-icons edit">edit</i>
                                    </a>
                                </td>
                                <td>
                                    <i class="material-icons delete" onclick="deleteTopicById(${topic.getId()}, '${topicName}')">
                                        delete_forever
                                    </i>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            <div class="under_table_block">
                <ct:paginationBar page="${pageNumber}"
                                  total="${requestScope.totalPages}"
                                  command="TOPICS_PAGE"/>
            </div>
                </c:when>
                <c:otherwise>
                    </tbody>
                    </table>
                        <span class="no_topics_to_show"><fmt:message key="home.no_topics_to_show"/></span>
                </c:otherwise>
            </c:choose>
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/topics.js"></script>
</body>
</html>