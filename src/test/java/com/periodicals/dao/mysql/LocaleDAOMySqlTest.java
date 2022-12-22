package com.periodicals.dao.mysql;

import com.periodicals.dao.LocaleDAO;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.entity.LocaleCustom;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.matchers.JUnitMatchers.containsString;

class LocaleDAOMySqlTest {
    private static DBManager dbManager;
    private static Connection connection;
    LocaleDAO localeDao = new LocaleDAOMySql();

    @BeforeAll
    static void globalSetUp() throws SQLException, IOException {
        dbManager = DBManager.getInstance();
        connection = dbManager.getConnection();
        dbManager.setUpDatabase(connection);
    }


    @AfterAll
    static void globalTearDown() throws SQLException {
        dbManager.dropDatabase(connection);
        dbManager.close(connection);
    }

    @BeforeEach
    void setUp() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("INSERT INTO locale values('uk', 'українська', 'грн', '/img/ua_flag.png')");
        statement.execute("INSERT INTO locale values('en', 'english', 'uah', '/img/en_flag.png')");
        statement.close();
    }

    @AfterEach
    void tearDown() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("DELETE FROM locale");
        statement.execute("DELETE FROM user");
        statement.close();
    }

    @Test
    void create() throws DAOException {
        LocaleCustom locale =
                new LocaleCustom("de", "German", "uah", "/img/de_flag.png");
        assertDoesNotThrow(() -> localeDao.create(locale, connection));

        assertEquals(3, localeDao.getAll(connection).size());

        assertThrows(DAOException.class, () -> localeDao.create(locale, connection));
        assertThrows(NullPointerException.class, () -> localeDao.create(null, connection));
    }

    @Test
    void getAll() throws DAOException {
        List<LocaleCustom> locales = localeDao.getAll(connection);
        assertEquals(2, locales.size());
    }

    @Test
    void getEntityById() throws DAOException {
        assertNotNull(localeDao.getEntityById("uk", connection));
        assertNotNull(localeDao.getEntityById("en", connection));
        assertNull(localeDao.getEntityById("de", connection));
    }

    @Test
    void update() throws DAOException {
        LocaleCustom falseLocaleToUpdate =
                new LocaleCustom("de", "German", "uah", "/img/de_flag.png");

        DAOException daoException = assertThrows(DAOException.class,
                () -> localeDao.update(falseLocaleToUpdate, connection));
        assertThat(daoException.getMessage(), containsString("Can not update locale"));

        LocaleCustom correctLocaleToUpdate =
                new LocaleCustom("en", "German", "uah", "/img/de_flag.png");
        assertDoesNotThrow(() -> localeDao.update(correctLocaleToUpdate, connection));
    }

    @Test
    void delete() throws DAOException {
        int countBeforeDeleting = localeDao.getAll(connection).size();

        localeDao.delete("uk", connection);
        localeDao.delete("en", connection);

        int countAfterDeleting = localeDao.getAll(connection).size();
        assertNotEquals(countBeforeDeleting, countAfterDeleting);

        DAOException daoException = assertThrows(DAOException.class, () -> localeDao.delete("uk", connection));
        assertThat(daoException.getMessage(), containsString("Can not delete locale"));
    }
}