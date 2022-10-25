package com.periodicals.service.exceptions;

import java.sql.SQLException;

public class ServiceException extends SQLException {
    public ServiceException() {
        super();
    }

    public ServiceException(String reason, Throwable cause) {
        super(reason, cause);
    }

    public ServiceException(String reason) {
        super(reason);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }
}
