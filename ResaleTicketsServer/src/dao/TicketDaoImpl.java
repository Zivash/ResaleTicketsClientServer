package dao;

import dm.Ticket;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TicketDaoImpl implements IDao<Ticket> {
    private final String filePath;

    public TicketDaoImpl() {
        filePath = "resources\\TicketDataSource.txt";
    }

    private HashMap<String, Ticket> readToHashMap() throws IOException {
        HashMap<String, Ticket> ticketsHashMap = new HashMap<>();

        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            String[] parts = line.split(",");
            String id = parts[0];
            double price = Double.parseDouble(parts[1]);
            String showName = parts[2];
            String category = parts[3];
            String date = parts[4];
            int section = Integer.parseInt(parts[5]);
            int row = Integer.parseInt(parts[6]);
            int seat = Integer.parseInt(parts[7]);
            String description = parts[8];
            String email = parts[9];
            ticketsHashMap.put(id, new Ticket(id, price, showName, category, date, section, row, seat, description, email));
        }

        bufferedReader.close();

        return ticketsHashMap;
    }

    private void writeToFile(HashMap<String, Ticket> ticketsHashMap) throws IOException {
        PrintWriter printWriter;
        printWriter = new PrintWriter(new FileWriter(filePath));

        if (ticketsHashMap.isEmpty()) {
            printWriter.print("");
        }

        for (Ticket ticketInHashMap : ticketsHashMap.values()) {
            printWriter.println(ticketInHashMap.getId() + "," + ticketInHashMap.getPrice() + "," + ticketInHashMap.getShowName() + "," + ticketInHashMap.getCategory() + "," +
                    ticketInHashMap.getDate() + "," + ticketInHashMap.getSection() + "," + ticketInHashMap.getRow() + "," +
                    ticketInHashMap.getSeat() + "," + ticketInHashMap.getDescription() + "," + ticketInHashMap.getEmail());
        }

        printWriter.flush();
        printWriter.close();
    }

    public boolean add(Ticket ticket) throws IOException {

        HashMap<String, Ticket> ticketsHashMap = readToHashMap();
        ticketsHashMap.put(ticket.getId(), ticket);
        writeToFile(ticketsHashMap);

        return true;
    }

    public boolean delete(String id) throws IOException {
        HashMap<String, Ticket> ticketsHashMap = readToHashMap();
        if (!ticketsHashMap.containsKey(id)) {
            return false;
        }

        ticketsHashMap.remove(id);
        writeToFile(ticketsHashMap);

        return true;
    }

    public Ticket find(String id) throws IOException {
        HashMap<String, Ticket> ticketsHashMap = readToHashMap();

        return ticketsHashMap.get(id);
    }

    public List<Ticket> findAll() throws IOException {
        HashMap<String, Ticket> ticketsHashMap = readToHashMap();

        return new ArrayList<>(ticketsHashMap.values());
    }

    public void update(Ticket ticket) throws IOException {
        add(ticket);
    }
}