package jm.task.core.jdbc;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {

    private static final UserService userService = new UserServiceImpl();

    public static void main(String[] args) {
        userService.createUsersTable();

        userService.saveUser("Alex", "Smith", (byte) 37);
        userService.saveUser("Bill", "Jones", (byte) 74);
        userService.saveUser("Nick", "Davis", (byte) 59);
        userService.saveUser("Steven", "Miller", (byte) 22);

        userService.removeUserById(2);

        userService.getAllUsers();

        userService.cleanUsersTable();

        userService.dropUsersTable();
    }
}
