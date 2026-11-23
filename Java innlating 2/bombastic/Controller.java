
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

        // Get menus from View.
        mainMenu = view.getMainMenu();
        settingsMenu = view.getSettingsMenu();
        chatMenu = view.getChatMenu();
        game = view.getGame();

        // Get scenes from View.
        mainScene = view.getMainScene();
        settingsScene = view.getSettingsScene();
        chatScene = view.getChatScene();
        gameScene = view.getGameScene();

        // Synchronize backgrounds across all menus.
        mainMenu.selectedBackgroundProperty().bind(
            model.backgroundProperty()
        );

        settingsMenu.selectedBackgroundProperty().bind(
            model.backgroundProperty()
        );

        chatMenu.selectedBackgroundProperty().bind(
            model.backgroundProperty()
        );

        // Display existing chat messages.
        chatMenu.getMessagesArea().setText(
            String.join("\n", model.getChatMessages()) + "\n"
        );

        // Update chat whenever a new message is added.
        model.getChatMessages().addListener(
            (ListChangeListener<String>) change -> {
                chatMenu.getMessagesArea().setText(
                    String.join("\n", model.getChatMessages()) + "\n"
                );
            }
        );

        // Connect online users to the chat.
        chatMenu.getUsersList().setItems(model.getOnlineUsers());

        setupMainMenu();
        setupSettingsMenu();
        setupChatMenu();
        setupGame();

        showMainMenu();
    }

    // Main menu button actions.
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

    // Settings menu button actions.
    private void setupSettingsMenu() {

        settingsMenu.getMainMenuButton().setOnAction(event -> {
            showMainMenu();
        });

        // Background selection.
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

        // Arena selection.
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

    // Chat menu button actions.
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

    // Game menu button actions.
    private void setupGame() {

        game.getMainMenuButton().setOnAction(event -> {
            showMainMenu();
        });
    }

    // Changes screens while keeping the same window dimensions.
    private void changeScene(Scene scene, String title) {

        stage.setMaximized(false);
        stage.setFullScreen(false);

        stage.setScene(scene);
        stage.setTitle(title);

        stage.setWidth(View.WINDOW_WIDTH);
        stage.setHeight(View.WINDOW_HEIGHT);

        stage.centerOnScreen();
        stage.show();
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
