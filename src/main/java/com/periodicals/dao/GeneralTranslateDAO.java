package com.periodicals.dao;

import com.periodicals.dao.exception.DAOException;

import java.sql.Connection;
import java.util.Map;

public interface GeneralTranslateDAO<E> {

        void create(E entity, Connection connection) throws DAOException;

        Map<String, E> getAllTranslates(int parentObjID, Connection connection) throws DAOException;

        E getTranslateByLocale(int parentObjID, String localeID, Connection connection) throws DAOException;

        void update(E entity, Connection connection) throws DAOException;

        void delete(E entity, Connection connection) throws DAOException;

}
