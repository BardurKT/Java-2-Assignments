
package bombastic;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Model {

    private final StringProperty background =
            new SimpleStringProperty("bg1.png");

    private final StringProperty arena =
            new SimpleStringProperty("Arena 1");

    private final ObservableList<String> chatMessages =
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

    public StringProperty backgroundProperty() {
        return background;
    }

    public String getBackground() {
        return background.get();
    }

    public void setBackground(String background) {
        this.background.set(background);
    }

    public StringProperty arenaProperty() {
        return arena;
    }

    public String getArena() {
        return arena.get();
    }

    public void setArena(String arena) {
        this.arena.set(arena);
    }

    public ObservableList<String> getChatMessages() {
        return chatMessages;
    }

    public void addChatMessage(String message) {
        chatMessages.add(message);
    }

    public ObservableList<String> getOnlineUsers() {
        return onlineUsers;
    }
}
