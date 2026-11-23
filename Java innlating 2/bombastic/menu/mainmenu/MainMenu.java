
package bombastic.menu.mainmenu;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.image.Image;

public class MainMenu extends VBox {

    private Button startButton;
    private Button chatButton;
    private Button settingsButton;
    private Button exitButton;

    private StringProperty background =
            new SimpleStringProperty("bg1.png");

    public MainMenu() {

        startButton = new Button("Start Game");
        chatButton = new Button("Chat");
        settingsButton = new Button("Settings");
        exitButton = new Button("Exit");

        // Make all buttons the same width
        double buttonWidth = 160;

        startButton.setPrefWidth(buttonWidth);
        chatButton.setPrefWidth(buttonWidth);
        settingsButton.setPrefWidth(buttonWidth);
        exitButton.setPrefWidth(buttonWidth);

        // Center buttons and space them evenly
        setAlignment(Pos.CENTER);
        setSpacing(15);

        getChildren().addAll(
            startButton,
            chatButton,
            settingsButton,
            exitButton
        );

        background.addListener((observable, oldValue, newValue) -> {
            setBackgroundImage(newValue);
        });

        setBackgroundImage(background.get());
    }

    public StringProperty selectedBackgroundProperty() {
        return background;
    }

    public void setBackgroundImage(String imageName) {

        Image image = new Image(
            "file:resources/images/" + imageName
        );

        BackgroundImage backgroundImage = new BackgroundImage(
            image,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            new BackgroundSize(
                1, 1, true, true, false, true
            )
        );

        setBackground(new Background(backgroundImage));
    }

    public Button getStartButton() {
        return startButton;
    }

    public Button getChatButton() {
        return chatButton;
    }

    public Button getSettingsButton() {
        return settingsButton;
    }

    public Button getExitButton() {
        return exitButton;
    }
}
