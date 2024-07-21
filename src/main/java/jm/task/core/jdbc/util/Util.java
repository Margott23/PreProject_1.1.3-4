package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private final String URL = "jdbc:mysql://localhost:3306/preproject_1.1.4";
    private final String USER = "admin";
    private final String PASSWORD = "admin";
    private Connection connection;
    private static volatile Util instance;

    public static Util getInstance() {
        if (instance == null) {
            synchronized (Util.class) {
                if (instance == null) {
                    instance = new Util();
                }
            }
        }
        return instance;
    }

    public Util() { // for JDBC
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            if (!connection.isClosed() || connection != null) {
                System.out.println("Connection established");
            }
        } catch (SQLException e) {
            System.err.printf("Connection failed: %s\n", e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
