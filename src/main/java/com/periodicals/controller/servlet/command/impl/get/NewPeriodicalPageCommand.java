package com.periodicals.controller.servlet.command.impl.get;

import com.periodicals.controller.servlet.command.FrontCommand;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.dao.manager.DAOManager;
import com.periodicals.entity.LocaleCustom;
import com.periodicals.entity.Topic;
import com.periodicals.service.exceptions.ServiceException;
import com.periodicals.service.TopicService;
import com.periodicals.service.impl.TopicServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class NewPeriodicalPageCommand implements FrontCommand {
    @Override
    public void execute(final HttpServletRequest request,
                        final HttpServletResponse response, final DAOManager daoManager)
            throws DAOException, ServletException, IOException, ServiceException {
        String defaultLocale = request.getSession()
                .getServletContext()
                .getInitParameter("defaultLocale");
        LocaleCustom localeTmp = (LocaleCustom) request.getSession().getAttribute("locale");
        String currentLocale = localeTmp.getShortNameId();
        TopicService topicService = new TopicServiceImpl(daoManager);
        List<Topic> topics = topicService.getAllTopicsByLocale(currentLocale, defaultLocale);
        request.setAttribute("topics", topics);

        request.getRequestDispatcher("WEB-INF/NewPeriodical.jsp").forward(request, response);
    }
}
