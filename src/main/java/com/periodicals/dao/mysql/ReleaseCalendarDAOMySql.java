package com.periodicals.dao.mysql;

import com.periodicals.dao.ReleaseCalendarDAO;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.entity.MonthSelector;
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
    @Override
    public void create(final int periodicalId, final MonthSelector entity,
                       final Connection connection) throws DAOException {
        try(PreparedStatement ps = connection.prepareStatement(Queries.CREATE_PERIODICAL_RELEASE_CALENDAR)) {
            fillPreparedStatement(ps, periodicalId, entity);
            ps.execute();
        } catch (SQLException e) {
            throw new DAOException("Can not add new periodical's release calendar to the database. " + e.getMessage());
        }
    }

    @Override
    public Map<Integer, MonthSelector> getCalendarByPeriodicalId(
            final int id, final Connection connection) throws DAOException {
        Map<Integer, MonthSelector> calendar = new HashMap<>();
        try (PreparedStatement ps = connection.prepareStatement(Queries.GET_PERIODICAL_RELEASE_CALENDAR_BY_PERIODICAL_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                MonthSelector monthSelector = fillEntityFromResultSet(rs);
                calendar.put(monthSelector.getYear(), monthSelector);
            }
            rs.close();
        } catch (SQLException | ParseException e) {
            throw new DAOException("Error while trying to get release calendar for periodical with id="
                    + id + ". " + e.getMessage());
        }
        return calendar;
    }

    @Override
    public boolean checkIfCalendarExists(final int id, final int year, final Connection connection) throws DAOException {
        boolean exists = false;
        try (PreparedStatement ps = connection.prepareStatement(Queries.PERIODICAL_CALENDAR_EXISTS)) {
            ps.setInt(1, id);
            ps.setInt(2, year);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (rs.getInt(1) == 1) {
                    exists = true;
                }
            }
            rs.close();
        } catch (SQLException e) {
            throw new DAOException("An error occurred while trying to check if calendar for " +
                    "periodical with id=" + id + " and year=" + year + " is exists. " + e.getMessage());
        }
        return exists;
    }

    @Override
    public void update(final int id, final MonthSelector entity, final Connection connection) throws DAOException {
        try (PreparedStatement ps = connection.prepareStatement(Queries.UPDATE_PERIODICAL_CALENDAR)) {
            ps.setString(1, entity.getMonth().toJSONString());
            ps.setInt(2, id);
            ps.setInt(3, entity.getYear());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Can not update periodical calendar with year=" + entity.getYear()
                    + " for periodical with id=" + id + ". "+ e.getMessage());
        }
    }

    private void fillPreparedStatement(PreparedStatement ps, final int periodicalId, MonthSelector entity)
            throws SQLException {
        ps.setInt(1, periodicalId);
        ps.setInt(2, entity.getYear());
        ps.setString(3, entity.getMonth().toJSONString());
    }

    private MonthSelector fillEntityFromResultSet(ResultSet resultSet) throws SQLException, ParseException {
        int year = resultSet.getInt(2);
        JSONArray month = (JSONArray) new JSONParser().parse(resultSet.getString(3));
        return new MonthSelector(year, month);
    }
}
