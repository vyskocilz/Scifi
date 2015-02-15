package main;

import base.SpotrebiceEntity;
import data.ClientType;
import data.PalivoData;
import data.SpotrebaData;
import save.SaveSpotreba;
import server.Server;
import server.ServerUtils;
import zdroje.Zdroj;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

public class Spotreba implements Serializable {

    ClientData clientData;

    public Spotreba() {
    }

    public Spotreba(ClientData clientData) {
        this.clientData = clientData;
        timerSpotreba = new Timer(500, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (timerSpotrebaEnable) {
                    try {
                        prepocitejSpotrebu();
                        // todo spocitat palivo
                    } catch (Exception ignored) {

                    }
                }
            }
        });
        timerSpotrebaPaliva = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (timerSpotrebaPalivoEnable) {
                    try {
                        prepocitejPalivo();
                    } catch (Exception ignored) {

                    }
                }
            }
        });
        timerSpotreba.start();
        timerSpotrebaPaliva.start();
    }

    private boolean timerSpotrebaEnable = false;
    private boolean timerSpotrebaPalivoEnable = false;

    private Timer timerSpotreba;
    private Timer timerSpotrebaPaliva;

    private Integer celkovyVykon = 0;
    private Integer celkovaSpotreba = 0;
    private Integer celkovyVykonSpotrebicu = 0;

    private Integer zbraneSpotreba = 0;
    private Integer zbraneVykon = 0;

    private Integer motorySpotreba = 0;
    private Integer motoryVykon = 0;

    private Integer scanerySpotreba = 0;
    private Integer scaneryVykon = 0;

    private Integer stitySpotreba = 0;
    private Integer stityVykon = 0;

    private boolean enablePlus = false;

    private boolean enableMinusMotory = false;
    private boolean enableMinusStity = false;
    private boolean enableMinusZbrane = false;
    private boolean enableMinusScanery = false;

    private boolean enableMotory = false;
    private boolean enableStity = false;
    private boolean enableZbrane = false;
    private boolean enableScanery = false;


    private long palivoCelkem = 0L;
    private long palivoSpotreba = 0L;
    private long palivoTime = 0L;

    private void prepocitejSpotrebu() {
        // spocita vykon
        updateCelkovyVyknon();

        updateStity();
        while (stitySpotreba > stityVykon) {
            snizUroven(clientData.getMinPriorityElement(clientData.getStity()));
            updateStity();
        }

        updateMotory();
        while (motorySpotreba > motoryVykon) {
            snizUroven(clientData.getMinPriorityElement(clientData.getMotory()));
            updateMotory();
        }

        updateZbrane();
        while (zbraneSpotreba > zbraneVykon) {
            snizUroven(clientData.getMinPriorityElement(clientData.getZbrane()));
            updateZbrane();
        }

        updateScanery();
        while (scanerySpotreba > scaneryVykon) {
            snizUroven(clientData.getMinPriorityElement(clientData.getScanery()));
            updateScanery();
        }

        updateCelkovaSpotreba();
        updateCelkovyVykonSpotrebicu();
        while (celkovaSpotreba > celkovyVykon) {
            snizUroven(clientData.getMinPriorityElement());
            updateCelkovaSpotreba();
            updateCelkovyVyknon();
        }
        updateCelkovyVykonSpotrebicu();

        if (celkovyVykon < celkovyVykonSpotrebicu) {
            setScaneryVykon(scanerySpotreba);
            setZbraneVykon(zbraneSpotreba);
            setStityVykon(stitySpotreba);
            setMotoryVykon(motorySpotreba);
        }
        updateCelkovyVykonSpotrebicu();
        setEnablePlus(celkovyVykon > celkovyVykonSpotrebicu);
        setEnableMinusMotory(motoryVykon > 0);
        setEnableMinusScanery(scaneryVykon > 0);
        setEnableMinusStity(stityVykon > 0);
        setEnableMinusZbrane(zbraneVykon > 0);
        sendSpotreba();
    }

    private void prepocitejPalivo() {
        long spotreba = updatePalivoSpotreba();
        while (palivoCelkem < spotreba) {
            Zdroj minZdroj = clientData.getZdroje().getMinPriorityElement();
            if (minZdroj != null) {
                minZdroj.setZapnut(false);
            }
            spotreba = updatePalivoSpotreba();
        }
        setPalivoCelkem(palivoCelkem - spotreba);
        setPalivoSpotreba(spotreba);
        prepoctiPalivoTime();
        sendPalivo();
    }

    public void addPalivo(long hodnota) {
        if ((palivoCelkem + hodnota) <= 0) {
            setPalivoCelkem(0);
        } else {
            setPalivoCelkem(palivoCelkem + hodnota);
        }
        prepoctiPalivoTime();
    }

    private void prepoctiPalivoTime() {
        if (palivoSpotreba == 0) {
            setPalivoTime(0);
        } else {
            setPalivoTime(palivoCelkem / palivoSpotreba);
        }
        sendPalivo();
    }

    private long updatePalivoSpotreba() {
        Long spotreba = 0L;
        for (Zdroj zdroj : clientData.getZdroje().getList()) {
            if (zdroj.isZapnut()) {
                spotreba += zdroj.getSpotrebaPalivaCelkem();
            }
        }
        return spotreba;
    }

    private void sendSpotreba() {
        if (Server.isRunning()) {
            SpotrebaData spotrebaData = new SpotrebaData(
                    celkovyVykon, celkovaSpotreba,
                    enablePlus && enableMotory, enableMinusMotory && enableMotory, motorySpotreba, motoryVykon,
                    enablePlus && enableScanery, enableMinusScanery && enableScanery, scanerySpotreba, scaneryVykon,
                    enablePlus && enableStity, enableMinusStity && enableStity, stitySpotreba, stityVykon,
                    enablePlus && enableZbrane, enableMinusZbrane && enableZbrane, zbraneSpotreba, zbraneVykon
            );
            ServerUtils.getClientGroup().broadcast(spotrebaData, ClientType.STROJOVNA);
        }
    }

    private void sendPalivo() {
        if (Server.isRunning()) {
            PalivoData palivoData = new PalivoData();
            palivoData.setPalivoTime(palivoTime);
            palivoData.setPalivoSpotreba(palivoSpotreba);
            palivoData.setPalivoCelkem(palivoCelkem);
            ServerUtils.getClientGroup().broadcast(palivoData, ClientType.STROJOVNA);
        }
    }

    private void updateCelkovyVyknon() {
        Integer celkovyVykon = 0;
        for (Zdroj zdroj : clientData.getZdroje().getList()) {
            celkovyVykon += zdroj.getAktualniVykon();
        }
        setCelkovyVykon(celkovyVykon);
    }

    private void updateStity() {
        //spocita spotrebu
        Integer spotreba = 0;
        for (SpotrebiceEntity entity : clientData.getStity().getList()) {
            spotreba += entity.getAktualniUroven().getSpotreba();
        }
        setStitySpotreba(spotreba);
    }

    private void updateMotory() {
        //spocita spotrebu
        Integer spotreba = 0;
        for (SpotrebiceEntity entity : clientData.getMotory().getList()) {
            spotreba += entity.getAktualniUroven().getSpotreba();
        }
        setMotorySpotreba(spotreba);
    }

    private void updateZbrane() {
        //spocita spotrebu
        Integer spotreba = 0;
        for (SpotrebiceEntity entity : clientData.getZbrane().getList()) {
            spotreba += entity.getAktualniUroven().getSpotreba();
        }
        setZbraneSpotreba(spotreba);
    }

    private void updateScanery() {
        //spocita spotrebu
        Integer spotreba = 0;
        for (SpotrebiceEntity entity : clientData.getScanery().getList()) {
            spotreba += entity.getAktualniUroven().getSpotreba();
        }
        setScanerySpotreba(spotreba);
    }

    private void updateCelkovyVykonSpotrebicu() {
        Integer celkovyVykonSpotrebicu = zbraneVykon + stityVykon + scaneryVykon + motoryVykon;
        setCelkovyVykonSpotrebicu(celkovyVykonSpotrebicu);
    }

    private void updateCelkovaSpotreba() {
        updateStity();
        Integer celkovaSpotreba = stitySpotreba + zbraneSpotreba + zbraneSpotreba + scanerySpotreba;
        setCelkovaSpotreba(celkovaSpotreba);
    }

    private void snizUroven(SpotrebiceEntity spotrebiceEntity) {
        if (spotrebiceEntity != null) {
            spotrebiceEntity.snizUroven();
        }
    }

    public ClientData getClientData() {
        return clientData;
    }

    public boolean isTimerSpotrebaEnable() {
        return timerSpotrebaEnable;
    }

    public Timer getTimerSpotreba() {
        return timerSpotreba;
    }

    public Integer getCelkovyVykon() {
        return celkovyVykon;
    }

    public Integer getCelkovaSpotreba() {
        return celkovaSpotreba;
    }

    public Integer getCelkovyVykonSpotrebicu() {
        return celkovyVykonSpotrebicu;
    }

    public Integer getZbraneSpotreba() {
        return zbraneSpotreba;
    }

    public Integer getZbraneVykon() {
        return zbraneVykon;
    }

    public Integer getMotorySpotreba() {
        return motorySpotreba;
    }

    public Integer getMotoryVykon() {
        return motoryVykon;
    }

    public Integer getScanerySpotreba() {
        return scanerySpotreba;
    }

    public Integer getScaneryVykon() {
        return scaneryVykon;
    }

    public Integer getStitySpotreba() {
        return stitySpotreba;
    }

    public Integer getStityVykon() {
        return stityVykon;
    }

    public PropertyChangeSupport getSupport() {
        return support;
    }

    public void setCelkovyVykon(Integer celkovyVykon) {
        Integer old = this.celkovyVykon;
        this.celkovyVykon = celkovyVykon;
        support.firePropertyChange("celkovyVykon", old, celkovyVykon);
    }

    public void setCelkovaSpotreba(Integer celkovaSpotreba) {
        Integer old = this.celkovaSpotreba;
        this.celkovaSpotreba = celkovaSpotreba;
        support.firePropertyChange("celkovaSpotreba", old, celkovaSpotreba);
    }

    public void setCelkovyVykonSpotrebicu(Integer celkovyVykonSpotrebicu) {
        Integer old = this.celkovyVykonSpotrebicu;
        this.celkovyVykonSpotrebicu = celkovyVykonSpotrebicu;
        support.firePropertyChange("celkovyVykonSpotrebicu", old, celkovyVykonSpotrebicu);
    }

    public void setZbraneSpotreba(Integer zbraneSpotreba) {
        Integer old = this.zbraneSpotreba;
        this.zbraneSpotreba = zbraneSpotreba;
        support.firePropertyChange("zbraneSpotreba", old, zbraneSpotreba);
    }

    public void setZbraneVykon(Integer zbraneVykon) {
        Integer old = this.zbraneVykon;
        this.zbraneVykon = zbraneVykon;
        support.firePropertyChange("zbraneVykon", old, zbraneVykon);
    }

    public void setMotorySpotreba(Integer motorySpotreba) {
        Integer old = this.motorySpotreba;
        this.motorySpotreba = motorySpotreba;
        support.firePropertyChange("motorySpotreba", old, motorySpotreba);
    }

    public void setMotoryVykon(Integer motoryVykon) {
        Integer old = this.motoryVykon;
        this.motoryVykon = motoryVykon;
        support.firePropertyChange("motoryVykon", old, motoryVykon);
    }

    public void setScanerySpotreba(Integer scanerySpotreba) {
        Integer old = this.scanerySpotreba;
        this.scanerySpotreba = scanerySpotreba;
        support.firePropertyChange("scanerySpotreba", old, scanerySpotreba);
    }

    public void setScaneryVykon(Integer scaneryVykon) {
        Integer old = this.scaneryVykon;
        this.scaneryVykon = scaneryVykon;
        support.firePropertyChange("scaneryVykon", old, scaneryVykon);
    }

    public void setStitySpotreba(Integer stitySpotreba) {
        Integer old = this.stitySpotreba;
        this.stitySpotreba = stitySpotreba;
        support.firePropertyChange("stitySpotreba", old, stitySpotreba);
    }

    public void setStityVykon(Integer stityVykon) {
        Integer old = this.stityVykon;
        this.stityVykon = stityVykon;
        support.firePropertyChange("stityVykon", old, stityVykon);
    }


    public boolean isEnablePlus() {
        return enablePlus;

    }

    public boolean isEnableMinusMotory() {
        return enableMinusMotory;
    }

    public boolean isEnableMinusScanery() {
        return enableMinusScanery;
    }

    public boolean isEnableMinusStity() {
        return enableMinusStity;
    }

    public boolean isEnableMinusZbrane() {
        return enableMinusZbrane;
    }

    public void setEnablePlus(boolean enablePlus) {
        boolean old = this.enablePlus;
        this.enablePlus = enablePlus;
        support.firePropertyChange("enablePlus", old, enablePlus);
    }

    public void setEnableMinusMotory(boolean enableMinusMotory) {
        boolean old = this.enableMinusMotory;
        this.enableMinusMotory = enableMinusMotory;
        support.firePropertyChange("enableMinusMotory", old, enableMinusMotory);
    }

    public void setEnableMinusScanery(boolean enableMinusScanery) {
        boolean old = this.enableMinusScanery;
        this.enableMinusScanery = enableMinusScanery;
        support.firePropertyChange("enablePlus", old, enableMinusScanery);
    }

    public void setEnableMinusStity(boolean enableMinusStity) {
        boolean old = this.enableMinusStity;
        this.enableMinusStity = enableMinusStity;
        support.firePropertyChange("enableMinusStity", old, enableMinusStity);
    }

    public void setEnableMinusZbrane(boolean enableMinusZbrane) {
        boolean old = this.enableMinusZbrane;
        this.enableMinusZbrane = enableMinusZbrane;
        support.firePropertyChange("enableMinusZbrane", old, enableMinusZbrane);
    }

    public boolean isEnableMotory() {
        return enableMotory;
    }

    public boolean isEnableScanery() {
        return enableScanery;
    }

    public boolean isEnableStity() {
        return enableStity;
    }

    public boolean isEnableZbrane() {
        return enableZbrane;
    }

    public long getPalivoCelkem() {
        return palivoCelkem;
    }

    public void setPalivoCelkem(long palivoCelkem) {
        long old = this.palivoCelkem;
        this.palivoCelkem = palivoCelkem;
        support.firePropertyChange("palivoCelkem", old, palivoCelkem);
    }

    public long getPalivoSpotreba() {
        return palivoSpotreba;
    }

    public void setPalivoSpotreba(long palivoSpotreba) {
        long old = this.palivoSpotreba;
        this.palivoSpotreba = palivoSpotreba;
        support.firePropertyChange("palivoSpotreba", old, palivoSpotreba);
    }

    public void setEnableMotory(boolean enableMotory) {
        boolean old = this.enableMotory;
        this.enableMotory = enableMotory;
        support.firePropertyChange("enableMotory", old, enableMotory);
    }

    public void setEnableScanery(boolean enableScanery) {
        boolean old = this.enableScanery;
        this.enableScanery = enableScanery;
        support.firePropertyChange("enablePlus", old, enableScanery);
    }

    public void setEnableStity(boolean enableStity) {
        boolean old = this.enableStity;
        this.enableStity = enableStity;
        support.firePropertyChange("enableStity", old, enableStity);
    }

    public void setEnableZbrane(boolean enableZbrane) {
        boolean old = this.enableZbrane;
        this.enableZbrane = enableZbrane;
        support.firePropertyChange("enableZbrane", old, enableZbrane);
    }

    public void setTimerSpotrebaEnable(boolean timerSpotrebaEnable) {
        boolean old = this.timerSpotrebaEnable;
        this.timerSpotrebaEnable = timerSpotrebaEnable;
        support.firePropertyChange("timerSpotrebaEnable", old, timerSpotrebaEnable);
    }

    public void setTimerSpotrebaPalivoEnable(boolean timerSpotrebaPalivoEnable) {
        boolean old = this.timerSpotrebaPalivoEnable;
        this.timerSpotrebaPalivoEnable = timerSpotrebaPalivoEnable;
        support.firePropertyChange("timerSpotrebaPalivoEnable", old, timerSpotrebaPalivoEnable);
    }

    public long getPalivoTime() {
        return palivoTime;
    }

    public void setPalivoTime(long palivoTime) {
        long old = this.palivoTime;
        this.palivoTime = palivoTime;
        support.firePropertyChange("palivoTime", old, palivoTime);
    }

    private PropertyChangeSupport support = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.support.addPropertyChangeListener(listener);
    }

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        this.support.addPropertyChangeListener(propertyName, listener);
    }

    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        this.support.removePropertyChangeListener(propertyName, listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.support.removePropertyChangeListener(listener);
    }

    public void zvisitStity() {
        updateCelkovyVykonSpotrebicu();
        updateCelkovyVyknon();
        if (celkovyVykonSpotrebicu < celkovyVykon) {
            setStityVykon(stityVykon + 1);
        }
    }

    public void snizitStity() {
        if (stityVykon > 0) {
            setStityVykon(stityVykon - 1);
        }
    }

    public void zvisitMotory() {
        updateCelkovyVykonSpotrebicu();
        updateCelkovyVyknon();
        if (celkovyVykonSpotrebicu < celkovyVykon) {
            setMotoryVykon(motoryVykon + 1);
        }
    }

    public void snizitMotory() {
        if (motoryVykon > 0) {
            setMotoryVykon(motoryVykon - 1);
        }
    }

    public void zvisitScenery() {
        updateCelkovyVykonSpotrebicu();
        updateCelkovyVyknon();
        if (celkovyVykonSpotrebicu < celkovyVykon) {
            setScaneryVykon(scaneryVykon + 1);
        }
    }

    public void snizitScenery() {
        if (scaneryVykon > 0) {
            setScaneryVykon(scaneryVykon - 1);
        }
    }

    public void zvisitZbrane() {
        updateCelkovyVykonSpotrebicu();
        updateCelkovyVyknon();
        if (celkovyVykonSpotrebicu < celkovyVykon) {
            setZbraneVykon(zbraneVykon + 1);
        }
    }

    public void snizitZbrane() {
        if (zbraneVykon > 0) {
            setZbraneVykon(zbraneVykon - 1);
        }
    }

    public void loadFromFile(SaveSpotreba saveSpotreba) {
        setZbraneVykon(saveSpotreba.getZbraneVykon());
        setMotoryVykon(saveSpotreba.getMotoryVykon());
        setScaneryVykon(saveSpotreba.getScaneryVykon());
        setStityVykon(saveSpotreba.getStityVykon());
        setEnableMotory(saveSpotreba.isEnableMotory());
        setEnableStity(saveSpotreba.isEnableStity());
        setEnableZbrane(saveSpotreba.isEnableZbrane());
        setEnableScanery(saveSpotreba.isEnableScanery());
    }
}
