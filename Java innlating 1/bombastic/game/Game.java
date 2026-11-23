
package bombastic.game;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Game extends VBox {

    private final Button mainMenuButton;

    public Game() {

        Label title = new Label("Bombastic - Game");

        mainMenuButton = new Button("Main Menu");

        setSpacing(20);

        getChildren().addAll(
                title,
                mainMenuButton
        );
    }

    // The controller supplies the action for the button.
    public void setOnMainMenu(Runnable action) {
        mainMenuButton.setOnAction(event -> action.run());
    }
}
