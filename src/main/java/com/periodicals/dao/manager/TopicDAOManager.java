package com.periodicals.dao.manager;

import com.periodicals.dao.ConnectionManager;
import com.periodicals.dao.TopicDAO;
import com.periodicals.dao.TopicTranslateDAO;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.dao.mysql.TopicDAOMySql;
import com.periodicals.dao.mysql.TopicTranslateDAOMySql;
import com.periodicals.entity.Topic;
import com.periodicals.entity.TopicTranslate;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class TopicDAOManager {
    private ConnectionManager conManager;
    private TopicDAO topicDAO;
    private TopicTranslateDAO topicTranslateDAO;

    private TopicDAOManager() {
    }

    public TopicDAOManager(ConnectionManager connectionManager) {
        this.conManager = connectionManager;
        topicDAO = new TopicDAOMySql();
        topicTranslateDAO = new TopicTranslateDAOMySql();
    }

    public void createTopic(Topic topic) throws DAOException {
        Connection connection = conManager.getConnectionForTransaction();
        try {
            topicDAO.create(topic, connection);
            Map<String, TopicTranslate> translates = topic.getAllTranslates();
            for (TopicTranslate t : translates.values()) {
                topicTranslateDAO.create(topic.getId(), t, connection);
            }
            connection.commit();
        } catch (SQLException e) {
            rollback(connection);
            throw new DAOException("Unable to finish transaction of creating new Topic. " + e.getMessage());
        } finally {
            conManager.close(connection);
        }
    }

    public Topic getTopicById(int topicId) throws DAOException {
        Connection connection = conManager.getConnection();
        Topic topic = topicDAO.getEntityById(topicId, connection);
        if (topic != null) {
            topic.addTranslationsMap(topicTranslateDAO.getAllTranslates(topicId, connection));
        }
        conManager.close(connection);
        return topic;
    }

    public Topic getTopicByIdAndLocale(int topicId, String localeId, String defaultLocale) throws DAOException {
        Connection connection = conManager.getConnection();
        Topic topic = topicDAO.getEntityById(topicId, connection);
        if (topic != null) {
            topic.addTranslate(topicTranslateDAO.getTranslateByLocale(topicId, localeId, connection));
            if (topic.getAllTranslates().size() == 0) {
                topic.addTranslate(topicTranslateDAO.getTranslateByLocale(topicId, defaultLocale, connection));
            }
        }
        conManager.close(connection);
        return topic;
    }

    public TopicTranslate getTranslationByIdAndLocale(int topicId, String localeId) throws DAOException {
        Connection connection = conManager.getConnection();
        TopicTranslate topicTranslate = topicTranslateDAO.getTranslateByLocale(topicId, localeId, connection);
        conManager.close(connection);
        return topicTranslate;
    }

    public List<Topic> getTopicsSortPag(final String locale,
                                        final String defaultLocale,
                                        final int skip,
                                        final int amount,
                                        final String sorting) throws DAOException {
        Connection connection = conManager.getConnection();
        List<Topic> topics = topicDAO
                .getAllByLocalePagination(connection, locale, defaultLocale, skip, amount, sorting);
        conManager.close(connection);
        return topics;
    }

    public List<Topic> getTopicsByNameSortPag(final String locale,
                                              final String defaultLocale,
                                              final int skip,
                                              final int amount,
                                              final String sorting,
                                              final String name) throws DAOException {
        Connection connection = conManager.getConnection();
        List<Topic> topics = topicDAO.getAllByNameAndLocalePagination(
                connection, name, locale, defaultLocale, skip, amount, sorting);
        conManager.close(connection);
        return topics;
    }

    public List<Topic> getAllTopicsByLocale(String localeId, String defaultLocale) throws DAOException {
        Connection connection = conManager.getConnection();
        List<Topic> topics = topicDAO.getAllWithTranslatesByLocale(connection, localeId, defaultLocale);
        conManager.close(connection);
        return topics;
    }

    public int getTopicsAmount() throws DAOException {
        Connection connection = conManager.getConnection();
        int count = topicDAO.getTopicsAmount(connection);
        conManager.close(connection);
        return count;
    }

    public int getTopicsAmountSearchMode(final String searchQuery) throws DAOException {
        Connection connection = conManager.getConnection();
        int count = topicDAO.getTopicsAmountSearchMode(connection, searchQuery);
        conManager.close(connection);
        return count;
    }

    public Topic getTopicByName(final List<String> strings) throws DAOException {
        Connection connection = conManager.getConnection();
        Topic topic = null;
        for (String s : strings) {
            topic = topicDAO.getTopicByName(s, connection);
            if (topic != null) {
                break;
            }
        }
        conManager.close(connection);
        return topic;
    }

    public void updateTopic(Topic topic) throws DAOException {
        Connection connection = conManager.getConnectionForTransaction();
        try {
            Map<String, TopicTranslate> translates = topic.getAllTranslates();
            for (TopicTranslate t : translates.values()) {
                t.setTopicID(topic.getId());
                if (topicTranslateDAO.checkIfTranslationExists(t.getTopicID(), t.getLocaleID(), connection)) {
                    topicTranslateDAO.update(t, connection);
                } else {
                    topicTranslateDAO.create(topic.getId(), t, connection);
                }
            }
            connection.commit();
        } catch (SQLException e) {
            rollback(connection);
            throw new DAOException("Unable to finish transaction of updating Topic with ID="
                    + topic.getId() + ". " + e.getMessage());
        } finally {
            conManager.close(connection);
        }
    }

    public void deleteTopic(final int id) throws DAOException {
        Connection connection = conManager.getConnection();
        topicDAO.delete(id, connection);
        conManager.close(connection);
    }

    private void rollback(final Connection con) throws DAOException {
        try {
            con.rollback();
        } catch (SQLException ex) {
            throw new DAOException("Something went wrong while trying to rollback. " + ex.getMessage());
        }
    }
}