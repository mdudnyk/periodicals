package com.periodicals.service;

import com.periodicals.dao.exception.DAOException;
import com.periodicals.entity.PeriodicalForTable;

import java.util.List;

public interface PeriodicalService {

    List<PeriodicalForTable> getPeriodicalsForTableSortPagination(final String localeId,
                                                                  final String defaultLocaleId,
                                                                  final int skipPositions,
                                                                  final int amountOnPage,
                                                                  final String sortBy,
                                                                  final String sortOrder) throws DAOException;

}
