package com.periodicals.dao.manager;

import com.periodicals.dao.ConnectionManager;
import com.periodicals.dao.UserDAO;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.dao.mysql.SubscriptionCalendarDAOMySql;
import com.periodicals.dao.mysql.UserDAOMySql;
import com.periodicals.entity.MonthSelector;
import com.periodicals.entity.Subscription;
import com.periodicals.dao.SubscriptionCalendarDAO;
import com.periodicals.dao.SubscriptionDAO;
import com.periodicals.dao.mysql.SubscriptionDAOMySql;
import com.periodicals.entity.User;

import java.sql.Connection;
import java.sql.SQLException;

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
        subscriptionCalendarDAO = new SubscriptionCalendarDAOMySql();
        userDAO = new UserDAOMySql();
    }


    public void subscribeUserToPeriodical(final Subscription subscription, User user) throws DAOException {
        Connection connection = conManager.getConnectionForTransaction();
        try {
            subscriptionDAO.create(subscription, connection);
            for (MonthSelector m : subscription.getSubscriptionCalendar().values()) {
                subscriptionCalendarDAO.create(subscription.getId(), m, connection);
            }
            userDAO.update(user, connection);
            connection.commit();
        } catch (SQLException e) {
            conManager.rollback(connection);
            throw new DAOException("Unable to finish transaction of creating subscription " +
                    "for periodical with title=" + subscription.getPeriodicalTitle() + ". " + e.getMessage());
        } finally {
            conManager.close(connection);
        }
    }
}