package com.periodicals.service;

import com.periodicals.dao.exception.DAOException;
import com.periodicals.entity.User;
import com.periodicals.service.exceptions.ServiceException;

import java.util.List;

public interface UserService {

    User signInUser(String email, String password) throws DAOException;

    void signUpUser(User user) throws ServiceException, DAOException;

    void updateUserInformation(User user) throws DAOException;

    List<User> getCustomersPagination(int positionsToSkip, int amountOnPage,
                                      String sortBy, String sortOrder) throws DAOException;

    List<User> getCustomersPagination(int positionsToSkip, int amountOnPage,
                                      String sortBy, String sortOrder,
                                      String searchString) throws DAOException;

    int getCustomersTotal() throws DAOException;

    int getCustomersTotal(String searchQuery) throws DAOException;

}
