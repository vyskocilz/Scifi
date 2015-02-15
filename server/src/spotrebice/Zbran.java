package spotrebice;

import base.SpotrebiceEntity;

public class Zbran extends SpotrebiceEntity {

    public Zbran() {
        super("zbran");
    }

    public Zbran(String nazev) {
        this();
        this.nazev = nazev;
    }
}
