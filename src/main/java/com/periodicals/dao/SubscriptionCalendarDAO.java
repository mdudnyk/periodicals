package com.periodicals.dao;

import com.periodicals.dao.exception.DAOException;
import com.periodicals.entity.MonthSelector;

import java.sql.Connection;

public interface SubscriptionCalendarDAO {

    void create(final int subscriptionId, final MonthSelector entity,
                final Connection connection) throws DAOException;

}
