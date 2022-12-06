package fifth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Solver2 {

    public static void main(String[] args) throws IOException {
        long totalMS = 0;
        long timeA = System.nanoTime();
        for(int i = 0; i < 1_000; i++){
            fifth.Solver1.getTopCrates(Solver2::moveCrates);
        }
        totalMS += (System.nanoTime() - timeA) / 1_000;
        //WLCTSZHVB is wrong
        System.out.println("Letters of top crates are: " + fifth.Solver1.getTopCrates(Solver2::moveCrates));
        System.out.println("Solve time: " + totalMS + "ns");
    }

    private static final List<String> temp = new ArrayList<>();

    public static void moveCrates(List<String> fromPile, List<String> toPile, int num){
        temp.clear();
        int count = 0;
        while(!fromPile.isEmpty() && count < num){
            String cargoBox = fromPile.remove(fromPile.size() -1);
            temp.add(cargoBox);
        }
        Collections.reverse(temp);
        toPile.addAll(temp);
    }
}
