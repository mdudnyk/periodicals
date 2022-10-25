package com.periodicals.controller.servlet.command.impl.post;

import com.periodicals.controller.servlet.command.FrontCommand;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.dao.manager.DAOManagerFactory;
import com.periodicals.entity.Topic;
import com.periodicals.entity.TopicTranslate;
import com.periodicals.service.exceptions.ServiceException;
import com.periodicals.service.TopicService;
import com.periodicals.service.impl.TopicServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EditTopicCommand implements FrontCommand {
    @Override
    public void execute(final HttpServletRequest request,
                        final HttpServletResponse response, final DAOManagerFactory daoManager)
            throws DAOException, ServletException, IOException, ServiceException {
        Map<String, TopicTranslate> translations = getTranslationsMap(request);
        TopicService topicService = new TopicServiceImpl(daoManager);
        String topicId = request.getParameter("id");
        int id = 0;
        if (topicId != null) {
            try {
                id = Integer.parseInt(topicId);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Topic ID should represents number. " + e.getMessage());
            }
        }
        if (translations.size() > 0 && id > 0) {
            try {
                Topic topic = new Topic(id);
                topic.addTranslationsMap(translations);
                topicService.updateTopic(topic);
            } catch (ServiceException e) {
                response.setStatus(563);
            }
        } else {
            response.setStatus(562);
        }
    }

    private Map<String, TopicTranslate> getTranslationsMap(HttpServletRequest request) {
        Map<String, TopicTranslate> translations = new HashMap<>();
        String[] locales = request.getParameterValues("lang");
        String[] names = request.getParameterValues("name");

        if (locales != null && names != null && locales.length == names.length) {
            for (int i = 0; i < locales.length; i++) {
                translations.put(locales[i], new TopicTranslate(0, locales[i], names[i]));
            }
        }
        return translations;
    }
}