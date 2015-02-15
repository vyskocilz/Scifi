package main;

import controler.EnergieSourceServerController;

/**
 * Created by Zdenec    .
 * User: Zdenec
 * WWW BASKET TREBIC
 */
public class ServerController {
    private EnergieSourceServerController energieSourceServerController;

    public ServerController() {
        energieSourceServerController = new EnergieSourceServerController();
    }

    public EnergieSourceServerController getEnergieSourceServerController() {
        return energieSourceServerController;
    }
}
