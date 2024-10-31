package com.example.client;

import com.example.client.model.Request;
import com.example.client.model.Ticket;
import com.example.client.model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerAccess {
    private static final Gson gson = new Gson();

    public static List<Ticket> getTickets(String searchString) throws IOException {
        Socket myServer = new Socket("localhost", 12346);
        Request request;
        if (searchString.isEmpty()) {
            request = new Request("GetAllTickets");
        } else {
            request = new Request("SearchByTicketDescription", searchString);
        }

        PrintWriter writer = new PrintWriter(myServer.getOutputStream(), true);
        String x = gson.toJson(request);
        writer.println(x);
        writer.flush();

        BufferedReader reader = new BufferedReader(new InputStreamReader(myServer.getInputStream()));
        String line = reader.readLine();
        Type ticketListType = new TypeToken<List<Ticket>>() {
        }.getType();

        return gson.fromJson(line, ticketListType);
    }

    public static <T> void deleteFromServer(T object) {
        Request request;
        try {
            Map<String, Object> reqMap = new HashMap<>();
            Class<?> objectClass = object.getClass();

            if (objectClass == User.class) {
                User user = (User) object;
                reqMap.put(user.getEmail(), user);
                request = new Request("DeleteUser", reqMap);
            } else {
                Ticket ticket = (Ticket) object;
                reqMap.put(ticket.getId(), ticket);
                request = new Request("DeleteTicket", reqMap);
            }

            saveToServer(request);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void update(Ticket ticket) {
        Request request;
        Map<String, Object> reqMap = new HashMap<>();

        reqMap.put(ticket.getId(), ticket);
        request = new Request("UpdateTicket", reqMap);

        saveToServer(request);
    }

    private static void saveToServer(Request request) {
        try {
            Socket myServer = new Socket("localhost", 12346);
            PrintWriter writer = new PrintWriter(myServer.getOutputStream(), true);
            String x = gson.toJson(request);
            writer.println(x);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Ticket> getUserTickets(User user) throws IOException {
        Socket myServer = new Socket("localhost", 12346);
        Map<String, Object> reqMap = new HashMap<>();
        reqMap.put(user.getEmail(), user);
        Request request;
        request = new Request("GetAllTicketsUser", reqMap);

        PrintWriter writer = new PrintWriter(myServer.getOutputStream(), true);
        String x = gson.toJson(request);
        writer.println(x);
        writer.flush();

        BufferedReader reader = new BufferedReader(new InputStreamReader(myServer.getInputStream()));
        String line = reader.readLine();
        Type ticketListType = new TypeToken<List<Ticket>>() {
        }.getType();

        return gson.fromJson(line, ticketListType);
    }

    public static List<User> getUser(String email) throws IOException {
        Socket myServer = new Socket("localhost", 12346);
        Request request;

        if (!email.isEmpty()) {
            Map<String, Object> reqMap = new HashMap<>();
            reqMap.put(email, new User(email));
            request = new Request("GetUser", reqMap);
        } else {
            request = new Request("GetAllUsers");
        }

        PrintWriter writer = new PrintWriter(myServer.getOutputStream(), true);
        String x = gson.toJson(request);
        writer.println(x);
        writer.flush();

        BufferedReader reader = new BufferedReader(new InputStreamReader(myServer.getInputStream()));
        String line = reader.readLine();
        Type userType = new TypeToken<List<User>>() {
        }.getType();

        return gson.fromJson(line, userType);
    }

    public static <T> void addToServer(T object) {
        Request request;
        try {
            Map<String, Object> reqMap = new HashMap<>();
            Class<?> objectClass = object.getClass();

            if (objectClass == User.class) {
                User user = (User) object;
                reqMap.put(user.getEmail(), user);
                request = new Request("AddUser", reqMap);
            } else {
                Ticket ticket = (Ticket) object;
                reqMap.put(ticket.getId(), ticket);
                request = new Request("AddTicket", reqMap);
            }

            saveToServer(request);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}