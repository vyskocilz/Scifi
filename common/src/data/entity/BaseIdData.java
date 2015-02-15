package data.entity;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Zdenec    .
 * User: Zdenec
 * WWW BASKET TREBIC
 */
public class BaseIdData implements Serializable {
    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
