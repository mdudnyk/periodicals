package com.periodicals.dao;

import com.periodicals.dao.exception.DAOException;
import com.periodicals.entity.PeriodicalTranslate;

import java.sql.Connection;
import java.util.Map;

public interface PeriodicalTranslationDAO {

    void create(final int periodicalId, final PeriodicalTranslate entity,
                final Connection connection) throws DAOException;

    Map<String, PeriodicalTranslate> getTranslationsByPeriodicalId(int id, Connection connection) throws DAOException;

    PeriodicalTranslate getTranslationByPeriodicalIdAndLocale(int id, String locale,
                                                              Connection connection) throws DAOException;

    boolean checkIfTranslationExists(int id, String localeID, Connection connection) throws DAOException;

    void update(int id, PeriodicalTranslate pt, Connection connection) throws DAOException;

}
