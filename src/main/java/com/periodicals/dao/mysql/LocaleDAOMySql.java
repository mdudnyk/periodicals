package com.periodicals.dao.mysql;

import com.periodicals.dao.LocaleDAO;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.entity.LocaleCustom;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LocaleDAOMySql implements LocaleDAO {

    @Override
    public void create(final LocaleCustom entity, final Connection connection) throws DAOException {
        try(PreparedStatement ps = connection.prepareStatement(Queries.CREATE_LOCALE, Statement.RETURN_GENERATED_KEYS)) {
            fillPreparedStatement(ps, entity);
            ps.execute();
        } catch (SQLException e) {
            throw new DAOException("Can not add new locale to the database. " + e.getMessage());
        }
    }

    @Override
    public List<LocaleCustom> getAll(final Connection connection) throws DAOException {
        List<LocaleCustom> locales = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(Queries.GET_ALL_LOCALES);
             ResultSet resultSet = ps.executeQuery()) {
            while (resultSet.next()) {
                locales.add(fillEntityFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException("Error while trying to get locales list from database. " + e.getMessage());
        }

        return locales;
    }

    @Override
    public LocaleCustom getEntityById(final String id, final Connection connection) throws DAOException {
        LocaleCustom locale = null;

        try (PreparedStatement ps = connection.prepareStatement(Queries.GET_LOCALE_BY_ID)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.isBeforeFirst()) {
                rs.next();
                locale = fillEntityFromResultSet(rs);
                rs.close();
            }
        } catch (SQLException e) {
            throw new DAOException("Error while trying to get locale by it`s ID=" + id + ". " + e.getMessage());
        }

        return locale;
    }

    @Override
    public void update(final LocaleCustom entity, final Connection connection) throws DAOException {
        try (PreparedStatement ps = connection.prepareStatement(Queries.UPDATE_LOCALE)) {
            fillPreparedStatement(ps, entity);
            ps.setString(4, entity.getShortNameId());
            if (ps.executeUpdate() < 1) {
                throw new DAOException("We don`t have such locale. ");
            }
        } catch (SQLException e) {
            throw new DAOException("Can not update locale with ID=" + entity.getShortNameId() + " in database. " + e.getMessage());
        }
    }

    @Override
    public void delete(final String id, final Connection connection) throws DAOException {
        try (PreparedStatement ps = connection.prepareStatement(Queries.DELETE_LOCALE)) {
            ps.setString(1, id);
            if (ps.executeUpdate() < 1) {
                throw new DAOException("We don`t have such locale. ");
            }
        } catch (SQLException e) {
            throw new DAOException("Can not delete locale with ID=" + id + " from database. " + e.getMessage());
        }
    }

    private void fillPreparedStatement(PreparedStatement ps, LocaleCustom entity) throws SQLException {
        ps.setString(1, entity.getShortNameId());
        ps.setString(2, entity.getLangNameOriginal());
        ps.setString(3, entity.getCurrency());
        ps.setString(4, entity.getFlagIconURL());
    }

    private LocaleCustom fillEntityFromResultSet(ResultSet resultSet) throws SQLException {
        String shortNameId = resultSet.getString(1);
        String langOriginalName = resultSet.getString(2);
        String currency = resultSet.getString(3);
        String flagIconUrl = resultSet.getString(4);

        return new LocaleCustom(shortNameId, langOriginalName, currency, flagIconUrl);
    }
}
