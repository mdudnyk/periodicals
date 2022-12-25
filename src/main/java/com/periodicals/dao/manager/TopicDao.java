package com.periodicals.dao.manager;

import com.periodicals.dao.ConnectionManager;
import com.periodicals.dao.interfacesForDAO.TopicDAO;
import com.periodicals.dao.interfacesForDAO.TopicTranslateDAO;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.dao.mysql.TopicDAOMySql;
import com.periodicals.dao.mysql.TopicTranslateDAOMySql;
import com.periodicals.entity.Topic;
import com.periodicals.entity.TopicTranslate;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class TopicDao {
    private ConnectionManager conManager;
    private TopicDAO topicDAO;
    private TopicTranslateDAO topicTranslateDAO;

    private TopicDao() {
    }

    public TopicDao(ConnectionManager connectionManager) {
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
        try {
            Topic topic = topicDAO.getEntityById(topicId, connection);
            if (topic != null) {
                topic.addTranslationsMap(topicTranslateDAO.getAllTranslates(topicId, connection));
            }
            return topic;
        } finally {
            conManager.close(connection);
        }
    }

    public Topic getTopicByIdAndLocale(int topicId, String localeId, String defaultLocale) throws DAOException {
        Connection connection = conManager.getConnection();
        try {
            Topic topic = topicDAO.getEntityById(topicId, connection);
            if (topic != null) {
                topic.addTranslate(topicTranslateDAO.getTranslateByLocale(topicId, localeId, connection));
                if (topic.getAllTranslates().size() == 0) {
                    topic.addTranslate(topicTranslateDAO.getTranslateByLocale(topicId, defaultLocale, connection));
                }
            }
            return topic;
        } finally {
            conManager.close(connection);
        }
    }

    public TopicTranslate getTranslationByIdAndLocale(int topicId, String localeId) throws DAOException {
        Connection connection = conManager.getConnection();
        try {
            return topicTranslateDAO.getTranslateByLocale(topicId, localeId, connection);
        } finally {
            conManager.close(connection);
        }
    }

    public List<Topic> getTopicsSortPag(final String locale,
                                        final String defaultLocale,
                                        final int skip,
                                        final int amount,
                                        final String sorting) throws DAOException {
        Connection connection = conManager.getConnection();
        try {
            return topicDAO
                    .getAllByLocalePagination(connection, locale, defaultLocale, skip, amount, sorting);
        } finally {
            conManager.close(connection);
        }
    }

    public List<Topic> getTopicsByNameSortPag(final String locale,
                                              final String defaultLocale,
                                              final int skip,
                                              final int amount,
                                              final String sorting,
                                              final String name) throws DAOException {
        Connection connection = conManager.getConnection();
        try {
            return topicDAO.getAllByNameAndLocalePagination(
                    connection, name, locale, defaultLocale, skip, amount, sorting);
        } finally {
            conManager.close(connection);
        }
    }

    public List<Topic> getAllTopicsByLocale(String localeId, String defaultLocale) throws DAOException {
        Connection connection = conManager.getConnection();
        try {
            return topicDAO.getAllWithTranslatesByLocale(connection, localeId, defaultLocale);
        } finally {
            conManager.close(connection);
        }
    }

    public int getTopicsAmount() throws DAOException {
        Connection connection = conManager.getConnection();
        try {
            return topicDAO.getTopicsAmount(connection);
        } finally {
            conManager.close(connection);
        }
    }

    public int getTopicsAmountSearchMode(final String searchQuery) throws DAOException {
        Connection connection = conManager.getConnection();
        try {
            return topicDAO.getTopicsAmountSearchMode(connection, searchQuery);
        } finally {
            conManager.close(connection);
        }
    }

    public Topic getTopicByName(final List<String> strings) throws DAOException {
        Connection connection = conManager.getConnection();
        Topic topic = null;
        try {
            for (String s : strings) {
                topic = topicDAO.getTopicByName(s, connection);
                if (topic != null) {
                    break;
                }
            }
        } finally {
            conManager.close(connection);
        }
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
        try {
            topicDAO.delete(id, connection);
        } finally {
            conManager.close(connection);
        }
    }

    private void rollback(final Connection con) throws DAOException {
        try {
            con.rollback();
        } catch (SQLException ex) {
            throw new DAOException("Something went wrong while trying to rollback. " + ex.getMessage());
        }
    }
}