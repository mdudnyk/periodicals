package com.periodicals.controller.servlet.command.impl.post;

import com.periodicals.controller.servlet.command.FrontCommand;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.dao.manager.DAOManager;
import com.periodicals.service.UserService;
import com.periodicals.service.exceptions.ServiceException;
import com.periodicals.service.impl.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class SetCustomerStatusCommand implements FrontCommand {
    @Override
    public void execute(final HttpServletRequest request, final HttpServletResponse response,
                        final DAOManager daoManager)
            throws DAOException, ServletException, IOException, ServiceException {
        String customerId = request.getParameter("id");
        String status = request.getParameter("status");

        try {
            int id = Integer.parseInt(customerId);
            boolean isBlocked;

            if (status.equalsIgnoreCase("false")) {
                isBlocked = false;
            } else if (status.equalsIgnoreCase("true")) {
                isBlocked = true;
            } else {
                throw new IllegalArgumentException("Wrong customer blocking status in request parameters.");
            }

            UserService userService = new UserServiceImpl(daoManager);
            userService.setBlockingStatusForCustomer(id, isBlocked);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Wrong user id format in request parameters.");
        }
    }
}