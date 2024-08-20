package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private final String URL = "jdbc:mysql://localhost:3306/preproject_1.1.4";
    private final String USER = "admin";
    private final String PASSWORD = "admin";
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

    private Util() {
    }

    private Connection openConnection() {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connection established: " + !connection.isClosed());
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        return Util.getInstance().openConnection();
    }
}
