
package bombastic;

import java.util.function.BiConsumer;

import bombastic.game.Game;
import bombastic.menu.mainmenu.MainMenuController;
import bombastic.menu.settingsmenu.SettingsController;
import bombastic.menu.chatmenu.ChatController;

import javafx.application.Platform;
import javafx.scene.Scene;

public class Controller {

    private final Model model;
    private final BiConsumer<Scene, String> sceneChanger;

    private final MainMenuController mainMenuController;
    private final SettingsController settingsController;
    private final ChatController chatController;

    private final Game game;
    private final Scene gameScene;

    public Controller(BiConsumer<Scene, String> sceneChanger) {

        this.sceneChanger = sceneChanger;
        model = new Model();

        // Create the game view.
        game = new Game();
        gameScene = new Scene(game, 600, 400);

        // Create the individual menu controllers.
        mainMenuController = new MainMenuController(
                model,
                this::showGame,
                this::showChatMenu,
                this::showSettingsMenu,
                Platform::exit
        );

        settingsController = new SettingsController(
                model,
                this::showMainMenu
        );

        chatController = new ChatController(
                model,
                this::showMainMenu
        );

        // Connect the game navigation.
        game.setOnMainMenu(this::showMainMenu);

        // Display the main menu.
        showMainMenu();
    }

    private void showMainMenu() {
        sceneChanger.accept(
                mainMenuController.getScene(),
                "Bombastic - Main Menu"
        );
    }

    private void showSettingsMenu() {
        sceneChanger.accept(
                settingsController.getScene(),
                "Bombastic - Settings"
        );
    }

    private void showChatMenu() {
        sceneChanger.accept(
                chatController.getScene(),
                "Bombastic - Chat"
        );
    }

    private void showGame() {
        sceneChanger.accept(
                gameScene,
                "Bombastic - Game"
        );
    }
}
