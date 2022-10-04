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

    public List<Topic> getAllTopics() throws DAOException {
        Connection connection = conManager.getConnection();

        List<Topic> topics = topicDAO.getAll(connection);
        for (Topic t : topics) {
            t.addTranslationsMap(topicTranslateDAO.getAllTranslates(t.getId(), connection));
        }
        conManager.close(connection);
        return topics;
    }

    public List<Topic> getAllTopicsByLocale(String localeId) throws DAOException {
        Connection connection = conManager.getConnection();

        List<Topic> topics = topicDAO.getAll(connection);
        for (Topic t : topics) {
            t.addTranslate(topicTranslateDAO
                    .getTranslateByLocale(t.getId(), localeId, connection));
        }
        conManager.close(connection);
        return topics;
    }

    private void rollback(final Connection con) throws DAOException {
        try {
            con.rollback();
        } catch (SQLException ex) {
            throw new DAOException("Something went wrong while trying to rollback. " + ex.getMessage());
        }
    }
}
