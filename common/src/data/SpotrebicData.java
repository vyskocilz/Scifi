package data;

import java.io.Serializable;

public class SpotrebicData implements Serializable {

    public static final String TYP_ZBRAN = "TYP_ZBRAN";
    public static final String TYP_SCANER = "TYP_SCANER";
    public static final String TYP_MOTOR = "TYP_MOTOR";
    public static final String TYP_STIT = "TYP_STIT";

    private String sysName;
    private String urovenText;
    private String typ;
    private boolean plusEnabled;
    private boolean minusEnabled;
    private boolean zapnut;
    private boolean ovladatelnost;
    private boolean viditelnost;
    private String nazev;

    public SpotrebicData() {
    }

    public SpotrebicData(String nazev, String sysName, String urovenText, String typ, boolean plusEnabled, boolean minusEnabled, boolean zapnut, boolean ovladatelnost, boolean viditelnost) {
        this.nazev = nazev;
        this.sysName = sysName;
        this.urovenText = urovenText;
        this.typ = typ;
        this.plusEnabled = plusEnabled;
        this.minusEnabled = minusEnabled;
        this.zapnut = zapnut;
        this.ovladatelnost = ovladatelnost;
        this.viditelnost = viditelnost;
    }

    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
    }

    public String getUrovenText() {
        return urovenText;
    }

    public void setUrovenText(String urovenText) {
        this.urovenText = urovenText;
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
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

    public String getNazev() {
        return nazev;
    }

    public void setNazev(String nazev) {
        this.nazev = nazev;
    }
}
