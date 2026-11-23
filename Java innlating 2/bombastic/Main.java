
package bombastic;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        // Set the initial window dimensions.
        stage.setWidth(780);
        stage.setHeight(720);
        stage.centerOnScreen();

        // Create the application controller.
        // Main keeps ownership of the Stage.
        new Controller((Scene scene, String title) -> {

            boolean wasFullScreen = stage.isFullScreen();
            boolean wasMaximized = stage.isMaximized();

            double width = stage.getWidth();
            double height = stage.getHeight();
            double x = stage.getX();
            double y = stage.getY();

            // Change the displayed scene.
            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();

            // Restore the previous window state.
            Platform.runLater(() -> {

                if (wasFullScreen) {
                    stage.setFullScreen(true);

                } else if (wasMaximized) {
                    stage.setMaximized(true);

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
