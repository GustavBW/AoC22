package ninth;

import javax.imageio.IIOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Solver1 {
    public static final String filepath = "src/main/java/ninth/input.txt";

    public static void main(String[] args) throws IOException {
        long totalMS = 0;
        long timeA = System.nanoTime();
        /*for(int i = 0; i < 1_000_000; i++){
            getAmountOfPositionsOfTail();
        }
        totalMS += (System.nanoTime() - timeA) / 1_000_000;

         */
        //1629, 6444 is too low
        System.out.println("Amount of unique positions of tail: " + evenBetterSolution());
        System.out.println("Solve time: " + totalMS + "ns");
    }
    private record Vec2(int x, int y) implements Comparable<Vec2>{
        @Override
        public int compareTo(Vec2 o) {
            return Integer.compare(
                    Integer.compare(this.x(),o.x()),
                    Integer.compare(this.y(),o.y())
            );
        }
    }
    private static final Map<String, Vec2> directions = new HashMap<>(Map.of(
            "D",new Vec2(0,-1),
            "L",new Vec2(-1,0),
            "U",new Vec2(0,1),
            "R",new Vec2(1,0)
    ));
    private static final double sqrt2 = Math.sqrt(2);

    public static int getAmountOfPositionsOfTail() throws IOException
    {
        BufferedReader br = new BufferedReader(new FileReader(filepath));
        Set<Vec2> tailPositions = new HashSet<>();
        Vec2 lastKnownTailPosition = new Vec2(0,0);
        int[] headPosition = new int[2];
        String currentLine;

        while((currentLine = br.readLine()) != null){
            String[] split = currentLine.split(" ");
            int magnitudeOfMove = Integer.parseInt(split[1]);
            Vec2 direction = directions.get(split[0]);

            int[] newHeadPosition = new int[]{
                    headPosition[0] + direction.x() * magnitudeOfMove,
                    headPosition[1] + direction.y() * magnitudeOfMove
            };

            int xDiff = Math.abs(lastKnownTailPosition.x() - newHeadPosition[0]);
            int yDiff = Math.abs(lastKnownTailPosition.y() - newHeadPosition[1]);
            Vec2 latestTailPosition = null;
            if(xDiff >= 2){
                for(int i = 0; i < xDiff; i++){
                    latestTailPosition = new Vec2(
                            lastKnownTailPosition.x() + direction.x() * i,
                            lastKnownTailPosition.y()
                    );
                    tailPositions.add(latestTailPosition);
                }
            }else if(yDiff >= 2){
                for(int i = 0; i < yDiff; i++){
                    latestTailPosition = new Vec2(
                            lastKnownTailPosition.x(),
                            lastKnownTailPosition.y() + direction.y() * i
                    );
                    tailPositions.add(latestTailPosition);
                }
            }


            //lastKnownTailPosition = goodSolution(tailPositions, lastKnownTailPosition, magnitudeOfMove, direction, newHeadPosition);
            headPosition = newHeadPosition;
        }
        //plotAndPrint(tailPositions);
        return tailPositions.size();
    }

    private static int evenBetterSolution() throws IOException
    {
        int[] tx = new int[10];
        int[] ty = new int[10];
        BufferedReader br = new BufferedReader(new FileReader(filepath));
        Set<String> marked = new HashSet<>();
        String line = "";

        while((line = br.readLine()) != null) {
            String[] p = line.split(" ");
            int dir = ((line.charAt(0) / 3) - 1) & 3;
            int mag = (-1 + (dir >> 1) * 2);
            for (int step = 0; step < Integer.parseInt(p[1]); step++) {
                tx[0] += mag * (~dir & 1);
                ty[0] += mag * (dir & 1);
                for (int ptr = 1; ptr < 10; ptr++) {
                    int dx = tx[ptr - 1] - tx[ptr];
                    int dy = ty[ptr - 1] - ty[ptr];
                    if (Math.abs(dx) == 2 || Math.abs(dy) == 2) {
                        tx[ptr] += Integer.signum(dx);
                        ty[ptr] += Integer.signum(dy);
                    }
                }
                marked.add(tx[9] + "," + ty[9]);
            }
        }
        return marked.size();
    }

    private static Vec2 goodSolution(Set<Vec2> tailPositions, Vec2 lastKnownTailPosition, int magnitudeOfMove, Vec2 direction, int[] newHeadPosition) {
        double distSQ = (lastKnownTailPosition.x() - newHeadPosition[0]) * (lastKnownTailPosition.x() - newHeadPosition[0])
                + (lastKnownTailPosition.y() - newHeadPosition[1]) * (lastKnownTailPosition.y() - newHeadPosition[1]);

        if(distSQ > 2){
            Vec2 updatedPosition = null;
            for(int i = 0; i < magnitudeOfMove; i++){
                updatedPosition = new Vec2(
                        lastKnownTailPosition.x() + (direction.x() * i),
                        lastKnownTailPosition.y() + (direction.y() * i)
                );
                tailPositions.add(updatedPosition);
            }
            lastKnownTailPosition = updatedPosition;
        }
        return lastKnownTailPosition;
    }

    public static void plotAndPrint(Set<Vec2> positions)
    {
        Vec2[] asArray = positions.toArray(new Vec2[0]);
        int[] extremes = new int[4];
        for(Vec2 vec: asArray){
            extremes[0] = vec.x() > extremes[0] ? vec.x() : extremes[0];
            extremes[1] = vec.y() > extremes[1] ? vec.y() : extremes[1];
            extremes[2] = vec.x() < extremes[2] ? vec.x() : extremes[2];
            extremes[3] = vec.y() < extremes[3] ? vec.y() : extremes[3];
        }
        System.out.println("Extremes: " + asString(extremes));

        int[] offsetXY = new int[]{
                Math.abs(extremes[2]),Math.abs(extremes[3])
        };
        System.out.println("Offsets: " + asString(offsetXY));

        int[] gridSizeXY = new int[]{
                extremes[0] + offsetXY[0], extremes[1] + offsetXY[1]
        };
        System.out.println("GridSize: " + asString(gridSizeXY));
        boolean[][] grid = new boolean[gridSizeXY[1] + 1][gridSizeXY[0] + 1];
        for(Vec2 vec: asArray){
            grid[vec.y() + offsetXY[1]][vec.x() + offsetXY[0]] = true;
        }
        System.out.println();
        for(boolean[] row: grid){
            for(boolean b: row){
                System.out.println(b ? "T" : " ");
            }
            System.out.println();
        }
    }
    public static String asString(int[] array){
        String asString = "";
        for(int i : array){
            asString += i+",";
        }
        return asString;
    }
}
