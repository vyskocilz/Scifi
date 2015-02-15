package spotrebice;

import base.SpotrebiceEntity;

public class Scaner extends SpotrebiceEntity {

    public Scaner() {
        super("scaner");
    }

    public Scaner(String nazev) {
        this();
        this.nazev = nazev;
    }
}
