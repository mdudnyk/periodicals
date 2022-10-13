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
    public List<Topic> getTopicsSortPagination(final String localeId,
                                               final String defaultLocaleId,
                                               int skipPositions,
                                               int amountOnPage,
                                               final String sortOrder) throws DAOException {

        skipPositions = Math.max(skipPositions, 0);
        amountOnPage = Math.max(amountOnPage, 1);
        TopicDAOManager tdm = daoManger.getTopicDAOManager();
        return tdm.getTopicsSortPag(
                localeId, defaultLocaleId, skipPositions, amountOnPage, sortOrder);
    }

    @Override
    public List<Topic> getTopicsByNameSortPagination(final String localeId,
                                                     final String defaultLocaleId,
                                                     int skipPositions,
                                                     int amountOnPage,
                                                     final String sortOrder,
                                                     final String searchedName) throws DAOException {

        skipPositions = Math.max(skipPositions, 0);
        amountOnPage = Math.max(amountOnPage, 1);
        TopicDAOManager tdm = daoManger.getTopicDAOManager();
        return tdm.getTopicsByNameSortPag(
                localeId, defaultLocaleId, skipPositions, amountOnPage, sortOrder, searchedName);
    }

    @Override
    public int getTopicsTotal() throws DAOException {
        TopicDAOManager tdm = daoManger.getTopicDAOManager();
        return tdm.getTopicsAmount();
    }

    @Override
    public int getTopicsTotalSearchMode(final String searchQuery) throws DAOException {
        TopicDAOManager tdm = daoManger.getTopicDAOManager();
        return tdm.getTopicsAmountSearchMode(searchQuery);
    }

    @Override
    public Topic getTopicById(final int id) throws DAOException, ServiceException {
        Topic topic = null;
        if (id > 0) {
            topic = daoManger.getTopicDAOManager().getTopicById(id);
            if (topic == null) {
                throw new ServiceException("There is nothing to edit. Topic with ID="
                        + id + " is not existing. ");
            }
        } else {
            throw new ServiceException("There is nothing to edit. Topic ID="
                    + id + " is not valid. ID should be > 1. ");
        }
        return topic;
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

    @Override
    public void updateTopic(Topic topic) throws DAOException, ServiceException {
        TopicDAOManager tdm = daoManger.getTopicDAOManager();
        if (tdm.getTopicById(topic.getId()) == null) {
            throw new ServiceException("Topic with ID=" + topic.getId() + " not exists");
        } else {
            tdm.updateTopic(topic);
        }
    }

    @Override
    public void deleteTopic(final int id) throws DAOException, ServiceException {
        TopicDAOManager tdm = daoManger.getTopicDAOManager();
        if (id > 0) {
            tdm.deleteTopic(id);
        } else {
            throw new ServiceException("Topic with can't be less than 1. ");
        }
    }
}