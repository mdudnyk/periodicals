package com.periodicals.service;

import com.periodicals.dao.exception.DAOException;
import com.periodicals.entity.User;

public interface UserService {
    User signInUser(String email, String password) throws DAOException;
    void signUpUser(User user) throws ServiceException, DAOException;
    void updateUserInformation(User user) throws DAOException;
}