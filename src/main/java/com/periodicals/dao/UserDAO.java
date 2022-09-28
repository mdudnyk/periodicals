package com.periodicals.dao;

import com.periodicals.dao.exception.DAOException;
import com.periodicals.entity.User;

import java.sql.Connection;

public interface UserDAO extends GeneralDAO<User, Integer> {

    User getUserByEmail(String email, Connection connection) throws DAOException;

}
