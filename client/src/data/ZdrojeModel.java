package data;

import komponenty.SpotrebiceKomponenta;
import komponenty.ZdrojKomponenta;

import javax.swing.*;
import java.util.HashMap;

public class ZdrojeModel {
    static JPanel strojovnaPanel;
    static JPanel zbranePanel;
    static JPanel stityPanel;
    static JPanel motoryPanel;
    static JPanel sceneryPanel;

    static HashMap<String, ZdrojKomponenta> zdroje = new HashMap<String, ZdrojKomponenta>();
    static HashMap<String, SpotrebiceKomponenta> stity = new HashMap<String, SpotrebiceKomponenta>();
    static HashMap<String, SpotrebiceKomponenta> zbrane = new HashMap<String, SpotrebiceKomponenta>();
    static HashMap<String, SpotrebiceKomponenta> motory = new HashMap<String, SpotrebiceKomponenta>();
    static HashMap<String, SpotrebiceKomponenta> scanery = new HashMap<String, SpotrebiceKomponenta>();

    public static void updatePristoreje(SpotrebicData spotrebicData) {
        HashMap<String, SpotrebiceKomponenta> seznam = null;
        JPanel panel = null;
        if (SpotrebicData.TYP_MOTOR.equals(spotrebicData.getTyp())) {
            seznam = motory;
            panel = motoryPanel;
        } else if (SpotrebicData.TYP_STIT.equals(spotrebicData.getTyp())) {
            seznam = stity;
            panel = stityPanel;
        } else if (SpotrebicData.TYP_SCANER.equals(spotrebicData.getTyp())) {
            seznam = scanery;
            panel = sceneryPanel;
        } else if (SpotrebicData.TYP_ZBRAN.equals(spotrebicData.getTyp())) {
            seznam = zbrane;
            panel = zbranePanel;
        }
        if (seznam == null || panel == null) {
            return;
        }

        SpotrebiceKomponenta spotrebiceKomponenta = seznam.get(spotrebicData.getSysName());
        if (spotrebiceKomponenta == null) {
            spotrebiceKomponenta = new SpotrebiceKomponenta();
            spotrebiceKomponenta.setSysName(spotrebicData.getSysName());
            spotrebiceKomponenta.setType(spotrebicData.getTyp());
            seznam.put(spotrebicData.getSysName(), spotrebiceKomponenta);
            panel.add(spotrebiceKomponenta);
        }
        spotrebiceKomponenta.setNazev(spotrebicData.getNazev());
        spotrebiceKomponenta.setUroven(spotrebicData.getUrovenText());
        spotrebiceKomponenta.setEnabled(spotrebicData.isOvladatelnost());
        spotrebiceKomponenta.setVisible(spotrebicData.isViditelnost());
        spotrebiceKomponenta.setZapnuto(spotrebicData.isZapnut());
        spotrebiceKomponenta.setPlusMinusEnabled(spotrebicData.isPlusEnabled(), spotrebicData.isMinusEnabled());
        panel.repaint();
    }

    public static void updatePristrojePanel(SpotrebiceData spotrebiceData) {
        HashMap<String, SpotrebiceKomponenta> seznam = null;
        JPanel panel = null;
        if (SpotrebicData.TYP_MOTOR.equals(spotrebiceData.getTyp())) {
            seznam = motory;
            panel = motoryPanel;
        } else if (SpotrebicData.TYP_STIT.equals(spotrebiceData.getTyp())) {
            seznam = stity;
            panel = stityPanel;
        } else if (SpotrebicData.TYP_SCANER.equals(spotrebiceData.getTyp())) {
            seznam = scanery;
            panel = sceneryPanel;
        } else if (SpotrebicData.TYP_ZBRAN.equals(spotrebiceData.getTyp())) {
            seznam = zbrane;
            panel = zbranePanel;
        }
        if (seznam == null || panel == null) {
            return;
        }

        seznam.clear();
        panel.removeAll();
        for (SpotrebicData spotrebicData : spotrebiceData.getSpotrebice()) {
            updatePristoreje(spotrebicData);
        }
    }

    public static void updatePanel(ZdrojData zdrojData) {
        ZdrojKomponenta zdroj = zdroje.get(zdrojData.getSysName());
        if (zdroj == null) {
            zdroj = new ZdrojKomponenta();
            zdroj.setSysName(zdrojData.getSysName());
            zdroje.put(zdrojData.getSysName(), zdroj);
            strojovnaPanel.add(zdroj);
        }
        zdroj.setNazev(zdrojData.getNazev());
        zdroj.setVykon(zdrojData.getVykon());
        zdroj.setEnabled(zdrojData.isOvladatelnost());
        zdroj.setVisible(zdrojData.isViditelnost());
        zdroj.setZapnuto(zdrojData.isZapnut());
        zdroj.setPlusMinusEnabled(zdrojData.isPlusEnabled(), zdrojData.isMinusEnabled());
        zdroj.setSpotrebaPaliva(zdrojData.getSpotrebaPaliva());
        zdroj.setMaxVykon(zdrojData.getMaxVykon());
        strojovnaPanel.repaint();
    }

    public static void updatePanel(ZdrojeData zdrojeData) {
        zdroje.clear();
        strojovnaPanel.removeAll();
        for (ZdrojData zdroj : zdrojeData.getZdroje()) {
            updatePanel(zdroj);
        }
    }

    public static JPanel getStrojovnaPanel() {
        return strojovnaPanel;
    }

    public static void setStrojovnaPanel(JPanel strojovnaPanel) {
        ZdrojeModel.strojovnaPanel = strojovnaPanel;
    }

    public static JPanel getZbranePanel() {
        return zbranePanel;
    }

    public static void setZbranePanel(JPanel zbranePanel) {
        ZdrojeModel.zbranePanel = zbranePanel;
    }

    public static JPanel getStityPanel() {
        return stityPanel;
    }

    public static void setStityPanel(JPanel stityPanel) {
        ZdrojeModel.stityPanel = stityPanel;
    }

    public static JPanel getMotoryPanel() {
        return motoryPanel;
    }

    public static void setMotoryPanel(JPanel motoryPanel) {
        ZdrojeModel.motoryPanel = motoryPanel;
    }

    public static JPanel getSceneryPanel() {
        return sceneryPanel;
    }

    public static void setSceneryPanel(JPanel sceneryPanel) {
        ZdrojeModel.sceneryPanel = sceneryPanel;
    }
}
