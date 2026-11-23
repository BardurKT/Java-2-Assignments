
package bombastic.game.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Explosion extends Entity {

    private static final double EXPLOSION_DURATION = 0.5;

    private double timer;
    private boolean finished;

    public Explosion(double x, double y, double size, Image image) {
        super(x, y, size, size, image);

        timer = 0;
        finished = false;
    }

    public boolean isFinished() {
        return finished;
    }

    @Override
    public boolean isOccupying() {
        return !finished;
    }

    @Override
    public void interact(Entity entity) {
        if (entity == this || finished) {
            return;
        }

        // Let the entity decide how it responds to an explosion.
        entity.interact(this);
    }

    @Override
    public void update(double dt) {
        if (finished) {
            return;
        }

        timer += dt;

        if (timer >= EXPLOSION_DURATION) {
            finished = true;
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!finished && image != null) {
            gc.drawImage(image, x, y, width, height);
        }
    }
}
