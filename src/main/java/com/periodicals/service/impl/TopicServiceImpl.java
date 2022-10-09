package com.periodicals.service.impl;

import com.periodicals.dao.exception.DAOException;
import com.periodicals.dao.manager.DAOManagerFactory;
import com.periodicals.dao.manager.TopicDAOManager;
import com.periodicals.entity.Topic;
import com.periodicals.service.TopicService;

import java.util.List;

public class TopicServiceImpl implements TopicService {
    private final DAOManagerFactory daoManger;

    public TopicServiceImpl(DAOManagerFactory daoManger) {
        this.daoManger = daoManger;
    }

    @Override
    public List<Topic> getAllTopics() {
        return null;
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
    public int getTopicsTotal() throws DAOException {
        TopicDAOManager tdm = daoManger.getTopicDAOManager();
        return tdm.getTopicsAmount();
    }
}
