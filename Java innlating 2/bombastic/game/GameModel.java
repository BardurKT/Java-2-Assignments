
package bombastic.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

import bombastic.game.arena.Arena;
import bombastic.game.entities.Bomb;
import bombastic.game.entities.Entity;
import bombastic.game.entities.Explosion;
import bombastic.game.entities.Player;
import bombastic.game.entities.Wall;
import bombastic.game.entities.powerups.CapacityPowerUp;
import bombastic.game.entities.powerups.LifePowerUp;
import bombastic.game.entities.powerups.PowerUp;
import bombastic.game.entities.powerups.RangePowerUp;
import bombastic.game.entities.powerups.SpeedPowerUp;
import javafx.scene.image.Image;

public class GameModel {

    private static final double PLAYER_SIZE = 32;
    private static final double PLAYER_SPEED = 150;
    private static final double POWERUP_SIZE = 32;
    private static final double POWERUP_DROP_CHANCE = 0.30;

    private final Arena arena;

    private final Player player1;
    private final Player player2;

    private final List<Bomb> bombs = new ArrayList<>();
    private final List<Explosion> explosions = new ArrayList<>();
    private final List<PowerUp> powerUps = new ArrayList<>();

    private final Image bombImage;
    private final Image explosionImage;

    private final Image capacityImage;
    private final Image rangeImage;
    private final Image speedImage;
    private final Image lifeImage;

    private final Random random = new Random();

    private boolean gameOver = false;
    private String winner = "";

    public GameModel() {

        arena = new Arena();

        Image player1Image = new Image(
                getClass().getResourceAsStream("/assets/players/blue_down_0.png"));

        Image player2Image = new Image(
                getClass().getResourceAsStream("/assets/players/red_down_0.png"));

        bombImage = new Image(
                getClass().getResourceAsStream("/assets/bombs/bomb_0.png"));

        explosionImage = new Image(
                getClass().getResourceAsStream("/assets/explosions/explosion_3.png"));

        capacityImage = new Image(
                getClass().getResourceAsStream("/assets/powerups/capacity.png"));

        rangeImage = new Image(
                getClass().getResourceAsStream("/assets/powerups/range.png"));

        speedImage = new Image(
                getClass().getResourceAsStream("/assets/powerups/speed.png"));

        lifeImage = new Image(
                getClass().getResourceAsStream("/assets/powerups/life.png"));

        player1 = new Player(
                "Player 1", 48, 48, PLAYER_SIZE, PLAYER_SPEED, player1Image);

        player2 = new Player(
                "Player 2",
                (15 - 2) * 48, (13 - 2) * 48,
                PLAYER_SIZE, PLAYER_SPEED, player2Image);
    }

    public Arena getArena() {
        return arena;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public List<Bomb> getBombs() {
        return bombs;
    }

    public List<Explosion> getExplosions() {
        return explosions;
    }

    public List<PowerUp> getPowerUps() {
        return powerUps;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public String getWinner() {
        return winner;
    }

    public void placeBomb(Player player) {

        if (gameOver || !player.canPlaceBomb()) {
            return;
        }

        double tileSize = Arena.TILE_SIZE;

        double tileX = Math.floor(
                (player.getX() + player.getWidth() / 2) / tileSize) * tileSize;

        double tileY = Math.floor(
                (player.getY() + player.getHeight() / 2) / tileSize) * tileSize;

        for (Bomb bomb : bombs) {
            if (bomb.getX() == tileX && bomb.getY() == tileY) {
                return;
            }
        }

        Bomb bomb = new Bomb(
                tileX, tileY, tileSize,
                bombImage, player, player.getExplosionRange());

        bombs.add(bomb);
        player.placeBomb();
    }

    public void update(double dt) {

        if (gameOver) {
            return;
        }

        // Arena walls and active bombs block player movement.
        List<Entity> obstacles = new ArrayList<>(arena.getEntities());
        obstacles.addAll(bombs);

        // Update both players concurrently.
        CompletableFuture<Void> player1Update =
                CompletableFuture.runAsync(() -> player1.update(dt, obstacles));

        CompletableFuture<Void> player2Update =
                CompletableFuture.runAsync(() -> player2.update(dt, obstacles));

        CompletableFuture.allOf(player1Update, player2Update).join();

        checkPowerUpCollisions();

        for (Bomb bomb : bombs) {
            bomb.update(dt);
        }

        processDetonations();

        checkExplosionCollisions();

        for (Explosion explosion : explosions) {
            explosion.update(dt);
        }

        explosions.removeIf(Explosion::isFinished);

        checkGameOver();
    }

    private void checkPowerUpCollisions() {

        for (PowerUp powerUp : powerUps) {

            if (!powerUp.isOccupying()) {
                continue;
            }

            if (overlaps(player1, powerUp)) {
                powerUp.interact(player1);
            }

            if (overlaps(player2, powerUp)) {
                powerUp.interact(player2);
            }
        }

        powerUps.removeIf(powerUp -> !powerUp.isOccupying());
    }

    private void spawnPowerUp(double tileX, double tileY) {

        if (random.nextDouble() >= POWERUP_DROP_CHANCE) {
            return;
        }

        double offset = (Arena.TILE_SIZE - POWERUP_SIZE) / 2;

        double x = tileX + offset;
        double y = tileY + offset;

        PowerUp powerUp;

        // Randomly choose one of the four power-up types.
        switch (random.nextInt(4)) {

            case 0 -> powerUp = new CapacityPowerUp(
                    x, y, POWERUP_SIZE, capacityImage);

            case 1 -> powerUp = new RangePowerUp(
                    x, y, POWERUP_SIZE, rangeImage);

            case 2 -> powerUp = new SpeedPowerUp(
                    x, y, POWERUP_SIZE, speedImage);

            default -> powerUp = new LifePowerUp(
                    x, y, POWERUP_SIZE, lifeImage);
        }

        powerUps.add(powerUp);
    }

    private void processDetonations() {

        boolean chainReaction;

        do {
            chainReaction = false;

            for (Bomb bomb : new ArrayList<>(bombs)) {

                if (!bomb.hasExploded()) {
                    continue;
                }

                createExplosion(bomb);

                bomb.getOwner().bombExploded();
                bombs.remove(bomb);

                chainReaction = true;
            }

        } while (chainReaction);
    }

    private void createExplosion(Bomb bomb) {

        double x = bomb.getX();
        double y = bomb.getY();

        addExplosion(x, y);

        int range = bomb.getExplosionRange();
        double tileSize = Arena.TILE_SIZE;

        spreadExplosion(x, y, 0, -1, range, tileSize);
        spreadExplosion(x, y, 0, 1, range, tileSize);
        spreadExplosion(x, y, -1, 0, range, tileSize);
        spreadExplosion(x, y, 1, 0, range, tileSize);
    }

    private void spreadExplosion(
            double startX, double startY,
            int directionX, int directionY,
            int range, double tileSize) {

        for (int i = 1; i <= range; i++) {

            double tileX = startX + directionX * tileSize * i;
            double tileY = startY + directionY * tileSize * i;

            if (tileX < 0 || tileY < 0
                    || tileX >= Arena.COLUMNS * tileSize
                    || tileY >= Arena.ROWS * tileSize) {
                break;
            }

            Wall hitWall = null;

            // Find a wall occupying the current tile.
            for (Entity entity : arena.getEntities()) {

                if (!(entity instanceof Wall wall)) {
                    continue;
                }

                if (wall.isOccupying()
                        && wall.getX() == tileX
                        && wall.getY() == tileY) {
                    hitWall = wall;
                    break;
                }
            }

            if (hitWall != null) {

                // Only destructible walls receive an explosion graphic.
                if (hitWall.isDestructible()) {
                    Explosion explosion = addExplosion(tileX, tileY);
                    explosion.interact(hitWall);

                    spawnPowerUp(tileX, tileY);
                }

                // Both wall types stop the explosion.
                break;
            }

            // Create an explosion at the current tile.
            Explosion explosion = addExplosion(tileX, tileY);

            // Trigger bombs caught in the explosion.
            for (Bomb otherBomb : bombs) {

                if (otherBomb.isOccupying()
                        && otherBomb.getX() == tileX
                        && otherBomb.getY() == tileY) {

                    explosion.interact(otherBomb);
                }
            }
        }
    }

    private Explosion addExplosion(double x, double y) {

        Explosion explosion = new Explosion(
                x, y, Arena.TILE_SIZE, explosionImage);

        explosions.add(explosion);

        return explosion;
    }

    private void checkExplosionCollisions() {

        // Detect each player's explosion collision concurrently.
        CompletableFuture<Explosion> player1Hit =
                CompletableFuture.supplyAsync(
                        () -> findExplosionHittingPlayer(player1));

        CompletableFuture<Explosion> player2Hit =
                CompletableFuture.supplyAsync(
                        () -> findExplosionHittingPlayer(player2));

        Explosion explosionForPlayer1 = player1Hit.join();
        Explosion explosionForPlayer2 = player2Hit.join();

        // Process interactions safely on the game thread.
        if (explosionForPlayer1 != null) {
            explosionForPlayer1.interact(player1);
        }

        if (explosionForPlayer2 != null) {
            explosionForPlayer2.interact(player2);
        }
    }

    private Explosion findExplosionHittingPlayer(Player player) {

        for (Explosion explosion : explosions) {

            if (!explosion.isOccupying()) {
                continue;
            }

            if (overlaps(player, explosion)) {
                return explosion;
            }
        }

        return null;
    }

    private boolean overlaps(Entity a, Entity b) {

        return a.getX() < b.getX() + b.getWidth()
                && a.getX() + a.getWidth() > b.getX()
                && a.getY() < b.getY() + b.getHeight()
                && a.getY() + a.getHeight() > b.getY();
    }

    private void checkGameOver() {

        if (player1.isDead() || player2.isDead()) {

            gameOver = true;

            if (player1.isDead() && player2.isDead()) {
                winner = "Draw";
            } else if (player1.isDead()) {
                winner = "Player 2 Wins!";
            } else {
                winner = "Player 1 Wins!";
            }
        }
    }
}
