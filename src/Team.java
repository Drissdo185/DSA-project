import java.util.Objects;
public class Team {

    private String name;
    private double OffensiveRating; // Offensive rating
    private double DefensiveRating; // Defensive rating
    private double pace; // Pace rating

    public Team(String name, double OffensiveRating, double DefensiveRating, double pace){
        this.name = name;
        this.OffensiveRating = OffensiveRating;
        this.DefensiveRating = DefensiveRating;
        this.pace = pace;
    }
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Team: " + name + " | Offensive Rating: " + OffensiveRating + " | Defensive Rating: " + DefensiveRating +
                " | Pace: " + pace);
        return sb.toString();
    }
    public String getName()
    {
        return name;
    }

    public double getOffRating()
    {
        return OffensiveRating;
    }

    public double getDefRating()
    {
        return DefensiveRating;
    }

    public double getPace()
    {
        return pace;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setOffensiveRating(double offensiveRating)
    {
        this.OffensiveRating = offensiveRating;
    }

    public void setDefensiveRating(double defensiveRating)
    {
        this.DefensiveRating = defensiveRating;
    }

    public void setPace(double pace)
    {
        this.pace = pace;
    }

    public boolean equals(Object team)
    {
        if (team == this) return true;

        if (!(team instanceof Team)) return false;

        Team t = (Team) team;
        double epsilon = 0.01;
        return this.getName() == t.getName() &&
                this.getOffRating() - t.getOffRating() <= epsilon &&
                this.getDefRating() - t.getDefRating() <= epsilon &&
                this.getPace() - t.getPace() <= epsilon;
    }

    public int hashCode() {
        return Objects.hash(name, OffensiveRating, DefensiveRating, pace);
    }

}