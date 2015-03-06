package data.mapa;

import java.io.Serializable;

/**
 * Created by pupupaj on 6.3.2015.
 */
public class MapaStrelba implements Serializable {
    int x;
    int y;
    int type;

    public MapaStrelba() {
    }

    public MapaStrelba(int y, int x, int type) {
        this.y = y;
        this.x = x;
        this.type = type;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

