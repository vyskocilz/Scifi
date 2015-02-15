package base;

import data.ClientType;
import data.SpotrebicAkce;
import data.SpotrebicData;
import data.SpotrebiceData;
import save.SaveSpotrebic;
import save.SaveUrovne;
import server.Server;
import server.ServerUtils;
import urovne.Uroven;

import java.util.ArrayList;
import java.util.List;

public abstract class SpotrebiceEntityList<T extends SpotrebiceEntity> extends EntityList<T> {

    private String type;

    protected SpotrebiceEntityList(String type) {
        this.type = type;
    }

    public void doAction(SpotrebicAkce akce) {
        for (T spotrebic : getList()) {
            if (spotrebic.getSysName().equals(akce.getSysName())) {
                switch (akce.getAkce()) {
                    case PLUS:
                        spotrebic.zvysUrove();
                        break;
                    case MINUS:
                        spotrebic.snizUroven();
                        break;
                    case VYPNOUT:
                        spotrebic.setZapnut(false);
                        break;
                    case ZAPNOUT:
                        spotrebic.setZapnut(true);
                        break;
                }
                break;
            }
        }
    }


    @Override
    public void init() {
        createNewElement();
    }

    @Override
    public void sendElement(T element) {
        if (Server.isRunning()) {
            ServerUtils.getClientGroup().broadcast(mapSpotrebicData(element), ClientType.MUSTEK);
        }
    }

    @Override
    public void sendElements() {
        if (Server.isRunning()) {
            List<SpotrebicData> spotrebice = new ArrayList<SpotrebicData>();
            for (SpotrebiceEntity zdroj : getList()) {
                spotrebice.add(mapSpotrebicData(zdroj));
            }
            ServerUtils.getClientGroup().broadcast(new SpotrebiceData(spotrebice, type), ClientType.MUSTEK);
        }
    }

    @Override
    public abstract T createElements();

    public void snizUroven(Integer index) {
        if (index < 0 || index >= getList().size()) {
            return;
        }
        getList().get(index).snizUroven();
    }

    public void zvysUrove(Integer index) {
        if (index < 0 || index >= getList().size()) {
            return;
        }
        getList().get(index).zvysUrove();
    }

    protected SpotrebicData mapSpotrebicData(SpotrebiceEntity spotrebic) {
        boolean plusEnabled = false;
        boolean minusEnabled = false;
        if (spotrebic.isOvladatelnost() && spotrebic.isZapnut()) {
            plusEnabled = !spotrebic.isLastUroven();
            minusEnabled = !spotrebic.isPrvniUroven();
        }
        return new SpotrebicData(
                spotrebic.getNazev(),
                spotrebic.getSysName(),
                spotrebic.getAktualniUroven().getNazev(),
                type,
                plusEnabled,
                minusEnabled,
                spotrebic.isZapnut(),
                spotrebic.isOvladatelnost(),
                spotrebic.isViditelnost());
    }


    public void loadFromSave(List<SaveSpotrebic> spotrebicList) {
        getList().clear();
        for (SaveSpotrebic saveSpotrebic : spotrebicList) {
            T loadItem = createElements();
            loadItem.getUrovne().getList().clear();
            for (SaveUrovne urovne : saveSpotrebic.getUrovne()) {
                loadItem.getUrovne().addElement(new Uroven(urovne.getNazev(), urovne.getSpotreba()));
            }
            loadItem.setNazev(saveSpotrebic.getNazev());
            loadItem.setPriorita(saveSpotrebic.getPriorita());
            loadItem.setAktualniUroven(loadItem.getUrovne().getList().get(0));
            loadItem.setOvladatelnost(saveSpotrebic.isOvladatelnost());
            loadItem.setZapnut(saveSpotrebic.isZapnut());
            loadItem.setViditelnost(saveSpotrebic.isViditelnost());
            addElement(loadItem);
        }
    }
}
