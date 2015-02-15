package save;

import java.io.Serializable;
import java.util.ArrayList;

public class SaveSpotrebic implements Serializable {

    String nazev;
    Integer priorita;
    ArrayList<SaveUrovne> urovne = new ArrayList<SaveUrovne>();

    boolean zapnut;
    boolean ovladatelnost;
    boolean viditelnost;

    public SaveSpotrebic() {
    }

    public SaveSpotrebic(ArrayList<SaveUrovne> urovne, String nazev, Integer priorita) {
        this.urovne = urovne;
        this.nazev = nazev;
        this.priorita = priorita;
    }

    public String getNazev() {
        return nazev;
    }

    public void setNazev(String nazev) {
        this.nazev = nazev;
    }

    public Integer getPriorita() {
        return priorita;
    }

    public void setPriorita(Integer priorita) {
        this.priorita = priorita;
    }

    public ArrayList<SaveUrovne> getUrovne() {
        return urovne;
    }

    public void setUrovne(ArrayList<SaveUrovne> urovne) {
        this.urovne = urovne;
    }

    public boolean isZapnut() {
        return zapnut;
    }

    public void setZapnut(boolean zapnut) {
        this.zapnut = zapnut;
    }

    public boolean isOvladatelnost() {
        return ovladatelnost;
    }

    public void setOvladatelnost(boolean ovladatelnost) {
        this.ovladatelnost = ovladatelnost;
    }

    public boolean isViditelnost() {
        return viditelnost;
    }

    public void setViditelnost(boolean viditelnost) {
        this.viditelnost = viditelnost;
    }
}
