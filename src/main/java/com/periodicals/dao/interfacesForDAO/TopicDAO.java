package com.periodicals.dao.interfacesForDAO;

import com.periodicals.dao.exception.DAOException;
import com.periodicals.entity.Topic;

import java.sql.Connection;
import java.util.List;

public interface TopicDAO extends GeneralDAO<Topic, Integer> {

    int getTopicsAmount(Connection connection) throws DAOException;

    int getTopicsAmountSearchMode(Connection connection, String name) throws DAOException;

    List<Topic> getAllWithTranslatesByLocale(Connection connection, String locale,
                                             String defaultLocale) throws DAOException;

    List<Topic> getAllByLocalePagination(Connection connection, String locale, String defaultLocale,
                                         int skip, int amount, String order) throws DAOException;

    Topic getTopicByName(String s, Connection connection) throws DAOException;

    List<Topic> getAllByNameAndLocalePagination(Connection connection, String name, String locale,
                                                String defaultLocale, int skip,
                                                int amount, String order) throws DAOException;

}