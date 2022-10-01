package com.periodicals.service;

import com.periodicals.dao.exception.DAOException;
import com.periodicals.entity.LocaleCustom;

import java.util.Map;

public interface LocaleService {

    Map<String, LocaleCustom> getAllLocalesMap() throws DAOException;

    LocaleCustom getLocaleByShortName(String shortName) throws DAOException;

}
