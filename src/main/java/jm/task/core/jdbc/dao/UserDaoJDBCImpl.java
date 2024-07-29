package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        try (PreparedStatement preparedStatement = Util.getInstance().getConnection().prepareStatement("DROP TABLE IF EXISTS users")) {
            preparedStatement.executeUpdate();
            System.out.println("Table drop successful");
        } catch (SQLException e) {
            System.err.println("UserDaoJDBCImpl error: method dropUsersTable" + e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = Util.getInstance().getConnection().prepareStatement("INSERT INTO users (name, lastName, age) VALUES (?,?,?)")) {
            Util.getInstance().getConnection().setAutoCommit(false);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.printf("User %s successful added\n", name);
            Util.getInstance().getConnection().commit();
        } catch (SQLException e) {
            System.err.println("UserDaoJDBCImpl error: method saveUser");
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
