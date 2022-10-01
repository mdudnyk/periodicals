package com.periodicals.dao.mysql;

import com.periodicals.dao.exception.DAOException;
import com.periodicals.dao.UserDAO;
import com.periodicals.entity.User;
import com.periodicals.entity.enums.BlockingStatus;
import com.periodicals.entity.enums.Role;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOMySql implements UserDAO {

    @Override
    public void create(final User entity, Connection connection) throws DAOException {
        try(PreparedStatement ps = connection.prepareStatement(Queries.CREATE_USER, Statement.RETURN_GENERATED_KEYS)) {
            fillPreparedStatement(ps, entity);
            if (ps.executeUpdate() > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        entity.setId(rs.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Can not add new user to the database. " + e.getMessage());
        }
    }

    @Override
    public List<User> getAll(Connection connection) throws DAOException {
        List<User> users = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(Queries.GET_ALL_USERS);
             ResultSet resultSet = ps.executeQuery()) {
            while (resultSet.next()) {
                users.add(fillEntityFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException("Error while trying to get users list from database. " + e.getMessage());
        }

        return users;
    }

    @Override
    public User getEntityById(final Integer id, Connection connection) throws DAOException {
        User user = null;

        try (PreparedStatement ps = connection.prepareStatement(Queries.GET_USER_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.isBeforeFirst()) {
                rs.next();
                user = fillEntityFromResultSet(rs);
                rs.close();
            }
        } catch (SQLException e) {
            throw new DAOException("Error while trying to get user by it`s ID=" + id + ". " + e.getMessage());
        }

        return user;
    }

    @Override
    public User getUserByEmail(final String email, Connection connection) throws DAOException {
        User user = null;

        try (PreparedStatement ps = connection.prepareStatement(Queries.GET_USER_BY_EMAIL)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.isBeforeFirst()) {
                rs.next();
                user = fillEntityFromResultSet(rs);
                rs.close();
            }
        } catch (SQLException e) {
            throw new DAOException("Error while trying to get user by it`s email: " + email + ". " + e.getMessage());
        }

        return user;
    }

    @Override
    public void update(final User entity, Connection connection) throws DAOException {
        try (PreparedStatement ps = connection.prepareStatement(Queries.UPDATE_USER)) {
            fillPreparedStatement(ps, entity);
            ps.setInt(9, entity.getId());
            if (ps.executeUpdate() < 1) {
                throw new DAOException("We don`t have such user. ");
            }
        } catch (SQLException e) {
            throw new DAOException("Can not update user with ID=" + entity.getId() + " in database. " + e.getMessage());
        }
    }

    @Override
    public void delete(final Integer id, Connection connection) throws DAOException {
        try (PreparedStatement ps = connection.prepareStatement(Queries.DELETE_USER)) {
            ps.setInt(1, id);
            if (ps.executeUpdate() < 1) {
                throw new DAOException("We don`t have such user. ");
            }
        } catch (SQLException e) {
            throw new DAOException("Can not delete user with ID=" + id + " from database. " + e.getMessage());
        }
    }

    private void fillPreparedStatement(PreparedStatement ps, User entity) throws SQLException {
        ps.setString(1, entity.getLocaleId());
        ps.setString(2, entity.getFirstname());
        ps.setString(3, entity.getLastname());
        ps.setString(4, entity.getPassword());
        ps.setString(5, entity.getEmail());
        ps.setString(6, entity.getRole().name());
        ps.setInt(7, entity.getBalance());
        ps.setString(8, entity.getBlockingStatus().name());
    }

    private User fillEntityFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(1);
        String localeId = resultSet.getString(2);
        String firstName = resultSet.getString(3);
        String lastName = resultSet.getString(4);
        String password = resultSet.getString(5);
        String email = resultSet.getString(6);
        Role role = Role.valueOf(resultSet.getString(7));
        int balance = resultSet.getInt(8);
        BlockingStatus blockingStatus = BlockingStatus.valueOf(resultSet.getString(9));

        return new User(id, localeId, firstName, lastName, password, email, role, balance, blockingStatus);
    }
}
