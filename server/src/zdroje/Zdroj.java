package zdroje;

import base.Entity;

import java.util.Timer;
import java.util.TimerTask;

public class Zdroj extends Entity {

    private Timer zapnutiTimer = new Timer();

    Integer aktualniVykon = 0;
    Integer pozadovanyVykon = 0;
    Integer maxVykon = 0;
    Integer minVykon = 0;
    Integer rychlostNabihani = 1000;
    long spotrebaPaliva = 1;
    long spotrebaPalivaCelkem = 0;
    boolean zapnut = false;
    boolean ovladatelnost = false;
    boolean viditelnost = false;
    Integer priorita = 0;

    public Zdroj() {
        super("EnergieSource");
    }

    public Zdroj(String nazev, Integer aktualniVykon, Integer pozadovanyVykon, Integer maxVykon, Integer minVykon, Integer rychlostNabihani) {
        this();
        this.nazev = nazev;
        this.aktualniVykon = aktualniVykon;
        this.pozadovanyVykon = pozadovanyVykon;
        this.maxVykon = maxVykon;
        this.minVykon = minVykon;
        this.rychlostNabihani = rychlostNabihani;
    }

    public Integer getAktualniVykon() {
        return aktualniVykon;
    }

    public void setAktualniVykon(Integer aktualniVykon) {
        aktualniVykon = notNull(aktualniVykon);
        if (maxVykon < aktualniVykon) {
            aktualniVykon = maxVykon;
        }
        if (minVykon > aktualniVykon) {
            aktualniVykon = minVykon;
        }
        if (aktualniVykon > pozadovanyVykon) {
            aktualniVykon = pozadovanyVykon;
        }
        this.aktualniVykon = aktualniVykon;
        setSpotrebaPalivaCelkem(aktualniVykon * spotrebaPaliva);
        onDataChange();

    }

    public Integer getPozadovanyVykon() {
        return pozadovanyVykon;
    }

    public void setPozadovanyVykon(Integer pozadovanyVykon) {
        pozadovanyVykon = notNull(pozadovanyVykon);
        if (maxVykon < pozadovanyVykon) {
            pozadovanyVykon = maxVykon;
        }
        if (minVykon > pozadovanyVykon) {
            pozadovanyVykon = minVykon;
        }
        if (pozadovanyVykon < aktualniVykon) {
            setAktualniVykon(pozadovanyVykon);
        }
        this.pozadovanyVykon = pozadovanyVykon;
        onDataChange();
    }

    public Integer getMaxVykon() {
        return maxVykon;
    }

    public void setMaxVykon(Integer maxVykon) {
        this.maxVykon = notNull(maxVykon);
        onDataChange();
    }

    public Integer getMinVykon() {
        return minVykon;
    }

    public void setMinVykon(Integer minVykon) {
        this.minVykon = notNull(minVykon);
        onDataChange();
    }

    public Integer getRychlostNabihani() {
        return rychlostNabihani;
    }

    public void setRychlostNabihani(Integer rychlostNabihani) {
        this.rychlostNabihani = notNull(rychlostNabihani, 1000);
        if (rychlostNabihani <= 0) {
            this.rychlostNabihani = 1000;
        }
        if (isZapnut()) {
            zapnutiTimer.cancel();
            zapnutiTimer = new Timer();
            zapnutiTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        onNabehnuti();
                    } catch (Exception ignored) {

                    }
                }
            }, this.rychlostNabihani, this.rychlostNabihani);
        }
    }

    public boolean isZapnut() {
        return zapnut;
    }

    private void onNabehnuti() {
        if (aktualniVykon < pozadovanyVykon && aktualniVykon < maxVykon) {
            setAktualniVykon(aktualniVykon + 1);
        }
    }

    public void setZapnut(boolean zapnut) {
        this.zapnut = zapnut;
        if (zapnut) {
            zapnutiTimer.cancel();
            zapnutiTimer = new Timer();
            zapnutiTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        onNabehnuti();
                    } catch (Exception ignored) {

                    }
                }
            }, rychlostNabihani, rychlostNabihani);
        } else {
            zapnutiTimer.cancel();
            setAktualniVykon(minVykon);
        }
        onDataChange();
    }

    public boolean isOvladatelnost() {
        return ovladatelnost;
    }

    public void setOvladatelnost(boolean ovladatelnost) {
        this.ovladatelnost = ovladatelnost;
        onDataChange();
    }

    public boolean isViditelnost() {
        return viditelnost;
    }

    public void setViditelnost(boolean viditelnost) {
        this.viditelnost = viditelnost;
        onDataChange();
    }

    public long getSpotrebaPaliva() {
        return spotrebaPaliva;
    }

    public void setSpotrebaPaliva(long spotrebaPaliva) {
        this.spotrebaPaliva = spotrebaPaliva;
        onDataChange();
    }

    public Integer getPriorita() {
        return priorita;
    }

    public void setPriorita(Integer priorita) {
        if (notNull(priorita) < 0) {
            priorita = 0;
        }
        this.priorita = notNull(priorita);
        onDataChange();
    }

    public long getSpotrebaPalivaCelkem() {
        return spotrebaPalivaCelkem;
    }

    public void setSpotrebaPalivaCelkem(long spotrebaPalivaCelkem) {
        this.spotrebaPalivaCelkem = spotrebaPalivaCelkem;
        onDataChange();
    }
}
