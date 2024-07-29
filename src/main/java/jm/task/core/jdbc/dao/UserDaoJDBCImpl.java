package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (PreparedStatement preparedStatement = Util.getInstance().getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS `users` " +
                "(`id` INT NOT NULL AUTO_INCREMENT, " +
                "`name` VARCHAR(45) NULL, " +
                "`lastName` VARCHAR(45) NULL, " +
                "`age` INT NULL, PRIMARY KEY (`id`));")) {
            preparedStatement.executeUpdate();
            System.out.println("Table created successful");
        } catch (SQLException e) {
            System.err.println("UserDaoJDBCImpl error: method createUsersTable");
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DROP TABLE IF EXISTS users")) {
            preparedStatement.executeUpdate();
            System.out.println("Table drop successful");
        } catch (SQLException e) {
            System.err.println("UserDaoJDBCImpl error: method dropUsersTable");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        Connection connection = Util.getInstance().getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (name, lastName, age) VALUES (?,?,?)")) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.printf("User %s successful added\n", name);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.err.println("Connection rollback failed");
            }
            System.err.println("UserDaoJDBCImpl error: method saveUser");
        }

        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("Connection close failed");
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement = Util.getInstance().getConnection().prepareStatement("DELETE FROM `users` WHERE `id` = ?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("User removed successful");
        } catch (SQLException e) {
            System.err.println("UserDaoJDBCImpl error: method removeUserById");
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Statement statement = Util.getInstance().getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM `users`")) {
            while (resultSet.next()) {
                String userName = resultSet.getString("name");
                String userLastName = resultSet.getString("lastName");
                byte userAge = resultSet.getByte("age");
                User user = new User(userName, userLastName, userAge);
                users.add(user);
            }
        } catch (SQLException e) {
            System.err.println("UserDaoJDBCImpl error: method getAllUsers");
        }
        System.out.println("Get all users successful");
        System.out.println(users);
        return users;
    }

    public void cleanUsersTable() {
        try (PreparedStatement preparedStatement = Util.getInstance().getConnection().prepareStatement("DELETE FROM `users`")) {
            preparedStatement.execute();
            System.out.println("Table clean successful");
        } catch (SQLException e) {
            System.err.println("UserDaoJDBCImpl error: method cleanUsersTable");
        }
    }
}
