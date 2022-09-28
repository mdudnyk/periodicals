package com.periodicals.service;

import com.periodicals.dao.exception.DAOException;
import com.periodicals.dao.manager.DAOManagerFactory;
import com.periodicals.dao.manager.UserDAOManager;
import com.periodicals.entity.User;

public class UserService {
    public User signInUser(final String email, final String password) throws DAOException {
        UserDAOManager userDAO = DAOManagerFactory.getInstance().getUserDAOManager();

        User user = userDAO.getUserByEmail(email);

        if (user.getPassword().equals(password)) {
            return user;
        }

        return null;
    }

    public String signUpUser(final User user) throws ServiceException, DAOException {
        return null;
    }
}
