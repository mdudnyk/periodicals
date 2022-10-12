package com.periodicals.dao.manager;

import com.periodicals.dao.ConnectionManager;
import com.periodicals.dao.HikariConnectionPool;
import com.periodicals.dao.exception.DAOException;

public class DAOManagerFactory {
    private static DAOManagerFactory instance;
    private final ConnectionManager connectionManager;

    private final UserDAOManager userDAOManager;
    private final LocaleDAOManager localeDAOManager;
    private final TopicDAOManager topicDAOManager;
    private final PeriodicalDAOManager periodicalDAOManager;


    private DAOManagerFactory() throws DAOException {
        connectionManager = HikariConnectionPool.getInstance();
        userDAOManager = new UserDAOManager(connectionManager);
        localeDAOManager = new LocaleDAOManager(connectionManager);
        topicDAOManager = new TopicDAOManager(connectionManager);
        periodicalDAOManager = new PeriodicalDAOManager(connectionManager);
    }

    public static synchronized DAOManagerFactory getInstance() throws DAOException {
        if (instance == null) {
            instance = new DAOManagerFactory();
        }
        return instance;
    }

    public UserDAOManager getUserDAOManager() {
        return userDAOManager;
    }

    public LocaleDAOManager getLocaleDAOManager() {
        return localeDAOManager;
    }

    public TopicDAOManager getTopicDAOManager() {
        return topicDAOManager;
    }

    public PeriodicalDAOManager getPeriodicalDAOManager() {
        return periodicalDAOManager;
    }

    public void closeDAO() {
        instance = null;
        connectionManager.closeDataSource();
    }
}
