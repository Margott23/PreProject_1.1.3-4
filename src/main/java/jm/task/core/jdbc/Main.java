package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;


public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Ivan", "Ivanov", (byte) 20);
        userService.saveUser("Stepan", "Stepanov", (byte) 21);
        userService.saveUser("Pavel", "Pavlov", (byte) 22);
        userService.saveUser("Sara", "Sarovna", (byte) 23);
        userService.removeUserById(1L);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
