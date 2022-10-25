package com.periodicals.dao.mysql;

import com.periodicals.dao.exception.DAOException;
import com.periodicals.entity.User;
import com.periodicals.entity.enums.UserRole;
import org.junit.jupiter.api.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

class UserDAOMySqlTest {
    private static DBManager dbManager;
    private static Connection connection;

    @BeforeAll
    static void globalSetUp() throws SQLException {
        dbManager = DBManager.getInstance();
        connection = dbManager.getConnection();
    }

    @AfterAll
    static void globalTearDown() throws SQLException {
        dbManager.close(connection);
    }

    @BeforeEach
    void setUp() throws SQLException {
        Statement statement = connection.createStatement();
//        statement.execute("DELETE FROM user");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void create() throws DAOException {
        User user = new User(1, "ua", "Myroslav", "Dudnyk", "yamahar1",
                "mad0013@mail.ru", UserRole.CUSTOMER, 100, false);
        new UserDAOMySql().create(user, connection);
    }

    @Test
    void getAll() {
    }

    @Test
    void getEntityById() {
    }

    @Test
    void getUserByEmail() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}