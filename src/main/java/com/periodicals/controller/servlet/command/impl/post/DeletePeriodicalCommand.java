package com.periodicals.controller.servlet.command.impl.post;

import com.periodicals.controller.servlet.command.FrontCommand;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.dao.manager.DAOManager;
import com.periodicals.service.PeriodicalService;
import com.periodicals.service.exceptions.ServiceException;
import com.periodicals.service.impl.PeriodicalServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class DeletePeriodicalCommand implements FrontCommand  {
    @Override
    public void execute(final HttpServletRequest request, final HttpServletResponse response,
                        final DAOManager daoManager)
            throws DAOException, ServletException, IOException, ServiceException {
        PeriodicalService periodicalService = new PeriodicalServiceImpl(daoManager);

        try {
            int topicID  = Integer.parseInt(request.getParameter("id"));
            periodicalService.deletePeriodical(topicID);
        } catch (NumberFormatException e) {
            throw new ServletException("Can't get an appropriate periodical ID from request");
        }
    }
}
