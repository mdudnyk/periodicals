package com.periodicals.dao.exception;

import java.sql.SQLException;

public class DAOException extends SQLException {
    public DAOException() {
        super();
    }

    public DAOException(String reason, String dbState, Throwable cause) {
        super(reason, dbState, cause);
    }

    public DAOException(String reason, String dbState) {
        super(reason, dbState);
    }

    public DAOException(String reason, Throwable cause) {
        super(reason, cause);
    }

    public DAOException(String reason) {
        super(reason);
    }

    public DAOException(Throwable cause) {
        super(cause);
    }
}