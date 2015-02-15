package data;

import java.io.Serializable;

public class ChatData implements Serializable {
    private String text;

    public ChatData(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
