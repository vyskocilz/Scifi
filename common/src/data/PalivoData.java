package data;

import java.io.Serializable;

/**
 * Created by Zdenec    .
 * User: Zdenec
 * WWW BASKET TREBIC
 */
public class PalivoData implements Serializable {
    private long palivoCelkem;
    private long palivoSpotreba;
    private long palivoTime;

    public long getPalivoCelkem() {
        return palivoCelkem;
    }

    public void setPalivoCelkem(long palivoCelkem) {
        this.palivoCelkem = palivoCelkem;
    }

    public long getPalivoSpotreba() {
        return palivoSpotreba;
    }

    public void setPalivoSpotreba(long palivoSpotreba) {
        this.palivoSpotreba = palivoSpotreba;
    }

    public long getPalivoTime() {
        return palivoTime;
    }

    public void setPalivoTime(long palivoTime) {
        this.palivoTime = palivoTime;
    }
}
