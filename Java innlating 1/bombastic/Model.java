
package bombastic;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Model {

    private final StringProperty background =
            new SimpleStringProperty("bg1.png");

    private final StringProperty arena =
            new SimpleStringProperty("Arena 1");

    public StringProperty backgroundProperty() {
        return background;
    }

    public String getBackground() {
        return background.get();
    }

    public void setBackground(String background) {
        this.background.set(background);
    }

    public StringProperty arenaProperty() {
        return arena;
    }

    public String getArena() {
        return arena.get();
    }

    public void setArena(String arena) {
        this.arena.set(arena);
    }
}
