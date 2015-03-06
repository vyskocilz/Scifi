package data;

import client.ClientUtils;
import data.mapa.MapData;
import data.mapa.MapaStrelba;
import data.mapa.MapaStrelbaDelete;

/**
 * Created by pupupaj on 16.2.2015.
 */
public class MapController {

    MapData mapaData = new MapData();
    private Integer waitForShootClick;

    public void setMapaData(MapData mapaData) {
        this.mapaData.setShips(mapaData.getShips());
        this.mapaData.setShoot(mapaData.getShoot());
    }


    public MapData getMapaData() {
        return mapaData;
    }

    public void setWaitForShootClick(int waitForShootClick) {
        this.waitForShootClick = waitForShootClick;
    }

    public void onClick(int x, int y) {
        x = x / MapData.MAPS_CELL_SIZE;
        y = y / MapData.MAPS_CELL_SIZE;
        if (waitForShootClick != null) {
            ClientUtils.sendData(new MapaStrelba(y, x, waitForShootClick));
            waitForShootClick = null;
        }
    }

    public void delete(int type) {
        ClientUtils.sendData(new MapaStrelbaDelete(type));
    }
}
