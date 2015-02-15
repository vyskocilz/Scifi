package data;

import java.io.Serializable;

public class SpotrebaData implements Serializable {

    private Integer celkovyVykon = 0;
    private Integer celkovaSpotreba = 0;

    private Integer zbraneSpotreba = 0;
    private Integer zbraneVykon = 0;
    private boolean zbranePlusEnebla = false;
    private boolean zbraneMinusEnebla = false;

    private Integer motorySpotreba = 0;
    private Integer motoryVykon = 0;
    private boolean motoryPlusEnebla = false;
    private boolean motoryMinusEnebla = false;

    private Integer scanerySpotreba = 0;
    private Integer scaneryVykon = 0;
    private boolean scaneryPlusEnebla = false;
    private boolean scaneryMinusEnebla = false;

    private Integer stitySpotreba = 0;
    private Integer stityVykon = 0;
    private boolean stityPlusEnebla = false;
    private boolean stityMinusEnebla = false;

    public SpotrebaData() {
    }

    public SpotrebaData(Integer celkovyVykon, Integer celkovaSpotreba,
                        boolean motoryPlusEnebla, boolean motoryMinusEnebla, Integer motorySpotreba, Integer motoryVykon,
                        boolean scaneryPlusEnebla, boolean scaneryMinusEnebla, Integer scanerySpotreba, Integer scaneryVykon,
                        boolean stityPlusEnebla, boolean stityMinusEnebla, Integer stitySpotreba, Integer stityVykon,
                        boolean zbranePlusEnebla, boolean zbraneMinusEnebla, Integer zbraneSpotreba, Integer zbraneVykon) {
        this.celkovyVykon = celkovyVykon;
        this.celkovaSpotreba = celkovaSpotreba;
        this.motoryPlusEnebla = motoryPlusEnebla;
        this.motoryMinusEnebla = motoryMinusEnebla;
        this.motorySpotreba = motorySpotreba;
        this.motoryVykon = motoryVykon;
        this.scaneryMinusEnebla = scaneryMinusEnebla;
        this.scaneryPlusEnebla = scaneryPlusEnebla;
        this.scanerySpotreba = scanerySpotreba;
        this.scaneryVykon = scaneryVykon;
        this.stityMinusEnebla = stityMinusEnebla;
        this.stityPlusEnebla = stityPlusEnebla;
        this.stitySpotreba = stitySpotreba;
        this.stityVykon = stityVykon;
        this.zbraneMinusEnebla = zbraneMinusEnebla;
        this.zbranePlusEnebla = zbranePlusEnebla;
        this.zbraneSpotreba = zbraneSpotreba;
        this.zbraneVykon = zbraneVykon;
    }

    public Integer getCelkovaSpotreba() {
        return celkovaSpotreba;
    }

    public void setCelkovaSpotreba(Integer celkovaSpotreba) {
        this.celkovaSpotreba = celkovaSpotreba;
    }

    public Integer getCelkovyVykon() {
        return celkovyVykon;
    }

    public void setCelkovyVykon(Integer celkovyVykon) {
        this.celkovyVykon = celkovyVykon;
    }

    public boolean isMotoryPlusEnebla() {
        return motoryPlusEnebla;
    }

    public void setMotoryPlusEnebla(boolean motoryPlusEnebla) {
        this.motoryPlusEnebla = motoryPlusEnebla;
    }

    public boolean isMotoryMinusEnebla() {
        return motoryMinusEnebla;
    }

    public void setMotoryMinusEnebla(boolean motoryMinusEnebla) {
        this.motoryMinusEnebla = motoryMinusEnebla;
    }

    public Integer getMotorySpotreba() {
        return motorySpotreba;
    }

    public void setMotorySpotreba(Integer motorySpotreba) {
        this.motorySpotreba = motorySpotreba;
    }

    public Integer getMotoryVykon() {
        return motoryVykon;
    }

    public void setMotoryVykon(Integer motoryVykon) {
        this.motoryVykon = motoryVykon;
    }

    public boolean isScaneryMinusEnebla() {
        return scaneryMinusEnebla;
    }

    public void setScaneryMinusEnebla(boolean scaneryMinusEnebla) {
        this.scaneryMinusEnebla = scaneryMinusEnebla;
    }

    public boolean isScaneryPlusEnebla() {
        return scaneryPlusEnebla;
    }

    public void setScaneryPlusEnebla(boolean scaneryPlusEnebla) {
        this.scaneryPlusEnebla = scaneryPlusEnebla;
    }

    public Integer getScanerySpotreba() {
        return scanerySpotreba;
    }

    public void setScanerySpotreba(Integer scanerySpotreba) {
        this.scanerySpotreba = scanerySpotreba;
    }

    public Integer getScaneryVykon() {
        return scaneryVykon;
    }

    public void setScaneryVykon(Integer scaneryVykon) {
        this.scaneryVykon = scaneryVykon;
    }

    public boolean isStityMinusEnebla() {
        return stityMinusEnebla;
    }

    public void setStityMinusEnebla(boolean stityMinusEnebla) {
        this.stityMinusEnebla = stityMinusEnebla;
    }

    public boolean isStityPlusEnebla() {
        return stityPlusEnebla;
    }

    public void setStityPlusEnebla(boolean stityPlusEnebla) {
        this.stityPlusEnebla = stityPlusEnebla;
    }

    public Integer getStitySpotreba() {
        return stitySpotreba;
    }

    public void setStitySpotreba(Integer stitySpotreba) {
        this.stitySpotreba = stitySpotreba;
    }

    public Integer getStityVykon() {
        return stityVykon;
    }

    public void setStityVykon(Integer stityVykon) {
        this.stityVykon = stityVykon;
    }

    public boolean isZbraneMinusEnebla() {
        return zbraneMinusEnebla;
    }

    public void setZbraneMinusEnebla(boolean zbraneMinusEnebla) {
        this.zbraneMinusEnebla = zbraneMinusEnebla;
    }

    public boolean isZbranePlusEnebla() {
        return zbranePlusEnebla;
    }

    public void setZbranePlusEnebla(boolean zbranePlusEnebla) {
        this.zbranePlusEnebla = zbranePlusEnebla;
    }

    public Integer getZbraneSpotreba() {
        return zbraneSpotreba;
    }

    public void setZbraneSpotreba(Integer zbraneSpotreba) {
        this.zbraneSpotreba = zbraneSpotreba;
    }

    public Integer getZbraneVykon() {
        return zbraneVykon;
    }

    public void setZbraneVykon(Integer zbraneVykon) {
        this.zbraneVykon = zbraneVykon;
    }
}
