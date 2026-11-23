
package bombastic.game.arena;

import java.util.ArrayList;
import java.util.List;

import bombastic.game.entities.Entity;
import bombastic.game.entities.Floor;
import bombastic.game.entities.Wall;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Arena {

    public static final int COLUMNS = 15;
    public static final int ROWS = 13;
    public static final int TILE_SIZE = 48;

    private final List<Entity> entities = new ArrayList<>();

    private final Image grassImage;
    private final Image wallImage;
    private final Image crateImage;

    // # represents an indestructible wall.
    // . represents an open space where crates may appear.
    private final char[][] layout = {
        "###############".toCharArray(),
        "#.............#".toCharArray(),
        "#.#.#.#.#.#.#.#".toCharArray(),
        "#.............#".toCharArray(),
        "#.#.#.#.#.#.#.#".toCharArray(),
        "#.............#".toCharArray(),
        "#.#.#.#.#.#.#.#".toCharArray(),
        "#.............#".toCharArray(),
        "#.#.#.#.#.#.#.#".toCharArray(),
        "#.............#".toCharArray(),
        "#.#.#.#.#.#.#.#".toCharArray(),
        "#.............#".toCharArray(),
        "###############".toCharArray()
    };

    public Arena() {

        // Load terrain images.
        grassImage = loadImage("assets/terrain/grass.png");
        wallImage = loadImage("assets/terrain/wall.png");
        crateImage = loadImage("assets/terrain/crate.png");

        createArena();
    }

    // Loads an image from the project directory.
    private Image loadImage(String path) {
        return new Image("file:" + path);
    }

    // Creates the floor, walls and crates.
    private void createArena() {

        entities.clear();

        for (int row = 0; row < ROWS; row++) {

            for (int col = 0; col < COLUMNS; col++) {

                double x = col * TILE_SIZE;
                double y = row * TILE_SIZE;

                // Every position has a grass floor.
                entities.add(new Floor(
                    x, y, TILE_SIZE, grassImage
                ));

                char tile = layout[row][col];

                if (tile == '#') {

                    // Create an indestructible wall.
                    entities.add(new Wall(
                        x, y, TILE_SIZE, wallImage, false
                    ));

                } else if (shouldCreateCrate(row, col)) {

                    // Create a destructible crate.
                    entities.add(new Wall(
                        x, y, TILE_SIZE, crateImage, true
                    ));
                }
            }
        }
    }

    // Keeps both player starting areas free of crates.
    private boolean shouldCreateCrate(int row, int col) {

        // Player 1 starting area: top-left corner.
        if (row <= 3 && col <= 3) {
            return false;
        }

        // Player 2 starting area: bottom-right corner.
        if (row >= ROWS - 4 && col >= COLUMNS - 4) {
            return false;
        }

        // Randomly distribute crates throughout the arena.
        return Math.random() < 0.65;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    // Draws every arena entity.
    public void render(GraphicsContext gc) {

        for (Entity entity : entities) {
            entity.render(gc);
        }
    }
}
