package com.periodicals.controller.servlet.command.impl.get;

import com.periodicals.controller.servlet.command.FrontCommand;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.dao.manager.DAOManager;
import com.periodicals.entity.LocaleCustom;
import com.periodicals.entity.PeriodicalForHomePage;
import com.periodicals.entity.Topic;
import com.periodicals.service.PeriodicalService;
import com.periodicals.service.exceptions.ServiceException;
import com.periodicals.service.TopicService;
import com.periodicals.service.impl.PeriodicalServiceImpl;
import com.periodicals.service.impl.TopicServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class HomePageCommand implements FrontCommand {
    public void execute(final HttpServletRequest request, final HttpServletResponse response,
                        final DAOManager daoManager)
            throws DAOException, ServletException, IOException, ServiceException {
        String defaultLocale = request.getSession()
                .getServletContext()
                .getInitParameter("defaultLocale");
        LocaleCustom localeTmp = (LocaleCustom) request.getSession().getAttribute("locale");
        String currentLocale = localeTmp.getShortNameId();

        TopicService topicService = new TopicServiceImpl(daoManager);
        List<Topic> topics = topicService.getAllTopicsByLocale(currentLocale, defaultLocale);

        PeriodicalService periodicalService = new PeriodicalServiceImpl(daoManager);
        Map<Integer, List<PeriodicalForHomePage>> periodicals = periodicalService.getPeriodicalsForHomePage(topics);

        request.setAttribute("topics", topics);
        request.setAttribute("periodicals", periodicals);

        request.getRequestDispatcher("WEB-INF/HomePage.jsp").forward(request, response);
    }
}
