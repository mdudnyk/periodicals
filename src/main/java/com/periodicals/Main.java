package com.periodicals;

import com.periodicals.dao.exception.DAOException;
import com.periodicals.dao.manager.DAOManagerFactory;
import com.periodicals.dao.manager.PeriodicalDAOManager;

public class Main {
    public static void main(String[] args) throws DAOException {
        PeriodicalDAOManager daoManager = DAOManagerFactory.getInstance().getPeriodicalDAOManager();
        System.out.println(daoManager.getPeriodicalById(1, "ua", "en", 2022));
    }
}