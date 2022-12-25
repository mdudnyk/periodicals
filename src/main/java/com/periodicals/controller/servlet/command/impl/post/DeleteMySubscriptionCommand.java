package com.periodicals.controller.servlet.command.impl.post;

import com.periodicals.controller.servlet.command.FrontCommand;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.dao.manager.DAOManager;
import com.periodicals.entity.User;
import com.periodicals.service.SubscriptionsService;
import com.periodicals.service.exceptions.ServiceException;
import com.periodicals.service.impl.SubscriptionsServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class DeleteMySubscriptionCommand implements FrontCommand {
    @Override
    public void execute(final HttpServletRequest request, final HttpServletResponse response,
                        final DAOManager daoManager)
            throws DAOException, ServletException, IOException, ServiceException {
        SubscriptionsService service = new SubscriptionsServiceImpl(daoManager);
        String id = request.getParameter("id");
        User user = (User) request.getSession().getAttribute("user");
        int subscriptionId;

        try {
            subscriptionId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            response.setStatus(500);
            return;
        }

        try {
            service.deleteMySubscriptionById(subscriptionId, user.getId());
        } catch (ServiceException e) {
            if (e.getMessage().equals("Can't delete not expired subscription.")) {
                response.setStatus(568);
            } else if (e.getMessage().equals("Don't enough rights to delete this subscription.")) {
                response.setStatus(569);
            }
        }
    }
}
