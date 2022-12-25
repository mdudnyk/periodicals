package com.periodicals.dao.interfacesForDAO;

import com.periodicals.dao.exception.DAOException;
import com.periodicals.entity.TopicTranslate;

import java.sql.Connection;

public interface TopicTranslateDAO extends GeneralTranslateDAO<TopicTranslate> {

    void create(final int topicId, final TopicTranslate entity, final Connection connection) throws DAOException;

    boolean checkIfTranslationExists(final int topicId, final String localeId,
                                     final Connection connection) throws DAOException;

}
