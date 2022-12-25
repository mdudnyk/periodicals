package com.periodicals.dao.mysql;

import com.periodicals.dao.TopicDAO;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A class for interacting with the {@code topic} table in the {@code MySql} database.
 */
public class TopicDAOMySql implements TopicDAO {
    private static final Logger LOG = LogManager.getLogger(TopicDAOMySql.class);

    /**
     * Inserts {@link Topic} entity into the database.
     * Database returns generated {@code topic ID} and this method sets it to the presented topic entity.
     *
     * @throws NullPointerException in case of {@link Topic} or {@link Connection} parameter is {@code null}.
     * @throws DAOException         in case of error at the database side.
     */
    @Override
    public void create(final Topic entity, final Connection connection) throws DAOException {
        try (PreparedStatement ps = connection
                .prepareStatement(Queries.CREATE_TOPIC, Statement.RETURN_GENERATED_KEYS)) {
            if (ps.executeUpdate() > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        entity.setId(rs.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            LOG.error("Can not add new topic to the database. " + e.getMessage());
            throw new DAOException("Can not add new topic to the database.");
        }

        LOG.debug("New topic was successfully added to the database. " +
                "It`s id was set to " + entity.getId());
    }

    /**
     * Returns an {@link ArrayList} with all {@link Topic} entities (without topic translations)
     * represented in the database.
     *
     * @return an {@link ArrayList} with all {@link Topic} entities represented in the database or an
     * empty {@code ArrayList} when there is no topic entities represented in the database.
     * @throws NullPointerException in case of {@link Connection} parameter is {@code null}.
     * @throws DAOException         in case of error on database side.
     */
    @Override
    public List<Topic> getAll(final Connection connection) throws DAOException {
        List<Topic> topics = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(Queries.GET_ALL_TOPICS)) {
            while (resultSet.next()) {
                topics.add(new Topic(resultSet.getInt(1)));
            }
        } catch (SQLException e) {
            LOG.error("Did not retrieve topics list from database. " + e.getMessage());
            throw new DAOException("Error while trying to get topics list from database.");
        }

        LOG.debug("All topics list was successfully retrieved from database. " +
                "List size=" + topics.size());
        return topics;
    }

    /**
     * Returns an {@link ArrayList} with all {@link Topic} entities with topic translations by specified locale
     * represented in the database.
     *
     * @param locale        preferable {@link LocaleCustom} ID of {@link TopicTranslate} objects to looking for in database.
     * @param defaultLocale alternative {@link LocaleCustom} ID of {@link TopicTranslate} objects to looking for in database.
     * @return an {@link ArrayList} with all {@link Topic} entities represented in the database or an
     * empty {@code ArrayList} when there is no topic entities represented in the database.
     * @throws NullPointerException in case of {@link Connection} parameter is {@code null}.
     * @throws DAOException         in case of error on database side.
     */
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
            LOG.error("Did not retrieve list of topics with translations by locale from database. "
                    + e.getMessage());
            throw new DAOException("Error while trying to retrieve list of topics " +
                    "with translations by locale from database.");
        }

        LOG.debug("List of topics was successfully retrieved from database (with translations by locale). " +
                "List size=" + topics.size());
        return topics;
    }

    /**
     * Returns an {@link ArrayList} with {@link Topic} entities presented in the database
     * according to the method parameters.
     *
     * @param locale        preferable {@link LocaleCustom} ID of {@link TopicTranslate} objects to looking for in database.
     * @param defaultLocale alternative {@link LocaleCustom} ID of {@link TopicTranslate} objects to looking for in database.
     * @param skip          how many rows in topic result table we need to skip.
     *                      {@code OFFSET} command in MySQL.
     * @param amount        maximum amount of {@link Topic} objects that can be in the resulting list.
     *                      {@code LIMIT} command in MySQL.
     * @param order         {@link String} value with a sorting order. There are only 2 parameters that can be set as sorting:
     *                      {@code ASK, DESK} (case-insensitive). If you insert another value or {@code null} it
     *                      will automatically sort in {@code ASK} order.
     * @return an {@link ArrayList} with {@link Topic} entities presented in the database
     * according to the method parameters or an empty {@code ArrayList} when there is no entities.
     * @throws NullPointerException in case of {@link Connection} parameter is {@code null}.
     * @throws DAOException         in case of error on database side.
     */
    @Override
    public List<Topic> getAllByLocalePagination(final Connection connection, final String locale,
                                                final String defaultLocale, final int skip, final int amount,
                                                final String order) throws DAOException {
        List<Topic> topics = new ArrayList<>();

        String query = order != null && order.equals("DESC")
                ? Queries.GET_TOPICS_WITH_TRANSLATES_BY_LOCALE_PAGINATION_DESC
                : Queries.GET_TOPICS_WITH_TRANSLATES_BY_LOCALE_PAGINATION_ASC;

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, locale);
            ps.setString(2, defaultLocale);
            ps.setInt(3, amount);
            ps.setInt(4, skip);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    Topic topic = new Topic(resultSet.getInt(1));
                    topic.addTranslate(
                            new TopicTranslate(topic.getId(), locale, resultSet.getString(2))
                    );
                    topics.add(topic);
                }
            }
        } catch (SQLException e) {
            LOG.error("Did not retrieve topics list from database (pagination). " +
                    "LOCALE=" + locale + ", DEFAULT_LOCALE=" + defaultLocale + ", SKIP=" + skip +
                    ", AMOUNT=" + amount + ", ORDER=" + order + ". " + e.getMessage());
            throw new DAOException("Error while trying to get topics with translations (by locale) " +
                    "list from database (pagination).");
        }

        LOG.debug("List of topics was successfully retrieved from database (pagination). " +
                "List size=" + topics.size());
        return topics;
    }

    /**
     * Returns an {@link ArrayList} with {@link Topic} entities presented in the database
     * according to the method parameters.
     *
     * @param topicName     searched {@link Topic} {@code name} value in {@code topic translate} table from database.
     * @param locale        preferable {@link LocaleCustom} ID of {@link TopicTranslate} objects to looking for in database.
     * @param defaultLocale alternative {@link LocaleCustom} ID of {@link TopicTranslate} objects to looking for in database.
     * @param skip          how many rows in topic result table we need to skip.
     *                      {@code OFFSET} command in MySQL.
     * @param amount        maximum amount of {@link Topic} objects that can be in the resulting list.
     *                      {@code LIMIT} command in MySQL.
     * @param order         {@link String} value with a sorting order. There are only 2 parameters that can be set as sorting:
     *                      {@code ASK, DESK} (case-insensitive). If you insert another value or {@code null} it
     *                      will automatically sort in {@code ASK} order.
     * @return an {@link ArrayList} with {@link Topic} entities presented in the database
     * according to the method parameters or an empty {@code ArrayList} when there is no entities.
     * @throws NullPointerException in case of {@link Connection} parameter is {@code null}.
     * @throws DAOException         in case of error on database side.
     */
    @Override
    public List<Topic> getAllByNameAndLocalePagination(final Connection connection, final String topicName,
                                                       final String locale, final String defaultLocale,
                                                       final int skip, final int amount,
                                                       final String order) throws DAOException {
        List<Topic> topics = new ArrayList<>();

        String query = order != null && order.equals("DESC")
                ? Queries.GET_TOPICS_WITH_TRANSLATES_BY_NAME_AND_LOCALE_PAGINATION_DESC
                : Queries.GET_TOPICS_WITH_TRANSLATES_BY_NAME_AND_LOCALE_PAGINATION_ASC;

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, locale);
            ps.setString(2, defaultLocale);
            ps.setString(3, topicName);
            ps.setInt(4, amount);
            ps.setInt(5, skip);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    Topic topic = new Topic(resultSet.getInt(1));
                    topic.addTranslate(
                            new TopicTranslate(topic.getId(), locale, resultSet.getString(2))
                    );
                    topics.add(topic);
                }
            }
        } catch (SQLException e) {
            LOG.error("Did not retrieve topics list from database (pagination with searched topic name). " +
                    "TOPIC_NAME=" + topicName + ", LOCALE=" + locale + ", DEFAULT_LOCALE=" + defaultLocale +
                    ", SKIP=" + skip + ", AMOUNT=" + amount + ", ORDER=" + order + ". " + e.getMessage());
            throw new DAOException("Error while trying to retrieve list of topics with translations " +
                    "by topic name specified by locale value from database (pagination).");
        }

        LOG.debug("List of topics with name=" + topicName + " was successfully retrieved from " +
                "database (pagination). List size=" + topics.size());
        return topics;
    }

    /**
     * Returns a {@link Topic} entity presented in the database by it`s {@code ID} value (without translations).
     *
     * @param topicId is an unique {@code Integer} ID value of {@link Topic} object.
     * @return a {@link Topic} entity presented in the database or {@code null} if there is
     * no topics with this ID storing in database.
     * @throws NullPointerException in case of {@link Connection} parameter is {@code null}.
     * @throws DAOException         in case of error on database side.
     */
    @Override
    public Topic getEntityById(final Integer topicId, final Connection connection) throws DAOException {
        Topic topic = null;

        try (PreparedStatement ps = connection.prepareStatement(Queries.GET_TOPIC_BY_ID)) {
            ps.setInt(1, topicId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    topic = new Topic(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            LOG.error("Did not retrieve topic by it`s id=" + topicId + " from database. " + e.getMessage());
            throw new DAOException("Error while trying to get topic with id=" + topicId + ".");
        }

        if (topic != null) {
            LOG.debug("Topic with id=" + topicId + " successfully retrieved from database");
        } else {
            LOG.debug("Topic with id=" + topicId + " is not presented in database");
        }
        return topic;
    }

    /**
     * Returns an {@link Topic} entity presented in the database by it`s {@code name}
     * value in {@code topic translate} table (locale-insensitive).
     *
     * @param topicName is a {@link String} value with a topic name.
     * @return an {@link Topic} entity presented in the database or {@code null} if there is
     * no topic with this name storing in database.
     * @throws NullPointerException in case of {@link Connection} parameter is {@code null}.
     * @throws DAOException         in case of error on database side.
     */
    @Override
    public Topic getTopicByName(final String topicName, final Connection connection) throws DAOException {
        Topic topic = null;
        try (PreparedStatement ps = connection.prepareStatement(Queries.GET_TOPIC_BY_NAME)) {
            ps.setString(1, topicName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.isBeforeFirst()) {
                    rs.next();
                    topic = new Topic(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            LOG.error("Did not retrieve topic by it`s name=" + topicName + " from database. " + e.getMessage());
            throw new DAOException("Error while trying to get topic with the name=" + topicName + ".");
        }

        if (topic != null) {
            LOG.debug("Topic with name=" + topicName + " successfully retrieved from database");
        } else {
            LOG.debug("Topic with name=" + topicName + " is not presented in database");
        }
        return topic;
    }

    /**
     * This method does not work due to lack of necessity.
     *
     * @throws UnsupportedOperationException always.
     */
    @Override
    public void update(final Topic entity, final Connection connection) throws DAOException {
        LOG.error("Method does not work due to lack of necessity");
        throw new UnsupportedOperationException();
    }

    /**
     * Deletes a {@link Topic} entity from the database by it`s id.
     *
     * @throws NullPointerException in case of {@link Connection} parameter is {@code null}.
     * @throws DAOException         in case of error on database side.
     */
    @Override
    public void delete(final Integer id, final Connection connection) throws DAOException {
        try (PreparedStatement ps = connection.prepareStatement(Queries.DELETE_TOPIC)) {
            ps.setInt(1, id);
            if (ps.executeUpdate() < 1) {
                throw new DAOException("The topic is not represented in the database");
            }
        } catch (SQLException e) {
            LOG.error("Can not delete the topic with id=" + id + ". " + e.getMessage());
            throw new DAOException("Can not delete topic with id=" + id + " from database.");
        }
    }

    /**
     * Returns a total amount of {@link Topic} entities that are represented in the database.
     *
     * @return a total amount of topics that are represented in the database.
     * @throws NullPointerException in case of {@link Connection} parameter is {@code null}.
     * @throws DAOException         in case of error on database side.
     */
    @Override
    public int getTopicsAmount(Connection connection) throws DAOException {
        int count = 0;

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(Queries.GET_TOPICS_COUNT)) {
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            LOG.error("Did not retrieve topics amount from database. " + e.getMessage());
            throw new DAOException("Error while trying to retrieve amount of topics.");
        }

        LOG.debug("Amount of topics successfully retrieved from database and equals " + count);
        return count;
    }

    /**
     * Returns a total amount of {@link Topic} entities in database that are matching the search query.
     *
     * @param topicName {@link String} value with searched topics title. {@code WHERE} command in MySQL.
     * @return a total amount of topic entities in database that are matching the search query.
     * @throws NullPointerException in case of {@link Connection} parameter is {@code null}.
     * @throws DAOException         in case of error on database side.
     */
    @Override
    public int getTopicsAmountSearchMode(final Connection connection, final String topicName) throws DAOException {
        int count = 0;

        try (PreparedStatement ps = connection.prepareStatement(Queries.GET_TOPICS_COUNT_SEARCH_MODE)) {
            ps.setString(1, topicName);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    count = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            LOG.error("Did not retrieve topics amount by it searched name="
                    + topicName + " from database " + e.getMessage());
            throw new DAOException("Error while trying to retrieve amount of topics " +
                    "searched by name=" + topicName + ".");
        }

        LOG.debug("An amount of topics searched by name=" + topicName +
                " was successfully retrieved from database and equals " + count);
        return count;
    }
}