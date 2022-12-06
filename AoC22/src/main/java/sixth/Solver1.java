package sixth;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Solver1 {

    public static final String filepath = "src/main/java/sixth/input.txt";
    public static final Set<Integer> duplicatesSet = new HashSet<>();

    public static void main(String[] args) throws IOException
    {
        long totalMS = 0;
        long timeA = System.nanoTime();
        for(int i = 0; i < 1_000; i++){
            getBytesToFirstMarker();
        }
        totalMS += (System.nanoTime() - timeA) / 1_000;
        //4096 is too high
        System.out.println("Character count position of package start marker: " + getBytesToFirstMarker());
        System.out.println("Solve time: " + totalMS + "ns");
    }

    public static int getBytesToFirstMarker() throws IOException
    {
        FileReader reader = new FileReader(filepath);
        int currentChar;
        int[] maybeMarker = new int[14]; //Changing this value from 4 to 14 gives solution to task 2
        Arrays.fill(maybeMarker, -1);
        int counter = 0;

        while((currentChar = reader.read()) != -1){
            maybeMarker[counter % maybeMarker.length] = currentChar;
            if(isUnique(maybeMarker)){
                break;
            }
            counter++;
        }
        return counter + 1;
    }
    public static boolean isUnique(int[] array){
        duplicatesSet.clear();
        for(int c : array){
            if(!duplicatesSet.add(c) || c < 0){
                return false;
            }
        }
        return true;
    }
    public static String arrayToString(int[] array){
        String asString = "";
        for(int c : array){
            asString += (char) c+",";
        }
        return asString;
    }

}
