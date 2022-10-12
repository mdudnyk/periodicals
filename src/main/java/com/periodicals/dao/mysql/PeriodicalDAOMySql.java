package com.periodicals.dao.mysql;

import com.periodicals.dao.PeriodicalDAO;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.entity.Periodical;
import com.periodicals.entity.PeriodicalForTable;
import com.periodicals.entity.Topic;
import com.periodicals.entity.TopicTranslate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PeriodicalDAOMySql implements PeriodicalDAO {
    @Override
    public void create(final Periodical entity, final Connection connection) throws DAOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Periodical> getAll(final Connection connection) throws DAOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Periodical getEntityById(final Integer id, final Connection connection) throws DAOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(final Periodical entity, final Connection connection) throws DAOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(final Integer id, final Connection connection) throws DAOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<PeriodicalForTable> getPeriodicalsForTableSortPag(
            final Connection connection, final String locale, final String defaultLocale,
            final int skip, final int amount, final String sortBy, final String order) throws DAOException {
        List<PeriodicalForTable> periodicals = new ArrayList<>();
        String query = order.equals("DESC") ? Queries.GET_PERIODICALS_FOR_TABLE_PAGINATION_DESC :
                Queries.GET_PERIODICALS_FOR_TABLE_PAGINATION_ASC;
            int sortingColumnNumber = sortBy.equalsIgnoreCase("title") ? 2
                    : sortBy.equalsIgnoreCase("topicName") ? 3
                    : sortBy.equalsIgnoreCase("price") ? 4
                    : sortBy.equalsIgnoreCase("status") ? 5 : 2;

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, locale);
            ps.setString(2, defaultLocale);
            ps.setInt(3, sortingColumnNumber);
            ps.setInt(4, amount);
            ps.setInt(5, skip);

            System.out.println(sortBy);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                periodicals.add(new PeriodicalForTable(
                   rs.getInt(1),
                   rs.getString(2),
                   rs.getString(3),
                   rs.getInt(4),
                   rs.getString(5)
                ));
            }
        } catch (SQLException e) {
            throw new DAOException("Error while trying to get list of periodicals for table with pagination. "
                    + e.getMessage());
        }
        return periodicals;
    }
}

