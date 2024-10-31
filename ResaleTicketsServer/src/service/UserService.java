package service;

import dao.UserDaoImpl;
import dm.Ticket;
import dm.User;
import java.io.IOException;
import java.util.List;

public class UserService {
    private final UserDaoImpl userDao;

    public UserService() {
        userDao = new UserDaoImpl();
    }

    public List<User> getAllUsers() throws IOException {
        return userDao.findAll();
    }

    public User getUser(String email) throws IOException {
        return userDao.find(email);
    }

    public boolean deleteUser(String email) throws IOException {
        return userDao.delete(email);
    }

    public boolean addUser(User user) throws IOException {
        return userDao.add(user);
    }

    public List<Ticket> getUserTickets(String email) throws IOException {
        return userDao.getUserTickets(email);
    }

    public void updateUser(User user) throws IOException {
        userDao.update(user);
    }
}