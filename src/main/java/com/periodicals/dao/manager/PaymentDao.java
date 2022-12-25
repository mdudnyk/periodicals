package com.periodicals.dao.manager;

import com.periodicals.dao.ConnectionManager;
import com.periodicals.dao.interfacesForDAO.PaymentDAO;
import com.periodicals.dao.interfacesForDAO.UserDAO;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.dao.mysql.PaymentDAOMySql;
import com.periodicals.dao.mysql.UserDAOMySql;
import com.periodicals.entity.Payment;
import com.periodicals.entity.User;
import com.periodicals.entity.enums.PaymentStatus;

import java.sql.Connection;
import java.sql.SQLException;

public class PaymentDao {
    private ConnectionManager conManager;
    private PaymentDAO paymentDAO;
    private UserDAO userDAO;

    private PaymentDao() {
    }

    public PaymentDao(ConnectionManager connectionManager) {
        this.conManager = connectionManager;
        paymentDAO = new PaymentDAOMySql();
        userDAO = new UserDAOMySql();
    }

    public void createPayment(Payment payment) throws DAOException {
        Connection connection = conManager.getConnection();
        paymentDAO.create(payment, connection);
        conManager.close(connection);
    }

    public Payment getPaymentById(final String paymentId) throws DAOException {
        Connection connection = conManager.getConnection();
        Payment payment = paymentDAO.getEntityById(paymentId, connection);
        conManager.close(connection);

        return payment;
    }

    public void updatePaymentStatusAndUserBalance(final Payment payment) throws DAOException {
        Connection connection = conManager.getConnectionForTransaction();
        try {
            paymentDAO.update(payment, connection);
            if (payment.getStatus() == PaymentStatus.SUCCESS) {
                User user = userDAO.getEntityById(payment.getUserId(), connection);
                user.setBalance(user.getBalance() + payment.getAmount());
                userDAO.update(user, connection);
            }
            connection.commit();
        } catch (SQLException e) {
            conManager.rollback(connection);
        } finally {
            conManager.close(connection);
        }
    }
}