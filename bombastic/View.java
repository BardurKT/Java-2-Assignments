
package bombastic;

import bombastic.game.Game;
import bombastic.menu.mainmenu.MainMenu;
import bombastic.menu.settingsmenu.SettingsMenu;
import bombastic.menu.chatmenu.ChatMenu;

import javafx.scene.Scene;

public class View {

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

        mainScene = new Scene(mainMenu, 600, 400);
        settingsScene = new Scene(settingsMenu, 600, 400);
        chatScene = new Scene(chatMenu, 600, 400);
        gameScene = new Scene(game, 600, 400);
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
