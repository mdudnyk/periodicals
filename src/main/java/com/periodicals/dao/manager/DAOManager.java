package com.periodicals.dao.manager;

import com.periodicals.dao.ConnectionManager;
import com.periodicals.dao.HikariConnectionPool;
import com.periodicals.dao.exception.DAOException;

/**
 * Main class for interaction with the database layer of the application.
 */
public class DAOManager {
    private static DAOManager instance;
    private final ConnectionManager connectionManager;

    private final UserDao userDao;
    private final LocaleDao localeDao;
    private final TopicDao topicDao;
    private final PeriodicalDao periodicalDao;
    private final SubscriptionDao subscriptionDao;
    private final PaymentDao paymentDao;

    /**
     * Constructor initialized {@link HikariConnectionPool} object and all DAO Manager objects
     * for every specific entity in database.
     */
    private DAOManager() throws DAOException {
        connectionManager = HikariConnectionPool.getInstance();
        userDao = new UserDao(connectionManager);
        localeDao = new LocaleDao(connectionManager);
        topicDao = new TopicDao(connectionManager);
        periodicalDao = new PeriodicalDao(connectionManager);
        subscriptionDao = new SubscriptionDao(connectionManager);
        paymentDao = new PaymentDao(connectionManager);
    }

    /**
     * Using a {@code Singleton} pattern to create and get only one instance of {@link DAOManager} object.
     * This method is {@code synchronized} to work properly in multithreading web application.
     *
     * @return new {@link DAOManager} object if it wasn't initialize before or already
     * initialized instance of this object if it was initialized before.
     */
    public static synchronized DAOManager getInstance() throws DAOException {
        if (instance == null) {
            instance = new DAOManager();
        }
        return instance;
    }

    /**
     * Returns an object to interact with the {@code user} table in the database.
     */
    public UserDao getUserDao() {
        return userDao;
    }

    /**
     * Returns an object to interact with the {@code locale} table in the database.
     */
    public LocaleDao getLocaleDao() {
        return localeDao;
    }

    /**
     * Returns an object to interact with the {@code topic} and {@code topic_translate} tables in the database.
     */
    public TopicDao getTopicDao() {
        return topicDao;
    }

    /**
     * Returns an object to interact with the {@code periodical}, {@code release_calendar}
     * and {@code periodical_translate} tables in the database.
     */
    public PeriodicalDao getPeriodicalDao() {
        return periodicalDao;
    }

    /**
     * Returns an object to interact with the {@code subscription}
     * and {@code subscription_calendar} tables in the database.
     */
    public SubscriptionDao getSubscriptionDao() {
        return subscriptionDao;
    }

    /**
     * Returns an object to interact with the {@code payment} table in the database.
     */
    public PaymentDao getPaymentDao() {
        return paymentDao;
    }

    /**
     * Shutdown the DataSource object.
     */
    public void closeDAO() {
        instance = null;
        connectionManager.closeDataSource();
    }
}