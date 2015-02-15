package spotrebice;

import base.SpotrebiceEntity;

public class Stit extends SpotrebiceEntity {

    public Stit() {
        super("stit");
    }

    public Stit(String nazev) {
        this();
        this.nazev = nazev;
    }
}
