
package bombastic.game;

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

    private AnimationTimer gameLoop;
    private long lastTime = 0;

    public Game() {

        // Create the game components.
        gameModel = new GameModel();
        gameView = new GameView();
        gameController = new GameController(gameModel, gameView);

        // Create the buttons.
        mainMenuButton = new Button("Main Menu");
        playAgainButton = new Button("Play Again");

        // Hide the restart button until the round ends.
        playAgainButton.setVisible(false);
        playAgainButton.setManaged(false);

        // Place both buttons in a horizontal layout.
        HBox buttonBar = new HBox(10);
        buttonBar.setAlignment(Pos.CENTER);
        buttonBar.getChildren().addAll(
                mainMenuButton,
                playAgainButton
        );

        // Arrange the game screen.
        setAlignment(Pos.CENTER);
        setSpacing(10);
        setStyle("-fx-background-color: #222222;");

        getChildren().addAll(
                buttonBar,
                gameView
        );

        // Restart the game when Play Again is clicked.
        playAgainButton.setOnAction(event -> restartGame());

        // Draw the initial game state.
        gameView.render(gameModel);

        // Create the game loop.
        gameLoop = new AnimationTimer() {

            @Override
            public void handle(long currentTime) {

                if (lastTime == 0) {
                    lastTime = currentTime;
                    return;
                }

                double dt = (currentTime - lastTime) / 1_000_000_000.0;
                lastTime = currentTime;

                gameModel.update(dt);
                gameView.render(gameModel);

                // Show the restart button when the round ends.
                if (gameModel.isGameOver()) {
                    playAgainButton.setVisible(true);
                    playAgainButton.setManaged(true);
                }
            }
        };

        // Start and stop the loop when the game scene changes.
        sceneProperty().addListener((observable, oldScene, newScene) -> {

            if (newScene != null) {
                initializeGameScene(newScene);
                gameLoop.start();
            } else {
                gameLoop.stop();
                lastTime = 0;
            }
        });
    }

    // Create a fresh round.
    private void restartGame() {

        gameModel = new GameModel();
        gameController = new GameController(gameModel, gameView);

        playAgainButton.setVisible(false);
        playAgainButton.setManaged(false);

        lastTime = 0;

        gameView.render(gameModel);

        Scene scene = getScene();

        if (scene != null) {
            initializeGameScene(scene);
        }

        gameView.requestFocus();
    }

    // Connect keyboard controls to the current game scene.
    private void initializeGameScene(Scene scene) {
        gameController.initializeKeyBindings(scene);
    }

    public Button getMainMenuButton() {
        return mainMenuButton;
    }
}
