package com.periodicals.dao.mysql;

import com.periodicals.dao.PeriodicalTranslationDAO;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.entity.PeriodicalTranslate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class PeriodicalTranslationDAOMySql implements PeriodicalTranslationDAO {

    public void create(final int periodicalId, final PeriodicalTranslate entity, final Connection connection)
            throws DAOException {
        try(PreparedStatement ps = connection.prepareStatement(Queries.CREATE_PERIODICAL_TRANSLATE)) {
            fillPreparedStatement(ps, periodicalId, entity);
            ps.execute();
        } catch (SQLException e) {
            throw new DAOException("Can not add new periodical translate to the database. " + e.getMessage());
        }
    }

    @Override
    public Map<String, PeriodicalTranslate> getTranslationsByPeriodicalId(
            final int id, final Connection connection) throws DAOException {
        Map<String, PeriodicalTranslate> translations = new HashMap<>();
        try (PreparedStatement ps = connection.prepareStatement(Queries.GET_PERIODICAL_TRANSLATION_BY_PERIODICAL_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PeriodicalTranslate pt = fillEntityFromResultSet(rs);
                translations.put(pt.getLocaleID(), pt);
            }
            rs.close();
        } catch (SQLException e) {
            throw new DAOException("Error while trying to get translation for periodical with id="
                    + id + ". " + e.getMessage());
        }
        return translations;
    }

    @Override
    public PeriodicalTranslate getTranslationByPeriodicalIdAndLocale(final int id, final String locale,
                                                                     final Connection connection) throws DAOException {
        PeriodicalTranslate translation = null;
        try (PreparedStatement ps = connection
                .prepareStatement(Queries.GET_PERIODICAL_TRANSLATION_BY_PERIODICAL_ID_AND_LOCALE)) {
            ps.setInt(1, id);
            ps.setString(2, locale);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                translation = fillEntityFromResultSet(rs);
            }
            rs.close();
        } catch (SQLException e) {
            throw new DAOException("Error while trying to get translation for periodical with id="
                    + id + " and locale=" + locale + ". " + e.getMessage());
        }
        return translation;
    }

    @Override
    public boolean checkIfTranslationExists(final int id,
                                            final String localeId, final Connection connection) throws DAOException {
        boolean exists = false;
        try (PreparedStatement ps = connection.prepareStatement(Queries.PERIODICAL_TRANSLATE_EXISTS)) {
            ps.setInt(1, id);
            ps.setString(2, localeId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (rs.getInt(1) == 1) {
                    exists = true;
                }
            }
            rs.close();
        } catch (SQLException e) {
            throw new DAOException("An error occurred while trying to check if translation for " +
                    "periodical with id=" + id + " and locale=" + localeId + " is exists. " + e.getMessage());
        }
        return exists;
    }

    @Override
    public void update(final int id, final PeriodicalTranslate entity, final Connection connection) throws DAOException {
        try (PreparedStatement ps = connection.prepareStatement(Queries.UPDATE_PERIODICAL_TRANSLATION)) {
            ps.setString(1, entity.getCountry());
            ps.setString(2, entity.getLanguage());
            ps.setString(3, entity.getDescription());
            ps.setInt(4, id);
            ps.setString(5, entity.getLocaleID());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Can not update periodical translation with locale=" + entity.getLocaleID()
                    + " for periodical with id=" + id + ". "+ e.getMessage());
        }
    }


    public void update(final PeriodicalTranslate entity, final Connection connection) throws DAOException {

    }

    private void fillPreparedStatement(PreparedStatement ps, final int periodicalId, PeriodicalTranslate entity)
            throws SQLException {
        ps.setInt(1, periodicalId);
        ps.setString(2, entity.getLocaleID());
        ps.setString(3, entity.getCountry());
        ps.setString(4, entity.getLanguage());
        ps.setString(5, entity.getDescription());
    }

    private PeriodicalTranslate fillEntityFromResultSet(ResultSet resultSet) throws SQLException {
        String localeID = resultSet.getString(2);
        String country = resultSet.getString(3);
        String language = resultSet.getString(4);
        String description = resultSet.getString(5);
        return new PeriodicalTranslate(localeID, country, language, description);
    }
}
