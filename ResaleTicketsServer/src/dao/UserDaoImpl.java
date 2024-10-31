package dao;

import dm.Ticket;
import dm.User;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserDaoImpl implements IDao<User> {
    private final String filePath;

    public UserDaoImpl() {
        filePath = "resources\\UserDataSource.txt";
    }

    private HashMap<String, User> readToHashMap() throws IOException {
        HashMap<String, User> usersHashMap = new HashMap<>();

        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            List<Ticket> tickets = new ArrayList<>();

            String[] parts = line.split(",");
            String email = parts[0];
            String password = parts[1];
            String name = parts[2];
            String phoneNumber = parts[3];

            for (int i = 4; i < parts.length; i++) {
                String id = parts[i];
                double price = Double.parseDouble(parts[++i]);
                String showName = parts[++i];
                String category = parts[++i];
                String date = parts[++i];
                int section = Integer.parseInt(parts[++i]);
                int row = Integer.parseInt(parts[++i]);
                int seat = Integer.parseInt(parts[++i]);
                String description = parts[++i];
                String newEmail = parts[++i];
                tickets.add(new Ticket(id, price, showName, category, date, section, row, seat, description, newEmail));
            }
            usersHashMap.put(email, new User(email, password, name, phoneNumber, tickets));
        }

        bufferedReader.close();

        return usersHashMap;
    }

    private void writeToFile(HashMap<String, User> usersHashMap) throws IOException {
        PrintWriter printWriter = new PrintWriter(new FileWriter(filePath));

        for (User userInHashMap : usersHashMap.values()) {
            if(!userInHashMap.getTicketsForSale().isEmpty()) {
                printWriter.println(userInHashMap.getEmail() + "," + userInHashMap.getPassword() + "," + userInHashMap.getName() + "," + userInHashMap.getPhoneNumber() + writeListToFile(userInHashMap.getTicketsForSale()));
            }
            else {
                printWriter.println(userInHashMap.getEmail() + "," + userInHashMap.getPassword() + "," + userInHashMap.getName() + "," + userInHashMap.getPhoneNumber());
            }
        }

        printWriter.flush();
        printWriter.close();
    }

    private String writeListToFile(List<Ticket> tickets)
    {
        StringBuilder list = new StringBuilder(",");
        for(Ticket ticket : tickets)
        {
            list.append(ticket.toString());
            list.append(",");
        }

        list.deleteCharAt(list.length() - 1);

        return list.toString();
    }

    public boolean add(User user) throws IOException {

        HashMap<String, User> usersHashMap = readToHashMap();
        usersHashMap.put(user.getEmail(), user);
        writeToFile(usersHashMap);

        return true;
    }

    public boolean delete(String email) throws IOException {
        HashMap<String, User> usersHashMap = readToHashMap();
        if (!usersHashMap.containsKey(email)) {
            return false;
        }

        usersHashMap.remove(email);
        writeToFile(usersHashMap);

        return true;
    }

    public User find(String email) throws IOException {
        HashMap<String, User> usersHashMap = readToHashMap();

        return usersHashMap.get(email);
    }

    public List<User> findAll() throws IOException {
        HashMap<String, User> usersHashMap = readToHashMap();

        return new ArrayList<>(usersHashMap.values());
    }

    public List<Ticket> getUserTickets(String email) throws IOException {
        HashMap<String, User> usersHashMap = readToHashMap();

        return usersHashMap.get(email).getTicketsForSale();
    }

    public void update(User user) throws IOException {
        add(user);
    }
}