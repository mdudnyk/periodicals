package com.periodicals.service.impl;

import com.periodicals.dao.exception.DAOException;
import com.periodicals.dao.manager.DAOManager;
import com.periodicals.dao.manager.PeriodicalDao;
import com.periodicals.dao.manager.SubscriptionDao;
import com.periodicals.dao.manager.UserDao;
import com.periodicals.entity.*;
import com.periodicals.entity.enums.UserRole;
import com.periodicals.service.SubscriptionsService;
import com.periodicals.service.exceptions.ServiceException;
import com.periodicals.util.PriceDeterminant;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.List;
import java.util.Map;

public class SubscriptionsServiceImpl implements SubscriptionsService {
    private final DAOManager daoManger;

    public SubscriptionsServiceImpl(DAOManager daoManger) {
        this.daoManger = daoManger;
    }

    @Override
    public void createSubscription(User user, final int periodicalId, final List<MonthSelector> calendar)
            throws DAOException, ServiceException {
        PeriodicalDao periodicalDao = daoManger.getPeriodicalDao();
        UserDao userDao = daoManger.getUserDao();

        Periodical periodical = periodicalDao.getPeriodicalById(periodicalId);
        if (!isValidSubscriptionCalendar(periodical.getReleaseCalendar(), calendar)) {
            throw new IllegalArgumentException("Invalid subscription months input.");
        }

        PriceDeterminant pd = new PriceDeterminant(periodical.getPrice(), periodical.getFrequency());
        int basePrice = pd.getPrice();
        int totalPrice = countTotalPrice(basePrice, calendar);

        User userFromDB = userDao.getUserById(user.getId());
        int userBalance = userFromDB.getBalance();
        if (user.isBlocked()) {
            throw new ServiceException("Account is blocked.");
        }
        if (userBalance < totalPrice) {
            throw new ServiceException("Not enough money.");
        } else {
            userFromDB.setBalance(userBalance - totalPrice);
        }

        Subscription subscription = new Subscription(
                1,
                user.getId(),
                periodicalId,
                periodical.getTitle(),
                totalPrice,
                LocalDateTime.now(),
                MonthSelector.getLastMonth(calendar));
        subscription.addSubscriptionYearList(calendar);

        SubscriptionDao subscriptionDao = daoManger.getSubscriptionDao();
        subscriptionDao.subscribeUserToPeriodical(subscription, userFromDB);
        user.setBalance(userFromDB.getBalance());

    }

    @Override
    public int getSubscriptionsTotal(final int userId) throws DAOException, ServiceException {
        userIdValidation(userId);
        return daoManger.getSubscriptionDao().getSubscriptionsTotal(userId);
    }

    @Override
    public int getSubscriptionsTotal(final int userId, final String searchQuery)
            throws DAOException, ServiceException {
        userIdValidation(userId);
        searchStringValidation(searchQuery);
        return daoManger.getSubscriptionDao().getSubscriptionsTotal(userId, searchQuery);
    }

    @Override
    public List<Subscription> getSubscriptionsByUserIdPagination(final int userId, int positionsToSkip,
                                                                 int amountOnPage, final String subscriptionsSortBy,
                                                                 final String subscriptionsSortOrder)
            throws ServiceException, DAOException {
        userIdValidation(userId);
        positionsToSkip = Math.max(positionsToSkip, 0);
        amountOnPage = Math.max(amountOnPage, 1);
        return daoManger
                .getSubscriptionDao()
                .getSubscriptionsByUserIdPagination(userId, positionsToSkip, amountOnPage,
                        subscriptionsSortBy, subscriptionsSortOrder);
    }

    @Override
    public List<Subscription> getSubscriptionsByUserIdPagination(final int userId, final String searchString,
                                                                 int positionsToSkip, int amountOnPage,
                                                                 final String subscriptionsSortBy,
                                                                 final String subscriptionsSortOrder)
            throws ServiceException, DAOException {
        userIdValidation(userId);
        searchStringValidation(searchString);
        positionsToSkip = Math.max(positionsToSkip, 0);
        amountOnPage = Math.max(amountOnPage, 1);
        return daoManger
                .getSubscriptionDao()
                .getSubscriptionsByUserIdPagination(userId, searchString, positionsToSkip, amountOnPage,
                        subscriptionsSortBy, subscriptionsSortOrder);
    }

    @Override
    public void deleteMySubscriptionById(final int subscriptionId, final int userId)
            throws DAOException, ServiceException {
        userIdValidation(userId);
        subscriptionIdValidation(subscriptionId);
        Subscription subscription = daoManger.getSubscriptionDao().getSubscriptionById(subscriptionId);
        if (subscription.getUserId() != userId) {
            throw new ServiceException("Don't enough rights to delete this subscription.");
        } else if (subscription.getExpiredAt().isAfter(LocalDate.now())) {
            throw new ServiceException("Can't delete not expired subscription.");
        }
        daoManger.getSubscriptionDao().deleteSubscription(subscriptionId);
    }

    @Override
    public SubscriptionDetails getSubscriptionDetailsById(final int subscriptionId, final int userId,
                                                          final String currentLocale, final String defaultLocale)
            throws DAOException, ServiceException {
        userIdValidation(userId);
        subscriptionIdValidation(subscriptionId);

        Subscription subscription = daoManger.getSubscriptionDao().getSubscriptionById(subscriptionId);
        if (subscription == null) {
            throw new ServiceException("Can't find this subscription.");
        }
        SubscriptionDetails subscriptionDetails = new SubscriptionDetails(
                subscription.getPeriodicalTitle(),
                subscription.getSubscriptionCalendar());

        User user = daoManger.getUserDao().getUserById(userId);
        if (subscription.getUserId() != userId
                && user != null
                && user.getRole() != UserRole.ADMIN) {
            throw new ServiceException("Don't enough rights to delete this subscription.");
        }

        if (subscription.getPeriodicalId() != 0) {
            Periodical periodical = daoManger
                    .getPeriodicalDao()
                    .getPeriodicalById(
                            subscription.getPeriodicalId(),
                            currentLocale,
                            defaultLocale,
                            LocalDate.now().getYear());
            if (periodical != null && periodical.getTopicID() != 0) {
                subscriptionDetails.setTitleImgLink(periodical.getTitleImgLink());
                subscriptionDetails.setPublishingFrequency(periodical.getFrequency());

                PeriodicalTranslate periodicalTranslate = periodical.getTranslation().values().iterator().next();
                subscriptionDetails.setOriginCountry(periodicalTranslate.getCountry());
                subscriptionDetails.setPublishingLanguage(periodicalTranslate.getLanguage());
                Topic topic = daoManger
                        .getTopicDao()
                        .getTopicByIdAndLocale(
                                periodical.getTopicID(),
                                currentLocale,
                                defaultLocale);
                if (topic != null) {
                    TopicTranslate topicTranslate = topic.getAllTranslates().values().iterator().next();
                    subscriptionDetails.setTopicName(topicTranslate.getName());
                }
            }
        }

        return subscriptionDetails;
    }

    private int countTotalPrice(final int basePrice, final List<MonthSelector> calendar) {
        int price = 0;
        for (MonthSelector subCalendar : calendar) {
            for (int i = 0; i < 12; i++) {
                boolean selected = (boolean) subCalendar.getMonth().get(i);
                if (selected) {
                    price += basePrice;
                }
            }
        }
        return price;
    }

    private boolean isValidSubscriptionCalendar(final Map<Integer, MonthSelector> releaseCalendar,
                                                final List<MonthSelector> calendar) {
        for (MonthSelector subCalendar : calendar) {
            MonthSelector perCalendar = releaseCalendar.get(subCalendar.getYear());
            if (perCalendar == null) {
                return false;
            } else {
                for (int i = 0; i < 12; i++) {
                    boolean selected = (boolean) subCalendar.getMonth().get(i);
                    boolean allowed = (boolean) perCalendar.getMonth().get(i);
                    if (selected && !allowed) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void userIdValidation(final int id) throws ServiceException {
        if (id < 1) {
            throw new ServiceException("Invalid user id: " + id);
        }
    }

    private void subscriptionIdValidation(final int id) throws ServiceException {
        if (id < 1) {
            throw new ServiceException("Invalid subscription id: " + id);
        }
    }

    private void searchStringValidation(final String searchString) throws ServiceException {
        if (searchString == null || searchString.isBlank()) {
            throw new ServiceException("Invalid search string. ");
        }
    }
}