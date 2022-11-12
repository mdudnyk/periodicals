package com.periodicals.controller.servlet.command.impl.get;

import com.periodicals.controller.servlet.command.FrontCommand;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.dao.manager.DAOManagerFactory;
import com.periodicals.entity.LocaleCustom;
import com.periodicals.entity.SubscriptionDetails;
import com.periodicals.entity.User;
import com.periodicals.service.SubscriptionsService;
import com.periodicals.service.exceptions.ServiceException;
import com.periodicals.service.impl.SubscriptionsServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class SubscriptionDetailsCommand implements FrontCommand {
    @Override
    public void execute(final HttpServletRequest request, final HttpServletResponse response,
                        final DAOManagerFactory daoManager)
            throws DAOException, ServletException, IOException, ServiceException {
        User user = (User) request.getSession().getAttribute("user");
        String defaultLocale = request.getSession()
                .getServletContext()
                .getInitParameter("defaultLocale");
        LocaleCustom localeTmp = (LocaleCustom) request.getSession().getAttribute("locale");
        String currentLocale = localeTmp.getShortNameId();
        String id = request.getParameter("id");
        int subscriptionId;

        try {
            subscriptionId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Wrong subscription id. ");
        }

        SubscriptionsService subscriptionsService = new SubscriptionsServiceImpl(daoManager);
        SubscriptionDetails subscriptionDetails = subscriptionsService
                .getSubscriptionDetailsById(
                        subscriptionId,
                        user.getId(),
                        currentLocale,
                        defaultLocale);
        request.setAttribute("subscriptionDetails", subscriptionDetails);

        System.out.println(subscriptionDetails);

        request.getRequestDispatcher("WEB-INF/SubscriptionDetails.jsp").forward(request, response);
    }
}