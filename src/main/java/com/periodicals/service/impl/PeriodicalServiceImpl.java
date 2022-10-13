package com.periodicals.service.impl;

import com.periodicals.dao.exception.DAOException;
import com.periodicals.dao.manager.DAOManagerFactory;
import com.periodicals.dao.manager.PeriodicalDAOManager;
import com.periodicals.entity.PeriodicalForTable;
import com.periodicals.service.PeriodicalService;

import java.util.List;

public class PeriodicalServiceImpl implements PeriodicalService {
    private final DAOManagerFactory daoManger;

    public PeriodicalServiceImpl(DAOManagerFactory daoManger) {
        this.daoManger = daoManger;
    }

    public List<PeriodicalForTable> getPeriodicalsForTableSortPagination(final String localeId,
                                                                         final String defaultLocaleId,
                                                                         final int skipPositions,
                                                                         final int amountOnPage,
                                                                         final String sortBy,
                                                                         final String sortOrder) throws DAOException {
        PeriodicalDAOManager pdm = daoManger.getPeriodicalDAOManager();
        return pdm.getPeriodicalsForTableSortPag(
                localeId, defaultLocaleId, skipPositions, amountOnPage, sortBy, sortOrder);
    }

    @Override
    public List<PeriodicalForTable> getPeriodicalsForTableByTitleSortPagination(final String localeId,
                                                                         final String defaultLocaleId,
                                                                         final int skipPositions,
                                                                         final int amountOnPage,
                                                                         final String sortBy,
                                                                         final String sortOrder,
                                                                         final String searchedTitle)  throws DAOException {
        PeriodicalDAOManager pdm = daoManger.getPeriodicalDAOManager();
        return pdm.getPeriodicalsForTableByTitleSortPag(
                localeId, defaultLocaleId, skipPositions, amountOnPage, sortBy, sortOrder, searchedTitle);
    }


    @Override
    public int getPeriodicalsTotal() throws DAOException {
        PeriodicalDAOManager tdm = daoManger.getPeriodicalDAOManager();
        return tdm.getPeriodicalsAmount();
    }

    @Override
    public int getPeriodicalsTotalSearchMode(final String searchQuery) throws DAOException {
        PeriodicalDAOManager tdm = daoManger.getPeriodicalDAOManager();
        return tdm.getPeriodicalsAmountSearchMode(searchQuery);
    }
}
