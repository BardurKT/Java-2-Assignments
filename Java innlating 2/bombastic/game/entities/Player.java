
package bombastic.game.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.List;

public class Player extends Entity {

    private final String playerName;

    private int lives = 3;
    private int maxBombs = 1;
    private int activeBombs = 0;
    private int explosionRange = 2;

    private double speed;
    private double invulnerabilityTimer = 0;

    private static final double INVULNERABILITY_DURATION = 1.0;

    private boolean movingUp;
    private boolean movingDown;
    private boolean movingLeft;
    private boolean movingRight;

    public Player(String playerName, double x, double y,
                  double size, double speed, Image image) {
        super(x, y, size, size, image);
        this.playerName = playerName;
        this.speed = speed;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getLives() {
        return lives;
    }

    public boolean isDead() {
        return lives <= 0;
    }

    public boolean isInvulnerable() {
        return invulnerabilityTimer > 0;
    }

    public boolean canPlaceBomb() {
        return activeBombs < maxBombs && !isDead();
    }

    public void placeBomb() {
        if (canPlaceBomb()) {
            activeBombs++;
        }
    }

    public void bombExploded() {
        if (activeBombs > 0) {
            activeBombs--;
        }
    }

    public void loseLife() {
        if (isDead() || isInvulnerable()) {
            return;
        }

        lives--;
        invulnerabilityTimer = INVULNERABILITY_DURATION;
    }

    public void addLife() {
        lives++;
    }

    public int getExplosionRange() {
        return explosionRange;
    }

    public void increaseBombCapacity() {
        maxBombs++;
    }

    public void increaseExplosionRange() {
        explosionRange++;
    }

    public void increaseSpeed() {
        speed += 20;
    }

    public void setMovingUp(boolean moving) {
        movingUp = moving;
    }

    public void setMovingDown(boolean moving) {
        movingDown = moving;
    }

    public void setMovingLeft(boolean moving) {
        movingLeft = moving;
    }

    public void setMovingRight(boolean moving) {
        movingRight = moving;
    }

    @Override
    public void update(double dt) {
        updateInvulnerability(dt);
    }

    public void update(double dt, List<Entity> obstacles) {
        updateInvulnerability(dt);

        if (isDead()) return;

        double dx = 0;
        double dy = 0;

        if (movingUp) dy--;
        if (movingDown) dy++;
        if (movingLeft) dx--;
        if (movingRight) dx++;

        // Normalize diagonal movement.
        if (dx != 0 && dy != 0) {
            double length = Math.sqrt(dx * dx + dy * dy);
            dx /= length;
            dy /= length;
        }

        double moveX = dx * speed * dt;
        double moveY = dy * speed * dt;

        // Check horizontal and vertical movement separately.
        if (!collides(x + moveX, y, obstacles)) {
            x += moveX;
        }

        if (!collides(x, y + moveY, obstacles)) {
            y += moveY;
        }
    }

    private void updateInvulnerability(double dt) {
        if (invulnerabilityTimer > 0) {
            invulnerabilityTimer -= dt;

            if (invulnerabilityTimer < 0) {
                invulnerabilityTimer = 0;
            }
        }
    }

    private boolean collides(double nextX, double nextY,
                             List<Entity> obstacles) {

        for (Entity obstacle : obstacles) {

            if (!obstacle.isOccupying()) continue;

            // Allow the owner to move out of their own bomb.
            if (obstacle instanceof Bomb bomb
                    && bomb.getOwner() == this
                    && overlaps(x, y, bomb)) {
                continue;
            }

            if (overlaps(nextX, nextY, obstacle)) {
                return true;
            }
        }

        return false;
    }

    private boolean overlaps(double playerX, double playerY, Entity obstacle) {
        return playerX < obstacle.getX() + obstacle.getWidth()
                && playerX + width > obstacle.getX()
                && playerY < obstacle.getY() + obstacle.getHeight()
                && playerY + height > obstacle.getY();
    }

    @Override
    public boolean isOccupying() {
        return !isDead();
    }

    @Override
    public void interact(Entity entity) {
        if (entity instanceof Explosion) {
            loseLife();
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!isDead() && image != null) {
            gc.drawImage(image, x, y, width, height);
        }
    }
}
