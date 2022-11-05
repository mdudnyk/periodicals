package com.periodicals;

import com.periodicals.dao.exception.DAOException;

import com.periodicals.entity.User;
import com.periodicals.entity.enums.UserRole;
import com.periodicals.service.exceptions.ServiceException;

public class Main {
    public static void main(String[] args) throws DAOException, ServiceException {
        User user = new User("ua", "Myroslav", "Dudnyk", "sdsdds", "sdsd", UserRole.CUSTOMER, 100, true);
        System.out.println(user.getFirstname().charAt(0) + user.getLastname().charAt(0));
    }
}
