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
        localeDAO.create(locale, connection);
        conManager.close(connection);
    }

    public List<LocaleCustom> getAllLocalesList() throws DAOException {
        Connection connection = conManager.getConnection();
        List<LocaleCustom> locales = localeDAO.getAll(connection);
        conManager.close(connection);
        return locales;
    }

    public LocaleCustom getLocaleById(int id) throws DAOException {
        Connection connection = conManager.getConnection();
        LocaleCustom locale = localeDAO.getEntityById(id, connection);
        conManager.close(connection);
        return locale;
    }

    public void updateLocale(LocaleCustom locale) throws DAOException {
        Connection connection = conManager.getConnection();
        localeDAO.update(locale, connection);
        conManager.close(connection);
    }

    public void deleteLocale(int id) throws DAOException {
        Connection connection = conManager.getConnection();
        localeDAO.delete(id, connection);
        conManager.close(connection);
    }
}
