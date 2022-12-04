package fourth;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Solver2 {

    public static final String filepath = "src/main/java/fourth/input.txt";

    //In how many assignment pairs do the ranges overlap?
    public static void main(String[] args) throws IOException {
        long totalMS = 0;
        long timeA = System.nanoTime();
        for(int i = 0; i < 1_000; i++){
            getCountOfContainedRanges();
        }
        totalMS += (System.nanoTime() - timeA) / 1_000;
        //21449 is too high
        System.out.println("Sum of overlapping pairs in ranges are: " + getCountOfContainedRanges());
        System.out.println("Solve time: " + totalMS + "ns");
    }

    public static int getCountOfContainedRanges() throws IOException
    {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath));
        String currentLine;
        int sumOverlappingPairs = 0;
        int[] rangeOneInt = new int[2];
        int[] rangeTwoInt = new int[2];

        while((currentLine = bufferedReader.readLine()) != null){
            String[] ranges = currentLine.split(",");
            String[] rangeOne = ranges[0].split("-");
            String[] rangeTwo = ranges[1].split("-");
            rangeOneInt[0] = Integer.parseInt(rangeOne[0]);
            rangeOneInt[1] = Integer.parseInt(rangeOne[1]);
            rangeTwoInt[0] = Integer.parseInt(rangeTwo[0]);
            rangeTwoInt[1] = Integer.parseInt(rangeTwo[1]);

            sumOverlappingPairs += overlaps(rangeOneInt[0],rangeOneInt[1],rangeTwoInt[0], rangeTwoInt[1]);
        }
        bufferedReader.close();
        return sumOverlappingPairs;
    }
    public static int overlaps(int a1, int a2, int b1, int b2)
    {
        int maxA1B1 = a1 < b1 ? b1 : a1;
        int minA2B2 = a2 < b2 ? a2 : b2;
        if(!(maxA1B1 <= minA2B2)){
            return 0;
        }
        return 1;
    }


}
