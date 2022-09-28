package com.periodicals.dao.manager;

import com.periodicals.dao.ConnectionManager;
import com.periodicals.dao.HikariConnectionPool;

public class DAOManagerFactory {
    private static DAOManagerFactory instance;
    private final ConnectionManager connectionManager;

    private final UserDAOManager userDAOManager;
    private final LocaleDAOManager localeDAOManager;

    private DAOManagerFactory() {
        connectionManager = HikariConnectionPool.getInstance();
        userDAOManager = new UserDAOManager(connectionManager);
        localeDAOManager = new LocaleDAOManager(connectionManager);
    }

    public static synchronized DAOManagerFactory getInstance() {
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

    public void closeDAO() {
        instance = null;
        connectionManager.closeDataSource();
    }
}
