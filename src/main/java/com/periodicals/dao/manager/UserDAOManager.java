package com.periodicals.dao.manager;

import com.periodicals.dao.ConnectionManager;
import com.periodicals.dao.UserDAO;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.dao.mysql.UserDAOMySql;
import com.periodicals.entity.User;

import java.sql.Connection;
import java.util.List;

public class UserDAOManager {
    private ConnectionManager conManager;

    private UserDAOManager() {}

    public UserDAOManager(ConnectionManager connectionManager) {
        this.conManager = connectionManager;
    }

    public void createUser(User user) throws DAOException {
        Connection connection = conManager.getConnection();
        UserDAO userDAO = new UserDAOMySql(connection);

        userDAO.create(user);

        conManager.close(connection);
    }

    public List<User> getAllUsersList() throws DAOException {
        Connection connection = conManager.getConnection();
        UserDAO userDAO = new UserDAOMySql(connection);

        List<User> users = userDAO.getAll();

        conManager.close(connection);

        return users;
    }

    public User getUserById(int id) throws DAOException {
        Connection connection = conManager.getConnection();
        UserDAO userDAO = new UserDAOMySql(connection);

        return userDAO.getEntityById(id);
    }

    public User getUserByEmail(String email) throws DAOException {
        Connection connection = conManager.getConnection();
        UserDAO userDAO = new UserDAOMySql(connection);

        return userDAO.getUserByEmail(email);
    }

    public void updateUser(User user) throws DAOException {
        Connection connection = conManager.getConnection();
        UserDAO userDAO = new UserDAOMySql(connection);

        userDAO.update(user);
    }

    public void deleteUser(int id) throws DAOException {
        Connection connection = conManager.getConnection();
        UserDAO userDAO = new UserDAOMySql(connection);

        userDAO.delete(id);
    }
}
