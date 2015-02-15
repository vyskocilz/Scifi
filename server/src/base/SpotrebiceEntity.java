package base;

import urovne.Uroven;
import urovne.Urovne;

public abstract class SpotrebiceEntity extends Entity {

    private Urovne urovne = new Urovne();

    private Uroven aktualniUroven;

    private boolean zapnut = false;
    private boolean ovladatelnost = false;
    private boolean viditelnost = false;

    private Integer priorita = 0;


    public SpotrebiceEntity(String sysName) {
        super(sysName);
        urovne.createNewElement();

        urovne.addEntityListChangeHandler(new EntityListChangeHandler<Uroven>() {

            public void onAdd(Uroven element) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            public void onRemove(Uroven element) {
                if (urovne.getList().indexOf(aktualniUroven) == -1) {
                    setAktualniUroven(urovne.getList().get(0));
                }
            }

            public void onDataChange(Uroven element) {
                if (element.equals(aktualniUroven)) {
                    onUrovenChange();
                }
            }
        });
    }

    public boolean isPrvniUroven() {
        return urovne.getList().indexOf(aktualniUroven) == 0;
    }

    public boolean isLastUroven() {
        return urovne.getList().indexOf(aktualniUroven) + 1 == urovne.getList().size();
    }

    public void onUrovenChange() {
        onDataChange();
    }

    public Urovne getUrovne() {
        return urovne;
    }

    public void setUrovne(Urovne urovne) {
        this.urovne = urovne;

    }

    public Uroven getAktualniUroven() {
        if (aktualniUroven == null) {
            setAktualniUroven(urovne.getList().get(0));
        }
        return aktualniUroven;
    }

    public void setAktualniUroven(Uroven aktualniUroven) {
        this.aktualniUroven = aktualniUroven;
        onUrovenChange();
    }

    public boolean isZapnut() {
        return zapnut;
    }

    public void setZapnut(boolean zapnut) {
        this.zapnut = zapnut;
        if (!zapnut) {
            setAktualniUroven(urovne.getList().get(0));
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

    public void zvysUrove() {
        if (isZapnut()) {
            Integer index = urovne.getList().indexOf(aktualniUroven);
            if (index < urovne.getList().size() - 1) {
                setAktualniUroven(urovne.getList().get(index + 1));
            }
        }
    }

    public void snizUroven() {
        Integer index = urovne.getList().indexOf(aktualniUroven);
        if (index != 0) {
            setAktualniUroven(urovne.getList().get(index - 1));
        }
    }
}
