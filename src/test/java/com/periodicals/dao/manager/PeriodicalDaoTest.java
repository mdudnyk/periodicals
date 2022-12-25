package com.periodicals.dao.manager;

import com.periodicals.dao.exception.DAOException;
import com.periodicals.dao.mysql.DBManager;
import com.periodicals.dao.mysql.PeriodicalDAOMySql;
import com.periodicals.dao.mysql.PeriodicalTranslationDAOMySql;
import com.periodicals.dao.mysql.ReleaseCalendarDAOMySql;
import com.periodicals.entity.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.matchers.JUnitMatchers.containsString;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PeriodicalDaoTest {

    @Mock
    PeriodicalDAOMySql periodicalDAO;

    @Mock
    PeriodicalTranslationDAOMySql translationDAO;

    @Mock
    ReleaseCalendarDAOMySql calendarDAO;

    PeriodicalDao daoManager;

    Periodical periodical;
    PeriodicalForTable periodicalForTable;


    @BeforeEach
    void beforeEach() {
        daoManager = new PeriodicalDao(DBManager.getInstance());
        replaceWithMockedField();
        periodical = new Periodical(1, 1, "TopGear",
                "no_image.jpg", 100, new JSONObject(), true);
        periodicalForTable = new PeriodicalForTable(1, "TopGear",
                "Automotive", 100, true);
    }

    private void replaceWithMockedField() {
        try {
            Field field = daoManager.getClass().getDeclaredField("periodicalDAO");
            field.setAccessible(true);
            field.set(daoManager, periodicalDAO);

            field = daoManager.getClass().getDeclaredField("periodicalTranslationDAO");
            field.setAccessible(true);
            field.set(daoManager, translationDAO);

            field = daoManager.getClass().getDeclaredField("releaseCalendarDAO");
            field.setAccessible(true);
            field.set(daoManager, calendarDAO);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getIsPeriodicalExists() throws DAOException {
        doReturn(true).when(periodicalDAO)
                .getIsPeriodicalExists(any(Connection.class), eq("TopGear"));
        assertTrue(daoManager.getIsPeriodicalExists("TopGear"));

        doReturn(false).when(periodicalDAO)
                .getIsPeriodicalExists(any(Connection.class), isNull());
        assertFalse(daoManager.getIsPeriodicalExists(null));
    }

    @Test
    void testGetIsPeriodicalExists() throws DAOException {
        doReturn(true).when(periodicalDAO)
                .getIsPeriodicalExists(any(Connection.class), anyInt(), eq("TopGear"));
        assertTrue(daoManager.getIsPeriodicalExists(1, "TopGear"));

        doReturn(false).when(periodicalDAO)
                .getIsPeriodicalExists(any(Connection.class), anyInt(), isNull());
        assertFalse(daoManager.getIsPeriodicalExists(1,null));
    }

    @Test
    void getPeriodicalsForTableSortPag() throws DAOException {
        List<PeriodicalForTable> periodicals = List.of(periodicalForTable);

        doReturn(periodicals).when(periodicalDAO)
                .getPeriodicalsForTableSortPag(any(Connection.class),
                        anyString(), anyString(), anyInt(), anyInt(),
                        anyString(), anyString());
        assertEquals(periodicals, daoManager
                .getPeriodicalsForTableSortPag(
                        "ua", "en", 0, 0, "title", "ASC"));

        doReturn(List.of()).when(periodicalDAO)
                .getPeriodicalsForTableSortPag(any(Connection.class),
                        isNull(), isNull(), anyInt(), anyInt(),
                        isNull(),  isNull());
        assertEquals(List.of(), daoManager
                .getPeriodicalsForTableSortPag(
                        null, null, 0, 0, null, null));
    }

    @Test
    void getPeriodicalsForTableByTitleSortPag() throws DAOException {
        List<PeriodicalForTable> periodicals = List.of(periodicalForTable);

        doReturn(periodicals).when(periodicalDAO)
                .getPeriodicalsForTableByTitleSortPag(any(Connection.class),
                        anyString(), anyString(), anyInt(), anyInt(),
                        anyString(), anyString(), anyString());
        assertEquals(periodicals, daoManager
                .getPeriodicalsForTableByTitleSortPag(
                        "ua", "en", 0, 0, "title", "ASC", "Top"));

        doReturn(List.of()).when(periodicalDAO)
                .getPeriodicalsForTableByTitleSortPag(any(Connection.class),
                        isNull(), isNull(), anyInt(), anyInt(),
                        isNull(),  isNull(), isNull());
        assertEquals(List.of(), daoManager
                .getPeriodicalsForTableByTitleSortPag(
                        null, null, 0, 0, null, null, null));
    }

    @Test
    void getPeriodicalsAmount() throws DAOException {
        doReturn(10).when(periodicalDAO).getPeriodicalsAmount(any(Connection.class));
        assertEquals(10, daoManager.getPeriodicalsAmount());
    }

    @Test
    void getPeriodicalsAmountSearchMode() throws DAOException {
        doReturn(1).when(periodicalDAO)
                .getPeriodicalsAmountSearchMode(any(Connection.class), eq("Top"));
        assertEquals(1, daoManager.getPeriodicalsAmountSearchMode("Top"));

        doReturn(0).when(periodicalDAO)
                .getPeriodicalsAmountSearchMode(any(Connection.class), isNull());
        assertEquals(0, daoManager.getPeriodicalsAmountSearchMode(null));
    }

    @Test
    void deletePeriodical() throws DAOException {
        assertDoesNotThrow(() -> daoManager.deletePeriodical(1));

        doThrow(DAOException.class).when(periodicalDAO).delete(anyInt(), any(Connection.class));
        assertThrows(DAOException.class, () -> daoManager.deletePeriodical(100));
    }

    @Test
    void createPeriodical() throws DAOException {
        assertThrows(NullPointerException.class, () -> daoManager.createPeriodical(periodical));

        PeriodicalTranslate translation =
                new PeriodicalTranslate("ua", "Ukraine", "Ukrainian", "About");
        MonthSelector calendar = new MonthSelector(2022, new JSONArray());
        periodical.setTranslation(Map.of(translation.getLocaleID(), translation));
        periodical.setReleaseCalendar(Map.of(calendar.getYear(), calendar));
        assertDoesNotThrow(() -> daoManager.createPeriodical(periodical));

        doThrow(DAOException.class).when(translationDAO)
                .create(anyInt(), any(PeriodicalTranslate.class), any(Connection.class));
        DAOException daoException = assertThrows(DAOException.class, () -> daoManager.createPeriodical(periodical));
        assertThat(daoException.getMessage(), containsString("Unable to finish transaction"));
    }

    @Test
    void editPeriodical() throws DAOException {
        assertThrows(NullPointerException.class, () -> daoManager.editPeriodical(periodical));

        PeriodicalTranslate translation =
                new PeriodicalTranslate("ua", "Ukraine", "Ukrainian", "About");
        MonthSelector calendar = new MonthSelector(2022, new JSONArray());
        periodical.setTranslation(Map.of(translation.getLocaleID(), translation));
        periodical.setReleaseCalendar(Map.of(calendar.getYear(), calendar));
        assertDoesNotThrow(() -> daoManager.editPeriodical(periodical));

        doReturn(true).when(calendarDAO)
                .checkIfCalendarExists(anyInt(), anyInt(), any(Connection.class));
        doThrow(DAOException.class).when(calendarDAO)
                .update(anyInt(), any(MonthSelector.class), any(Connection.class));
        DAOException daoException = assertThrows(DAOException.class, () -> daoManager.editPeriodical(periodical));
        assertThat(daoException.getMessage(), containsString("Unable to finish transaction"));
    }

    @Test
    void getPeriodicalById() throws DAOException {
        doReturn(periodical).when(periodicalDAO).getEntityById(anyInt(), any(Connection.class));
        assertEquals(periodical, daoManager.getPeriodicalById(1));

        doReturn(null).when(periodicalDAO).getEntityById(anyInt(), any(Connection.class));
        assertNull(daoManager.getPeriodicalById(1));
    }

    @Test
    void testGetPeriodicalById() throws DAOException {
        PeriodicalTranslate translation =
                new PeriodicalTranslate("ua", "Ukraine", "Ukrainian", "About");
        MonthSelector calendar = new MonthSelector(2022, new JSONArray());

        doReturn(null).when(periodicalDAO)
                .getEntityById(anyInt(), any(Connection.class));
        assertNull(daoManager
                .getPeriodicalById(1, "ua", "en", 2022));

        doReturn(periodical).when(periodicalDAO)
                .getEntityById(anyInt(), any(Connection.class));
        doReturn(translation).when(translationDAO)
                .getTranslationByPeriodicalIdAndLocale(anyInt(), anyString(), any(Connection.class));
        doReturn(calendar).when(calendarDAO)
                .getCalendarByPeriodicalIdAndYear(anyInt(), anyInt(), any(Connection.class));

        periodical.setTranslation(Map.of(translation.getLocaleID(), translation));
        periodical.setReleaseCalendar(Map.of(calendar.getYear(), calendar));

        assertEquals(periodical, daoManager
                .getPeriodicalById(1, "ua", "en", 2022));
    }

    @Test
    void getPeriodicalsForHomePage() throws DAOException {
        PeriodicalForHomePage periodical = new PeriodicalForHomePage(1, "TopGear",
                "Automotive", 100);
        List<Topic> topics = List.of(new Topic(1));

        doReturn(List.of(periodical)).when(periodicalDAO)
                .getPeriodicalsForHomePage(anyInt(), any(Connection.class));

        assertEquals(Map.of(1, List.of(periodical)), daoManager.getPeriodicalsForHomePage(topics));
    }
}