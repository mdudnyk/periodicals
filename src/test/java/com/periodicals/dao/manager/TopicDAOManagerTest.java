package com.periodicals.dao.manager;

import com.periodicals.dao.exception.DAOException;
import com.periodicals.dao.mysql.DBManager;
import com.periodicals.dao.mysql.TopicDAOMySql;
import com.periodicals.dao.mysql.TopicTranslateDAOMySql;
import com.periodicals.entity.PeriodicalTranslate;
import com.periodicals.entity.Topic;
import com.periodicals.entity.TopicTranslate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.matchers.JUnitMatchers.containsString;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class TopicDAOManagerTest {

    @Mock
    TopicDAOMySql topicDAO;

    @Mock
    TopicTranslateDAOMySql translationDAO;

    TopicDAOManager daoManager;

    @BeforeEach
    void beforeEach() {
        daoManager = new TopicDAOManager(DBManager.getInstance());
        replaceWithMockedField();
    }

    private void replaceWithMockedField() {
        try {
            Field field = daoManager.getClass().getDeclaredField("topicDAO");
            field.setAccessible(true);
            field.set(daoManager, topicDAO);

            field = daoManager.getClass().getDeclaredField("topicTranslateDAO");
            field.setAccessible(true);
            field.set(daoManager, translationDAO);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    void createTopic() throws DAOException {
        TopicTranslate translate = new TopicTranslate(1, "en", "Automotive");
        Topic topic = new Topic(1, translate);
        assertDoesNotThrow(() -> daoManager.createTopic(topic));

        Topic topicSimple = new Topic(2);
        assertDoesNotThrow(() -> daoManager.createTopic(topicSimple));

        doThrow(DAOException.class).when(translationDAO)
                .create(anyInt(), any(TopicTranslate.class), any(Connection.class));
        DAOException daoException = assertThrows(DAOException.class, () -> daoManager.createTopic(topic));
        assertThat(daoException.getMessage(), containsString("Unable to finish transaction"));
    }

    @Test
    void getTopicById() throws DAOException {
        Topic topic = new Topic(1);

        doReturn(topic).when(topicDAO).getEntityById(anyInt(), any(Connection.class));
        doThrow(DAOException.class).when(translationDAO).getAllTranslates(anyInt(), any(Connection.class));
        assertThrows(DAOException.class, () -> daoManager.getTopicById(1));

        doReturn(null).when(translationDAO).getAllTranslates(anyInt(), any(Connection.class));
        assertThrows(NullPointerException.class, () -> daoManager.getTopicById(1));

        TopicTranslate translate = new TopicTranslate(1, "en", "Automotive");
        topic.addTranslate(translate);

        doReturn(Map.of(translate.getLocaleID(), translate))
                .when(translationDAO).getAllTranslates(anyInt(), any(Connection.class));
        assertEquals(topic, daoManager.getTopicById(1));
    }

    @Test
    void getTopicByIdAndLocale() throws DAOException {
        Topic topic = new Topic(1);

        doReturn(null).when(topicDAO).getEntityById(anyInt(), any(Connection.class));
        assertNull(daoManager.getTopicByIdAndLocale(1, "ua", "en"));

        doReturn(topic).when(topicDAO).getEntityById(anyInt(), any(Connection.class));
        assertEquals(topic, daoManager.getTopicByIdAndLocale(1, "ua", "en"));
    }

    @Test
    void getTranslationByIdAndLocale() throws DAOException {
        TopicTranslate translate = new TopicTranslate(1, "en", "Automotive");

        doReturn(translate).when(translationDAO)
                .getTranslateByLocale(anyInt(), anyString(), any(Connection.class));
        assertEquals(translate, daoManager.getTranslationByIdAndLocale(1, "en"));

        doReturn(null).when(translationDAO)
                .getTranslateByLocale(anyInt(), isNull(), any(Connection.class));
        assertNull(daoManager.getTranslationByIdAndLocale(1, null));
    }

    @Test
    void getTopicsSortPag() throws DAOException {
        List<Topic> topics = List.of(new Topic(1), new Topic(2));

        doReturn(topics).when(topicDAO)
                .getAllByLocalePagination(any(Connection.class), anyString(),
                        anyString(), anyInt(), anyInt(), anyString());
        assertEquals(topics, daoManager
                .getTopicsSortPag("en", "uk", 0, 0, "ASC"));

        doReturn(List.of()).when(topicDAO)
                .getAllByLocalePagination(any(Connection.class), isNull(),
                        isNull(), anyInt(), anyInt(), isNull());
        assertEquals(List.of(), daoManager
                .getTopicsSortPag(null, null, 0, 0, null));
    }

    @Test
    void getTopicsByNameSortPag() throws DAOException {
        List<Topic> topics = List.of(new Topic(1));

        doReturn(topics).when(topicDAO)
                .getAllByNameAndLocalePagination(any(Connection.class), anyString(),
                        anyString(), anyString(), anyInt(), anyInt(), anyString());
        assertEquals(topics, daoManager
                .getTopicsByNameSortPag("en", "uk", 0, 0,
                        "ASC", "Automotive"));

        doReturn(List.of()).when(topicDAO)
                .getAllByNameAndLocalePagination(any(Connection.class), isNull(),
                        isNull(), isNull(), anyInt(), anyInt(), isNull());
        assertEquals(List.of(), daoManager
                .getTopicsByNameSortPag(null, null, 0, 0,
                        null, null));
    }

    @Test
    void getAllTopicsByLocale() throws DAOException {
        List<Topic> topics = List.of(new Topic(1));

        doReturn(topics).when(topicDAO)
                .getAllWithTranslatesByLocale(any(Connection.class), anyString(), anyString());
        assertEquals(topics, daoManager
                .getAllTopicsByLocale("en", "uk"));

        doReturn(List.of()).when(topicDAO)
                .getAllWithTranslatesByLocale(any(Connection.class), isNull(), isNull());
        assertEquals(List.of(), daoManager
                .getAllTopicsByLocale(null, null));
    }

    @Test
    void getTopicsAmount() throws DAOException {
        doReturn(10).when(topicDAO)
                .getTopicsAmount(any(Connection.class));
        assertEquals(10, daoManager.getTopicsAmount());

        doReturn(0).when(topicDAO)
                .getTopicsAmount(any(Connection.class));
        assertEquals(0, daoManager.getTopicsAmount());
    }

    @Test
    void getTopicsAmountSearchMode() throws DAOException {
        doReturn(1).when(topicDAO)
                .getTopicsAmountSearchMode(any(Connection.class), anyString());
        assertEquals(1, daoManager.getTopicsAmountSearchMode("Automotive"));

        doReturn(0).when(topicDAO)
                .getTopicsAmountSearchMode(any(Connection.class), isNull());
        assertEquals(0, daoManager.getTopicsAmountSearchMode(null));
    }

    @Test
    void getTopicByName() throws DAOException {
        Topic topic = new Topic(1);
        List<String> searchWords = List.of("Automotive", "Bober", "Bimba");

        doReturn(topic).when(topicDAO)
                .getTopicByName(anyString(), any(Connection.class));
        assertEquals(topic, daoManager.getTopicByName(searchWords));

        doReturn(null).when(topicDAO)
                .getTopicByName(anyString(), any(Connection.class));
        assertNull(daoManager.getTopicByName(searchWords));

        assertThrows(NullPointerException.class, () -> daoManager.getTopicByName(null));
    }

    @Test
    void updateTopic() throws DAOException {
        TopicTranslate translate = new TopicTranslate(1, "en", "Automotive");
        Topic topic = new Topic(1, translate);

        assertDoesNotThrow(() -> daoManager.updateTopic(topic));

        assertThrows(NullPointerException.class, () -> daoManager.updateTopic(null));

        doThrow(DAOException.class).when(translationDAO).create(anyInt(),
                any(TopicTranslate.class), any(Connection.class));

        DAOException daoException = assertThrows(DAOException.class, () -> daoManager.updateTopic(topic));
        assertThat(daoException.getMessage(), containsString("Unable to finish transaction"));
    }

    @Test
    void deleteTopic() throws DAOException {
        assertDoesNotThrow(() -> daoManager.deleteTopic(1));

        doThrow(DAOException.class).when(topicDAO).delete(anyInt(), any(Connection.class));
        assertThrows(DAOException.class, () -> daoManager.deleteTopic(10));
    }
}