package DAO.JDBC;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbConnection {

    private static Connection _connection;

    public static Connection getConnection() {
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
}
