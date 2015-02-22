package data;

import client.ClientUtils;
import org.jdesktop.observablecollections.ObservableCollections;
import org.jdesktop.observablecollections.ObservableList;

import java.util.ArrayList;

//import server.ServerUtils;

public class Chat {
    static ObservableCollections.ObservableListHelper<String> chatModel = ObservableCollections.observableListHelper(new ArrayList<String>());
    static ObservableList<String> chatListModel = chatModel.getObservableList();

    public static ObservableList<String> getChatListModel() {
        return chatListModel;
    }

    public static void writeText(String text) {
        while (chatListModel.size() > 100) {
            chatListModel.remove(100);
        }
        chatListModel.add(0, text);
    }

    public static void sendText(String text) {
        ClientUtils.sendData(new ChatData(text));
    }
}
