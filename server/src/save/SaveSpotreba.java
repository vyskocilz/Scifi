package save;

/**
 * Created by Zdenec    .
 * User: Zdenec
 * WWW BASKET TREBIC
 */
public class SaveSpotreba {

    private Integer zbraneVykon = 0;

    private Integer motoryVykon = 0;

    private Integer scaneryVykon = 0;

    private Integer stityVykon = 0;

    private boolean enableMotory = false;
    private boolean enableStity = false;
    private boolean enableZbrane = false;
    private boolean enableScanery = false;

    public SaveSpotreba() {
    }

    public SaveSpotreba(Integer zbraneVykon, Integer motoryVykon, Integer scaneryVykon, Integer stityVykon, boolean enableMotory, boolean enableStity, boolean enableZbrane, boolean enableScanery) {
        this.zbraneVykon = zbraneVykon;
        this.motoryVykon = motoryVykon;
        this.scaneryVykon = scaneryVykon;
        this.stityVykon = stityVykon;
        this.enableMotory = enableMotory;
        this.enableStity = enableStity;
        this.enableZbrane = enableZbrane;
        this.enableScanery = enableScanery;
    }

    public Integer getZbraneVykon() {
        return zbraneVykon;
    }

    public void setZbraneVykon(Integer zbraneVykon) {
        this.zbraneVykon = zbraneVykon;
    }

    public Integer getMotoryVykon() {
        return motoryVykon;
    }

    public void setMotoryVykon(Integer motoryVykon) {
        this.motoryVykon = motoryVykon;
    }

    public Integer getScaneryVykon() {
        return scaneryVykon;
    }

    public void setScaneryVykon(Integer scaneryVykon) {
        this.scaneryVykon = scaneryVykon;
    }

    public Integer getStityVykon() {
        return stityVykon;
    }

    public void setStityVykon(Integer stityVykon) {
        this.stityVykon = stityVykon;
    }

    public boolean isEnableMotory() {
        return enableMotory;
    }

    public void setEnableMotory(boolean enableMotory) {
        this.enableMotory = enableMotory;
    }

    public boolean isEnableStity() {
        return enableStity;
    }

    public void setEnableStity(boolean enableStity) {
        this.enableStity = enableStity;
    }

    public boolean isEnableZbrane() {
        return enableZbrane;
    }

    public void setEnableZbrane(boolean enableZbrane) {
        this.enableZbrane = enableZbrane;
    }

    public boolean isEnableScanery() {
        return enableScanery;
    }

    public void setEnableScanery(boolean enableScanery) {
        this.enableScanery = enableScanery;
    }
}
