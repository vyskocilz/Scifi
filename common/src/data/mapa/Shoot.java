package data.mapa;

import java.awt.*;
import java.io.Serializable;

/**
 * Created by pupupaj on 4.3.2015.
 */
public class Shoot implements Serializable {
    Color color;
    int shotSize;
    int shootPoolSize;

    public Shoot() {
    }

    public Shoot(Shoot shoot) {
        this.color = shoot.color;
        this.shotSize = shoot.shotSize;
        this.shootPoolSize = shoot.shootPoolSize;
    }

    public Shoot(Color color, int shotSize, int shootPoolSize) {
        this.color = color;
        this.shotSize = shotSize;
        this.shootPoolSize = shootPoolSize;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getShotSize() {
        return shotSize;
    }

    public void setShotSize(int shotSize) {
        this.shotSize = shotSize;
    }

    public int getShootPoolSize() {
        return shootPoolSize;
    }

    public void setShootPoolSize(int shootPoolSize) {
        this.shootPoolSize = shootPoolSize;
    }
}
