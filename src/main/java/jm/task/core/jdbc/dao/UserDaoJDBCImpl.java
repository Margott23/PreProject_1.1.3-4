package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import javax.transaction.Transaction;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class UserDaoJDBCImpl implements UserDao {

    private final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS `users` " +
            "(`id` INT NOT NULL AUTO_INCREMENT, " +
            "`name` VARCHAR(45) NULL, " +
            "`lastName` VARCHAR(45) NULL, " +
            "`age` INT NULL, PRIMARY KEY (`id`));";
    private final String DROP_TABLE = "DROP TABLE IF EXISTS users";
    private final String SAVE_USER = "INSERT INTO users (name, lastName, age) VALUES (?,?,?)";
    private final String REMOVE_USER_BY_ID = "DELETE FROM `users` WHERE `id` = ?";
    private final String GET_ALL_USERS = "SELECT * FROM `users`";
    private final String CLEAN_USERS_TABLE = "DELETE FROM `users`";
    private final String CHECK_TABLE = "SHOW TABLES LIKE 'users'";

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (PreparedStatement preparedStatement = Util.getInstance().getConnection().prepareStatement(CREATE_TABLE)) {
            preparedStatement.executeUpdate(CREATE_TABLE);
            System.out.println("Table created successfull");
        } catch (SQLException e) {
            System.out.println("UserDaoJDBCImpl error: method createUsersTable");
        }
    }

    public void dropUsersTable() {
        try (PreparedStatement preparedStatement = Util.getInstance().getConnection().prepareStatement(DROP_TABLE)) {
            preparedStatement.executeUpdate();
            System.out.println("Table drop successfull");
        } catch (SQLException e) {
            System.out.println("UserDaoJDBCImpl error: method dropUsersTable" + e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = Util.getInstance().getConnection().prepareStatement(SAVE_USER)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.printf("User %s successfull added\n", name);
        } catch (SQLException e) {
            System.out.println("UserDaoJDBCImpl error: method saveUser");
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement = Util.getInstance().getConnection().prepareStatement(REMOVE_USER_BY_ID)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("User removed successfull");
        } catch (SQLException e) {
            System.out.println("UserDaoJDBCImpl error: method removeUserById");
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Statement statement = Util.getInstance().getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(GET_ALL_USERS)) {
            while (resultSet.next()) {
                String userName = resultSet.getString("name");
                String userLastName = resultSet.getString("lastName");
                byte userAge = resultSet.getByte("age");
                User user = new User(userName, userLastName, userAge);
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println("UserDaoJDBCImpl error: method getAllUsers");
        }
        System.out.println("Get all users successfull");
        System.out.println(users);
        return users;
    }

    public void cleanUsersTable() {
        try (PreparedStatement preparedStatement = Util.getInstance().getConnection().prepareStatement(CLEAN_USERS_TABLE)) {
            preparedStatement.execute();
            System.out.println("Table clean successfull");
        } catch (SQLException e) {
            System.out.println("UserDaoJDBCImpl error: method cleanUsersTable");
        }
    }
}
