package com.periodicals.dao;

import com.periodicals.dao.exception.DAOException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;

public class HikariConnectionPool implements ConnectionManager {
    private static HikariConnectionPool instance;
    private static HikariDataSource dataSource;

    private HikariConnectionPool() {
        if (dataSource == null) {
            String propertiesFilename = "hikari.properties";
            URL url = this.getClass()
                    .getClassLoader()
                    .getResource(propertiesFilename);

            if (url == null) {
                throw new IllegalArgumentException(propertiesFilename + " is not found. ");
            }

            HikariConfig config = new HikariConfig(url.getPath());
            dataSource = new HikariDataSource(config);
        }
    }

    public static synchronized HikariConnectionPool getInstance() {
        if (instance == null) {
            instance = new HikariConnectionPool();
        }
        return instance;
    }

    @Override
    public Connection getConnection() throws DAOException {
        Connection connection;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            throw new DAOException("Can not get connection from the Hikari connection pool: ", e.getMessage());
        }
        return connection;
    }

    @Override
    public Connection getConnectionForTransaction() throws DAOException {
        Connection connection = getConnection();

        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new DAOException("Unable to set 'false' on auto commit property of the connection. ", e);
        }

        return connection;
    }

    @Override
    public void close(Connection connection) throws DAOException {
        try {
            if (!connection.getAutoCommit()) {
                connection.setAutoCommit(true);
            }
            connection.close();
        } catch (SQLException e) {
            throw new DAOException("Unable to release the Hikari connection to the pool. ", e);
        }
    }

    @Override
    public void closeDataSource() {
        dataSource.close();
        instance = null;
    }
}
