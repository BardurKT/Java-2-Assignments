
package bombastic.game.entities.powerups;

import bombastic.game.entities.Entity;
import bombastic.game.entities.Player;
import javafx.scene.image.Image;

public class LifePowerUp extends PowerUp {

    public LifePowerUp(double x, double y, double size, Image image) {
        super(x, y, size, image);
    }

    @Override
    public void interact(Entity entity) {
        if (!collected && entity instanceof Player player) {
            player.addLife();
            collect();
        }
    }
}
