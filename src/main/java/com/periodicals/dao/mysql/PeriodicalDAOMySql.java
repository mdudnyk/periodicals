package com.periodicals.dao.mysql;

import com.periodicals.dao.PeriodicalDAO;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.entity.Periodical;
import com.periodicals.entity.PeriodicalForTable;

import java.sql.*;
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
        try (PreparedStatement ps = connection.prepareStatement(Queries.DELETE_PERIODICAL)) {
            ps.setInt(1, id);
            if (ps.executeUpdate() < 1) {
                throw new DAOException("We don`t have such periodical. ");
            }
        } catch (SQLException e) {
            throw new DAOException("Can not delete periodical with ID=" + id + " from database. "
                    + e.getMessage());
        }
    }

    @Override
    public List<PeriodicalForTable> getPeriodicalsForTableSortPag(
            final Connection connection, final String locale, final String defaultLocale,
            final int skip, final int amount, final String sortBy, final String order) throws DAOException {
        List<PeriodicalForTable> periodicals = new ArrayList<>();
        String query = order.equalsIgnoreCase("DESC")
                ? Queries.GET_PERIODICALS_FOR_TABLE_PAGINATION_DESC
                : Queries.GET_PERIODICALS_FOR_TABLE_PAGINATION_ASC;
            int sortingColumnNumber = sortBy.equalsIgnoreCase("title") ? 2
                    : sortBy.equalsIgnoreCase("topic") ? 3
                    : sortBy.equalsIgnoreCase("price") ? 4
                    : sortBy.equalsIgnoreCase("status") ? 5 : 2;

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, locale);
            ps.setString(2, defaultLocale);
            ps.setObject(3, sortingColumnNumber);
            ps.setInt(4, amount);
            ps.setInt(5, skip);

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
            rs.close();
        } catch (SQLException e) {
            throw new DAOException("Error while trying to get list of 'periodicals for table' with pagination. "
                    + e.getMessage());
        }
        return periodicals;
    }

    @Override
    public List<PeriodicalForTable> getPeriodicalsForTableByTitleSortPag(
            final Connection connection, final String locale, final String defaultLocale,
            final int skip, final int amount, final String sortBy, final String order,
            final String searchedTitle) throws DAOException {
        List<PeriodicalForTable> periodicals = new ArrayList<>();
        String query = order.equalsIgnoreCase("DESC")
                ? Queries.GET_PERIODICALS_FOR_TABLE_BY_TITLE_PAGINATION_DESC
                : Queries.GET_PERIODICALS_FOR_TABLE_BY_TITLE_PAGINATION_ASC;
        int sortingColumnNumber = sortBy.equalsIgnoreCase("title") ? 2
                : sortBy.equalsIgnoreCase("topicName") ? 3
                : sortBy.equalsIgnoreCase("price") ? 4
                : sortBy.equalsIgnoreCase("status") ? 5 : 2;

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, locale);
            ps.setString(2, defaultLocale);
            ps.setString(3, searchedTitle);
            ps.setInt(4, sortingColumnNumber);
            ps.setInt(5, amount);
            ps.setInt(6, skip);

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
            rs.close();
        } catch (SQLException e) {
            throw new DAOException("Error while trying to get list of 'periodicals for " +
                    "table' by title with pagination. " + e.getMessage());
        }
        return periodicals;
    }

    @Override
    public int getPeriodicalsAmount(final Connection connection) throws DAOException {
        int count = 0;
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(Queries.GET_PERIODICALS_COUNT)) {
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new DAOException("Error while trying to get all periodicals count from database. " + e.getMessage());
        }
        return count;
    }

    @Override
    public int getPeriodicalsAmountSearchMode(final Connection connection, final String searchQuery)
            throws DAOException {
        int count = 0;
        try (PreparedStatement ps = connection.prepareStatement(Queries.GET_PERIODICALS_COUNT_SEARCH_MODE)) {
            ps.setString(1, searchQuery);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DAOException("Error while trying to get all periodicals count " +
                    "(in search mode) from database. " + e.getMessage());
        }
        return count;
    }
}

