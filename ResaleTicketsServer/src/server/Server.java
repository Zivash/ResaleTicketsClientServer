package server;

import controllers.TicketController;
import controllers.UserController;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
    private final int port;

    public Server(int port) {
        this.port = port;
    }

    public void run() {
        ServerSocket server;
        try {
            server = new ServerSocket(port);
            TicketController ticketController = new TicketController();
            UserController userController = new UserController();
            while (true) {
                Socket someClient;
                someClient = server.accept();
                HandleRequest request = new HandleRequest(someClient, ticketController, userController);
                request.process();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}