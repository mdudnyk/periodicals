package com.periodicals.controller.servlet.command.impl.get;

import com.periodicals.controller.servlet.command.FrontCommand;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.dao.manager.DAOManagerFactory;
import com.periodicals.entity.LocaleCustom;
import com.periodicals.entity.Topic;
import com.periodicals.service.ServiceException;
import com.periodicals.service.TopicService;
import com.periodicals.service.impl.TopicServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

public class TopicsPageCommand implements FrontCommand {
    private final String SEARCH_STRING_DEFAULT = "";
    private final String SORT_ORDER_DEFAULT = "ASC";
    private final int AMOUNT_ON_PAGE_DEFAULT = 10;
    private final int PAGE_NUMBER_DEFAULT = 1;

    @Override
    public void execute(final HttpServletRequest request, final HttpServletResponse response,
                        final DAOManagerFactory daoManager)
            throws DAOException, ServletException, IOException, ServiceException {
        TopicService topicService = new TopicServiceImpl(daoManager);
        initSessionAttributes(request.getSession());

        setSortingOrder(request);
        setAmountOnPage(request);
        setSearchString(request);
        setTopicsTotal(request, topicService);
        setCurrentPage(request);

        request.setAttribute("topics", getTopicsList(request, topicService));
        request.getRequestDispatcher("WEB-INF/TopicsPage.jsp").forward(request, response);
    }

    private List<Topic> getTopicsList(final HttpServletRequest request,
                                                        final TopicService topicService) throws DAOException {
        String defaultLocaleName = request.getSession()
                .getServletContext()
                .getInitParameter("defaultLocale");
        LocaleCustom localeTmp = (LocaleCustom) request.getSession()
                .getAttribute("locale");
        String currentLocale = localeTmp.getShortNameId();
        int amountOnPage = (Integer) request.getSession().getAttribute("topicsAmountOnPage");
        int pageNumber = (Integer) request.getSession().getAttribute("topicsPageNumber");
        String searchString = (String) request.getSession().getAttribute("topicsSearchString");
        int positionsToSkip = pageNumber * amountOnPage - amountOnPage;

        List<Topic> topics;

        if (searchString.equals("")) {
            topics = topicService.getTopicsSortPagination(
                    currentLocale,
                    defaultLocaleName,
                    positionsToSkip,
                    amountOnPage,
                    (String) request.getSession().getAttribute("topicsSortOrder"));
        } else {
            topics = topicService.getTopicsByNameSortPagination(
                    currentLocale,
                    defaultLocaleName,
                    positionsToSkip,
                    amountOnPage,
                    (String) request.getSession().getAttribute("topicsSortOrder"),
                    searchString);
        }
        return topics;
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
            currentPage = (Integer) request.getSession().getAttribute("topicsPageNumber");
        }
        if (currentPage > totalPages) {
            currentPage = totalPages;
        }
        if (currentPage < 1) {
            currentPage = 1;
        }

        request.getSession().setAttribute("topicsPageNumber", currentPage);
    }

    private void setTopicsTotal(final HttpServletRequest request,
                                     final TopicService topicService) throws DAOException {
        String searchQuery = (String) request.getSession().getAttribute("topicsSearchString");
        int amountOnPage = (Integer) request.getSession().getAttribute("topicsAmountOnPage");
        int topicsTotal;
        int totalPages;

        if(searchQuery.equals("")) {
            topicsTotal = topicService.getTopicsTotal();
        } else {
            topicsTotal = topicService.getTopicsTotalSearchMode(searchQuery);
        }

        totalPages = topicsTotal / amountOnPage + 1;
        if (topicsTotal % amountOnPage == 0) {
            totalPages--;
        }

        request.setAttribute("topicsTotal", topicsTotal);
        request.setAttribute("totalPages", totalPages);
    }

    private void setSearchString(final HttpServletRequest request) {
        String searchQuery = request.getParameter("searchString");
        if(searchQuery != null) {
            request.getSession().setAttribute("topicsSearchString", searchQuery);
        }
    }

    private void setAmountOnPage(final HttpServletRequest request) {
        String amount = request.getParameter("amount");
        if (amount != null && amount.length() > 0) {
            try {
                int amountOnPage = Integer.parseInt(amount);
                request.getSession().setAttribute("topicsAmountOnPage", amountOnPage);
            } catch (NumberFormatException e) {
                //TODO LOGGING
            }
        }
    }

    private void setSortingOrder(final HttpServletRequest request) {
        String sorting = request.getParameter("sorting");
        if (sorting != null && sorting.length() > 0) {
            request.getSession().setAttribute("topicsSortOrder", sorting);
        }
    }

    private void initSessionAttributes(HttpSession session) {
        if (session.getAttribute("topicsSearchString") == null) {
            session.setAttribute("topicsSearchString", SEARCH_STRING_DEFAULT);
        }
        if (session.getAttribute("topicsSortOrder") == null) {
            session.setAttribute("topicsSortOrder", SORT_ORDER_DEFAULT);
        }
        if (session.getAttribute("topicsAmountOnPage") == null) {
            session.setAttribute("topicsAmountOnPage", AMOUNT_ON_PAGE_DEFAULT);
        }
        if (session.getAttribute("topicsPageNumber") == null) {
            session.setAttribute("topicsPageNumber", PAGE_NUMBER_DEFAULT);
        }
    }
}