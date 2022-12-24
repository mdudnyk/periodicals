package com.periodicals.dao.mysql;

import com.periodicals.dao.TopicTranslateDAO;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * A class for interacting with the {@code topic translate} table in the {@code MySql} database.
 */
public class TopicTranslateDAOMySql implements TopicTranslateDAO {
    private static final Logger LOG = LogManager.getLogger(TopicTranslateDAOMySql.class);


    /**
     * This method does not work due to lack of necessity.
     *
     * @throws UnsupportedOperationException always.
     */
    @Override
    public void create(final TopicTranslate entity, final Connection connection) throws DAOException {
        LOG.error("Method does not work due to lack of necessity");
        throw new UnsupportedOperationException();
    }

    /**
     * Inserts {@link TopicTranslate} entity into the database.
     *
     * @throws NullPointerException in case of {@link TopicTranslate} or {@link Connection} parameter is {@code null}.
     * @throws DAOException         in case of error at the database side.
     */
    public void create(final int topicId, final TopicTranslate entity, final Connection connection) throws DAOException {
        try (PreparedStatement ps = connection.prepareStatement(Queries.CREATE_TOPIC_TRANSLATE)) {
            ps.setInt(1, topicId);
            ps.setString(2, entity.getLocaleID());
            ps.setString(3, entity.getName());
            ps.execute();
        } catch (SQLException e) {
            LOG.error("Can not add new topic translation entity with topic id="
                    + topicId + " and locale id=" + entity.getLocaleID() +
                    " to the database. " + e.getMessage());
            throw new DAOException("Can not add new topic translate to the database.");
        }

        LOG.debug("The topic translation with periodical id="
                + topicId + " and locale id=" + entity.getLocaleID() +
                " was successfully added to the database");
    }

    /**
     * Returns a {@link HashMap} with all {@link TopicTranslate} entities that are presented in the database
     * with specified {@code topic id}.
     *
     * @param topicId is an {@link Topic} {@code ID} for searched {@code topic translation}.
     * @return a {@code HashMap} with all {@link TopicTranslate} entities that are presented in the database
     * for specified {@code topic id}. The {@code key} of this map is a string with {@code locale id} value.
     * Method can return an empty Map in of there is no {@link TopicTranslate} entities in database.
     * @throws NullPointerException in case of {@link Connection} parameter is {@code null}.
     * @throws DAOException         in case of error on database side.
     */
    @Override
    public Map<String, TopicTranslate> getAllTranslates(final int topicId, final Connection connection)
            throws DAOException {
        Map<String, TopicTranslate> translations = new HashMap<>();

        try (PreparedStatement ps = connection.prepareStatement(Queries.GET_ALL_TOPIC_TRANSLATES)) {
            ps.setInt(1, topicId);
            TopicTranslate tmp;
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    tmp = fillEntityFromResultSet(resultSet);
                    translations.put(tmp.getLocaleID(), tmp);
                }
            }
        } catch (SQLException e) {
            LOG.error("Did not retrieve topic translations by topic id=" + topicId
                    + " from database. " + e.getMessage());
            throw new DAOException("Error while trying to get all translations of topic with id=" + topicId
                    + " from database.");
        }

        LOG.debug("Map with topic translations successfully retrieved from database by topic id="
                + topicId + ". Map size=" + translations.size());
        return translations;
    }

    /**
     * Returns a {@link TopicTranslate} entity that are presented in the database with specified {@code periodical id}.
     *
     * @param topicId  is an {@link Topic} {@code ID} for searched {@code topic translation}.
     * @param localeId is an {@link LocaleCustom} {@code ID} for searched {@code topic translation}.
     * @return a {@link TopicTranslate} object with specified periodical and locale IDs or {@code null} value if
     * there is no suitable data in database.
     * @throws NullPointerException in case of {@link Connection} parameter is {@code null}.
     * @throws DAOException         in case of error on database side.
     */
    @Override
    public TopicTranslate getTranslateByLocale(final int topicId, final String localeId,
                                               final Connection connection) throws DAOException {
        TopicTranslate translation = null;

        try (PreparedStatement ps = connection.prepareStatement(Queries.GET_TOPIC_TRANSLATE_BY_LOCALE)) {
            ps.setInt(1, topicId);
            ps.setString(2, localeId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    translation = fillEntityFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            LOG.error("Did not retrieve topic translation by topic id=" + topicId
                    + " and locale id=" + localeId + " from database. " + e.getMessage());
            throw new DAOException("Error while trying to get translation for topic with ID=" + topicId + " and locale="
                    + localeId + ".");
        }

        if (translation != null) {
            LOG.debug("The topic translation with locale id=" + localeId + " and topic id=" + topicId
                    + " successfully retrieved from database");
        } else {
            LOG.debug("The topic translation with locale id=" + localeId + " and topic id=" + topicId
                    + " is not represented in database");
        }
        return translation;
    }

    /**
     * Updates a {@link TopicTranslate} entity represented in the database.
     *
     * @throws NullPointerException in case of {@link Connection} or {@link TopicTranslate} argument is {@code null}.
     * @throws DAOException         in case of error on database side.
     */
    @Override
    public void update(final TopicTranslate entity, final Connection connection) throws DAOException {
        try (PreparedStatement ps = connection.prepareStatement(Queries.UPDATE_TOPIC_TRANSLATE)) {
            ps.setString(1, entity.getName());
            ps.setInt(2, entity.getTopicID());
            ps.setString(3, entity.getLocaleID());
            if (ps.executeUpdate() < 1) {
                LOG.error("The topic translation is not represented in the database");
                throw new DAOException("We don`t have such topic translation.");
            }
        } catch (SQLException e) {
            LOG.error("Can not update topic translation with locale id=" + entity.getLocaleID()
                    + " and topic id=" + entity.getTopicID() + "." + e.getMessage());
            throw new DAOException("Can not update topic translation with locale id=" + entity.getLocaleID()
                    + " and topic id=" + entity.getTopicID() + ".");
        }

        LOG.debug("The topic translation with locale id=" + entity.getLocaleID() +
                " and topic id=" + entity.getTopicID() + " was successfully updated");
    }

    /**
     * This method does not work due to lack of necessity.
     *
     * @throws UnsupportedOperationException always.
     */
    @Override
    public void delete(final TopicTranslate entity, final Connection connection) throws DAOException {
        LOG.error("Method does not work due to lack of necessity");
        throw new UnsupportedOperationException();
    }

    /**
     * Check if {@link TopicTranslate} entity with specified {@code topic and locale IDs} is
     * represented in database.
     *
     * @param topicId  is an {@link Topic} {@code ID} for searched {@code topic translation}.
     * @param localeId is an {@link LocaleCustom} {@code ID} for searched {@code topic translation}.
     * @return {@code true} when topic translation with specified {@code topic and locale IDs} is
     * represented in database. Otherwise, returns {@code false}.
     * @throws NullPointerException in case of {@link Connection} parameter is {@code null}.
     * @throws DAOException         in case of error on database side.
     */
    @Override
    public boolean checkIfTranslationExists(final int topicId, final String localeId,
                                            final Connection connection) throws DAOException {
        boolean isRepresented = false;

        try (PreparedStatement ps = connection.prepareStatement(Queries.TOPIC_TRANSLATE_EXISTS)) {
            ps.setInt(1, topicId);
            ps.setString(2, localeId);
            try (ResultSet rs = ps.executeQuery();) {
                while (rs.next()) {
                    if (rs.getInt(1) == 1) {
                        isRepresented = true;
                    }
                }
            }
        } catch (SQLException e) {
            LOG.error("Did not check if topic translation with topic id=" + topicId +
                    " and locale id=" + localeId + " is represented in database. " + e.getMessage());
            throw new DAOException("An error occurred while trying to check if topic translation with" +
                    " topic id=" + topicId + " and locale id=" + localeId + " is represented in database.");
        }

        if (isRepresented) {
            LOG.debug("The topic translation with topic id=" + topicId +
                    " and locale id=" + localeId + " is represented in database.");
        } else {
            LOG.debug("The topic translation with topic id=" + topicId +
                    " and locale id=" + localeId + " is not represented in database.");
        }
        return isRepresented;
    }

    /**
     * Helper method to create an {@link TopicTranslate} object using data stored in the {@link ResultSet} object.
     *
     * @return {@link TopicTranslate} object retrieved from {@link ResultSet}.
     * @throws NullPointerException in case of {@link ResultSet} parameter is {@code null}.
     * @throws SQLException         in case of error when retrieves the value from the {@link ResultSet} object.
     */
    private TopicTranslate fillEntityFromResultSet(ResultSet resultSet) throws SQLException {
        int topicID = resultSet.getInt(1);
        String localeID = resultSet.getString(2);
        String name = resultSet.getString(3);
        return new TopicTranslate(topicID, localeID, name);
    }
}