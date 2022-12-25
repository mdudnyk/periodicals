package com.periodicals.dao.interfacesForDAO;

import com.periodicals.dao.exception.DAOException;
import com.periodicals.entity.MonthSelector;

import java.sql.Connection;
import java.util.Map;

public interface ReleaseCalendarDAO {

    void create(final int periodicalId, final MonthSelector entity,
                final Connection connection) throws DAOException;

    Map<Integer, MonthSelector> getCalendarByPeriodicalId(int periodicalId,
                                                          Connection connection) throws DAOException;

    MonthSelector getCalendarByPeriodicalIdAndYear(int periodicalId, int year,
                                                   Connection connection) throws DAOException;

    boolean checkIfCalendarExists(int periodicalId, int year,
                                  Connection connection) throws DAOException;

    void update(int periodicalId, MonthSelector entity,
                Connection connection) throws DAOException;

}
