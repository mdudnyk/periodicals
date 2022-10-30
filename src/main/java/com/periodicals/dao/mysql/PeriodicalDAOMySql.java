package com.periodicals.dao.mysql;

import com.periodicals.dao.PeriodicalDAO;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.entity.Periodical;
import com.periodicals.entity.PeriodicalForHomePage;
import com.periodicals.entity.PeriodicalForTable;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PeriodicalDAOMySql implements PeriodicalDAO {
    @Override
    public void create(final Periodical entity, final Connection connection) throws DAOException {
        try (PreparedStatement ps = connection.prepareStatement(Queries.CREATE_PERIODICAL, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, entity.getTopicID());
            ps.setString(2, entity.getTitle());
            ps.setString(3, entity.getTitleImgLink());
            ps.setInt(4, entity.getPrice());
            ps.setString(5, entity.getFrequency().toJSONString());
            ps.setBoolean(6, entity.isActive());
            if (ps.executeUpdate() > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        entity.setId(rs.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error while trying to create new periodical with title="
                    + entity.getTitle() + ". " + e.getMessage());
        }
    }

    @Override
    public List<Periodical> getAll(final Connection connection) throws DAOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Periodical getEntityById(final Integer id, final Connection connection) throws DAOException {
        Periodical periodical = null;
        try (PreparedStatement ps = connection.prepareStatement(Queries.GET_PERIODICAL_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int topicId = rs.getInt(2);
                String title = rs.getString(3);
                String imageUrl = rs.getString(4);
                int price = rs.getInt(5);
                JSONObject frequency = (JSONObject) new JSONParser().parse(rs.getString(6));
                boolean status = rs.getBoolean(7);
                periodical = new Periodical(id, topicId, title, imageUrl, price, frequency, status);
            }
            rs.close();
        } catch (SQLException | ParseException e) {
            throw new DAOException("Error while trying to get periodical with id=" + id + ". " + e.getMessage());
        }
        return periodical;
    }

    @Override
    public void update(final Periodical entity, final Connection connection) throws DAOException {
        try (PreparedStatement ps = connection.prepareStatement(Queries.UPDATE_PERIODICAL)) {
            ps.setInt(1, entity.getTopicID());
            ps.setString(2, entity.getTitle());
            ps.setString(3, entity.getTitleImgLink());
            ps.setInt(4, entity.getPrice());
            ps.setString(5, entity.getFrequency().toJSONString());
            ps.setBoolean(6, entity.isActive());
            ps.setInt(7, entity.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Can not update periodical with id="
                    + entity.getId() + ". "+ e.getMessage());
        }
    }

    @Override
    public void updateWithoutImage(final Periodical entity, final Connection connection) throws DAOException {
        try (PreparedStatement ps = connection.prepareStatement(Queries.UPDATE_PERIODICAL_WITHOUT_IMAGE)) {
            ps.setInt(1, entity.getTopicID());
            ps.setString(2, entity.getTitle());
            ps.setInt(3, entity.getPrice());
            ps.setString(4, entity.getFrequency().toJSONString());
            ps.setBoolean(5, entity.isActive());
            ps.setInt(6, entity.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Can not update periodical(without image) with id="
                    + entity.getId() + ". "+ e.getMessage());
        }
    }

    @Override
    public List<PeriodicalForHomePage> getPeriodicalsForHomePage(final int id, final Connection connection)
            throws DAOException {
        List<PeriodicalForHomePage> periodicals = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(Queries.GET_PERIODICALS_FOR_HOME_PAGE)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PeriodicalForHomePage periodical = new PeriodicalForHomePage(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4));
                periodicals.add(periodical);
            }
            rs.close();
        } catch (SQLException e) {
            throw new DAOException("Error while trying to get periodicals for " +
                    "home page with topic id=" + id + ". " + e.getMessage());
        }
        return periodicals;
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
                   rs.getBoolean(5)
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
                        rs.getBoolean(5)
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

    @Override
    public boolean getIsPeriodicalExists(Connection connection, final String title) throws DAOException {
        boolean exists = false;
        try (PreparedStatement ps = connection.prepareStatement(Queries.GET_IS_PERIODICAL_EXISTS)) {
            ps.setString(1, title);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (rs.getInt(1) == 1) {
                    exists = true;
                }
            }
            rs.close();
        } catch (SQLException e) {
            throw new DAOException("Error while trying to check if periodical with title="
                    + title + " exists. " + e.getMessage());
        }
        return exists;
    }

    @Override
    public boolean getIsPeriodicalExists(final Connection connection, final int id,
                                         final String title) throws DAOException {
        boolean exists = false;
        try (PreparedStatement ps = connection.prepareStatement(Queries.GET_IS_PERIODICAL_EXISTS_EXCEPT_ID)) {
            ps.setString(1, title);
            ps.setInt(2, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (rs.getInt(1) == 1) {
                    exists = true;
                }
            }
            rs.close();
        } catch (SQLException e) {
            throw new DAOException("Error while trying to check if periodical with title="
                    + title + " and !id= " + id + "exists. " + e.getMessage());
        }
        return exists;
    }
}