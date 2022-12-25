package com.periodicals.controller.servlet.command.impl.get;

import com.periodicals.controller.servlet.command.FrontCommand;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.dao.manager.DAOManager;
import com.periodicals.entity.Subscription;
import com.periodicals.entity.User;
import com.periodicals.service.SubscriptionsService;
import com.periodicals.service.exceptions.ServiceException;
import com.periodicals.service.impl.SubscriptionsServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

public class MySubscriptionsCommand implements FrontCommand {
    @Override
    public void execute(final HttpServletRequest request, final HttpServletResponse response,
                        final DAOManager daoManager)
            throws DAOException, ServiceException, ServletException, IOException {
        SubscriptionsService service = new SubscriptionsServiceImpl(daoManager);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        initSessionAttributes(session);

        setSortingParameters(request);
        setAmountOnPage(request);
        setSearchString(request);
        setSubscriptionsTotal(request, service, user.getId());
        setCurrentPage(request);

        request.setAttribute("subscriptions", getSubscriptionsList(request, service, user.getId()));
        request.getRequestDispatcher("WEB-INF/MySubscriptions.jsp").forward(request, response);
    }

    private void setCurrentPage(final HttpServletRequest request) {
        String pageNumber = request.getParameter("pageNumber");
        int totalPages = (Integer) request.getAttribute("totalPages");
        int currentPage = 1;

        if (pageNumber != null) {
            try {
                currentPage = Integer.parseInt(pageNumber);
            } catch (NumberFormatException e) {
                //TODO LOGGING
            }
        } else {
            currentPage = (Integer) request.getSession().getAttribute("subscriptionsPageNumber");
        }

        if (currentPage > totalPages) {
            currentPage = totalPages;
        }
        if (currentPage < 1) {
            currentPage = 1;
        }

        request.getSession().setAttribute("subscriptionsPageNumber", currentPage);
    }

    private void setSubscriptionsTotal(final HttpServletRequest request,
                                     final SubscriptionsService service, final int userId)
            throws DAOException, ServiceException {
        String searchQuery = (String) request.getSession().getAttribute("subscriptionsSearchString");
        int amountOnPage = (Integer) request.getSession().getAttribute("subscriptionsAmountOnPage");
        int subscriptionsTotal;
        int totalPages;

        if(searchQuery.equals("")) {
            subscriptionsTotal = service.getSubscriptionsTotal(userId);
        } else {
            subscriptionsTotal = service.getSubscriptionsTotal(userId, searchQuery);
        }

        totalPages = subscriptionsTotal / amountOnPage + 1;
        if (subscriptionsTotal % amountOnPage == 0) {
            totalPages--;
        }

        request.setAttribute("subscriptionsTotal", subscriptionsTotal);
        request.setAttribute("totalPages", totalPages);
    }

    private void setSearchString(final HttpServletRequest request) {
        String searchQuery = request.getParameter("searchString");
        if(searchQuery != null) {
            request.getSession().setAttribute("subscriptionsSearchString", searchQuery);
        }
    }

    private void setAmountOnPage(final HttpServletRequest request) {
        String amount = request.getParameter("amount");
        if (amount != null && amount.length() > 0) {
            try {
                int amountOnPage = Integer.parseInt(amount);
                request.getSession().setAttribute("subscriptionsAmountOnPage", amountOnPage);
            } catch (NumberFormatException e) {
                //TODO LOGGING
            }
        }
    }

    private void setSortingParameters(final HttpServletRequest request) {
        String sortBy = request.getParameter("sortBy");
        String sorting = request.getParameter("sorting");
        if (sortBy != null && sortBy.length() > 0) {
            request.getSession().setAttribute("subscriptionsSortBy", sortBy);
        }
        if (sorting != null && sorting.length() > 0) {
            request.getSession().setAttribute("subscriptionsSortOrder", sorting);
        }
    }

    private void initSessionAttributes(HttpSession session) {
        if (session.getAttribute("subscriptionsSearchString") == null) {
            session.setAttribute("subscriptionsSearchString", "");
        }
        if (session.getAttribute("subscriptionsSortBy") == null) {
            session.setAttribute("subscriptionsSortBy", "title");
        }
        if (session.getAttribute("subscriptionsSortOrder") == null) {
            session.setAttribute("subscriptionsSortOrder", "ASC");
        }
        if (session.getAttribute("subscriptionsAmountOnPage") == null) {
            session.setAttribute("subscriptionsAmountOnPage", 10);
        }
        if (session.getAttribute("subscriptionsPageNumber") == null) {
            session.setAttribute("subscriptionsPageNumber", 1);
        }
    }

    private List<Subscription> getSubscriptionsList(final HttpServletRequest request,
                                                    final SubscriptionsService service,
                                                    final int userId) throws DAOException, ServiceException {
        int amountOnPage = (Integer) request.getSession().getAttribute("subscriptionsAmountOnPage");
        int pageNumber = (Integer) request.getSession().getAttribute("subscriptionsPageNumber");
        String searchString = (String) request.getSession().getAttribute("subscriptionsSearchString");
        int positionsToSkip = pageNumber * amountOnPage - amountOnPage;
        List<Subscription> subscriptions;

        if (searchString.equals("")) {
            subscriptions = service.getSubscriptionsByUserIdPagination(
                    userId,
                    positionsToSkip,
                    amountOnPage,
                    (String) request.getSession().getAttribute("subscriptionsSortBy"),
                    (String) request.getSession().getAttribute("subscriptionsSortOrder"));
        } else {
            subscriptions = service.getSubscriptionsByUserIdPagination(
                    userId,
                    searchString,
                    positionsToSkip,
                    amountOnPage,
                    (String) request.getSession().getAttribute("subscriptionsSortBy"),
                    (String) request.getSession().getAttribute("subscriptionsSortOrder"));
        }
        return subscriptions;
    }
}