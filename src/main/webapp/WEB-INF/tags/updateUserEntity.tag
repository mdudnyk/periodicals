<%@ tag import="com.periodicals.dao.exception.DAOException" %>
<%@ tag import="com.periodicals.dao.manager.DAOManager" %>
<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ attribute name="user" required="true" type="com.periodicals.entity.User"%>

<%
    if (user != null) {
        try {
            user = DAOManager.getInstance().getUserDao().getUserById(user.getId());
        } catch (DAOException e) {
            //TODO <logger> Unable update user entity for header
        }
        session.setAttribute("user", user);
    }
%>