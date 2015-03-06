package ini;

import data.ClientType;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ClientProperty {

    private static ClientProperty INSTANCE;
    private Properties properties = new Properties();
    public static final String PROPERTY_FILE = "client.properties";
    public static final String SERVER_ADDR = "server_ip";
    public static final String SERVER_PC_NAME = "server_pc_name";
    public static final String SERVER_PORT = "server_port";
    public static final String CLIENT_MUSTEK = "client_mustek";
    public static final String CLIENT_NAME = "client_name";
    public static final String CLIENT_STROJOVNA = "client_strojovna";
    public static final String CLIENT_MAPA = "client_mapa";
    public static final String CLIENT_LOG = "client_log";
    public static final String CLIENT_CLOSE_PASSWORD = "client_close_password";

    private ClientProperty() {
        initPropertyFile();
        load();
        checkProperties();
        save();
    }

    private void load() {
        try {
            properties.load(new FileInputStream(PROPERTY_FILE));
        } catch (IOException e) {
        }
    }

    private void checkProperties() {
        String msg = "";
        if (properties.getProperty(SERVER_ADDR) == null) {
            msg += "Ip adresa není vyplněna";
        }
        if (properties.getProperty(SERVER_PORT) == null) {
            msg += (msg.length() > 0) ? "\n" : "" + "Port není vyplněn";
        }
        if (msg.length() > 0) {
            JOptionPane.showMessageDialog(null,
                    "Špatné hodnoty", msg,
                    JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    private void save() {
        try {
            properties.store(new FileOutputStream(PROPERTY_FILE), null);
        } catch (IOException e) {
        }
    }


    private void initPropertyFile() {
        if (!new File(PROPERTY_FILE).exists()) {
            properties.put(SERVER_ADDR, DefaultProperty.APPLICATION_IP);
            properties.put(SERVER_PORT, DefaultProperty.APPLICATION_PORT);
            properties.put(CLIENT_MUSTEK, DefaultProperty.APPLICATION_CLIENT_TYPE);
            properties.put(CLIENT_STROJOVNA, DefaultProperty.APPLICATION_CLIENT_TYPE);
            properties.put(CLIENT_CLOSE_PASSWORD, DefaultProperty.APPLICATION_CLIENT_TYPE);
            properties.put(CLIENT_NAME, DefaultProperty.APPLICATION_CLIENT_NAME);
        }
    }

    public static String getProperty(String name) {
        if (CLIENT_LOG.equals(name)) {
            return DefaultProperty.APPLICATION_DEBUG;
        }
        if (INSTANCE == null) {
            INSTANCE = new ClientProperty();
        }
        return INSTANCE.properties.getProperty(name);
    }

    public static boolean getPropertyAsBoolean(String name) {
        return Boolean.valueOf(getProperty(name));
    }

    public static int getPropertyAsInteger(String name) {
        return Integer.valueOf(getProperty(name));
    }

    public static List<String> getClientType() {
        List<String> clientTypes = new ArrayList<String>();
        if (getPropertyAsBoolean(CLIENT_MUSTEK)) {
            clientTypes.add(ClientType.MUSTEK);
        }
        if (getPropertyAsBoolean(CLIENT_STROJOVNA)) {
            clientTypes.add(ClientType.STROJOVNA);
        }
        if (getPropertyAsBoolean(CLIENT_MAPA)) {
            clientTypes.add(ClientType.MAPA);
        }
        if (clientTypes.isEmpty()) {
            clientTypes.add(ClientType.NEZNAMA);
        }
        return clientTypes;
    }

    public static String getClosePassword() {
        return getProperty(CLIENT_CLOSE_PASSWORD);
    }

    public static String getClientName() {
        return getProperty(CLIENT_NAME);
    }
}

