
package bombastic.menu.settingsmenu;

import bombastic.Model;
import javafx.scene.Scene;

public class SettingsController {

    private final SettingsMenu view;
    private final Scene scene;

    public SettingsController(
            Model model,
            Runnable onMainMenu) {

        view = new SettingsMenu();
        scene = new Scene(view, 600, 400);

        // Connect the background to the application model
        view.selectedBackgroundProperty().bind(
                model.backgroundProperty()
        );

        // Connect settings actions to the model
        view.setOnBackgroundSelected(model::setBackground);
        view.setOnArenaSelected(model::setArena);

        // Connect navigation
        view.setOnMainMenu(onMainMenu);
    }

    public Scene getScene() {
        return scene;
    }
}
