package test;

import dm.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.UserService;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class UserServiceTest {
    static UserService userService;

    @BeforeAll
    public static void createInstance() {
        userService = new UserService();
    }

    @BeforeEach
    public void clearFile() throws FileNotFoundException {
        PrintWriter writer = new PrintWriter("resources\\UserDataSource.txt");
        writer.print("");
        writer.close();
    }

    @Test
    public void addUser() throws IOException {
        boolean addUser = userService.addUser(new User("sb@gmail.com", "Sarel", "0123"));

        Assertions.assertTrue(addUser);
    }

    @Test
    public void deleteUser() throws IOException {
        userService.addUser(new User("sb@gmail.com", "Sarel", "0123"));
        boolean deleteUser = userService.deleteUser("sb@gmail.com");

        Assertions.assertTrue(deleteUser);
    }

    @Test
    public void getUser() throws IOException {
        User userOne = (new User("sb@gmail.com", "Sarel", "0123"));
        userService.addUser(userOne);
        User findUser = userService.getUser("sb@gmail.com");

        Assertions.assertEquals(findUser.getEmail(), userOne.getEmail());
    }

    @Test
    public void getAllUsers() throws IOException {
        boolean equals = true;
        List<User> usersForCompare = new ArrayList<>();
        User userOne = new User("sb@gmail.com", "Sarel", "0123");
        User userTwo = new User("za@gmail.com", "Ziv", "0167");

        usersForCompare.add(userOne);
        usersForCompare.add(userTwo);
        userService.addUser(userOne);
        userService.addUser(userTwo);

        List<User> users = userService.getAllUsers();

        for (int i = 0; i < 2; i++) {
            if (!users.get(i).getEmail().equals(usersForCompare.get(i).getEmail())) {
                equals = false;
                break;
            }
        }

        Assertions.assertTrue(equals);
    }
}