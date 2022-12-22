package com.periodicals.dao.mysql;

import com.periodicals.dao.PeriodicalDAO;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.entity.Periodical;
import com.periodicals.entity.PeriodicalForTable;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.matchers.JUnitMatchers.containsString;

class PeriodicalDAOMySqlTest {
    private static DBManager dbManager;
    private static Connection connection;
    PeriodicalDAO periodicalDAO = new PeriodicalDAOMySql();

    @BeforeAll
    static void globalSetUp() throws SQLException, IOException {
        dbManager = DBManager.getInstance();
        connection = dbManager.getConnection();
        dbManager.setUpDatabase(connection);
        Statement statement = connection.createStatement();
        statement.execute("INSERT INTO locale values('en', 'English', 'uah', 'no_flag')");
        statement.execute("INSERT INTO locale values('uk', 'Українська', 'грн', 'no_flag')");
        statement.execute("INSERT INTO topic values(1)");
        statement.execute("INSERT INTO topic_translate values(1, 'en', 'Automotive')");
        statement.execute("INSERT INTO topic_translate values(1, 'uk', 'Автомобілі')");
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
        statement.execute("INSERT INTO periodical values(1, 1, 'Classic Cars1', '166643252774218.jpeg', 16300, " +
                "'{\"amount\": 1, \"period\": \"month\"}', true)");
        statement.execute("INSERT INTO periodical values(2, 1, 'Classic Cars2', '166643252774218.jpeg', 16301, " +
                "'{\"amount\": 1, \"period\": \"month\"}', true)");
        statement.execute("INSERT INTO periodical values(3, 1, 'Classic Cars3', '166643252774218.jpeg', 16302, " +
                "'{\"amount\": 1, \"period\": \"month\"}', true)");
        statement.execute("INSERT INTO periodical values(4, 1, 'Classic Cars4', '166643252774218.jpeg', 16303, " +
                "'{\"amount\": 1, \"period\": \"month\"}', true)");
        statement.execute("INSERT INTO periodical values(5, 1, 'Classic Cars5', '166643252774218.jpeg', 16304, " +
                "'{\"amount\": 1, \"period\": \"month\"}', true)");
        statement.close();
    }

    @AfterEach
    void tearDown() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("DELETE FROM periodical");
        statement.close();
    }

    @Test
    void create() throws DAOException {
        JSONObject frequency = new JSONObject();
        frequency.put("amount", 1);
        frequency.put("period", "month");
        Periodical periodical = new Periodical(1, "Classic Cars", "166643252774218.jpeg",
                15400, frequency, true);

        assertDoesNotThrow(() -> periodicalDAO.create(periodical, connection));

        assertEquals(6, periodicalDAO.getPeriodicalsAmount(connection));

        assertThrows(DAOException.class, () -> periodicalDAO.create(periodical, connection));
        assertThrows(NullPointerException.class, () -> periodicalDAO.create(null, connection));
    }

    @Test
    void getAll() {
        assertThrows(UnsupportedOperationException.class, () -> periodicalDAO.getAll(connection));
    }

    @Test
    void getEntityById() throws DAOException {
        assertNotNull(periodicalDAO.getEntityById(1, connection));
        assertNull(periodicalDAO.getEntityById(99, connection));
    }

    @Test
    void update() throws DAOException {
        Periodical periodicalBeforeUpdate = periodicalDAO.getEntityById(1, connection);

        JSONObject frequency = new JSONObject();
        frequency.put("amount", 1);
        frequency.put("period", "month");
        Periodical periodicalForTest = new Periodical(1, 1, "Classic Cars",
                "166643252774218.jpeg", 15400, frequency, true);
        assertDoesNotThrow(() -> periodicalDAO.update(periodicalForTest, connection));

        Periodical periodicalAfterUpdate = periodicalDAO.getEntityById(1, connection);

        assertNotEquals(periodicalBeforeUpdate, periodicalAfterUpdate);

        assertThrows(NullPointerException.class, () -> periodicalDAO.update(null, connection));

        Periodical falsePeriodicalForTest = new Periodical(10, 1, "Be-be-be",
                "166643252774218.jpeg", 15400, frequency, true);
        DAOException daoException = assertThrows(DAOException.class, () -> periodicalDAO.update(falsePeriodicalForTest, connection));
        assertThat(daoException.getMessage(), containsString("Can not update periodical"));
    }

    @Test
    void updateWithoutImage() throws DAOException {
        Periodical periodicalBeforeUpdate = periodicalDAO.getEntityById(1, connection);

        JSONObject frequency = new JSONObject();
        frequency.put("amount", 1);
        frequency.put("period", "month");
        Periodical periodicalForTest = new Periodical(1, 1, "Classic Cars",
                "166643252774218.jpeg", 15400, frequency, true);
        periodicalDAO.updateWithoutImage(periodicalForTest, connection);

        Periodical periodicalAfterUpdate = periodicalDAO.getEntityById(1, connection);

        assertNotEquals(periodicalBeforeUpdate, periodicalAfterUpdate);

        assertThrows(NullPointerException.class, () -> periodicalDAO.updateWithoutImage(null, connection));

        Periodical falsePeriodicalForTest = new Periodical(10, 1, "Be-be-be",
                "166643252774218.jpeg", 15400, frequency, true);
        DAOException daoException = assertThrows(DAOException.class, () -> periodicalDAO.update(falsePeriodicalForTest, connection));
        assertThat(daoException.getMessage(), containsString("Can not update periodical"));
    }

    @Test
    void getPeriodicalsForHomePage() throws DAOException {
        assertEquals(4, periodicalDAO.getPeriodicalsForHomePage(1, connection).size());
        assertEquals(0, periodicalDAO.getPeriodicalsForHomePage(2, connection).size());
    }

    @Test
    void delete() throws DAOException {
        int countBeforeDeleting = periodicalDAO.getPeriodicalsAmount(connection);

        periodicalDAO.delete(1, connection);

        int countAfterDeleting = periodicalDAO.getPeriodicalsAmount(connection);
        assertEquals(1, countBeforeDeleting - countAfterDeleting);

        DAOException daoException = assertThrows(DAOException.class, () -> periodicalDAO.delete(1, connection));
        assertThat(daoException.getMessage(), containsString("Can not delete periodical"));
    }

    @Test
    void getPeriodicalsForTableSortPag() throws DAOException {
        assertEquals(5, periodicalDAO
                .getPeriodicalsForTableSortPag(connection, "en", "en", 0, 10,
                        "status", "ASC").size());
        assertEquals(3, periodicalDAO
                .getPeriodicalsForTableSortPag(connection, "en", "en", 0, 3,
                        "status", "ASC").size());
        assertEquals(0, periodicalDAO
                .getPeriodicalsForTableSortPag(connection, "en", "en", 5, 3,
                        "status", "ASC").size());
        assertEquals(0, periodicalDAO
                .getPeriodicalsForTableSortPag(connection, "en", "en", 0, 0,
                        "status", "ASC").size());
        assertEquals(1, periodicalDAO
                .getPeriodicalsForTableSortPag(connection, "en", "en", 4, 10,
                        "status", "ASC").size());
        assertTrue(periodicalDAO
                .getPeriodicalsForTableSortPag(connection, null, null, 0, 0,
                        null, null).isEmpty());

        PeriodicalForTable periodical = new PeriodicalForTable(4, "Classic Cars4", "Автомобілі",
                16303, true);
        List<PeriodicalForTable> periodicalList = new ArrayList<>();
        periodicalList.add(periodical);

        List<PeriodicalForTable> periodicalListFromDB = periodicalDAO
                .getPeriodicalsForTableSortPag(connection, "uk", "en", 3, 1,
                        "title", "ASC");

        assertEquals(periodicalList.get(0), periodicalListFromDB.get(0));
    }

    @Test
    void getPeriodicalsForTableByTitleSortPag() throws DAOException {
        assertEquals(5, periodicalDAO
                .getPeriodicalsForTableByTitleSortPag(connection, "en", "en", 0, 10,
                        "status", "ASC", "classic cars").size());
        assertEquals(0, periodicalDAO
                .getPeriodicalsForTableByTitleSortPag(connection, "en", "en", 0, 0,
                        "status", "ASC", "classic cars").size());
        assertEquals(1, periodicalDAO
                .getPeriodicalsForTableByTitleSortPag(connection, "en", "en", 0, 10,
                        "status", "ASC", "classic cars1").size());
        assertEquals(0, periodicalDAO
                .getPeriodicalsForTableByTitleSortPag(connection, "en", "en", 0, 10,
                        "status", "ASC", "classic cars100").size());
        assertTrue(periodicalDAO
                .getPeriodicalsForTableByTitleSortPag(connection, null, null, 0, 0,
                        null, null, null).isEmpty());

        PeriodicalForTable periodical = new PeriodicalForTable(4, "Classic Cars4", "Автомобілі",
                16303, true);
        List<PeriodicalForTable> periodicalList = new ArrayList<>();
        periodicalList.add(periodical);

        List<PeriodicalForTable> periodicalListFromDB = periodicalDAO
                .getPeriodicalsForTableByTitleSortPag(connection, "uk", "en", 0, 10,
                        "status", "ASC", "classic cars4");

        assertEquals(periodicalList.get(0), periodicalListFromDB.get(0));
    }

    @Test
    void getPeriodicalsAmount() throws DAOException {
        assertEquals(5, periodicalDAO.getPeriodicalsAmount(connection));

        for (int i = 1; i < 6; i++) {
            periodicalDAO.delete(i, connection);
        }

        assertEquals(0, periodicalDAO.getPeriodicalsAmount(connection));
    }

    @Test
    void getPeriodicalsAmountSearchMode() throws DAOException {
        assertEquals(5, periodicalDAO.getPeriodicalsAmountSearchMode(connection, "classic"));
        assertEquals(1, periodicalDAO.getPeriodicalsAmountSearchMode(connection, "cars1"));

        periodicalDAO.delete(1, connection);

        assertEquals(0, periodicalDAO.getPeriodicalsAmountSearchMode(connection, "cars1"));

        for (int i = 2; i < 6; i++) {
            periodicalDAO.delete(i, connection);
        }

        assertEquals(0, periodicalDAO.getPeriodicalsAmountSearchMode(connection, "classic cars"));
    }

    @Test
    void getIsPeriodicalExists() throws DAOException {
        assertTrue(periodicalDAO.getIsPeriodicalExists(connection, "classic cars1"));
        periodicalDAO.delete(1, connection);
        assertFalse(periodicalDAO.getIsPeriodicalExists(connection, "classic cars1"));
        assertFalse(periodicalDAO.getIsPeriodicalExists(connection, "classic"));
    }

    @Test
    void testGetIsPeriodicalExists() throws DAOException {
        assertFalse(periodicalDAO.getIsPeriodicalExists(connection, 1, "classic cars1"));
        assertTrue(periodicalDAO.getIsPeriodicalExists(connection, 3, "classic cars1"));

        periodicalDAO.delete(1, connection);

        assertFalse(periodicalDAO.getIsPeriodicalExists(connection, 3, "classic cars1"));
        assertFalse(periodicalDAO.getIsPeriodicalExists(connection, 1, "classic"));
    }
}