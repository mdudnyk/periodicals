package com.periodicals.dao.mysql;

import com.periodicals.dao.TopicTranslateDAO;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.entity.TopicTranslate;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class TopicTranslateDAOMySql implements TopicTranslateDAO {

    @Override
    public void create(final TopicTranslate entity, final Connection connection) throws DAOException {
        throw new UnsupportedOperationException();
    }

    public void create(final int parentObjID, final TopicTranslate entity, final Connection connection) throws DAOException {
        try(PreparedStatement ps = connection.prepareStatement(Queries.CREATE_TOPIC_TRANSLATE)) {
            ps.setInt(1, parentObjID);
            ps.setString(2, entity.getLocaleID());
            ps.setString(3, entity.getName());
            ps.execute();
        } catch (SQLException e) {
            throw new DAOException("Can not add new topic translate to the database. " + e.getMessage());
        }
    }

    @Override
    public Map<String, TopicTranslate> getAllTranslates(final int parentObjID,
                                                        final Connection connection) throws DAOException {
        Map<String, TopicTranslate> translates = new HashMap<>();

        try (PreparedStatement ps = connection.prepareStatement(Queries.GET_ALL_TOPIC_TRANSLATES)) {
            ps.setInt(1, parentObjID);
            ResultSet resultSet = ps.executeQuery();
            TopicTranslate tmp;
            while (resultSet.next()) {
                tmp = fillEntityFromResultSet(resultSet);
                translates.put(tmp.getLocaleID(), tmp);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DAOException("Error while trying to get all translates of topic with ID='" + parentObjID
                    + "' list from database. " + e.getMessage());
        }
        return translates;
    }

    @Override
    public TopicTranslate getTranslateByLocale(final int parentObjID, final String localeID,
                                               final Connection connection) throws DAOException {
        TopicTranslate topicTranslate = null;

        try (PreparedStatement ps = connection.prepareStatement(Queries.GET_TOPIC_TRANSLATE_BY_LOCALE)) {
            ps.setInt(1, parentObjID);
            ps.setString(2, localeID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                topicTranslate = fillEntityFromResultSet(rs);
            }
            rs.close();
        } catch (SQLException e) {
            throw new DAOException("Error while trying to get translation for topic with ID=" + parentObjID + " and locale="
                    + localeID + ". " + e.getMessage());
        }
        return topicTranslate;
    }


    @Override
    public void update(final TopicTranslate entity, final Connection connection) throws DAOException {
        try (PreparedStatement ps = connection.prepareStatement(Queries.UPDATE_TOPIC_TRANSLATE)) {
            ps.setString(1, entity.getName());
            ps.setInt(2, entity.getTopicID());
            ps.setString(3, entity.getLocaleID());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Can not update topic translate with topicID=" + entity.getTopicID()
                    + " and localeID='" + entity.getLocaleID() + "'. " + e.getMessage());
        }
    }

    @Override
    public void delete(final TopicTranslate entity, final Connection connection) throws DAOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean checkIfTranslationExists(final int topicID,
                                            final String localeID, final Connection connection) throws DAOException {
        boolean exists = false;
        try (PreparedStatement ps = connection.prepareStatement(Queries.TOPIC_TRANSLATE_EXISTS)){
            ps.setInt(1, topicID);
            ps.setString(2, localeID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (rs.getInt(1) == 1) {
                    exists = true;
                }
            }
            rs.close();
        } catch (SQLException e) {
            throw new DAOException("An error occurred while trying to check if translation for topic with ID=" + topicID + " and locale="
                    + localeID + " is exists. " + e.getMessage());
        }
        return exists;
    }

    private TopicTranslate fillEntityFromResultSet(ResultSet resultSet) throws SQLException {
        int topicID = resultSet.getInt(1);
        String localeID = resultSet.getString(2);
        String name = resultSet.getString(3);
        return new TopicTranslate(topicID, localeID, name);
    }
}