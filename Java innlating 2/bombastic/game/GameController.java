
package bombastic.game;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

public class GameController {

    private final GameModel gameModel;
    private final GameView gameView;

    private boolean player1BombPressed = false;
    private boolean player2BombPressed = false;

    public GameController(GameModel gameModel, GameView gameView) {
        this.gameModel = gameModel;
        this.gameView = gameView;
    }

    public void initializeKeyBindings(Scene scene) {

        scene.setOnKeyPressed(event -> {
            event.consume();

            KeyCode key = event.getCode();

            switch (key) {
                // Player 1 - WASD and Space
                case W -> gameModel.getPlayer1().setMovingUp(true);
                case S -> gameModel.getPlayer1().setMovingDown(true);
                case A -> gameModel.getPlayer1().setMovingLeft(true);
                case D -> gameModel.getPlayer1().setMovingRight(true);

                case SPACE -> {
                    if (!player1BombPressed) {
                        gameModel.placeBomb(gameModel.getPlayer1());
                        player1BombPressed = true;
                    }
                }

                // Player 2 - Arrow keys and Enter
                case UP -> gameModel.getPlayer2().setMovingUp(true);
                case DOWN -> gameModel.getPlayer2().setMovingDown(true);
                case LEFT -> gameModel.getPlayer2().setMovingLeft(true);
                case RIGHT -> gameModel.getPlayer2().setMovingRight(true);

                case ENTER -> {
                    if (!player2BombPressed) {
                        gameModel.placeBomb(gameModel.getPlayer2());
                        player2BombPressed = true;
                    }
                }

                default -> {
                }
            }
        });

        scene.setOnKeyReleased(event -> {
            event.consume();

            KeyCode key = event.getCode();

            switch (key) {
                case W -> gameModel.getPlayer1().setMovingUp(false);
                case S -> gameModel.getPlayer1().setMovingDown(false);
                case A -> gameModel.getPlayer1().setMovingLeft(false);
                case D -> gameModel.getPlayer1().setMovingRight(false);

                case SPACE -> player1BombPressed = false;

                case UP -> gameModel.getPlayer2().setMovingUp(false);
                case DOWN -> gameModel.getPlayer2().setMovingDown(false);
                case LEFT -> gameModel.getPlayer2().setMovingLeft(false);
                case RIGHT -> gameModel.getPlayer2().setMovingRight(false);

                case ENTER -> player2BombPressed = false;

                default -> {
                }
            }
        });

        gameView.setFocusTraversable(true);
        gameView.requestFocus();
    }
}
