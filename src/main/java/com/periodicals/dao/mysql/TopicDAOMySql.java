package com.periodicals.dao.mysql;

import com.periodicals.dao.TopicDAO;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.entity.Topic;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TopicDAOMySql implements TopicDAO {

    @Override
    public void create(final Topic entity, final Connection connection) throws DAOException {
        try(PreparedStatement ps = connection.prepareStatement(Queries.CREATE_TOPIC, Statement.RETURN_GENERATED_KEYS)) {
            if (ps.executeUpdate() > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        entity.setId(rs.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Can not add new topic to the database. " + e.getMessage());
        }
    }

    @Override
    public List<Topic> getAll(final Connection connection) throws DAOException {
        List<Topic> topics = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(Queries.GET_ALL_TOPICS);
             ResultSet resultSet = ps.executeQuery()) {
            while (resultSet.next()) {
                topics.add(new Topic(resultSet.getInt(1)));
            }
        } catch (SQLException e) {
            throw new DAOException("Error while trying to get topics list from database. " + e.getMessage());
        }

        return topics;
    }

    @Override
    public Topic getEntityById(final Integer id, final Connection connection) throws DAOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(final Topic entity, final Connection connection) throws DAOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(final Integer id, final Connection connection) throws DAOException {
        try (PreparedStatement ps = connection.prepareStatement(Queries.DELETE_TOPIC)) {
            ps.setInt(1, id);
            if (ps.executeUpdate() < 1) {
                throw new DAOException("We don`t have such topic. ");
            }
        } catch (SQLException e) {
            throw new DAOException("Can not delete topic with ID=" + id + " from database. " + e.getMessage());
        }
    }
}
