
package bombastic;

import bombastic.game.Game;
import bombastic.menu.mainmenu.MainMenu;
import bombastic.menu.settingsmenu.SettingsMenu;
import bombastic.menu.chatmenu.ChatMenu;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Controller {

    private Model model;
    private View view;

    private MainMenu mainMenu;
    private SettingsMenu settingsMenu;
    private ChatMenu chatMenu;
    private Game game;

    private Stage stage;

    private Scene mainScene;
    private Scene settingsScene;
    private Scene chatScene;
    private Scene gameScene;

    public Controller(Stage stage) {

        this.stage = stage;

        model = new Model();
        view = new View();

        // Get menus from View
        mainMenu = view.getMainMenu();
        settingsMenu = view.getSettingsMenu();
        chatMenu = view.getChatMenu();
        game = view.getGame();

        // Get scenes from View
        mainScene = view.getMainScene();
        settingsScene = view.getSettingsScene();
        chatScene = view.getChatScene();
        gameScene = view.getGameScene();

        // Bind menu backgrounds to the Model
        mainMenu.selectedBackgroundProperty().bind(
            model.backgroundProperty()
        );

        settingsMenu.selectedBackgroundProperty().bind(
            model.backgroundProperty()
        );

        chatMenu.selectedBackgroundProperty().bind(
            model.backgroundProperty()
        );

        // Connect chat messages to the Model
        chatMenu.getMessagesArea().setText(
            String.join("\n", model.getChatMessages()) + "\n"
        );

        model.getChatMessages().addListener(
            (ListChangeListener<String>) change -> {
                chatMenu.getMessagesArea().setText(
                    String.join("\n", model.getChatMessages()) + "\n"
                );
            }
        );

        // Connect online users to the Model
        chatMenu.getUsersList().setItems(model.getOnlineUsers());

        setupMainMenu();
        setupSettingsMenu();
        setupChatMenu();
        setupGame();

        showMainMenu();
    }

    private void setupMainMenu() {

        mainMenu.getStartButton().setOnAction(event -> {
            showGame();
        });

        mainMenu.getChatButton().setOnAction(event -> {
            showChatMenu();
        });

        mainMenu.getSettingsButton().setOnAction(event -> {
            showSettingsMenu();
        });

        mainMenu.getExitButton().setOnAction(event -> {
            Platform.exit();
        });
    }

    private void setupSettingsMenu() {

        settingsMenu.getMainMenuButton().setOnAction(event -> {
            showMainMenu();
        });

        // Background selection
        settingsMenu.getBackgroundMenu().getItems().get(0)
                .setOnAction(event -> {
                    model.setBackground("bg1.png");
                    settingsMenu.getBackgroundMenu().setText(
                        "Background: Background 1"
                    );
                });

        settingsMenu.getBackgroundMenu().getItems().get(1)
                .setOnAction(event -> {
                    model.setBackground("bg2.png");
                    settingsMenu.getBackgroundMenu().setText(
                        "Background: Background 2"
                    );
                });

        settingsMenu.getBackgroundMenu().getItems().get(2)
                .setOnAction(event -> {
                    model.setBackground("bg3.png");
                    settingsMenu.getBackgroundMenu().setText(
                        "Background: Background 3"
                    );
                });

        // Arena selection
        settingsMenu.getArenaMenu().getItems().get(0)
                .setOnAction(event -> {
                    model.setArena("Arena 1");
                    settingsMenu.getArenaMenu().setText("Arena: Arena 1");
                });

        settingsMenu.getArenaMenu().getItems().get(1)
                .setOnAction(event -> {
                    model.setArena("Arena 2");
                    settingsMenu.getArenaMenu().setText("Arena: Arena 2");
                });

        settingsMenu.getArenaMenu().getItems().get(2)
                .setOnAction(event -> {
                    model.setArena("Arena 3");
                    settingsMenu.getArenaMenu().setText("Arena: Arena 3");
                });
    }

    private void setupChatMenu() {

        chatMenu.getMainMenuButton().setOnAction(event -> {
            showMainMenu();
        });

        chatMenu.getSendButton().setOnAction(event -> {

            String message = chatMenu.getMessageInput().getText().trim();

            if (!message.isBlank()) {
                model.addChatMessage("You: " + message);
                chatMenu.getMessageInput().clear();
            }
        });
    }

    private void setupGame() {

        game.getMainMenuButton().setOnAction(event -> {
            showMainMenu();
        });
    }

    // Switch scenes while preserving fullscreen state
    private void changeScene(Scene scene, String title) {

        boolean wasFullScreen = stage.isFullScreen();

        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();

        if (wasFullScreen) {
            stage.setFullScreen(true);
        }
    }

    private void showMainMenu() {
        changeScene(mainScene, "Bombastic - Main Menu");
    }

    private void showSettingsMenu() {
        changeScene(settingsScene, "Bombastic - Settings");
    }

    private void showChatMenu() {
        changeScene(chatScene, "Bombastic - Chat");
    }

    private void showGame() {
        changeScene(gameScene, "Bombastic - Game");
    }
}
