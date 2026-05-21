package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import app.db.DatabaseInitializer;
import javafx.scene.Parent;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        DatabaseInitializer.initializeDatabase();

        Parent root = FXMLLoader.load(getClass().getResource("/app/views/RootLayout.fxml"));
        stage.setScene(new Scene(root, 900, 600));
        stage.setTitle("Stats Tracker");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
