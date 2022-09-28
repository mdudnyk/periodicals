package com.periodicals;

import com.periodicals.dao.manager.DAOManagerFactory;
import com.periodicals.dao.manager.LocaleDAOManager;

public class Main {
    public static void main(String[] args) {
        DAOManagerFactory dmf = DAOManagerFactory.getInstance();
        LocaleDAOManager localeDAOManager = dmf.getLocaleDAOManager();
//        try {
//
//
//
//        } catch (DAOException e) {
//            System.out.println(e.getMessage());
//        } finally {
//            dmf.closeDAO();
//        }
    }
}
