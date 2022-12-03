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
        long timeA = System.currentTimeMillis();
        System.out.println("Sum of priorities of items in both compartments are: " + getSumOfPriorities());
        System.out.println("Solve time: " + (System.currentTimeMillis() - timeA) + "ms");
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
        return prioritySum;
    }

    private static char findDuplicate(String[] compartments)
    {
        for(char b : compartments[0].toCharArray()){
            if(compartments[1].indexOf(b) != -1){
                return b;
            }
        }
        return 'a';
    }

    public static String[] getCompartments(String s)
    {
        String[] compartments = new String[2];
        int middle = (int) (s.length() / 2.0);
        compartments[0] = s.substring(0,middle);
        compartments[1] = s.substring(middle);
        return compartments;
    }

    public static int getPriority(char c)
    {
        return priorities.indexOf(c) + 1;
    }
}
