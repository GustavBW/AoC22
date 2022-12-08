package eighth;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Solver1 {

    public static final String filepath = "src/main/java/eighth/input.txt";

    public static void main(String[] args) throws IOException
    {
        long totalMS = 0;
        long timeA = System.nanoTime();
        for(int i = 0; i < 1_000_000; i++){
            countVisibleTrees();
        }
        totalMS += (System.nanoTime() - timeA) / 1_000_000;

        //1089,1059,1055,16 is not right
        //521 is too low,
        //1647 is right
        System.out.println("Amount of trees visible from the outside: " + countVisibleTrees());
        System.out.println("Solve time: " + totalMS + "ns");
    }

    public static int countVisibleTrees() throws IOException
    {
        byte[][] grid = readAsByteGrid();
        boolean[][] isVisibleGrid = getAsBoolGrid(grid);
        return countOccurrences(isVisibleGrid); //subtracting the corner trees which are counted
    }
    public static boolean[][] getAsBoolGrid(byte[][] grid){
        boolean[][] isVisibleGrid = new boolean[grid.length][grid[0].length];

        markVertical(grid,isVisibleGrid,0,1);
        markVertical(grid,isVisibleGrid,grid.length-1,-1);
        markHorizontal(grid,isVisibleGrid, 0,1);
        markHorizontal(grid,isVisibleGrid, grid[0].length-1,-1);

        return isVisibleGrid;
    }
    public static byte[][] readAsByteGrid() throws IOException
    {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath));
        String currentLine;
        byte[][] grid = new byte[99][];
        int index = 0;

        while((currentLine = bufferedReader.readLine()) != null){
            grid[index] = currentLine.getBytes();
            index++;
        }
        return grid;
    }

    private static int countOccurrences(boolean[][] visibilityGrid){
        int sum = 0;
        for(boolean[] row : visibilityGrid){
            for(boolean b : row){
                if(b){sum++;}
            }
        }
        return sum;
    }

    private static void markVertical(byte[][] grid, boolean[][] visibilityGrid, int startY, int increment)
    {
        byte current = 0, highest = -1;

        for(int x = 0; x < grid[0].length; x++){
            for(int y = startY; y >= 0 && y < grid.length; y += increment){
                current = grid[y][x];
                if(current <= highest){continue;}
                visibilityGrid[y][x] = true;
                highest = current;
            }
            highest = -1; //resetting
        }
    }
    private static void markHorizontal(byte[][] grid, boolean[][] visibilityGrid, int startX, int increment)
    {
        byte current = 0, highest = -1;

        for (int y = 0; y < grid.length; y++) {
            for (int x = startX; x >= 0 && x < grid[y].length; x += increment) {
                current = grid[y][x];
                if (current <= highest) {continue;}
                visibilityGrid[y][x] = true;
                highest = current;
            }
            highest = -1;
        }
    }
    public static void print2DGrid(byte[][] grid){
        System.out.println();
        for(byte[] row : grid){
            for(byte b : row){
                System.out.print(b + " ");
            }
            System.out.println();
        }
    }
    public static void print2DGrid(boolean[][] grid){
        System.out.println();
        for(boolean[] row: grid){
            for(boolean b: row){
                System.out.print(b ? "Y " : "  ");
            }
            System.out.println();
        }
    }

}
