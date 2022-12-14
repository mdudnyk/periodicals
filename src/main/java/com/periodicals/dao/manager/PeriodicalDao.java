package com.periodicals.dao.manager;

import com.periodicals.dao.ConnectionManager;
import com.periodicals.dao.interfacesForDAO.PeriodicalDAO;

import com.periodicals.dao.interfacesForDAO.PeriodicalTranslationDAO;
import com.periodicals.dao.interfacesForDAO.ReleaseCalendarDAO;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.dao.mysql.PeriodicalDAOMySql;
import com.periodicals.dao.mysql.PeriodicalTranslationDAOMySql;
import com.periodicals.dao.mysql.ReleaseCalendarDAOMySql;
import com.periodicals.entity.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PeriodicalDao {
    private ConnectionManager conManager;
    private PeriodicalDAO periodicalDAO;
    private PeriodicalTranslationDAO periodicalTranslationDAO;
    private ReleaseCalendarDAO releaseCalendarDAO;

    private PeriodicalDao() {
    }

    public PeriodicalDao(ConnectionManager connectionManager) {
        this.conManager = connectionManager;
        periodicalDAO = new PeriodicalDAOMySql();
        periodicalTranslationDAO = new PeriodicalTranslationDAOMySql();
        releaseCalendarDAO = new ReleaseCalendarDAOMySql();
    }

    public boolean getIsPeriodicalExists(final String title) throws DAOException {
        Connection connection = conManager.getConnection();
        return periodicalDAO.getIsPeriodicalExists(connection, title);
    }

    public boolean getIsPeriodicalExists(final int id, final String title) throws DAOException {
        Connection connection = conManager.getConnection();
        return periodicalDAO.getIsPeriodicalExists(connection, id, title);
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
            conManager.rollback(connection);
            throw new DAOException("Unable to finish transaction of updating Topic with ID="
                    + periodical.getId() + ". " + e.getMessage());
        } finally {
            conManager.close(connection);
        }
    }

    public void editPeriodical(final Periodical periodical) throws DAOException {
        Connection connection = conManager.getConnectionForTransaction();
        try {
            if (periodical.getTitleImgLink() == null) {
                periodicalDAO.updateWithoutImage(periodical, connection);
            } else {
                periodicalDAO.update(periodical, connection);
            }
            for (PeriodicalTranslate pt : periodical.getTranslation().values()) {
                if (periodicalTranslationDAO
                        .checkIfTranslationExists(periodical.getId(), pt.getLocaleID(), connection)) {
                    periodicalTranslationDAO.update(periodical.getId(), pt, connection);
                } else {
                    periodicalTranslationDAO.create(periodical.getId(), pt, connection);
                }
            }
            for (MonthSelector ms : periodical.getReleaseCalendar().values()) {
                if (releaseCalendarDAO
                        .checkIfCalendarExists(periodical.getId(), ms.getYear(), connection)) {
                    releaseCalendarDAO.update(periodical.getId(), ms, connection);
                } else {
                    releaseCalendarDAO.create(periodical.getId(), ms, connection);
                }
            }
            connection.commit();
        } catch (SQLException e) {
            conManager.rollback(connection);
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

    public Periodical getPeriodicalById(final int id, final String currentLocale,
                                        final String defaultLocale, final int currentYear) throws DAOException {
        Connection connection = conManager.getConnection();
        Periodical periodical = periodicalDAO.getEntityById(id, connection);
        if (periodical != null) {
            Map<String, PeriodicalTranslate> translationMap = new HashMap<>();
            PeriodicalTranslate periodicalTranslate = periodicalTranslationDAO
                    .getTranslationByPeriodicalIdAndLocale(id, currentLocale, connection);
            if (periodicalTranslate == null) {
                periodicalTranslate = periodicalTranslationDAO
                        .getTranslationByPeriodicalIdAndLocale(id, defaultLocale, connection);
            }
            translationMap.put(periodicalTranslate.getLocaleID(), periodicalTranslate);
            periodical.setTranslation(translationMap);

            Map<Integer, MonthSelector> calendar = new HashMap<>();
            for (int i = 0; i < 2; i++) {
                MonthSelector months = releaseCalendarDAO
                        .getCalendarByPeriodicalIdAndYear(id, currentYear + i, connection);
                if (months != null) {
                    calendar.put(months.getYear(), months);
                }
            }
            periodical.setReleaseCalendar(calendar);
        }
        conManager.close(connection);
        return periodical;
    }

    public Map<Integer, List<PeriodicalForHomePage>> getPeriodicalsForHomePage(final List<Topic> topics)
            throws DAOException {
        Connection connection = conManager.getConnection();
        Map<Integer, List<PeriodicalForHomePage>> periodicals = new HashMap<>();
        List<PeriodicalForHomePage> list;
        for (Topic t : topics) {
            list = periodicalDAO.getPeriodicalsForHomePage(t.getId(), connection);
            periodicals.put(t.getId(), list);
        }
        conManager.close(connection);
        return periodicals;
    }
}