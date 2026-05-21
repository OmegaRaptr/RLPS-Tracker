package app.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class RootController {

    public StackPane contentArea;

    private void loadPage(String path) {
        try {
            Node page = FXMLLoader.load(getClass().getResource(path));
            contentArea.getChildren().setAll(page);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void goDashboard() { loadPage("/app/views/pages/Dashboard.fxml"); }
    public void goAddMatch() { loadPage("/app/views/pages/AddMatch.fxml"); }
    public void goPlayerProfile() { loadPage("/app/views/pages/PlayerProfile.fxml"); }
    public void goLeaderboards() { loadPage("/app/views/pages/Leaderboards.fxml"); }
    public void goSeasonView() { loadPage("/app/views/pages/SeasonView.fxml"); }
}
