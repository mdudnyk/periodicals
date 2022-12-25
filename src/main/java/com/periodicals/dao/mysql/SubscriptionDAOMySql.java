package com.periodicals.dao.mysql;

import com.periodicals.dao.interfacesForDAO.SubscriptionDAO;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.entity.Subscription;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * A class for interacting with the {@code subscription} table in the {@code MySql} database.
 */
public class SubscriptionDAOMySql implements SubscriptionDAO {
    private static final Logger LOG = LogManager.getLogger(SubscriptionDAOMySql.class);

    /**
     * Inserts {@link Subscription} entity into the database.
     * Database returns generated {@code subscription ID} and this method sets it to the presented Subscription entity.
     *
     * @throws NullPointerException in case of {@link Subscription} or {@link Connection} parameter is {@code null}.
     * @throws DAOException         in case of error at the database side.
     */
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
            LOG.error("Can not add new subscription with periodical id="
                    + entity.getPeriodicalId() + " and user id=" + entity.getUserId() +
                    " to the database. " + e.getMessage());
            throw new DAOException("Error while trying to create new subscription for periodical with id="
                    + entity.getPeriodicalId() + ".");
        }

        LOG.debug("The subscription with periodical id=" + entity.getPeriodicalTitle() + " and user id="
                + entity.getUserId() + " was successfully added to the database. " +
                "It`s id was set to " + entity.getId());
    }

    /**
     * This method does not work due to lack of necessity.
     *
     * @throws UnsupportedOperationException always.
     */
    @Override
    public List<Subscription> getAll(final Connection connection) throws DAOException {
        LOG.error("Method does not work due to lack of necessity");
        throw new UnsupportedOperationException();
    }

    /**
     * Returns a {@link Subscription} entity presented in the database by it`s {@code id} value.
     *
     * @param id is a unique {@code Integer} ID value of the {@link Subscription} object.
     * @return a {@link Subscription} entity presented in the database or {@code null} if there is
     * no Subscription with this ID storing in database.
     * @throws NullPointerException in case of {@link Connection} parameter is {@code null}.
     * @throws DAOException         in case of error on database side.
     */
    @Override
    public Subscription getEntityById(final Integer id, final Connection connection) throws DAOException {
        Subscription subscription = null;

        try (PreparedStatement ps = connection.prepareStatement(Queries.GET_SUBSCRIPTION_BY_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    subscription = fillEntityFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            LOG.error("Did not retrieve subscription by it`s id=" + id + " from database. " + e.getMessage());
            throw new DAOException("Error while trying to get Subscription with id=" + id + ".");
        }

        if (subscription != null) {
            LOG.debug("Subscription with id=" + id + " successfully retrieved from database");
        } else {
            LOG.debug("Subscription with id=" + id + " is not presented in database");
        }
        return subscription;
    }

    /**
     * Returns a total amount of {@link Subscription} entities in database where the search
     * query matches periodical title for specified user id.
     *
     * @param userId          {@code integer} value with searched used id in subscription table.
     *                        {@code WHERE} command in MySQL.
     * @param periodicalTitle {@link String} value with searched periodical title in subscription table.
     *                        {@code WHERE} command in MySQL.
     * @return a total amount of subscription entities in database that are matching input method parameters.
     * @throws NullPointerException in case of {@link Connection} parameter is {@code null}.
     * @throws DAOException         in case of error on database side.
     */
    @Override
    public int getTotalAmountByUserIdAndSearchQuery(final int userId, final String periodicalTitle,
                                                    final Connection connection) throws DAOException {
        int count = 0;
        try (PreparedStatement ps = connection.prepareStatement(Queries.GET_SUBSCRIPTIONS_COUNT_BY_USER_ID_SEARCH_MODE)) {
            ps.setInt(1, userId);
            ps.setString(2, periodicalTitle);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    count = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            LOG.error("Did not retrieve subscriptions with periodical title matching '" + periodicalTitle +
                    "' and user id=" + userId + " from database. " + e.getMessage());
            throw new DAOException("Error while trying to retrieve amount of subscriptions.");
        }

        LOG.debug("Amount of subscriptions with periodical title matching '" + periodicalTitle +
                "' and user id=" + userId + " successfully retrieved from database and equals " + count);
        return count;
    }

    /**
     * Returns a total amount of {@link Subscription} entities in database for specified user id.
     *
     * @param userId {@code integer} value with searched used id in subscription table.
     *               {@code WHERE} command in MySQL.
     * @return a total amount of subscription entities in database that are matching input method parameter - user ID.
     * @throws NullPointerException in case of {@link Connection} parameter is {@code null}.
     * @throws DAOException         in case of error on database side.
     */
    @Override
    public int getTotalAmountByUserId(final int userId, final Connection connection) throws DAOException {
        int count = 0;
        try (PreparedStatement ps = connection.prepareStatement(Queries.GET_SUBSCRIPTIONS_COUNT_BY_USER_ID)) {
            ps.setInt(1, userId);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    count = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            LOG.error("Did not retrieve subscriptions with user id=" + userId + " from database. " + e.getMessage());
            throw new DAOException("Error while trying to retrieve amount of subscriptions.");
        }

        LOG.debug("Amount of subscriptions with user id=" + userId +
                " successfully retrieved from database and equals " + count);
        return count;
    }

    /**
     * Returns an {@link ArrayList} with {@link Subscription} entities only where {@code user id}
     * is equals to {@code userId} input parameter.
     *
     * @param userId          {@code integer} value with searched used id in subscription table
     *                        {@code WHERE} command in MySQL.
     * @param positionsToSkip how many rows in user result table we need to skip.
     *                        {@code OFFSET} command in MySQL.
     * @param amountOnPage    maximum amount of subscription objects that can be in the resulting list.
     *                        {@code LIMIT} command in MySQL.
     * @param sortBy          {@link String} value with name of database column which will be used for sorting.
     *                        {@code ORDER BY} command in MySQL. There are only 4 columns to be chosen for sorting:
     *                        {@code TITLE, PRICE, CREATED_AT, EXPIRED_AT} (case-insensitive).
     *                        If you insert another value or {@code null} it will automatically sort by {@code TITLE} column.
     * @param sortOrder       {@link String} value with a sorting order. There are only 2 parameters that can be set sorting:
     *                        {@code ASK, DESK} (case-insensitive). If you insert another value or {@code null} it
     *                        will automatically sort in {@code ASK} order.
     * @return an {@link ArrayList} with {@link Subscription} entities only where {@code user id}
     * is equals to {@code userId} input parameter. or an empty {@code ArrayList when there is no entities.
     * @throws NullPointerException in case of {@link Connection} parameter is {@code null}.
     * @throws DAOException         in case of error on database side.
     */
    @Override
    public List<Subscription> getSubscriptionsByUserIdPagination(final int userId, final int positionsToSkip,
                                                                 final int amountOnPage, final String sortBy,
                                                                 final String sortOrder,
                                                                 final Connection connection) throws DAOException {
        List<Subscription> subscriptions = new ArrayList<>();
        String query = getSQLQueryWithSortingOrder(sortOrder, false);
        int sortingColumnNumber = getSortingColumnNumber(sortBy);

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, userId);
            ps.setObject(2, sortingColumnNumber);
            ps.setInt(3, amountOnPage);
            ps.setInt(4, positionsToSkip);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    subscriptions.add(fillEntityFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            LOG.error("Did not retrieve subscriptions list from database. " +
                    "USER_ID=" + userId + ", SKIP=" + positionsToSkip + ", AMOUNT=" + amountOnPage +
                    ", SORT_BY=" + sortBy + ", ORDER=" + sortOrder + ". " + e.getMessage());
            throw new DAOException("Error while trying to retrieve list of subscriptions.");
        }

        LOG.debug("List of subscriptions with user id=" + userId + " successfully retrieved from " +
                "database (pagination mode). List size=" + subscriptions.size());
        return subscriptions;
    }

    /**
     * Returns an {@link ArrayList} with {@link Subscription} entities only where {@code user id}
     * is equals to {@code userId} input parameter.
     *
     * @param userId          {@code integer} value with searched used id in subscription table
     *                        {@code WHERE} command in MySQL.
     * @param periodicalTitle {@link String} value with searched periodical title in subscription table.
     *                        {@code WHERE} command in MySQL.
     * @param positionsToSkip how many rows in user result table we need to skip.
     *                        {@code OFFSET} command in MySQL.
     * @param amountOnPage    maximum amount of subscription objects that can be in the resulting list.
     *                        {@code LIMIT} command in MySQL.
     * @param sortBy          {@link String} value with name of database column which will be used for sorting.
     *                        {@code ORDER BY} command in MySQL. There are only 4 columns to be chosen for sorting:
     *                        {@code TITLE, PRICE, CREATED_AT, EXPIRED_AT} (case-insensitive).
     *                        If you insert another value or {@code null} it will automatically sort by {@code TITLE} column.
     * @param sortOrder       {@link String} value with a sorting order. There are only 2 parameters that can be set sorting:
     *                        {@code ASK, DESK} (case-insensitive). If you insert another value or {@code null} it
     *                        will automatically sort in {@code ASK} order.
     * @return an {@link ArrayList} with {@link Subscription} entities only where {@code user id}
     * is equals to {@code userId} input parameter. or an empty {@code ArrayList when there is no entities.
     * @throws NullPointerException in case of {@link Connection} parameter is {@code null}.
     * @throws DAOException         in case of error on database side.
     */
    @Override
    public List<Subscription> getSubscriptionsByUserIdPagination(final int userId, final String periodicalTitle,
                                                                 final int positionsToSkip, final int amountOnPage,
                                                                 final String sortBy,
                                                                 final String sortOrder,
                                                                 final Connection connection) throws DAOException {
        List<Subscription> subscriptions = new ArrayList<>();
        String query = getSQLQueryWithSortingOrder(sortOrder, true);
        int sortingColumnNumber = getSortingColumnNumber(sortBy);

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, userId);
            ps.setString(2, periodicalTitle);
            ps.setObject(3, sortingColumnNumber);
            ps.setInt(4, amountOnPage);
            ps.setInt(5, positionsToSkip);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    subscriptions.add(fillEntityFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            LOG.error("Did not retrieve subscriptions list from database. " +
                    "USER_ID=" + userId + ", PERIODICAL_TITLE=" + periodicalTitle + ", SKIP=" +
                    positionsToSkip + ", AMOUNT=" + amountOnPage + ", SORT_BY=" + sortBy +
                    ", ORDER=" + sortOrder + ". " + e.getMessage());
            throw new DAOException("Error while trying to retrieve list of subscriptions.");
        }

        LOG.debug("List of subscriptions where periodical title matches '" + periodicalTitle +
                "' and with user id=" + userId + " successfully retrieved from " +
                "database (pagination mode). List size=" + subscriptions.size());
        return subscriptions;
    }

    /**
     * This method does not work due to lack of necessity.
     *
     * @throws UnsupportedOperationException always.
     */
    @Override
    public void update(final Subscription entity, final Connection connection) throws DAOException {
        LOG.error("Method does not work due to lack of necessity");
        throw new UnsupportedOperationException();
    }

    /**
     * Deletes a {@link Subscription} entity from the database by it`s id.
     *
     * @throws NullPointerException in case of {@link Connection} parameter is {@code null}.
     * @throws DAOException         in case of error on database side.
     */
    @Override
    public void delete(final Integer id, final Connection connection) throws DAOException {
        try (PreparedStatement ps = connection.prepareStatement(Queries.DELETE_SUBSCRIPTION)) {
            ps.setInt(1, id);
            if (ps.executeUpdate() < 1) {
                throw new DAOException("We don`t have such subscription in database");
            }
        } catch (SQLException e) {
            LOG.error("Can not delete the subscription with id=" + id + ". " + e.getMessage());
            throw new DAOException("Can not delete subscription with id=" + id + " from database.");
        }

        LOG.debug("The subscription with id=" + id + " was successfully deleted");
    }

    /**
     * Helper method to create an {@link Subscription} object using data stored in the {@link ResultSet} object.
     *
     * @return {@link Subscription} object retrieved from {@link ResultSet}.
     * @throws NullPointerException in case of {@link ResultSet} parameter is {@code null}.
     * @throws SQLException         in case of error when retrieves the value from the {@link ResultSet} object.
     */
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

    /**
     * Helper method that returns SQL query string according to the input {@code sortOrder} parameter.
     *
     * @param sortOrder         {@link String} value with a sorting order. There are only 2 correct
     *                          variations of this parameter: {@code ASK, DESK} (case-insensitive). If you insert another
     *                          value or {@code null} it will automatically return SQL query string for {@code ASK} order.
     * @param isSearchedByTitle determines which SQL query will return (with searched title parameter or without).
     * @return SQL query string.
     */
    private String getSQLQueryWithSortingOrder(final String sortOrder, boolean isSearchedByTitle) {
        if (isSearchedByTitle) {
            return sortOrder != null && sortOrder.equalsIgnoreCase("DESC")
                    ? Queries.GET_SUBSCRIPTIONS_BY_USER_ID_AND_TITLE_PAGINATION_DESC
                    : Queries.GET_SUBSCRIPTIONS_BY_USER_ID_AND_TITLE_PAGINATION_ASC;
        } else {
            return sortOrder != null && sortOrder.equalsIgnoreCase("DESC")
                    ? Queries.GET_SUBSCRIPTIONS_BY_USER_ID_PAGINATION_DESC
                    : Queries.GET_SUBSCRIPTIONS_BY_USER_ID_PAGINATION_ASC;
        }
    }

    /**
     * Helper method that returns index of sorting column in {@code subscription} table.
     *
     * @param sortBy {@link String} value with name of the database column which will be used for sorting.
     *               There are only 4 columns to be chosen for sorting: {@code TITLE, PRICE, CREATED_AT, EXPIRED_AT}
     *               (case-insensitive). If you insert another value or {@code null} it will automatically
     *               return 4 - index of {@code TITLE} column in {@code subscription} table.
     * @return index of sorting column.
     */
    private int getSortingColumnNumber(final String sortBy) {
        return sortBy != null ?
                (sortBy.equalsIgnoreCase("title") ? 4
                        : sortBy.equalsIgnoreCase("price") ? 5
                        : sortBy.equalsIgnoreCase("createdAt") ? 6
                        : sortBy.equalsIgnoreCase("expiredAt") ? 7 : 4)
                : 4;
    }
}