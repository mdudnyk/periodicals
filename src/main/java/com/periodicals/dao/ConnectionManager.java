package com.periodicals.dao;

import com.periodicals.dao.exception.DAOException;

import java.sql.Connection;

public interface ConnectionManager {

    Connection getConnection() throws DAOException;

    Connection getConnectionForTransaction() throws DAOException;

    void rollback(Connection connection) throws DAOException;

    void close(Connection connection) throws DAOException;

    void closeDataSource();

}
