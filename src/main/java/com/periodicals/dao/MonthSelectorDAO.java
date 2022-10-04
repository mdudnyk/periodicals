package com.periodicals.dao;

import com.periodicals.dao.exception.DAOException;
import com.periodicals.entity.MonthSelector;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public interface MonthSelectorDAO {
    void create(int parentObjID, MonthSelector entity, Connection connection) throws DAOException;
    Map<Integer, MonthSelector> getAllById(int parentObjID, Connection connection) throws DAOException;
    MonthSelector getEntityByIdAndYear(int parentObjID, int year, Connection connection) throws DAOException;

}

//    void update(E entity, Connection connection) throws DAOException;
//
//    void delete(K id, Connection connection) throws DAOException;