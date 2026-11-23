package bombastic.game;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Game extends VBox {

    private Button mainMenuButton;

    public Game() {
        Label title = new Label("Bombastic - Game");

        mainMenuButton = new Button("Main Menu");

        setSpacing(20);

        getChildren().addAll(
            title,
            mainMenuButton
        );
    }

    public Button getMainMenuButton() {
        return mainMenuButton;
    }
}