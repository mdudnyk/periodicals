package com.periodicals.service;

import com.periodicals.dao.exception.DAOException;
import com.periodicals.entity.Topic;
import com.periodicals.entity.TopicTranslate;
import com.periodicals.service.exceptions.ServiceException;

import java.util.List;
import java.util.Map;

public interface TopicService {
    List<Topic> getAllTopicsByLocale(String localeId, String defaultLocale) throws DAOException;

    List<Topic> getTopicsSortPagination(final String localeId,
                                        final String defaultLocaleId,
                                        int skipPositions,
                                        int amountOnPage,
                                        final String sortOrder) throws DAOException;

    List<Topic> getTopicsByNameSortPagination(final String localeId,
                                              final String defaultLocaleId,
                                              int skipPositions,
                                              int amountOnPage,
                                              final String sortOrder,
                                              final String searchedName) throws DAOException;

    int getTopicsTotal() throws DAOException;

    int getTopicsTotalSearchMode(String searchQuery) throws DAOException;

    void createNewTopic(Map<String, String> translations) throws ServiceException, DAOException;

    Topic getTopicById(int id) throws DAOException, ServiceException;

    void updateTopic(Topic t) throws DAOException, ServiceException;

    void deleteTopic(int topicID) throws DAOException, ServiceException;

    TopicTranslate getTopicTranslateByIdAndLocale(int topicId, String currentLocale,
                                                  String defaultLocale) throws DAOException;

}