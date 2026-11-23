
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

        // Shared application model.
        model = new Model();

        // Create the game using the selected arena.
        game = new Game(model::getArena);
        gameScene = new Scene(game, 780, 720);

        // Create the menu controllers.
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

        // Connect game navigation.
        game.setOnMainMenu(this::showMainMenu);

        // Display the main menu.
        showMainMenu();
    }

    private void showMainMenu() {

        // Make sure the game is stopped when leaving it.
        game.stopGame();

        sceneChanger.accept(
                mainMenuController.getScene(),
                "Bombastic - Main Menu"
        );
    }

    private void showSettingsMenu() {

        game.stopGame();

        sceneChanger.accept(
                settingsController.getScene(),
                "Bombastic - Settings"
        );
    }

    private void showChatMenu() {

        game.stopGame();

        sceneChanger.accept(
                chatController.getScene(),
                "Bombastic - Chat"
        );
    }

    private void showGame() {

        // Always create a fresh game using the selected arena.
        game.startNewGame();

        sceneChanger.accept(
                gameScene,
                "Bombastic - Game"
        );
    }
}
