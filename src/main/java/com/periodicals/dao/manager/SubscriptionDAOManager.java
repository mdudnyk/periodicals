package com.periodicals.dao.manager;

import com.periodicals.dao.*;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.dao.mysql.SubscriptionCalendarDAOMySql;
import com.periodicals.dao.mysql.UserDAOMySql;
import com.periodicals.entity.MonthSelector;
import com.periodicals.entity.User;
import com.periodicals.dao.SubscriptionCalendarDAO;
import com.periodicals.dao.SubscriptionDAO;
import com.periodicals.dao.mysql.SubscriptionDAOMySql;

import java.util.List;

public class SubscriptionDAOManager {
    private ConnectionManager conManager;
    private SubscriptionDAO subscriptionDAO;
    private SubscriptionCalendarDAO subscriptionCalendarDAO;
    private UserDAO userDAO;

    private SubscriptionDAOManager() {
    }

    public SubscriptionDAOManager(ConnectionManager connectionManager) {
        this.conManager = connectionManager;
        subscriptionDAO = new SubscriptionDAOMySql();
        subscriptionCalendarDAO =  new SubscriptionCalendarDAOMySql();
        userDAO = new UserDAOMySql();
    }


    public void subscribeUserToPeriodical(final User user, final int periodicalId,
                                          final List<MonthSelector> calendar, final int price) throws DAOException {

    }
}
