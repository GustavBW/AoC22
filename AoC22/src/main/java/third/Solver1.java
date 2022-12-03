package third;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;

public class Solver1 {

    public static final String filepath = "src/main/java/third/input.txt";

    /*
    Find the item type that appears in both compartments of each rucksack.
    What is the sum of the priorities of those item types?
     */

    public static void main(String[] args) throws IOException {
        System.out.println("Sum of priorities of items in both compartments are: " + getSumOfPriorities());
    }

    public static int getSumOfPriorities() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath));

        String currentLine;
        int prioritySum = 0;

        while((currentLine = bufferedReader.readLine()) != null){
            String[] compartments = getCompartments(currentLine);
            byte[] inBoth = findDublicate(compartments);
            prioritySum += getPriority(inBoth);
        }

        return prioritySum;
    }

    private static byte[] findDublicate(String[] compartments) {
        for(int i = 0; i < compartments[0].length(); i++){
            for(int j = 0; j < compartments[1].length(); j++){
                if((compartments[0].charAt(i)+"").equalsIgnoreCase(compartments[1].charAt(j)+"")){
                    return new byte[]{
                            (byte) compartments[0].charAt(i),
                            (byte) compartments[1].charAt(j)
                    };
                }
            }
        }
        return new byte[2];
    }

    public static String[] getCompartments(String s)
    {
        String[] compartments = new String[2];
        int middle = (int) Math.floor(s.length() / 2.0);
        compartments[0] = s.substring(0,middle);
        compartments[1] = s.substring(middle);
        return compartments;
    }

    public static int getPriority(byte[] bArr)
    {
        int sum = 0;
        for(byte b : bArr){
            if(b < 96){
                sum += b - 64;
            }else{
                sum += b - 96;
            }
        }
        System.out.println("equals: " + (char) bArr[1] + " " + (char) bArr[0]);
        return sum;
    }
}
