package dm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User implements Serializable {
    private final String email;
    private String password;
    private final String name;
    private final String phoneNumber;
    private List<Ticket> ticketsForSale = new ArrayList<>();

    public User(String email, String name, String phoneNumber) {
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public User(String email, String password, String name, String phoneNumber, List<Ticket> ticketsForSale) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.ticketsForSale = ticketsForSale;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public List<Ticket> getTicketsForSale() {
        return ticketsForSale;
    }

    public void addTicketForSale(Ticket ticket) {
        this.ticketsForSale.add(ticket);
    }

    public void RemoveTicketForSale(String id) {
        this.ticketsForSale.removeIf(ticket -> Objects.equals(ticket.getId(), id));
    }
}