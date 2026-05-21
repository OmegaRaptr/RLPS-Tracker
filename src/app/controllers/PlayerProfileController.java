package app.controllers;

import app.db.PlayerService;
import app.models.PlayerStats;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class PlayerProfileController {

    @FXML
    private TextField playerNameField;

    @FXML
    private ListView<PlayerStats> playerListView;

    @FXML
    public void initialize() {

        refreshPlayers();
    }

    @FXML
    private void handleAddPlayer() {

        String name = playerNameField.getText();

        if (name == null || name.isBlank()) {
            return;
        }

        PlayerService.addPlayer(name);

        playerNameField.clear();

        refreshPlayers();
    }

    @FXML
    private void handleRemovePlayer() {

        PlayerStats selected = playerListView.getSelectionModel().getSelectedItem();

        if (selected == null) {
            return;
        }

        PlayerService.removePlayer(selected.getId());

        refreshPlayers();
    }

    @FXML
    private void handleEditPlayer() {

        PlayerStats selected = playerListView.getSelectionModel().getSelectedItem();

        if (selected == null) {
            return;
        }

        TextInputDialog dialog = new TextInputDialog(selected.getName());

        dialog.setTitle("Edit Player");
        dialog.setHeaderText(null);
        dialog.setContentText("New Name:");

        dialog.showAndWait().ifPresent(newName -> {

            if (!newName.isBlank()) {

                PlayerService.updatePlayer(
                        selected.getId(),
                        newName
                );

                refreshPlayers();
            }
        });
    }
    @FXML
    private void handleRefreshPlayers() {
        refreshPlayers();
    }
    private void refreshPlayers() {

        playerListView.getItems().clear();

        playerListView.getItems().addAll(
                PlayerService.getAllPlayers()
        );
    }
}