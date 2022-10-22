<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="ct" tagdir="/WEB-INF/tags" %>

<html lang="${sessionScope.locale.getShortNameId()}">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>PressReader | Edit Periodical</title>
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/img/tab-icon.ico">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/materialize/css/materialize.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/new_periodical.css">
</head>
<body>
<%@ include file="fragment/Header.jspf" %>
<div class="content">
    <div class="content_topper">
        <a href="controller?cmd=PERIODICALS_PAGE" class="periodicals_page_link">
            <fmt:message key="new_periodical.periodicals"/>
        </a>
        <i class="small material-icons" style="margin: 0 10px; opacity: 70%;">navigate_next</i>
        <a style="color: #27c18a; font-weight: 500;"><fmt:message key="edit_periodical.edit_periodical"/></a>
    </div>
    <div class="content_main">
        <div class="content_main_top_text">
            <fmt:message key="edit_periodical.header"/>
        </div>
        <div class="msg_container">
            <div class="common_msg_block alert" id="alert_block">
                <i class="material-icons">error</i>
                <div>
                    <span class="new_periodical_alerts" id="try_later">
                        <fmt:message key="new_periodical.alert.try_later"/>
                    </span>
                    <span class="new_periodical_alerts" id="image_to_big">
                        <fmt:message key="new_periodical.alert.big_image"/>
                    </span>
                    <span class="new_periodical_alerts" id="make_changes">
                        <fmt:message key="new_periodical.alert.make_changes"/>
                    </span>
                    <span class="new_periodical_alerts" id="fill_all">
                        <fmt:message key="new_periodical.alert.fill_all"/>
                    </span>
                    <span class="new_periodical_alerts" id="have_periodical">
                        <fmt:message key="new_periodical.alert.have_periodical"/>
                    </span>
                    <span class="new_periodical_alerts" id="no_topic">
                        <fmt:message key="new_periodical.alert.no_topic"/>
                    </span>
                    <span class="new_periodical_alerts" id="not_zero">
                        <fmt:message key="new_periodical.alert.not_zero"/>
                    </span>
                    <span class="new_periodical_alerts" id="empty_release">
                        <fmt:message key="new_periodical.alert.empty_release"/>
                    </span>
                </div>
            </div>
            <div class="common_msg_block success" id="success_block">
                <i class="material-icons">check_circle</i>
                <div>
                    <span class="new_periodical_alerts" id="success">
                        <fmt:message key="edit_periodical.success.edited"/>
                    </span>
                </div>
            </div>
        </div>
        <div class="periodical_info_container">
            <div class="periodical_info_row">
                <div class="periodical_info_block">
                    <div class="info_block_header"><fmt:message key="new_periodical.title_image"/></div>
                    <div class="title_img_container" style="margin-top: 30px;">
                        <div class="please_upload_image"><fmt:message key="new_periodical.please_upload"/></div>
                        <img class="title_img" id="title_img"
                             src="<ct:imageEncoderBase64 image_name="${requestScope.periodical.getTitleImgLink()}"/>">
                        <div class="edit_image_block" onclick="activate_file_input_window()">
                            <i class="material-icons small" style="color: white;">edit</i>
                        </div>
                    </div>
                    <form id="upload" style="display: none;">
                        <input type="file" id="file" accept="image/png, image/jpeg">
                    </form>
                    <div class="title_image_recommend" style="margin-top: 20px;">
                        <p><fmt:message key="new_periodical.resolution"/></p>
                        <p><fmt:message key="new_periodical.file_size"/></p>
                    </div>
                </div>
                <div>
                    <div class="periodical_info_block">
                        <div class="info_block_header"><fmt:message key="new_periodical.title"/></div>
                        <div class="row">
                            <div class="input-field col s12">
                                <input id="title_input" type="text" spellcheck="false" autocomplete="off"
                                       value="${requestScope.periodical.getTitle()}">
                                <label for="title_input"><fmt:message key="new_periodical.periodical_name"/></label>
                            </div>
                        </div>
                    </div>
                    <div class="periodical_info_block">
                        <div>
                            <div class="info_block_header" style="width: 300px;"><fmt:message key="new_periodical.topic"/></div>
                            <div class="show_all_topics_field" onclick="open_topic_modal()">
                                <c:if test="${requestScope.periodical.getTopicID() < 1}">
                                    <span id="show_all_topics_text" topicId="0">
                                        <fmt:message key="periodicals.no_topic_name"/>
                                    </span>
                                </c:if>
                                <c:if test="${requestScope.periodical.getTopicID() > 0}">
                                    <c:forEach var="topic" items="${requestScope.topics}">
                                        <c:if test="${topic.getId() == requestScope.periodical.getTopicID()}">
                                            <c:set var="topicName" scope="page"
                                                value="${topic.getAllTranslates().values().iterator().next().getName()}"/>
                                        </c:if>
                                    </c:forEach>
                                    <span id="show_all_topics_text" topicId="${requestScope.periodical.getTopicID()}">
                                            ${topicName}
                                    </span>
                                </c:if>
                                <c:if test="${requestScope.topics.size() > 0}">
                                    <span id="select_one_topic_text" style="display: none;"><fmt:message key="home.choose_one"/></span>
                                </c:if>
                                <i class="material-icons topic" id="topic_modal_icon">expand_more</i>
                            </div>
                            <div class="topic_modal" id="topic_modal">
                                <ul>
                                    <c:forEach var="topic" items="${requestScope.topics}">
                                        <c:set var="topicId" scope="page"
                                               value="${topic.getId()}"/>
                                        <c:set var="topicName" scope="page"
                                               value="${topic.getAllTranslates().values().iterator().next().getName()}"/>
                                        <li topicId="${topicId}" onclick="setTopic('${topicName}', ${topicId})">${topicName}</li>
                                    </c:forEach>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="periodical_info_block">
                        <div class="info_block_header" style="width: 300px;"><fmt:message key="new_periodical.frequency"/></div>
                        <div style="display: flex; align-items: center; justify-content: center; padding-top: 5px;">
                            <div class="month_amount">
                                <div class="month_amount_btn" style="border-radius: 10px 10px 0 0;" onclick="up_frequency()">
                                    <i class="material-icons" style="color: white;">arrow_drop_up</i>
                                </div>
                                <div class="month_amount_digit" id="frequency_digit">
                                    ${requestScope.periodical.getFrequency().get("amount")}
                                </div>
                                <div class="month_amount_btn" style="border-radius: 0 0 10px 10px;" onclick="down_frequency()">
                                    <i class="material-icons" style="color: white;">arrow_drop_down</i>
                                </div>
                            </div>
                            <div style="width: 45px; text-align: center; color: rgb(121, 121, 121); font-size: 14pt; font-weight: 300;">
                                <fmt:message key="new_periodical.per"/>
                            </div>
                            <div class="show_frequency_field" onclick="open_frequency_modal()">
                                <span id="selected_frequency_text"
                                      period="${requestScope.periodical.getFrequency().get("period")}">
                                    <fmt:message key="new_periodical.frequency_${requestScope
                                                    .periodical.getFrequency().get('period')}"/>
                                </span>
                                <i class="material-icons frequency" id="frequency_modal_icon">expand_more</i>
                            </div>
                            <div class="frequency_modal" id="frequency_modal">
                                <ul>
                                    <li onclick="setFrequency('<fmt:message key="new_periodical.frequency_week"/>', 'week')">
                                        <fmt:message key="new_periodical.frequency_week"/>
                                    </li>
                                    <li onclick="setFrequency('<fmt:message key="new_periodical.frequency_month"/>', 'month')">
                                        <fmt:message key="new_periodical.frequency_month"/>
                                    </li>
                                    <li onclick="setFrequency('<fmt:message key="new_periodical.frequency_year"/>', 'year')">
                                        <fmt:message key="new_periodical.frequency_year"/>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
                <div>
                    <div class="periodical_info_block">
                        <div class="info_block_header"><fmt:message key="new_periodical.subscription_period"/></div>
                        <div style="display: flex; align-items: center; justify-content: center; padding-top: 5px;">
                            <div class="month_amount">
                                <div class="month_amount_btn" style="border-radius: 10px 10px 0 0;" onclick="up_period()">
                                    <i class="material-icons" style="color: white;">arrow_drop_up</i>
                                </div>
                                <div class="month_amount_digit" id="period_digit">
                                    ${requestScope.periodical.getSubPeriod()}
                                </div>
                                <div class="month_amount_btn" style="border-radius: 0 0 10px 10px;" onclick="down_period()">
                                    <i class="material-icons" style="color: white;">arrow_drop_down</i>
                                </div>
                            </div>
                            <div style="margin: 0 10px; color: rgb(121, 121, 121); font-size: 14pt; font-weight: 300;">
                                <fmt:message key="new_periodical.subscription_period_month"/></div>
                        </div>
                    </div>
                    <div class="periodical_info_block">
                        <div class="info_block_header"><fmt:message key="new_periodical.price"/>
                            <span style="font-size: 10.5pt; margin-left: 4px; margin-top: 7px;"><fmt:message key="new_periodical.price_addition"/></span>
                        </div>
                        <div class="row">
                            <c:set var="price" scope="page"
                                   value="${MoneyFormatter.toHumanReadable(requestScope.periodical.getPrice())}"/>
                            <div class="input-field col s12" style="display: flex; flex-direction:row; align-items: center; justify-content: center;">
                                <input id="hryvnias" type="text" style="width: 65px; text-align: right;"
                                       value="${price.split(',')[0]}"
                                       maxlength="5" autocomplete="off">
                                <span style="font-size: 16pt; margin: 3px; color: rgba(0, 0, 0, 0.555);">,</span>
                                <input id="kopecks" type="text" style="width: 30px; text-align: center;"
                                       value="${price.split(',')[1]}"
                                       maxlength="2" autocomplete="off">
                                <span style="font-size: 14pt; font-weight: 300; color: rgba(0, 0, 0, 0.555); margin-bottom: 4px; margin-left: 5px;">${sessionScope.locale.getCurrency()}</span>
                            </div>
                        </div>
                    </div>
                    <div class="periodical_info_block">
                        <div class="info_block_header"><fmt:message key="new_periodical.status"/></div>
                        <div class="switch" style="margin-top: 34px;">
                            <label>
                                <fmt:message key="new_periodical.status_disabled"/>
                                <c:if test="${requestScope.periodical.isActive() == true }">
                                    <input type="checkbox" id="switch_block" checked>
                                </c:if>
                                <c:if test="${requestScope.periodical.isActive() == false }">
                                    <input type="checkbox" id="switch_block">
                                </c:if>
                                <span class="lever"></span>
                                <fmt:message key="new_periodical.status_active"/>
                            </label>
                        </div>
                    </div>
                </div>
            </div>
            <div class="periodical_info_row" style="margin-top: 40px;">
                <div class="periodical_info_block" style="min-width: 800px;">
                    <div class="info_block_header"><fmt:message key="new_periodical.release"/></div>
                    <div id="month_selector_container">
                        <ct:monthSelectorPerEdit calendar="${requestScope.periodical.getReleaseCalendar()}"/>
                    </div>
                    <button onclick="addNextYear()" id="add_year_btn">
                        <fmt:message key="new_periodical.release.add_year"/>
                    </button>
                    <button onclick="removeLastYear()" id="remove_year_btn" style="display: none;">
                        <fmt:message key="new_periodical.release.remove_last"/>
                    </button>
                </div>
            </div>
            <div class="periodical_info_row" style="margin-top: 40px;">
                <div class="periodical_info_block" style="min-width: 250px;">
                    <div class="info_block_header"><fmt:message key="new_periodical.publishing_country"/></div>
                    <c:forEach var="locale" items="${applicationScope.locales.values()}">
                        <c:set var="locale_id" scope="page" value="${locale.getShortNameId()}"/>
                        <div class="row">
                            <div class="input-field col s12">
                                <input class="publishing_county" id="country_input_${locale_id}"
                                        lang="${locale_id}" type="text"
                                        <c:set var="translation"
                                               scope="request"
                                               value="${requestScope.periodical.getTranslation().get(locale_id)}"/>
                                        <c:if test="${translation != null}">
                                            value="${requestScope.periodical.getTranslation().get(locale_id).getCountry()}"
                                        </c:if>
                                        <c:if test="${translation == null}">
                                            value=""
                                        </c:if>
                                       spellcheck="false" autocomplete="off">
                                <label for="country_input_${locale_id}">
                                    <fmt:message key="new_periodical.input_lang"/>
                                    <span class="lang_name">${locale.getLangNameOriginal()}</span>
                                </label>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <div class="periodical_info_block" style="min-width: 250px;">
                    <div class="info_block_header"><fmt:message key="new_periodical.publishing_language"/></div>
                    <c:forEach var="locale" items="${applicationScope.locales.values()}">
                        <c:set var="locale_id" scope="page" value="${locale.getShortNameId()}"/>
                        <div class="row">
                            <div class="input-field col s12">
                                <input class="publishing_language" id="lang_input_${locale_id}"
                                        lang="${locale_id}" type="text"
                                        <c:set var="translation"
                                            scope="request"
                                            value="${requestScope.periodical.getTranslation().get(locale_id)}"/>
                                        <c:if test="${translation != null}">
                                            value="${requestScope.periodical.getTranslation().get(locale_id).getLanguage()}"
                                        </c:if>
                                        <c:if test="${translation == null}">
                                            value=""
                                        </c:if>
                                       spellcheck="false" autocomplete="off">
                                <label for="lang_input_${locale_id}">
                                    <fmt:message key="new_periodical.input_lang"/>
                                    <span class="lang_name">${locale.getLangNameOriginal()}</span>
                                </label>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <div class="periodical_info_block" style="width: 470px;">
                    <div class="info_block_header"><fmt:message key="new_periodical.publishing_description"/></div>
                    <c:forEach var="locale" items="${applicationScope.locales.values()}">
                        <c:set var="locale_id" scope="page" value="${locale.getShortNameId()}"/>
                        <div class="row">
                            <div class="input-field col s12">
                                <c:if test="${requestScope.periodical.getTranslation().get(locale_id) != null}">
                                    <textarea class="materialize-textarea" id="desc_input_${locale_id}"
                                              lang="${locale_id}" type="text" spellcheck="false" autocomplete="off"
                                    >${requestScope.periodical.getTranslation().get(locale_id).getDescription()}</textarea>
                                </c:if>
                                <c:if test="${requestScope.periodical.getTranslation().get(locale_id) == null}">
                                    <textarea class="materialize-textarea" id="desc_input_${locale_id}"
                                              lang="${locale_id}" type="text" spellcheck="false" autocomplete="off"></textarea>
                                </c:if>
                                <label for="desc_input_${locale_id}">
                                    <fmt:message key="new_periodical.input_lang"/>
                                    <span class="lang_name">${locale.getLangNameOriginal()}</span>
                                </label>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
        <button class="create_periodical_button"
                onclick="createEditPeriodical(${requestScope.periodical.getId()})">
            <fmt:message key="edit_periodical.edit_periodical_btn"/>
        </button>
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/create_edit_periodical.js"></script>
<script src="${pageContext.request.contextPath}/materialize/js/materialize.min.js"></script>
</body>
</html>