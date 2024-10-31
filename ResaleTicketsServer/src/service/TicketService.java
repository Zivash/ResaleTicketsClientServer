package service;

import StringMatchingAlgorithm.IAlgoStringMatching;
import dao.TicketDaoImpl;
import dm.Ticket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TicketService {
    private final TicketDaoImpl ticketDao;

    public TicketService() {
        ticketDao = new TicketDaoImpl();
    }

    public List<Ticket> getAllTickets() throws IOException {
        return ticketDao.findAll();
    }

    public Ticket getTicket(String id) throws IOException {
        return ticketDao.find(id);
    }

    public boolean deleteTicket(String id) throws IOException {
        return ticketDao.delete(id);
    }

    public boolean addTicket(Ticket ticket) throws IOException {
        return ticketDao.add(ticket);
    }

    public List<Ticket> searchByDescription(String description, IAlgoStringMatching algoName) throws IOException {
        List<Ticket> allTickets = ticketDao.findAll();
        List<Ticket> ticketsDescriptionMatching = new ArrayList<>();

        for (Ticket ticket : allTickets) {
            if (algoName.Search(ticket.getDescription(), description)) {
                ticketsDescriptionMatching.add(ticket);
            }
        }

        return ticketsDescriptionMatching;
    }

    public void updateTicket(Ticket ticket) throws IOException {
        ticketDao.update(ticket);
    }
}