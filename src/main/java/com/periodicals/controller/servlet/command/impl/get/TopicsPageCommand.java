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

import java.io.IOException;
import java.util.List;


public class TopicsPageCommand implements FrontCommand {
    private int pageNumber;

    @Override
    public void execute(final HttpServletRequest request, final HttpServletResponse response,
                        final DAOManagerFactory daoManager)
            throws DAOException, ServletException, IOException, ServiceException {

        String defaultLocaleName = request.getSession()
                .getServletContext()
                .getInitParameter("defaultLocale");
        LocaleCustom localeTmp = (LocaleCustom) request.getSession()
                .getAttribute("locale");
        String currentLocale = localeTmp.getShortNameId();
        TopicService topicService = new TopicServiceImpl(daoManager);
        int topicsTotal = topicService.getTopicsTotal();

        pageNumber = setPageNumber(request);
        int amountOnPage = setAmountOnPage(request);
        String sortByName = setNameSorting(request);
        checkPageNumberAccordingToTotalTopicsAmount(amountOnPage, topicsTotal);
        int positionsToSkip = pageNumber * amountOnPage - amountOnPage;

        List<Topic> topics = null;
        if (topicsTotal != 0) {
            topics = topicService.getTopicsByLocalePagination(currentLocale, defaultLocaleName,
                    positionsToSkip, amountOnPage, sortByName);
        }

        request.setAttribute("topicsTotal", topicsTotal);
        request.getSession().setAttribute("topicPageNumber", pageNumber);
        request.setAttribute("topics", topics);
        request.getRequestDispatcher("WEB-INF/jsp/TopicsPage.jsp").forward(request, response);
    }

    private void checkPageNumberAccordingToTotalTopicsAmount(
            final int amountOnPage, final int topicsTotal) {
        int totalPagesExists = topicsTotal / amountOnPage + 1;
        if (topicsTotal % amountOnPage == 0) {
            totalPagesExists--;
        }
        if (pageNumber > totalPagesExists) {
            pageNumber = totalPagesExists;
        }
    }

    private String setNameSorting(final HttpServletRequest request) {
        String defaultSorting = "ASC";
        String sorting = request.getParameter("sorting");
        if (sorting != null) {
            if (!sorting.equals("ASC") && !sorting.equals("DESC")) {
                sorting = defaultSorting;
            }
            pageNumber = 1;
            request.getSession().setAttribute("sorting", sorting);
        } else {
            String sortingFromSession = (String) request.getSession().getAttribute("sorting");
            if (sortingFromSession != null) {
                sorting = sortingFromSession;
            } else {
                sorting = defaultSorting;
                request.getSession().setAttribute("sorting", sorting);
            }
        }
        return sorting;
    }

    private int setAmountOnPage(final HttpServletRequest request) {
        int amount = 10;
        String amountString = request.getParameter("amount");
        if (amountString != null) {
            try {
                amount = Integer.parseInt(amountString);
            } catch (NumberFormatException e) {
                //TODO LOGGING
            }
            if (amount > 25) {
                amount = 25;
            }
            pageNumber = 1;
            request.getSession().setAttribute("topicAmountOnPage", amount);
        } else {
            Integer amountFromSession = (Integer) request.getSession().getAttribute("topicAmountOnPage");
            if (amountFromSession != null) {
                amount = amountFromSession;
            } else {
                request.getSession().setAttribute("topicAmountOnPage", amount);
            }
        }
        return amount;
    }

    private int setPageNumber(final HttpServletRequest request) {
        int page = 1;
        String pageString = request.getParameter("pageNumber");
        if (pageString != null) {
            try {
                page = Integer.parseInt(pageString);
            } catch (NumberFormatException e) {
                //TODO LOGGING
            }
            if (page < 1) {
                page = 1;
            }
        } else {
            Integer pageFromSession = (Integer) request.getSession().getAttribute("topicPageNumber");
            if (pageFromSession != null) {
                page = pageFromSession;
            }
        }
        return page;
    }
}
