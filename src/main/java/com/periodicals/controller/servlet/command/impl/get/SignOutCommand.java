package com.periodicals.controller.servlet.command.impl.get;

import com.periodicals.controller.servlet.command.FrontCommand;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.dao.manager.DAOManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class SignOutCommand implements FrontCommand {
    @Override
    public void execute(final HttpServletRequest request, final HttpServletResponse response,
                        final DAOManager daoManager) throws DAOException, ServletException, IOException {

        Cookie[] cookies = request.getCookies();
        String cookieName = "name";
        if  (cookies != null) {
            for (Cookie c: cookies) {
                if (cookieName.equals(c.getName())) {
                    c.setValue("");
                    c.setMaxAge(0);
                    response.addCookie(c);
                    break;
                }
            }
        }
        request.getSession().invalidate();
        response.sendRedirect("/periodicals");
    }
}
