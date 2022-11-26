package com.periodicals.controller.servlet.command.impl.get;

import com.periodicals.controller.servlet.command.FrontCommand;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.dao.manager.DAOManagerFactory;
import com.periodicals.service.exceptions.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class TopUpPageCommand implements FrontCommand {
    @Override
    public void execute(final HttpServletRequest request, final HttpServletResponse response,
                        final DAOManagerFactory daoManager)
            throws DAOException, ServletException, IOException, ServiceException {
        String refererLink = request.getHeader("referer");
        request.getSession().setAttribute("refererForLiqPay", refererLink);
        request.getRequestDispatcher("WEB-INF/TopUpPage.jsp").forward(request, response);
    }
}