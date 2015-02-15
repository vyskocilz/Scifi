package urovne;

import base.Entity;

public class Uroven extends Entity {

    private Integer spotreba = 0;

    public Uroven() {
        super("Uroven");
    }

    public Uroven(String nazev, Integer spotreba) {
        this();
        this.nazev = nazev;
        this.spotreba = spotreba;
    }

    public Integer getSpotreba() {
        return spotreba;
    }

    public void setSpotreba(Integer spotreba) {
        this.spotreba = notNull(spotreba);
        onDataChange();
    }

}
