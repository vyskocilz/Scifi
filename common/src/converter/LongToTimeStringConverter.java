package converter;

import org.jdesktop.beansbinding.Converter;

import java.util.TimeZone;

/**
 * Created by Zdenec    .
 * User: Zdenec
 * WWW BASKET TREBIC
 */
public class LongToTimeStringConverter extends Converter<Long, String> {
    static long minuta = 60;
    static long hodina = 60 * minuta;
    static long den = 24 * hodina;
    static long rok = 365 * den;

    @Override
    public String convertForward(Long l) {
        if (l == null || l <= 0L) {
            return "Žádná spotřeba";
        }

        if (l < den) {
            return String.format("%1$TH:%1$TM:%1$TS", getTimZoneLong(l));
        }
        if (l < rok) {
            long pocetDnu = l / den;
            if (pocetDnu <= 1) {
                return String.format("%1$d", pocetDnu) + " den " + String.format("%2$TH:%2$TM:%2$TS", pocetDnu, getTimZoneLong(l));
            } else if (pocetDnu < 5) {
                return String.format("%1$d", pocetDnu) + " dny " + String.format("%2$TH:%2$TM:%2$TS", pocetDnu, getTimZoneLong(l));
            } else {
                return String.format("%1$d", pocetDnu) + " dní " + String.format("%2$TH:%2$TM:%2$TS", pocetDnu, getTimZoneLong(l));
            }
        }
        return "pres rok";
    }

    private long getTimZoneLong(long l) {
        return l * 1000 - (TimeZone.getDefault().getRawOffset());
    }

    @Override
    public Long convertReverse(String s) {
        return 0L;
    }
}
