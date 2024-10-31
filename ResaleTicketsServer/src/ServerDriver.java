import server.Server;

public class ServerDriver {
    public static void main(String[] args) {
        Server server = new Server(12346);
        new Thread(server).start();
    }
}