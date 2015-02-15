package data;

import java.io.Serializable;

public class ZdrojData implements Serializable {
    private String nazev;
    private String sysName;
    private String vykon;
    private long spotrebaPaliva;
    private boolean plusEnabled;
    private boolean minusEnabled;
    private boolean zapnut;
    private boolean ovladatelnost;
    private boolean viditelnost;
    private long maxVykon = 0;

    public ZdrojData() {
    }

    public ZdrojData(boolean viditelnost, String nazev, String sysName, String vykon, boolean plusEnabled, boolean minusEnabled, boolean zapnut, boolean ovladatelnost) {
        this.viditelnost = viditelnost;
        this.nazev = nazev;
        this.sysName = sysName;
        this.vykon = vykon;
        this.plusEnabled = plusEnabled;
        this.minusEnabled = minusEnabled;
        this.zapnut = zapnut;
        this.ovladatelnost = ovladatelnost;
    }

    public String getNazev() {
        return nazev;
    }

    public void setNazev(String nazev) {
        this.nazev = nazev;
    }

    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
    }

    public String getVykon() {
        return vykon;
    }

    public void setVykon(String vykon) {
        this.vykon = vykon;
    }

    public boolean isPlusEnabled() {
        return plusEnabled;
    }

    public void setPlusEnabled(boolean plusEnabled) {
        this.plusEnabled = plusEnabled;
    }

    public boolean isMinusEnabled() {
        return minusEnabled;
    }

    public void setMinusEnabled(boolean minusEnabled) {
        this.minusEnabled = minusEnabled;
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

    public long getSpotrebaPaliva() {
        return spotrebaPaliva;
    }

    public void setSpotrebaPaliva(long spotrebaPaliva) {
        this.spotrebaPaliva = spotrebaPaliva;
    }

    public long getMaxVykon() {
        return maxVykon;
    }

    public void setMaxVykon(long maxVykon) {
        this.maxVykon = maxVykon;
    }
}
