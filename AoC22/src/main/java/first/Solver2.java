package first;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Solver2 {

    /*
        Find the top three Elves carrying the most Calories. How many Calories are those Elves carrying in total?
     */

    public static final String filepath = "src/main/java/first/input.txt";

    public static void main(String[] args) throws IOException, NumberFormatException
    {
        long timeA = System.currentTimeMillis();
        System.out.println(new Solver2().getCalorieCountOfTopThree(filepath));
        System.out.println("Solve time: " + (System.currentTimeMillis() - timeA) + "ms");
    }

    public int getCalorieCountOfTopThree(String filepath) throws IOException, NumberFormatException
    {
        int[] topThree = new int[3];
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath));

        String currentLine = "";
        int currentElfCalCount = 0;

        while((currentLine = bufferedReader.readLine()) != null){
            if(!currentLine.equals("")){
                currentElfCalCount += Integer.parseInt(currentLine);
            }else{  //New elf encountered in file. Begin new count.
                replaceIndexIfLessThan(currentElfCalCount,topThree);
                currentElfCalCount = 0;
            }
        }
        bufferedReader.close();
        return topThree[0] + topThree[1] + topThree[2];
    }

    public void replaceIndexIfLessThan(int a, int[] b)
    {
        for(int i = 0; i < b.length; i++){
            if(b[i] < a){
                b[i] = a;
                return;
            }
        }
    }
}
