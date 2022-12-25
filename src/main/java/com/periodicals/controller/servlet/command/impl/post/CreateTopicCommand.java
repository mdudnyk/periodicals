package com.periodicals.controller.servlet.command.impl.post;

import com.periodicals.controller.servlet.command.FrontCommand;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.dao.manager.DAOManager;
import com.periodicals.service.exceptions.ServiceException;
import com.periodicals.service.TopicService;
import com.periodicals.service.impl.TopicServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;

public class CreateTopicCommand implements FrontCommand {
    @Override
    public void execute(final HttpServletRequest request, final HttpServletResponse response,
                        final DAOManager daoManager)
            throws DAOException, ServletException {
        Map<String, String> translations = getTranslationsMap(request);
        TopicService topicService = new TopicServiceImpl(daoManager);

        if (translations.size() > 0) {
            try {
                topicService.createNewTopic(translations);
            } catch (ServiceException e) {
                response.setStatus(563);
            }
        } else {
            response.setStatus(562);
        }
    }

    private Map<String, String> getTranslationsMap(HttpServletRequest request) {
        Map<String, String> translations = new HashMap<>();
        String[] locales = request.getParameterValues("lang");
        String[] names = request.getParameterValues("name");

        if (locales != null && names != null && locales.length == names.length) {
            for (int i = 0; i < locales.length; i++) {
                translations.put(locales[i], names[i]);
            }
        }

        return translations;
    }
}
