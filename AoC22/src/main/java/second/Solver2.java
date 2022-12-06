package second;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Solver2 {

    public static final String filepath = "src/main/java/second/input.txt";

    @FunctionalInterface
    public interface Func{
        int evaluate(String enemyChoice);
    }

    private static final Map<String, Integer> valueOfChoiceMap = new HashMap<>(
            Map.of("A", 1, "B", 2, "C", 3));
    private static final Map<String, String> whoBeatsWhoMap = new HashMap<>(Map.of(
            "A", "C", //Rock beats Scissors
            "B", "A", //Paper beats Rock
            "C", "B" //Scissors beats Paper
    ));
    private static final Map<String, String> whoLoosesToWhoMap = new HashMap<>(Map.of(
            "C","A",
            "A", "B",
            "B","C"
    ));
    private static final Map<String, Func> approachMap = new HashMap<>(Map.of(
            // You need to loose the match
            "X", enemyChoice -> valueOfChoiceMap.get(whoBeatsWhoMap.get(enemyChoice)),
            // You need to draw the match
            "Y", enemyChoice ->  3 + valueOfChoiceMap.get(enemyChoice),
            // You need to win the match
            "Z", enemyChoice -> 6 + valueOfChoiceMap.get(whoLoosesToWhoMap.get(enemyChoice))
    ));

    public static void main(String[] args) throws IOException {
        Solver2 solver = new Solver2();
        long timeA = System.currentTimeMillis();
        System.out.println("Score when following guide: " + solver.calculateScore(filepath));
        System.out.println("Time to solve: " + (System.currentTimeMillis() - timeA) + "ms");
    }

    public int calculateScore(String filepath) throws IOException, NumberFormatException
    {
        //Enemy Choice | Your Choice \n
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath));
        String currentLine;
        String[] playerChoices = null;
        int scoreSum = 0;

        while((currentLine = bufferedReader.readLine()) != null){
            playerChoices = currentLine.split(" ");
            scoreSum += calculateRoundResult(playerChoices[0], playerChoices[1]);
        }

        return scoreSum;
    }
    public int calculateRoundResult(String enemyChoice, String approach)
    {
        return approachMap.get(approach).evaluate(enemyChoice);
    }

}
