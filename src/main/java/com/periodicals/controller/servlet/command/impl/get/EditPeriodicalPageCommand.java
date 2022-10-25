package com.periodicals.controller.servlet.command.impl.get;

import com.periodicals.controller.servlet.command.FrontCommand;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.dao.manager.DAOManagerFactory;
import com.periodicals.entity.LocaleCustom;
import com.periodicals.entity.Periodical;
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

public class EditPeriodicalPageCommand implements FrontCommand {
    @Override
    public void execute(final HttpServletRequest request, final HttpServletResponse response,
                        final DAOManagerFactory daoManager)
            throws DAOException, ServletException, IOException, ServiceException {
        String defaultLocale = request.getSession()
                .getServletContext()
                .getInitParameter("defaultLocale");
        LocaleCustom localeTmp = (LocaleCustom) request.getSession().getAttribute("locale");
        String currentLocale = localeTmp.getShortNameId();

        PeriodicalService periodicalService = new PeriodicalServiceImpl(daoManager);
        Periodical periodical = null;
        String periodicalId = request.getParameter("id");
        int id;

        if (periodicalId != null) {
            try {
                id = Integer.parseInt(periodicalId);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Periodical ID should represents number. " + e.getMessage());
            }
            try {
                periodical = periodicalService.getPeriodicalById(id);
            } catch (ServiceException e) {
                response.setStatus(566);
            }
            request.setAttribute("periodical", periodical);
        } else {
            throw new IllegalArgumentException("Nothing to edit. Periodical without ID is not editable. ");
        }

        TopicService topicService = new TopicServiceImpl(daoManager);
        List<Topic> topics = topicService.getAllTopicsByLocale(currentLocale, defaultLocale);
        request.setAttribute("topics", topics);

        request.getRequestDispatcher("WEB-INF/EditPeriodical.jsp").forward(request, response);
    }
}
