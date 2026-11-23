
package bombastic;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        new Controller((Scene scene, String title) -> {

            // Remember the current window state and dimensions
            boolean wasFullScreen = stage.isFullScreen();

            double width = stage.getWidth();
            double height = stage.getHeight();
            double x = stage.getX();
            double y = stage.getY();

            // Switch scenes
            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();

            // Restore the previous window state
            Platform.runLater(() -> {

                if (wasFullScreen) {
                    stage.setFullScreen(true);
                } else {
                    stage.setWidth(width);
                    stage.setHeight(height);
                    stage.setX(x);
                    stage.setY(y);
                }

                stage.toFront();
                stage.requestFocus();
            });
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
