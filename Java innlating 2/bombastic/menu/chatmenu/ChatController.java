
package bombastic.menu.chatmenu;

import bombastic.Model;
import javafx.collections.ListChangeListener;
import javafx.scene.Scene;

public class ChatController {

    private final ChatMenu view;
    private final Scene scene;
    private final Model model;

    public ChatController(Model model, Runnable onMainMenu) {

        this.model = model;

        view = new ChatMenu();
        scene = new Scene(view, 600, 400);

        // Bind the shared background setting.
        view.selectedBackgroundProperty()
                .bind(model.backgroundProperty());

        // Display existing messages.
        view.setMessages(model.getChatMessages());

        // Update the chat whenever a message is added.
        model.getChatMessages().addListener(
                (ListChangeListener<String>) change -> {
                    view.setMessages(model.getChatMessages());
                }
        );

        // Display online users.
        view.setOnlineUsers(model.getOnlineUsers());

        // Handle sending messages.
        view.setOnSend(this::sendMessage);

        // Handle navigation.
        view.setOnMainMenu(onMainMenu);
    }

    private void sendMessage(String message) {
        model.addChatMessage("You: " + message);
    }

    public Scene getScene() {
        return scene;
    }
}
