<%@ tag import="java.time.Year" %>
<%@ tag import="com.periodicals.entity.MonthSelector" %>
<%@ tag import="org.json.simple.JSONArray" %>
<%@ tag import="com.periodicals.entity.LocaleCustom" %>
<%@ tag import="com.periodicals.util.DateFormatter" %>
<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value = "${sessionScope.locale.getShortNameId()}"/>
<fmt:setBundle basename="messages"/>

<%@ attribute name="calendar" required="true"
              type="java.util.Map<java.lang.Integer, com.periodicals.entity.MonthSelector>"%>

<%
    String[] monthNames = {"jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec"};
    int currentYear = Year.now().getValue();
    LocaleCustom locale = (LocaleCustom) session.getAttribute("locale");

    if (calendar.get(currentYear) != null) {
        for (MonthSelector m : calendar.values()) {
            if (m.getYear() >= currentYear) {
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
                    <%=
                        DateFormatter.getShortMonthName(i + 1, locale.getShortNameId())
                    %>
                    <label class="checkbox_label">
                        <input type="checkbox" class="filled-in checkbox-green"
                               <% if (isChecked) {%>checked<%} %>/>
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
        }
    } else {
            %>
            <div class="month_selector">
                <span class="month_selector_year" style="margin-right: 10px;"><%=currentYear%></span>
                <form action="#" class="month_form" style="display: flex;" year="<%=currentYear%>">
                    <%
                        for (int i = 0; i < 12; i++) {
                            request.setAttribute("month" , monthNames[i]);
                    %>
                    <p class="checkbox_block">
                        <fmt:message key="periodical.${month}"/>
                        <label class="checkbox_label">
                            <input type="checkbox" class="filled-in checkbox-green"/>
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


