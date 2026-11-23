
package bombastic.menu.mainmenu;

import bombastic.Model;
import javafx.scene.Scene;

public class MainMenuController {

    private final MainMenu view;
    private final Scene scene;

    public MainMenuController(
            Model model,
            Runnable onStart,
            Runnable onChat,
            Runnable onSettings,
            Runnable onExit) {

        view = new MainMenu();
        scene = new Scene(view, 600, 400);

        // Connect the background to the application model.
        view.selectedBackgroundProperty().bind(
                model.backgroundProperty()
        );

        // Connect the menu actions.
        view.setOnStart(onStart);
        view.setOnChat(onChat);
        view.setOnSettings(onSettings);
        view.setOnExit(onExit);
    }

    public Scene getScene() {
        return scene;
    }
}
