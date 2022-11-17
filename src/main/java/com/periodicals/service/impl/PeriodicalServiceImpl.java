package com.periodicals.service.impl;

import com.periodicals.dao.exception.DAOException;
import com.periodicals.dao.manager.DAOManagerFactory;
import com.periodicals.dao.manager.PeriodicalDAOManager;
import com.periodicals.entity.Periodical;
import com.periodicals.entity.PeriodicalForHomePage;
import com.periodicals.entity.PeriodicalForTable;
import com.periodicals.entity.Topic;
import com.periodicals.service.PeriodicalService;
import com.periodicals.service.exceptions.ServiceException;

import java.time.Year;
import java.util.List;
import java.util.Map;

public class PeriodicalServiceImpl implements PeriodicalService {
    private final DAOManagerFactory daoManger;

    public PeriodicalServiceImpl(DAOManagerFactory daoManger) {
        this.daoManger = daoManger;
    }

    @Override
    public void createPeriodical(final Periodical periodical) throws DAOException, ServiceException {
        PeriodicalDAOManager pdm = daoManger.getPeriodicalDAOManager();
        if (!pdm.getIsPeriodicalExists(periodical.getTitle())) {
            pdm.createPeriodical(periodical);
        } else {
            throw new ServiceException("Periodical with title=" + periodical.getTitle() + " already exists. ");
        }
    }

    @Override
    public void editPeriodical(final Periodical periodical) throws DAOException, ServiceException {
        PeriodicalDAOManager pdm = daoManger.getPeriodicalDAOManager();
        if (!pdm.getIsPeriodicalExists(periodical.getId(), periodical.getTitle())) {
            pdm.editPeriodical(periodical);
        } else {
            throw new ServiceException("Periodical with title=" + periodical.getTitle() + " already exists. ");
        }
    }

    public List<PeriodicalForTable> getPeriodicalsForTableSortPagination(final String localeId,
                                                                         final String defaultLocaleId,
                                                                         int skipPositions,
                                                                         int amountOnPage,
                                                                         final String sortBy,
                                                                         final String sortOrder) throws DAOException {
        skipPositions = Math.max(skipPositions, 0);
        amountOnPage = Math.max(amountOnPage, 1);
        return daoManger.getPeriodicalDAOManager()
                .getPeriodicalsForTableSortPag(localeId, defaultLocaleId,
                        skipPositions, amountOnPage, sortBy, sortOrder);
    }

    @Override
    public List<PeriodicalForTable> getPeriodicalsForTableByTitleSortPagination(final String localeId,
                                                                         final String defaultLocaleId,
                                                                         int skipPositions,
                                                                         int amountOnPage,
                                                                         final String sortBy,
                                                                         final String sortOrder,
                                                                         final String searchedTitle)  throws DAOException {
        skipPositions = Math.max(skipPositions, 0);
        amountOnPage = Math.max(amountOnPage, 1);
        return daoManger.getPeriodicalDAOManager()
                .getPeriodicalsForTableByTitleSortPag(localeId, defaultLocaleId,
                        skipPositions, amountOnPage, sortBy, sortOrder, searchedTitle);
    }

    @Override
    public int getPeriodicalsTotal() throws DAOException {
        return daoManger.getPeriodicalDAOManager().getPeriodicalsAmount();
    }

    @Override
    public int getPeriodicalsTotalSearchMode(final String searchQuery) throws DAOException {
        return daoManger.getPeriodicalDAOManager().getPeriodicalsAmountSearchMode(searchQuery);
    }

    @Override
    public void deletePeriodical(final int id) throws DAOException, ServiceException {
        PeriodicalDAOManager pdm = daoManger.getPeriodicalDAOManager();
        if (id > 0) {
            pdm.deletePeriodical(id);
        } else {
            throw new ServiceException("Periodical ID can't be less than 1. ");
        }
    }

    @Override
    public Periodical getPeriodicalById(final int id) throws DAOException, ServiceException {
        Periodical periodical;
        if (id > 0) {
            periodical = daoManger.getPeriodicalDAOManager().getPeriodicalById(id);
            if (periodical == null) {
                throw new ServiceException("Periodical with ID="
                        + id + " is not exists. ");
            }
        } else {
            throw new ServiceException("Periodical ID="
                    + id + " is not valid. ID should be > 1. ");
        }
        return periodical;
    }

    @Override
    public Map<Integer, List<PeriodicalForHomePage>> getPeriodicalsForHomePage(final List<Topic> topics)
            throws DAOException {
        PeriodicalDAOManager pdm = daoManger.getPeriodicalDAOManager();
        return pdm.getPeriodicalsForHomePage(topics);
    }

    @Override
    public Periodical getPeriodicalByIdAndLocale(final int id,
                                                 final String currentLocale,
                                                 final String defaultLocale) throws DAOException, ServiceException {
        Periodical periodical;
        int currentYear = Year.now().getValue();

        if (id > 0) {
            periodical = daoManger.getPeriodicalDAOManager()
                    .getPeriodicalById(id, currentLocale, defaultLocale, currentYear);
            if (periodical == null) {
                throw new ServiceException("Periodical with ID="
                        + id + " is not exists. ");
            }
        } else {
            throw new ServiceException("Periodical ID="
                    + id + " is not valid. ID should be > 1. ");
        }
        return periodical;
    }
}
