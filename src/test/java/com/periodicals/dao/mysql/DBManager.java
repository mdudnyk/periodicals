package com.periodicals.dao.mysql;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.periodicals.dao.ConnectionManager;
import com.periodicals.dao.exception.DAOException;
import org.apache.ibatis.jdbc.ScriptRunner;

import javax.sql.DataSource;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBManager implements ConnectionManager {
    private static final String PROPERTIES_FILE_NAME = "db.properties";
    private static final String DB_URL = "MYSQL_DB_URL";
    private static final String DB_USERNAME = "MYSQL_DB_USERNAME";
    private static final String DB_PASSWORD = "MYSQL_DB_PASSWORD";

    private static DBManager instance;
    private static DataSource dataSource;


    private DBManager() {
        createDataSource();
    }

    private void createDataSource() {
        Properties properties = new Properties();
        try (InputStream is = DBManager.class.getClassLoader()
                .getResourceAsStream(PROPERTIES_FILE_NAME)) {
            properties.load(is);
            MysqlDataSource ds = new MysqlDataSource();
            ds.setURL(properties.getProperty(DB_URL));
            ds.setUser(properties.getProperty(DB_USERNAME));
            ds.setPassword(properties.getProperty(DB_PASSWORD));
            dataSource = ds;
        } catch (IOException e) {
            throw new IllegalArgumentException("Can't get DB properties from " + PROPERTIES_FILE_NAME + " file. " + e.getMessage());
        }
    }

    public static DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    public void setUpDatabase(Connection connection) throws FileNotFoundException {
        ScriptRunner sr = new ScriptRunner(connection);
        Reader reader = new BufferedReader(
                new FileReader(
                        "dbSource/periodicals_datasource_test.sql"));
        sr.runScript(reader);
    }

    public void dropDatabase(Connection connection) throws SQLException {
        String crateTablesSQL =
                "DROP SCHEMA IF EXISTS `periodicals_db_test`";
        Statement statement = connection.createStatement();
        statement.execute(crateTablesSQL);
        statement.close();
    }

    @Override
    public Connection getConnection() throws DAOException {
        Connection connection;
        try {
            connection =  dataSource.getConnection();
        } catch (SQLException e) {
            throw new DAOException("Can not establish connection to the DB" + e.getMessage());
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
    public void rollback(final Connection connection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void close(final Connection connection) throws DAOException {
        try {
            if (!connection.getAutoCommit()) {
                connection.setAutoCommit(true);
            }
            connection.close();
        } catch (SQLException e) {
            throw new DAOException("Unable to close connection to DB ", e);
        }
    }

    @Override
    public void closeDataSource() {
        throw new UnsupportedOperationException();
    }
}