package com.periodicals.controller.servlet.command.impl.get;

import com.periodicals.controller.servlet.command.FrontCommand;
import com.periodicals.dao.exception.DAOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class SetLocaleCommand implements FrontCommand {
    @Override
    public void execute(final HttpServletRequest request, final HttpServletResponse response) throws DAOException, ServletException, IOException {
        String langParam = request.getParameter("lang");
        if (langParam != null) {
            request.getSession().setAttribute("lang", langParam);
        }
        response.sendRedirect(request.getHeader("Referer"));
    }
}
