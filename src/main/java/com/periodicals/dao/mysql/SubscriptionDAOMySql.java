package com.periodicals.dao.mysql;

import com.periodicals.dao.SubscriptionDAO;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.entity.Subscription;

import java.sql.*;
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
        return null;
    }

    @Override
    public void update(final Subscription entity, final Connection connection) throws DAOException {

    }

    @Override
    public void delete(final Integer id, final Connection connection) throws DAOException {

    }
}