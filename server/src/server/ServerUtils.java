package server;

import data.ChatData;
import org.jdesktop.observablecollections.ObservableList;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ServerUtils {

    private static Server server = new Server();

    public static void startServer(final Integer port) {
        if (!Server.isRunning()) {
            Thread runServer = new Thread() {
                public void run() {
                    server.start(port);
                }
            };
            runServer.start();
        }
    }

    public static String getIPAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return "Neznámá";
    }

    public static void stopServer() {
        server.stop();
    }

    public static ObservableList<ServerClient> getServerClients() {
        return server.getClientGroup().getServerClientList();
    }

    public static void sendChatMessage(String msg) {
        server.getClientGroup().broadcast(new ChatData(msg));
    }

    public static ClientGroup getClientGroup() {
        return server.getClientGroup();
    }
}
