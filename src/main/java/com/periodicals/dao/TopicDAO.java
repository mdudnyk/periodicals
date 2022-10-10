package com.periodicals.dao;

import com.periodicals.dao.exception.DAOException;
import com.periodicals.entity.Topic;

import java.sql.Connection;
import java.util.List;

public interface TopicDAO extends GeneralDAO<Topic, Integer> {

    int getTopicsAmount(Connection connection) throws DAOException;

    List<Topic> getAllWithTranslatesByLocale(Connection connection, String locale,
                                             String defaultLocale) throws DAOException;

    List<Topic> getAllByLocalePagination(Connection connection, String locale, String defaultLocale,
                                         int skip, int amount, String sorting) throws DAOException;

    Topic getTopicByName(String s, Connection connection) throws DAOException;

}


