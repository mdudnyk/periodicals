package com.periodicals.dao;

import com.periodicals.dao.exception.DAOException;

import java.sql.Connection;
import java.util.List;

public interface GeneralDAO<E, K> {

    void create(E entity, Connection connection) throws DAOException;

    List<E> getAll(Connection connection) throws DAOException;

    E getEntityById(K id, Connection connection) throws DAOException;

    void update(E entity, Connection connection) throws DAOException;

    void delete(K id, Connection connection) throws DAOException;

}