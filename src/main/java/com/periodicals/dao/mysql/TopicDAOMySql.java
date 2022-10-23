package com.periodicals.dao.mysql;

import com.periodicals.dao.TopicDAO;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.entity.Topic;
import com.periodicals.entity.TopicTranslate;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TopicDAOMySql implements TopicDAO {

    @Override
    public void create(final Topic entity, final Connection connection) throws DAOException {
        try (PreparedStatement ps = connection.prepareStatement(Queries.CREATE_TOPIC, Statement.RETURN_GENERATED_KEYS)) {
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

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(Queries.GET_ALL_TOPICS)) {
            while (resultSet.next()) {
                topics.add(new Topic(resultSet.getInt(1)));
            }
        } catch (SQLException e) {
            throw new DAOException("Error while trying to get topics list from database. " + e.getMessage());
        }

        return topics;
    }

    @Override
    public List<Topic> getAllWithTranslatesByLocale(final Connection connection,
                                                    final String locale,
                                                    final String defaultLocale) throws DAOException {
        List<Topic> topics = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(Queries.GET_TOPICS_WITH_TRANSLATES_BY_LOCALE)) {
            ps.setString(1, locale);
            ps.setString(2, defaultLocale);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Topic topic = new Topic(resultSet.getInt(1));
                topic.addTranslate(
                        new TopicTranslate(topic.getId(), locale, resultSet.getString(2))
                );
                topics.add(topic);
            }
        } catch (SQLException e) {
            throw new DAOException("Error while trying to get topics with translations (by locale) list from database. " + e.getMessage());
        }
        return topics;
    }

    @Override
    public List<Topic> getAllByLocalePagination(final Connection connection, final String locale,
                                                final String defaultLocale, final int skip, final int amount,
                                                final String sorting) throws DAOException {
        List<Topic> topics = new ArrayList<>();
        String query = sorting.equals("DESC") ? Queries.GET_TOPICS_WITH_TRANSLATES_BY_LOCALE_PAGINATION_DESC :
                Queries.GET_TOPICS_WITH_TRANSLATES_BY_LOCALE_PAGINATION_ASC;
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, locale);
            ps.setString(2, defaultLocale);
            ps.setInt(3, amount);
            ps.setInt(4, skip);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Topic topic = new Topic(resultSet.getInt(1));
                topic.addTranslate(
                        new TopicTranslate(topic.getId(), locale, resultSet.getString(2))
                );
                topics.add(topic);
            }
        } catch (SQLException e) {
            throw new DAOException("Error while trying to get topics with translations (by locale) " +
                    "list for pagination from database. " + e.getMessage());
        }
        return topics;
    }

    @Override
    public List<Topic> getAllByNameAndLocalePagination(final Connection connection, final String name,
                                                       final String locale, final String defaultLocale,
                                                       final int skip, final int amount,
                                                       final String sorting) throws DAOException {
        List<Topic> topics = new ArrayList<>();
        String query = sorting.equals("DESC") ? Queries.GET_TOPICS_WITH_TRANSLATES_BY_NAME_AND_LOCALE_PAGINATION_DESC :
                Queries.GET_TOPICS_WITH_TRANSLATES_BY_NAME_AND_LOCALE_PAGINATION_ASC;
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, locale);
            ps.setString(2, defaultLocale);
            ps.setString(3, name);
            ps.setInt(4, amount);
            ps.setInt(5, skip);

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Topic topic = new Topic(resultSet.getInt(1));
                topic.addTranslate(
                        new TopicTranslate(topic.getId(), locale, resultSet.getString(2))
                );
                topics.add(topic);
            }
        } catch (SQLException e) {
            throw new DAOException("Error while trying to search topics by name with translations (by locale) " +
                    "list for pagination from database. " + e.getMessage());
        }
        return topics;
    }


    @Override
    public Topic getEntityById(final Integer id, final Connection connection) throws DAOException {
        Topic topic = null;
        try (PreparedStatement ps = connection.prepareStatement(Queries.GET_TOPIC_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                topic = new Topic(rs.getInt(1));
            }
            rs.close();
        } catch (SQLException e) {
            throw new DAOException();
        }

        return topic;
    }

    @Override
    public Topic getTopicByName(final String s, final Connection connection) throws DAOException {
        Topic topic = null;
        try (PreparedStatement ps = connection.prepareStatement(Queries.GET_TOPIC_BY_NAME)) {
            ps.setString(1, s);
            ResultSet rs = ps.executeQuery();
            if (rs.isBeforeFirst()) {
                rs.next();
                topic = new Topic(rs.getInt(1));
                rs.close();
            }
        } catch (SQLException e) {
            throw new DAOException();
        }

        return topic;
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

    @Override
    public int getTopicsAmount(Connection connection) throws DAOException {
        int count = 0;
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(Queries.GET_TOPICS_COUNT)) {
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new DAOException("Error while trying to get all topics count from database. " + e.getMessage());
        }
        return count;
    }

    @Override
    public int getTopicsAmountSearchMode(final Connection connection, final String searchQuery) throws DAOException {
        int count = 0;
        try (PreparedStatement ps = connection.prepareStatement(Queries.GET_TOPICS_COUNT_SEARCH_MODE)) {
            ps.setString(1, searchQuery);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DAOException("Error while trying to get all topics count (in search mode) from database. " + e.getMessage());
        }
        return count;
    }
}