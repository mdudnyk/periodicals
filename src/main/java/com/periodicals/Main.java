package com.periodicals;

import com.periodicals.dao.exception.DAOException;
import com.periodicals.dao.manager.DAOManagerFactory;
import com.periodicals.entity.Subscription;
import com.periodicals.service.SubscriptionsService;
import com.periodicals.service.exceptions.ServiceException;
import com.periodicals.service.impl.SubscriptionsServiceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws DAOException, ServiceException {
        DAOManagerFactory daoF = DAOManagerFactory.getInstance();
        SubscriptionsService service = new SubscriptionsServiceImpl(daoF);

        List<Integer> list = new ArrayList<>();
        System.out.println(list.isEmpty());
    }
}