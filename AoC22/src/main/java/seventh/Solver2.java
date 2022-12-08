package seventh;

import java.io.IOException;
import java.util.*;

public class Solver2 {

    public static void main(String[] args) throws IOException
    {
        long totalMS = 0;
        long timeA = System.nanoTime();
        for(int i = 0; i < 1_000; i++){
            getSizeOfSmallestViableDir();
        }
        totalMS += (System.nanoTime() - timeA) / 1_000;

        //44974370 is too high
        //1274107 is too low
        System.out.println("Size of smallest dir above delete threshold: " + getSizeOfSmallestViableDir());
        System.out.println("Solve time: " + totalMS + "ns");
    }

    public static int getSizeOfSmallestViableDir() throws IOException
    {
        seventh.Solver1.ElfDir rootDir = seventh.Solver1.loadFileTree();

        int totalCapacity = 70_000_000; //70MB
        int freeSpace = totalCapacity - rootDir.size;
        int updateSize = 30_000_000; //30MB
        int deleteThreshold = updateSize - freeSpace;

        Queue<Solver1.ElfDir> queue = new ArrayDeque<>(rootDir.subDirs);
        Solver1.ElfDir currentDir;
        List<Solver1.ElfDir> dirsThatQualifies = new ArrayList<>();

        while((currentDir = queue.poll()) != null){
            if(currentDir.size >= deleteThreshold){
                dirsThatQualifies.add(currentDir);
            }
            queue.addAll(currentDir.subDirs);
        }

        dirsThatQualifies.sort(Comparator.comparingInt(o -> o.size));
        return dirsThatQualifies.get(0).size;
    }

}
