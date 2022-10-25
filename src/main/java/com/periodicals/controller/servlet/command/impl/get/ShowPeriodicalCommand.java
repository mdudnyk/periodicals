package com.periodicals.controller.servlet.command.impl.get;

import com.periodicals.controller.servlet.command.FrontCommand;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.dao.manager.DAOManagerFactory;
import com.periodicals.entity.LocaleCustom;
import com.periodicals.entity.Periodical;
import com.periodicals.entity.TopicTranslate;
import com.periodicals.service.PeriodicalService;
import com.periodicals.service.exceptions.ServiceException;
import com.periodicals.service.TopicService;
import com.periodicals.service.impl.PeriodicalServiceImpl;
import com.periodicals.service.impl.TopicServiceImpl;
import com.periodicals.util.MoneyFormatter;
import com.periodicals.util.PriceDeterminant;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ShowPeriodicalCommand implements FrontCommand {
    @Override
    public void execute(final HttpServletRequest request,
                        final HttpServletResponse response, final DAOManagerFactory daoManager)
            throws DAOException, ServletException, IOException, ServiceException {
        String defaultLocale = request.getSession()
                .getServletContext()
                .getInitParameter("defaultLocale");
        LocaleCustom localeTmp = (LocaleCustom) request.getSession().getAttribute("locale");
        String currentLocale = localeTmp.getShortNameId();
        String idStr = request.getParameter("id");
        int periodicalId;

        try {
            periodicalId = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            response.getWriter().print("Wrong periodical id. ");
            response.setStatus(500);
            return;
        }

        PeriodicalService periodicalService = new PeriodicalServiceImpl(daoManager);
        TopicService topicService = new TopicServiceImpl(daoManager);

        Periodical periodical = periodicalService
                .getPeriodicalByIdAndLocale(periodicalId, currentLocale, defaultLocale);
        TopicTranslate topicTranslate = topicService
                .getTopicTranslateByIdAndLocale(periodical.getTopicID(), currentLocale, defaultLocale);
        PriceDeterminant priceDeterminant = new PriceDeterminant(periodical.getPrice(), periodical.getFrequency());

        request.setAttribute("periodical", periodical);
        request.setAttribute("topicTranslate", topicTranslate);
        request.setAttribute("price", MoneyFormatter.toHumanReadable(priceDeterminant.getPrice()));
        request.setAttribute("isLessThanMonth", priceDeterminant.isPeriodLessThanMonth());

        request.getRequestDispatcher("WEB-INF/ShowPeriodical.jsp").forward(request, response);
    }
}
