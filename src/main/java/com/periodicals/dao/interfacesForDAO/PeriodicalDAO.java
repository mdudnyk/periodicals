package com.periodicals.dao.interfacesForDAO;

import com.periodicals.dao.exception.DAOException;
import com.periodicals.entity.Periodical;
import com.periodicals.entity.PeriodicalForHomePage;
import com.periodicals.entity.PeriodicalForTable;

import java.sql.Connection;
import java.util.List;

public interface PeriodicalDAO extends GeneralDAO<Periodical, Integer> {

    List<PeriodicalForTable> getPeriodicalsForTableSortPag(
            Connection connection, String locale, String defaultLocale,
            int skip, int amount, String sortBy, String order) throws DAOException;

    List<PeriodicalForTable> getPeriodicalsForTableByTitleSortPag(
            Connection connection, String locale, String defaultLocale,
            int skip, int amount, String sortBy, String order, String title) throws DAOException;

    int getPeriodicalsAmount(Connection connection) throws DAOException;

    int getPeriodicalsAmountSearchMode(Connection connection, String title) throws DAOException;

    boolean getIsPeriodicalExists(Connection connection, String title) throws DAOException;

    boolean getIsPeriodicalExists(Connection connection, int id, String title) throws DAOException;

    void updateWithoutImage(Periodical periodical, Connection connection) throws DAOException;

    List<PeriodicalForHomePage> getPeriodicalsForHomePage(int id, Connection connection) throws DAOException;

}
