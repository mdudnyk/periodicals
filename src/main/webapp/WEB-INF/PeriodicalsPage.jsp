<%@ page import="com.periodicals.entity.Topic" %>
<%@ page import="java.util.List" %>
<%@ page import="com.periodicals.entity.Periodical" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
<%
    List<Periodical> periodicals = (List<Periodical>) request.getAttribute("periodicals");
%>
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
                <div class="amount_number">${sessionScope.periodicalAmountOnPage}</div>
                <i class="material-icons" id="amount_modal_icon">expand_more</i>
                <div class="amount_modal" id="amount_modal">
                    <ul>
                        <%
                            int amount = (Integer) session.getAttribute("periodicalAmountOnPage");
                            if (amount != 5) {
                        %>
                        <a href="${pageContext.request.contextPath}/controller?cmd=PERIODICALS_PAGE&amount=5">
                            <li>5</li>
                        </a>
                        <%
                            }
                            if (amount != 10) {
                        %>
                        <a href="${pageContext.request.contextPath}/controller?cmd=PERIODICALS_PAGE&amount=10">
                            <li>10</li>
                        </a>
                        <%
                            }
                            if (amount != 15) {
                        %>
                        <a href="${pageContext.request.contextPath}/controller?cmd=PERIODICALS_PAGE&amount=15">
                            <li>15</li>
                        </a>
                        <%
                            }
                        %>
                    </ul>
                </div>
            </div>
            <div class="close_search_block">
                <c:if test="${sessionScope.periodicalSearchString.length() > 0}">
                    <form style="z-index: 1;" action="${pageContext.request.contextPath}/controller">
                        <input type="hidden" name="cmd" value="PERIODICALS_PAGE">
                        <input type="hidden" name="searchString" value="">
                        <button type="submit" class="close_search_btn">
                            <fmt:message key="periodicals.end_search"/>
                        </button>
                    </form>
                </c:if>
            </div>
            <div class="search_block_topic">
                <form action="${pageContext.request.contextPath}/controller">
                    <input type="hidden" name="cmd" value="SEARCH_PERIODICAL">
                    <input type="text" name="searchString" value="${sessionScope.periodicalSearchString}"
                           placeholder="<fmt:message key="periodicals.search_periodical"/>" autocomplete="off">
                    <button class="search_btn" type="submit">
                        <i class="material-icons search">search</i>
                    </button>
                </form>
            </div>
        </div>
        <table class="centered">
            <thead class="table_head">
            <tr style="border: none">
                <th style="width: 70px;"><fmt:message key="topics.number"/></th>
                <th>
                    <div class="sortable_column"><fmt:message key="periodicals.topic_name"/>
                        <div class="sorting_block">
                            <i class="material-icons sort disabled">arrow_drop_up</i>
                            <i class="material-icons sort">arrow_drop_down</i>
                        </div>
                    </div>
                </th>
                <th>
                    <div class="sortable_column"><fmt:message key="topics.topic_name"/>
                        <div class="sorting_block">
                            <i class="material-icons sort disabled">arrow_drop_up</i>
                            <i class="material-icons sort">arrow_drop_down</i>
                        </div>
                    </div>
                </th>
                <th>
                    <div class="sortable_column" style="width: 210px;"><fmt:message key="periodicals.issue_country"/>
                        <div class="sorting_block">
                            <i class="material-icons sort disabled">arrow_drop_up</i>
                            <i class="material-icons sort">arrow_drop_down</i>
                        </div>
                    </div>
                </th>
                <th style="width: 80px;"><fmt:message key="topics.edit"/></th>
                <th style="width: 80px; padding-right: 10px;"><fmt:message key="topics.delete"/></th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>1</td>
                <td class="periodical_column">Top gear</td>
                <td class="periodical_column">Automotive</td>
                <td>Ukraine</td>
                <td><a href="/controller?cmd=EDIT_TOPIC_PAGE&id="><i class="material-icons edit" >edit</i></a></td>
                <td><i class="material-icons delete" onclick="deletePeriodicalById(1)">delete_forever</i></td>
            </tr>
            </tbody>
        </table>
        <div class="under_table_block">
            <ul class="pagination" style="cursor: default;">
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
                <li><a href="#!"><i class="material-icons" >chevron_right</i></a></li>
            </ul>
        </div>

    </div>
</div>


<script src="${pageContext.request.contextPath}/js/periodicals.js"></script>
</body>
</html>