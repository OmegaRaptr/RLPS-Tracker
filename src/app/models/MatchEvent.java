package app.models;

public class MatchEvent {

    public static final String GOAL = "GOAL";
    public static final String SHOT = "SHOT";
    public static final String SAVE = "SAVE";
    public static final String DEMO = "DEMO";

    private int matchId;
    private int playerId;
    private String eventType;
    private int eventTime;
    private Boolean shotScored;
    private Boolean shotSaved;
    private Boolean epicSave;
    private Double goalSpeed;

    public MatchEvent(
            int matchId,
            int playerId,
            String eventType,
            int eventTime,
            Boolean shotScored,
            Boolean shotSaved,
            Boolean epicSave,
            Double goalSpeed
    ) {
        this.matchId = matchId;
        this.playerId = playerId;
        this.eventType = eventType;
        this.eventTime = eventTime;
        this.shotScored = shotScored;
        this.shotSaved = shotSaved;
        this.epicSave = epicSave;
        this.goalSpeed = goalSpeed;
    }

    public int getMatchId() {
        return matchId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public String getEventType() {
        return eventType;
    }

    public int getEventTime() {
        return eventTime;
    }

    public Boolean getShotScored() {
        return shotScored;
    }

    public Boolean getShotSaved() {
        return shotSaved;
    }

    public Boolean getEpicSave() {
        return epicSave;
    }

    public Double getGoalSpeed() {
        return goalSpeed;
    }
}