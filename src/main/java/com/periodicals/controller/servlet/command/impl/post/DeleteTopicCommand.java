package com.periodicals.controller.servlet.command.impl.post;

import com.periodicals.controller.servlet.command.FrontCommand;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.dao.manager.DAOManagerFactory;
import com.periodicals.service.exceptions.ServiceException;
import com.periodicals.service.TopicService;
import com.periodicals.service.impl.TopicServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class DeleteTopicCommand implements FrontCommand {
    @Override
    public void execute(final HttpServletRequest request,
                        final HttpServletResponse response, final DAOManagerFactory daoManager)
            throws DAOException, ServletException, IOException, ServiceException {
        TopicService topicService = new TopicServiceImpl(daoManager);

        try {
            int topicID  = Integer.parseInt(request.getParameter("id"));
            topicService.deleteTopic(topicID);
        } catch (NumberFormatException e) {
            throw new ServletException("Can't get an appropriate topic ID from request");
        }
    }
}
