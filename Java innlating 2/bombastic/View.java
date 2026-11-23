
package bombastic;

import bombastic.game.Game;
import bombastic.menu.mainmenu.MainMenu;
import bombastic.menu.settingsmenu.SettingsMenu;
import bombastic.menu.chatmenu.ChatMenu;

import javafx.scene.Scene;

public class View {

    // Shared resolution for every screen.
    public static final double WINDOW_WIDTH = 780;
    public static final double WINDOW_HEIGHT = 720;

    private MainMenu mainMenu;
    private SettingsMenu settingsMenu;
    private ChatMenu chatMenu;
    private Game game;

    private Scene mainScene;
    private Scene settingsScene;
    private Scene chatScene;
    private Scene gameScene;

    public View() {

        mainMenu = new MainMenu();
        settingsMenu = new SettingsMenu();
        chatMenu = new ChatMenu();
        game = new Game();

        // All scenes use the same resolution.
        mainScene = new Scene(mainMenu, WINDOW_WIDTH, WINDOW_HEIGHT);
        settingsScene = new Scene(settingsMenu, WINDOW_WIDTH, WINDOW_HEIGHT);
        chatScene = new Scene(chatMenu, WINDOW_WIDTH, WINDOW_HEIGHT);
        gameScene = new Scene(game, WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    public MainMenu getMainMenu() {
        return mainMenu;
    }

    public SettingsMenu getSettingsMenu() {
        return settingsMenu;
    }

    public ChatMenu getChatMenu() {
        return chatMenu;
    }

    public Game getGame() {
        return game;
    }

    public Scene getMainScene() {
        return mainScene;
    }

    public Scene getSettingsScene() {
        return settingsScene;
    }

    public Scene getChatScene() {
        return chatScene;
    }

    public Scene getGameScene() {
        return gameScene;
    }
}
