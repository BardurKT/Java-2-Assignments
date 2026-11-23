
package bombastic.game.arena;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    private final Random random = new Random();

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

    private final double crateChance;

    public Arena() {
        this("Arena 1");
    }

    public Arena(String arenaName) {

        // Choose the crate density for the selected arena.
        switch (arenaName) {

            case "Arena 2" -> crateChance = 0.30;

            case "Arena 3" -> crateChance = 0.85;

            case "Arena 1" -> crateChance = 0.65;

            default -> crateChance = 0.65;
        }

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

        // Randomly distribute crates according to the selected arena.
        return random.nextDouble() < crateChance;
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
