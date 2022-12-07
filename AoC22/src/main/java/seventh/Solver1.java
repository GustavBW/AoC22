package seventh;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Solver1 {

    public static final String filepath = "src/main/java/seventh/input.txt";

    public static void main(String[] args) throws IOException
    {
        long totalMS = 0;
        long timeA = System.nanoTime();
        for(int i = 0; i < 1_000; i++){
            getSumOfSizesOfDirsOfAtMost100kSize();
        }
        totalMS += (System.nanoTime() - timeA) / 1_000;

        //44974370 is too high
        //1274107 is too low
        System.out.println("Sum of dir sizes for dirs above threshold: " + getSumOfSizesOfDirsOfAtMost100kSize());
        System.out.println("Solve time: " + totalMS + "ns");
    }

    private record ElfFile(int size, String name){}
    public static class ElfDir{
        public String name;
        public ElfDir parent;
        public List<ElfDir> subDirs;
        public List<ElfFile> files;
        public int size;
        public ElfDir(String name, ElfDir parent, List<ElfDir> subDirs, List<ElfFile> files, int size)
        {
            this.name = name;
            this.parent = parent;
            this.subDirs = subDirs;
            this.files = files;
            this.size = size;
        }
        @Override
        public String toString(){
            return "dir: " + name + " " + size + "bytes" + " | " + "\n\t" + subDirs.toString();
        }
    }

    public static int getSumOfSizesOfDirsOfAtMost100kSize() throws IOException
    {
        ElfDir rootDir = loadFileTree();
        int threshold = 100_000;

        int sumOfDirsAboveThreshold = 0;

        Queue<ElfDir> queue = new ArrayDeque<>(rootDir.subDirs);
        ElfDir currentDir;

        while((currentDir = queue.poll()) != null){
            sumOfDirsAboveThreshold += currentDir.size <= threshold ? currentDir.size : 0;
            queue.addAll(currentDir.subDirs);
        }

        return sumOfDirsAboveThreshold;
    }

    public static ElfDir loadFileTree() throws IOException
    {
        BufferedReader reader = new BufferedReader(new FileReader(filepath));
        ElfDir rootDir = new ElfDir("/",null, new ArrayList<>(),new ArrayList<>(),0);
        String currentLine;
        ElfDir currentWorkingDirectory = rootDir;

        while((currentLine = reader.readLine()) != null){
            String[] split = currentLine.split(" ");

            if(split.length == 3){
                if(split[2].equals("..")){
                    currentWorkingDirectory = currentWorkingDirectory.parent == null ? rootDir : currentWorkingDirectory.parent;
                    continue;
                }
                currentWorkingDirectory = traverseAndFindDir(currentWorkingDirectory,split[2]);
            }else if (split.length == 2){
                addDirOrFile(currentWorkingDirectory, split);
            }
        }

        reader.close();
        return rootDir;
    }

    private static void addDirOrFile(ElfDir currentWorkingDirectory, String[] split) {
        if(!split[0].equals("$")){
            if(split[0].equals("dir")){
                currentWorkingDirectory.subDirs.add(
                        new ElfDir(
                                split[1], currentWorkingDirectory,new ArrayList<>(),new ArrayList<>(), 0
                        )
                );
                return;
            }
            ElfFile newFile = new ElfFile(Integer.parseInt(split[0]), split[1]);
            currentWorkingDirectory.files.add(newFile);
            currentWorkingDirectory.size += newFile.size();
            propegateSizeIncrease(currentWorkingDirectory, newFile.size());
        }
    }

    public static void propegateSizeIncrease(ElfDir subDir, int sizeIncrease)
    {
        ElfDir parent;
        ElfDir current = subDir;
        while((parent = current.parent) != null){
            parent.size += sizeIncrease;
            current = parent;
        }
    }

    public static ElfDir traverseAndFindDir(ElfDir rootOfSearch, String name)
    {
        Queue<ElfDir> dirsToSearch = new ArrayDeque<>(rootOfSearch.subDirs);
        dirsToSearch.add(rootOfSearch);

        ElfDir currentDir;
        while((currentDir = dirsToSearch.poll()) != null){
            if(currentDir.name.equals(name)){
                return currentDir;
            }
            dirsToSearch.addAll(currentDir.subDirs);
        }
        return rootOfSearch;
    }

    public static int sizeOf(ElfDir dir)
    {
        int sum = 0;
        for(ElfFile file : dir.files){
            sum += file.size();
        }
        return sum;
    }
}
