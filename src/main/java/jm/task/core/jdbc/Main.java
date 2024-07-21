package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();
        userService.saveUser("Ivan", "Ivanov", (byte) 20);
        userService.saveUser("Petr", "Petrov", (byte) 25);
        userService.saveUser("Mary", "Queen", (byte) 30);
        userService.saveUser("David", "Davidov", (byte) 40);
        userService.getAllUsers();
        userService.removeUserById(3L);
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
