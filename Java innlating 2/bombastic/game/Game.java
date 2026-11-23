
package bombastic.game;

import java.util.function.Supplier;

import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Game extends VBox {

    private final Button mainMenuButton;
    private final Button playAgainButton;

    private GameModel gameModel;
    private final GameView gameView;
    private GameController gameController;

    private final Supplier<String> arenaProvider;

    private final AnimationTimer gameLoop;
    private long lastTime = 0;

    public Game(Supplier<String> arenaProvider) {

        this.arenaProvider = arenaProvider;

        gameView = new GameView();

        mainMenuButton = new Button("Main Menu");
        playAgainButton = new Button("Play Again");

        playAgainButton.setVisible(false);
        playAgainButton.setManaged(false);

        HBox buttonBar = new HBox(10);
        buttonBar.setAlignment(Pos.CENTER);
        buttonBar.getChildren().addAll(
                mainMenuButton,
                playAgainButton
        );

        setAlignment(Pos.CENTER);
        setSpacing(10);
        setStyle("-fx-background-color: #222222;");

        getChildren().addAll(buttonBar, gameView);

        // Create the initial game round.
        createNewRound();

        // Restart the game when Play Again is clicked.
        playAgainButton.setOnAction(event -> restartGame());

        // Create the game loop.
        gameLoop = new AnimationTimer() {

            @Override
            public void handle(long currentTime) {

                if (lastTime == 0) {
                    lastTime = currentTime;
                    return;
                }

                double dt = (currentTime - lastTime)
                        / 1_000_000_000.0;

                lastTime = currentTime;

                gameModel.update(dt);
                gameView.render(gameModel);

                // Show the restart button when the game ends.
                if (gameModel.isGameOver()) {
                    playAgainButton.setVisible(true);
                    playAgainButton.setManaged(true);
                }
            }
        };
    }

    // Create a completely fresh round.
    private void createNewRound() {

        gameModel = new GameModel(arenaProvider.get());

        gameController = new GameController(
                gameModel,
                gameView
        );

        gameView.render(gameModel);
    }

    // Start a new game using the currently selected arena.
    public void startNewGame() {

        stopGame();

        createNewRound();

        playAgainButton.setVisible(false);
        playAgainButton.setManaged(false);

        lastTime = 0;

        Scene scene = getScene();

        if (scene != null) {
            initializeGameScene(scene);
        }

        gameView.requestFocus();

        gameLoop.start();
    }

    // Stop the game loop.
    public void stopGame() {

        gameLoop.stop();
        lastTime = 0;
    }

    // Play Again creates a completely fresh round.
    private void restartGame() {
        startNewGame();
    }

    // Connect keyboard controls to the game scene.
    private void initializeGameScene(Scene scene) {
        gameController.initializeKeyBindings(scene);
    }

    // Handle navigation back to the main menu.
    public void setOnMainMenu(Runnable action) {

        mainMenuButton.setOnAction(event -> {

            stopGame();

            action.run();
        });
    }
}
