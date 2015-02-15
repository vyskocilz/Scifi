package controler;

import data.entity.EnergieSource;
import data.entity.Fuel;
import org.jdesktop.beansbinding.Binding;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Zdenec    .
 * User: Zdenec
 * WWW BASKET TREBIC
 */
public class EnergieSourceServerController extends BaseEntityServerController<EnergieSource> {

    private Boolean recalculateFuel;

    private Fuel fuel;

    public EnergieSourceServerController() {
        super();
        recalculateFuel = false;
        fuel = new Fuel();
        fuel.setFuel(0L);
        fuel.setFuelUsage(0L);
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(new Runnable() {
            public void run() {
                try {
                    recalculateActualFuel();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 1, 1, TimeUnit.SECONDS);
    }

    @Override
    protected EnergieSource create() {
        return new EnergieSource();
    }

    public void addNew() {
        EnergieSource energieSource = createNew();
        // base config
        energieSource.setName("Nový zdroj energie");
        energieSource.setEditable(false);
        energieSource.setVisible(false);

        // EnergieSource setting
        energieSource.setEnable(false);
        energieSource.setPriority(0L);
        energieSource.setPowerNeeded(0L);
        energieSource.setPowerActual(0L);
        energieSource.setPowerMax(0L);
        energieSource.setPowerMin(0L);
        energieSource.setPowerAddSpeed(0L);
        energieSource.setFuelUsageActual(0L);
        energieSource.setFuelUsage(0L);
        list.add(energieSource);
    }

    @Override
    protected void validate(Binding binding, EnergieSource entity) {
        processPower(entity);
        processFuelUsage(entity);
    }

    private void processPower(EnergieSource energieSource) {
        // Min power
        if (energieSource.getPowerMin() > energieSource.getPowerMax()) {
            energieSource.setPowerMin(energieSource.getPowerMax());
        }
        // Needed power
        if (energieSource.getPowerNeeded() > energieSource.getPowerMax()) {
            energieSource.setPowerNeeded(energieSource.getPowerMax());
        }
        // Actual power
        if (energieSource.getPowerActual() > energieSource.getPowerMax()) {
            energieSource.setPowerActual(energieSource.getPowerMax());
        }
        if (energieSource.getEnable()) {
            if (energieSource.getPowerActual() < energieSource.getPowerMin()) {
                energieSource.setPowerActual(energieSource.getPowerMin());
            }
        } else {
            energieSource.setPowerActual(0L);
        }
    }

    private boolean processFuelUsage(EnergieSource energieSource) {
        Long oldFuelUsage = energieSource.getFuelUsage();
        Long fuelUsage;
        if (energieSource.getEnable()) {
            fuelUsage = energieSource.getPowerActual() * energieSource.getFuelUsage();
        } else {
            fuelUsage = 0L;
        }
        if (!fuelUsage.equals(oldFuelUsage)) {
            energieSource.setFuelUsageActual(fuelUsage);
            return true;
        } else {
            return false;
        }
    }

    private void recalculateActualFuel() {
        Long actualFuel = 0L;
        for (EnergieSource energieSource : getList()) {
            if (processFuelUsage(energieSource)) {
                entityChanged(energieSource);
            }
            actualFuel += energieSource.getFuelUsageActual();

        }
        processFuel(fuel, actualFuel);
    }

    private void processFuel(Fuel fuel, Long fuelUsage) {
        if (recalculateFuel) {
            fuel.setFuelUsage(fuelUsage);
            Long actualFuel = fuel.getFuel() - fuelUsage;
            if (actualFuel < 0) {
                disableSourceEngine();
                recalculateActualFuel();
            } else {
                fuel.setFuel(actualFuel);
            }
        } else {
            fuel.setFuelUsage(0L);
        }
    }

    private void disableSourceEngine() {
        EnergieSource lowPriorityEnergieSource = null;
        for (EnergieSource energieSource : getList()) {
            if (lowPriorityEnergieSource == null) {
                lowPriorityEnergieSource = energieSource;
            } else if (energieSource.getEnable() && energieSource.getPriority() < lowPriorityEnergieSource.getPriority()) {
                lowPriorityEnergieSource = energieSource;
            }
        }
        if (lowPriorityEnergieSource != null) {
            lowPriorityEnergieSource.setEnable(false);
            processFuelUsage(lowPriorityEnergieSource);
            entityChanged(lowPriorityEnergieSource);
        }
    }

    public Boolean getRecalculateFuel() {
        return recalculateFuel;
    }

    public void setRecalculateFuel(Boolean recalculateFuel) {
        this.recalculateFuel = recalculateFuel;
    }
}
