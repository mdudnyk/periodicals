package com.periodicals.dao.mysql;

import com.periodicals.dao.PaymentDAO;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.entity.Payment;
import com.periodicals.entity.enums.PaymentStatus;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

public class PaymentDAOMySql implements PaymentDAO {
    @Override
    public void create(final Payment entity, final Connection connection) throws DAOException {
        try (PreparedStatement ps = connection.prepareStatement(Queries.CREATE_PAYMENT)) {
            fillPreparedStatement(ps, entity);
            ps.execute();
        } catch (SQLException e) {
            throw new DAOException("Error while trying to create new payment with id="
                    + entity.getId() + ". " + e.getMessage());
        }
    }

    @Override
    public List<Payment> getAll(final Connection connection) throws DAOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Payment getEntityById(final String id, final Connection connection) throws DAOException {
        Payment payment = null;

        try (PreparedStatement ps = connection.prepareStatement(Queries.GET_PAYMENT_BY_ID)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                payment = fillEntityFromResultSet(rs);
            }
            rs.close();
        } catch (SQLException e) {
            throw new DAOException("Error while trying to get payment with id=" + id + ". " + e.getMessage());
        }

        return payment;
    }

    @Override
    public void update(final Payment entity, final Connection connection) throws DAOException {
        try (PreparedStatement ps = connection.prepareStatement(Queries.UPDATE_PAYMENT_STATUS)) {
            ps.setString(1, entity.getStatus().name());
            ps.setString(2, entity.getId());
            if (ps.executeUpdate() < 1) {
                throw new DAOException("We don`t have such payment. ");
            }
        } catch (SQLException e) {
            throw new DAOException("Can not update payment status for payment with ID="
                    + entity.getId() + ". " + e.getMessage());
        }
    }

    @Override
    public void delete(final String id, final Connection connection) throws DAOException {
        throw new UnsupportedOperationException();
    }

    private void fillPreparedStatement(PreparedStatement ps, Payment entity) throws SQLException {
        ps.setString(1, entity.getId());
        ps.setInt(2, entity.getUserId());
        ps.setInt(3, entity.getAmount());
        ps.setString(4, entity.getStatus().name());
        ps.setTimestamp(5, Timestamp.valueOf(entity.getCreatedAt()));
    }

    private Payment fillEntityFromResultSet(ResultSet resultSet) throws SQLException {
        String id = resultSet.getString(1);
        int userId = resultSet.getInt(2);
        int amount = resultSet.getInt(3);
        PaymentStatus paymentStatus = PaymentStatus.valueOf(resultSet.getString(4));
        LocalDateTime createdAt = resultSet.getTimestamp(5).toLocalDateTime();

        return new Payment(id, userId, amount, paymentStatus, createdAt);
    }
}