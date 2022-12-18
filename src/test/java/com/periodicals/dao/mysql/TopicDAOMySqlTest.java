package com.periodicals.dao.mysql;

import com.periodicals.dao.TopicDAO;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.entity.Topic;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.matchers.JUnitMatchers.containsString;

class TopicDAOMySqlTest {
    private static DBManager dbManager;
    private static Connection connection;
    TopicDAO topicDAO = new TopicDAOMySql();

    @BeforeAll
    static void globalSetUp() throws SQLException, IOException {
        dbManager = DBManager.getInstance();
        connection = dbManager.getConnection();
        dbManager.setUpDatabase(connection);
        Statement statement = connection.createStatement();
        statement.execute("INSERT INTO locale values('uk', 'українська', 'грн', '/img/ua_flag.png')");
        statement.execute("INSERT INTO locale values('en', 'english', 'uah', '/img/en_flag.png')");
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
        statement.execute("INSERT INTO topic values (1)");
        statement.execute("INSERT INTO topic values (2)");
        statement.execute("INSERT INTO topic_translate values (1, 'en', 'Automotive')");
        statement.execute("INSERT INTO topic_translate values (1, 'uk', 'Автомобілі')");
        statement.execute("INSERT INTO topic_translate values (2, 'en', 'Sport')");
        statement.close();
    }

    @AfterEach
    void tearDown() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("DELETE FROM topic");
        statement.close();
    }

    @Test
    void create() throws DAOException {
        assertDoesNotThrow(() -> topicDAO.create(new Topic(2), connection));
        assertEquals(3, topicDAO.getAll(connection).size());
        assertThrows(NullPointerException.class, () -> topicDAO.create(null, connection));
    }

    @Test
    void getAll() throws DAOException {
        List<Topic> topicsFromDB = topicDAO.getAll(connection);
        assertEquals(2, topicsFromDB.size());
    }

    @Test
    void getAllWithTranslatesByLocale() throws DAOException {
        List<Topic> topics = topicDAO.getAllWithTranslatesByLocale(connection, "uk", "en");
        assertEquals(2, topics.size());

        topics = topicDAO.getAllWithTranslatesByLocale(connection, "uk", "uk");
        assertEquals(2, topics.size());

        for (Topic topic : topics) {
            if (topic.getId() == 2) {
                assertNull(topic.getTranslate("uk").getName());
            } else if (topic.getId() == 1) {
                assertNotNull(topic.getTranslate("uk").getName());
            }
        }
    }

    @Test
    void getAllByLocalePagination() throws DAOException {
        List<Topic> topics = topicDAO
                .getAllByLocalePagination(connection, "en", "en", 0, 10, "ASC");
        assertEquals(2, topics.size());

        topics = topicDAO
                .getAllByLocalePagination(connection, "en", "en", 2, 10, "ASC");
        assertEquals(0, topics.size());

        topics = topicDAO
                .getAllByLocalePagination(connection, "en", "en", 0, 10, null);
        assertEquals(2, topics.size());
    }

    @Test
    void getAllByNameAndLocalePagination() throws DAOException {
        List<Topic> topics = topicDAO
                .getAllByNameAndLocalePagination(connection, "Автомобілі", "en", "en",
                        0, 10, "ASC");
        assertEquals(1, topics.size());

        topics = topicDAO
                .getAllByNameAndLocalePagination(connection, "Автомобілі", "en", "en",
                        2, 10, "ASC");
        assertEquals(0, topics.size());

        topics = topicDAO
                .getAllByNameAndLocalePagination(connection, "Sport", "ua", "en",
                        0, 10, "DESC");
        assertEquals(1, topics.size());
    }

    @Test
    void getEntityById() throws DAOException {
        assertNotNull(topicDAO.getEntityById(1, connection));
        assertNull(topicDAO.getEntityById(10, connection));
    }

    @Test
    void getTopicByName() throws DAOException {
        assertNotNull(topicDAO.getTopicByName("Automotive", connection));
        assertNotNull(topicDAO.getTopicByName("Автомобілі", connection));
        assertNull(topicDAO.getTopicByName("Авт", connection));
        assertNull(topicDAO.getTopicByName(null, connection));
    }

    @Test
    void update() {
        assertThrows(UnsupportedOperationException.class, () -> topicDAO.update(new Topic(1), connection));
    }

    @Test
    void delete() throws DAOException {
        int countBeforeDeleting = topicDAO.getAll(connection).size();

        topicDAO.delete(1, connection);

        int countAfterDeleting = topicDAO.getAll(connection).size();
        assertEquals(1, countBeforeDeleting - countAfterDeleting);

        DAOException daoException = assertThrows(DAOException.class, () -> topicDAO.delete(1, connection));
        assertThat(daoException.getMessage(), containsString("We don`t have such topic"));
    }

    @Test
    void getTopicsAmount() throws DAOException {
        assertEquals(2, topicDAO.getTopicsAmount(connection));
    }

    @Test
    void getTopicsAmountSearchMode() throws DAOException {
        assertEquals(1, topicDAO.getTopicsAmountSearchMode(connection, "Auto"));
        assertEquals(0, topicDAO.getTopicsAmountSearchMode(connection, "qwerty"));
        assertEquals(0, topicDAO.getTopicsAmountSearchMode(connection, null));
    }
}