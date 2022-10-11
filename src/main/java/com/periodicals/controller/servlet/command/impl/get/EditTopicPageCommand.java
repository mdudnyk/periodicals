package com.periodicals.controller.servlet.command.impl.get;

import com.periodicals.controller.servlet.command.FrontCommand;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.dao.manager.DAOManagerFactory;
import com.periodicals.entity.Topic;
import com.periodicals.service.ServiceException;
import com.periodicals.service.TopicService;
import com.periodicals.service.impl.TopicServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class EditTopicPageCommand implements FrontCommand {
    @Override
    public void execute(final HttpServletRequest request, final HttpServletResponse response,
                        final DAOManagerFactory daoManager)
            throws DAOException, ServletException, IOException, ServiceException {
        TopicService topicService = new TopicServiceImpl(daoManager);
        Topic topic = null;
        String topicId = request.getParameter("id");
        int id;

        if (topicId != null) {
            try {
                id = Integer.parseInt(topicId);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Topic ID should represents number. " + e.getMessage());
            }
            try {
                topic = topicService.getTopicById(id);
            } catch (ServiceException e) {
                response.setStatus(564);
            }
            request.setAttribute("topic", topic);
        } else {
            throw new IllegalArgumentException("Nothing to edit. Topic without ID is not editable. ");
        }

        request.getRequestDispatcher("WEB-INF/EditTopic.jsp").forward(request, response);
    }
}
