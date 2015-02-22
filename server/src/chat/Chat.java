package chat;

import org.jdesktop.observablecollections.ObservableCollections;
import org.jdesktop.observablecollections.ObservableList;
import server.ServerUtils;

import java.util.ArrayList;

public class Chat {
    static ObservableCollections.ObservableListHelper<String> chatModel = ObservableCollections.observableListHelper(new ArrayList<String>());
    static ObservableList<String> chatListModel = chatModel.getObservableList();

    public static ObservableList<String> getChatListModel() {
        return chatListModel;
    }

    public static void writeText(String client, String text) {
        String msg = String.format("%1$15s %2$s", client + ">", text);
        while (chatListModel.size() > 100) {
            chatListModel.remove(100);
        }
        chatListModel.add(0, msg);
        ServerUtils.sendChatMessage(msg);
    }
}
