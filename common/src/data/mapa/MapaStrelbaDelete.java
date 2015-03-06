package data.mapa;

import java.io.Serializable;

/**
 * Created by pupupaj on 6.3.2015.
 */
public class MapaStrelbaDelete implements Serializable {
    int type;

    public MapaStrelbaDelete() {
    }

    public MapaStrelbaDelete(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

