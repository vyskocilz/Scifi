package validator;

import org.jdesktop.beansbinding.Validator;

/**
 * Created by Zdenec    .
 * User: Zdenec
 * WWW BASKET TREBIC
 */
public class PositiveValidator extends Validator<Long> {
    @Override
    public Result validate(Long value) {
        if (value == null || (value < 0L)) {
            return new Result(null, "Číslo musí být nezáporný");
        }
        return null;
    }
}
