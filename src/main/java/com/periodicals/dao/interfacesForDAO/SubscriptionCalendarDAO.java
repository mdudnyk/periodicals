package com.periodicals.dao.interfacesForDAO;

import com.periodicals.dao.exception.DAOException;
import com.periodicals.entity.MonthSelector;

import java.sql.Connection;
import java.util.Map;

public interface SubscriptionCalendarDAO {

    void create(final int subscriptionId, final MonthSelector entity,
                final Connection connection) throws DAOException;

    Map<Integer, MonthSelector> getCalendarBySubscriptionId(
            final int subscriptionId, final Connection connection) throws DAOException;

}
