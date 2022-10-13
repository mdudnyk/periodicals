package com.periodicals.controller.servlet.command.impl.get;

import com.periodicals.controller.servlet.command.FrontCommand;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.dao.manager.DAOManagerFactory;
import com.periodicals.entity.LocaleCustom;
import com.periodicals.entity.PeriodicalForTable;
import com.periodicals.service.PeriodicalService;
import com.periodicals.service.ServiceException;
import com.periodicals.service.impl.PeriodicalServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

public class PeriodicalsPageCommand implements FrontCommand {
    private final String SEARCH_STRING_DEFAULT = "";
    private final String SORT_BY_DEFAULT = "title";
    private final String SORT_ORDER_DEFAULT = "ASC";
    private final int AMOUNT_ON_PAGE_DEFAULT = 10;
    private final int PAGE_NUMBER_DEFAULT = 1;

    @Override
    public void execute(final HttpServletRequest request, final HttpServletResponse response,
                        final DAOManagerFactory daoManager)
            throws DAOException, ServletException, IOException, ServiceException {
        PeriodicalService periodicalService = new PeriodicalServiceImpl(daoManager);
        initSessionAttributes(request.getSession());

        setSortingParameters(request);
        setAmountOnPage(request);
        setSearchString(request);
        setPeriodicalsTotal(request, periodicalService);
        setCurrentPage(request);

        request.setAttribute("periodicals", getPeriodicalsList(request, periodicalService));
        request.getRequestDispatcher("WEB-INF/PeriodicalsPage.jsp").forward(request, response);
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
            currentPage = (Integer) request.getSession().getAttribute("periodicalsPageNumber");
        }

        if (currentPage > totalPages) {
            currentPage = totalPages;
        }
        if (currentPage < 1) {
            currentPage = 1;
        }

        request.getSession().setAttribute("periodicalsPageNumber", currentPage);
    }

    private void setPeriodicalsTotal(final HttpServletRequest request,
                               final PeriodicalService periodicalService) throws DAOException {
        String searchQuery = (String) request.getSession().getAttribute("periodicalsSearchString");
        int amountOnPage = (Integer) request.getSession().getAttribute("periodicalsAmountOnPage");
        int periodicalsTotal;
        int totalPages;

        if(searchQuery.equals("")) {
            periodicalsTotal = periodicalService.getPeriodicalsTotal();
        } else {
            periodicalsTotal = periodicalService.getPeriodicalsTotalSearchMode(searchQuery);
        }

        totalPages = periodicalsTotal / amountOnPage + 1;
        if (periodicalsTotal % amountOnPage == 0) {
            totalPages--;
        }

        request.setAttribute("periodicalsTotal", periodicalsTotal);
        request.setAttribute("totalPages", totalPages);
    }


    private void setSearchString(final HttpServletRequest request) {
        String searchQuery = request.getParameter("searchString");
        if(searchQuery != null) {
            request.getSession().setAttribute("periodicalsSearchString", searchQuery);
        }
    }

    private void setAmountOnPage(final HttpServletRequest request) {
        String amount = request.getParameter("amount");
        if (amount != null && amount.length() > 0) {
            try {
                int amountOnPage = Integer.parseInt(amount);
                request.getSession().setAttribute("periodicalsAmountOnPage", amountOnPage);
            } catch (NumberFormatException e) {
                //TODO LOGGING
            }
        }
    }

    private void setSortingParameters(final HttpServletRequest request) {
        String sortBy = request.getParameter("sortBy");
        String sorting = request.getParameter("sorting");
        if (sortBy != null && sortBy.length() > 0) {
            request.getSession().setAttribute("periodicalsSortBy", sortBy);
        }
        if (sorting != null && sorting.length() > 0) {
            request.getSession().setAttribute("periodicalsSortOrder", sorting);
        }
    }

    private void initSessionAttributes(HttpSession session) {
        if (session.getAttribute("periodicalsSearchString") == null) {
            session.setAttribute("periodicalsSearchString", SEARCH_STRING_DEFAULT);
        }
        if (session.getAttribute("periodicalsSortBy") == null) {
            session.setAttribute("periodicalsSortBy", SORT_BY_DEFAULT);
        }
        if (session.getAttribute("periodicalsSortOrder") == null) {
            session.setAttribute("periodicalsSortOrder", SORT_ORDER_DEFAULT);
        }
        if (session.getAttribute("periodicalsAmountOnPage") == null) {
            session.setAttribute("periodicalsAmountOnPage", AMOUNT_ON_PAGE_DEFAULT);
        }
        if (session.getAttribute("periodicalsPageNumber") == null) {
            session.setAttribute("periodicalsPageNumber", PAGE_NUMBER_DEFAULT);
        }
    }

    private List<PeriodicalForTable> getPeriodicalsList(final HttpServletRequest request,
                                                        final PeriodicalService periodicalService) throws DAOException {
        String defaultLocaleName = request.getSession()
                .getServletContext()
                .getInitParameter("defaultLocale");
        LocaleCustom localeTmp = (LocaleCustom) request.getSession()
                .getAttribute("locale");
        String currentLocale = localeTmp.getShortNameId();
        int amountOnPage = (Integer) request.getSession().getAttribute("periodicalsAmountOnPage");
        int pageNumber = (Integer) request.getSession().getAttribute("periodicalsPageNumber");
        String searchString = (String) request.getSession().getAttribute("periodicalsSearchString");
        int positionsToSkip = pageNumber * amountOnPage - amountOnPage;

        List<PeriodicalForTable> periodicals;

        if (searchString.equals("")) {
            periodicals = periodicalService.getPeriodicalsForTableSortPagination(
                    currentLocale,
                    defaultLocaleName,
                    positionsToSkip,
                    amountOnPage,
                    (String) request.getSession().getAttribute("periodicalsSortBy"),
                    (String) request.getSession().getAttribute("periodicalsSortOrder"));
        } else {
            periodicals = periodicalService.getPeriodicalsForTableByTitleSortPagination(
                    currentLocale,
                    defaultLocaleName,
                    positionsToSkip,
                    amountOnPage,
                    (String) request.getSession().getAttribute("periodicalsSortBy"),
                    (String) request.getSession().getAttribute("periodicalsSortOrder"),
                    searchString);
        }
        return periodicals;
    }
}
