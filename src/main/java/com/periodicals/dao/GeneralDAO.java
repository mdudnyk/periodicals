package com.periodicals.dao;

import com.periodicals.dao.exception.DAOException;

import java.util.List;

public interface GeneralDAO<E, K> {

    void create(E entity) throws DAOException;

    List<E> getAll() throws DAOException;

    E getEntityById(K id) throws DAOException;

    void update(E entity) throws DAOException;

    void delete(K id) throws DAOException;

}