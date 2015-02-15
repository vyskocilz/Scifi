package data;

import java.io.Serializable;
import java.util.List;

public class SpotrebiceData implements Serializable {
    private String typ;
    List<SpotrebicData> spotrebice;

    public SpotrebiceData() {
    }

    public SpotrebiceData(List<SpotrebicData> spotrebice, String typ) {
        this.spotrebice = spotrebice;
        this.typ = typ;
    }

    public List<SpotrebicData> getSpotrebice() {
        return spotrebice;
    }

    public void setSpotrebice(List<SpotrebicData> spotrebice) {
        this.spotrebice = spotrebice;
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }
}
