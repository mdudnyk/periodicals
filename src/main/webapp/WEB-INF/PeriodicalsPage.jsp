<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ct" tagdir="/WEB-INF/tags" %>

<html lang="${sessionScope.locale.getShortNameId()}">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>PressReader | Periodicals</title>
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/img/tab-icon.ico">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/materialize/css/materialize.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/periodicals.css">
</head>
<body>
<%@ include file="fragment/Header.jspf" %>
<div class="delete_periodical_modal_container" id="delete_modal">
    <div class="delete_modal_block">
        <div class="delete_modal_header">
            <fmt:message key="periodicals.deletion_warning"/>
        </div>
        <div class="delete_modal_content">
            <p>
                <fmt:message key="periodicals.deletion_warning_msg_1"/>
                <br>
                    <span class="delete_modal_periodical_name" id="periodical_name"></span>
                <br>
                <fmt:message key="periodicals.deletion_warning_msg_2"/>
            </p>
        </div>
        <div class="delete_modal_buttons_block">
            <button class="periodical_delete_button" onclick="confirmDeleteModal()">
                <fmt:message key="periodicals.modal_delete_btn"/>
            </button>
            <button class="periodical_delete_cancel_button" onclick="closeDeleteModal()">
                <fmt:message key="periodicals.modal_cancel_btn"/>
            </button>
        </div>
    </div>
</div>
<div class="content">
    <div class="content_topper">
        <div class="content_topper_title"><fmt:message key="periodicals.periodicals"/></div>
        <div style="display: flex; align-items: flex-end">
            <div class="content_topper_qty"><fmt:message key="periodicals.total"/>: ${requestScope.periodicalsTotal}</div>
            <a href="${pageContext.request.contextPath}/controller?cmd=NEW_PERIODICAL_PAGE" style="margin-bottom: -4px;">
                <button class="add_periodical_btn" type="submit" >
                    <div class="add_btn_box">
                        <i class="material-icons add_periodical">add</i>
                        <fmt:message key="periodicals.new_topic"/>
                    </div>
                </button>
            </a>
        </div>
    </div>
    <div class="content_main">
        <div class="above_table_block">
            <div class="amount_manage_block" onclick="open_amount_modal()">
                <c:set var="amountOnPage" scope="request" value="${sessionScope.periodicalsAmountOnPage}"/>
                <div class="amount_number">${amountOnPage}</div>
                <i class="material-icons" id="amount_modal_icon">expand_more</i>
                <div class="amount_modal" id="amount_modal">
                    <ul>
                        <c:if test="${amountOnPage != 5}">
                            <a href="${pageContext.request.contextPath}/controller?cmd=PERIODICALS_PAGE&amount=5">
                                <li>5</li>
                            </a>
                        </c:if>
                        <c:if test="${amountOnPage != 10}">
                            <a href="${pageContext.request.contextPath}/controller?cmd=PERIODICALS_PAGE&amount=10">
                                <li>10</li>
                            </a>
                        </c:if>
                        <c:if test="${amountOnPage != 15}">
                            <a href="${pageContext.request.contextPath}/controller?cmd=PERIODICALS_PAGE&amount=15">
                                <li>15</li>
                            </a>
                        </c:if>
                    </ul>
                </div>
            </div>
            <div class="close_search_block">
                <c:if test="${sessionScope.periodicalsSearchString.length() > 0}">
                    <form style="z-index: 1;" action="${pageContext.request.contextPath}/controller">
                        <input type="hidden" name="cmd" value="PERIODICALS_PAGE">
                        <input type="hidden" name="searchString" value="" spellcheck="false" autocomplete="off">
                        <button type="submit" class="close_search_btn">
                            <fmt:message key="periodicals.end_search"/>
                        </button>
                    </form>
                </c:if>
            </div>
            <div class="search_block_periodical">
                <form action="${pageContext.request.contextPath}/controller">
                    <input type="hidden" name="cmd" value="PERIODICALS_PAGE">
                    <input type="text" name="searchString" value="${sessionScope.periodicalsSearchString}"
                           style="width: 200px;" placeholder="<fmt:message key="periodicals.search_periodical"/>"
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
                <c:set var="sortBy" scope="request" value="${sessionScope.periodicalsSortBy}"/>
                <c:set var="sortOrder" scope="request" value="${sessionScope.periodicalsSortOrder}"/>
                <th style="width: 70px;"><fmt:message key="topics.number"/></th>
                <th>
                    <div class="sortable_column"><fmt:message key="periodicals.periodicals_title"/>
                        <div class="sorting_block">
                            <c:choose>
                                <c:when test="${sortBy.equals('title')}">
                                    <c:choose>
                                        <c:when test="${sortOrder.equals('ASC')}">
                                            <a href="${pageContext.request.contextPath}/controller?cmd=PERIODICALS_PAGE&sortBy=title&sorting=DESC">
                                                <i class="material-icons sort">arrow_drop_up</i>
                                            </a>
                                            <a href="">
                                                <i class="material-icons sort disabled">arrow_drop_down</i>
                                            </a>
                                        </c:when>
                                        <c:otherwise>
                                            <a href="">
                                                <i class="material-icons sort disabled">arrow_drop_up</i>
                                            </a>
                                            <a href="${pageContext.request.contextPath}/controller?cmd=PERIODICALS_PAGE&sortBy=title&sorting=ASC">
                                                <i class="material-icons sort">arrow_drop_down</i>
                                            </a>
                                        </c:otherwise>
                                    </c:choose>
                                </c:when>
                                <c:otherwise>
                                    <a href="${pageContext.request.contextPath}/controller?cmd=PERIODICALS_PAGE&sortBy=title&sorting=DESC">
                                        <i class="material-icons sort">arrow_drop_up</i>
                                    </a>
                                    <a href="${pageContext.request.contextPath}/controller?cmd=PERIODICALS_PAGE&sortBy=title&sorting=ASC">
                                        <i class="material-icons sort">arrow_drop_down</i>
                                    </a>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </th>
                <th>
                    <div class="sortable_column"><fmt:message key="topics.topic_name"/>
                        <div class="sorting_block">
                            <c:choose>
                                <c:when test="${sortBy.equals('topic')}">
                                    <c:choose>
                                        <c:when test="${sortOrder.equals('ASC')}">
                                            <a href="${pageContext.request.contextPath}/controller?cmd=PERIODICALS_PAGE&sortBy=topic&sorting=DESC">
                                                <i class="material-icons sort">arrow_drop_up</i>
                                            </a>
                                            <a href="">
                                                <i class="material-icons sort disabled">arrow_drop_down</i>
                                            </a>
                                        </c:when>
                                        <c:otherwise>
                                            <a href="">
                                                <i class="material-icons sort disabled">arrow_drop_up</i>
                                            </a>
                                            <a href="${pageContext.request.contextPath}/controller?cmd=PERIODICALS_PAGE&sortBy=topic&sorting=ASC">
                                                <i class="material-icons sort">arrow_drop_down</i>
                                            </a>
                                        </c:otherwise>
                                    </c:choose>
                                </c:when>
                                <c:otherwise>
                                    <a href="${pageContext.request.contextPath}/controller?cmd=PERIODICALS_PAGE&sortBy=topic&sorting=DESC">
                                        <i class="material-icons sort">arrow_drop_up</i>
                                    </a>
                                    <a href="${pageContext.request.contextPath}/controller?cmd=PERIODICALS_PAGE&sortBy=topic&sorting=ASC">
                                        <i class="material-icons sort">arrow_drop_down</i>
                                    </a>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </th>
                <th>
                    <div class="sortable_column" style="width: 160px;"><fmt:message key="periodicals.price"/>${sessionScope.locale.getCurrency()}
                        <div class="sorting_block">
                            <c:choose>
                                <c:when test="${sortBy.equals('price')}">
                                    <c:choose>
                                        <c:when test="${sortOrder.equals('ASC')}">
                                            <a href="${pageContext.request.contextPath}/controller?cmd=PERIODICALS_PAGE&sortBy=price&sorting=DESC">
                                                <i class="material-icons sort">arrow_drop_up</i>
                                            </a>
                                            <a href="">
                                                <i class="material-icons sort disabled">arrow_drop_down</i>
                                            </a>
                                        </c:when>
                                        <c:otherwise>
                                            <a href="">
                                                <i class="material-icons sort disabled">arrow_drop_up</i>
                                            </a>
                                            <a href="${pageContext.request.contextPath}/controller?cmd=PERIODICALS_PAGE&sortBy=price&sorting=ASC">
                                                <i class="material-icons sort">arrow_drop_down</i>
                                            </a>
                                        </c:otherwise>
                                    </c:choose>
                                </c:when>
                                <c:otherwise>
                                    <a href="${pageContext.request.contextPath}/controller?cmd=PERIODICALS_PAGE&sortBy=price&sorting=DESC">
                                        <i class="material-icons sort">arrow_drop_up</i>
                                    </a>
                                    <a href="${pageContext.request.contextPath}/controller?cmd=PERIODICALS_PAGE&sortBy=price&sorting=ASC">
                                        <i class="material-icons sort">arrow_drop_down</i>
                                    </a>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </th>
                <th>
                    <div class="sortable_column" style="width: 120px;"><fmt:message key="periodicals.status"/>
                        <div class="sorting_block">
                            <c:choose>
                                <c:when test="${sortBy.equals('status')}">
                                    <c:choose>
                                        <c:when test="${sortOrder.equals('ASC')}">
                                            <a href="${pageContext.request.contextPath}/controller?cmd=PERIODICALS_PAGE&sortBy=status&sorting=DESC">
                                                <i class="material-icons sort">arrow_drop_up</i>
                                            </a>
                                            <a href="">
                                                <i class="material-icons sort disabled">arrow_drop_down</i>
                                            </a>
                                        </c:when>
                                        <c:otherwise>
                                            <a href="">
                                                <i class="material-icons sort disabled">arrow_drop_up</i>
                                            </a>
                                            <a href="${pageContext.request.contextPath}/controller?cmd=PERIODICALS_PAGE&sortBy=status&sorting=ASC">
                                                <i class="material-icons sort">arrow_drop_down</i>
                                            </a>
                                        </c:otherwise>
                                    </c:choose>
                                </c:when>
                                <c:otherwise>
                                    <a href="${pageContext.request.contextPath}/controller?cmd=PERIODICALS_PAGE&sortBy=status&sorting=DESC">
                                        <i class="material-icons sort">arrow_drop_up</i>
                                    </a>
                                    <a href="${pageContext.request.contextPath}/controller?cmd=PERIODICALS_PAGE&sortBy=status&sorting=ASC">
                                        <i class="material-icons sort">arrow_drop_down</i>
                                    </a>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </th>
                <th style="width: 80px;"><fmt:message key="topics.edit"/></th>
                <th style="width: 80px; padding-right: 10px;"><fmt:message key="topics.delete"/></th>
            </tr>
            </thead>
            <tbody>
            <c:choose>
                <c:when test="${requestScope.periodicals != null && requestScope.periodicals.size() > 0}">
                    <c:set var="pageNumber" scope="request" value="${sessionScope.periodicalsPageNumber}"/>
                    <c:set var="rowIndex" scope="request" value="${pageNumber * amountOnPage - amountOnPage}"/>
                    <c:forEach var="periodical" items="${requestScope.periodicals}">
                        <c:set var="rowIndex" scope="request" value="${rowIndex + 1}"/>
                        <tr>
                            <td style="padding-left: 20px;">${rowIndex}</td>
                            <td class="periodical_column">${periodical.getTitle()}</td>
                            <td class="periodical_column">
                                    <c:if test="${periodical.getTopicName() == null}">
                                        <fmt:message key="periodicals.no_topic_name"/>
                                    </c:if>
                                    <c:if test="${periodical.getTopicName() != null}">
                                        ${periodical.getTopicName()}
                                    </c:if>
                            </td>
                            <td>${periodical.getPrice()}</td>
                            <td
                                    <c:set var="status" scope="page" value="${periodical.isActive()}"/>
                                    style="font-size: 11pt; font-weight: 700;
                                    <c:if test="${status == false}">color: rgb(255, 117, 117);">
                                        <fmt:message key="periodicals.status.disabled"/>
                                    </c:if>
                                    <c:if test="${status == true}">color: #27c18a;">
                                        <fmt:message key="periodicals.status.active"/>
                                    </c:if>
                            </td>
                            <td>
                                <a href="${pageContext.request.contextPath}/controller?cmd=EDIT_PERIODICAL_PAGE&id=${periodical.getId()}">
                                    <i class="material-icons edit">edit</i>
                                </a>
                            </td>
                            <td>
                                <i class="material-icons delete" onclick="deletePeriodicalById(${periodical.getId()}, '${periodical.getTitle()}')">
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
                                  command="PERIODICALS_PAGE"/>
            </div>
                </c:when>
                <c:otherwise>
                        </tbody>
                    </table>
                        <span class="no_periodicals_to_show"><fmt:message key="periodicals.no_periodicals_to_show"/></span>
                </c:otherwise>
            </c:choose>
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/periodicals.js"></script>
</body>
</html>