package first;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Solver2 {

    /*
        Find the top three Elves carrying the most Calories. How many Calories are those Elves carrying in total?
     */

    public static final String filepath = "src/main/java/first/input.txt";

    public static void main(String[] args)
    {
        System.out.println(new Solver2().getCalorieCountOfTopThree(filepath));
    }

    public int getCalorieCountOfTopThree(String filepath)
    {
        int[] topThree = new int[3];

        try{
            BufferedReader br = new BufferedReader(new FileReader(filepath));

            String currentLine = "";
            int currentElfCalCount = 0;

            while((currentLine = br.readLine()) != null){
                if(!currentLine.equals("")){

                    currentElfCalCount += Integer.parseInt(currentLine);

                }else{  //New elf encountered in file. Begin new count.

                    replaceIndexIfLessThan(currentElfCalCount,topThree);
                    currentElfCalCount = 0;
                }
            }

            br.close();
        }catch (IOException | NumberFormatException | NullPointerException e){
            e.printStackTrace();
        }

        return topThree[0]+topThree[1]+topThree[2];
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
