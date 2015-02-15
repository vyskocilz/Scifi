package server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

public class ServerClient {
    private String clientIp;
    private Integer port;
    private ObjectOutputStream out;
    private List<String> clientTypes;
    private String clientName;
    private boolean connected = false;


    public ServerClient(String clientIP, int port, ObjectOutputStream out) {
        this.clientIp = clientIP;
        this.port = port;
        this.out = out;
    }


    public boolean matches(String ca, int p)
    // the address and port of a client are used to uniquely identify it
    {
        return clientIp.equals(ca) && (port == p);
    } // end of matches()


    public void sendMessage(Object msg) {
        try {
            out.writeObject(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String toString() {
        return clientIp + " & " + port + " & ";
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(int Integer) {
        this.port = port;
    }

    public List<String> getClientTypes() {
        return clientTypes;
    }

    public void setClientTypes(List<String> clientTypes) {
        this.clientTypes = clientTypes;

    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
}
