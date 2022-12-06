package fifth;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Solver1 {

    public static final String filepath = "src/main/java/fifth/input.txt";
    private static class CraneAction{
        public int amountOfCrates, fromPile, toPile;
        private static CraneAction reused = new CraneAction();
        public static CraneAction parse(String s){
            String[] split = s.split(" ");
            reused.amountOfCrates = Integer.parseInt(split[1]);
            reused.fromPile = Integer.parseInt(split[3]);
            reused.toPile = Integer.parseInt(split[5]);
            return reused;
        }
    }
    @FunctionalInterface
    public static interface CrateMoveFunction{
        void move(List<String> fromPile, List<String> toPile, int amount);
    }

    public static void main(String[] args) throws IOException {
        long totalMS = 0;
        long timeA = System.nanoTime();
        for(int i = 0; i < 1_000_000; i++){
            getTopCrates(Solver1::moveCrates);
        }
        totalMS += (System.nanoTime() - timeA) / 1_000_000;

        System.out.println("Letters of top crates are: " + getTopCrates(Solver1::moveCrates));
        System.out.println("Solve time: " + totalMS + "ns");
    }

    public static String getTopCrates(CrateMoveFunction moveCratesFunc) throws IOException
    {
        ArrayList<String>[] cargoPiles = new ArrayList[9];

        BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath));
        String currentLine;

        for(int i = 0; i < 9; i++){
            currentLine = bufferedReader.readLine();
            cargoPiles[i] = new ArrayList<String>(Arrays.stream(
                    currentLine.replaceAll("\\[","")
                            .replaceAll("]","")
                            .split(" ")
            ).toList());
        }

        while((currentLine = bufferedReader.readLine()) != null){
            CraneAction action = CraneAction.parse(currentLine);
            moveCratesFunc.move(
                    cargoPiles[action.fromPile - 1],
                    cargoPiles[action.toPile - 1],
                    action.amountOfCrates);
        }

        bufferedReader.close();
        return getLastIndexes(cargoPiles);
    }

    private static String getLastIndexes(List<String>[] cargoPiles) {
        String toReturn = "";
        for(List<String> list : cargoPiles){
            //System.out.println("Getting last index of " + list);
            if(list.isEmpty()){continue;}
            toReturn += list.get(list.size() - 1);
        }
        return toReturn;
    }

    public static void moveCrates(List<String> fromPile, List<String> toPile, int amount){
        int count = 0;
        while(!fromPile.isEmpty() && count < amount){
            count++;
            String cargoBox = fromPile.remove(fromPile.size() -1);
            toPile.add(cargoBox);
        }

    }
}
