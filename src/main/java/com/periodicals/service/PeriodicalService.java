package com.periodicals.service;

import com.periodicals.dao.exception.DAOException;
import com.periodicals.entity.Periodical;
import com.periodicals.entity.PeriodicalForHomePage;
import com.periodicals.entity.PeriodicalForTable;
import com.periodicals.entity.Topic;

import java.util.List;
import java.util.Map;

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

    Map<Integer, List<PeriodicalForHomePage>> getPeriodicalsForHomePage(List<Topic> topics) throws DAOException;

    Periodical getPeriodicalByIdAndLocale(int id, String currentLocale,
                                          String defaultLocale) throws DAOException, ServiceException;

}
