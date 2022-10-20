package com.periodicals.dao;

import com.periodicals.dao.exception.DAOException;
import com.periodicals.entity.MonthSelector;

import java.sql.Connection;
import java.util.Map;

public interface ReleaseCalendarDAO {
    void create(final int periodicalId, final MonthSelector entity,
                final Connection connection) throws DAOException;

    Map<Integer, MonthSelector> getCalendarByPeriodicalId(int id, Connection connection) throws DAOException;

}
