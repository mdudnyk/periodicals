package com.periodicals.service.impl;

import com.periodicals.dao.exception.DAOException;
import com.periodicals.dao.manager.DAOManager;
import com.periodicals.dao.manager.UserDao;
import com.periodicals.entity.User;
import com.periodicals.entity.enums.UserRole;
import com.periodicals.service.exceptions.ServiceException;
import com.periodicals.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {
    private final DAOManager daoManger;

    public UserServiceImpl(DAOManager daoManger) {
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
            daoManger.getUserDao().createUser(user);
        } else {
            throw new ServiceException("Sorry, this email is already being used");
        }
    }

    @Override
    public void updateUserInformation(final User user) throws DAOException {
        daoManger.getUserDao().updateUser(user);
    }

    @Override
    public List<User> getCustomersPagination(int positionsToSkip, int amountOnPage,
                                             final String sortBy, final String sortOrder) throws DAOException {
        positionsToSkip = Math.max(positionsToSkip, 0);
        amountOnPage = Math.max(amountOnPage, 1);
        return daoManger
                .getUserDao()
                .getCustomersPagination(positionsToSkip, amountOnPage, sortBy, sortOrder);
    }

    @Override
    public List<User> getCustomersPagination(int positionsToSkip, int amountOnPage,
                                             final String sortBy, final String sortOrder,
                                             final String searchString) throws DAOException {
        positionsToSkip = Math.max(positionsToSkip, 0);
        amountOnPage = Math.max(amountOnPage, 1);
        return daoManger
                .getUserDao()
                .getCustomersPagination(searchString, positionsToSkip, amountOnPage, sortBy, sortOrder);
    }

    @Override
    public int getCustomersTotal() throws DAOException {
        return daoManger.getUserDao().getCustomersAmount();
    }

    @Override
    public int getCustomersTotal(final String searchQuery) throws DAOException {
        return daoManger.getUserDao().getCustomersAmount(searchQuery);
    }

    @Override
    public void setBlockingStatusForCustomer(final int customerId, final boolean isBlocked)
            throws DAOException, ServiceException {
        UserDao userDao = daoManger.getUserDao();
        User user = userDao.getUserById(customerId);
        if (user != null) {
            if (user.getRole() == UserRole.CUSTOMER) {
                user.setIsBlocked(isBlocked);
                userDao.updateUser(user);
            } else {
                throw new ServiceException("Set blocking status option valid only for customers.");
            }
        } else {
            throw new ServiceException("Can not set blocking status for customer. We do not have such user.");
        }
    }


    private User findUserByEmail(final String email) throws DAOException {
        UserDao userDAO = daoManger.getUserDao();
        return userDAO.getUserByEmail(email);
    }
}