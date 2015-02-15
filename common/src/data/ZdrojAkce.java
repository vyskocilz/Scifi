package data;

public class ZdrojAkce implements ClientAkce {

    String sysName;
    Akce akce;

    public ZdrojAkce(Akce akce, String sysName) {
        this.akce = akce;
        this.sysName = sysName;
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

    public ZdrojAkce() {
    }

    public enum Akce {
        PLUS, MINUS, ZAPNOUT, VYPNOUT;
    }

}
