package com.periodicals.dao.mysql;

import com.periodicals.dao.ReleaseCalendarDAO;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.entity.MonthSelector;
import com.periodicals.entity.Periodical;
import com.periodicals.entity.PeriodicalTranslate;
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


public class ReleaseCalendarDAOMySql implements ReleaseCalendarDAO {
    private static final Logger LOG = LogManager.getLogger(PeriodicalDAOMySql.class);

    /**
     * Inserts {@link MonthSelector} entity into the {@code release_calendar} table.
     *
     * @throws NullPointerException in case of {@link MonthSelector} or {@link Connection} parameter is {@code null}.
     * @throws DAOException         in case of error at the database side or in case
     *                              when entity with {@code periodical_id} is not represented
     *                              in database.
     */
    @Override
    public void create(final int periodicalId, final MonthSelector entity,
                       final Connection connection) throws DAOException {
        try (PreparedStatement ps = connection.prepareStatement(Queries.CREATE_PERIODICAL_RELEASE_CALENDAR)) {
            fillPreparedStatement(ps, periodicalId, entity);
            ps.execute();
        } catch (SQLException e) {
            LOG.error("Can not add new release calendar for year=" + entity.getYear() +
                    " and for periodical with id=" + periodicalId + " to the database. " + e.getMessage());
            throw new DAOException("Can not add new release calendar for periodical to the database.");
        }

        LOG.debug("The release calendar for year=" + entity.getYear() + " and for periodical with id="
                + periodicalId + " was successfully added to the database");
    }

    /**
     * Returns a {@link HashMap} with all {@link MonthSelector} entities that are presented in the database
     * with specified {@code periodical id}.
     *
     * @param periodicalId is an {@link Periodical} {@code ID} for searched {@code release calendar}.
     * @return a {@code HashMap} with all {@link MonthSelector} entities that are presented in the database
     * for specified {@code periodical id}. The {@code key} of this map is an integer with {@code year} value.
     * Method can return an empty Map in of there is no {@link MonthSelector} entities in database.
     * @throws NullPointerException in case of {@link Connection} parameter is {@code null}.
     * @throws DAOException         in case of error on database side.
     */
    @Override
    public Map<Integer, MonthSelector> getCalendarByPeriodicalId(final int periodicalId,
                                                                 final Connection connection) throws DAOException {
        Map<Integer, MonthSelector> calendar = new HashMap<>();

        try (PreparedStatement ps = connection.prepareStatement(Queries.GET_PERIODICAL_RELEASE_CALENDAR_BY_ID)) {
            ps.setInt(1, periodicalId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    MonthSelector monthSelector = fillEntityFromResultSet(rs);
                    calendar.put(monthSelector.getYear(), monthSelector);
                }
            }
        } catch (SQLException | ParseException e) {
            LOG.error("Did not retrieve release calendars by periodical id=" + periodicalId
                    + " from database. " + e.getMessage());
            throw new DAOException("Error while trying to retrieve release calendar for periodical with id="
                    + periodicalId + ".");
        }

        LOG.debug("Map with release calendars successfully retrieved from database by periodical id="
                + periodicalId + ". Map size=" + calendar.size());
        return calendar;
    }

    /**
     * Returns a {@link MonthSelector} with entity that are presented in the database
     * with specified {@code periodical id} and {@code year}.
     *
     * @param periodicalId is an {@link Periodical} {@code ID} for searched {@code release calendar}.
     * @param year         is a specified year for searched {@code release calendar}.
     * @return a {@code MonthSelector} with entity that are presented in the database
     * for specified {@code periodical id} and {@code year}.
     * Method can return {@code null} value if there is no {@link MonthSelector} entities with
     * specified parameters in database.
     * @throws NullPointerException in case of {@link Connection} parameter is {@code null}.
     * @throws DAOException         in case of error on database side.
     */
    @Override
    public MonthSelector getCalendarByPeriodicalIdAndYear(final int periodicalId, final int year,
                                                          final Connection connection) throws DAOException {
        MonthSelector calendar = null;

        try (PreparedStatement ps = connection
                .prepareStatement(Queries.GET_PERIODICAL_RELEASE_CALENDAR_BY_ID_AND_YEAR)) {
            ps.setInt(1, periodicalId);
            ps.setInt(2, year);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    calendar = fillEntityFromResultSet(rs);
                }
            }
        } catch (SQLException | ParseException e) {
            LOG.error("Did not retrieve release calendar by periodical id=" + periodicalId
                    + " from database. " + e.getMessage());
            throw new DAOException("Error while trying to retrieve release calendar for periodical with id="
                    + periodicalId + " and year=" + year + ".");
        }

        if (calendar != null) {
            LOG.debug("The periodical release calendar with periodical id=" + periodicalId + " and year=" + year
                    + " successfully retrieved from database");
        } else {
            LOG.debug("The periodical release calendar with periodical id=" + periodicalId + " and year=" + year
                    + " is not represented in database");
        }
        return calendar;
    }

    /**
     * Check if {@link MonthSelector} entity with specified {@code periodical ID} and {@code year} is
     * represented in database.
     *
     * @param periodicalId is an {@link Periodical} {@code ID} for searched {@code release calendar}.
     * @param year         is a specified year for searched {@code release calendar}.
     * @return {@code true} when periodical release calendar with specified {@code periodical} and {@code year} is
     * represented in database. Otherwise, returns {@code false}.
     * @throws NullPointerException in case of {@link Connection} parameter is {@code null}.
     * @throws DAOException         in case of error on database side.
     */
    @Override
    public boolean checkIfCalendarExists(final int periodicalId, final int year,
                                         final Connection connection) throws DAOException {
        boolean isRepresented = false;

        try (PreparedStatement ps = connection.prepareStatement(Queries.PERIODICAL_CALENDAR_EXISTS)) {
            ps.setInt(1, periodicalId);
            ps.setInt(2, year);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    if (rs.getInt(1) == 1) {
                        isRepresented = true;
                    }
                }
            }
        } catch (SQLException e) {
            LOG.error("Did not check if periodical release calendar with periodical id=" + periodicalId +
                    " and year=" + year + " is represented in database. " + e.getMessage());
            throw new DAOException("An error occurred while trying to check if periodical release calendar with" +
                    " periodical id=" + periodicalId + " and year=" + year + " is represented in database.");
        }

        if (isRepresented) {
            LOG.debug("The periodical release calendar with periodical id=" + periodicalId +
                    " and year=" + year + " is represented in database.");
        } else {
            LOG.debug("The periodical release calendar with periodical id=" + periodicalId +
                    " and year=" + year + " is not represented in database.");
        }
        return isRepresented;
    }

    /**
     * Updates a {@link MonthSelector} entity with specified periodical ID and year is represented in the database.
     *
     * @param periodicalId is an {@link Periodical} {@code ID} for {@code release calendar} entity in database.
     * @throws NullPointerException in case of {@link Connection} or {@link PeriodicalTranslate} argument is {@code null}.
     * @throws DAOException         in case of error on database side.
     */
    @Override
    public void update(final int periodicalId, final MonthSelector entity, final Connection connection) throws DAOException {
        try (PreparedStatement ps = connection.prepareStatement(Queries.UPDATE_PERIODICAL_CALENDAR)) {
            ps.setString(1, entity.getMonth().toJSONString());
            ps.setInt(2, periodicalId);
            ps.setInt(3, entity.getYear());
            if (ps.executeUpdate() < 1) {
                throw new DAOException("The periodical release calendar is not represented in the database");
            }
        } catch (SQLException e) {
            LOG.error("Can not update periodical release calendar with periodical id=" + periodicalId
                    + " and year=" + entity.getYear() + ". " + e.getMessage());
            throw new DAOException("Can not update periodical release calendar with periodical id="
                    + periodicalId + ".");
        }

        LOG.debug("The periodical release calendar with periodical id=" + periodicalId +
                " and year=" + entity.getYear() + " was successfully updated");
    }

    /**
     * Helper method to set parameters of the {@link PreparedStatement} object from the
     * presented {@link MonthSelector} entity.
     *
     * @param periodicalId is an {@link Periodical} {@code ID} for {@code periodical release calendar} entity in database.
     * @throws NullPointerException in case of {@link MonthSelector} parameter is {@code null}.
     * @throws SQLException         in case of error when setting parameters of the {@link PreparedStatement} object.
     */
    private void fillPreparedStatement(PreparedStatement ps, final int periodicalId, MonthSelector entity)
            throws SQLException {
        ps.setInt(1, periodicalId);
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