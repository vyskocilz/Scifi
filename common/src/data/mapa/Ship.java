package data.mapa;

import java.awt.*;
import java.io.Serializable;

/**
 * Created by pupupaj on 4.3.2015.
 */
public class Ship implements Serializable {
    Color color;
    String imageUrl;

    public Ship() {
    }

    public Ship(Ship ship) {
        this.color = ship.color;
        this.imageUrl = ship.imageUrl;
    }

    public Ship(Color color, String imageUrl) {
        this.color = color;
        this.imageUrl = imageUrl;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
