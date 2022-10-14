package com.periodicals.dao;

import com.periodicals.dao.exception.DAOException;
import com.periodicals.entity.TopicTranslate;

import java.sql.Connection;

public interface TopicTranslateDAO extends GeneralTranslateDAO<TopicTranslate> {

    void create(final int parentObjID, final TopicTranslate entity, final Connection connection) throws DAOException;

    boolean checkIfTranslationExists(final int topicID,
                                     final String localeID, final Connection connection) throws DAOException;

}
