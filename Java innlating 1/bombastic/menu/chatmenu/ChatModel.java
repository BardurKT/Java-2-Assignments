
package bombastic.menu.chatmenu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ChatModel {

    private final ObservableList<String> messages =
            FXCollections.observableArrayList(
                    "Player1: Welcome to Bombastic!",
                    "Player2: Hello everyone!"
            );

    private final ObservableList<String> onlineUsers =
            FXCollections.observableArrayList(
                    "Player1",
                    "Player2",
                    "Player3"
            );

    public ObservableList<String> getMessages() {
        return messages;
    }

    public ObservableList<String> getOnlineUsers() {
        return onlineUsers;
    }

    public void addMessage(String message) {
        messages.add(message);
    }
}
