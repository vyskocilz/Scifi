package save;

import java.io.Serializable;

public class SaveUrovne implements Serializable {
    String nazev;
    Integer spotreba;

    public SaveUrovne() {
    }

    public SaveUrovne(Integer spotreba, String nazev) {
        this.spotreba = spotreba;
        this.nazev = nazev;
    }

    public Integer getSpotreba() {
        return spotreba;
    }

    public void setSpotreba(Integer spotreba) {
        this.spotreba = spotreba;
    }

    public String getNazev() {
        return nazev;
    }

    public void setNazev(String nazev) {
        this.nazev = nazev;
    }
}
