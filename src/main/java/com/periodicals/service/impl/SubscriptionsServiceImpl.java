package com.periodicals.service.impl;

import com.periodicals.dao.exception.DAOException;
import com.periodicals.dao.manager.DAOManagerFactory;
import com.periodicals.dao.manager.PeriodicalDAOManager;
import com.periodicals.dao.manager.SubscriptionDAOManager;
import com.periodicals.dao.manager.UserDAOManager;
import com.periodicals.entity.MonthSelector;
import com.periodicals.entity.Periodical;
import com.periodicals.entity.Subscription;
import com.periodicals.entity.User;
import com.periodicals.service.SubscriptionsService;
import com.periodicals.service.exceptions.ServiceException;
import com.periodicals.util.PriceDeterminant;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.List;
import java.util.Map;

public class SubscriptionsServiceImpl implements SubscriptionsService {
    private final DAOManagerFactory daoManger;

    public SubscriptionsServiceImpl(DAOManagerFactory daoManger) {
        this.daoManger = daoManger;
    }

    @Override
    public void createSubscription(User user, final int periodicalId, final List<MonthSelector> calendar)
            throws DAOException, ServiceException {
        PeriodicalDAOManager periodicalDAOManager = daoManger.getPeriodicalDAOManager();
        UserDAOManager userDAOManager = daoManger.getUserDAOManager();

        Periodical periodical = periodicalDAOManager.getPeriodicalById(periodicalId);
        if (!isValidSubscriptionCalendar(periodical.getReleaseCalendar(), calendar)) {
            throw new IllegalArgumentException("Invalid subscription months input. ");
        }

        PriceDeterminant pd = new PriceDeterminant(periodical.getPrice(), periodical.getFrequency());
        int basePrice = pd.getPrice();
        int totalPrice = countTotalPrice(basePrice, calendar);

        User userFromDB = userDAOManager.getUserById(user.getId());
        int userBalance = userFromDB.getBalance();
        if (userBalance < totalPrice) {
            throw new ServiceException("Subscription is denied. Not enough money. ");
        } else {
            userFromDB.setBalance(userBalance - totalPrice);
        }

        Subscription subscription = new Subscription(
                user.getId(),
                periodicalId,
                periodical.getTitle(),
                totalPrice,
                LocalDateTime.now(),
                MonthSelector.getLastMonth(calendar));
        subscription.addSubscriptionYearList(calendar);

        SubscriptionDAOManager subscriptionDAOManager = daoManger.getSubscriptionDAOManager();
        subscriptionDAOManager.subscribeUserToPeriodical(subscription, userFromDB);
        user.setBalance(userFromDB.getBalance());

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
}