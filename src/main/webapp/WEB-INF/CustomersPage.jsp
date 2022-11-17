<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ct" tagdir="/WEB-INF/tags" %>

<html lang="${sessionScope.locale.getShortNameId()}">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>PressReader | Customers</title>
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/img/tab-icon.ico">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/materialize/css/materialize.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/customers.css">
</head>
<body>
<%@ include file="fragment/Header.jspf" %>

<div class="content">
    <div class="content_topper">
        <div class="content_topper_title"><fmt:message key="customers.customers"/></div>
        <div style="display: flex; align-items: flex-end">
            <div class="content_topper_qty"><fmt:message key="periodicals.total"/>: ${requestScope.customersTotal}</div>
        </div>
    </div>
    <div class="content_main">
        <div class="above_table_block">
            <div class="amount_manage_block" onclick="open_amount_modal()">
                <c:set var="amountOnPage" scope="request" value="${sessionScope.customersAmountOnPage}"/>
                <div class="amount_number">${amountOnPage}</div>
                <i class="material-icons" id="amount_modal_icon">expand_more</i>
                <div class="amount_modal" id="amount_modal">
                    <ul>
                        <c:if test="${amountOnPage != 5}">
                            <a href="${pageContext.request.contextPath}/controller?cmd=CUSTOMERS_PAGE&amount=5">
                                <li>5</li>
                            </a>
                        </c:if>
                        <c:if test="${amountOnPage != 10}">
                            <a href="${pageContext.request.contextPath}/controller?cmd=CUSTOMERS_PAGE&amount=10">
                                <li>10</li>
                            </a>
                        </c:if>
                        <c:if test="${amountOnPage != 15}">
                            <a href="${pageContext.request.contextPath}/controller?cmd=CUSTOMERS_PAGE&amount=15">
                                <li>15</li>
                            </a>
                        </c:if>
                    </ul>
                </div>
            </div>
            <div class="close_search_block">
                <c:if test="${sessionScope.customersSearchString.length() > 0}">
                        <form style="z-index: 1;" action="${pageContext.request.contextPath}/controller">
                            <input type="hidden" name="cmd" value="CUSTOMERS_PAGE">
                            <input type="hidden" name="searchString" value="" spellcheck="false" autocomplete="off">
                            <button type="submit" class="close_search_btn">
                                <fmt:message key="periodicals.end_search"/>
                            </button>
                        </form>
                    </c:if>
            </div>
            <div class="search_block_customer">
                <form action="${pageContext.request.contextPath}/controller">
                    <input type="hidden" name="cmd" value="CUSTOMERS_PAGE">
                    <input type="text" name="searchString" value="${sessionScope.customersSearchString}"
                           style="width: 200px;" placeholder="<fmt:message key="customers.search_customer"/>"
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
                <c:set var="sortBy" scope="request" value="${sessionScope.customersSortBy}"/>
                <c:set var="sortOrder" scope="request" value="${sessionScope.customersSortOrder}"/>
                <th style="width: 70px; padding-left: 20px;"><fmt:message key="topics.number"/></th>
                <th>
                    <div class="sortable_column" style="width: 300px; justify-content: flex-start; padding-left: 20px;"><fmt:message key="customers.name"/>
                        <div class="sorting_block">
                            <c:choose>
                                <c:when test="${sortBy.equals('name')}">
                                    <c:choose>
                                        <c:when test="${sortOrder.equals('ASC')}">
                                            <a href="${pageContext.request.contextPath}/controller?cmd=CUSTOMERS_PAGE&sortBy=name&sorting=DESC">
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
                                            <a href="${pageContext.request.contextPath}/controller?cmd=CUSTOMERS_PAGE&sortBy=name&sorting=ASC">
                                                <i class="material-icons sort">arrow_drop_down</i>
                                            </a>
                                        </c:otherwise>
                                    </c:choose>
                                </c:when>
                                <c:otherwise>
                                    <a href="${pageContext.request.contextPath}/controller?cmd=CUSTOMERS_PAGE&sortBy=name&sorting=DESC">
                                        <i class="material-icons sort">arrow_drop_up</i>
                                    </a>
                                    <a href="${pageContext.request.contextPath}/controller?cmd=CUSTOMERS_PAGE&sortBy=name&sorting=ASC">
                                        <i class="material-icons sort">arrow_drop_down</i>
                                    </a>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </th>
                <th>
                    <div class="sortable_column" style="width: 350px; justify-content: flex-start; padding-left: 20px;"><fmt:message key="customers.email"/>
                        <div class="sorting_block">
                            <c:choose>
                                <c:when test="${sortBy.equals('email')}">
                                    <c:choose>
                                        <c:when test="${sortOrder.equals('ASC')}">
                                            <a href="${pageContext.request.contextPath}/controller?cmd=CUSTOMERS_PAGE&sortBy=email&sorting=DESC">
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
                                            <a href="${pageContext.request.contextPath}/controller?cmd=CUSTOMERS_PAGE&sortBy=email&sorting=ASC">
                                                <i class="material-icons sort">arrow_drop_down</i>
                                            </a>
                                        </c:otherwise>
                                    </c:choose>
                                </c:when>
                                <c:otherwise>
                                    <a href="${pageContext.request.contextPath}/controller?cmd=CUSTOMERS_PAGE&sortBy=email&sorting=DESC">
                                        <i class="material-icons sort">arrow_drop_up</i>
                                    </a>
                                    <a href="${pageContext.request.contextPath}/controller?cmd=CUSTOMERS_PAGE&sortBy=email&sorting=ASC">
                                        <i class="material-icons sort">arrow_drop_down</i>
                                    </a>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </th>
                <th>
                    <div class="sortable_column">ID
                        <div class="sorting_block">
                            <c:choose>
                                <c:when test="${sortBy.equals('id')}">
                                    <c:choose>
                                        <c:when test="${sortOrder.equals('ASC')}">
                                            <a href="${pageContext.request.contextPath}/controller?cmd=CUSTOMERS_PAGE&sortBy=id&sorting=DESC">
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
                                            <a href="${pageContext.request.contextPath}/controller?cmd=CUSTOMERS_PAGE&sortBy=id&sorting=ASC">
                                                <i class="material-icons sort">arrow_drop_down</i>
                                            </a>
                                        </c:otherwise>
                                    </c:choose>
                                </c:when>
                                <c:otherwise>
                                    <a href="${pageContext.request.contextPath}/controller?cmd=CUSTOMERS_PAGE&sortBy=id&sorting=DESC">
                                        <i class="material-icons sort">arrow_drop_up</i>
                                    </a>
                                    <a href="${pageContext.request.contextPath}/controller?cmd=CUSTOMERS_PAGE&sortBy=id&sorting=ASC">
                                        <i class="material-icons sort">arrow_drop_down</i>
                                    </a>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </th>
                <th>
                    <div class="sortable_column"><fmt:message key="customers.balance"/>
                        <div class="sorting_block">
                            <c:choose>
                                <c:when test="${sortBy.equals('balance')}">
                                    <c:choose>
                                        <c:when test="${sortOrder.equals('ASC')}">
                                            <a href="${pageContext.request.contextPath}/controller?cmd=CUSTOMERS_PAGE&sortBy=balance&sorting=DESC">
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
                                            <a href="${pageContext.request.contextPath}/controller?cmd=CUSTOMERS_PAGE&sortBy=balance&sorting=ASC">
                                                <i class="material-icons sort">arrow_drop_down</i>
                                            </a>
                                        </c:otherwise>
                                    </c:choose>
                                </c:when>
                                <c:otherwise>
                                    <a href="${pageContext.request.contextPath}/controller?cmd=CUSTOMERS_PAGE&sortBy=balance&sorting=DESC">
                                        <i class="material-icons sort">arrow_drop_up</i>
                                    </a>
                                    <a href="${pageContext.request.contextPath}/controller?cmd=CUSTOMERS_PAGE&sortBy=balance&sorting=ASC">
                                        <i class="material-icons sort">arrow_drop_down</i>
                                    </a>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </th>
                <th>
                    <div class="sortable_column" style="width: 160px;"><fmt:message key="customers.status"/></div>
                </th>
            </tr>
            </thead>
            <tbody>
            <c:choose>
                <c:when test="${requestScope.customers != null && !requestScope.customers.isEmpty()}">
                    <c:set var="pageNumber" scope="request" value="${sessionScope.customersPageNumber}"/>
                    <c:set var="rowIndex" scope="request" value="${pageNumber * amountOnPage - amountOnPage}"/>
                    <c:forEach var="customer" items="${requestScope.customers}">
                        <c:set var="rowIndex" scope="request" value="${rowIndex + 1}"/>
                        <tr>
                            <td style="padding-left: 20px;">${rowIndex}</td>
                            <td class="customer_column">${customer.getFirstname()} ${customer.getLastname()}</td>
                            <td class="customer_column">${customer.getEmail()}</td>
                            <td>${customer.getId()}</td>
                            <td>${MoneyFormatter.toHumanReadable(customer.getBalance())}</td>
                            <td>
                                <div class="switch">
                                    <label>
                                        <fmt:message key="customers.blocked"/>
                                        <c:if test="${!customer.isBlocked()}">
                                            <input type="checkbox" onchange="changeStatus(${customer.getId()})"
                                                   id="switch_${customer.getId()}" checked>
                                        </c:if>
                                        <c:if test="${customer.isBlocked()}">
                                            <input type="checkbox" onchange="changeStatus(${customer.getId()})"
                                               id="switch_${customer.getId()}">
                                        </c:if>
                                        <span class="lever"></span>
                                        <fmt:message key="customers.active"/>
                                    </label>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <div class="under_table_block">
                <ct:paginationBar page="${pageNumber}"
                                  total="${requestScope.totalPages}"
                                  command="CUSTOMERS_PAGE"/>
            </div>
                </c:when>
                <c:otherwise>
                </tbody>
            </table>
             <span class="no_customers_to_show"><fmt:message key="customers.no_customers_to_show"/></span>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/customers.js"></script>
</body>
</html>