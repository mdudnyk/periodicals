package com.periodicals.service.impl;

import com.periodicals.dao.exception.DAOException;
import com.periodicals.dao.manager.DAOManager;
import com.periodicals.entity.LocaleCustom;
import com.periodicals.service.LocaleService;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class LocaleServiceImpl implements LocaleService {
    private final DAOManager daoManger;

    public LocaleServiceImpl(DAOManager daoManger) {
        this.daoManger = daoManger;
    }

    @Override
    public Map<String, LocaleCustom> getAllLocalesMap() throws DAOException {
        return daoManger.getLocaleDao().getAllLocalesList()
                .stream()
                .collect(Collectors
                        .toMap(LocaleCustom::getShortNameId, Function.identity()));
    }

    @Override
    public LocaleCustom getLocaleByShortName(final String shortName) throws DAOException {
        return daoManger.getLocaleDao().getLocaleById(shortName);
    }
}
