package eighth;

import java.io.IOException;

public class Solver2 {

    public static void main(String[] args) throws IOException
    {
        long totalMS = 0;
        long timeA = System.nanoTime();
        for(int i = 0; i < 1_000; i++){
            getHighestScenicScore();
        }
        totalMS += (System.nanoTime() - timeA) / 1_000;

        //392080 was right
        //2147083848 is too high
        System.out.println("Highest scenic score: " + getHighestScenicScore());
        System.out.println("Solve time: " + totalMS + "ns");
    }
    public static long getHighestScenicScore() throws IOException
    {
        byte[][] grid = eighth.Solver1.readAsByteGrid();
        long highestScenicScore = -1;

        //Excluding checking edges since they multiply the score with 0
        for(int y = 1; y < grid.length - 1; y++){
            for(int x = 1; x < grid[y].length - 1; x++){
                int scenicScore = getScenicScore(x,y,grid);
                if(scenicScore > highestScenicScore){
                    highestScenicScore = scenicScore;
                }
            }
        }
        return highestScenicScore;
    }
    private static int getScenicScore(int x, int y, byte[][] grid)
    {
        int countXPositive = treeViewHorizontal(grid,y,x,1);
        int countXNegative = treeViewHorizontal(grid,y,x,-1);
        int countYPositive = treeViewVertical(grid,y,x,1);
        int countYNegative = treeViewVertical(grid,y,x,-1);

        return countXPositive * countXNegative * countYPositive * countYNegative;
    }
    private static int treeViewHorizontal(byte[][] grid, int ySlice, int startX, int increment)
    {
        int sum = 0;
        byte treeHouseHeight = grid[ySlice][startX];

        for (int x = startX + increment; x >= 0 && x < grid[ySlice].length; x += increment) {
            sum++;
            if (grid[ySlice][x] >= treeHouseHeight) {break;}
        }

        return sum;
    }
    private static int treeViewVertical(byte[][] grid, int startY, int xSlice, int increment)
    {
        int sum = 0;
        byte treeHouseHeight = grid[startY][xSlice];

        for(int y = startY + increment; y >= 0 && y < grid.length; y += increment){
            sum++;
            if(grid[y][xSlice] >= treeHouseHeight){break;}
        }

        return sum;
    }
}
