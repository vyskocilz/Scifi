package data.mapa;

import java.io.Serializable;

/**
 * Created by pupupaj on 4.3.2015.
 */
public class MapData implements Serializable {
    public static int MAPS_SIZE_X = 31;
    public static int MAPS_SIZE_Y = 31;
    public static int MAPS_CELL_SIZE = 16;
    Ship[][] ships;
    Shoot[][] shoot;


    public MapData() {
    }

    public MapData(Ship[][] ships, Shoot[][] shoot) {
        this.ships = ships;
        this.shoot = shoot;
    }

    public Ship[][] getShips() {
        return ships;
    }

    public void setShips(Ship[][] ships) {
        this.ships = ships;
    }

    public Shoot[][] getShoot() {
        return shoot;
    }

    public void setShoot(Shoot[][] shoot) {
        this.shoot = shoot;
    }

    public MapData copy() {
        Shoot[][] shoots = new Shoot[MapData.MAPS_SIZE_X][MapData.MAPS_SIZE_X];
        for (int i = 0; i < MapData.MAPS_SIZE_X; i++) {
            for (int j = 0; j < MapData.MAPS_SIZE_Y; j++) {
                if (this.shoot[i][j] != null) {
                    shoots[i][j] = new Shoot(this.shoot[i][j]);
                }
            }
        }
        Ship[][] ships = new Ship[MapData.MAPS_SIZE_X][MapData.MAPS_SIZE_X];
        for (int i = 0; i < MapData.MAPS_SIZE_X; i++) {
            for (int j = 0; j < MapData.MAPS_SIZE_Y; j++) {
                if (this.ships[i][j] != null) {
                    ships[i][j] = new Ship(this.ships[i][j]);
                }
            }
        }
        return new MapData(ships, shoots);
    }
}
