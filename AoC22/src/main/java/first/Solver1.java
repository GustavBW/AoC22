package first;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Solver1 {

    /*
        Find the Elf carrying the most Calories. How many total Calories is that Elf carrying?
     */

    public static final String filepath = "src/main/java/first/input.txt";

    public static void main(String[] args)
    {
        System.out.println(new Solver1().getCalorieCount(filepath));
    }

    public int getCalorieCount(String filepath)
    {
        int highestElfCalCount = 0;
        try{
            BufferedReader br = new BufferedReader(new FileReader(filepath));

            String currentLine = "";
            int currentElfCalCount = 0;

            while((currentLine = br.readLine()) != null){
                if(!currentLine.equals("")){

                    currentElfCalCount += Integer.parseInt(currentLine);

                }else{  //New elf encountered in file. Begin new count.

                    if(currentElfCalCount > highestElfCalCount){
                        highestElfCalCount = currentElfCalCount;
                    }
                    currentElfCalCount = 0;
                }
            }

            br.close();
        }catch (IOException | NumberFormatException | NullPointerException e){
            e.printStackTrace();
        }
        return highestElfCalCount;
    }
}
