package app.controllers;

import app.db.MatchService;
import app.db.PlayerService;
import app.models.MatchEvent;
import app.models.PlayerStats;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.List;

public class AddMatchController {

    @FXML
    private ComboBox<String> playerOneBox;

    @FXML
    private ComboBox<String> playerTwoBox;

    @FXML
    private ComboBox<String> eventTypeBox;

    @FXML
    private ComboBox<String> eventPlayerBox;
@FXML
    private TextField timeField;

    @FXML
    private TextField goalSpeedField;

    @FXML
    private CheckBox shotScoredCheck;

    @FXML
    private CheckBox shotSavedCheck;

    @FXML
    private CheckBox epicSaveCheck;

    @FXML
    private ListView<String> eventListView;

    @FXML
    private final List<MatchEvent> events = new ArrayList<>();
 
    @FXML
    public void initialize() {

        loadPlayers();

        eventTypeBox.setItems(FXCollections.observableArrayList(
                MatchEvent.GOAL,
                MatchEvent.SHOT,
                MatchEvent.SAVE,
                MatchEvent.DEMO
        ));
        playerOneBox.setOnAction(e -> refreshEventPlayers());
        playerTwoBox.setOnAction(e -> refreshEventPlayers());
    }
    private void loadPlayers() {
        var players = PlayerService.getAllPlayers();
        for (var player : players) {
            playerOneBox.getItems().add(player.getName());
            playerTwoBox.getItems().add(player.getName());
        }
    }
 @FXML
    private void handleAddEvent() {

        String type = eventTypeBox.getValue();
        String player = eventPlayerBox.getValue();

        if (type == null || player == null || timeField.getText().isEmpty()) {
            showAlert("Missing Data", "Please fill in required fields.");
            return;
        }

        int playerId = getPlayerId(player);

        int time = Integer.parseInt(timeField.getText());

        Double goalSpeed = null;

        if (!goalSpeedField.getText().isEmpty()) {
            goalSpeed = Double.parseDouble(goalSpeedField.getText());
        }

        MatchEvent event = new MatchEvent(
                -1,
                playerId,
                type,
                time,
                shotScoredCheck.isSelected(),
                shotSavedCheck.isSelected(),
                epicSaveCheck.isSelected(),
                goalSpeed
        );

        events.add(event);

        eventListView.getItems().add(
                type + " | " +
                player + " | " +
                time + "s"
        );

        clearEventFields();
    }
    @FXML
    private void handleSaveMatch() {

        if (playerOneBox.getValue() == null || playerTwoBox.getValue() == null) {
            showAlert("Missing Players", "Select both players.");
            return;
        }
        if (playerOneBox.getValue().equals(playerTwoBox.getValue())) {
            showAlert("Invalid Match","A player cannot play against themselves.");
            return;
        }

        int matchId = MatchService.createMatch();

        int playerOneId = getPlayerId(playerOneBox.getValue());
        int playerTwoId = getPlayerId(playerTwoBox.getValue());

        MatchService.addPlayerToMatch(matchId, playerOneId);
        MatchService.addPlayerToMatch(matchId, playerTwoId);

        for (MatchEvent event : events) {

            MatchEvent updatedEvent = new MatchEvent(
                    matchId,
                    event.getPlayerId(),
                    event.getEventType(),
                    event.getEventTime(),
                    event.getShotScored(),
                    event.getShotSaved(),
                    event.getEpicSave(),
                    event.getGoalSpeed()
            );

            MatchService.addEvent(updatedEvent);
        }

        showAlert("Success", "Match saved successfully.");

        events.clear();
        eventListView.getItems().clear();
    }
    private void refreshEventPlayers() {

        eventPlayerBox.getItems().clear();

        String playerOne = playerOneBox.getValue();
        String playerTwo = playerTwoBox.getValue();

        if (playerOne != null) {
            eventPlayerBox.getItems().add(playerOne);
        }

        if (playerTwo != null && !playerTwo.equals(playerOne)) {
            eventPlayerBox.getItems().add(playerTwo);
        }
    }
    private int getPlayerId(String name) {

    return PlayerService.getAllPlayers()
            .stream()
            .filter(p -> p.getName().equals(name))
            .findFirst()
            .map(PlayerStats::getId)
            .orElse(-1);
}

    private void clearEventFields() {

        timeField.clear();
        goalSpeedField.clear();

        shotScoredCheck.setSelected(false);
        shotSavedCheck.setSelected(false);
        epicSaveCheck.setSelected(false);
    }

    private void showAlert(String title, String message) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}