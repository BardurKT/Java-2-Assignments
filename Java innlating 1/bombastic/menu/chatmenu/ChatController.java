
package bombastic.menu.chatmenu;

import bombastic.Model;
import javafx.scene.Scene;

public class ChatController {

    private final ChatModel model;
    private final ChatMenu view;
    private final Scene scene;

    public ChatController(Model applicationModel, Runnable onMainMenu) {

        model = new ChatModel();
        view = new ChatMenu();
        scene = new Scene(view, 600, 400);

        // Connect the background to the application model.
        view.selectedBackgroundProperty().bind(
                applicationModel.backgroundProperty()
        );

        // Connect model data to the view.
        view.setMessages(model.getMessages());
        view.setOnlineUsers(model.getOnlineUsers());

        // Connect user actions.
        view.setOnSend(this::sendMessage);
        view.setOnMainMenu(onMainMenu);
    }

    private void sendMessage(String message) {
        model.addMessage("You: " + message);
    }

    public Scene getScene() {
        return scene;
    }
}
