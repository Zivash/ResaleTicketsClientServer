package test;

import StringMatchingAlgorithm.KMPImpl;
import dm.Ticket;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.TicketService;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class TicketServiceTest {
    static TicketService ticketService;

    @BeforeAll
    public static void createInstance() {
        ticketService = new TicketService();
    }

    @BeforeEach
    public void clearFile() throws FileNotFoundException {
        PrintWriter writer = new PrintWriter("resources\\TicketDataSource.txt");
        writer.print("");
        writer.close();
    }

    @Test
    public void addTicket() throws IOException {
        Ticket ticket = new Ticket(10.3, "df", "foot", "2019-08-12", 144, 2, 3, "sdfdsfsdfds", "q@gmail.com");
        boolean addTicket = ticketService.addTicket(ticket);

        Assertions.assertTrue(addTicket);
    }

    @Test
    public void deleteTicket() throws IOException {
        Ticket ticket = new Ticket(10.3, "df", "foot", "2019-08-12", 144, 2, 3, "sdfdsfsdfds", "q@gmail.com");
        ticketService.addTicket(ticket);
        boolean deleteTicket = ticketService.deleteTicket(ticket.getId());

        Assertions.assertTrue(deleteTicket);
    }

    @Test
    public void getTicket() throws IOException {
        Ticket ticket = new Ticket(10.3, "df", "foot", "2019-08-12", 144, 2, 3, "sdfdsfsdfds", "q@gmail.com");
        ticketService.addTicket(ticket);


        Ticket findTicket = ticketService.getTicket(ticket.getId());

        Assertions.assertEquals(findTicket.getId(), ticket.getId());
    }

    @Test
    public void getAllTickets() throws IOException {
        boolean equals = true;
        List<Ticket> ticketsForCompare = new ArrayList<>();
        Ticket ticketOne = new Ticket(10.3, "df", "foot", "2019-08-12", 144, 2, 3, "sdfdsfsdfds", "q@gmail.com");
        Ticket ticketTwo = new Ticket(10.3, "hap", "foot", "2019-08-12", 144, 2, 3, "pet vs sac", "q@gmail.com");

        ticketsForCompare.add(ticketOne);
        ticketsForCompare.add(ticketTwo);
        ticketService.addTicket(ticketOne);
        ticketService.addTicket(ticketTwo);

        List<Ticket> tickets = ticketService.getAllTickets();

        for (int i = 0; i < 2; i++) {
            if (!tickets.get(i).getId().equals(ticketsForCompare.get(i).getId())) {
                equals = false;
                break;
            }
        }

        Assertions.assertTrue(equals);
    }

    @Test
    public void getTicketByDescription() throws IOException {
        boolean equal = false;
        ticketService.addTicket(new Ticket(10.3, "df", "foot", "2019-08-12", 144, 2, 3, "sdfetfsdfds", "q@gmail.com"));
        ticketService.addTicket(new Ticket(10.3, "hap", "foot", "2019-08-12", 144, 2, 3, "pet vs sac", "q@gmail.com"));
        List<Ticket> tickets = ticketService.searchByDescription("et", new KMPImpl());

        if (tickets.size() == 2) {
            equal = true;
        }

        Assertions.assertTrue(equal);
    }
}