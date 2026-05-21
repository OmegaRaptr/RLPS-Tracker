package app.models;

public class PlayerStats {

    private int id;
    private String name;

    private int goals;
    private int shots;
    private int saves;
    private int demos;

    private double shotPercentage;
    private double averageGoalSpeed;

    public PlayerStats(
            int id,
            String name,
            int goals,
            int shots,
            int saves,
            int demos,
            double shotPercentage,
            double averageGoalSpeed
    ) {
        this.id = id;
        this.name = name;
        this.goals = goals;
        this.shots = shots;
        this.saves = saves;
        this.demos = demos;
        this.shotPercentage = shotPercentage;
        this.averageGoalSpeed = averageGoalSpeed;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getGoals() {
        return goals;
    }

    public int getShots() {
        return shots;
    }

    public int getSaves() {
        return saves;
    }

    public int getDemos() {
        return demos;
    }

    public double getShotPercentage() {
        return shotPercentage;
    }

    public double getAverageGoalSpeed() {
        return averageGoalSpeed;
    }

    @Override
    public String toString() {

        return name +
                " | Goals: " + goals +
                " | Shots: " + shots +
                " | Saves: " + saves +
                " | Demos: " + demos +
                " | Shot%: " + String.format("%.1f", shotPercentage) +
                " | Avg Goal Speed: " + String.format("%.1f", averageGoalSpeed);
    }
}