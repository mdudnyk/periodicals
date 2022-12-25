package com.periodicals.dao.interfacesForDAO;

import com.periodicals.dao.exception.DAOException;

import java.sql.Connection;
import java.util.Map;

public interface GeneralTranslateDAO<E> {

    void create(E entity, Connection connection) throws DAOException;

    Map<String, E> getAllTranslates(int parentObjId, Connection connection) throws DAOException;

    E getTranslateByLocale(int parentObjId, String localeId, Connection connection) throws DAOException;

    void update(E entity, Connection connection) throws DAOException;

    void delete(E entity, Connection connection) throws DAOException;

}
