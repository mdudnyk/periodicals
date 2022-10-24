<%@ tag import="com.periodicals.entity.MonthSelector" %>
<%@ tag import="org.json.simple.JSONArray" %>
<%@ tag import="java.time.LocalDate" %>
<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value = "${sessionScope.locale.getShortNameId()}"/>
<fmt:setBundle basename="messages"/>

<%@ attribute name="calendar" required="true"
              type="java.util.Map<java.lang.Integer, com.periodicals.entity.MonthSelector>"%>

<%
    String[] monthNames = {"jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec"};

    LocalDate today = LocalDate.now();
    int currentMonth = today.getMonthValue();
    int currentYear = today.getYear();

    for (MonthSelector m : calendar.values()) {
        JSONArray checkedMonth = m.getMonth();
%>
<div class="month_selector">
    <span class="month_selector_year" style="margin-right: 10px;"><%=m.getYear()%></span>
    <form action="#" class="month_form" style="display: flex;" year="<%=m.getYear()%>">
        <%
            for (int i = 0; i < 12; i++) {
                boolean isChecked = (boolean) checkedMonth.get(i);
                request.setAttribute("month" , monthNames[i]);
        %>
        <p class="checkbox_block">
            <fmt:message key="periodical.${month}"/>
            <label class="checkbox_label">
                <input type="checkbox" onchange="countTotalPrice()" class="filled-in checkbox-green" <%
                    if (m.getYear() == currentYear && i < currentMonth) {
                        %>disabled<%
                    } else if (!isChecked) {
                        %>disabled<%
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