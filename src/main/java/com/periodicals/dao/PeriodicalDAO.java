package com.periodicals.dao;

import com.periodicals.dao.exception.DAOException;
import com.periodicals.entity.Periodical;
import com.periodicals.entity.PeriodicalForTable;

import java.sql.Connection;
import java.util.List;

public interface PeriodicalDAO extends GeneralDAO<Periodical, Integer> {
    List<PeriodicalForTable> getPeriodicalsForTableSortPag(
            Connection connection, String locale, String defaultLocale,
            int skip, int amount, String sortBy, String order) throws DAOException;

}
