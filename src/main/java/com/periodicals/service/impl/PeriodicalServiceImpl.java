package com.periodicals.service.impl;

import com.periodicals.dao.exception.DAOException;
import com.periodicals.dao.manager.DAOManagerFactory;
import com.periodicals.dao.manager.PeriodicalDAOManager;
import com.periodicals.entity.PeriodicalForTable;
import com.periodicals.service.PeriodicalService;
import com.periodicals.service.ServiceException;

import java.util.List;

public class PeriodicalServiceImpl implements PeriodicalService {
    private final DAOManagerFactory daoManger;

    public PeriodicalServiceImpl(DAOManagerFactory daoManger) {
        this.daoManger = daoManger;
    }

    public List<PeriodicalForTable> getPeriodicalsForTableSortPagination(final String localeId,
                                                                         final String defaultLocaleId,
                                                                         int skipPositions,
                                                                         int amountOnPage,
                                                                         final String sortBy,
                                                                         final String sortOrder) throws DAOException {
        skipPositions = Math.max(skipPositions, 0);
        amountOnPage = Math.max(amountOnPage, 1);
        PeriodicalDAOManager pdm = daoManger.getPeriodicalDAOManager();
        return pdm.getPeriodicalsForTableSortPag(
                localeId, defaultLocaleId, skipPositions, amountOnPage, sortBy, sortOrder);
    }

    @Override
    public List<PeriodicalForTable> getPeriodicalsForTableByTitleSortPagination(final String localeId,
                                                                         final String defaultLocaleId,
                                                                         int skipPositions,
                                                                         int amountOnPage,
                                                                         final String sortBy,
                                                                         final String sortOrder,
                                                                         final String searchedTitle)  throws DAOException {
        PeriodicalDAOManager pdm = daoManger.getPeriodicalDAOManager();
        return pdm.getPeriodicalsForTableByTitleSortPag(
                localeId, defaultLocaleId, skipPositions, amountOnPage, sortBy, sortOrder, searchedTitle);
    }


    @Override
    public int getPeriodicalsTotal() throws DAOException {
        PeriodicalDAOManager pdm = daoManger.getPeriodicalDAOManager();
        return pdm.getPeriodicalsAmount();
    }

    @Override
    public int getPeriodicalsTotalSearchMode(final String searchQuery) throws DAOException {
        PeriodicalDAOManager pdm = daoManger.getPeriodicalDAOManager();
        return pdm.getPeriodicalsAmountSearchMode(searchQuery);
    }

    @Override
    public void deletePeriodical(final int id) throws DAOException, ServiceException {
        PeriodicalDAOManager pdm = daoManger.getPeriodicalDAOManager();
        if (id > 0) {
            pdm.deleteTopic(id);
        } else {
            throw new ServiceException("Periodical ID can't be less than 1. ");
        }
    }
}
