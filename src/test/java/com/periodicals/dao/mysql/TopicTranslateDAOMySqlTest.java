package com.periodicals.dao.mysql;

import com.periodicals.dao.TopicTranslateDAO;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.entity.TopicTranslate;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.matchers.JUnitMatchers.containsString;

class TopicTranslateDAOMySqlTest {
    private static DBManager dbManager;
    private static Connection connection;
    TopicTranslateDAO topicTranslateDAO = new TopicTranslateDAOMySql();

    @BeforeAll
    static void globalSetUp() throws SQLException, IOException {
        dbManager = DBManager.getInstance();
        connection = dbManager.getConnection();
        dbManager.setUpDatabase(connection);
        Statement statement = connection.createStatement();
        statement.execute("INSERT INTO locale values('uk', 'українська', 'грн', '/img/ua_flag.png')");
        statement.execute("INSERT INTO locale values('en', 'english', 'uah', '/img/en_flag.png')");
        statement.execute("INSERT INTO topic values (1)");
        statement.execute("INSERT INTO topic values (2)");
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
        statement.execute("INSERT INTO topic_translate values (1, 'en', 'Automotive')");
        statement.execute("INSERT INTO topic_translate values (1, 'uk', 'Автомобілі')");
        statement.execute("INSERT INTO topic_translate values (2, 'en', 'Sport')");
        statement.close();
    }

    @AfterEach
    void tearDown() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("DELETE FROM topic_translate");
        statement.close();
    }
    @Test
    void create() {
        TopicTranslate topicTranslate = new TopicTranslate(1, "ua", "Films");
        assertThrows(UnsupportedOperationException.class, () -> topicTranslateDAO.create(topicTranslate, connection));
    }

    @Test
    void testCreate() throws DAOException {
        TopicTranslate topicTranslate = new TopicTranslate(2, "uk", "Спорт");
        topicTranslateDAO.create(2, topicTranslate, connection);
        assertEquals(2, topicTranslateDAO.getAllTranslates(2, connection).size());

        DAOException daoException = assertThrows(DAOException.class,
                () -> topicTranslateDAO.create(2, topicTranslate, connection));
        assertThat(daoException.getMessage(), containsString("Can not add new topic translate"));

        topicTranslate.setTopicID(5);
        daoException = assertThrows(DAOException.class,
                () -> topicTranslateDAO.create(2, topicTranslate, connection));
        assertThat(daoException.getMessage(), containsString("Can not add new topic translate"));

        daoException = assertThrows(DAOException.class,
                () -> topicTranslateDAO.create(122, topicTranslate, connection));
        assertThat(daoException.getMessage(), containsString("Can not add new topic translate"));

        topicTranslate.setName(null);
        daoException = assertThrows(DAOException.class,
                () -> topicTranslateDAO.create(122, topicTranslate, connection));
        assertThat(daoException.getMessage(), containsString("Can not add new topic translate"));
    }

    @Test
    void getAllTranslates() throws DAOException {
        assertEquals(2, topicTranslateDAO.getAllTranslates(1, connection).size());
        assertEquals(1, topicTranslateDAO.getAllTranslates(2, connection).size());
        assertEquals(0, topicTranslateDAO.getAllTranslates(100, connection).size());
    }

    @Test
    void getTranslateByLocale() throws DAOException {
        assertNotNull(topicTranslateDAO.getTranslateByLocale(1, "uk", connection));
        assertNull(topicTranslateDAO.getTranslateByLocale(2, "uk", connection));
        assertNull(topicTranslateDAO.getTranslateByLocale(10, "en", connection));
    }

    @Test
    void update() throws DAOException {
        TopicTranslate topicTranslate = topicTranslateDAO.getTranslateByLocale(1, "uk", connection);
        String newTopicName = "Автосвіт";
        topicTranslate.setName(newTopicName);
        topicTranslateDAO.update(topicTranslate, connection);
        topicTranslate = topicTranslateDAO.getTranslateByLocale(1, "uk", connection);
        assertEquals(newTopicName, topicTranslate.getName());

        TopicTranslate falseTopicTranslate = new TopicTranslate(100, "es", "Food");
        assertDoesNotThrow(() -> topicTranslateDAO.update(falseTopicTranslate, connection));
    }

    @Test
    void delete() throws DAOException {
        TopicTranslate topicTranslate = topicTranslateDAO.getTranslateByLocale(1, "uk", connection);
        assertThrows(UnsupportedOperationException.class, () -> topicTranslateDAO.create(topicTranslate, connection));
    }

    @Test
    void checkIfTranslationExists() throws DAOException {
        assertTrue(topicTranslateDAO.checkIfTranslationExists(1, "uk", connection));
        assertFalse(topicTranslateDAO.checkIfTranslationExists(2, "uk", connection));
        assertFalse(topicTranslateDAO.checkIfTranslationExists(100, null, connection));
    }
}