<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ct" tagdir="/WEB-INF/tags" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="com.periodicals.util.DateFormatter" %>

<html lang="${sessionScope.locale.getShortNameId()}">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>PressReader | My Subscriptions</title>
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/img/tab-icon.ico">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/materialize/css/materialize.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/my_subscriptions.css">
</head>
<body>
    <%@ include file="fragment/Header.jspf" %>
    <div class="delete_subscription_modal_container" id="alert_modal">
        <div class="delete_modal_block">
            <div class="delete_modal_header">
                <fmt:message key="my_subscriptions.deletion_error"/>
            </div>
            <div class="delete_modal_content">
                <p class="alert_msg" id="something_went_wrong"><fmt:message key="my_subscriptions.alert_try_later"/></p>
                <p class="alert_msg" id="cant_delete"><fmt:message key="my_subscriptions.alert_cant_delete"/></p>
                <p class="alert_msg" id="dont_have_rights"><fmt:message key="my_subscriptions.alert_no_rights"/></p>
            </div>
            <div class="delete_modal_buttons_block">
                <button class="subscription_delete_cancel_button" onclick="closeAlertModal()">
                    <fmt:message key="my_subscriptions.close_btn"/>
                </button>
            </div>
        </div>
    </div>
    <div class="content">
        <div class="content_topper">
            <div class="content_topper_title"><fmt:message key="my_subscriptions.my_subscriptions"/></div>
            <div style="display: flex; align-items: flex-end">
                <div class="content_topper_qty">
                    <fmt:message key="my_subscriptions.total"/>${requestScope.subscriptionsTotal}</div>
            </div>
        </div>
        <div class="content_main">
            <div class="above_table_block">
                <div class="amount_manage_block" onclick="open_amount_modal()">
                    <c:set var="amountOnPage" scope="request" value="${sessionScope.subscriptionsAmountOnPage}"/>
                    <div class="amount_number">${amountOnPage}</div>
                    <i class="material-icons" id="amount_modal_icon">expand_more</i>
                    <div class="amount_modal" id="amount_modal">
                        <ul>
                            <c:if test="${amountOnPage != 5}">
                                <a href="${pageContext.request.contextPath}/controller?cmd=MY_SUBSCRIPTIONS&amount=5">
                                    <li>5</li>
                                </a>
                            </c:if>
                            <c:if test="${amountOnPage != 10}">
                                <a href="${pageContext.request.contextPath}/controller?cmd=MY_SUBSCRIPTIONS&amount=10">
                                    <li>10</li>
                                </a>
                            </c:if>
                            <c:if test="${amountOnPage != 15}">
                                <a href="${pageContext.request.contextPath}/controller?cmd=MY_SUBSCRIPTIONS&amount=15">
                                    <li>15</li>
                                </a>
                            </c:if>
                        </ul>
                    </div>
                </div>
                <div class="close_search_block">
                    <c:if test="${sessionScope.subscriptionsSearchString.length() > 0}">
                        <form style="z-index: 1;" action="${pageContext.request.contextPath}/controller">
                            <input type="hidden" name="cmd" value="MY_SUBSCRIPTIONS">
                            <input type="hidden" name="searchString" value="" spellcheck="false" autocomplete="off">
                            <button type="submit" class="close_search_btn">
                                <fmt:message key="my_subscriptions.end_search"/>
                            </button>
                        </form>
                    </c:if>
                </div>
                <div class="search_block_subscription">
                    <form action="${pageContext.request.contextPath}/controller">
                        <input type="hidden" name="cmd" value="MY_SUBSCRIPTIONS">
                        <input type="text" name="searchString" value="${sessionScope.subscriptionsSearchString}"
                            style="width: 200px;" placeholder="<fmt:message key="my_subscriptions.search_by_title"/>"
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
                        <c:set var="sortBy" scope="request" value="${sessionScope.subscriptionsSortBy}"/>
                        <c:set var="sortOrder" scope="request" value="${sessionScope.subscriptionsSortOrder}"/>
                        <th style="width: 70px; padding-left: 20px;"><fmt:message key="topics.number"/></th>
                        <th>
                            <div class="sortable_column" style="width: 250px;"><fmt:message key="my_subscriptions.periodical_title"/>
                                <div class="sorting_block">
                                    <c:choose>
                                        <c:when test="${sortBy.equals('title')}">
                                            <c:choose>
                                                <c:when test="${sortOrder.equals('ASC')}">
                                                    <a href="${pageContext.request.contextPath}/controller?cmd=MY_SUBSCRIPTIONS&sortBy=title&sorting=DESC">
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
                                                    <a href="${pageContext.request.contextPath}/controller?cmd=MY_SUBSCRIPTIONS&sortBy=title&sorting=ASC">
                                                        <i class="material-icons sort">arrow_drop_down</i>
                                                    </a>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:when>
                                        <c:otherwise>
                                            <a href="${pageContext.request.contextPath}/controller?cmd=MY_SUBSCRIPTIONS&sortBy=title&sorting=DESC">
                                                <i class="material-icons sort">arrow_drop_up</i>
                                            </a>
                                            <a href="${pageContext.request.contextPath}/controller?cmd=MY_SUBSCRIPTIONS&sortBy=title&sorting=ASC">
                                                <i class="material-icons sort">arrow_drop_down</i>
                                            </a>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </th>
                        <th>
                            <div class="sortable_column"><fmt:message key="my_subscriptions.subscription_date"/>
                                <div class="sorting_block">
                                    <c:choose>
                                        <c:when test="${sortBy.equals('createdAt')}">
                                            <c:choose>
                                                <c:when test="${sortOrder.equals('ASC')}">
                                                    <a href="${pageContext.request.contextPath}/controller?cmd=MY_SUBSCRIPTIONS&sortBy=createdAt&sorting=DESC">
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
                                                    <a href="${pageContext.request.contextPath}/controller?cmd=MY_SUBSCRIPTIONS&sortBy=createdAt&sorting=ASC">
                                                        <i class="material-icons sort">arrow_drop_down</i>
                                                    </a>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:when>
                                        <c:otherwise>
                                            <a href="${pageContext.request.contextPath}/controller?cmd=MY_SUBSCRIPTIONS&sortBy=createdAt&sorting=DESC">
                                                <i class="material-icons sort">arrow_drop_up</i>
                                            </a>
                                            <a href="${pageContext.request.contextPath}/controller?cmd=MY_SUBSCRIPTIONS&sortBy=createdAt&sorting=ASC">
                                                <i class="material-icons sort">arrow_drop_down</i>
                                            </a>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </th>
                        <th>
                            <div class="sortable_column" style="width: 160px;">
                                <fmt:message key="my_subscriptions.total_cost"/>
                                <div class="sorting_block">
                                    <c:choose>
                                        <c:when test="${sortBy.equals('price')}">
                                            <c:choose>
                                                <c:when test="${sortOrder.equals('ASC')}">
                                                    <a href="${pageContext.request.contextPath}/controller?cmd=MY_SUBSCRIPTIONS&sortBy=price&sorting=DESC">
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
                                                    <a href="${pageContext.request.contextPath}/controller?cmd=MY_SUBSCRIPTIONS&sortBy=price&sorting=ASC">
                                                        <i class="material-icons sort">arrow_drop_down</i>
                                                    </a>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:when>
                                        <c:otherwise>
                                            <a href="${pageContext.request.contextPath}/controller?cmd=MY_SUBSCRIPTIONS&sortBy=price&sorting=DESC">
                                                <i class="material-icons sort">arrow_drop_up</i>
                                            </a>
                                            <a href="${pageContext.request.contextPath}/controller?cmd=MY_SUBSCRIPTIONS&sortBy=price&sorting=ASC">
                                                <i class="material-icons sort">arrow_drop_down</i>
                                            </a>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </th>
                        <th>
                            <div class="sortable_column">
                                <fmt:message key="my_subscriptions.expiration_date"/>
                                <div class="sorting_block">
                                    <c:choose>
                                        <c:when test="${sortBy.equals('expiredAt')}">
                                            <c:choose>
                                                <c:when test="${sortOrder.equals('ASC')}">
                                                    <a href="${pageContext.request.contextPath}/controller?cmd=MY_SUBSCRIPTIONS&sortBy=expiredAt&sorting=DESC">
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
                                                    <a href="${pageContext.request.contextPath}/controller?cmd=MY_SUBSCRIPTIONS&sortBy=expiredAt&sorting=ASC">
                                                        <i class="material-icons sort">arrow_drop_down</i>
                                                    </a>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:when>
                                        <c:otherwise>
                                            <a href="${pageContext.request.contextPath}/controller?cmd=MY_SUBSCRIPTIONS&sortBy=expiredAt&sorting=DESC">
                                                <i class="material-icons sort">arrow_drop_up</i>
                                            </a>
                                            <a href="${pageContext.request.contextPath}/controller?cmd=MY_SUBSCRIPTIONS&sortBy=expiredAt&sorting=ASC">
                                                <i class="material-icons sort">arrow_drop_down</i>
                                            </a>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </th>
                        <th>
                            <div class="sortable_column" style="width: 90px;"><fmt:message key="my_subscriptions.status"/></div>
                        </th>
                        <th style="width: 80px;"><fmt:message key="my_subscriptions.details"/></th>
                        <th style="width: 80px;"><fmt:message key="my_subscriptions.delete"/></th>
                    </tr>
                </thead>
                <tbody>
                <c:choose>
                    <c:when test="${requestScope.subscriptions != null && requestScope.subscriptions.size() > 0}">
                        <c:set var="pageNumber" scope="request" value="${sessionScope.subscriptionsPageNumber}"/>
                        <c:set var="rowIndex" scope="request" value="${pageNumber * amountOnPage - amountOnPage}"/>
                        <c:forEach var="subscription" items="${requestScope.subscriptions}">
                            <c:set var="rowIndex" scope="request" value="${rowIndex + 1}"/>
                            <tr>
                                <td style="padding-left: 20px;">${rowIndex}</td>
                                <td style="font-weight: 700; text-align: left; padding-left: 35px;" class="subscription_column"
                                    style="text-align: left; padding-left: 15px;">${subscription.getPeriodicalTitle()}</td>
                                <td class="subscription_column"
                                    >${DateFormatter.getString(subscription.getCreatedAt(), sessionScope.locale.getShortNameId())}</td>
                                <td>${MoneyFormatter.toHumanReadable(subscription.getPrice())}</td>
                                <td class="subscription_column"
                                >${DateFormatter.getString(subscription.getExpiredAt(), sessionScope.locale.getShortNameId())}</td>
                                <td <c:set var="expired" scope="page" value="${subscription.getExpiredAt().isAfter(LocalDate.now())}"/>
                                    style="font-size: 11pt; font-weight: 700;
                                    <c:if test="${expired == false}">color: rgb(255, 117, 117);">
                                        <fmt:message key="my_subscriptions.status.expired"/>
                                    </c:if>
                                    <c:if test="${expired == true}">color: #27c18a;">
                                        <fmt:message key="my_subscriptions.status.active"/>
                                    </c:if>
                                </td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/controller?cmd=SUBSCRIPTION_DETAILS&id=${subscription.getId()}">
                                        <i class="material-icons edit">zoom_in</i>
                                    </a>
                                </td>
                                <td>
                                    <i class="material-icons delete"
                                       onclick="deleteSubscriptionById(${subscription.getId()})">delete_forever</i>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <div class="under_table_block">
                    <ct:paginationBar page="${pageNumber}"
                                      total="${requestScope.totalPages}"
                                      command="MY_SUBSCRIPTIONS"/>
                </div>
                    </c:when>
                    <c:otherwise>
                            </tbody>
                        </table>
                        <span class="no_subscriptions_to_show"><fmt:message key="my_subscriptions.no_subscriptions_to_show"/></span>
                    </c:otherwise>
                </c:choose>
        </div>
    </div>
    <script src="${pageContext.request.contextPath}/js/my_subscriptions.js"></script>
</body>
</html>