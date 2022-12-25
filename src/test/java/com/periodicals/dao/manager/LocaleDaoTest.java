package com.periodicals.dao.manager;

import com.periodicals.dao.exception.DAOException;
import com.periodicals.dao.mysql.DBManager;
import com.periodicals.dao.mysql.LocaleDAOMySql;
import com.periodicals.entity.LocaleCustom;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LocaleDaoTest {

    @Mock
    LocaleDAOMySql dao;

    LocaleDao daoManager;

    @BeforeEach
    void beforeEach() {
        daoManager = new LocaleDao(DBManager.getInstance());
        replaceWithMockedField();
    }

    private void replaceWithMockedField() {
        try {
            Field field = daoManager.getClass().getDeclaredField("localeDAO");
            field.setAccessible(true);
            field.set(daoManager, dao);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    void createLocale() throws DAOException {
        LocaleCustom locale = new LocaleCustom(
                "en", "English", "uah", null);

        assertDoesNotThrow(() -> daoManager.createLocale(locale));

        doThrow(new DAOException()).when(dao).create(isA(LocaleCustom.class), isA(Connection.class));
        assertThrows(DAOException.class, () -> daoManager.createLocale(locale));
    }

    @Test
    void getAllLocalesList() throws DAOException {
        when(dao.getAll(any(Connection.class))).thenReturn(null);
        assertNull(daoManager.getAllLocalesList());

        when(dao.getAll(any(Connection.class))).thenReturn(new ArrayList<>());
        assertEquals(new ArrayList<>(), daoManager.getAllLocalesList());
    }

    @Test
    void getLocaleById() throws DAOException {
        LocaleCustom locale = new LocaleCustom(
                "en", "English", "uah", null);

        doReturn(locale).when(dao).getEntityById(eq("en"), any(Connection.class));
        assertEquals(locale, daoManager.getLocaleById("en"));

        doReturn(null).when(dao).getEntityById(eq("uk"), any(Connection.class));
        assertNull(daoManager.getLocaleById("uk"));

        doReturn(null).when(dao).getEntityById(isNull(), any(Connection.class));
        assertNull(daoManager.getLocaleById(null));
    }

    @Test
    void updateLocale() throws DAOException {
        LocaleCustom locale = new LocaleCustom(
                "en", "English", "usd", null);

        assertDoesNotThrow(() -> daoManager.updateLocale(locale));

        doThrow(new DAOException()).when(dao).update(isA(LocaleCustom.class), isA(Connection.class));
        assertThrows(DAOException.class, () -> daoManager.updateLocale(locale));

        doThrow(new NullPointerException())
                .when(dao).update(isNull(), isA(Connection.class));
        assertThrows(NullPointerException.class, () -> daoManager.updateLocale(null));
    }

    @Test
    void deleteLocale() throws DAOException {
        assertDoesNotThrow(() -> daoManager.deleteLocale("uk"));
        assertDoesNotThrow(() -> daoManager.deleteLocale(null));

        doThrow(new DAOException()).when(dao).delete(any(String.class), isA(Connection.class));
        assertThrows(DAOException.class, () -> daoManager.deleteLocale("de"));
    }
}