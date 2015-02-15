package main;

import base.SpotrebiceEntity;
import base.SpotrebiceEntityList;
import data.*;
import server.ServerClient;
import spotrebice.Motory;
import spotrebice.Scanery;
import spotrebice.Stity;
import spotrebice.Zbrane;
import zdroje.Zdroje;

import javax.swing.*;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.JOptionPane.YES_OPTION;

public class ClientData {

    private Zdroje zdroje = new Zdroje();
    private Motory motory = new Motory();
    private Zbrane zbrane = new Zbrane();
    private Scanery scanery = new Scanery();
    private Stity stity = new Stity();
    private Spotreba spotreba;

    public void init() {
        zdroje.init();
        stity.init();
        motory.init();
        scanery.init();
        zbrane.init();
        spotreba = new Spotreba(this);

    }

    public void save(String file) {
        if (YES_OPTION == JOptionPane.showConfirmDialog(null, "Opravdu uložit data?", "Uložení dat", JOptionPane.YES_NO_OPTION)) {
            SaveData data = new SaveData();
//        data.fill(zdroje.getList());
            data.fillMotory(motory.getList());
            data.fillStity(stity.getList());
            data.fillZbrane(zbrane.getList());
            data.fillScenery(scanery.getList());
            data.fillZdroje(zdroje.getList());
            data.fillSpotreba(spotreba);
            XMLEncoder e;
            try {
                e = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(file)));
                e.writeObject(data);
                e.close();
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }

    public void load(String file) {
        if (YES_OPTION == JOptionPane.showConfirmDialog(null, "Opravdu nahrát data?", "Nahrání dat", JOptionPane.YES_NO_OPTION)) {
            XMLDecoder d = null;
            try {
                if (new File(file).exists()) {
                    XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(file)));
                    Object o = decoder.readObject();
                    if (o instanceof SaveData) {
                        SaveData data = (SaveData) o;
                        motory.loadFromSave(data.getMotory());
                        scanery.loadFromSave(data.getScenary());
                        zbrane.loadFromSave(data.getZbrane());
                        stity.loadFromSave(data.getStity());
                        zdroje.loadFromSave(data.getZdroje());
                        spotreba.loadFromFile(data.getSpotreba());

                    }
                    decoder.close();
                }
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
        }
    }

    public Zdroje getZdroje() {
        return zdroje;
    }

    public Stity getStity() {
        return stity;
    }

    public Motory getMotory() {
        return motory;
    }

    public Scanery getScanery() {
        return scanery;
    }

    public Zbrane getZbrane() {
        return zbrane;
    }

    public void onLogin(ServerClient client) {
        zdroje.sendElements();
        stity.sendElements();
        zbrane.sendElements();
        motory.sendElements();
        scanery.sendElements();
    }


    public void onAction(ClientAkce akce) {
        if (akce instanceof ZdrojAkce) {
            zdroje.doAction((ZdrojAkce) akce);
        } else if (akce instanceof SpotrebicAkce) {
            if (SpotrebicData.TYP_MOTOR.equals(((SpotrebicAkce) akce).getTyp())) {
                motory.doAction((SpotrebicAkce) akce);
            } else if (SpotrebicData.TYP_STIT.equals(((SpotrebicAkce) akce).getTyp())) {
                stity.doAction((SpotrebicAkce) akce);
            } else if (SpotrebicData.TYP_SCANER.equals(((SpotrebicAkce) akce).getTyp())) {
                scanery.doAction((SpotrebicAkce) akce);
            } else if (SpotrebicData.TYP_ZBRAN.equals(((SpotrebicAkce) akce).getTyp())) {
                zbrane.doAction((SpotrebicAkce) akce);
            }
        } else if (akce instanceof SpotrebaAkce) {
            doSpotrebaAkce((SpotrebaAkce) akce);
        }
    }

    public void doSpotrebaAkce(SpotrebaAkce akce) {
        switch (akce.getTyp()) {
            case MOTORY:
                if (akce.getAkce() == SpotrebaAkce.Akce.MINUS) {
                    getSpotreba().snizitMotory();
                } else if (akce.getAkce() == SpotrebaAkce.Akce.PLUS) {
                    getSpotreba().zvisitMotory();
                }
                break;
            case STITY:
                if (akce.getAkce() == SpotrebaAkce.Akce.MINUS) {
                    getSpotreba().snizitStity();
                } else if (akce.getAkce() == SpotrebaAkce.Akce.PLUS) {
                    getSpotreba().zvisitStity();
                }
                break;
            case SCANERY:
                if (akce.getAkce() == SpotrebaAkce.Akce.MINUS) {
                    getSpotreba().snizitScenery();
                } else if (akce.getAkce() == SpotrebaAkce.Akce.PLUS) {
                    getSpotreba().zvisitScenery();
                }
                break;
            case ZBRANE:
                if (akce.getAkce() == SpotrebaAkce.Akce.MINUS) {
                    getSpotreba().snizitZbrane();
                } else if (akce.getAkce() == SpotrebaAkce.Akce.PLUS) {
                    getSpotreba().zvisitZbrane();
                }
                break;
        }

    }

    public SpotrebiceEntity getMinPriorityElement() {
        List<SpotrebiceEntity> seznam = new ArrayList<SpotrebiceEntity>();
        SpotrebiceEntity spotrebicStit = getMinPriorityElement(stity);
        SpotrebiceEntity spotrebicMotor = getMinPriorityElement(motory);
        SpotrebiceEntity spotrebicScanery = getMinPriorityElement(scanery);
        SpotrebiceEntity spotrebicZbrane = getMinPriorityElement(zbrane);
        if (spotrebicStit != null) {
            seznam.add(spotrebicStit);
        }
        if (spotrebicMotor != null) {
            seznam.add(spotrebicMotor);
        }
        if (spotrebicScanery != null) {
            seznam.add(spotrebicScanery);
        }
        if (spotrebicZbrane != null) {
            seznam.add(spotrebicZbrane);
        }
        SpotrebiceEntity spotrebiceEntity = null;

        for (SpotrebiceEntity spotrebic : seznam) {
            if (spotrebiceEntity == null) {
                spotrebiceEntity = spotrebic;
            } else {
                if (spotrebiceEntity.getPriorita() > spotrebic.getPriorita()) {
                    spotrebiceEntity = spotrebic;
                }
            }
        }

        return spotrebiceEntity;
    }

    public SpotrebiceEntity getMinPriorityElement(SpotrebiceEntityList<? extends SpotrebiceEntity> list) {
        SpotrebiceEntity spotrebic = null;
        for (SpotrebiceEntity spotrebiceEntity : list.getList()) {
            if (!spotrebiceEntity.isPrvniUroven()) {
                if (spotrebic == null) {
                    spotrebic = spotrebiceEntity;
                } else if (spotrebic.getPriorita() > spotrebiceEntity.getPriorita()) {
                    spotrebic = spotrebiceEntity;
                }
            }
        }
        return spotrebic;
    }


    public Spotreba getSpotreba() {
        return spotreba;
    }

    public void setSpotreba(Spotreba spotreba) {
        this.spotreba = spotreba;
    }
}
