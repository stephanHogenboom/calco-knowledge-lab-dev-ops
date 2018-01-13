package acces;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
  * Base class for all DataBase connections and general queries
  */
public abstract class GeneralDAO {

    private static Connection connection;

    protected Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                try {
                    connection = DriverManager.getConnection(getConnectionString());
                } catch (SQLException e) {
                    System.out.printf("Error getting connection: %s", e.getMessage() + "\n");
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static String getStringSaveNullSave(String string) {
        return (string == null) ? "" : string;
    }

    protected Integer incrementAndGetId(String tableName) {
        String sql = String.format("SELECT max(id) FROM %s", tableName);
        try {
            Statement stmnt = connection.createStatement();
            ResultSet rs = stmnt.executeQuery(sql);
            if (rs.next()) {
                System.out.println(rs.getInt(1));
                return rs.getInt(1) + 1;
            } else return 1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<String> getDataBases() {
        List<String> dataBases = new ArrayList<>();
        try {
            DatabaseMetaData md = connection.getMetaData();
            ResultSet rs = md.getTables(null, null, "%", null);
            while (rs.next()) {
                dataBases.add(rs.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataBases;
    }


    /**
     * This method returns the whole resultSet of a custom created query
     * Warning this method is easily compromised by sql-injections
     * Does not support ALTER, DROP, TRUNCATE, CREATE etc..
     * @param query String
     * @return Resultset
     */
    public ResultSet executeQueryPlain(String query) throws Exception {
        if (query.startsWith("SELECT") || query.startsWith("select")) {
            try {
                return connection.createStatement().executeQuery(query);
            } catch (SQLException e) {
                e.printStackTrace();
                throw (e);
            }
        }
        if (query.startsWith("update") ||
            query.startsWith("UPDATE") ||
            query.startsWith("INSERT") ||
            query.startsWith("insert")) {
            try {
                connection.createStatement().execute(query);
            } catch (SQLException e) {
                e.printStackTrace();
                throw (e);
            }
        }
        return null;
    }

    private static String getConnectionString() {
        String currentDir = System.getProperty("user.dir");
        return String.format("jdbc:sqlite:%s%s", currentDir, "/knowledgeLab.db");
    }
}
