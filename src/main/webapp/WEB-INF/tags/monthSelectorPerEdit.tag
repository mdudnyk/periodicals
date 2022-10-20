<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value = "${sessionScope.locale.getShortNameId()}"/>
<fmt:setBundle basename="messages"/>

<%@ attribute name="calendar" required="true" type="java.util.Map"%>

<div class="month_selector">
    <c:set var = "now" value = "<%=new java.util.Date()%>" />
    <span class="month_selector_year" style="margin-right: 10px;">
                                <fmt:formatDate pattern="yyyy" value="${now}"/>
                            </span>
    <form action="#" class="month_form" style="display: flex;"
          year="<fmt:formatDate pattern="yyyy" value="${now}"/>">
        <p class="checkbox_block">
            <fmt:message key="periodical.jan"/>
            <label class="checkbox_label">
                <input type="checkbox" class="filled-in checkbox-green"/>
                <span></span>
            </label>
        </p>
        <p class="checkbox_block">
            <fmt:message key="periodical.feb"/>
            <label class="checkbox_label">
                <input type="checkbox" class="filled-in checkbox-green"/>
                <span></span>
            </label>
        </p>
        <p class="checkbox_block">
            <fmt:message key="periodical.mar"/>
            <label class="checkbox_label">
                <input type="checkbox" class="filled-in checkbox-green"/>
                <span></span>
            </label>
        </p>
        <p class="checkbox_block">
            <fmt:message key="periodical.apr"/>
            <label class="checkbox_label">
                <input type="checkbox" class="filled-in checkbox-green"/>
                <span></span>
            </label>
        </p>
        <p class="checkbox_block">
            <fmt:message key="periodical.may"/>
            <label class="checkbox_label">
                <input type="checkbox" class="filled-in checkbox-green"/>
                <span></span>
            </label>
        </p>
        <p class="checkbox_block">
            <fmt:message key="periodical.jun"/>
            <label class="checkbox_label">
                <input type="checkbox" class="filled-in checkbox-green"/>
                <span></span>
            </label>
        </p>
        <p class="checkbox_block">
            <fmt:message key="periodical.jul"/>
            <label class="checkbox_label">
                <input type="checkbox" class="filled-in checkbox-green"/>
                <span></span>
            </label>
        </p>
        <p class="checkbox_block">
            <fmt:message key="periodical.aug"/>
            <label class="checkbox_label">
                <input type="checkbox" class="filled-in checkbox-green"/>
                <span></span>
            </label>
        </p>
        <p class="checkbox_block">
            <fmt:message key="periodical.sep"/>
            <label class="checkbox_label">
                <input type="checkbox" class="filled-in checkbox-green"/>
                <span></span>
            </label>
        </p>
        <p class="checkbox_block">
            <fmt:message key="periodical.oct"/>
            <label class="checkbox_label">
                <input type="checkbox" class="filled-in checkbox-green"/>
                <span></span>
            </label>
        </p>
        <p class="checkbox_block">
            <fmt:message key="periodical.nov"/>
            <label class="checkbox_label">
                <input type="checkbox" class="filled-in checkbox-green"/>
                <span></span>
            </label>
        </p>
        <p class="checkbox_block">
            <fmt:message key="periodical.dec"/>
            <label class="checkbox_label">
                <input type="checkbox" class="filled-in checkbox-green"/>
                <span></span>
            </label>
        </p>
    </form>
</div>