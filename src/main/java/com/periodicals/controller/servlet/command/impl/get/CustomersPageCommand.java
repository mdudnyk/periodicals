package com.periodicals.controller.servlet.command.impl.get;

import com.periodicals.controller.servlet.command.FrontCommand;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.dao.manager.DAOManager;
import com.periodicals.entity.User;
import com.periodicals.service.UserService;
import com.periodicals.service.exceptions.ServiceException;
import com.periodicals.service.impl.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

public class CustomersPageCommand implements FrontCommand {
    private final String SEARCH_STRING_DEFAULT = "";
    private final String SORT_BY_DEFAULT = "name";
    private final String SORT_ORDER_DEFAULT = "ASC";
    private final int AMOUNT_ON_PAGE_DEFAULT = 10;
    private final int PAGE_NUMBER_DEFAULT = 1;

    @Override
    public void execute(final HttpServletRequest request, final HttpServletResponse response,
                        final DAOManager daoManager)
            throws DAOException, ServletException, IOException, ServiceException {
        UserService userService = new UserServiceImpl(daoManager);
        initSessionAttributes(request.getSession());

        setSortingParameters(request);
        setAmountOnPage(request);
        setSearchString(request);
        setCustomersTotal(request, userService);
        setCurrentPage(request);

        request.setAttribute("customers", getCustomersList(request, userService));
        request.getRequestDispatcher("WEB-INF/CustomersPage.jsp").forward(request, response);
    }

    private void setCurrentPage(final HttpServletRequest request) {
        String pageNumber = request.getParameter("pageNumber");
        int totalPages = (Integer) request.getAttribute("totalPages");
        int currentPage = PAGE_NUMBER_DEFAULT;

        if (pageNumber != null) {
            try {
                currentPage = Integer.parseInt(pageNumber);
            } catch (NumberFormatException e) {
                //TODO LOGGING
            }
        } else {
            currentPage = (Integer) request.getSession().getAttribute("customersPageNumber");
        }

        if (currentPage > totalPages) {
            currentPage = totalPages;
        }
        if (currentPage < 1) {
            currentPage = 1;
        }

        request.getSession().setAttribute("customersPageNumber", currentPage);
    }

    private void setCustomersTotal(final HttpServletRequest request,
                                     final UserService userService) throws DAOException {
        String searchQuery = (String) request.getSession().getAttribute("customersSearchString");
        int amountOnPage = (Integer) request.getSession().getAttribute("customersAmountOnPage");
        int customersTotal;
        int totalPages;

        if (searchQuery.equals("")) {
            customersTotal = userService.getCustomersTotal();
        } else {
            customersTotal = userService.getCustomersTotal(searchQuery);
        }

        totalPages = customersTotal / amountOnPage + 1;
        if (customersTotal % amountOnPage == 0) {
            totalPages--;
        }

        request.setAttribute("customersTotal", customersTotal);
        request.setAttribute("totalPages", totalPages);
    }


    private void setSearchString(final HttpServletRequest request) {
        String searchQuery = request.getParameter("searchString");
        if (searchQuery != null) {
            request.getSession().setAttribute("customersSearchString", searchQuery);
        }
    }

    private void setAmountOnPage(final HttpServletRequest request) {
        String amount = request.getParameter("amount");
        if (amount != null && amount.length() > 0) {
            try {
                int amountOnPage = Integer.parseInt(amount);
                request.getSession().setAttribute("customersAmountOnPage", amountOnPage);
            } catch (NumberFormatException e) {
                //TODO LOGGING
            }
        }
    }

    private void setSortingParameters(final HttpServletRequest request) {
        String sortBy = request.getParameter("sortBy");
        String sorting = request.getParameter("sorting");
        if (sortBy != null && sortBy.length() > 0) {
            request.getSession().setAttribute("customersSortBy", sortBy);
        }
        if (sorting != null && sorting.length() > 0) {
            request.getSession().setAttribute("customersSortOrder", sorting);
        }
    }

    private void initSessionAttributes(HttpSession session) {
        if (session.getAttribute("customersSearchString") == null) {
            session.setAttribute("customersSearchString", SEARCH_STRING_DEFAULT);
        }
        if (session.getAttribute("customersSortBy") == null) {
            session.setAttribute("customersSortBy", SORT_BY_DEFAULT);
        }
        if (session.getAttribute("customersSortOrder") == null) {
            session.setAttribute("customersSortOrder", SORT_ORDER_DEFAULT);
        }
        if (session.getAttribute("customersAmountOnPage") == null) {
            session.setAttribute("customersAmountOnPage", AMOUNT_ON_PAGE_DEFAULT);
        }
        if (session.getAttribute("customersPageNumber") == null) {
            session.setAttribute("customersPageNumber", PAGE_NUMBER_DEFAULT);
        }
    }

    private List<User> getCustomersList(final HttpServletRequest request,
                                                        final UserService userService) throws DAOException {
        int amountOnPage = (Integer) request.getSession().getAttribute("customersAmountOnPage");
        int pageNumber = (Integer) request.getSession().getAttribute("customersPageNumber");
        String searchString = (String) request.getSession().getAttribute("customersSearchString");
        int positionsToSkip = pageNumber * amountOnPage - amountOnPage;
        List<User> customers;

        if (searchString.equals("")) {
            customers = userService.getCustomersPagination(
                    positionsToSkip,
                    amountOnPage,
                    (String) request.getSession().getAttribute("customersSortBy"),
                    (String) request.getSession().getAttribute("customersSortOrder"));
        } else {
            customers = userService.getCustomersPagination(
                    positionsToSkip,
                    amountOnPage,
                    (String) request.getSession().getAttribute("customersSortBy"),
                    (String) request.getSession().getAttribute("customersSortOrder"),
                    searchString);
        }
        return customers;
    }
}