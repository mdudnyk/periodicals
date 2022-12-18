package com.periodicals.dao.mysql;

import com.periodicals.dao.PaymentDAO;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.entity.Payment;
import com.periodicals.entity.enums.PaymentStatus;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.matchers.JUnitMatchers.containsString;

class PaymentDAOMySqlTest {
    private static DBManager dbManager;
    private static Connection connection;
    PaymentDAO paymentDAO = new PaymentDAOMySql();

    @BeforeAll
    static void globalSetUp() throws SQLException, IOException {
        dbManager = DBManager.getInstance();
        connection = dbManager.getConnection();
        dbManager.setUpDatabase(connection);
        Statement statement = connection.createStatement();
        statement.execute("INSERT INTO locale values('uk', 'українська', 'грн', '/img/ua_flag.png')");
        statement.execute("INSERT INTO user values(1, 'uk', 'Myroslav', 'Dudnyk', " +
                "'qwerty123', 'mdudnyk.sps@gmail.com', 'CUSTOMER', 500000, false)");
        statement.close();
    }


    @AfterAll
    static void globalTearDown() throws SQLException {
        dbManager.dropDatabase(connection);
        dbManager.close(connection);
    }

    @BeforeEach
    void setUp() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("INSERT INTO payment values('1111', 1, 1000, 'NOT_COMPLETED', DEFAULT)");
        statement.close();
    }

    @AfterEach
    void tearDown() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("DELETE FROM payment");
        statement.close();
    }

    @Test
    void create() throws DAOException {
        Payment payment = new Payment("2222", 1, 100);
        paymentDAO.create(payment, connection);
        assertNotNull(paymentDAO.getEntityById(payment.getId(), connection));
    }

    @Test
    void getAll() {
        assertThrows(UnsupportedOperationException.class, () -> paymentDAO.getAll(connection));
    }

    @Test
    void getEntityById() throws DAOException {
        assertNotNull(paymentDAO.getEntityById("1111", connection));
        assertNull(paymentDAO.getEntityById("2222", connection));
    }

    @Test
    void update() throws DAOException {
        Payment payment = paymentDAO.getEntityById("1111", connection);
        PaymentStatus statusBeforeUpdate = paymentDAO.getEntityById("1111", connection).getStatus();
        payment.setStatus(PaymentStatus.SUCCESS);
        paymentDAO.update(payment, connection);

        PaymentStatus statusAfterUpdate = paymentDAO.getEntityById("1111", connection).getStatus();
        assertNotEquals(statusBeforeUpdate, statusAfterUpdate);

        Payment failPayment = new Payment("2222", 1, 100);
        DAOException daoException = assertThrows(DAOException.class, () -> paymentDAO.update(failPayment, connection));
        assertThat(daoException.getMessage(), containsString("We don`t have such payment"));
    }

    @Test
    void delete() {
        assertThrows(UnsupportedOperationException.class, () -> paymentDAO.delete("22", connection));
    }
}