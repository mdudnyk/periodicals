package com.periodicals.dao.mysql;

import com.periodicals.dao.PeriodicalTranslationDAO;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.entity.PeriodicalTranslate;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.matchers.JUnitMatchers.containsString;

class PeriodicalTranslationDAOMySqlTest {
    private static DBManager dbManager;
    private static Connection connection;
    PeriodicalTranslationDAO periodicalTranslationDAO = new PeriodicalTranslationDAOMySql();

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
        statement.execute("INSERT INTO periodical values(1, 1, 'Classic Cars1', '166643252774218.jpeg', 16300, " +
                "'{\"amount\": 1, \"period\": \"month\"}', true)");
        statement.execute("INSERT INTO periodical values(2, 1, 'Classic Cars2', '166643252774218.jpeg', 16301, " +
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
        statement.execute("INSERT INTO periodical_translate values(1, 'en', 'Great Britain', 'English', " +
                "'BBC Top Gear is the UK’s best-selling car magazine.')");
        statement.execute("INSERT INTO periodical_translate values(1, 'uk', 'Велика Британія', 'Англійська', " +
                "'BBC Top Gear — це найпопулярніший автомобільний журнал у Великій Британії.')");
        statement.close();
    }

    @AfterEach
    void tearDown() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("DELETE FROM periodical_translate");
        statement.close();
    }

    @Test
    void create() throws DAOException {
        PeriodicalTranslate periodicalTranslate =
                new PeriodicalTranslate("uk", "Україна", "Англійська", "Привіт");
        assertDoesNotThrow(() -> periodicalTranslationDAO.create(2, periodicalTranslate, connection));

        DAOException daoException = assertThrows(DAOException.class,
                () -> periodicalTranslationDAO.create(2, periodicalTranslate, connection));
        assertThat(daoException.getMessage(), containsString("Can not add new periodical translate"));
    }

    @Test
    void getTranslationsByPeriodicalId() throws DAOException {
        PeriodicalTranslate periodicalTranslateEn =
                new PeriodicalTranslate("en", "Great Britain", "English",
                        "BBC Top Gear is the UK’s best-selling car magazine.");
        PeriodicalTranslate periodicalTranslateUk =
                new PeriodicalTranslate("uk", "Велика Британія", "Англійська",
                        "BBC Top Gear — це найпопулярніший автомобільний журнал у Великій Британії.");

        Map<String, PeriodicalTranslate> translationsFromDB = periodicalTranslationDAO
                .getTranslationsByPeriodicalId(1, connection);

        assertEquals(periodicalTranslateEn, translationsFromDB.get("en"));
        assertEquals(periodicalTranslateUk, translationsFromDB.get("uk"));
        assertNull(translationsFromDB.get("de"));

        translationsFromDB = periodicalTranslationDAO
                .getTranslationsByPeriodicalId(2, connection);
        assertEquals(0, translationsFromDB.size());
    }

    @Test
    void getTranslationByPeriodicalIdAndLocale() throws DAOException {
        PeriodicalTranslate periodicalTranslateEn =
                new PeriodicalTranslate("en", "Great Britain", "English",
                        "BBC Top Gear is the UK’s best-selling car magazine.");
        PeriodicalTranslate periodicalTranslateUk =
                new PeriodicalTranslate("uk", "Велика Британія", "Англійська",
                        "BBC Top Gear — це найпопулярніший автомобільний журнал у Великій Британії.");

        assertEquals(periodicalTranslateEn, periodicalTranslationDAO
                .getTranslationByPeriodicalIdAndLocale(1, "en", connection));
        assertEquals(periodicalTranslateUk, periodicalTranslationDAO
                .getTranslationByPeriodicalIdAndLocale(1, "uk", connection));
        assertNull(periodicalTranslationDAO
                .getTranslationByPeriodicalIdAndLocale(1, "de", connection));
        assertNull(periodicalTranslationDAO
                .getTranslationByPeriodicalIdAndLocale(2, "en", connection));
    }

    @Test
    void checkIfTranslationExists() throws DAOException {
        assertTrue(periodicalTranslationDAO.checkIfTranslationExists(1, "en", connection));
        assertTrue(periodicalTranslationDAO.checkIfTranslationExists(1, "uk", connection));
        assertFalse(periodicalTranslationDAO.checkIfTranslationExists(1, "de", connection));
        assertFalse(periodicalTranslationDAO.checkIfTranslationExists(1, null, connection));
        assertFalse(periodicalTranslationDAO.checkIfTranslationExists(2, "en", connection));
    }

    @Test
    void update() throws DAOException {
        PeriodicalTranslate periodicalTranslateEnFromDBBefore = periodicalTranslationDAO
                .getTranslationByPeriodicalIdAndLocale(1, "en", connection);

        PeriodicalTranslate periodicalTranslateEn =
                new PeriodicalTranslate("en", "Great Britain", "French",
                        "BBC Top Gear is the UK’s best-selling car magazine.");
        assertDoesNotThrow(() -> periodicalTranslationDAO.update(1, periodicalTranslateEn, connection));

        PeriodicalTranslate periodicalTranslateEnFromDBAfter = periodicalTranslationDAO
                .getTranslationByPeriodicalIdAndLocale(1, "en", connection);

        assertNotEquals(periodicalTranslateEnFromDBBefore, periodicalTranslateEnFromDBAfter);
        assertDoesNotThrow(() -> periodicalTranslationDAO.update(100, periodicalTranslateEn, connection));
    }
}