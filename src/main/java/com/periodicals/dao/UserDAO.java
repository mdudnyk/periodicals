package com.periodicals.dao;

import com.periodicals.dao.exception.DAOException;
import com.periodicals.entity.User;

public interface UserDAO extends GeneralDAO<User, Integer> {

    User getUserByEmail(String email) throws DAOException;

}
