package com.periodicals.dao.mysql;

import com.periodicals.dao.SubscriptionCalendarDAO;
import com.periodicals.dao.SubscriptionDAO;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.entity.MonthSelector;
import org.json.simple.JSONArray;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.matchers.JUnitMatchers.containsString;

class SubscriptionCalendarDAOMySqlTest {
    private static DBManager dbManager;
    private static Connection connection;
    SubscriptionCalendarDAO subscriptionCalendarDAO = new SubscriptionCalendarDAOMySql();

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
        statement.execute("INSERT INTO subscription values(1, 1, 1, 'Classic Cars1', 16300, " +
                "'2022-12-09 00:00:00', '2023-11-30 00:00:00')");
        statement.execute("INSERT INTO subscription values(2, 1, 1, 'Classic Cars2', 16300, " +
                "'2022-12-09 00:00:00', '2023-11-30 00:00:00')");
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
        statement.execute("INSERT INTO subscription_calendar values (1, 2022, " +
                "'[true, true, true, true, true, true, true, true, true, true, true, true]')");
        statement.execute("INSERT INTO subscription_calendar values (1, 2023, " +
                "'[true, true, true, true, true, true, true, true, true, true, true, true]')");
        statement.close();
    }

    @AfterEach
    void tearDown() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("DELETE FROM subscription_calendar");
        statement.close();
    }

    @Test
    void create() throws DAOException {
        JSONArray activeMonths = new JSONArray();
        for (int i = 0; i < 12; i++) {
            activeMonths.add(true);
        }
        MonthSelector releaseYear2022 = new MonthSelector(2022, activeMonths);
        assertDoesNotThrow(() -> {
            subscriptionCalendarDAO.create(2, releaseYear2022, connection);
        });

        DAOException daoException = assertThrows(DAOException.class, () -> {
            subscriptionCalendarDAO.create(1, releaseYear2022, connection);
        });
        assertThat(daoException.getMessage(), containsString("Can not add new subscription calendar"));
        daoException = assertThrows(DAOException.class, () -> {
            subscriptionCalendarDAO.create(5, releaseYear2022, connection);
        });
        assertThat(daoException.getMessage(), containsString("Can not add new subscription calendar"));

        Map<Integer, MonthSelector> calendar = subscriptionCalendarDAO
                .getCalendarBySubscriptionId(2, connection);
        assertEquals(releaseYear2022, calendar.get(2022));
        assertEquals(1, calendar.size());
    }

    @Test
    void getCalendarBySubscriptionId() throws DAOException {
        JSONArray activeMonths = new JSONArray();
        for (int i = 0; i < 12; i++) {
            activeMonths.add(true);
        }
        MonthSelector releaseYear2022 = new MonthSelector(2022, activeMonths);
        MonthSelector releaseYear2023 = new MonthSelector(2023, activeMonths);

        Map<Integer, MonthSelector> calendar = subscriptionCalendarDAO
                .getCalendarBySubscriptionId(1, connection);

        assertEquals(releaseYear2022, calendar.get(2022));
        assertEquals(releaseYear2023, calendar.get(2023));
        assertEquals(2, calendar.size());

        calendar = subscriptionCalendarDAO
                .getCalendarBySubscriptionId(3, connection);
        assertEquals(0, calendar.size());
    }
}