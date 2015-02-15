package urovne;

import base.EntityList;

import java.util.List;

public class Urovne extends EntityList<Uroven> {

    public Urovne() {
    }

    public Urovne(List<Uroven> urovenList) {
        setList(urovenList);
    }

    public void removeElement(int index) {
        if (index != 0) {
            super.removeElement(index);
        }
    }

    @Override
    public void updateElement(Uroven element) {
        if (getList().indexOf(element) == 0) {
            if (element.getSpotreba() != 0) {
                element.setSpotreba(0);
                return;
            }
            super.updateElement(element);
            return;
        }
        // musi byt vetsi nez predchozi a musi byt mensi nez dalsi
        Integer index = getList().indexOf(element);

        if (getList().get(index - 1).getSpotreba() > element.getSpotreba()) {
            element.setSpotreba(getList().get(index - 1).getSpotreba());
            return;
        }
        if (index != (getList().size() - 1)) {
            if (getList().get(index + 1).getSpotreba() < element.getSpotreba()) {
                element.setSpotreba(getList().get(index + 1).getSpotreba());
                return;
            }
        }
        super.updateElement(element);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void sendElement(Uroven element) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void sendElements() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Uroven createElements() {
        Integer spotreba = 0;
        for (Uroven uroven : getList()) {
            if (spotreba < uroven.getSpotreba()) {
                spotreba = uroven.getSpotreba();
            }
        }
        String name = Integer.toString(getList().size());
        return new Uroven(name, spotreba);  //To change body of implemented methods use File | Settings | File Templates.
    }
}
