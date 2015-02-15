package spotrebice;

import base.SpotrebiceEntityList;
import data.SpotrebicData;

public class Scanery extends SpotrebiceEntityList<Scaner> {


    public Scanery() {
        super(SpotrebicData.TYP_SCANER);
    }

    @Override
    public Scaner createElements() {
        Scaner stit = new Scaner("Scaner");
        return stit;
    }
}


