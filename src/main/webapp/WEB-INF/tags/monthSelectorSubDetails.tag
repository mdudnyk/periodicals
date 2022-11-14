<%@ tag import="com.periodicals.entity.MonthSelector" %>
<%@ tag import="com.periodicals.util.DateFormatter" %>
<%@ tag import="org.json.simple.JSONArray" %>
<%@ tag import="com.periodicals.entity.LocaleCustom" %>
<%@ tag import="java.time.LocalDate" %>
<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value = "${sessionScope.locale.getShortNameId()}"/>
<fmt:setBundle basename="messages"/>

<%@ attribute name="calendar" required="true"
              type="java.util.Map<java.lang.Integer, com.periodicals.entity.MonthSelector>"%>

<%
    LocalDate today = LocalDate.now();
    int currentMonth = today.getMonthValue();
    int currentYear = today.getYear();
    LocaleCustom locale = (LocaleCustom) session.getAttribute("locale");

    for (MonthSelector m : calendar.values()) {
        JSONArray monthArray = m.getMonth();
%>
<div class="month_selector">
    <span class="month_selector_year" style="margin-right: 10px;"><%=m.getYear()%></span>
    <form action="#" class="month_form" style="display: flex;">
        <%
            for (int i = 0; i < 12; i++) {
                boolean isChecked = (boolean) monthArray.get(i);
        %>
        <p class="checkbox_block">
        <%=
            DateFormatter.getShortMonthName(i + 1, locale.getShortNameId())
        %>
            <label class="checkbox_label">
                <input type="checkbox" onclick="return false" class="filled-in checkbox-green" <%
                    if (m.getYear() == currentYear && i < currentMonth) {
                        %>disabled<%
                    }
                    if (isChecked) {
                        %>checked<%
                    }%>/>
                <span></span>
            </label>
        </p>
        <%
            }
        %>
    </form>
</div>
<%
    }
%>