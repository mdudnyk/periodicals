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

    public int getSubscriptionsTotal(final int userId) throws DAOException {
        Connection connection = conManager.getConnection();
        int result = subscriptionDAO.getTotalAmountByUserId(userId, connection);
        conManager.close(connection);
        return result;
    }

    public int getSubscriptionsTotal(final int userId, final String searchQuery) throws DAOException {
        Connection connection = conManager.getConnection();
        int result = subscriptionDAO.getTotalAmountByUserIdAndSearchQuery(userId, searchQuery, connection);
        conManager.close(connection);
        return result;
    }

    public List<Subscription> getSubscriptionsByUserIdPagination(final int userId, final int positionsToSkip,
                                                                 final int amountOnPage,
                                                                 final String subscriptionsSortBy,
                                                                 final String subscriptionsSortOrder) throws DAOException {
        Connection connection = conManager.getConnection();
        List<Subscription> result = subscriptionDAO
                .getSubscriptionsByUserIdPagination(userId, positionsToSkip, amountOnPage,
                        subscriptionsSortBy, subscriptionsSortOrder, connection);
        conManager.close(connection);
        return result;
    }

    public List<Subscription> getSubscriptionsByUserIdPagination(final int userId, final String searchString,
                                                                 final int positionsToSkip, final int amountOnPage,
                                                                 final String subscriptionsSortBy,
                                                                 final String subscriptionsSortOrder) throws DAOException {
        Connection connection = conManager.getConnection();
        List<Subscription> result = subscriptionDAO
                .getSubscriptionsByUserIdPagination(userId, searchString, positionsToSkip,
                        amountOnPage, subscriptionsSortBy, subscriptionsSortOrder, connection);
        conManager.close(connection);
        return result;
    }

    public Subscription getSubscriptionById(final int subscriptionId) throws DAOException {
        Connection connection = conManager.getConnection();
        Subscription subscription = subscriptionDAO.getEntityById(subscriptionId, connection);
        conManager.close(connection);
        return subscription;
    }

    public void deleteSubscription(final int subscriptionId) throws DAOException {
        Connection connection = conManager.getConnection();
        subscriptionDAO.delete(subscriptionId, connection);
        conManager.close(connection);
    }
}