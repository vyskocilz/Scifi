package data;

import java.io.Serializable;
import java.util.List;

public class ZdrojeData implements Serializable {
    List<ZdrojData> zdroje;

    public ZdrojeData() {
    }

    public ZdrojeData(List<ZdrojData> zdroje) {
        this.zdroje = zdroje;
    }

    public List<ZdrojData> getZdroje() {
        return zdroje;
    }

    public void setZdroje(List<ZdrojData> zdroje) {
        this.zdroje = zdroje;
    }
}
