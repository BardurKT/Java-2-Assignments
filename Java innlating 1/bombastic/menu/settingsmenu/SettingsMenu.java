
package bombastic.menu.settingsmenu;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.image.Image;

public class SettingsMenu extends VBox {

    private MenuButton backgroundMenu;
    private MenuButton arenaMenu;
    private Button mainMenuButton;

    private StringProperty background =
            new SimpleStringProperty("bg1.png");

    public SettingsMenu() {

        // Background selection
        backgroundMenu = new MenuButton("Background: Background 1");

        MenuItem bg1 = new MenuItem("Background 1");
        MenuItem bg2 = new MenuItem("Background 2");
        MenuItem bg3 = new MenuItem("Background 3");

        backgroundMenu.getItems().addAll(bg1, bg2, bg3);

        // Arena selection
        arenaMenu = new MenuButton("Arena: Arena 1");

        MenuItem arena1 = new MenuItem("Arena 1");
        MenuItem arena2 = new MenuItem("Arena 2");
        MenuItem arena3 = new MenuItem("Arena 3");

        arenaMenu.getItems().addAll(arena1, arena2, arena3);

        // Main menu button
        mainMenuButton = new Button("Main Menu");

        // Make all controls the same width
        double buttonWidth = 180;

        backgroundMenu.setPrefWidth(buttonWidth);
        arenaMenu.setPrefWidth(buttonWidth);
        mainMenuButton.setPrefWidth(buttonWidth);

        // Center controls and space them evenly
        setAlignment(Pos.CENTER);
        setSpacing(15);

        getChildren().addAll(
            backgroundMenu,
            arenaMenu,
            mainMenuButton
        );

        // Update background when the property changes
        background.addListener((observable, oldValue, newValue) -> {
            setBackgroundImage(newValue);
        });

        // Set initial background
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

    public MenuButton getBackgroundMenu() {
        return backgroundMenu;
    }

    public MenuButton getArenaMenu() {
        return arenaMenu;
    }

    public Button getMainMenuButton() {
        return mainMenuButton;
    }
}
