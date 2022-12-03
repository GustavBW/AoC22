package second;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Solver1 {

    public static final String filepath = "src/main/java/second/input.txt";

    public static void main(String[] args) throws IOException {
        System.out.println("Score when following guide: " + new Solver1().calculateScore(filepath));
    }

    private static Map<String, Integer> valueOfChoiceMap = new HashMap<>(
            Map.of("A", 1, "B", 2, "C", 3));
    private static Map<String, String> whoBeatsWhoMap = new HashMap<>(Map.of(
            "A", "C", //Rock beats Scissors
            "B", "A", //Paper beats Rock
            "C", "B" //Scissors beats Paper
    ));
    private static Map<String, String> mapYourChoiceToABC = new HashMap<>(Map.of(
            "X","A",
            "Y","B",
            "Z","C"
    ));

    public int calculateScore(String filepath) throws IOException, NumberFormatException
    {
        //Enemy Choice | Your Choice \n
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath));
        String currentLine;
        String[] playerChoices = null;
        int scoreSum = 0;

        while((currentLine = bufferedReader.readLine()) != null){
            playerChoices = currentLine.split(" ");
            scoreSum += calculateRoundResult(playerChoices[0], mapYourChoiceToABC.get(playerChoices[1]));
        }

        return scoreSum;
    }
    public int calculateRoundResult(String enemyChoice, String yourChoice)
    {
        int outcome = 0; //0 on loss, 3 on draw, 6 on win
        if(whoBeatsWhoMap.get(yourChoice).equals(enemyChoice)){
            outcome = 6;
        }else if(enemyChoice.equals(yourChoice)){
            outcome = 3;
        }
        return valueOfChoiceMap.get(yourChoice) + outcome;
    }
}
