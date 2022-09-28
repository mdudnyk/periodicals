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

    public List<User> getAllUsersList() throws DAOException {
        Connection connection = conManager.getConnection();
        List<User> users = userDAO.getAll(connection);
        conManager.close(connection);
        return users;
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

    public void updateUser(User user) throws DAOException {
        Connection connection = conManager.getConnection();
        userDAO.update(user, connection);
        conManager.close(connection);
    }

    public void deleteUser(int id) throws DAOException {
        Connection connection = conManager.getConnection();
        userDAO.delete(id, connection);
        conManager.close(connection);
    }
}
