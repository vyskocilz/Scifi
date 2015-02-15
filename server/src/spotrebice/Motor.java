package spotrebice;

import base.SpotrebiceEntity;

public class Motor extends SpotrebiceEntity {

    public Motor() {
        super("motory");
    }

    public Motor(String nazev) {
        this();
        this.nazev = nazev;
    }
}
