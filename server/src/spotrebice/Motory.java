package spotrebice;

import base.SpotrebiceEntityList;
import data.SpotrebicData;

public class Motory extends SpotrebiceEntityList<Motor> {


    public Motory() {
        super(SpotrebicData.TYP_MOTOR);
    }

    @Override
    public Motor createElements() {
        Motor stit = new Motor("Motor");
        return stit;  //To change body of implemented methods use File | Settings | File Templates.
    }

}


