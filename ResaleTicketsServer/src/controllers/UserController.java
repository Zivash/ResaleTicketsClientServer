package controllers;

import dm.Ticket;
import dm.User;
import service.UserService;
import java.io.IOException;
import java.util.List;

public class UserController {
    private final UserService userService;

    public UserController() {
        userService = new UserService();
    }

    public List<User> getAllUsers() throws IOException {
        return userService.getAllUsers();
    }

    public User getUser(String email) throws IOException {
        return userService.getUser(email);
    }

    public void deleteUser(String email) throws IOException {
        userService.deleteUser(email);
    }

    public void addUser(User user) throws IOException {
        userService.addUser(user);
    }

    public List<Ticket> getUserTickets(String email) throws IOException {
        return userService.getUserTickets(email);
    }

    public void updateUser(User user) throws IOException {
        userService.updateUser(user);
    }
}