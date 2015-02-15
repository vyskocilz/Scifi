package zdroje;

import base.EntityList;
import data.ClientType;
import data.ZdrojAkce;
import data.ZdrojData;
import data.ZdrojeData;
import server.Server;
import server.ServerUtils;

import java.util.ArrayList;
import java.util.List;

public class Zdroje extends EntityList<Zdroj> {

    @Override
    public void init() {
        createNewElement();
    }

    public void doAction(ZdrojAkce akce) {
        for (Zdroj zdroj : getList()) {
            if (zdroj.getSysName().equals(akce.getSysName())) {
                switch (akce.getAkce()) {
                    case PLUS:
                        zdroj.setPozadovanyVykon(zdroj.getPozadovanyVykon() + 1);
                        break;
                    case MINUS:
                        zdroj.setPozadovanyVykon(zdroj.getPozadovanyVykon() - 1);
                        break;
                    case VYPNOUT:
                        zdroj.setZapnut(false);
                        break;
                    case ZAPNOUT:
                        zdroj.setZapnut(true);
                        break;
                }
                break;
            }
        }
    }

    @Override
    public void sendElement(Zdroj element) {
        if (Server.isRunning()) {
            ServerUtils.getClientGroup().broadcast(mapZdrojData(element), ClientType.STROJOVNA);
        }
    }

    @Override
    public void sendElements() {
        if (Server.isRunning()) {
            List<ZdrojData> zdroje = new ArrayList<ZdrojData>();
            for (Zdroj zdroj : getList()) {
                zdroje.add(mapZdrojData(zdroj));
            }
            ServerUtils.getClientGroup().broadcast(new ZdrojeData(zdroje), ClientType.STROJOVNA);
        }
    }

    @Override
    public Zdroj createElements() {
        return new Zdroj("Nový EnergieSource", 0, 0, 100, 0, 1000);
    }

    private static ZdrojData mapZdrojData(Zdroj zdroj) {
        String vykon = zdroj.getAktualniVykon() + "/" + zdroj.getPozadovanyVykon();
        boolean plusEnabled = false;
        boolean minusEnabled = false;
        if (zdroj.isOvladatelnost()) {
            plusEnabled = zdroj.getMaxVykon() > zdroj.getPozadovanyVykon();
            minusEnabled = zdroj.getMinVykon() < zdroj.getPozadovanyVykon();
        }
        ZdrojData zdrojData = new ZdrojData(zdroj.isViditelnost(), zdroj.getNazev(), zdroj.getSysName(), vykon, plusEnabled, minusEnabled, zdroj.isZapnut(), zdroj.isOvladatelnost());
        zdrojData.setSpotrebaPaliva(zdroj.getSpotrebaPalivaCelkem());
        zdrojData.setMaxVykon(zdroj.getMaxVykon());
        return zdrojData;
    }

    public void loadFromSave(List<Zdroj> list) {
        setList(list);
    }

    public Zdroj getMinPriorityElement() {
        Zdroj minPriorityZdroj = null;
        for (Zdroj zdroj : getList()) {
            if (zdroj.isZapnut()) {
                if (minPriorityZdroj == null) {
                    minPriorityZdroj = zdroj;
                } else if (minPriorityZdroj.getPriorita() > zdroj.getPriorita()) {
                    minPriorityZdroj = zdroj;
                }
            }
        }
        return minPriorityZdroj;
    }
}

