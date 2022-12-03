package third;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;

public class Solver1 {

    public static final String filepath = "src/main/java/third/input.txt";
    public static final String priorities = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /*
        Find the item type that appears in both compartments of each rucksack.
        What is the sum of the priorities of those item types?
     */

    public static void main(String[] args) throws IOException {
        long totalMS = 0;
        long timeA = System.nanoTime();
        for(int i = 0; i < 1_000_000; i++){
            getSumOfPriorities();
        }
        totalMS += (System.nanoTime() - timeA) / 1_000_000;

        System.out.println("Sum of priorities of items in both compartments are: " + getSumOfPriorities());
        System.out.println("Solve time: " + totalMS + "ns");
    }

    public static int getSumOfPriorities() throws IOException
    {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath));
        String currentLine;
        int prioritySum = 0;

        while((currentLine = bufferedReader.readLine()) != null){
            String[] compartments = getCompartments(currentLine);
            char inBoth = findDuplicate(compartments);
            prioritySum += getPriority(inBoth);
        }
        bufferedReader.close();
        return prioritySum;
    }

    private static char findDuplicate(String[] compartments)
    {
        for(char b : compartments[0].toCharArray()){
            for(char c : compartments[1].toCharArray()){
                if(b == c){
                    return b;
                }
            }
        }
        return 'a';
    }

    public static String[] getCompartments(String s)
    {
        String[] compartments = new String[2];
        int middle = s.length() / 2;
        compartments[0] = s.substring(0,middle);
        compartments[1] = s.substring(middle);
        return compartments;
    }

    public static int getPriority(char c)
    {
        return priorities.indexOf(c) + 1;
    }
}
