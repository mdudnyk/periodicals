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
        <div class="content_topper_title">Customers</div>
        <div style="display: flex; align-items: flex-end">
            <div class="content_topper_qty">total quantity: 8</div>
        </div>
    </div>
    <div class="content_main">
        <div class="above_table_block">
            <div class="amount_manage_block" onclick="open_amount_modal()">
                <div class="amount_number">5</div>
                <i class="material-icons" id="amount_modal_icon">expand_more</i>
                <div class="amount_modal" id="amount_modal">
                    <ul>
                        <a href="#">
                            <li>15</li>
                        </a>
                        <a href="#">
                            <li>25</li>
                        </a>
                    </ul>
                </div>
            </div>
            <div class="close_search_block">
                <c:if test="${sessionScope.CustomersSearchString.length() > 0}">
                        <form style="z-index: 1;" action="${pageContext.request.contextPath}/controller">
                            <input type="hidden" name="cmd" value="CUSTOMERS_PAGE">
                            <input type="hidden" name="searchString" value="" spellcheck="false" autocomplete="off">
                            <button type="submit" class="close_search_btn">
                                End Search
                            </button>
                        </form>
                    </c:if>
            </div>
            <div class="search_block_customer">
                <form action="${pageContext.request.contextPath}/controller">
                    <input type="hidden" name="cmd" value="CUSTOMERS_PAGE">
                    <input type="text" name="searchString"
                           style="width: 200px;" placeholder="Search customer"
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
                <th style="width: 70px; padding-left: 20px;">#</th>
                <th>
                    <div class="sortable_column" style="width: 300px; justify-content: flex-start; padding-left: 20px;">Customer name
                        <div class="sorting_block">
                            <i class="material-icons sort disabled">arrow_drop_up</i>
                            <i class="material-icons sort">arrow_drop_down</i>
                        </div>
                    </div>
                </th>
                <th>
                    <div class="sortable_column" style="width: 350px; justify-content: flex-start; padding-left: 20px;">E-mail
                        <div class="sorting_block">
                            <i class="material-icons sort disabled">arrow_drop_up</i>
                            <i class="material-icons sort">arrow_drop_down</i>
                        </div>
                    </div>
                </th>
                <th>
                    <div class="sortable_column">ID
                        <div class="sorting_block">
                            <i class="material-icons sort disabled">arrow_drop_up</i>
                            <i class="material-icons sort">arrow_drop_down</i>
                        </div>
                    </div>
                </th>
                <th>
                    <div class="sortable_column">Balance, uah
                        <div class="sorting_block">
                            <i class="material-icons sort disabled">arrow_drop_up</i>
                            <i class="material-icons sort">arrow_drop_down</i>
                        </div>
                    </div>
                </th>
                <th>
                    <div class="sortable_column" style="width: 160px;">Status</div>
                </th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td style="padding-left: 20px;">1</td>
                <td class="customer_column">Myroslav Dudnyk</td>
                <td class="customer_column">mdundyk.sps@gmail.com</td>
                <td>10</td>
                <td>465.50</td>
                <td>
                    <div class="switch">
                        <label>
                            blocked
                            <input type="checkbox" onchange="changeStatus(1)" id="switch_1" checked>
                            <span class="lever"></span>
                            active
                        </label>
                    </div>
                </td>
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
        </table>
<%--         <span class="no_customers_to_show"><fmt:message key="customers.no_customers_to_show"/></span> --%>
    </div>
</div>


<script src="${pageContext.request.contextPath}/js/customers.js"></script>
</body>
</html>