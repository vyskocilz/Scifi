package data;

public class SpotrebaAkce implements ClientAkce {
    public enum Akce {
        PLUS, MINUS
    }

    public enum Typ {
        ZBRANE, STITY, MOTORY, SCANERY
    }

    private Akce akce;
    private Typ typ;

    public SpotrebaAkce(Akce akce, Typ typ) {
        this.akce = akce;
        this.typ = typ;
    }

    public SpotrebaAkce() {
    }

    public Akce getAkce() {
        return akce;
    }

    public void setAkce(Akce akce) {
        this.akce = akce;
    }

    public Typ getTyp() {
        return typ;
    }

    public void setTyp(Typ typ) {
        this.typ = typ;
    }
}
