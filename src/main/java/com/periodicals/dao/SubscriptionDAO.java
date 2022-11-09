package com.periodicals.dao;

import com.periodicals.dao.exception.DAOException;
import com.periodicals.entity.Subscription;

import java.sql.Connection;
import java.util.List;

public interface SubscriptionDAO extends GeneralDAO<Subscription, Integer> {

    int getTotalAmountByUserIdAndSearchQuery(int userId, String searchQuery, Connection connection) throws DAOException;

    int getTotalAmountByUserId(int userId, Connection connection) throws DAOException;

    List<Subscription> getSubscriptionsByUserIdPagination(int userId, int positionsToSkip, int amountOnPage,
                                                          String subscriptionsSortBy, String subscriptionsSortOrder,
                                                          Connection connection) throws DAOException;


    List<Subscription> getSubscriptionsByUserIdPagination(int userId, String searchString, int positionsToSkip,
                                                          int amountOnPage, String subscriptionsSortBy,
                                                          String subscriptionsSortOrder, Connection connection)
            throws DAOException;

}