package com.periodicals.dao.manager;

import com.periodicals.dao.ConnectionManager;
import com.periodicals.dao.HikariConnectionPool;

public class DAOManagerFabric {
    private static DAOManagerFabric instance;
    private final ConnectionManager connectionManager;

    private final UserDAOManager userDAOManager;

    private DAOManagerFabric() {
        connectionManager = HikariConnectionPool.getInstance();
        userDAOManager = new UserDAOManager(connectionManager);
    }

    public static synchronized DAOManagerFabric getInstance() {
        if (instance == null) {
            instance = new DAOManagerFabric();
        }
        return instance;
    }

    public UserDAOManager getUserDAOManager() {
        return userDAOManager;
    }

    public void closeDAO() {
        instance = null;
        connectionManager.closeDataSource();
    }
}
