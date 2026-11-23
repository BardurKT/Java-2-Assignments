
package bombastic.menu.settingsmenu;

import bombastic.Model;
import javafx.scene.Scene;

public class SettingsController {

    private final SettingsMenu view;
    private final Scene scene;

    public SettingsController(Model model, Runnable onMainMenu) {

        view = new SettingsMenu();

        scene = new Scene(view, 600, 400);

        // Bind the settings menu background to the shared model.
        view.selectedBackgroundProperty().bind(
                model.backgroundProperty()
        );

        // Send background selections to the model.
        view.setOnBackgroundSelected(model::setBackground);

        // Send arena selections to the model.
        view.setOnArenaSelected(model::setArena);

        // Handle navigation back to the main menu.
        view.setOnMainMenu(onMainMenu);
    }

    public Scene getScene() {
        return scene;
    }
}
