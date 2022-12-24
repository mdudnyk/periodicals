package com.periodicals.dao.mysql;

import com.periodicals.dao.PeriodicalTranslationDAO;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.entity.Periodical;
import com.periodicals.entity.PeriodicalTranslate;
import com.periodicals.entity.LocaleCustom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * A class for interacting with the {@code periodical_translate} table in the {@code MySql} database.
 */
public class PeriodicalTranslationDAOMySql implements PeriodicalTranslationDAO {
    private static final Logger LOG = LogManager.getLogger(PeriodicalDAOMySql.class);

    /**
     * Inserts {@link PeriodicalTranslate} entity into the database.
     *
     * @throws NullPointerException in case of {@link Periodical} or {@link Connection} parameter is {@code null}.
     * @throws DAOException         in case of error at the database side or in case
     *                              when entities with {@code periodical_id} or {@code locale_id} are not represented
     *                              in database.
     */
    public void create(final int periodicalId, final PeriodicalTranslate entity,
                       final Connection connection) throws DAOException {
        try (PreparedStatement ps = connection.prepareStatement(Queries.CREATE_PERIODICAL_TRANSLATE)) {
            fillPreparedStatement(ps, periodicalId, entity);
            ps.execute();
        } catch (SQLException e) {
            LOG.error("Can not add new periodical translation with periodical id="
                    + periodicalId + " and locale id=" + entity.getLocaleID() +
                    " to the database. " + e.getMessage());
            throw new DAOException("Can not add new periodical translate to the database.");
        }

        LOG.debug("The periodical translation with periodical id="
                + periodicalId + " and locale id=" + entity.getLocaleID() +
                " was successfully added to the database");
    }

    /**
     * Returns a {@link HashMap} with all {@link PeriodicalTranslate} entities that are presented in the database
     * with specified {@code periodical id}.
     *
     * @param periodicalId is an {@link Periodical} {@code ID} for searched {@code periodical translation}.
     * @return a {@code HashMap} with all {@link PeriodicalTranslate} entities that are presented in the database
     * for specified {@code periodical id}. The {@code key} of this map is a string with {@code locale id} value.
     * Method can return an empty Map in of there is no {@link PeriodicalTranslate} entities in database.
     * @throws NullPointerException in case of {@link Connection} parameter is {@code null}.
     * @throws DAOException         in case of error on database side.
     */
    @Override
    public Map<String, PeriodicalTranslate> getTranslationsByPeriodicalId(
            final int periodicalId, final Connection connection) throws DAOException {
        Map<String, PeriodicalTranslate> translations = new HashMap<>();

        try (PreparedStatement ps = connection
                .prepareStatement(Queries.GET_PERIODICAL_TRANSLATION_BY_PERIODICAL_ID)) {
            ps.setInt(1, periodicalId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PeriodicalTranslate pt = fillEntityFromResultSet(rs);
                    translations.put(pt.getLocaleID(), pt);
                }
            }
        } catch (SQLException e) {
            LOG.error("Did not retrieve periodical translations by periodical id=" + periodicalId
                    + " from database. " + e.getMessage());
            throw new DAOException("Error while trying to retrieve translations for periodical with id="
                    + periodicalId + ".");
        }

        LOG.debug("Map with periodical translations successfully retrieved from database by periodical id="
                + periodicalId + ". Map size=" + translations.size());
        return translations;
    }

    /**
     * Returns a {@link PeriodicalTranslate} entity that are presented in the database with specified {@code periodical id}.
     *
     * @param periodicalId is an {@link Periodical} {@code ID} for searched {@code periodical translation}.
     * @param localeId     is an {@link LocaleCustom} {@code ID} for searched {@code periodical translation}.
     * @return a {@link PeriodicalTranslate} object with specified periodical and locale IDs or {@code null} value if
     * there is no suitable data in database.
     * @throws NullPointerException in case of {@link Connection} parameter is {@code null}.
     * @throws DAOException         in case of error on database side.
     */
    @Override
    public PeriodicalTranslate getTranslationByPeriodicalIdAndLocale(final int periodicalId, final String localeId,
                                                                     final Connection connection) throws DAOException {
        PeriodicalTranslate translation = null;

        try (PreparedStatement ps = connection
                .prepareStatement(Queries.GET_PERIODICAL_TRANSLATION_BY_PERIODICAL_ID_AND_LOCALE)) {
            ps.setInt(1, periodicalId);
            ps.setString(2, localeId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    translation = fillEntityFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            LOG.error("Did not retrieve periodical translation by periodical id=" + periodicalId
                    + " and locale id=" + localeId + " from database. " + e.getMessage());
            throw new DAOException("Error while trying to retrieve translation for periodical with id="
                    + periodicalId + " and locale id=" + localeId + ".");
        }

        if (translation != null) {
            LOG.debug("The periodical translation with locale id=" + localeId + " and periodical id=" + periodicalId
                    + " successfully retrieved from database");
        } else {
            LOG.debug("The periodical translation with locale id=" + localeId + " and periodical id=" + periodicalId
                    + " is not represented in database");
        }
        return translation;
    }

    /**
     * Check if {@link PeriodicalTranslate} entity with specified {@code periodical and locale IDs} is
     * represented in database.
     *
     * @param periodicalId is an {@link Periodical} {@code ID} for searched {@code periodical translation}.
     * @param localeId     is an {@link LocaleCustom} {@code ID} for searched {@code periodical translation}.
     * @return {@code true} when periodical translation with specified {@code periodical and locale IDs} is
     * represented in database. Otherwise, returns {@code false}.
     * @throws NullPointerException in case of {@link Connection} parameter is {@code null}.
     * @throws DAOException         in case of error on database side.
     */
    @Override
    public boolean checkIfTranslationExists(final int periodicalId, final String localeId,
                                            final Connection connection) throws DAOException {
        boolean isRepresented = false;

        try (PreparedStatement ps = connection.prepareStatement(Queries.PERIODICAL_TRANSLATE_EXISTS)) {
            ps.setInt(1, periodicalId);
            ps.setString(2, localeId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    if (rs.getInt(1) == 1) {
                        isRepresented = true;
                    }
                }
            }
        } catch (SQLException e) {
            LOG.error("Did not check if periodical translation with periodical id=" + periodicalId +
                    " and locale id=" + localeId + " is represented in database. " + e.getMessage());
            throw new DAOException("An error occurred while trying to check if periodical translation with" +
                    " periodical id=" + periodicalId + " and locale id=" + localeId + " is represented in database.");
        }

        if (isRepresented) {
            LOG.debug("The periodical translation with periodical id=" + periodicalId +
                    " and locale id=" + localeId + " is represented in database.");
        } else {
            LOG.debug("The periodical translation with periodical id=" + periodicalId +
                    " and locale id=" + localeId + " is not represented in database.");
        }
        return isRepresented;
    }

    /**
     * Updates a {@link PeriodicalTranslate} entity represented in the database.
     *
     * @param periodicalId is an {@link Periodical} {@code ID} for {@code periodical translation} entity in database.
     * @throws NullPointerException in case of {@link Connection} or {@link PeriodicalTranslate} argument is {@code null}.
     * @throws DAOException         in case of error on database side.
     */
    @Override
    public void update(final int periodicalId, final PeriodicalTranslate entity,
                       final Connection connection) throws DAOException {
        try (PreparedStatement ps = connection.prepareStatement(Queries.UPDATE_PERIODICAL_TRANSLATION)) {
            ps.setString(1, entity.getCountry());
            ps.setString(2, entity.getLanguage());
            ps.setString(3, entity.getDescription());
            ps.setInt(4, periodicalId);
            ps.setString(5, entity.getLocaleID());
            if (ps.executeUpdate() < 1) {
                LOG.error("The periodical translation is not represented in the database");
                throw new DAOException("We don`t have such periodical translation.");
            }
        } catch (SQLException e) {
            LOG.error("Can not update periodical translation with locale id=" + entity.getLocaleID()
                    + " and periodical id=" + periodicalId + ". " + e.getMessage());
            throw new DAOException("Can not update periodical translation with locale id=" + entity.getLocaleID()
                    + " and periodical id=" + periodicalId + ".");
        }

        LOG.debug("The periodical translation with locale id=" + entity.getLocaleID() +
                " and periodical id=" + periodicalId + " was successfully updated");
    }

    /**
     * Helper method to set parameters of the {@link PreparedStatement} object from the
     * presented {@link PeriodicalTranslate} entity.
     *
     * @param periodicalId is an {@link Periodical} {@code ID} for {@code periodical translation} entity in database.
     * @throws NullPointerException in case of {@link PeriodicalTranslate} parameter is {@code null}.
     * @throws SQLException         in case of error when setting parameters of the {@link PreparedStatement} object.
     */
    private void fillPreparedStatement(PreparedStatement ps, final int periodicalId, PeriodicalTranslate entity)
            throws SQLException {
        ps.setInt(1, periodicalId);
        ps.setString(2, entity.getLocaleID());
        ps.setString(3, entity.getCountry());
        ps.setString(4, entity.getLanguage());
        ps.setString(5, entity.getDescription());
    }

    /**
     * Helper method to create an {@link PeriodicalTranslate} object using data stored in the {@link ResultSet} object.
     *
     * @return {@link PeriodicalTranslate} object retrieved from {@link ResultSet}.
     * @throws NullPointerException in case of {@link ResultSet} parameter is {@code null}.
     * @throws SQLException         in case of error when retrieves the value from the {@link ResultSet} object.
     */
    private PeriodicalTranslate fillEntityFromResultSet(ResultSet resultSet) throws SQLException {
        String localeID = resultSet.getString(2);
        String country = resultSet.getString(3);
        String language = resultSet.getString(4);
        String description = resultSet.getString(5);

        return new PeriodicalTranslate(localeID, country, language, description);
    }
}