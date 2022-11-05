<%@ tag import="com.periodicals.dao.manager.DAOManagerFactory" %>
<%@ tag import="com.periodicals.dao.exception.DAOException" %>
<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ attribute name="user" required="true" type="com.periodicals.entity.User"%>

<%
    if (user != null) {
        try {
            user = DAOManagerFactory.getInstance().getUserDAOManager().getUserById(user.getId());
        } catch (DAOException e) {
            //TODO <logger> Unable update user entity for header
        }
        session.setAttribute("user", user);
    }
%>