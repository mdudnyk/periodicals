package com.periodicals.dao.mysql;

import com.periodicals.dao.SubscriptionCalendarDAO;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.entity.MonthSelector;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SubscriptionCalendarDAOMySql implements SubscriptionCalendarDAO {
    @Override
    public void create(final int periodicalId, final MonthSelector entity,
                       final Connection connection) throws DAOException {
        try(PreparedStatement ps = connection.prepareStatement(Queries.CREATE_SUBSCRIPTION_CALENDAR)) {
            fillPreparedStatement(ps, periodicalId, entity);
            ps.execute();
        } catch (SQLException e) {
            throw new DAOException("Can not add new subscription calendar to the database. " + e.getMessage());
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
