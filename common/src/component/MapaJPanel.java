package component;

import data.mapa.MapData;
import data.mapa.Ship;
import data.mapa.Shoot;

import javax.swing.*;
import java.awt.*;

/**
 * Created by pupupaj on 22.2.2015.
 */
public class MapaJPanel extends JPanel {
    int start_x = 0;
    int start_y = 0;
    MapData mapData;

    public MapaJPanel() {
    }

    public void setMapData(MapData mapData ) {
        this.mapData = mapData;
//        setPreferredSize(getDimension());
//        setMinimumSize(getDimension());
    }

    public Dimension getDimension() {
        return new Dimension(MapData.MAPS_SIZE_X * MapData.MAPS_CELL_SIZE, MapData.MAPS_SIZE_Y * MapData.MAPS_CELL_SIZE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (mapData == null) {
            return;
        }
        if (mapData.getShips() == null) {
            return;
        }

        int x;
        int y;

        // pain ship
        for (int i = 0; i < MapData.MAPS_SIZE_X; i++) {
            for (int j = 0; j < MapData.MAPS_SIZE_Y; j++) {
                x = i * MapData.MAPS_CELL_SIZE;
                y = j * MapData.MAPS_CELL_SIZE;
                Ship ship = mapData.getShips()[i][j];
                if (ship != null) {
                    g.setColor(ship.getColor());
                    g.fillRect(x, y, MapData.MAPS_CELL_SIZE, MapData.MAPS_CELL_SIZE);
                }
            }
        }
        // pain shoot
        for (int i = 0; i < MapData.MAPS_SIZE_X; i++) {
            for (int j = 0; j < MapData.MAPS_SIZE_Y; j++) {
                x = i * MapData.MAPS_CELL_SIZE;
                y = j * MapData.MAPS_CELL_SIZE;
                Shoot shoot = mapData.getShoot()[i][j];
                if (shoot != null) {
                    g.setColor(shoot.getColor());
                    int sx = x;
                    int sy = y;
                    for (int si = i; (si < i + shoot.getShotSize()) && si < MapData.MAPS_SIZE_X; si++) {
                        for (int sj = j; (sj < j + shoot.getShotSize()) && sj < MapData.MAPS_SIZE_X; sj++) {
                            sx = si * MapData.MAPS_CELL_SIZE;
                            sy = sj * MapData.MAPS_CELL_SIZE;
                            g.drawLine(sx, sy, sx + MapData.MAPS_CELL_SIZE, sy + MapData.MAPS_CELL_SIZE);
                            g.drawLine(sx + MapData.MAPS_CELL_SIZE, sy, sx, sy + MapData.MAPS_CELL_SIZE);
                        }
                    }
                }
            }
        }
        // pain net
        for (int i = 0; i <= MapData.MAPS_SIZE_X; i++) {
            for (int j = 0; j <= MapData.MAPS_SIZE_Y; j++) {
                g.setColor(Color.BLACK);
                x = i * MapData.MAPS_CELL_SIZE;
                g.drawLine(start_x + x, start_y, start_x + x, MapData.MAPS_CELL_SIZE * MapData.MAPS_SIZE_Y);
                y = j * MapData.MAPS_CELL_SIZE;
                g.drawLine(start_x, start_y + y, MapData.MAPS_CELL_SIZE * MapData.MAPS_SIZE_X, start_y + y);
            }
        }
    }

    protected Image createImageIcon(String path) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL).getImage();
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
}
