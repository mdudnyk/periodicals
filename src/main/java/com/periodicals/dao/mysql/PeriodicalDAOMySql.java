package com.periodicals.dao.mysql;

import com.periodicals.dao.PeriodicalDAO;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A class for interacting with the {@code periodical} table in the {@code MySql} database.
 */
public class PeriodicalDAOMySql implements PeriodicalDAO {
    private static final Logger LOG = LogManager.getLogger(PeriodicalDAOMySql.class);

    /**
     * Inserts {@link Periodical} entity into the database. Periodical {@code title} value should be unique.
     * Database returns generated {@code periodical ID} and this method sets it to the presented Periodical entity.
     *
     * @throws NullPointerException in case of {@link Periodical} or {@link Connection} parameter is {@code null}.
     * @throws DAOException         in case of error at the database side.
     */
    @Override
    public void create(final Periodical entity, final Connection connection) throws DAOException {
        try (PreparedStatement ps = connection.prepareStatement(Queries.CREATE_PERIODICAL, Statement.RETURN_GENERATED_KEYS)) {
            fillPreparedStatement(ps, entity);
            if (ps.executeUpdate() > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        entity.setId(rs.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            LOG.error("Can not add new periodical with title="
                    + entity.getTitle() + " to the database. " + e.getMessage());
            throw new DAOException("Error while trying to create new periodical with title="
                    + entity.getTitle() + ".");
        }

        LOG.debug("The periodical with title=" + entity.getTitle() + " was successfully added to the database. " +
                "It`s id was set to " + entity.getId());
    }

    /**
     * This method does not work due to lack of necessity.
     *
     * @throws UnsupportedOperationException always.
     */
    @Override
    public List<Periodical> getAll(final Connection connection) throws DAOException {
        LOG.error("Method does not work due to lack of necessity");
        throw new UnsupportedOperationException();
    }

    /**
     * Returns a {@link Periodical} entity presented in the database by it`s {@code id} value.
     *
     * @param id is an unique {@code Integer} ID value of {@link Periodical} object.
     * @return a {@link Periodical} entity presented in the database or {@code null} if there is
     * no Periodical with this ID storing in database.
     * @throws NullPointerException in case of {@link Connection} parameter is {@code null}.
     * @throws DAOException         in case of error on database side.
     */
    @Override
    public Periodical getEntityById(final Integer id, final Connection connection) throws DAOException {
        Periodical periodical = null;

        try (PreparedStatement ps = connection.prepareStatement(Queries.GET_PERIODICAL_BY_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int topicId = rs.getInt(2);
                    String title = rs.getString(3);
                    String imageUrl = rs.getString(4);
                    int price = rs.getInt(5);
                    JSONObject frequency = (JSONObject) new JSONParser().parse(rs.getString(6));
                    boolean status = rs.getBoolean(7);
                    periodical = new Periodical(id, topicId, title, imageUrl, price, frequency, status);
                }
            }
        } catch (SQLException | ParseException e) {
            LOG.error("Did not retrieve periodical by it`s id=" + id + " from database. " + e.getMessage());
            throw new DAOException("Error while trying to get periodical with id=" + id + ".");
        }

        if (periodical != null) {
            LOG.debug("Periodical with id=" + id + " successfully retrieved from database");
        } else {
            LOG.debug("Periodical with id=" + id + " is not presented in database");
        }
        return periodical;
    }

    /**
     * Updates an {@link Periodical} entity presented in the database.
     *
     * @throws NullPointerException in case of {@link Connection} or {@link Periodical} parameter is {@code null}.
     * @throws DAOException         in case of error on database side or when trying to update
     *                              entity that is not represented in database.
     */
    @Override
    public void update(final Periodical entity, final Connection connection) throws DAOException {
        try (PreparedStatement ps = connection.prepareStatement(Queries.UPDATE_PERIODICAL)) {
            fillPreparedStatement(ps, entity);
            ps.setInt(7, entity.getId());
            if (ps.executeUpdate() < 1) {
                LOG.error("The periodical is not represented in the database");
                throw new DAOException("We don`t have such periodical.");
            }
        } catch (SQLException e) {
            LOG.error("Can not update periodical with id=" + entity.getId() + ". " + e.getMessage());
            throw new DAOException("Can not update periodical with id=" + entity.getId() + ".");
        }

        LOG.debug("The periodical with id=" + entity.getId() + " was successfully updated");
    }

    /**
     * Updates an {@link Periodical} entity presented in the database as like usual {@code update}
     * method except {@code title_image_link} value.
     *
     * @throws NullPointerException in case of {@link Connection} or {@link Periodical} parameter is {@code null}.
     * @throws DAOException         in case of error on database side or when trying to update
     *                              entity that is not represented in database.
     */
    @Override
    public void updateWithoutImage(final Periodical entity, final Connection connection) throws DAOException {
        try (PreparedStatement ps = connection.prepareStatement(Queries.UPDATE_PERIODICAL_WITHOUT_IMAGE)) {
            ps.setInt(1, entity.getTopicID());
            ps.setString(2, entity.getTitle());
            ps.setInt(3, entity.getPrice());
            ps.setString(4, entity.getFrequency().toJSONString());
            ps.setBoolean(5, entity.isActive());
            ps.setInt(6, entity.getId());
            if (ps.executeUpdate() < 1) {
                LOG.error("The periodical is not represented in the database");
                throw new DAOException("We don`t have such periodical.");
            }
        } catch (SQLException e) {
            LOG.error("Can not update periodical with id=" + entity.getId() + ". " + e.getMessage());
            throw new DAOException("Can not update periodical with id=" + entity.getId() + ".");
        }

        LOG.debug("The periodical with id=" + entity.getId() + " was successfully updated");
    }

    /**
     * Returns an {@link ArrayList} with from 0 up to 4 {@link PeriodicalForHomePage} objects of Periodical
     * entities presented in the database with a specified {@link Topic} {@code ID}.
     *
     * @param topicId {@link Topic} {@code ID} of periodicals you wish to retrieve.
     * @return an {@link ArrayList} with max 4 {@link PeriodicalForHomePage} objects  of Periodical
     * entities with a specified {@link Topic} {@code ID} presented in the database or an empty {@code ArrayList}
     * when there is no periodical entities storing in database.
     * @throws NullPointerException in case of {@link Connection} parameter is {@code null}.
     * @throws DAOException         in case of error on database side.
     */
    @Override
    public List<PeriodicalForHomePage> getPeriodicalsForHomePage(final int topicId, final Connection connection)
            throws DAOException {
        List<PeriodicalForHomePage> periodicals = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(Queries.GET_PERIODICALS_FOR_HOME_PAGE)) {
            ps.setInt(1, topicId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PeriodicalForHomePage periodical = new PeriodicalForHomePage(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getInt(4));
                    periodicals.add(periodical);
                }
            }
        } catch (SQLException e) {
            LOG.error("Did not retrieve periodicals for home page specified by topic " +
                    "with id=" + topicId + ". " + e.getMessage());
            throw new DAOException("Error while trying to get periodicals for " +
                    "home page with topic id=" + topicId + ". ");
        }

        LOG.debug("Periodicals for home page list successfully retrieved from database. " +
                "List size=" + periodicals.size());
        return periodicals;
    }

    /**
     * Deletes a {@link Periodical} entity from the database by it`s id.
     *
     * @throws NullPointerException in case of {@link Connection} parameter is {@code null}.
     * @throws DAOException         in case of error on database side.
     */
    @Override
    public void delete(final Integer id, final Connection connection) throws DAOException {
        try (PreparedStatement ps = connection.prepareStatement(Queries.DELETE_PERIODICAL)) {
            ps.setInt(1, id);
            if (ps.executeUpdate() < 1) {
                LOG.error("The periodical is not represented in the database");
                throw new DAOException("We don`t have such periodical. ");
            }
        } catch (SQLException e) {
            LOG.error("Can not delete periodical with id=" + id + ". " + e.getMessage());
            throw new DAOException("Can not delete periodical with id=" + id + " from database.");
        }

        LOG.debug("The periodical with id=" + id + " was successfully deleted");
    }

    /**
     * Returns an {@link ArrayList} with {@link PeriodicalForTable} entities presented in the database
     * according to the method parameters.
     *
     * @param locale        preferable {@link LocaleCustom} ID of {@link PeriodicalTranslate} objects to looking for in database.
     * @param defaultLocale alternative {@link LocaleCustom} ID of {@link PeriodicalTranslate} objects to looking for in database.
     * @param skip          how many rows in periodical result table we need to skip.
     *                      {@code OFFSET} command in MySQL.
     * @param amount        maximum amount of periodical objects that can be in the resulting list.
     *                      {@code LIMIT} command in MySQL.
     * @param sortBy        {@link String} value with name of database column which will be used for sorting.
     *                      {@code ORDER BY} command in MySQL. There are only 4 columns to be chosen for sorting:
     *                      {@code TITLE, TOPIC, PRICE, STATUS} (case-insensitive). If you insert another value or {@code null} it
     *                      will automatically sort by {@code TITLE} column.
     * @param order         {@link String} value with a sorting order. There are only 2 parameters that can be set sorting:
     *                      {@code ASK, DESK} (case-insensitive). If you insert another value or {@code null} it
     *                      will automatically sort in {@code ASK} order.
     * @return an {@link ArrayList} with {@link PeriodicalForTable} entities presented in the database
     * according to the method parameters or an empty {@code ArrayList} when there is no entities.
     * @throws NullPointerException in case of {@link Connection} parameter is {@code null}.
     * @throws DAOException         in case of error on database side.
     */
    @Override
    public List<PeriodicalForTable> getPeriodicalsForTableSortPag(
            final Connection connection, final String locale, final String defaultLocale,
            final int skip, final int amount, final String sortBy, final String order) throws DAOException {
        List<PeriodicalForTable> periodicals = new ArrayList<>();
        String query = getSQLQueryWithSortingOrder(order, false);
        int sortingColumnNumber = getSortingColumnNumber(sortBy);

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, locale);
            ps.setString(2, defaultLocale);
            ps.setObject(3, sortingColumnNumber);
            ps.setInt(4, amount);
            ps.setInt(5, skip);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    periodicals.add(new PeriodicalForTable(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getInt(4),
                            rs.getBoolean(5)
                    ));
                }
            }
        } catch (SQLException e) {
            LOG.error("Did not retrieve periodicals list from database. " +
                    "LOCALE=" + locale + ", DEFAULT_LOCALE=" + defaultLocale + ", SKIP=" + skip +
                    ", AMOUNT=" + amount + ", SORT_BY=" + sortBy + ", ORDER=" + order + ". " + e.getMessage());
            throw new DAOException("Error while trying to retrieve list of periodicals (pagination mode).");
        }

        LOG.debug("List of periodicals successfully retrieved from database (pagination mode). " +
                "List size=" + periodicals.size());
        return periodicals;
    }

    /**
     * Returns an {@link ArrayList} with {@link PeriodicalForTable} entities presented in the database
     * according to the method parameters.
     *
     * @param locale        preferable {@link LocaleCustom} ID of {@link PeriodicalTranslate} objects to looking for in database.
     * @param defaultLocale alternative {@link LocaleCustom} ID of {@link PeriodicalTranslate} objects to looking for in database.
     * @param skip          how many rows in periodical result table we need to skip.
     *                      {@code OFFSET} command in MySQL.
     * @param amount        maximum amount of periodical objects that can be in the resulting list.
     *                      {@code LIMIT} command in MySQL.
     * @param sortBy        {@link String} value with name of database column which will be used for sorting.
     *                      {@code ORDER BY} command in MySQL. There are only 4 columns to be chosen for sorting:
     *                      {@code TITLE, TOPIC, PRICE, STATUS} (case-insensitive). If you insert another value or {@code null} it
     *                      will automatically sort by {@code TITLE} column.
     * @param order         {@link String} value with a sorting order. There are only 2 parameters that can be set sorting:
     *                      {@code ASK, DESK} (case-insensitive). If you insert another value or {@code null} it
     *                      will automatically sort in {@code ASK} order.
     * @param searchedTitle {@link String} value with searched periodical {@code title}. {@code WHERE} command in MySQL.
     * @return an {@link ArrayList} with {@link PeriodicalForTable} entities presented in the database
     * according to the method parameters or an empty {@code ArrayList} when there is no entities.
     * @throws NullPointerException in case of {@link Connection} parameter is {@code null}.
     * @throws DAOException         in case of error on database side.
     */
    @Override
    public List<PeriodicalForTable> getPeriodicalsForTableByTitleSortPag(
            final Connection connection, final String locale, final String defaultLocale,
            final int skip, final int amount, final String sortBy, final String order,
            final String searchedTitle) throws DAOException {
        List<PeriodicalForTable> periodicals = new ArrayList<>();
        String query = getSQLQueryWithSortingOrder(order, true);
        int sortingColumnNumber = getSortingColumnNumber(sortBy);

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, locale);
            ps.setString(2, defaultLocale);
            ps.setString(3, searchedTitle);
            ps.setInt(4, sortingColumnNumber);
            ps.setInt(5, amount);
            ps.setInt(6, skip);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    periodicals.add(new PeriodicalForTable(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getInt(4),
                            rs.getBoolean(5)
                    ));
                }
            }
        } catch (SQLException e) {
            LOG.error("Did not retrieve periodicals list from database. " +
                    "TITLE=" + searchedTitle + "LOCALE=" + locale + ", DEFAULT_LOCALE=" + defaultLocale + ", SKIP=" + skip +
                    ", AMOUNT=" + amount + ", SORT_BY=" + sortBy + ", ORDER=" + order + ". " + e.getMessage());
            throw new DAOException("Error while trying to retrieve list of periodicals (pagination and search mode).");
        }

        LOG.debug("List of periodicals successfully retrieved from database (pagination and search mode). " +
                "List size=" + periodicals.size());
        return periodicals;
    }

    /**
     * Returns a total amount of {@link Periodical} entities that are presented in the database.
     *
     * @return a total amount of periodicals that are presented in the database.
     * @throws NullPointerException in case of {@link Connection} parameter is {@code null}.
     * @throws DAOException         in case of error on database side.
     */
    @Override
    public int getPeriodicalsAmount(final Connection connection) throws DAOException {
        int count = 0;

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(Queries.GET_PERIODICALS_COUNT)) {
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            LOG.error("Did not retrieve periodicals amount from database. " + e.getMessage());
            throw new DAOException("Error while trying to retrieve amount of periodicals.");
        }

        LOG.debug("Amount of periodicals successfully retrieved from database and equals " + count);
        return count;
    }

    /**
     * Returns a total amount of {@link Periodical} entities in database that are matching the search query.
     *
     * @param searchQuery {@link String} value with searched periodicals title. {@code WHERE} command in MySQL.
     * @return a total amount of periodical entities in database that are matching the search query.
     * @throws NullPointerException in case of {@link Connection} parameter is {@code null}.
     * @throws DAOException         in case of error on database side.
     */
    @Override
    public int getPeriodicalsAmountSearchMode(final Connection connection, final String searchQuery)
            throws DAOException {
        int count = 0;

        try (PreparedStatement ps = connection.prepareStatement(Queries.GET_PERIODICALS_COUNT_SEARCH_MODE)) {
            ps.setString(1, searchQuery);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    count = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            LOG.error("Did not retrieve periodicals amount from database (search mode). " + e.getMessage());
            throw new DAOException("Error while trying to retrieve amount of periodicals (search mode).");
        }

        LOG.debug("Amount of periodicals matching search query=" + searchQuery +
                " successfully retrieved from database and equals " + count);
        return count;
    }

    /**
     * Check if {@link Periodical} entity with specified {@code title} is represented in database.
     *
     * @param title {@link String} value with searched periodicals title. {@code WHERE} command in MySQL.
     * @return {@code true} when periodical with specified {@code title} is represented in database.
     * Otherwise returns {@code false}.
     * @throws NullPointerException in case of {@link Connection} parameter is {@code null}.
     * @throws DAOException         in case of error on database side.
     */
    @Override
    public boolean getIsPeriodicalExists(Connection connection, final String title) throws DAOException {
        boolean exists = false;

        try (PreparedStatement ps = connection.prepareStatement(Queries.GET_IS_PERIODICAL_EXISTS)) {
            ps.setString(1, title);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    if (rs.getInt(1) == 1) {
                        exists = true;
                    }
                }
            }
        } catch (SQLException e) {
            LOG.error("Did not check if periodical with title=" + title + " is represented in database. " + e.getMessage());
            throw new DAOException("Error while trying to check if periodical with title="
                    + title + " represented in database.");
        }

        if (exists) {
            LOG.error("The periodical with title=" + title + " is represented in database");
        } else {
            LOG.error("The periodical with title=" + title + " is not represented in database");
        }
        return exists;
    }

    /**
     * Check if {@link Periodical} entity with specified {@code title} is represented in database
     * exclusively periodical with specified {@code ID}.
     *
     * @param id    {@code integer} value that specified {@code ID} of periodical which will be excluded.
     * @param title {@link String} value with searched periodicals title. {@code WHERE} command in MySQL.
     * @return {@code true} when periodical with specified {@code title} is represented in database.
     * Otherwise returns {@code false} (excluded periodical with the specified ID).
     * @throws NullPointerException in case of {@link Connection} parameter is {@code null}.
     * @throws DAOException         in case of error on database side.
     */
    @Override
    public boolean getIsPeriodicalExists(final Connection connection, final int id,
                                         final String title) throws DAOException {
        boolean exists = false;

        try (PreparedStatement ps = connection.prepareStatement(Queries.GET_IS_PERIODICAL_EXISTS_EXCEPT_ID)) {
            ps.setString(1, title);
            ps.setInt(2, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    if (rs.getInt(1) == 1) {
                        exists = true;
                    }
                }
            }
        } catch (SQLException e) {
            LOG.error("Did not check if periodical with title=" + title +
                    " is represented in database (id=" + id + " excluded). " + e.getMessage());
            throw new DAOException("Error while trying to check if periodical with title="
                    + title + " represented in database.");
        }

        if (exists) {
            LOG.error("The periodical with title=" + title +
                    " is represented in database (id=" + id + " excluded)");
        } else {
            LOG.error("The periodical with title=" + title +
                    " is not represented in database (id=" + id + " excluded)");
        }
        return exists;
    }

    /**
     * Helper method to set parameters of the {@link PreparedStatement} object from the
     * presented {@link Periodical} entity.
     *
     * @throws NullPointerException in case of {@link Periodical} parameter is {@code null}.
     * @throws SQLException         in case of error when setting parameters of the {@link PreparedStatement} object.
     */
    private void fillPreparedStatement(PreparedStatement ps, Periodical entity) throws SQLException {
        ps.setInt(1, entity.getTopicID());
        ps.setString(2, entity.getTitle());
        ps.setString(3, entity.getTitleImgLink());
        ps.setInt(4, entity.getPrice());
        ps.setString(5, entity.getFrequency().toJSONString());
        ps.setBoolean(6, entity.isActive());
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
                    ? Queries.GET_PERIODICALS_FOR_TABLE_BY_TITLE_PAGINATION_DESC
                    : Queries.GET_PERIODICALS_FOR_TABLE_BY_TITLE_PAGINATION_ASC;
        } else {
            return sortOrder != null && sortOrder.equalsIgnoreCase("DESC")
                    ? Queries.GET_PERIODICALS_FOR_TABLE_PAGINATION_DESC
                    : Queries.GET_PERIODICALS_FOR_TABLE_PAGINATION_ASC;
        }
    }

    /**
     * Helper method that returns index of sorting column in periodical table.
     *
     * @param sortBy {@link String} value with name of the database column which will be used for sorting.
     *               There are only 4 columns to be chosen for sorting: {@code TITLE, TOPIC, PRICE, STATUS}
     *               (case-insensitive). If you insert another value or {@code null} it will automatically
     *               return 2 - index of {@code TITLE} column in {@code periodical} table.
     * @return index of sorting column.
     */
    private int getSortingColumnNumber(final String sortBy) {
        return sortBy != null ?
                (sortBy.equalsIgnoreCase("title") ? 2
                        : sortBy.equalsIgnoreCase("topic") ? 3
                        : sortBy.equalsIgnoreCase("price") ? 4
                        : sortBy.equalsIgnoreCase("status") ? 5 : 2)
                : 2;
    }
}