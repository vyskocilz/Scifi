package client;

import data.*;
import log.Log;
import main.ClientApplication;

import java.io.ObjectInputStream;


public class ChatWatcher extends Thread {
    private Client client;   // ref to top-level client
    private ObjectInputStream ino;

    public ChatWatcher(Client c, ObjectInputStream ino) {
        client = c;
        this.ino = ino;
    }


    public void run() {
        Object line;
        try {
            while ((line = ino.readObject()) != null) {
                try {
                    doRequest(line);
                } catch (Exception e) {
                    Log.log(e.getMessage());
                }
            }
        } catch (Exception e) {
            Log.log("Server disconected");
            Client.setConnected(false);
        }
    } // end of run()

    protected void doRequest(Object data) {
        if (data instanceof ChatData) {
            Chat.writeText(((ChatData) data).getText());
        }
        if (data instanceof ZdrojData) {
            ZdrojeModel.updatePanel((ZdrojData) data);
        }
        if (data instanceof ZdrojeData) {
            ZdrojeModel.updatePanel((ZdrojeData) data);
        }
        if (data instanceof SpotrebicData) {
            ZdrojeModel.updatePristoreje((SpotrebicData) data);
        }
        if (data instanceof SpotrebiceData) {
            ZdrojeModel.updatePristrojePanel((SpotrebiceData) data);
        }
        if (data instanceof SpotrebaData) {
            ClientApplication.dialog.updateSpotreba((SpotrebaData) data);
        }
        if (data instanceof PalivoData) {
            ClientApplication.dialog.updatePalivo((PalivoData) data);
        }
        if (data.getClass() == String.class) {
//          client.showMsg((String) data + "\n");
            Log.log(data);
        }
    }

}