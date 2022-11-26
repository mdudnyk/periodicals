package com.periodicals.dao.manager;

import com.periodicals.dao.ConnectionManager;
import com.periodicals.dao.LocaleDAO;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.dao.mysql.LocaleDAOMySql;
import com.periodicals.entity.LocaleCustom;

import java.sql.Connection;
import java.util.List;

public class LocaleDAOManager {
    private ConnectionManager conManager;
    private LocaleDAO localeDAO;

    private LocaleDAOManager() {
    }

    public LocaleDAOManager(ConnectionManager connectionManager) {
        this.conManager = connectionManager;
        localeDAO = new LocaleDAOMySql();
    }

    public void createLocale(LocaleCustom locale) throws DAOException {
        Connection connection = conManager.getConnection();
        try {
            localeDAO.create(locale, connection);
        } finally {
            conManager.close(connection);
        }
    }

    public List<LocaleCustom> getAllLocalesList() throws DAOException {
        Connection connection = conManager.getConnection();
        List<LocaleCustom> locales;
        try {
            locales = localeDAO.getAll(connection);
        } finally {
            conManager.close(connection);
        }
        return locales;
    }

    public LocaleCustom getLocaleById(String id) throws DAOException {
        Connection connection = conManager.getConnection();
        LocaleCustom locale;
        try {
            locale = localeDAO.getEntityById(id, connection);
        } finally {
            conManager.close(connection);
        }
        return locale;
    }

    public void updateLocale(LocaleCustom locale) throws DAOException {
        Connection connection = conManager.getConnection();
        try {
            localeDAO.update(locale, connection);
        } finally {
            conManager.close(connection);
        }
    }

    public void deleteLocale(String id) throws DAOException {
        Connection connection = conManager.getConnection();
        try {
            localeDAO.delete(id, connection);
        } finally {
            conManager.close(connection);
        }
    }
}