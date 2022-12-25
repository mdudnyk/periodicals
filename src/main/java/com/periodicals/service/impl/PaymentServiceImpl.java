package com.periodicals.service.impl;

import com.periodicals.dao.exception.DAOException;
import com.periodicals.dao.manager.DAOManager;
import com.periodicals.entity.Payment;
import com.periodicals.entity.User;
import com.periodicals.entity.enums.PaymentStatus;
import com.periodicals.service.PaymentService;
import com.periodicals.service.exceptions.ServiceException;

public class PaymentServiceImpl implements PaymentService {
    private final DAOManager daoManger;

    public PaymentServiceImpl(DAOManager daoManger) {
        this.daoManger = daoManger;
    }

    @Override
    public void createPayment(final Payment payment) throws DAOException, ServiceException {
        User user = daoManger.getUserDao().getUserById(payment.getUserId());
        if (user.isBlocked()) {
            throw new ServiceException("Account is blocked.");
        }
        if (payment.getAmount() == 0) {
            throw new ServiceException("Amount of top up should be bigger than 0.");
        }
        daoManger.getPaymentDao().createPayment(payment);
    }

    @Override
    public void processPaymentStatus(final String paymentId, final String status)
            throws DAOException, ServiceException {
        Payment payment = daoManger.getPaymentDao().getPaymentById(paymentId);

        if (payment == null) {
            throw new ServiceException("Payment with id=" + paymentId + " is non-existent. ");
        }
        PaymentStatus paymentStatus = getPaymentStatus(status);
        if (payment.getStatus() != paymentStatus) {
            payment.setStatus(paymentStatus);
            daoManger.getPaymentDao().updatePaymentStatusAndUserBalance(payment);
        }
    }

    private PaymentStatus getPaymentStatus(final String status) {
        if (status.equals("success")) {
            return PaymentStatus.SUCCESS;
        } else {
            return PaymentStatus.FAILURE;
        }
    }
}