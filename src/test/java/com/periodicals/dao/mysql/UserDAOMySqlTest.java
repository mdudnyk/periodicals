package com.periodicals.dao.mysql;

import com.periodicals.dao.UserDAO;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.entity.User;
import com.periodicals.entity.enums.UserRole;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.List;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.matchers.JUnitMatchers.containsString;

class UserDAOMySqlTest {
    private static DBManager dbManager;
    private static Connection connection;
    UserDAO userDAO = new UserDAOMySql();

    @BeforeAll
    static void globalSetUp() throws SQLException, IOException {
        dbManager = DBManager.getInstance();
        connection = dbManager.getConnection();
        dbManager.setUpDatabase(connection);
        Statement statement = connection.createStatement();
        statement.execute("INSERT INTO locale values('uk', 'українська', 'грн', '/img/ua_flag.png')");
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
        statement.execute("INSERT INTO user values(1, 'uk', 'admin', 'admin', 'qwerty123', 'admin@gmail.com', 'ADMIN', 1000000, false)");
        statement.close();
    }

    @AfterEach
    void tearDown() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("DELETE FROM user");
        statement.close();
    }

    @Test
    void create() throws DAOException {
        User user = new User("uk", "Myroslav", "Dudnyk", "yamahar1",
                "mad0013@mail.ru", UserRole.CUSTOMER, 100, false);
        assertDoesNotThrow(() -> userDAO.create(user, connection));

        assertEquals(2, userDAO.getAll(connection).size());

        assertThrows(DAOException.class, () -> userDAO.create(user, connection));
        assertThrows(NullPointerException.class, () -> userDAO.create(null, connection));
    }

    @Test
    void getAll() throws DAOException {
        List<User> usersFromDB = userDAO.getAll(connection);
        assertEquals(1, usersFromDB.size());
    }

    @Test
    void getEntityById() throws DAOException {
        assertNotNull(userDAO.getEntityById(1, connection));
        assertNull(userDAO.getEntityById(99, connection));
    }

    @Test
    void getUserByEmail() throws DAOException {
        assertNotNull(userDAO.getUserByEmail("admin@gmail.com", connection));
        assertNull(userDAO.getUserByEmail(null, connection));
        assertNull(userDAO.getUserByEmail("admin@gmail", connection));
    }

    @Test
    void update() throws DAOException {
        User userBeforeUpdate = userDAO.getEntityById(1, connection);
        User userForTest = new User(1, "uk", "Myroslav", "Dudnyk", "yamahar1",
                "admin@gmail.com", UserRole.CUSTOMER, 100, false);
        userDAO.update(userForTest, connection);
        User userAfterUpdate = userDAO.getEntityById(1, connection);
        assertNotEquals(userBeforeUpdate, userAfterUpdate);

        userForTest.setId(10);
        DAOException daoException = assertThrows(DAOException.class, () -> userDAO.update(userForTest, connection));
        assertThat(daoException.getMessage(), containsString("Can not update user"));

        assertThrows(NullPointerException.class, () -> userDAO.update(null, connection));
    }

    @Test
    void delete() throws DAOException {
        int countBeforeDeleting = userDAO.getAll(connection).size();

        userDAO.delete(1, connection);

        int countAfterDeleting = userDAO.getAll(connection).size();
        assertNotEquals(countBeforeDeleting, countAfterDeleting);

        DAOException daoException = assertThrows(DAOException.class, () -> userDAO.delete(1, connection));
        assertThat(daoException.getMessage(), containsString("Can not delete user"));
    }

    @Test
    void getCustomersPaginationWithoutSearch() throws DAOException {
        User user1 = new User("uk", "1", "1", "1",
                "1", UserRole.CUSTOMER, 100, false);
        User user2 = new User("uk", "2", "2", "2",
                "2", UserRole.CUSTOMER, 100, false);
        User user3 = new User("uk", "3", "3", "3",
                "3", UserRole.CUSTOMER, 100, false);

        userDAO.create(user1, connection);
        userDAO.create(user2, connection);
        userDAO.create(user3, connection);

        List<User> users = userDAO
                .getCustomersPagination(0, 5, "name", "ASC", connection);
        assertEquals(3, users.size());
        users = userDAO
                .getCustomersPagination(0, 1, "name", "ASC", connection);
        assertEquals(1, users.size());
        users = userDAO
                .getCustomersPagination(3, 1, "name", "ASC", connection);
        assertEquals(0, users.size());
        users = userDAO
                .getCustomersPagination(3, 10, "name", "ASC", connection);
        assertEquals(0, users.size());
        users = userDAO
                .getCustomersPagination(0, 5, null, "ASC", connection);
        assertEquals(3, users.size());
        users = userDAO
                .getCustomersPagination(0, 5, "name", null, connection);
        assertEquals(3, users.size());
        users = userDAO
                .getCustomersPagination(0, 5, "AAA", "ASC", connection);
        assertEquals(3, users.size());
        users = userDAO
                .getCustomersPagination(0, 5, "name", "AAA", connection);
        assertEquals(3, users.size());

        assertThrows(DAOException.class, () -> userDAO
                .getCustomersPagination(-1, 1, "name", "ASC", connection));
        assertThrows(DAOException.class, () -> userDAO
                .getCustomersPagination(1, -1, "name", "ASC", connection));
    }

    @Test
    void GetCustomersPaginationWithSearch() throws DAOException {
        User user1 = new User("uk", "1", "1", "1",
                "1", UserRole.CUSTOMER, 100, false);
        User user2 = new User("uk", "2", "2", "2",
                "2", UserRole.CUSTOMER, 100, false);
        User user3 = new User("uk", "3", "3", "3",
                "3", UserRole.CUSTOMER, 100, false);

        userDAO.create(user1, connection);
        userDAO.create(user2, connection);
        userDAO.create(user3, connection);

        List<User> users = userDAO
                .getCustomersPagination("qwerty", 0, 5, "name", "ASC", connection);
        assertEquals(0, users.size());
    }

    @Test
    void getCustomersAmountWithoutSearch() throws DAOException {
        assertEquals(0, userDAO.getCustomersAmount(connection));
        User user1 = new User("uk", "1", "1", "1",
                "1", UserRole.CUSTOMER, 100, false);
        User user2 = new User("uk", "2", "2", "2",
                "2", UserRole.CUSTOMER, 100, false);

        userDAO.create(user1, connection);
        userDAO.create(user2, connection);

        assertEquals(2, userDAO.getCustomersAmount(connection));
    }

    @Test
    void testGetCustomersAmountWithSearch() throws DAOException {
        User user1 = new User("uk", "1", "1", "1",
                "1", UserRole.CUSTOMER, 100, false);
        User user2 = new User("uk", "2", "2", "2",
                "2", UserRole.CUSTOMER, 100, false);

        userDAO.create(user1, connection);
        userDAO.create(user2, connection);

        assertEquals(1, userDAO.getCustomersAmount("1", connection));
        assertEquals(0, userDAO.getCustomersAmount("3", connection));
    }
}