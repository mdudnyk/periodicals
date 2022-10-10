package com.periodicals.service.impl;

import com.periodicals.dao.exception.DAOException;
import com.periodicals.dao.manager.DAOManagerFactory;
import com.periodicals.dao.manager.TopicDAOManager;
import com.periodicals.entity.Topic;
import com.periodicals.entity.TopicTranslate;
import com.periodicals.service.ServiceException;
import com.periodicals.service.TopicService;

import java.util.List;
import java.util.Map;

public class TopicServiceImpl implements TopicService {
    private final DAOManagerFactory daoManger;

    public TopicServiceImpl(DAOManagerFactory daoManger) {
        this.daoManger = daoManger;
    }

    @Override
    public List<Topic> getAllTopicsByLocale(final String localeId, final String defaultLocaleId) throws DAOException {
        TopicDAOManager tdm = daoManger.getTopicDAOManager();
        return tdm.getAllTopicsByLocale(localeId, defaultLocaleId);
    }

    @Override
    public List<Topic> getTopicsByLocalePagination(final String localeId, final String defaultLocaleId,
                                                   final int skipPositions, final int amountOnPage,
                                                   final String sortByName) throws DAOException {

        String sorting = sortByName.equals("DESC") ? "DESC" : "ASC";
        int skip = Math.max(skipPositions, 0);
        int amount = Math.max(amountOnPage, 1);
        TopicDAOManager tdm = daoManger.getTopicDAOManager();

        return tdm.getAllTopicsByLocalePagination(localeId, defaultLocaleId, skip, amount, sorting);
    }

    @Override
    public List<Topic> getTopicsByNameAndLocalePagination(final String name, final String localeId,
                                                          final String defaultLocaleId, final int skipPositions,
                                                          final int amountOnPage, final String sortByName) throws DAOException {

        String sorting = sortByName.equals("DESC") ? "DESC" : "ASC";
        int skip = Math.max(skipPositions, 0);
        int amount = Math.max(amountOnPage, 1);
        TopicDAOManager tdm = daoManger.getTopicDAOManager();

        return tdm.getAllTopicsByNameAndLocalePagination(name, localeId, defaultLocaleId, skip, amount, sorting);
    }

    @Override
    public int getTopicsTotal() throws DAOException {
        TopicDAOManager tdm = daoManger.getTopicDAOManager();
        return tdm.getTopicsAmount();
    }

    @Override
    public void createNewTopic(final Map<String, String> translations) throws ServiceException, DAOException {
        TopicDAOManager tdm = daoManger.getTopicDAOManager();

        if (tdm.getTopicByName(translations.values().stream().toList()) != null) {
            throw new ServiceException("Topic with this name already exists");
        } else {
            Topic newTopic = new Topic(1);
            translations.forEach((key, value) -> {
                TopicTranslate tt = new TopicTranslate(1, key, value);
                newTopic.addTranslate(tt);
            });
            tdm.createTopic(newTopic);
        }
    }
}
