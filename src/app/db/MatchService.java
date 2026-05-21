package app.db;

import app.models.MatchEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MatchService {

    public static int createMatch() {

        String sql = "INSERT INTO matches DEFAULT VALUES";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public static void addPlayerToMatch(int matchId, int playerId) {

        String sql = "INSERT INTO match_players(match_id, player_id) VALUES (?, ?)";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, matchId);
            stmt.setInt(2, playerId);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
 public static void addEvent(MatchEvent event) {

        String sql = """
                INSERT INTO match_events(
                    match_id,
                    player_id,
                    event_type,
                    event_time,
                    shot_scored,
                    shot_saved,
                    epic_save,
                    goal_speed
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, event.getMatchId());
            stmt.setInt(2, event.getPlayerId());
            stmt.setString(3, event.getEventType());
            stmt.setInt(4, event.getEventTime());

            if (event.getShotScored() != null) {
                stmt.setInt(5, event.getShotScored() ? 1 : 0);
            } else {
                stmt.setNull(5, java.sql.Types.INTEGER);
            }

            if (event.getShotSaved() != null) {
                stmt.setInt(6, event.getShotSaved() ? 1 : 0);
            } else {
                stmt.setNull(6, java.sql.Types.INTEGER);
            }

            if (event.getEpicSave() != null) {
                stmt.setInt(7, event.getEpicSave() ? 1 : 0);
            } else {
                stmt.setNull(7, java.sql.Types.INTEGER);
            }

            if (event.getGoalSpeed() != null) {
                stmt.setDouble(8, event.getGoalSpeed());
            } else {
                stmt.setNull(8, java.sql.Types.REAL);
            }

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}