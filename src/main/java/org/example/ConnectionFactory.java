package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
    private static final String URL = "jdbc:postgresql://localhost:5432/school_db";
    private static final String USERNAME = "administrator";
    private static final String PASSWORD = "admin";

    public Connection getConnection() {
        Properties properties = new Properties();
        properties.setProperty("user", USERNAME);
        properties.setProperty("password", PASSWORD);

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(URL, properties);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }
}
