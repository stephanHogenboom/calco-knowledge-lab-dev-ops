package com.stephanHogenboom.cache;

import com.stephanHogenboom.acces.GeneralDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class ConfigDAO extends GeneralDAO {
    private Connection connection = getConnection();

    public HashMap<String, String> getConfigCache() {
        HashMap<String, String> configCache = new HashMap<>();
        String sql = "SELECT * FROM reg_item;";
        try(Statement stmnt = connection.createStatement();
            ResultSet rs = stmnt.executeQuery(sql)){
            while (rs.next()) {
                configCache.put(rs.getString(1), rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return configCache;
    }
}
