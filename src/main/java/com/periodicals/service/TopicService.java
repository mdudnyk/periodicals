package com.periodicals.service;

import com.periodicals.dao.exception.DAOException;
import com.periodicals.entity.Topic;

import java.util.List;
import java.util.Map;

public interface TopicService {
    List<Topic> getAllTopicsByLocale(String localeId, String defaultLocale) throws DAOException;

    List<Topic> getTopicsByLocalePagination(final String localeId, final String defaultLocaleId,
                                            final int pageNumber, final int amountOnPage,
                                            final String sortByName) throws DAOException;

    int getTopicsTotal() throws DAOException;

    void createNewTopic(Map<String, String> translations) throws ServiceException, DAOException;

    List<Topic> getTopicsByNameAndLocalePagination(String searchString, String currentLocale,
                                                   String defaultLocaleName, int positionsToSkip,
                                                   int amountOnPage, String sortByName) throws DAOException;

    Topic getTopicById(int id) throws DAOException, ServiceException;

    void updateTopic(Topic t) throws DAOException, ServiceException;

    void deleteTopic(int topicID) throws DAOException, ServiceException;

}
