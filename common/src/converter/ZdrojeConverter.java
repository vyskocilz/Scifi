package converter;

import data.entity.EnergieSource;
import org.jdesktop.beansbinding.Converter;

/**
 * Created by Zdenec    .
 * User: Zdenec
 * WWW BASKET TREBIC
 */
public class ZdrojeConverter extends Converter<EnergieSource, EnergieSource> {
    @Override
    public EnergieSource convertForward(EnergieSource value) {
        return value;
    }

    @Override
    public EnergieSource convertReverse(EnergieSource value) {
        return value;
    }
}
