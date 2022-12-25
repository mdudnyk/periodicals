package com.periodicals.dao.mysql;

import com.periodicals.dao.interfacesForDAO.ReleaseCalendarDAO;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.entity.MonthSelector;
import org.json.simple.JSONArray;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Myroslav Dudnyk
 */
class ReleaseCalendarDAOMySqlTest {
    private static DBManager dbManager;
    private static Connection connection;
    ReleaseCalendarDAO releaseCalendarDAO = new ReleaseCalendarDAOMySql();

    @BeforeAll
    static void globalSetUp() throws SQLException, IOException {
        dbManager = DBManager.getInstance();
        connection = dbManager.getConnection();
        dbManager.setUpDatabase(connection);
        Statement statement = connection.createStatement();
        statement.execute("INSERT INTO topic values(1)");
        statement.execute("INSERT INTO periodical values(1, 1, 'Classic Cars1', '166643252774218.jpeg', 16300, " +
                "'{\"amount\": 1, \"period\": \"month\"}', true)");
        statement.execute("INSERT INTO periodical values(2, 1, 'Classic Cars2', '166643252774218.jpeg', 16300, " +
                "'{\"amount\": 1, \"period\": \"month\"}', true)");
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
        statement.execute("INSERT INTO release_calendar values (1, 2022, " +
                "'[true, true, true, true, true, true, true, true, true, true, true, true]')");
        statement.execute("INSERT INTO release_calendar values (1, 2023, " +
                "'[true, true, true, true, true, true, true, true, true, true, true, true]')");
        statement.close();
    }

    @AfterEach
    void tearDown() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("DELETE FROM release_calendar");
        statement.close();
    }

    @Test
    void create() throws DAOException {
        JSONArray activeMonths = new JSONArray();
        for (int i = 0; i < 12; i++) {
            if ((i & 1) == 0) {
                activeMonths.add(true);
            } else {
                activeMonths.add(false);
            }
        }
        MonthSelector releaseYear2022 = new MonthSelector(2022, activeMonths);
        MonthSelector releaseYear2023 = new MonthSelector(2023, activeMonths);

        assertDoesNotThrow(() -> releaseCalendarDAO.create(2, releaseYear2022, connection));
        assertDoesNotThrow(() -> releaseCalendarDAO.create(2, releaseYear2023, connection));
        assertThrows(DAOException.class, () -> releaseCalendarDAO.create(2, releaseYear2022, connection));
        assertThrows(DAOException.class, () -> releaseCalendarDAO.create(3, releaseYear2022, connection));

        assertEquals(releaseYear2022, releaseCalendarDAO
                .getCalendarByPeriodicalIdAndYear(2, 2022, connection));
        assertEquals(releaseYear2023, releaseCalendarDAO
                .getCalendarByPeriodicalIdAndYear(2, 2023, connection));
        assertNotEquals(releaseYear2023, releaseCalendarDAO
                .getCalendarByPeriodicalIdAndYear(1, 2023, connection));
    }

    @Test
    void getCalendarByPeriodicalId() throws DAOException {
        JSONArray activeMonths = new JSONArray();
        for (int i = 0; i < 12; i++) {
            activeMonths.add(true);
        }
        MonthSelector releaseYear2022 = new MonthSelector(2022, activeMonths);
        MonthSelector releaseYear2023 = new MonthSelector(2023, activeMonths);
        Map<Integer, MonthSelector> calendarForTest = new HashMap<>();
        calendarForTest.put(2022, releaseYear2022);
        calendarForTest.put(2023, releaseYear2023);

        Map<Integer, MonthSelector> calendarFromDB = releaseCalendarDAO
                .getCalendarByPeriodicalId(1, connection);
        assertEquals(calendarForTest, calendarFromDB);

        calendarFromDB = releaseCalendarDAO
                .getCalendarByPeriodicalId(2, connection);
        assertEquals(0, calendarFromDB.size());
    }

    @Test
    void getCalendarByPeriodicalIdAndYear() throws DAOException {
        JSONArray activeMonths = new JSONArray();
        for (int i = 0; i < 12; i++) {
            activeMonths.add(true);
        }
        MonthSelector releaseYear2022 = new MonthSelector(2022, activeMonths);
        MonthSelector releaseYear2023 = new MonthSelector(2023, activeMonths);
        Map<Integer, MonthSelector> calendarForTest = new HashMap<>();
        calendarForTest.put(2022, releaseYear2022);
        calendarForTest.put(2023, releaseYear2023);

        assertEquals(releaseYear2022, releaseCalendarDAO
                .getCalendarByPeriodicalIdAndYear(1, 2022, connection));
        assertEquals(releaseYear2023, releaseCalendarDAO
                .getCalendarByPeriodicalIdAndYear(1, 2023, connection));
        assertNull(releaseCalendarDAO
                .getCalendarByPeriodicalIdAndYear(1, 2024, connection));
        assertNull(releaseCalendarDAO
                .getCalendarByPeriodicalIdAndYear(3, 2023, connection));
    }

    @Test
    void checkIfCalendarExists() throws DAOException {
        assertTrue(releaseCalendarDAO.checkIfCalendarExists(1, 2022, connection));
        assertTrue(releaseCalendarDAO.checkIfCalendarExists(1, 2023, connection));
        assertFalse(releaseCalendarDAO.checkIfCalendarExists(1, 2024, connection));
        assertFalse(releaseCalendarDAO.checkIfCalendarExists(22, 2, connection));
    }

    @Test
    void update() throws DAOException {
        MonthSelector releaseBeforeUpdate = releaseCalendarDAO
                .getCalendarByPeriodicalIdAndYear(1, 2022, connection);

        JSONArray activeMonths = new JSONArray();
        for (int i = 0; i < 12; i++) {
            if ((i & 1) == 0) {
                activeMonths.add(true);
            } else {
                activeMonths.add(false);
            }
        }
        MonthSelector releaseToUpdate = new MonthSelector(2022, activeMonths);
        releaseCalendarDAO.update(1, releaseToUpdate, connection);

        MonthSelector releaseAfterUpdate = releaseCalendarDAO
                .getCalendarByPeriodicalIdAndYear(1, 2022, connection);

        assertNotEquals(releaseBeforeUpdate, releaseAfterUpdate);

        MonthSelector releaseToUpdateFalse = new MonthSelector(2022, null);
        assertThrows(NullPointerException.class,
                () -> releaseCalendarDAO.update(1, releaseToUpdateFalse, connection));
        assertThrows(NullPointerException.class,
                () -> releaseCalendarDAO.update(1, null, connection));
    }
}