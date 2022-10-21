package com.periodicals.service;

import com.periodicals.dao.exception.DAOException;
import com.periodicals.entity.Periodical;
import com.periodicals.entity.PeriodicalForTable;

import java.util.List;

public interface PeriodicalService {

    void createPeriodical(Periodical periodical) throws DAOException, ServiceException;

    void editPeriodical(Periodical periodical) throws DAOException, ServiceException;

    List<PeriodicalForTable> getPeriodicalsForTableSortPagination(final String localeId,
                                                                  final String defaultLocaleId,
                                                                  int skipPositions,
                                                                  int amountOnPage,
                                                                  final String sortBy,
                                                                  final String sortOrder) throws DAOException;

    List<PeriodicalForTable> getPeriodicalsForTableByTitleSortPagination(final String localeId,
                                                                         final String defaultLocaleId,
                                                                         int skipPositions,
                                                                         int amountOnPage,
                                                                         final String sortBy,
                                                                         final String sortOrder,
                                                                         final String searchedTitle) throws DAOException;

    int getPeriodicalsTotal() throws DAOException;

    int getPeriodicalsTotalSearchMode(String searchQuery) throws DAOException;

    void deletePeriodical(int periodicalID) throws DAOException, ServiceException;

    Periodical getPeriodicalById(int id) throws DAOException, ServiceException;

}
