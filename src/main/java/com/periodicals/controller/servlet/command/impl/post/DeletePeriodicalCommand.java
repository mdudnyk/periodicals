package com.periodicals.controller.servlet.command.impl.post;

import com.periodicals.controller.servlet.command.FrontCommand;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.dao.manager.DAOManagerFactory;
import com.periodicals.service.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class DeletePeriodicalCommand implements FrontCommand  {
    @Override
    public void execute(final HttpServletRequest request, final HttpServletResponse response, final DAOManagerFactory daoManager) throws DAOException, ServletException, IOException, ServiceException {

    }
}
