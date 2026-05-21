package app.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void initializeDatabase() {

        try (Connection conn = DatabaseManager.connect();
             Statement stmt = conn.createStatement()) {

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS players (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL UNIQUE
                )
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS matches (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    match_date TEXT DEFAULT CURRENT_TIMESTAMP
                )
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS match_players (
                    match_id INTEGER,
                    player_id INTEGER,

                    FOREIGN KEY(match_id) REFERENCES matches(id),
                    FOREIGN KEY(player_id) REFERENCES players(id)
                )
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS match_events (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,

                    match_id INTEGER NOT NULL,
                    player_id INTEGER NOT NULL,

                    event_type TEXT NOT NULL,
                    event_time INTEGER NOT NULL,

                    shot_scored INTEGER,
                    shot_saved INTEGER,

                    epic_save INTEGER,

                    goal_speed REAL,

                    FOREIGN KEY(match_id) REFERENCES matches(id),
                    FOREIGN KEY(player_id) REFERENCES players(id)
                )
            """);

            System.out.println("Database initialized.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}