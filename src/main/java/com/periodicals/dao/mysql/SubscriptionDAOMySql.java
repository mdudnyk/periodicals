package com.periodicals.dao.mysql;

import com.periodicals.dao.SubscriptionDAO;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.entity.Periodical;
import com.periodicals.entity.Subscription;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SubscriptionDAOMySql implements SubscriptionDAO {
    @Override
    public void create(final Subscription entity, final Connection connection) throws DAOException {
        try (PreparedStatement ps = connection.prepareStatement(Queries.CREATE_SUBSCRIPTION, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, entity.getUserId());
            ps.setInt(2, entity.getPeriodicalId());
            ps.setString(3, entity.getPeriodicalTitle());
            ps.setInt(4, entity.getPrice());
            ps.setTimestamp(5, Timestamp.valueOf(entity.getCreatedAt()));
            ps.setTimestamp(6, Timestamp.valueOf(entity.getExpiredAt().atStartOfDay()));
            if (ps.executeUpdate() > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        entity.setId(rs.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error while trying to create new subscription for periodical with title="
                    + entity.getPeriodicalTitle() + ". " + e.getMessage());
        }
    }

    @Override
    public List<Subscription> getAll(final Connection connection) throws DAOException {
        return null;
    }

    @Override
    public Subscription getEntityById(final Integer id, final Connection connection) throws DAOException {
        Subscription subscription = null;

        try (PreparedStatement ps = connection.prepareStatement(Queries.GET_SUBSCRIPTION_BY_ID)){
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                subscription = fillEntityFromResultSet(rs);
            }
            rs.close();
        } catch (SQLException e) {
            throw new DAOException("Error while trying to get subscription with id="
                    + id + ". " + e.getMessage());
        }

        return subscription;
    }

    @Override
    public int getTotalAmountByUserIdAndSearchQuery(final int userId, final String searchQuery,
                                                    final Connection connection) throws DAOException {
        int count = 0;
        try (PreparedStatement ps = connection.prepareStatement(Queries.GET_SUBSCRIPTIONS_COUNT_BY_USER_ID_SEARCH_MODE)) {
            ps.setInt(1, userId);
            ps.setString(2, searchQuery);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DAOException("Error while trying to get amount of subscriptions " +
                    "for user with id=" + userId + " (in search mode) from database. " + e.getMessage());
        }
        return count;
    }

    @Override
    public int getTotalAmountByUserId(final int userId, final Connection connection) throws DAOException {
        int count = 0;
        try (PreparedStatement ps = connection.prepareStatement(Queries.GET_SUBSCRIPTIONS_COUNT_BY_USER_ID)) {
            ps.setInt(1, userId);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DAOException("Error while trying to get amount of subscriptions " +
                    "for user with id=" + userId + " from database. " + e.getMessage());
        }
        return count;
    }

    @Override
    public List<Subscription> getSubscriptionsByUserIdPagination(final int userId, final int positionsToSkip,
                                                                 final int amountOnPage, final String sortBy,
                                                                 final String sortOrder,
                                                                 final Connection connection) throws DAOException {
        List<Subscription> subscriptions = new ArrayList<>();
        String query = sortOrder.equalsIgnoreCase("DESC")
                ? Queries.GET_SUBSCRIPTIONS_BY_USER_ID_PAGINATION_DESC
                : Queries.GET_SUBSCRIPTIONS_BY_USER_ID_PAGINATION_ASC;
        int sortingColumnNumber = sortBy.equalsIgnoreCase("title") ? 4
                : sortBy.equalsIgnoreCase("price") ? 5
                : sortBy.equalsIgnoreCase("createdAt") ? 6
                : sortBy.equalsIgnoreCase("expiredAt") ? 7 : 4;

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, userId);
            ps.setObject(2, sortingColumnNumber);
            ps.setInt(3, amountOnPage);
            ps.setInt(4, positionsToSkip);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                subscriptions.add(fillEntityFromResultSet(rs));
            }
            rs.close();
        } catch (SQLException e) {
            throw new DAOException("Error while trying to get list of subscriptions with pagination " +
                    "for user with id=" + userId + ". " + e.getMessage());
        }
        return subscriptions;
    }

    @Override
    public List<Subscription> getSubscriptionsByUserIdPagination(final int userId, final String searchString,
                                                                 final int positionsToSkip, final int amountOnPage,
                                                                 final String sortBy,
                                                                 final String sortOrder,
                                                                 final Connection connection) throws DAOException {
        List<Subscription> subscriptions = new ArrayList<>();
        String query = sortOrder.equalsIgnoreCase("DESC")
                ? Queries.GET_SUBSCRIPTIONS_BY_USER_ID_AND_TITLE_PAGINATION_DESC
                : Queries.GET_SUBSCRIPTIONS_BY_USER_ID_AND_TITLE_PAGINATION_ASC;
        int sortingColumnNumber = sortBy.equalsIgnoreCase("title") ? 4
                : sortBy.equalsIgnoreCase("price") ? 5
                : sortBy.equalsIgnoreCase("createdAt") ? 6
                : sortBy.equalsIgnoreCase("expiredAt") ? 7 : 4;

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, userId);
            ps.setString(2, searchString);
            ps.setObject(3, sortingColumnNumber);
            ps.setInt(4, amountOnPage);
            ps.setInt(5, positionsToSkip);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                subscriptions.add(fillEntityFromResultSet(rs));
            }
            rs.close();
        } catch (SQLException e) {
            throw new DAOException("Error while trying to get list of subscriptions with pagination " +
                    "for user with id=" + userId + " and searched title_name=" + searchString + e.getMessage());
        }
        return subscriptions;
    }

    @Override
    public void update(final Subscription entity, final Connection connection) throws DAOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(final Integer id, final Connection connection) throws DAOException {
        try (PreparedStatement ps = connection.prepareStatement(Queries.DELETE_SUBSCRIPTION)) {
            ps.setInt(1, id);
            if (ps.executeUpdate() < 1) {
                throw new DAOException("We don`t have such subscription.");
            }
        } catch (SQLException e) {
            throw new DAOException("Can not delete subscription with id=" + id
                    + "from database. " + e.getMessage());
        }
    }

    private Subscription fillEntityFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(1);
        int userId = resultSet.getInt(2);
        int periodicalId = resultSet.getInt(3);
        String title = resultSet.getString(4);
        int price = resultSet.getInt(5);
        LocalDateTime createdAt = resultSet.getTimestamp(6).toLocalDateTime();
        LocalDate expiredAt = resultSet.getTimestamp(7).toLocalDateTime().toLocalDate();
        return new Subscription(id, userId, periodicalId, title, price, createdAt, expiredAt);
    }
}