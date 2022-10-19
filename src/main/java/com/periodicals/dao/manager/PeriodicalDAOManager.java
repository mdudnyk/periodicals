package com.periodicals.dao.manager;

import com.periodicals.dao.ConnectionManager;
import com.periodicals.dao.GeneralDAO;
import com.periodicals.dao.PeriodicalDAO;

import com.periodicals.dao.exception.DAOException;
import com.periodicals.dao.mysql.PeriodicalDAOMySql;
import com.periodicals.entity.Periodical;
import com.periodicals.entity.PeriodicalForTable;

import java.sql.Connection;
import java.util.List;

public class PeriodicalDAOManager  {
    private ConnectionManager conManager;
    private PeriodicalDAO periodicalDAO;

    private PeriodicalDAOManager() {
    }

    public PeriodicalDAOManager(ConnectionManager connectionManager) {
        this.conManager = connectionManager;
        periodicalDAO = new PeriodicalDAOMySql();
    }

    public boolean getIsPeriodicalExists(final String title) throws DAOException {
        Connection connection = conManager.getConnection();
        return periodicalDAO.getIsPeriodicalExists(connection, title);
    }

    public List<PeriodicalForTable> getPeriodicalsForTableSortPag(
            String locale, String defaultLocale, int skip, int amount,
            String orderBy, String sorting) throws DAOException {
        Connection connection = conManager.getConnection();
        List<PeriodicalForTable> periodicals = periodicalDAO.getPeriodicalsForTableSortPag(
                connection, locale, defaultLocale, skip, amount, orderBy, sorting);
        conManager.close(connection);
        return periodicals;
    }

    public List<PeriodicalForTable> getPeriodicalsForTableByTitleSortPag(
            String locale, String defaultLocale, int skip, int amount,
            String orderBy, String sorting, String searchedTitle)  throws DAOException {
        Connection connection = conManager.getConnection();
        List<PeriodicalForTable> periodicals = periodicalDAO.getPeriodicalsForTableByTitleSortPag(
                connection, locale, defaultLocale, skip, amount, orderBy, sorting, searchedTitle);
        conManager.close(connection);
        return periodicals;

    }

    public int getPeriodicalsAmount() throws DAOException {
        Connection connection = conManager.getConnection();
        int count = periodicalDAO.getPeriodicalsAmount(connection);
        conManager.close(connection);
        return count;
    }

    public int getPeriodicalsAmountSearchMode(final String searchQuery) throws DAOException {
        Connection connection = conManager.getConnection();
        int count = periodicalDAO.getPeriodicalsAmountSearchMode(connection, searchQuery);
        conManager.close(connection);
        return count;
    }

    public void deletePeriodical(final int id) throws DAOException {
        Connection connection = conManager.getConnection();
        periodicalDAO.delete(id, connection);
        conManager.close(connection);
    }

    public void createPeriodical(final Periodical periodical) throws DAOException {
        Connection connection = conManager.getConnectionForTransaction();

        periodicalDAO.create(periodical, connection);

        conManager.close(connection);
    }
}
