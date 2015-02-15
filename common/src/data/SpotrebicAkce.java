package data;

public class SpotrebicAkce implements ClientAkce {

    String sysName;
    String typ;
    Akce akce;

    public SpotrebicAkce(String sysName, String typ, Akce akce) {
        this.sysName = sysName;
        this.typ = typ;
        this.akce = akce;
    }

    public Akce getAkce() {
        return akce;
    }

    public void setAkce(Akce akce) {
        this.akce = akce;
    }

    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public SpotrebicAkce() {
    }

    public enum Akce {
        PLUS, MINUS, ZAPNOUT, VYPNOUT;
    }

}
