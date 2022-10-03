package com.periodicals;

import com.periodicals.dao.exception.DAOException;
import com.periodicals.dao.manager.DAOManagerFactory;
import com.periodicals.dao.manager.LocaleDAOManager;
import com.periodicals.util.MoneyFormatter;

public class Main {
    public static void main(String[] args) {
        int money = 1000000000;
        System.out.println(MoneyFormatter.toHumanReadable(money));
    }
}
