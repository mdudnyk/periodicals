package com.periodicals.dao.mysql;

import com.periodicals.dao.interfacesForDAO.SubscriptionCalendarDAO;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.entity.MonthSelector;
import com.periodicals.entity.Subscription;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * A class for interacting with the {@code subscription_calendar} table in the {@code MySql} database.
 */
public class SubscriptionCalendarDAOMySql implements SubscriptionCalendarDAO {
    private static final Logger LOG = LogManager.getLogger(SubscriptionCalendarDAOMySql.class);

    /**
     * Inserts {@link MonthSelector} entity into the {@code subscription_calendar} table.
     *
     * @throws NullPointerException in case of {@link MonthSelector} or {@link Connection} parameter is {@code null}.
     * @throws DAOException         in case of error at the database side or in case
     *                              when entity with {@code subscription_id} is not represented
     *                              in database.
     */
    @Override
    public void create(final int subscriptionId, final MonthSelector entity,
                       final Connection connection) throws DAOException {
        try (PreparedStatement ps = connection.prepareStatement(Queries.CREATE_SUBSCRIPTION_CALENDAR)) {
            fillPreparedStatement(ps, subscriptionId, entity);
            ps.execute();
        } catch (SQLException e) {
            LOG.error("Can not add new subscription calendar for year=" + entity.getYear() +
                    " and for subscription with id=" + subscriptionId + " to the database. " + e.getMessage());
            throw new DAOException("Can not add new subscription calendar for subscription to the database.");
        }

        LOG.debug("The subscription calendar for year=" + entity.getYear() + " and for subscription with id="
                + subscriptionId + " was successfully added to the database");
    }

    /**
     * Returns a {@link HashMap} with all {@link MonthSelector} entities from {@code subscription_calendar} table
     * that are represented with specified {@code subscription id}.
     *
     * @param subscriptionId is an {@link Subscription} {@code ID} for searched {@code subscription calendar}.
     * @return a {@code HashMap} with all {@link MonthSelector} entities that are represented in the database
     * for specified {@code subscription id}. The {@code key} of this map is an integer with {@code year} value.
     * Method can return an empty Map in of there is no {@link MonthSelector} entities in database.
     * @throws NullPointerException in case of {@link Connection} parameter is {@code null}.
     * @throws DAOException         in case of error on database side.
     */
    @Override
    public Map<Integer, MonthSelector> getCalendarBySubscriptionId(final int subscriptionId,
                                                                   final Connection connection) throws DAOException {
        Map<Integer, MonthSelector> calendar = new HashMap<>();

        try (PreparedStatement ps = connection.prepareStatement(Queries.GET_SUBSCRIPTION_CALENDAR_BY_ID)) {
            ps.setInt(1, subscriptionId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    MonthSelector monthSelector = fillEntityFromResultSet(rs);
                    calendar.put(monthSelector.getYear(), monthSelector);
                }
            }
        } catch (SQLException | ParseException e) {
            LOG.error("Did not retrieve subscription calendars by periodical id=" + subscriptionId
                    + " from database. " + e.getMessage());
            throw new DAOException("Error while trying to retrieve subscription calendar for subscription with id="
                    + subscriptionId + ".");
        }

        LOG.debug("Map with subscription calendars successfully retrieved from database by subscription id="
                + subscriptionId + ". Map size=" + calendar.size());
        return calendar;
    }

    /**
     * Helper method to set parameters of the {@link PreparedStatement} object from the
     * presented {@link MonthSelector} entity.
     *
     * @param subscriptionId is an {@link Subscription} {@code ID} for {@code subscription calendar} entity in database.
     * @throws NullPointerException in case of {@link MonthSelector} parameter is {@code null}.
     * @throws SQLException         in case of error when setting parameters of the {@link PreparedStatement} object.
     */
    private void fillPreparedStatement(PreparedStatement ps, final int subscriptionId, MonthSelector entity)
            throws SQLException {
        ps.setInt(1, subscriptionId);
        ps.setInt(2, entity.getYear());
        ps.setString(3, entity.getMonth().toJSONString());
    }

    /**
     * Helper method to create an {@link MonthSelector} object using data stored in the {@link ResultSet} object.
     *
     * @return {@link MonthSelector} object retrieved from {@link ResultSet}.
     * @throws NullPointerException in case of {@link ResultSet} parameter is {@code null}.
     * @throws SQLException         in case of error when retrieves the value from the {@link ResultSet} object.
     */
    private MonthSelector fillEntityFromResultSet(ResultSet resultSet) throws SQLException, ParseException {
        int year = resultSet.getInt(2);
        JSONArray month = (JSONArray) new JSONParser().parse(resultSet.getString(3));

        return new MonthSelector(year, month);
    }
}