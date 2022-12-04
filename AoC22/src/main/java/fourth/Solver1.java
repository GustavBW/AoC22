package fourth;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Solver1 {

    public static final String filepath = "src/main/java/fourth/input.txt";

    public static void main(String[] args) throws IOException {
        long totalMS = 0;
        long timeA = System.nanoTime();
        for(int i = 0; i < 1_000_000; i++){
            getCountOfContainedRanges();
        }
        totalMS += (System.nanoTime() - timeA) / 1_000_000;

        System.out.println("Sum of contained ranges are: " + getCountOfContainedRanges());
        System.out.println("Solve time: " + totalMS + "ns");
    }

    public static int getCountOfContainedRanges() throws IOException
    {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath));
        String currentLine;
        int containedCount = 0;
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

            if(isRangeWithin(rangeOneInt[0],rangeOneInt[1],rangeTwoInt[0], rangeTwoInt[1])
                    || isRangeWithin(rangeTwoInt[0],rangeTwoInt[1],rangeOneInt[0], rangeOneInt[1])){
                containedCount++;
            }
        }
        bufferedReader.close();
        return containedCount;
    }
    public static boolean isRangeWithin(int rangeA1, int rangeA2, int rangeB1, int rangeB2)
    {
        return rangeA1 >= rangeB1 && rangeA2 <= rangeB2;
    }
}
