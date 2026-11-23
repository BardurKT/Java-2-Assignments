
package bombastic.game.entities.powerups;

import bombastic.game.entities.Entity;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class PowerUp extends Entity {

    protected boolean collected = false;

    public PowerUp(double x, double y, double size, Image image) {
        super(x, y, size, size, image);
    }

    @Override
    public boolean isOccupying() {
        return !collected;
    }

    protected void collect() {
        collected = true;
    }

    @Override
    public void update(double dt) {
        // Power-ups do not need regular updates.
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!collected && image != null) {
            gc.drawImage(image, x, y, width, height);
        }
    }
}
