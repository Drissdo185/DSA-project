import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Random;
public class Match {
    private String name1, name2;
    private double OffensiveRating1, OffensiveRating2; // Offensive rating
    private double DefensiveRating1, DefensiveRating2; // Defensive rating
    private double pace1, pace2; // Pace rating
    private double point1, point2; // Points scored
    private final double leaguePace = 100.0;
    private final double leagueOffRating = 110.4;


    public Match(Team team1, Team team2){
        //team 1
        name1 = team1.getName();
        OffensiveRating1 = team1.getOffRating();
        DefensiveRating1 = team1.getDefRating();
        pace1 = team1.getPace();

        //team 2
        name2 = team2.getName();
        OffensiveRating2 = team2.getOffRating();
        DefensiveRating2 = team2.getDefRating();
        pace2 = team2.getPace();
    }

    public HashMap<String, Double> simulateMatch(){
        HashMap<String, Double> result = new HashMap<>();

        // Offensive and defensive efficiency has random -5% to 5% variations
        double ortg1 = OffensiveRating1 * (1 + (new Random().nextInt(5+ 1 -(-5)) + (-5))/100.0);
        double drtg1 = DefensiveRating1 * (1 + (new Random().nextInt(5+ 1 -(-5)) + (-5))/100.0);
        double ortg2 = OffensiveRating2 * (1 + (new Random().nextInt(5+ 1 -(-5)) + (-5))/100.0);
        double drtg2 = DefensiveRating2 * (1 + (new Random().nextInt(5+ 1 -(-5)) + (-5))/100.0);

        double gamePace = (pace1 + pace2) / leaguePace;



        // for each team, multiply their Offense with Opponent defense and divide by league average Offensive Rating per game
        double ppp1 = (ortg1 * drtg2)/ leagueOffRating;
        double ppp2 = (ortg2 * drtg1)/ leagueOffRating;
        // multiply predicted PPP by predicted game pace, and divide by 100 to get final score
        point1 = (int) (ppp1 * gamePace / 100);
        point2 = (int) (ppp2 * gamePace / 100);

        if (point1 == point2) // if points are equal, re-simulate
        {
            this.simulateMatch();
        }

        result.put(name1, point1);
        result.put(name2, point2);
        return result;
    }

    public HashMap<String, Double> simulateNMatch(int repetition)
    {
        int sum1 = 0;
        int sum2 = 0;
        double count1 = 0;
        double count2 = 0;

        HashMap<String, Double> sim = new HashMap<>();

        for (int i = 0; i < repetition; i++)
        {
            sim = simulateMatch();
            double score1 = sim.get(name1);
            double score2 = sim.get(name2);
            if (score1 > score2) // add up numbers of win per team
            {
                count1++;
            }
            else if (score1 < score2)
            {
                count2++;
            }
            else // if tie, re-simulate match
            {
                // re-simulate
                System.out.println("Resimulate");
                i--;
                continue;
            }
            sum1 += score1;
            sum2 += score2;
        }
        System.out.println(name1 + ": " + sum1/repetition + " , " + name2 + ": " + sum2/repetition);

        // put percentage as 2 decimal places
        DecimalFormat twodp = new DecimalFormat("#.00");
        double ratio1 = Double.valueOf(twodp.format(count1/repetition * 100));
        double ratio2 = Double.valueOf(twodp.format(count2/repetition * 100));
        sim.put(name1, ratio1);
        sim.put(name2, ratio2);

        return sim;
    }

}