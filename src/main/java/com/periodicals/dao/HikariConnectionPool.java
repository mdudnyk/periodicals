package com.periodicals.dao;

import com.periodicals.dao.exception.DAOException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * A class for managing Hikari Connection Pool.
 */
public class HikariConnectionPool implements ConnectionManager {
    private static final Logger LOG = LogManager.getLogger(HikariConnectionPool.class);

    private static HikariConnectionPool instance;
    private static HikariDataSource dataSource;

    /**
     * Private constructor to construct a HikariDataSource object with the specified configuration from {@code properties} file.
     */
    private HikariConnectionPool() throws DAOException {
        if (dataSource == null) {
            String propertiesFilename = "hikari.properties";
            URL url = this.getClass()
                    .getClassLoader()
                    .getResource(propertiesFilename);
            if (url == null) {
                LOG.fatal("Database initialization problems. File <" + propertiesFilename + "> not found");
                throw new IllegalArgumentException("Database initialization problems. File <" + propertiesFilename + "> not found.");
            }
            try {
                HikariConfig config = new HikariConfig(url.getPath());
                dataSource = new HikariDataSource(config);
            } catch (Exception e) {
                LOG.fatal("Can't retrieve Hikari DataSource object. " + e.getMessage());
                throw new DAOException(e);
            }
        }

        LOG.debug("HikariDataSource object was successfully created with constructor");
    }

    /**
     * Using a {@code Singleton} pattern to create and get only one instance of {@link HikariConnectionPool} object.
     * This method is {@code synchronized} to work properly in multithreading web application.
     *
     * @return new {@link HikariConnectionPool} object if it wasn't initialize before or already
     * initialized instance of this object if it was initialized before.
     */
    public static synchronized HikariConnectionPool getInstance() throws DAOException {
        if (instance == null) {
            instance = new HikariConnectionPool();
        }
        return instance;
    }

    /**
     * Returns {@link Connection} object from Hikari connection pool.
     *
     * @return {@link Connection} object from Hikari connection pool.
     * @throws DAOException in case of errors related with Hikari Data Source.
     */
    @Override
    public Connection getConnection() throws DAOException {
        Connection connection;

        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            LOG.error("Can not get connection from the Hikari connection pool: " + e.getMessage());
            throw new DAOException("Problems with connection to database.");
        }

        LOG.debug("Connection was successfully retrieved from connection pool");
        return connection;
    }

    /**
     * Returns {@link Connection} object from Hikari connection pool. This connection is modified to work with transaction.
     * Auto commit parameter set to {@code false} and transaction isolation level is set to {@code READ_COMMITTED} level.
     *
     * @return {@link Connection} object from Hikari connection pool.
     * @throws DAOException in case of errors related with Hikari Data Source or case of
     *                      problems with modifying connection object.
     */
    @Override
    public Connection getConnectionForTransaction() throws DAOException {
        Connection connection = getConnection();

        try {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        } catch (SQLException e) {
            LOG.error("Can not prepare connection to work with transactions. " + e.getMessage());
            throw new DAOException("Unable to modify connection object.");
        }

        LOG.debug("Connection was successfully prepared to work with transactions");
        return connection;
    }

    /**
     * Undoes all changes made in the current transaction and releases any database locks currently held by
     * this Connection object. This method should be used only when auto-commit mode has been disabled.
     *
     * @throws DAOException – if a database access error occurs, this method is called while participating in a
     *                      distributed transaction, this method is called on a closed connection or this Connection object is in
     *                      auto-commit mode
     */
    @Override
    public void rollback(Connection con) throws DAOException {
        try {
            con.rollback();
        } catch (SQLException e) {
            LOG.error("Can not rollback transaction. " + e.getMessage());
            throw new DAOException("Something went wrong while trying to rollback transaction.");
        }
    }

    /**
     * Releases this {@code Connection} object and returns it to the pool of connections.
     * <p>
     * It is <b>strongly recommended</b> that an application explicitly
     * commits or rolls back an active transaction prior to calling the
     * {@code close} method.  If the {@code close} method is called
     * and there is an active transaction, the results are implementation-defined.
     * </P>
     *
     * @throws DAOException if a database access error occurs
     */
    @Override
    public void close(Connection connection) throws DAOException {
        try {
            if (!connection.getAutoCommit()) {
                connection.setAutoCommit(true);
            }
            connection.close();
        } catch (SQLException e) {
            LOG.error("Can not close and release connection to the pool of connection. " + e.getMessage());
            throw new DAOException("Unable to release the connection to the pool");
        }
    }

    /**
     * Shutdown the DataSource and its associated pool.
     */
    @Override
    public void closeDataSource() {
        dataSource.close();
        instance = null;
        LOG.debug("Shutdown the Hikari DataSource and its associated pool");
    }
}