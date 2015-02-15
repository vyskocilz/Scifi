package spotrebice;

import base.SpotrebiceEntityList;
import data.SpotrebicData;

public class Zbrane extends SpotrebiceEntityList<Zbran> {


    public Zbrane() {
        super(SpotrebicData.TYP_ZBRAN);
    }

    @Override
    public Zbran createElements() {
        Zbran stit = new Zbran("Zbran");
        return stit;  //To change body of implemented methods use File | Settings | File Templates.
    }

}


