package com.periodicals.service;

import com.periodicals.dao.exception.DAOException;
import com.periodicals.entity.Topic;

import java.util.List;

public interface TopicService {
    List<Topic> getAllTopics() throws ServiceException;
    List<Topic> getAllTopicsByLocale(String localeId, String defaultLocale) throws ServiceException, DAOException;
    List<Topic> getTopicsByLocalePagination(final String localeId, final String defaultLocaleId,
                                             final int pageNumber, final int amountOnPage,
                                             final String sortByName) throws ServiceException, DAOException;

    int getTopicsTotal() throws DAOException;
}
