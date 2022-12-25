package com.periodicals.dao.interfacesForDAO;

import com.periodicals.dao.exception.DAOException;
import com.periodicals.entity.User;

import java.sql.Connection;
import java.util.List;

public interface UserDAO extends GeneralDAO<User, Integer> {
    User getUserByEmail(String email, Connection connection) throws DAOException;

    List<User> getCustomersPagination(int skip, int amount, String sortBy, String order,
                                      Connection connection) throws DAOException;

    List<User> getCustomersPagination(String searchString, int skip, int amount, String sortBy, String order,
                                      Connection connection) throws DAOException;

    int getCustomersAmount(Connection connection) throws DAOException;

    int getCustomersAmount(String searchString, Connection connection) throws DAOException;

}