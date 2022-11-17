package com.periodicals.dao.manager;

import com.periodicals.dao.ConnectionManager;
import com.periodicals.dao.UserDAO;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.dao.mysql.UserDAOMySql;
import com.periodicals.entity.Subscription;
import com.periodicals.entity.User;

import java.sql.Connection;
import java.util.List;

public class UserDAOManager {
    private ConnectionManager conManager;
    private UserDAO userDAO;

    private UserDAOManager() {
    }

    public UserDAOManager(ConnectionManager connectionManager) {
        this.conManager = connectionManager;
        userDAO = new UserDAOMySql();
    }

    public void createUser(User user) throws DAOException {
        Connection connection = conManager.getConnection();
        userDAO.create(user, connection);
        conManager.close(connection);
    }

    public User getUserById(int id) throws DAOException {
        Connection connection = conManager.getConnection();
        User user = userDAO.getEntityById(id, connection);
        conManager.close(connection);
        return user;
    }

    public User getUserByEmail(String email) throws DAOException {
        Connection connection = conManager.getConnection();
        User user = userDAO.getUserByEmail(email, connection);
        conManager.close(connection);
        return user;
    }

    public List<User> getCustomersPagination(final int positionsToSkip,
                                             final int amountOnPage,
                                             final String sortBy,
                                             final String sortOrder) throws DAOException {
        Connection connection = conManager.getConnection();
        List<User> result = userDAO
                .getCustomersPagination(positionsToSkip, amountOnPage,
                        sortBy, sortOrder, connection);
        conManager.close(connection);
        return result;
    }

    public List<User> getCustomersPagination(final String searchString,
                                                   final int positionsToSkip,
                                                   final int amountOnPage,
                                                   final String sortBy,
                                                   final String sortOrder) throws DAOException {
        Connection connection = conManager.getConnection();
        List<User> result = userDAO
                .getCustomersPagination(searchString, positionsToSkip, amountOnPage,
                        sortBy, sortOrder, connection);
        conManager.close(connection);
        return result;
    }

    public int getCustomersAmount() throws DAOException {
        Connection connection = conManager.getConnection();
        int amount = userDAO.getCustomersAmount(connection);
        conManager.close(connection);
        return amount;
    }

    public int getCustomersAmount(final String searchString) throws DAOException {
        Connection connection = conManager.getConnection();
        int amount = userDAO.getCustomersAmount(searchString, connection);
        conManager.close(connection);
        return amount;
    }

    public void updateUser(User user) throws DAOException {
        Connection connection = conManager.getConnection();
        userDAO.update(user, connection);
        conManager.close(connection);
    }
}
