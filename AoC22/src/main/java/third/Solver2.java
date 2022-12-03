package third;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Solver2 {

    /*
    Find the item type that corresponds to the badges of each three-Elf group.
    What is the sum of the priorities of those item types?
     */
    public static final String filepath = "src/main/java/third/input.txt";
    public static final String priorities = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final char[] prioArray = priorities.toCharArray();

    public static void main(String[] args) throws IOException {
        long totalMS = 0;
        long timeA = System.nanoTime();
        for(int i = 0; i < 1_000_000; i++){
            getSumOfPriorities();
        }
        totalMS += (System.nanoTime() - timeA) / 1_000_000;

        System.out.println("Sum of priorities of items shared between 3 bags: " + getSumOfPriorities());
        System.out.println("Solve time: " + totalMS + "ns");
    }

    public static int getSumOfPriorities() throws IOException
    {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath));
        String currentLineA, currentLineB, currentLineC;
        int prioritySum = 0;

        Set<Character> uniqueItemsInBagOne = new HashSet<>();
        Set<Character> sharedItemsBetweenOneAndTwo = new HashSet<>();

        while((currentLineA = bufferedReader.readLine()) != null){
            currentLineB = bufferedReader.readLine();
            currentLineC = bufferedReader.readLine();
            prioritySum += getPriorityOf(getSharedCharIn(
                    currentLineA,currentLineB,currentLineC,uniqueItemsInBagOne,sharedItemsBetweenOneAndTwo
            ));
        }

        bufferedReader.close();
        return prioritySum;
    }

    public static char getSharedCharIn(String r1, String r2, String r3, Set<Character> setOne, Set<Character> r1R2Duplicates)
    {
        setOne.clear();
        setOne.addAll(r1.chars().mapToObj(c -> (char) c).toList());
        r1R2Duplicates.clear();
        for(Character c : setOne){
            if(r2.contains(c.toString())){
                r1R2Duplicates.add(c);
            }
        }
        for(char letter : r3.toCharArray()){
            if(r1R2Duplicates.contains(letter)){
                return letter;
            }
        }
        return 'a';
    }
    public static int getPriorityOf(char c)
    {
        for(int i = 0; i < prioArray.length; i++){
            if(prioArray[i] == c){
                return i + 1;
            }
        }
        return 0;
    }
}
