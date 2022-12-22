package com.periodicals.dao.mysql;

import com.periodicals.dao.PaymentDAO;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.entity.Payment;
import com.periodicals.entity.enums.PaymentStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * A class for interacting with the {@code payment} table in the {@code MySql} database.
 */
public class PaymentDAOMySql implements PaymentDAO {
    private static final Logger LOG = LogManager.getLogger(PaymentDAOMySql.class);

    /**
     * Inserts {@link Payment} entity into the database. {@link Payment} field {@code id} should be unique in database.
     *
     * @throws NullPointerException in case of {@link Payment} or {@link Connection} parameter is {@code null}.
     * @throws DAOException         in case of error at the database side.
     */
    @Override
    public void create(final Payment entity, final Connection connection) throws DAOException {
        try (PreparedStatement ps = connection.prepareStatement(Queries.CREATE_PAYMENT)) {
            fillPreparedStatement(ps, entity);
            ps.execute();
        } catch (SQLException e) {
            LOG.error("Can not add new payment with id=" + entity.getId() + " for user with id="
                    + entity.getUserId() + " to the database. " + e.getMessage());
            throw new DAOException("Error while trying to create new payment with id="
                    + entity.getId() + ".");
        }

        LOG.debug("New payment with id=" + entity.getId() + " for user with id="
                + entity.getUserId() + "was successfully added to the database");
    }

    /**
     * This method does not work due to lack of necessity.
     *
     * @throws UnsupportedOperationException always.
     */
    @Override
    public List<Payment> getAll(final Connection connection) throws DAOException {
        LOG.error("Method does not work due to lack of necessity");
        throw new UnsupportedOperationException();
    }

    /**
     * Returns a {@link Payment} entity presented in the database by it`s {@code id} value.
     *
     * @param id is a {@code String} value with unique payment ID.
     * @return a {@link Payment} entity presented in the database or {@code null} if there is
     * no payment with this ID storing in database.
     * @throws NullPointerException in case of {@link Connection} parameter is {@code null}.
     * @throws DAOException         in case of error on database side.
     */
    @Override
    public Payment getEntityById(final String id, final Connection connection) throws DAOException {
        Payment payment = null;

        try (PreparedStatement ps = connection.prepareStatement(Queries.GET_PAYMENT_BY_ID)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    payment = fillEntityFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            LOG.error("Did not retrieve payment by it`s id=" + id + " from database. " + e.getMessage());
            throw new DAOException("Error while trying to get payment with id=" + id + ".");
        }

        if (payment != null) {
            LOG.debug("The payment with id=" + id + " successfully retrieved from database");
        } else {
            LOG.debug("The payment with id=" + id + " is not presented in database");
        }
        return payment;
    }

    /**
     * Updates an {@link Payment} entity presented in the database.
     *
     * @throws NullPointerException in case of {@link Connection} or {@link Payment} parameter is {@code null}.
     * @throws DAOException         in case of error on database side or when trying to update
     *                              entity that is not presented in database.
     */
    @Override
    public void update(final Payment entity, final Connection connection) throws DAOException {
        try (PreparedStatement ps = connection.prepareStatement(Queries.UPDATE_PAYMENT_STATUS)) {
            ps.setString(1, entity.getStatus().name());
            ps.setString(2, entity.getId());
            if (ps.executeUpdate() < 1) {
                LOG.error("The payment is not represented in the database");
                throw new DAOException("We don`t have such payment.");
            }
        } catch (SQLException e) {
            LOG.error("Can not update payment with id=" + entity.getId() + ". " + e.getMessage());
            throw new DAOException("Can not update payment with ID=" + entity.getId() + ".");
        }

        LOG.debug("Payment with id=" + entity.getId() + " was successfully updated");
    }

    /**
     * This method does not work due to lack of necessity.
     *
     * @throws UnsupportedOperationException always.
     */
    @Override
    public void delete(final String id, final Connection connection) throws DAOException {
        LOG.error("Method does not work due to lack of necessity");
        throw new UnsupportedOperationException();
    }

    /**
     * Helper method to set parameters of the {@link PreparedStatement} object from the
     * presented {@link Payment} entity.
     *
     * @throws NullPointerException in case of {@link Payment} parameter is {@code null}.
     * @throws SQLException         in case of error when setting parameters of the {@link PreparedStatement} object.
     */
    private void fillPreparedStatement(PreparedStatement ps, Payment entity) throws SQLException {
        ps.setString(1, entity.getId());
        ps.setInt(2, entity.getUserId());
        ps.setInt(3, entity.getAmount());
        ps.setString(4, entity.getStatus().name());
        ps.setTimestamp(5, Timestamp.valueOf(entity.getCreatedAt()));
    }

    /**
     * Helper method to create an {@link Payment} object using data stored in the {@link ResultSet} object.
     *
     * @return {@link Payment} object retrieved from {@link ResultSet}.
     * @throws NullPointerException in case of {@link ResultSet} parameter is {@code null}.
     * @throws SQLException         in case of error when retrieves the value from the {@link ResultSet} object.
     */
    private Payment fillEntityFromResultSet(ResultSet resultSet) throws SQLException {
        String id = resultSet.getString(1);
        int userId = resultSet.getInt(2);
        int amount = resultSet.getInt(3);
        PaymentStatus paymentStatus = PaymentStatus.valueOf(resultSet.getString(4));
        LocalDateTime createdAt = resultSet.getTimestamp(5).toLocalDateTime();

        return new Payment(id, userId, amount, paymentStatus, createdAt);
    }
}