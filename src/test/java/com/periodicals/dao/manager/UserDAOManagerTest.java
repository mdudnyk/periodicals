package com.periodicals.dao.manager;

import com.periodicals.dao.exception.DAOException;
import com.periodicals.dao.mysql.DBManager;
import com.periodicals.dao.mysql.UserDAOMySql;
import com.periodicals.entity.LocaleCustom;
import com.periodicals.entity.User;
import com.periodicals.entity.enums.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserDAOManagerTest {

    @Mock
    UserDAOMySql dao;

    UserDAOManager daoManager;

    User user;


    @BeforeEach
    void beforeEach() {
        daoManager = new UserDAOManager(DBManager.getInstance());
        replaceWithMockedField();
        user = new User(1, "en", "Miroslav",
                "Dudnyk", "password", "email", UserRole.CUSTOMER,
                1000, false);
    }

    private void replaceWithMockedField() {
        try {
            Field field = daoManager.getClass().getDeclaredField("userDAO");
            field.setAccessible(true);
            field.set(daoManager, dao);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    void createUser() throws DAOException {
        assertDoesNotThrow(() -> daoManager.createUser(user));

        doThrow(new DAOException()).when(dao).create(isA(User.class), isA(Connection.class));
        assertThrows(DAOException.class, () -> daoManager.createUser(user));

        doThrow(new NullPointerException()).when(dao).create(isNull(), isA(Connection.class));
        assertThrows(NullPointerException.class, () -> daoManager.createUser(null));
    }

    @Test
    void getUserById() throws DAOException {
        lenient().doReturn(user).when(dao).getEntityById(eq(1), any(Connection.class));
        lenient().doReturn(null).when(dao).getEntityById(eq(2), any(Connection.class));

        assertEquals(user, daoManager.getUserById(1));
        assertNull(daoManager.getUserById(2));
    }

    @Test
    void getUserByEmail() throws DAOException {
        lenient().doReturn(user).when(dao).getUserByEmail(eq("email"), any(Connection.class));
        lenient().doReturn(null).when(dao).getUserByEmail(eq("bober"), any(Connection.class));
        lenient().doReturn(null).when(dao).getUserByEmail(eq(null), any(Connection.class));

        assertEquals(user, daoManager.getUserByEmail("email"));
        assertNull(daoManager.getUserByEmail("bober"));
        assertNull(daoManager.getUserByEmail(null));
    }

    @Test
    void getCustomersPagination() throws DAOException {
        List<User> users = List.of(user);

        doReturn(users).when(dao).getCustomersPagination(
                anyInt(), anyInt(), anyString(), anyString(), any(Connection.class));
        assertEquals(users, daoManager
                .getCustomersPagination(1, 1, "name", "ASC"));

        doReturn(new ArrayList<>()).when(dao).getCustomersPagination(
                anyInt(), anyInt(), anyString(), anyString(), any(Connection.class));
        assertTrue(daoManager
                .getCustomersPagination(1, 1, "name", "ASC").isEmpty());

        doReturn(users).when(dao).getCustomersPagination(
                anyInt(), anyInt(), isNull(), isNull(), any(Connection.class));
        assertEquals(users, daoManager
                .getCustomersPagination(1, 1, null, null));
    }

    @Test
    void testGetCustomersPagination() throws DAOException {
        List<User> users = List.of(user);

        doReturn(users).when(dao).getCustomersPagination(
                eq("Miroslav"), anyInt(), anyInt(), anyString(), anyString(), any(Connection.class));
        assertEquals(users, daoManager
                .getCustomersPagination("Miroslav", 1, 1, "name", "ASC"));

        doReturn(users).when(dao).getCustomersPagination(
                isNull(), anyInt(), anyInt(), anyString(), anyString(), any(Connection.class));
        assertEquals(users, daoManager
                .getCustomersPagination(null, 1, 1, "name", "ASC"));

        doReturn(List.of()).when(dao).getCustomersPagination(
                eq("Petro"), anyInt(), anyInt(), anyString(), anyString(), any(Connection.class));
        assertTrue(daoManager
                .getCustomersPagination("Petro", 1, 1, "name", "ASC").isEmpty());

        doReturn(List.of()).when(dao).getCustomersPagination(
                isNull(), anyInt(), anyInt(), isNull(), isNull(), any(Connection.class));
        assertTrue(daoManager
                .getCustomersPagination(null, 1, 1, null, null).isEmpty());
    }

    @Test
    void getCustomersAmount() throws DAOException {
        doReturn(10).when(dao).getCustomersAmount(any(Connection.class));
        assertEquals(10, daoManager.getCustomersAmount());
    }

    @Test
    void testGetCustomersAmount() throws DAOException {
        doReturn(10).when(dao).getCustomersAmount(anyString(), any(Connection.class));
        assertEquals(10, daoManager.getCustomersAmount("Miroslav"));

        doReturn(0).when(dao).getCustomersAmount(isNull(), any(Connection.class));
        assertEquals(0, daoManager.getCustomersAmount(null));
    }

    @Test
    void updateUser() throws DAOException {
        assertDoesNotThrow(() -> daoManager.updateUser(user));

        doThrow(new DAOException()).when(dao).update(isA(User.class), isA(Connection.class));
        assertThrows(DAOException.class, () -> daoManager.updateUser(user));

        doThrow(new NullPointerException())
                .when(dao).update(isNull(), isA(Connection.class));
        assertThrows(NullPointerException.class, () -> daoManager.updateUser(null));
    }
}