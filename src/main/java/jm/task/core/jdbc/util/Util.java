package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/preproject_1.1.4";
    private static final String USER = "admin";
    private static final String PASSWORD = "admin";
//    private Connection connection;
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
    }

    private Connection openConnection() {
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            if (!connection.isClosed() || connection != null) {
                System.out.println("Connection established");
            }
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        return Util.getInstance().openConnection();
    }
}
