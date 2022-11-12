package com.periodicals.service;

import com.periodicals.dao.exception.DAOException;
import com.periodicals.entity.MonthSelector;
import com.periodicals.entity.Subscription;
import com.periodicals.entity.SubscriptionDetails;
import com.periodicals.entity.User;
import com.periodicals.service.exceptions.ServiceException;

import java.util.List;

public interface SubscriptionsService {

    void createSubscription(User user, int periodicalId, List<MonthSelector> calendar)
            throws DAOException, ServiceException;

    int getSubscriptionsTotal(int userId) throws DAOException, ServiceException;

    int getSubscriptionsTotal(int userId, String searchQuery) throws DAOException, ServiceException;

    List<Subscription> getSubscriptionsByUserIdPagination(int userId, int positionsToSkip, int amountOnPage,
                                                          String subscriptionsSortBy, String subscriptionsSortOrder)
            throws ServiceException, DAOException;

    List<Subscription> getSubscriptionsByUserIdPagination(int userId, String searchString, int positionsToSkip,
                                                          int amountOnPage, String subscriptionsSortBy,
                                                          String subscriptionsSortOrder)
            throws ServiceException, DAOException;

    void deleteMySubscriptionById(int subscriptionId, int userId) throws DAOException, ServiceException;

    SubscriptionDetails getSubscriptionDetailsById(int subscriptionId, int id, String currentLocale,
                                                   String defaultLocale) throws DAOException, ServiceException;

}
