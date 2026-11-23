
package bombastic.game;

import bombastic.game.entities.Bomb;
import bombastic.game.entities.Explosion;
import bombastic.game.entities.Player;
import bombastic.game.entities.powerups.PowerUp;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GameView extends Canvas {

    public GameView() {
        super(720, 624);
    }

    public void render(GameModel gameModel) {

        GraphicsContext gc = getGraphicsContext2D();

        // Clear the canvas.
        gc.clearRect(0, 0, getWidth(), getHeight());

        // Draw the arena.
        gameModel.getArena().render(gc);

        // Draw power-ups on the arena.
        for (PowerUp powerUp : gameModel.getPowerUps()) {
            powerUp.render(gc);
        }

        // Draw bombs.
        for (Bomb bomb : gameModel.getBombs()) {
            bomb.render(gc);
        }

        // Draw explosions.
        for (Explosion explosion : gameModel.getExplosions()) {
            explosion.render(gc);
        }

        // Draw players.
        gameModel.getPlayer1().render(gc);
        gameModel.getPlayer2().render(gc);

        // Draw player information.
        drawPlayerInfo(gc, gameModel.getPlayer1(), 10, 20, Color.BLUE);
        drawPlayerInfo(gc, gameModel.getPlayer2(), 610, 20, Color.RED);

        // Draw game-over message.
        if (gameModel.isGameOver()) {
            drawGameOver(gc, gameModel.getWinner());
        }
    }

    private void drawPlayerInfo(
            GraphicsContext gc,
            Player player,
            double x,
            double y,
            Color color) {

        gc.setFill(color);
        gc.setFont(Font.font("Arial", 16));

        gc.fillText(
                player.getPlayerName() + ": " + player.getLives() + " lives",
                x, y);
    }

    private void drawGameOver(GraphicsContext gc, String winner) {

        gc.setFill(Color.color(0, 0, 0, 0.75));
        gc.fillRect(0, 0, getWidth(), getHeight());

        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Arial", 36));

        gc.fillText(
                "GAME OVER",
                getWidth() / 2 - 115,
                getHeight() / 2 - 20);

        gc.setFont(Font.font("Arial", 24));

        gc.fillText(
                winner,
                getWidth() / 2 - 100,
                getHeight() / 2 + 25);
    }
}
