package controllers;

import StringMatchingAlgorithm.IAlgoStringMatching;
import dm.Ticket;
import dm.User;
import service.TicketService;
import java.io.IOException;
import java.util.List;

public class TicketController {

    private final TicketService ticketService;

    public TicketController() {
        ticketService = new TicketService();
    }

    public List<Ticket> getAllTickets() throws IOException {
        return ticketService.getAllTickets();
    }

    public void deleteTicketsAfterUserDeleted(User user) throws IOException {
        List<Ticket> allTickets = getAllTickets();
        for (Ticket ticket : allTickets)
        {
            if (ticket.getEmail().equals(user.getEmail()))
            {
                ticketService.deleteTicket(ticket.getId());
            }
        }
    }

    public Ticket getTicket(String id) throws IOException {
        return ticketService.getTicket(id);
    }

    public void deleteTicket(String id) throws IOException {
        ticketService.deleteTicket(id);
    }

    public void addTicket(Ticket ticket) throws IOException {
        ticketService.addTicket(ticket);
    }

    public List<Ticket> searchByDescription(String description, IAlgoStringMatching algoName) throws IOException {
        return ticketService.searchByDescription(description, algoName);
    }

    public void updateTicket(Ticket ticket) throws IOException {
        ticketService.updateTicket(ticket);
    }
}