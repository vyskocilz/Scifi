package data.entity;

public class EnergieSource extends BaseData {

    Boolean enable;         // on/off
    Long priority;          // priority - the highest will be disable last
    Long powerActual;       // power - actual
    Long powerNeeded;       // power - needed by operator
    Long powerMax;          // power - max
    Long powerMin;          // power - min
    Long powerAddSpeed;     // power - add energie per second
    Long fuelUsageActual;   // fuel - actual usage
    Long fuelUsage;         // fuel - usage per second

    public Long getPriority() {
        return priority;
    }

    public void setPriority(Long priority) {
        this.priority = priority;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Long getPowerActual() {
        return powerActual;
    }

    public void setPowerActual(Long powerActual) {
        this.powerActual = powerActual;
    }

    public Long getPowerMax() {
        return powerMax;
    }

    public void setPowerMax(Long powerMax) {
        this.powerMax = powerMax;
    }

    public Long getPowerMin() {
        return powerMin;
    }

    public void setPowerMin(Long powerMin) {
        this.powerMin = powerMin;
    }

    public Long getPowerAddSpeed() {
        return powerAddSpeed;
    }

    public void setPowerAddSpeed(Long powerAddSpeed) {
        this.powerAddSpeed = powerAddSpeed;
    }

    public Long getFuelUsageActual() {
        return fuelUsageActual;
    }

    public void setFuelUsageActual(Long fuelUsageActual) {
        this.fuelUsageActual = fuelUsageActual;
    }

    public Long getFuelUsage() {
        return fuelUsage;
    }

    public void setFuelUsage(Long fuelUsage) {
        this.fuelUsage = fuelUsage;
    }

    public Long getPowerNeeded() {
        return powerNeeded;
    }

    public void setPowerNeeded(Long powerNeeded) {
        this.powerNeeded = powerNeeded;
    }
}
