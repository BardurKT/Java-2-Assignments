
package bombastic.menu.settingsmenu;

import java.util.function.Consumer;

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

    private final MenuButton backgroundMenu;
    private final MenuButton arenaMenu;
    private final Button mainMenuButton;

    private final StringProperty background =
            new SimpleStringProperty("bg1.png");

    public SettingsMenu() {

        // Background selection.
        backgroundMenu = new MenuButton("Background: Background 1");

        MenuItem bg1 = new MenuItem("Background 1");
        MenuItem bg2 = new MenuItem("Background 2");
        MenuItem bg3 = new MenuItem("Background 3");

        backgroundMenu.getItems().addAll(bg1, bg2, bg3);

        // Arena selection.
        arenaMenu = new MenuButton("Arena: Arena 1");

        MenuItem arena1 = new MenuItem("Arena 1");
        MenuItem arena2 = new MenuItem("Arena 2");
        MenuItem arena3 = new MenuItem("Arena 3");

        arenaMenu.getItems().addAll(arena1, arena2, arena3);

        // Main menu button.
        mainMenuButton = new Button("Main Menu");

        double buttonWidth = 180;

        backgroundMenu.setPrefWidth(buttonWidth);
        arenaMenu.setPrefWidth(buttonWidth);
        mainMenuButton.setPrefWidth(buttonWidth);

        setAlignment(Pos.CENTER);
        setSpacing(15);

        getChildren().addAll(
                backgroundMenu,
                arenaMenu,
                mainMenuButton
        );

        background.addListener((observable, oldValue, newValue) ->
                setBackgroundImage(newValue)
        );

        setBackgroundImage(background.get());
    }

    // The controller supplies what happens when a background is selected.
    public void setOnBackgroundSelected(Consumer<String> action) {

        backgroundMenu.getItems().get(0).setOnAction(event -> {
            backgroundMenu.setText("Background: Background 1");
            action.accept("bg1.png");
        });

        backgroundMenu.getItems().get(1).setOnAction(event -> {
            backgroundMenu.setText("Background: Background 2");
            action.accept("bg2.png");
        });

        backgroundMenu.getItems().get(2).setOnAction(event -> {
            backgroundMenu.setText("Background: Background 3");
            action.accept("bg3.png");
        });
    }

    // The controller supplies what happens when an arena is selected.
    public void setOnArenaSelected(Consumer<String> action) {

        arenaMenu.getItems().get(0).setOnAction(event -> {
            arenaMenu.setText("Arena: Arena 1");
            action.accept("Arena 1");
        });

        arenaMenu.getItems().get(1).setOnAction(event -> {
            arenaMenu.setText("Arena: Arena 2");
            action.accept("Arena 2");
        });

        arenaMenu.getItems().get(2).setOnAction(event -> {
            arenaMenu.setText("Arena: Arena 3");
            action.accept("Arena 3");
        });
    }

    public void setOnMainMenu(Runnable action) {
        mainMenuButton.setOnAction(event -> action.run());
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
}
