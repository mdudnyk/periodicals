package com.periodicals.service;

import com.periodicals.dao.exception.DAOException;
import com.periodicals.entity.MonthSelector;
import com.periodicals.entity.User;
import com.periodicals.service.exceptions.ServiceException;

import java.util.List;

public interface SubscriptionsService {

    void createSubscription(User user, int periodicalId, List<MonthSelector> calendar)
            throws DAOException, ServiceException;

}
