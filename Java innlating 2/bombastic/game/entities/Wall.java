
package bombastic.game.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Wall extends Entity {

    private final boolean destructible;
    private boolean destroyed;

    public Wall(
            double x,
            double y,
            double size,
            Image image,
            boolean destructible
    ) {
        super(x, y, size, size, image);

        this.destructible = destructible;
        destroyed = false;
    }

    public boolean isDestructible() {
        return destructible;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void destroy() {
        if (destructible) {
            destroyed = true;
        }
    }

    @Override
    public boolean isOccupying() {
        return !destroyed;
    }

    @Override
    public void interact(Entity entity) {
        if (entity instanceof Explosion) {
            destroy();
        }
    }

    @Override
    public void update(double dt) {
        // Walls do not need regular updates.
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!destroyed && image != null) {
            gc.drawImage(image, x, y, width, height);
        }
    }
}
