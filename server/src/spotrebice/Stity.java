package spotrebice;

import base.SpotrebiceEntityList;
import data.SpotrebicData;

public class Stity extends SpotrebiceEntityList<Stit> {


    public Stity() {
        super(SpotrebicData.TYP_STIT);
    }

    @Override
    public Stit createElements() {
        Stit stit = new Stit("Stitek");
        return stit;  //To change body of implemented methods use File | Settings | File Templates.
    }

}


