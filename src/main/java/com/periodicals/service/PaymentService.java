package com.periodicals.service;

import com.periodicals.dao.exception.DAOException;
import com.periodicals.entity.Payment;
import com.periodicals.service.exceptions.ServiceException;

public interface PaymentService {

    void createPayment(Payment payment) throws DAOException, ServiceException;

    void processPaymentStatus(String paymentId, String status) throws DAOException, ServiceException;

}
