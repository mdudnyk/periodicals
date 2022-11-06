<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ct" tagdir="/WEB-INF/tags" %>

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
                    <fmt:message key="my_subscriptions.total"/>8</div>
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
                    <c:if test="${sessionScope.subscriptionsSearchString.length() > 0}">
                        <form style="z-index: 1;" action="${pageContext.request.contextPath}/controller">
                            <input type="hidden" name="cmd" value="MY_SUBSCRIPTIONS_PAGE">
                            <input type="hidden" name="searchString" value="" spellcheck="false" autocomplete="off">
                            <button type="submit" class="close_search_btn">
                                <fmt:message key="my_subscriptions.end_search"/>
                            </button>
                        </form>
                    </c:if>
                </div>
                <div class="search_block_subscription">
                    <form action="${pageContext.request.contextPath}/controller">
                        <input type="hidden" name="cmd" value="MY_SUBSCRIPTIONS_PAGE">
                        <input type="text" name="searchString"
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
                        <th style="width: 70px; padding-left: 20px;">#</th>
                        <th>
                            <div class="sortable_column" style="width: 250px;">
                                <fmt:message key="my_subscriptions.periodical_title"/>
                                <div class="sorting_block">
                                    <i class="material-icons sort disabled">arrow_drop_up</i>
                                    <i class="material-icons sort">arrow_drop_down</i>
                                </div>
                            </div>
                        </th>
                        <th>
                            <div class="sortable_column">
                                <fmt:message key="my_subscriptions.subscription_date"/>
                                <div class="sorting_block">
                                    <i class="material-icons sort disabled">arrow_drop_up</i>
                                    <i class="material-icons sort">arrow_drop_down</i>
                                </div>
                            </div>
                        </th>
                        <th>
                            <div class="sortable_column">
                                <fmt:message key="my_subscriptions.expiration_date"/>
                                <div class="sorting_block">
                                    <i class="material-icons sort disabled">arrow_drop_up</i>
                                    <i class="material-icons sort">arrow_drop_down</i>
                                </div>
                            </div>
                        </th>
                        <th>
                            <div class="sortable_column" style="width: 160px;">
                                <fmt:message key="my_subscriptions.total_cost"/>
                                <div class="sorting_block">
                                    <i class="material-icons sort disabled">arrow_drop_up</i>
                                    <i class="material-icons sort">arrow_drop_down</i>
                                </div>
                            </div>
                        </th>
                        <th>
                            <div class="sortable_column" style="width: 90px;">
                                <fmt:message key="my_subscriptions.status"/>
                                <div class="sorting_block">
                                    <i class="material-icons sort disabled">arrow_drop_up</i>
                                    <i class="material-icons sort">arrow_drop_down</i>
                                </div>
                            </div>
                        </th>
                        <th style="width: 80px;"><fmt:message key="my_subscriptions.details"/></th>
                        <th style="width: 80px;"><fmt:message key="my_subscriptions.delete"/></th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td style="padding-left: 20px;">1</td>
                        <td class="subscription_column" style="font-weight: 700;">Top gear</td>
                        <td class="subscription_column">09/2022</td>
                        <td class="subscription_column">08/2023</td>
                        <td>465.50</td>
                        <td style="font-size: 11pt; color: rgb(255, 117, 117); font-weight: 700;">ACTIVE</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/controller?cmd=SHOW_SUBSCRIPTION_DETAILS&id=4">
                                <i class="material-icons edit">zoom_in</i>
                            </a>
                        </td>
                        <td>
                            <i class="material-icons delete" onclick="deleteSubscriptionById(4)">delete_forever</i>
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
        </div>
    </div>
    <script src="${pageContext.request.contextPath}/js/my_subscriptions.js"></script>
</body>
</html>
