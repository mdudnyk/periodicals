package com.periodicals.service.impl;

import com.periodicals.dao.exception.DAOException;
import com.periodicals.dao.manager.DAOManagerFactory;
import com.periodicals.dao.manager.UserDAOManager;
import com.periodicals.entity.User;
import com.periodicals.service.exceptions.ServiceException;
import com.periodicals.service.UserService;

public class UserServiceImpl implements UserService {
    private final DAOManagerFactory daoManger;

    public UserServiceImpl(DAOManagerFactory daoManger) {
        this.daoManger = daoManger;
    }

    @Override
    public User signInUser(final String email, final String password) throws DAOException {
        //TODO input validation
        User user = findUserByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    @Override
    public void signUpUser(final User user) throws ServiceException, DAOException {
        //TODO input validation
        User tmpUser = findUserByEmail(user.getEmail());
        if (tmpUser == null) {
            daoManger.getUserDAOManager().createUser(user);
        } else {
            throw new ServiceException("Sorry, this email is already being used");
        }
    }

    @Override
    public void updateUserInformation(final User user) throws DAOException {
        daoManger.getUserDAOManager().updateUser(user);
    }

    private User findUserByEmail(final String email) throws DAOException {
        UserDAOManager userDAO = daoManger.getUserDAOManager();
        return userDAO.getUserByEmail(email);
    }
}
