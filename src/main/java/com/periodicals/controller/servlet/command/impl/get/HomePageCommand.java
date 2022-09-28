package com.periodicals.controller.servlet.command.impl.get;

import com.periodicals.controller.servlet.command.FrontCommand;
import com.periodicals.dao.exception.DAOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class HomePageCommand implements FrontCommand {
    @Override
    public void execute(final HttpServletRequest request, final HttpServletResponse response) throws DAOException, ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/jsp/HomePage.jsp").forward(request, response);
    }
}
