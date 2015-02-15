package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private ClientGroup clientGroup;
    private static boolean running = false;
    ServerSocket serverSock;
    List<ServerHandler> serverHandlers;

    public Server() {
        clientGroup = new ClientGroup();
        serverHandlers = new ArrayList<ServerHandler>();
    }  // end of Server()

    public void start(final Integer port) {
        if (Server.isRunning()) return;
        try {

            serverSock = new ServerSocket(port);
            setRunning(true);
            while (isRunning()) {
                System.out.println("Waiting for a client...");
                Socket clientSock = serverSock.accept();
                ServerHandler serverHandler = new ServerHandler(clientSock, clientGroup, serverHandlers);
                serverHandler.start();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        if (isRunning()) {
            setRunning(false);
            try {
                serverSock.close();
                serverSock = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (ServerHandler serverHandler : serverHandlers) {
                serverHandler.disconnect();
                serverHandler.interrupt();
            }
            serverHandlers.clear();
        }
    }

    public static boolean isRunning() {
        return running;
    }

    public static void setRunning(boolean running) {
        Server.running = running;
    }

    public ClientGroup getClientGroup() {
        return clientGroup;
    }
}