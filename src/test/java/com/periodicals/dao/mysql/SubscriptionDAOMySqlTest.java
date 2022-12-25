package com.periodicals.dao.mysql;

import com.periodicals.dao.SubscriptionDAO;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.entity.Subscription;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.matchers.JUnitMatchers.containsString;

class SubscriptionDAOMySqlTest {
    private static DBManager dbManager;
    private static Connection connection;
    SubscriptionDAO subscriptionDAO = new SubscriptionDAOMySql();

    @BeforeAll
    static void globalSetUp() throws SQLException, IOException {
        dbManager = DBManager.getInstance();
        connection = dbManager.getConnection();
        dbManager.setUpDatabase(connection);
        Statement statement = connection.createStatement();
        statement.execute("INSERT INTO locale values('en', 'English', 'uah', 'no_flag')");
        statement.execute("INSERT INTO locale values('uk', 'Українська', 'грн', 'no_flag')");
        statement.execute("INSERT INTO topic values(1)");
        statement.execute("INSERT INTO periodical values(1, 1, 'Classic Cars1', '166643252774218.jpeg', 16300, " +
                "'{\"amount\": 1, \"period\": \"month\"}', true)");
        statement.execute("INSERT INTO periodical values(2, 1, 'Classic Cars2', '166643252774218.jpeg', 16300, " +
                "'{\"amount\": 1, \"period\": \"month\"}', true)");
        statement.execute("INSERT INTO user values(1, 'uk', 'Biba', 'Boba', 'qwerty123', " +
                "'boba@gmail.com', 'CUSTOMER', 1000000, false)");
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
        statement.execute("INSERT INTO subscription values(1, 1, 1, 'Classic Cars1', 16300, " +
                "'2022-12-09 00:00:00', '2023-11-30 00:00:00')");
        statement.execute("INSERT INTO subscription values(2, 1, 1, 'Classic Cars2', 16300, " +
                "'2022-12-09 00:00:00', '2023-11-30 00:00:00')");
        statement.close();
    }

    @AfterEach
    void tearDown() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("DELETE FROM subscription");
        statement.close();
    }

    @Test
    void create() throws DAOException {
        Subscription subscription = new Subscription(3, 1, 1, "Classic Cars3",
                16300, LocalDateTime.now(), LocalDate.of(2023, 2, 4));
        assertDoesNotThrow(() -> subscriptionDAO.create(subscription, connection));
        assertEquals(3, subscriptionDAO.getTotalAmountByUserId(1, connection));
    }

    @Test
    void getAll() {
        assertThrows(UnsupportedOperationException.class, () -> subscriptionDAO.getAll(connection));
    }

    @Test
    void getEntityById() throws DAOException {
        Subscription subscriptionForTest = new Subscription(1, 1, 1,
                "Classic Cars1", 16300,
                LocalDateTime.of(2022, 12, 9, 0, 0, 0),
                LocalDate.of(2023, 11, 30));
        Subscription subscriptionFromBD = subscriptionDAO.getEntityById(1, connection);
        assertEquals(subscriptionForTest, subscriptionFromBD);
    }

    @Test
    void getTotalAmountByUserIdAndSearchQuery() throws DAOException {
        assertEquals(2, subscriptionDAO
                .getTotalAmountByUserIdAndSearchQuery(1, "classic", connection));
        assertEquals(1, subscriptionDAO
                .getTotalAmountByUserIdAndSearchQuery(1, "cars1", connection));
        assertEquals(0, subscriptionDAO
                .getTotalAmountByUserIdAndSearchQuery(1, "auto", connection));
        assertEquals(0, subscriptionDAO
                .getTotalAmountByUserIdAndSearchQuery(3, "classic", connection));
        assertEquals(0, subscriptionDAO
                .getTotalAmountByUserIdAndSearchQuery(1, null, connection));
    }

    @Test
    void getTotalAmountByUserId() throws DAOException {
        assertEquals(2, subscriptionDAO
                .getTotalAmountByUserId(1, connection));
        assertEquals(0, subscriptionDAO
                .getTotalAmountByUserId(3, connection));
    }

    @Test
    void getSubscriptionsByUserIdPagination() throws DAOException {
        assertEquals(2, subscriptionDAO
                .getSubscriptionsByUserIdPagination(1, 0, 10,
                        "title", "ASC", connection).size());
        assertEquals(1, subscriptionDAO
                .getSubscriptionsByUserIdPagination(1, 1, 10,
                        "title", "ASC", connection).size());
        assertEquals(1, subscriptionDAO
                .getSubscriptionsByUserIdPagination(1, 0, 1,
                        "title", "ASC", connection).size());
        assertEquals(0, subscriptionDAO
                .getSubscriptionsByUserIdPagination(2, 0, 10,
                        "title", "ASC", connection).size());
        assertDoesNotThrow(() -> subscriptionDAO
                .getSubscriptionsByUserIdPagination(1, 1, 10,
                        null, null, connection));
    }

    @Test
    void testGetSubscriptionsByUserIdPagination() throws DAOException {
        assertEquals(2, subscriptionDAO
                .getSubscriptionsByUserIdPagination(1, "classic", 0, 10,
                        "title", "ASC", connection).size());
        assertEquals(0, subscriptionDAO
                .getSubscriptionsByUserIdPagination(3, "classic", 0, 10,
                        "title", "ASC", connection).size());
        assertEquals(1, subscriptionDAO
                .getSubscriptionsByUserIdPagination(1, "cars1", 0, 10,
                        "title", "ASC", connection).size());
        assertEquals(0, subscriptionDAO
                .getSubscriptionsByUserIdPagination(1, "cars1", 1, 10,
                        "title", "ASC", connection).size());
        assertEquals(0, subscriptionDAO
                .getSubscriptionsByUserIdPagination(1, "auto", 0, 10,
                        "title", "ASC", connection).size());
    }

    @Test
    void update() throws DAOException {
        assertThrows(UnsupportedOperationException.class, () -> subscriptionDAO.update(null, connection));
    }

    @Test
    void delete() throws DAOException {
        int countBeforeDeleting = subscriptionDAO.getTotalAmountByUserId(1, connection);

        subscriptionDAO.delete(1, connection);

        int countAfterDeleting = subscriptionDAO.getTotalAmountByUserId(1, connection);
        assertEquals(1, countBeforeDeleting - countAfterDeleting);

        DAOException daoException = assertThrows(DAOException.class, () -> subscriptionDAO.delete(1, connection));
        assertThat(daoException.getMessage(), containsString("Can not delete subscription"));
    }
}