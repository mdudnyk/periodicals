package com.periodicals.dao.mysql;

import com.periodicals.dao.interfacesForDAO.LocaleDAO;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.entity.LocaleCustom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * A class for interacting with the {@code locale} table in the {@code MySql} database.
 */
public class LocaleDAOMySql implements LocaleDAO {
    private static final Logger LOG = LogManager.getLogger(LocaleDAOMySql.class);

    /**
     * Inserts {@link LocaleCustom} entity into the database.
     *
     * @throws NullPointerException in case of {@link LocaleCustom} or {@link Connection} parameter is {@code null}.
     * @throws DAOException         in case of error at the database side.
     */
    @Override
    public void create(final LocaleCustom entity, final Connection connection) throws DAOException {
        try (PreparedStatement ps = connection.prepareStatement(Queries.CREATE_LOCALE)) {
            fillPreparedStatement(ps, entity);
            ps.execute();
        } catch (SQLException e) {
            LOG.error("Can not add new locale with id="
                    + entity.getShortNameId() + " to the database. " + e.getMessage());
            throw new DAOException("Can not add new locale to the database.");
        }

        LOG.debug("Locale with id=" + entity.getShortNameId() + " successfully added to database");
    }

    /**
     * Returns an {@link ArrayList} with all {@link LocaleCustom} entities presented in the database.
     *
     * @return an {@link ArrayList} with all {@link LocaleCustom} entities presented in the database or an
     * empty {@code ArrayList} when there is no locales entities storing in database.
     * @throws NullPointerException in case of {@link Connection} parameter is {@code null}.
     * @throws DAOException         in case of error on database side.
     */
    @Override
    public List<LocaleCustom> getAll(final Connection connection) throws DAOException {
        List<LocaleCustom> locales = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(Queries.GET_ALL_LOCALES);
             ResultSet resultSet = ps.executeQuery()) {
            while (resultSet.next()) {
                locales.add(fillEntityFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            LOG.error("Did not retrieve locales list from database. " + e.getMessage());
            throw new DAOException("Error while trying to retrieve locales list from database");
        }

        LOG.debug("All locales list successfully retrieved from database. " +
                "List size=" + locales.size());
        return locales;
    }

    /**
     * Returns a {@link LocaleCustom} entity presented in the database by it`s {@code shotNameId}.
     *
     * @param id is {@link String} with unique locale id
     * @return a {@link LocaleCustom} entity presented in the database or {@code null} if there is
     * no locale with this id storing in database.
     * @throws NullPointerException in case of {@link Connection} parameter is {@code null}.
     * @throws DAOException         in case of error on database side.
     */
    @Override
    public LocaleCustom getEntityById(final String id, final Connection connection) throws DAOException {
        LocaleCustom locale = null;

        try (PreparedStatement ps = connection.prepareStatement(Queries.GET_LOCALE_BY_ID)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    locale = fillEntityFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            LOG.error("Did not retrieve locale by it`s id=" + id + " from database. " + e.getMessage());
            throw new DAOException("Error while trying to retrieve locale by it`s id=" + id + ".");
        }

        if (locale != null) {
            LOG.debug("Locale with id=" + id + " successfully retrieved from database");
        } else {
            LOG.debug("Locale with id=" + id + " is not represented in database");
        }
        return locale;
    }

    /**
     * Updates a {@link LocaleCustom} entity presented in the database.
     *
     * @throws NullPointerException in case of {@link Connection} or {@link LocaleCustom} parameter is {@code null}.
     * @throws DAOException         in case of error on database side.
     */
    @Override
    public void update(final LocaleCustom entity, final Connection connection) throws DAOException {
        try (PreparedStatement ps = connection.prepareStatement(Queries.UPDATE_LOCALE)) {
            fillPreparedStatement(ps, entity);
            ps.setString(5, entity.getShortNameId());
            if (ps.executeUpdate() < 1) {
                throw new DAOException("The locale is not represented in the database");
            }
        } catch (SQLException e) {
            LOG.error("Can not update locale with id=" + entity.getShortNameId() + ". " + e.getMessage());
            throw new DAOException("Can not update locale with ID=" + entity.getShortNameId() + " in database");
        }

        LOG.debug("The locale with id=" + entity.getShortNameId() + " was successfully updated");
    }

    /**
     * Deletes a {@link LocaleCustom} entity from the database by it`s id.
     *
     * @throws NullPointerException in case of {@link Connection} parameter is {@code null}.
     * @throws DAOException         in case of error on database side.
     */
    @Override
    public void delete(final String id, final Connection connection) throws DAOException {
        try (PreparedStatement ps = connection.prepareStatement(Queries.DELETE_LOCALE)) {
            ps.setString(1, id);
            if (ps.executeUpdate() < 1) {
                throw new DAOException("The locale is not represented in the database");
            }
        } catch (SQLException e) {
            LOG.error("Can not delete locale with id=" + id + ". " + e.getMessage());
            throw new DAOException("Can not delete locale with ID=" + id + " from database.");
        }

        LOG.debug("The locale with id=" + id + " was successfully deleted");
    }

    /**
     * Helper method to set parameters of the {@link PreparedStatement} object from the
     * presented {@link LocaleCustom} entity.
     *
     * @throws NullPointerException in case of {@link LocaleCustom} parameter is {@code null}.
     * @throws SQLException         in case of error when setting parameters of the {@link PreparedStatement} object.
     */
    private void fillPreparedStatement(PreparedStatement ps, LocaleCustom entity) throws SQLException {
        ps.setString(1, entity.getShortNameId());
        ps.setString(2, entity.getLangNameOriginal());
        ps.setString(3, entity.getCurrency());
        ps.setString(4, entity.getFlagIconURL());
    }

    /**
     * Helper method to create an {@link LocaleCustom} object using data stored in the {@link ResultSet} object.
     *
     * @return {@link LocaleCustom} object retrieved from {@link ResultSet}.
     * @throws NullPointerException in case of {@link ResultSet} parameter is {@code null}.
     * @throws SQLException         in case of error when retrieves the value from the {@link ResultSet} object.
     */
    private LocaleCustom fillEntityFromResultSet(ResultSet resultSet) throws SQLException {
        String shortNameId = resultSet.getString(1);
        String langOriginalName = resultSet.getString(2);
        String currency = resultSet.getString(3);
        String flagIconUrl = resultSet.getString(4);

        return new LocaleCustom(shortNameId, langOriginalName, currency, flagIconUrl);
    }
}