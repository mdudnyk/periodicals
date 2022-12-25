package com.periodicals.dao.interfacesForDAO;

import com.periodicals.dao.exception.DAOException;
import com.periodicals.entity.PeriodicalTranslate;

import java.sql.Connection;
import java.util.Map;

public interface PeriodicalTranslationDAO {

    void create(final int periodicalId, final PeriodicalTranslate entity,
                final Connection connection) throws DAOException;

    Map<String, PeriodicalTranslate> getTranslationsByPeriodicalId(int periodicalId,
                                                                   Connection connection) throws DAOException;

    PeriodicalTranslate getTranslationByPeriodicalIdAndLocale(int periodicalId, String locale,
                                                              Connection connection) throws DAOException;

    boolean checkIfTranslationExists(int periodicalId, String localeId, Connection connection) throws DAOException;

    void update(int periodicalId, PeriodicalTranslate pt, Connection connection) throws DAOException;

}
