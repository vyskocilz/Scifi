package server;

import chat.Chat;
import data.ChatData;
import data.ClientAkce;
import data.Login;
import data.Logout;
import main.Application;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.List;

public class ServerHandler extends Thread {
    private Socket clientSock;
    private String cliAddr;
    private int port;
    boolean closeAfterDisconnect = false;

    private ClientGroup cg;    // shared by all threads
    List<ServerHandler> serverHandlers;


    public ServerHandler(Socket s, ClientGroup cg, List<ServerHandler> serverHandlers) {
        this.cg = cg;
        this.serverHandlers = serverHandlers;

        clientSock = s;
        cliAddr = clientSock.getInetAddress().getHostAddress();
        port = clientSock.getPort();
        System.out.println("Client connection from (" + cliAddr + ", " + port + ")");
    }

    public void disconnect() {
        try {
            if (!clientSock.isClosed()) {
                clientSock.close();
                closeAfterDisconnect = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        ServerClient client = null;
        try {
            serverHandlers.add(this);
            // Get I/O streams from the socket
            ObjectInputStream in = new ObjectInputStream(clientSock.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(clientSock.getOutputStream());
            client = cg.addClient(cliAddr, port, out);
            processClient(client, in, out);
            cg.removeClient(client);
            clientSock.close();
            if (!closeAfterDisconnect) {
                serverHandlers.remove(this);
            }
            System.out.printf("Client (%s:%d) connection closed\n", cliAddr, port);
        } catch (Exception e) {
            e.printStackTrace();
        }
        serverHandlers = null;
        clientSock = null;
        cg = null;
    }  // end of run()


    private void processClient(ServerClient client, ObjectInputStream in, ObjectOutputStream out) {
        Object data;
        try {
            client.setConnected(true);
            while (Server.isRunning() && client.isConnected()) {
                if (!((data = in.readObject()) == null)) {
                    if (data instanceof String) {
                        System.out.println("Client (" + cliAddr + ", " + port + "): " + data);
//                        doRequest((String) data, out);
                    } else if (data instanceof Login) {
                        System.out.printf("User '%1$s' has logged at %2$tH:%2$tM:%2$tS\n", ((Login) data).getClientName(), new Date());
                        client.setClientTypes(((Login) data).getClientTypes());
                        client.setClientName(((Login) data).getClientName());
                        cg.fireUpdateChange(client);
                        Application.clientData.onLogin(client);
                    } else if (data instanceof Logout) {
                        System.out.printf("User '%s' has disconnected\n", ((Login) data).getClientName());
                        client.setConnected(false);
                    } else if (data instanceof ChatData) {
                        Chat.writeText(StringUtils.join(client.getClientTypes().get(0)), ((ChatData) data).getText());
                    } else if (data instanceof ClientAkce) {
                        Application.clientData.onAction((ClientAkce) data);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }  // end of processClient()
}
