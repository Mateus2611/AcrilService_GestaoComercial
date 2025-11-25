package DAO.JDBC;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class ConexaoDb {

    private static Connection _connection;

    public static Connection openConnection() {
        if (_connection == null) {
            try {
                Properties props = loadProperties();
                String url = props.getProperty("dburl");
                _connection = DriverManager.getConnection(url, props);
            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return _connection;
    }

    private static Properties loadProperties() {
        try (FileInputStream fs = new FileInputStream("database.properties")) {
            Properties props = new Properties();
            props.load(fs);
            return props;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void closeConnection() {
        if (_connection != null) {
            try {
                _connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    public static  void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    public static void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }
}
