package mapa;

import data.ClientType;
import data.mapa.MapData;
import data.mapa.Ship;
import data.mapa.Shoot;
import server.Server;
import server.ServerUtils;

import java.awt.*;

/**
 * Created by pupupaj on 16.2.2015.
 */
public class MapController {

    MapData mapaData;
    private static Ship ship = new Ship(Color.BLUE, "component/spaceship.ico");
    private static Ship otherShip1 = new Ship(Color.GREEN, "component/spaceship.ico");
    private static Ship otherShip2 = new Ship(Color.GREEN, "component/spaceship.ico");
    private static Ship otherShip3 = new Ship(Color.GREEN, "component/spaceship.ico");
    private static Shoot weapon1 = new Shoot(Color.red, 1, 3);
    private static Shoot weapon2 = new Shoot(Color.orange, 2, 1);
    private static Shoot weapon3 = new Shoot(Color.YELLOW, 3, 1);
    private Integer waitForShipClick;
    private Integer waitForShootClick;


    public void setMapaData(MapData mapaData) {
        this.mapaData = mapaData;
        initDefault();
    }

    public void initDefault() {
        resetShoot();
        resetShip();
    }

    public void setOtherShip(int type, int x, int y) {
        Ship ship = null;
        switch (type) {
            case 1:
                ship = otherShip1;
                break;
            case 2:
                ship = otherShip2;
                break;
            case 3:
                ship = otherShip3;
                break;
        }
        mapaData.getShips()[x][y] = ship;
    }

    public void setShipColor(int type, boolean enemy) {
        Ship ship = null;
        switch (type) {
            case 1:
                ship = otherShip1;
                break;
            case 2:
                ship = otherShip2;
                break;
            case 3:
                ship = otherShip3;
                break;
        }
        if (enemy) {
            ship.setColor(Color.ORANGE);
        } else {
            ship.setColor(Color.GREEN);
        }
    }

    public void setWaitForShipClick(int waitForShipClick) {
        this.waitForShipClick = waitForShipClick;
        this.waitForShootClick = null;
    }

    public void setWaitForShootClick(int waitForShootClick) {
        this.waitForShootClick = waitForShootClick;
        this.waitForShipClick = null;
    }

    public void onClick(int x, int y) {
        x = x / MapData.MAPS_CELL_SIZE;
        y = y / MapData.MAPS_CELL_SIZE;
        if (waitForShipClick != null) {
            deleteShip(waitForShipClick);
            setOtherShip(waitForShipClick, x, y);
            waitForShipClick = null;
        } else {
            if (waitForShootClick != null) {
                setShoot(waitForShootClick, x, y);
                waitForShootClick = null;
            }
        }
    }

    public void deleteShip(int type) {
        Ship ship = null;
        switch (type) {
            case 1:
                ship = otherShip1;
                break;
            case 2:
                ship = otherShip2;
                break;
            case 3:
                ship = otherShip3;
                break;
        }
        for (int i = 0; i < MapData.MAPS_SIZE_X; i++) {
            for (int j = 0; j < MapData.MAPS_SIZE_Y; j++) {
                if (mapaData.getShips()[i][j] == ship) {
                    mapaData.getShips()[i][j] = null;
                }
            }
        }
    }

    public void deleteShoot(int type) {
        if (type == 0) {
            for (int i = 0; i < MapData.MAPS_SIZE_X; i++) {
                for (int j = 0; j < MapData.MAPS_SIZE_Y; j++) {
                        mapaData.getShoot()[i][j] = null;
                }
            }
        } else {
            Shoot shoot = null;
            switch (type) {
                case 1:
                    shoot = weapon1;
                    break;
                case 2:
                    shoot = weapon2;
                    break;
                case 3:
                    shoot = weapon3;
                    break;
            }
            for (int i = 0; i < MapData.MAPS_SIZE_X; i++) {
                for (int j = 0; j < MapData.MAPS_SIZE_Y; j++) {
                    if (mapaData.getShoot()[i][j] == shoot) {
                        mapaData.getShoot()[i][j] = null;
                    }
                }
            }
        }
    }


    public void setShoot(int type, int x, int y) {
        Shoot shoot = null;
        switch (type) {
            case 1:
                shoot = weapon1;
                break;
            case 2:
                shoot = weapon2;
                break;
            case 3:
                shoot = weapon3;
                break;
        }
        int count = 0;
        for (int i = 0; i < MapData.MAPS_SIZE_X; i++) {
            for (int j = 0; j < MapData.MAPS_SIZE_Y; j++) {
                if (mapaData.getShoot()[i][j] == shoot) {
                    count++;
                }
            }
        }
        if (count < shoot.getShootPoolSize()) {
            mapaData.getShoot()[x][y] = shoot;
        }
    }

    public void resetShoot() {
        Shoot[][] shoots = new Shoot[MapData.MAPS_SIZE_X][MapData.MAPS_SIZE_X];
        mapaData.setShoot(shoots);
    }

    public void resetShip() {
        Ship[][] ships = new Ship[MapData.MAPS_SIZE_X][MapData.MAPS_SIZE_X];
        ships[MapData.MAPS_SIZE_X / 2][MapData.MAPS_SIZE_Y / 2] = ship;
        mapaData.setShips(ships);
    }

    public void sendMapsData() {
        if (Server.isRunning()) {
            ServerUtils.getClientGroup().broadcast(mapaData.copy(), ClientType.MAPA);
        }
    }
}
