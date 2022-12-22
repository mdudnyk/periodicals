package com.periodicals.dao.mysql;

import com.periodicals.dao.exception.DAOException;
import com.periodicals.dao.UserDAO;
import com.periodicals.entity.User;
import com.periodicals.entity.enums.UserRole;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A class for interacting with the {@code user} table in the {@code MySql} database.
 */
public class UserDAOMySql implements UserDAO {
    private static final Logger LOG = LogManager.getLogger(UserDAOMySql.class);

    /**
     * Inserts {@link User} entity into the database. User {@code email} value should be unique.
     *
     * @throws NullPointerException in case of {@link User} or {@link Connection} parameter is {@code null}.
     * @throws DAOException         in case of error at the database side.
     */
    @Override
    public void create(final User entity, Connection connection) throws DAOException {
        try (PreparedStatement ps = connection.prepareStatement(Queries.CREATE_USER, Statement.RETURN_GENERATED_KEYS)) {
            fillPreparedStatement(ps, entity);
            if (ps.executeUpdate() > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        entity.setId(rs.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            LOG.error("Can not add new user with email="
                    + entity.getEmail() + " to the database. " + e.getMessage());
            throw new DAOException("Can not add new user to the database.");
        }
        LOG.debug("User with email=" + entity.getEmail() + " successfully added to the database. " +
                "His user ID was set to the value=" + entity.getId()
                + " that was automatically generated by the database");
    }

    /**
     * Returns an {@link ArrayList} with all {@link User} entities presented in the database.
     *
     * @return an {@link ArrayList} with all {@link User} entities presented in the database or an
     * empty {@code ArrayList} when there is no user entities presented in the database.
     * @throws NullPointerException in case of {@link Connection} parameter is {@code null}.
     * @throws DAOException         in case of error on database side.
     */
    @Override
    public List<User> getAll(Connection connection) throws DAOException {
        List<User> users = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(Queries.GET_ALL_USERS);
             ResultSet resultSet = ps.executeQuery()) {
            while (resultSet.next()) {
                users.add(fillEntityFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            LOG.error("Did not retrieve users list from database. " + e.getMessage());
            throw new DAOException("Error while trying to retrieve users list from database.");
        }

        LOG.debug("All users list successfully retrieved from database. " +
                "List size=" + users.size());
        return users;
    }

    /**
     * Returns a {@link User} entity presented in the database by it`s {@code id} value.
     *
     * @param id is {@code integer} value with unique user ID.
     * @return a {@link User} entity presented in the database or {@code null} if there is
     * no user with this ID storing in database.
     * @throws NullPointerException in case of {@link Connection} parameter is {@code null}.
     * @throws DAOException         in case of error on database side.
     */
    @Override
    public User getEntityById(final Integer id, Connection connection) throws DAOException {
        User user = null;

        try (PreparedStatement ps = connection.prepareStatement(Queries.GET_USER_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = fillEntityFromResultSet(rs);
            }
            rs.close();
        } catch (SQLException e) {
            LOG.error("Did not retrieve user by it`s id=" + id + " from database. " + e.getMessage());
            throw new DAOException("Error while trying to retrieve user by it`s id=" + id + ".");
        }

        if (user != null) {
            LOG.debug("User with id=" + id + " successfully retrieved from database. " +
                    "His email=" + user.getEmail());
        } else {
            LOG.debug("User with id=" + id + " is not presented in database");
        }
        return user;
    }

    /**
     * Returns an {@link User} entity presented in the database by it`s {@code email} value.
     *
     * @param email is a {@link String} value with an unique user email address.
     * @return an {@link User} entity presented in the database or {@code null} if there is
     * no user with this email storing in database.
     * @throws NullPointerException in case of {@link Connection} parameter is {@code null}.
     * @throws DAOException         in case of error on database side.
     */
    @Override
    public User getUserByEmail(final String email, Connection connection) throws DAOException {
        User user = null;

        try (PreparedStatement ps = connection.prepareStatement(Queries.GET_USER_BY_EMAIL)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery();) {
                if (rs.next()) {
                    user = fillEntityFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            LOG.error("Did not retrieve user by it`s email=" + email + " from database. " + e.getMessage());
            throw new DAOException("Error while trying to retrieve user by it`s email=" + email);
        }

        if (user != null) {
            LOG.debug("User with email=" + email + " and id=" + user.getId()
                    + " successfully retrieved from database");
        } else {
            LOG.debug("User with email=" + email + " is not presented in database");
        }
        return user;
    }

    /**
     * Returns an {@link ArrayList} with {@link User} entities only where {@link UserRole} is equals to {@code CUSTOMER}
     * presented in the database and according to the method parameters.
     *
     * @param positionsToSkip how many rows in user result table we need to skip.
     *                        {@code OFFSET} command in MySQL.
     * @param amountOnPage    maximum amount of user objects that can be in the resulting list.
     *                        {@code LIMIT} command in MySQL.
     * @param sortBy          {@link String} value with name of database column which will be used for sorting.
     *                        {@code ORDER BY} command in MySQL. There are only 4 columns to be chosen for sorting:
     *                        {@code NAME, EMAIL, BALANCE, ID} (case-insensitive). If you insert another value or {@code null} it
     *                        will automatically sort by {@code NAME} column.
     * @param sortOrder       {@link String} value with a sorting order. There are only 2 parameters that can be set sorting:
     *                        {@code ASK, DESK} (case-insensitive). If you insert another value or {@code null} it
     *                        will automatically sort in {@code ASK} order.
     * @return an {@link ArrayList} with {@link User} entities only where {@link UserRole} is equals to {@code CUSTOMER}
     * presented in the database and according to the method parameters or an empty {@code ArrayList}
     * when there is no entities.
     * @throws NullPointerException in case of {@link Connection} parameter is {@code null}.
     * @throws DAOException         in case of error on database side.
     */
    @Override
    public List<User> getCustomersPagination(final int positionsToSkip,
                                             final int amountOnPage,
                                             final String sortBy,
                                             final String sortOrder,
                                             final Connection connection) throws DAOException {
        List<User> customers = new ArrayList<>();
        String query = getSQLQueryWithSortingOrder(sortOrder, false);
        int sortingColumnNumber = getSortingColumnNumber(sortBy);

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setObject(1, sortingColumnNumber);
            ps.setInt(2, amountOnPage);
            ps.setInt(3, positionsToSkip);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    customers.add(fillEntityFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            LOG.error("Did not retrieve customers list from database. " +
                    "SKIP=" + positionsToSkip + ", AMOUNT=" + amountOnPage +
                    ", SORT_BY=" + sortBy + ", ORDER=" + sortOrder + ". " + e.getMessage());
            throw new DAOException("Error while trying to retrieve list of customers (pagination mode).");
        }

        LOG.debug("List of customers successfully retrieved from database (pagination mode). " +
                "List size=" + customers.size());
        return customers;
    }

    /**
     * Returns an {@link ArrayList} with {@link User} entities only where {@link UserRole} is equals to {@code CUSTOMER}
     * presented in the database and according to the method parameters.
     *
     * @param searchString    {@link String} value with searched user parameter. {@code WHERE} command in MySQL.
     *                        SQL query will search for matches in 4 columns: {@code ID, FIRSTNAME, LASTNAME, EMAIL}.
     * @param positionsToSkip how many rows in user result table we need to skip.
     *                        {@code OFFSET} command in MySQL.
     * @param amountOnPage    maximum amount of user objects that can be in the resulting list.
     *                        {@code LIMIT} command in MySQL.
     * @param sortBy          {@link String} value with name of database column which will be used for sorting.
     *                        {@code ORDER BY} command in MySQL. There are only 4 columns to be chosen for sorting:
     *                        {@code NAME, EMAIL, BALANCE, ID} (case-insensitive). If you insert another value or {@code null} it
     *                        will automatically sort by {@code NAME} column.
     * @param sortOrder       {@link String} value with a sorting order. There are only 2 parameters that can be set sorting:
     *                        {@code ASK, DESK} (case-insensitive). If you insert another value or {@code null} it
     *                        will automatically sort in {@code ASK} order.
     * @return an {@link ArrayList} with {@link User} entities only where {@link UserRole} is equals to {@code CUSTOMER}
     * presented in the database and according to the method parameters or an empty {@code ArrayList}
     * when there is no entities.
     * @throws NullPointerException in case of {@link Connection} parameter is {@code null}.
     * @throws DAOException         in case of error on database side.
     */
    @Override
    public List<User> getCustomersPagination(final String searchString,
                                             final int positionsToSkip,
                                             final int amountOnPage,
                                             final String sortBy,
                                             final String sortOrder,
                                             final Connection connection) throws DAOException {
        List<User> customers = new ArrayList<>();
        String query = getSQLQueryWithSortingOrder(sortOrder, true);
        int sortingColumnNumber = getSortingColumnNumber(sortBy);

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, searchString);
            ps.setString(2, searchString);
            ps.setString(3, searchString);
            ps.setString(4, searchString);
            ps.setObject(5, sortingColumnNumber);
            ps.setInt(6, amountOnPage);
            ps.setInt(7, positionsToSkip);
            try (ResultSet rs = ps.executeQuery();) {
                while (rs.next()) {
                    customers.add(fillEntityFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            LOG.error("Did not retrieve customers list from database. " +
                    "SEARCH=" + searchString + ", SKIP=" + positionsToSkip + ", AMOUNT=" + amountOnPage +
                    ", SORT_BY=" + sortBy + ", ORDER=" + sortOrder + ". " + e.getMessage());
            throw new DAOException("Error while trying to retrieve list of customers (pagination and search mode).");
        }

        LOG.debug("List of customers successfully retrieved from database (pagination and search mode). " +
                "List size=" + customers.size());
        return customers;
    }

    /**
     * Returns a total amount of {@link User} entities with {@link UserRole} equals to {@code CUSTOMER}
     * that are presented in the database.
     *
     * @return a total amount of users with customer role that are presented in the database.
     * @throws NullPointerException in case of {@link Connection} parameter is {@code null}.
     * @throws DAOException         in case of error on database side.
     */
    @Override
    public int getCustomersAmount(final Connection connection) throws DAOException {
        int count = 0;

        try (Statement stmt = connection.createStatement();
             ResultSet resultSet = stmt.executeQuery(Queries.GET_CUSTOMERS_AMOUNT)) {
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            LOG.error("Did not retrieve customers amount from database. " + e.getMessage());
            throw new DAOException("Error while trying to retrieve amount of customers.");
        }

        LOG.debug("Amount of customers successfully retrieved from database and equals " + count);
        return count;
    }

    /**
     * Returns a total amount of {@link User} entities in database with {@link UserRole} equals to {@code CUSTOMER}
     * and matching the search query.
     *
     * @param searchString {@link String} value with searched user parameter. {@code WHERE} command in MySQL.
     *                     SQL query will search matches in 4 columns: {@code ID, FIRSTNAME, LASTNAME, EMAIL}.
     * @return a total amount of users in database with customer role and matching the search query.
     * @throws NullPointerException in case of {@link Connection} parameter is {@code null}.
     * @throws DAOException         in case of error on database side.
     */
    @Override
    public int getCustomersAmount(final String searchString, final Connection connection) throws DAOException {
        int count = 0;
        try (PreparedStatement ps = connection.prepareStatement(Queries.GET_CUSTOMERS_AMOUNT_SEARCH_MODE)) {
            ps.setString(1, searchString);
            ps.setString(2, searchString);
            ps.setString(3, searchString);
            ps.setString(4, searchString);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    count = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            LOG.error("Did not retrieve customers amount from database (search mode). " + e.getMessage());
            throw new DAOException("Error while trying to retrieve amount of customers (search mode).");
        }

        LOG.debug("Amount of customers matching search query=" + searchString +
                " successfully retrieved from database and equals " + count);
        return count;
    }

    /**
     * Updates an {@link User} entity presented in the database.
     *
     * @throws NullPointerException in case of {@link Connection} or {@link User} parameter is {@code null}.
     * @throws DAOException         in case of error on database side or when trying to update
     *                              entity that is not presented in database.
     */
    @Override
    public void update(final User entity, Connection connection) throws DAOException {
        try (PreparedStatement ps = connection.prepareStatement(Queries.UPDATE_USER)) {
            fillPreparedStatement(ps, entity);
            ps.setInt(9, entity.getId());
            if (ps.executeUpdate() < 1) {
                LOG.error("User with id=" + entity.getId() + " does not present in database");
                throw new DAOException("We don`t have such user.");
            }
        } catch (SQLException e) {
            LOG.error("Can not update un existing user. " + e.getMessage());
            throw new DAOException("Can not update user with ID=" + entity.getId() + " in database.");
        }

        LOG.debug("User with id=" + entity.getId() + " was successfully updated");
    }

    /**
     * Deletes a {@link User} entity from the database by it`s id.
     *
     * @throws NullPointerException in case of {@link Connection} parameter is {@code null}.
     * @throws DAOException         in case of error on database side or when trying to delete
     *                              entity that is not presented in database.
     */
    @Override
    public void delete(final Integer id, Connection connection) throws DAOException {
        try (PreparedStatement ps = connection.prepareStatement(Queries.DELETE_USER)) {
            ps.setInt(1, id);
            if (ps.executeUpdate() < 1) {
                LOG.error("User with id=" + id + " does not present in database");
                throw new DAOException("We don`t have such user.");
            }
        } catch (SQLException e) {
            LOG.error("Can not delete un existing user. " + e.getMessage());
            throw new DAOException("Can not delete user with ID=" + id + " from database. ");
        }

        LOG.debug("User with id=" + id + " was successfully deleted");
    }

    /**
     * Helper method to set parameters of the {@link PreparedStatement} object from the
     * presented {@link User} entity.
     *
     * @throws NullPointerException in case of {@link User} parameter is {@code null}.
     * @throws SQLException         in case of error when setting parameters of the {@link PreparedStatement} object.
     */
    private void fillPreparedStatement(PreparedStatement ps, User entity) throws SQLException {
        ps.setString(1, entity.getLocaleId());
        ps.setString(2, entity.getFirstname());
        ps.setString(3, entity.getLastname());
        ps.setString(4, entity.getPassword());
        ps.setString(5, entity.getEmail());
        ps.setString(6, entity.getRole().name());
        ps.setInt(7, entity.getBalance());
        ps.setBoolean(8, entity.isBlocked());
    }

    /**
     * Helper method to create an {@link User} object using data stored in the {@link ResultSet} object.
     *
     * @return {@link User} object retrieved from {@link ResultSet}.
     * @throws NullPointerException in case of {@link ResultSet} parameter is {@code null}.
     * @throws SQLException         in case of error when retrieves the value from the {@link ResultSet} object.
     */
    private User fillEntityFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(1);
        String localeId = resultSet.getString(2);
        String firstName = resultSet.getString(3);
        String lastName = resultSet.getString(4);
        String password = resultSet.getString(5);
        String email = resultSet.getString(6);
        UserRole userRole = UserRole.valueOf(resultSet.getString(7));
        int balance = resultSet.getInt(8);
        boolean isBlocked = resultSet.getBoolean(9);

        return new User(id, localeId, firstName, lastName, password, email, userRole, balance, isBlocked);
    }

    /**
     * Helper method that returns SQL query string according to the input {@code sortOrder} parameter.
     *
     * @param sortOrder          {@link String} value with a sorting order. There are only 2 correct
     *                           variations of this parameter: {@code ASK, DESK} (case-insensitive). If you insert another
     *                           value or {@code null} it will automatically return SQL query string for {@code ASK} order.
     * @param searchModeIsActive determines which SQL query will return (with search string parameter or without).
     * @return SQL query string.
     */
    private String getSQLQueryWithSortingOrder(final String sortOrder, boolean searchModeIsActive) {
        if (searchModeIsActive) {
            return sortOrder != null && sortOrder.equalsIgnoreCase("DESC")
                    ? Queries.SEARCH_CUSTOMERS_PAGINATION_DESC
                    : Queries.SEARCH_CUSTOMERS_PAGINATION_ASC;
        } else {
            return sortOrder != null && sortOrder.equalsIgnoreCase("DESC")
                    ? Queries.GET_CUSTOMERS_PAGINATION_DESC
                    : Queries.GET_CUSTOMERS_PAGINATION_ASC;
        }
    }

    /**
     * Helper method that returns index of sorting column in user table.
     *
     * @param sortBy {@link String} value with name of the database column which will be used for sorting.
     *               There are only 4 columns to be chosen for sorting: {@code NAME, EMAIL, BALANCE, ID}
     *               (case-insensitive). If you insert another value or {@code null} it will automatically
     *               return 3 - index of {@code NAME} column in {@code user} table.
     * @return index of sorting column.
     */
    private int getSortingColumnNumber(final String sortBy) {
        return sortBy != null ?
                (sortBy.equalsIgnoreCase("name") ? 3
                        : sortBy.equalsIgnoreCase("email") ? 6
                        : sortBy.equalsIgnoreCase("balance") ? 8
                        : sortBy.equalsIgnoreCase("id") ? 1 : 3)
                : 3;
    }
}