<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ attribute name="page" required="true" type="java.lang.Integer"%>
<%@ attribute name="total" required="true" type="java.lang.Integer"%>

<c:set var="first" value="${page - (page - 1) % 5}"/>
<c:set var="last" value="${(first + 4) > total ? total : first + 4}"/>

<ul class="pagination" style="cursor: default;">
    <c:if test="${page < 2}">
    <li class="disabled"><a><i class="material-icons">chevron_left</i></a></li>
    </c:if>
    <c:if test="${page > 1}">
        <li>
            <a href="${pageContext.request.contextPath}/controller?cmd=TOPICS_PAGE&pageNumber=${page - 1}">
                <i class="material-icons">chevron_left</i>
            </a>
        </li>
    </c:if>

    <c:if test="${first != 1}">
        <li>
            <a href="${pageContext.request.contextPath}/controller?cmd=TOPICS_PAGE&pageNumber=1">
                1
            </a>
        </li>
        <li class="disabled">
            <a>...</a>
        </li>
    </c:if>

    <c:forEach var="i" begin="${first}" end="${last}">
        <c:if test="${i == page}">
            <li class="active green" style="cursor: default"><a>${i}</a></li>
        </c:if>
        <c:if test="${i != page}">
        <li>
            <a href="${pageContext.request.contextPath}/controller?cmd=TOPICS_PAGE&pageNumber=${i}">${i}</a>
        </li>
        </c:if>
    </c:forEach>

    <c:if test="${last != total}">
    <li class="disabled">
        <a>...</a>
    </li>
    <li>
        <a href="${pageContext.request.contextPath}/controller?cmd=TOPICS_PAGE&pageNumber=${total}">
            ${total}
        </a>
    </li>
    </c:if>

    <c:if test="${page < total}">
        <li>
            <a href="${pageContext.request.contextPath}/controller?cmd=TOPICS_PAGE&pageNumber=${page + 1}">
                <i class="material-icons">chevron_right</i>
            </a>
        </li>
    </c:if>
    <c:if test="${page == total}">
        <li class="disabled"><a><i class="material-icons">chevron_right</i></a></li>
    </c:if>

</ul>