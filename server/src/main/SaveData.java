package main;

import base.SpotrebiceEntity;
import save.SaveSpotreba;
import save.SaveSpotrebic;
import save.SaveUrovne;
import spotrebice.Motor;
import spotrebice.Scaner;
import spotrebice.Stit;
import spotrebice.Zbran;
import urovne.Uroven;
import zdroje.Zdroj;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SaveData implements Serializable {


    List<SaveSpotrebic> zbrane;
    List<Zdroj> zdroje;
    List<SaveSpotrebic> motory;
    List<SaveSpotrebic> scenary;
    List<SaveSpotrebic> stity;
    SaveSpotreba spotreba;

    public SaveData() {
    }

    public void fillZbrane(List<Zbran> list) {
        zbrane = fillList(list);
    }

    public void fillMotory(List<Motor> list) {
        motory = fillList(list);
    }

    public void fillScenery(List<Scaner> list) {
        scenary = fillList(list);
    }

    public void fillStity(List<Stit> list) {
        stity = fillList(list);
    }

    public void fillZdroje(List<Zdroj> list) {
        zdroje = new ArrayList<Zdroj>();
        for (Zdroj element : list) {
            zdroje.add(element);
        }
    }

    public void fillSpotreba(Spotreba spotreba) {
        this.spotreba = new SaveSpotreba(spotreba.getZbraneVykon(), spotreba.getMotoryVykon(), spotreba.getScaneryVykon(), spotreba.getStityVykon(),
                spotreba.isEnableMotory(), spotreba.isEnableStity(), spotreba.isEnableZbrane(), spotreba.isEnableScanery());
    }

    protected List<SaveSpotrebic> fillList(List<? extends SpotrebiceEntity> listSpotrebic) {
        List<SaveSpotrebic> listSave = new ArrayList<SaveSpotrebic>();
        for (SpotrebiceEntity element : listSpotrebic) {
            SaveSpotrebic saveData = new SaveSpotrebic();
            saveData.setNazev(element.getNazev());
            saveData.setPriorita(element.getPriorita());
            saveData.setOvladatelnost(element.isOvladatelnost());
            saveData.setViditelnost(element.isViditelnost());
            saveData.setZapnut(element.isZapnut());
            ArrayList<Uroven> urovne = new ArrayList<Uroven>(element.getUrovne().getList().subList(0, element.getUrovne().getList().size()));
            element.getUrovne().getList();
            for (Uroven uroven : urovne) {
                saveData.getUrovne().add(new SaveUrovne(uroven.getSpotreba(), uroven.getNazev()));
            }
            listSave.add(saveData);
        }
        return listSave;
    }


    public List<SaveSpotrebic> getZbrane() {
        return zbrane;
    }

    public void setZbrane(List<SaveSpotrebic> zbrane) {
        this.zbrane = zbrane;
    }

    public List<Zdroj> getZdroje() {
        return zdroje;
    }

    public void setZdroje(List<Zdroj> zdroje) {
        this.zdroje = zdroje;
    }

    public List<SaveSpotrebic> getMotory() {
        return motory;
    }

    public void setMotory(List<SaveSpotrebic> motory) {
        this.motory = motory;
    }

    public List<SaveSpotrebic> getScenary() {
        return scenary;
    }

    public void setScenary(List<SaveSpotrebic> scenary) {
        this.scenary = scenary;
    }

    public List<SaveSpotrebic> getStity() {
        return stity;
    }

    public void setStity(List<SaveSpotrebic> stity) {
        this.stity = stity;
    }

    public SaveSpotreba getSpotreba() {
        return spotreba;
    }

    public void setSpotreba(SaveSpotreba spotreba) {
        this.spotreba = spotreba;
    }
}
