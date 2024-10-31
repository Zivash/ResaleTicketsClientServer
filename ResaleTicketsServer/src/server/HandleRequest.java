package server;

import StringMatchingAlgorithm.KMPImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controllers.TicketController;
import controllers.UserController;
import dm.Ticket;
import dm.User;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.Map;

public class HandleRequest {
    private final Gson gson;
    private final Socket someClient;
    private final TicketController ticketController;
    private final UserController userController;

    public HandleRequest(Socket someClient, TicketController ticketController, UserController userController) {
        this.someClient = someClient;
        this.ticketController = ticketController;
        this.userController = userController;
        gson = new GsonBuilder().create();
    }

    private <T> T getObject(Request request, Type classType) {
        Map<String, Object> body = request.getBody();
        Map.Entry<String, Object> entry = body.entrySet().iterator().next();
        String key = entry.getKey();
        String bodyStr = gson.toJson(body.get(key));

        return gson.fromJson(bodyStr, classType);
    }

    private void handleGetAllResponse(Response response) throws IOException {
        String bodyStr = gson.toJson(response.getBody());
        PrintWriter writer = new PrintWriter(this.someClient.getOutputStream(), true);
        writer.println(bodyStr);
        writer.flush();
        writer.close();
    }

    public void process() throws IOException {
        Ticket ticket;
        User user;
        BufferedReader reader = new BufferedReader(new InputStreamReader(this.someClient.getInputStream()));
        String line = reader.readLine();
        Request request = gson.fromJson(line, Request.class);
        String action = request.getAction();
        switch (action) {
            case "GetAllTickets":
                handleGetAllResponse(new Response(ticketController.getAllTickets()));
                break;
            case "GetTicket":
                ticket = getObject(request, Ticket.class);
                handleGetAllResponse(new Response(ticketController.getTicket(ticket.getId())));
                break;
            case "DeleteTicket":
                ticket = getObject(request, Ticket.class);
                ticketController.deleteTicket(ticket.getId());
                user = userController.getUser(ticket.getEmail());
                user.RemoveTicketForSale(ticket.getId());
                userController.updateUser(user);
                break;
            case "AddTicket":
                ticket = getObject(request, Ticket.class);
                ticketController.addTicket(ticket);
                user = userController.getUser(ticket.getEmail());
                user.addTicketForSale(ticket);
                userController.updateUser(user);
                break;
            case "UpdateTicket":
                ticket = getObject(request, Ticket.class);
                user = userController.getUser(ticket.getEmail());
                user.RemoveTicketForSale(ticket.getId());
                user.addTicketForSale(ticket);
                ticketController.updateTicket(ticket);
                userController.updateUser(user);
                break;
            case "SearchByTicketDescription":
                handleGetAllResponse(new Response(ticketController.searchByDescription(request.getDescription(), new KMPImpl())));
                break;
            case "GetAllUsers":
                handleGetAllResponse(new Response(userController.getAllUsers()));
                break;
            case "GetUser":
                user = getObject(request, User.class);
                handleGetAllResponse(new Response(userController.getUser(user.getEmail())));
                break;
            case "DeleteUser":
                user = getObject(request, User.class);
                ticketController.deleteTicketsAfterUserDeleted(user);
                userController.deleteUser(user.getEmail());
                break;
            case "AddUser":
                userController.addUser(getObject(request, User.class));
                break;
            case "GetAllTicketsUser":
                user = getObject(request, User.class);
                handleGetAllResponse(new Response(userController.getUserTickets(user.getEmail())));
                break;
        }

        reader.close();
    }
}