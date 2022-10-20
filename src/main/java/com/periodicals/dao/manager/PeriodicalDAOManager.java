package com.periodicals.dao.manager;

import com.periodicals.dao.ConnectionManager;
import com.periodicals.dao.PeriodicalDAO;

import com.periodicals.dao.PeriodicalTranslationDAO;
import com.periodicals.dao.ReleaseCalendarDAO;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.dao.mysql.PeriodicalDAOMySql;
import com.periodicals.dao.mysql.PeriodicalTranslationDAOMySql;
import com.periodicals.dao.mysql.ReleaseCalendarDAOMySql;
import com.periodicals.entity.MonthSelector;
import com.periodicals.entity.Periodical;
import com.periodicals.entity.PeriodicalForTable;
import com.periodicals.entity.PeriodicalTranslate;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PeriodicalDAOManager  {
    private ConnectionManager conManager;
    private PeriodicalDAO periodicalDAO;
    private PeriodicalTranslationDAO periodicalTranslationDAO;
    private ReleaseCalendarDAO releaseCalendarDAO;

    private PeriodicalDAOManager() {
    }

    public PeriodicalDAOManager(ConnectionManager connectionManager) {
        this.conManager = connectionManager;
        periodicalDAO = new PeriodicalDAOMySql();
        periodicalTranslationDAO = new PeriodicalTranslationDAOMySql();
        releaseCalendarDAO = new ReleaseCalendarDAOMySql();
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

        try {
            periodicalDAO.create(periodical, connection);
            for (PeriodicalTranslate pt : periodical.getTranslation().values()) {
                periodicalTranslationDAO.create(periodical.getId(), pt, connection);
            }
            for (MonthSelector ms : periodical.getReleaseCalendar().values()) {
                releaseCalendarDAO.create(periodical.getId(), ms, connection);
            }
            connection.commit();
        } catch (SQLException e) {
            rollback(connection);
            throw new DAOException("Unable to finish transaction of updating Topic with ID="
                    + periodical.getId() + ". " + e.getMessage());
        } finally {
            conManager.close(connection);
        }
    }

    public Periodical getPeriodicalById(final int id) throws DAOException {
        Connection connection = conManager.getConnection();
        Periodical periodical = periodicalDAO.getEntityById(id, connection);
        if (periodical != null) {
            periodical.setTranslation(periodicalTranslationDAO.getTranslationsByPeriodicalId(id, connection));
            periodical.setReleaseCalendar(releaseCalendarDAO.getCalendarByPeriodicalId(id, connection));
        }
        conManager.close(connection);
        return periodical;
    }

    private void rollback(final Connection con) throws DAOException {
        try {
            con.rollback();
        } catch (SQLException ex) {
            throw new DAOException("Something went wrong while trying to rollback. " + ex.getMessage());
        }
    }
}
