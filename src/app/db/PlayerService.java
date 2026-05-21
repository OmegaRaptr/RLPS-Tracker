package app.db;

import app.models.PlayerStats;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerService {

    public static void addPlayer(String name) {

        String sql = "INSERT INTO players(name) VALUES(?)";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removePlayer(int id) {

        String sql = "DELETE FROM players WHERE id = ?";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updatePlayer(int id, String newName) {

        String sql = "UPDATE players SET name = ? WHERE id = ?";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newName);
            stmt.setInt(2, id);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<PlayerStats> getAllPlayers() {

        List<PlayerStats> players = new ArrayList<>();

        String sql = """
                SELECT
                    p.id,
                    p.name,

                    SUM(CASE
                        WHEN me.event_type = 'GOAL' THEN 1
                        ELSE 0
                    END) AS goals,

                    SUM(CASE
                        WHEN me.event_type = 'SAVE' THEN 1
                        ELSE 0
                    END) AS saves,

                    SUM(CASE
                        WHEN me.event_type = 'DEMO' THEN 1
                        ELSE 0
                    END) AS demos,

                    SUM(CASE
                        WHEN me.event_type = 'SHOT' THEN 1
                        ELSE 0
                    END) AS shots

                FROM players p

                LEFT JOIN match_events me
                ON p.id = me.player_id

                GROUP BY p.id
                """;

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                int goals = rs.getInt("goals");
int shots = rs.getInt("shots");
int scoredShots = rs.getInt("scored_shots");

double shotPercent = 0;

if (shots > 0) {
    shotPercent = ((double) scoredShots / shots) * 100;
}

double avgGoalSpeed = rs.getDouble("avg_goal_speed");

players.add(
        new PlayerStats(
                rs.getInt("id"),
                rs.getString("name"),
                goals,
                shots,
                rs.getInt("saves"),
                rs.getInt("demos"),
                shotPercent,
                avgGoalSpeed
        )
);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return players;
    }
}