
package bombastic.game.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Bomb extends Entity {

    private static final double EXPLOSION_DELAY = 2.0;

    private final Player owner;
    private final int explosionRange;

    private double timer;
    private boolean exploded;

    public Bomb(
            double x,
            double y,
            double size,
            Image image,
            Player owner,
            int explosionRange
    ) {
        super(x, y, size, size, image);

        this.owner = owner;
        this.explosionRange = explosionRange;

        timer = 0;
        exploded = false;
    }

    public Player getOwner() {
        return owner;
    }

    public int getExplosionRange() {
        return explosionRange;
    }

    public boolean hasExploded() {
        return exploded;
    }

    public void trigger() {
        exploded = true;
    }

    @Override
    public boolean isOccupying() {
        return !exploded;
    }

    @Override
    public void interact(Entity entity) {
        if (entity instanceof Explosion) {
            trigger();
        }
    }

    @Override
    public void update(double dt) {
        if (exploded) {
            return;
        }

        timer += dt;

        if (timer >= EXPLOSION_DELAY) {
            exploded = true;
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!exploded && image != null) {
            gc.drawImage(image, x, y, width, height);
        }
    }
}
