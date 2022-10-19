package com.periodicals;


import com.periodicals.dao.HikariConnectionPool;
import com.periodicals.dao.exception.DAOException;
import com.periodicals.dao.manager.DAOManagerFactory;
import com.periodicals.dao.manager.PeriodicalDAOManager;
import com.periodicals.dao.manager.TopicDAOManager;
import com.periodicals.entity.*;
import com.periodicals.service.ServiceException;
import com.periodicals.service.TopicService;
import com.periodicals.service.impl.TopicServiceImpl;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.*;
import java.util.Base64;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws DAOException, ServiceException, IOException {
        HikariConnectionPool cp = HikariConnectionPool.getInstance();
        Connection connection = cp.getConnection();

        String sql = "INSERT INTO release_month VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1,7);
            ps.setInt(2,  2022);

            String jsonArray = "[false, true, false, false]";

            ps.setString(3, jsonArray);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String sqlSelect = "SELECT release_calendar FROM release_month";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sqlSelect);
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        cp.close(connection);
        cp.closeDataSource();
    }
}
