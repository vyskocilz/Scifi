package data;

import java.io.Serializable;
import java.util.List;

public class Login implements Serializable {
    private List<String> clientTypes;
    private String clientName;

    public Login() {

    }

    public List<String> getClientTypes() {
        return clientTypes;
    }

    public void setClientTypes(List<String> clientTypes) {
        this.clientTypes = clientTypes;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
}
