package client;

import data.Login;
import data.Logout;
import ini.ClientProperty;
import log.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class Client {
    private static int PORT = 1234;     // server details
    private static String HOST = "localhost";
    private Socket sock;
    private ObjectOutputStream out;
    private static boolean connected = false;
    private ChatWatcher chatWatcher;
    private Timer timer;

    public Client() {
        PORT = Integer.parseInt(ClientProperty.getProperty(ClientProperty.SERVER_PORT));
        HOST = ClientProperty.getProperty(ClientProperty.SERVER_ADDR);
    }

    public void start() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!isConnected()) {
                    connect();
                    login();
                }
            }
        }, 5000, 1000);
        connect();
        login();
    }

    public void stop() {
        if (timer != null) {
            timer.cancel();
            if (isConnected()) {
                sendData(new Logout());
                disconnect();
            }

        }
    }

    protected void connect() {
        if (isConnected()) return;
        try {
            sock = new Socket(HOST, PORT);
            setConnected(true);
            Log.log("Spojeni se server navazano");
            out = new ObjectOutputStream(sock.getOutputStream());
            ObjectInputStream ino = new ObjectInputStream(sock.getInputStream());
            chatWatcher = new ChatWatcher(this, ino);
            chatWatcher.start();
        } catch (Exception e) {
            Log.error(e);
            disconnect();
        }
    }

    protected void login() {
        Login login = new Login();
        login.setClientTypes(ClientProperty.getClientType());
        login.setClientName(ClientProperty.getClientName());
        sendData(login);
    }

    protected void disconnect() {
        if (isConnected()) {
            try {
                if (sock != null) {
                    sock.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            setConnected(false);
            Log.log("Spojeni se server ztraceno");
        }
    }

    public static boolean isConnected() {
        return connected;
    }

    public static void setConnected(boolean connected) {
        Client.connected = connected;
    }


    public void sendData(Object data) {
        if (isConnected()) {
            try {
                if (sock.isConnected()) {
                    out.writeObject(data);
                }
            } catch (IOException e) {
                Log.error(e);
                setConnected(false);
            }
        }
    }


}
