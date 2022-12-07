package seventh;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Solver1 {

    public static final String filepath = "src/main/java/seventh/input.txt";

    public static void main(String[] args) throws IOException
    {
        /*long totalMS = 0;
        long timeA = System.nanoTime();
        for(int i = 0; i < 1_000_000; i++){
            getSizeOfDirsAbove100kSize();
        }
        totalMS += (System.nanoTime() - timeA) / 1_000_000;
         */
        System.out.println("Character count position of package start marker: " + getSizeOfDirsAbove100kSize());
        //System.out.println("Solve time: " + totalMS + "ns");
    }

    private static record ElfFile(int size, String name){};
    private static record ElfDir(String name, ElfDir parent, List<ElfDir> subDirs, List<ElfFile> files){};

    public static int getSizeOfDirsAbove100kSize() throws IOException
    {
        ElfDir rootDir = loadFileTree();

        System.out.println(rootDir);

        return 1;
    }

    public static ElfDir loadFileTree() throws IOException
    {
        BufferedReader reader = new BufferedReader(new FileReader(filepath));
        ElfDir rootDir = new ElfDir("/",null, new ArrayList<>(),new ArrayList<>());
        String currentLine = "";
        ElfDir currentWorkingDirectory = rootDir;

        while((currentLine = reader.readLine()) != null){
            String[] split = currentLine.split(" ");

            if(split.length == 3){
                if(split[2].equals("..")){
                    currentWorkingDirectory = currentWorkingDirectory.parent() == null ? rootDir : currentWorkingDirectory.parent();
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
                currentWorkingDirectory.subDirs().add(
                        new ElfDir(split[1], currentWorkingDirectory,new ArrayList<>(),new ArrayList<>())
                );
                return;
            }
            currentWorkingDirectory.files().add(
                    new ElfFile(Integer.parseInt(split[0]), split[1])
            );
        }
    }

    public static ElfDir traverseAndFindDir(ElfDir rootOfSearch, String name)
    {   //TODO: Do a breadth first search instead
        if(rootOfSearch.name().equals(name)){
            return rootOfSearch;
        }
        for(ElfDir dir : rootOfSearch.subDirs){
            if(dir.name().equals(name)){
                return dir;
            }
            traverseAndFindDir(dir,name);
        }
        return rootOfSearch;
    }

    public static int sizeOf(ElfDir dir)
    {
        int sum = 0;
        for(ElfFile file : dir.files()){
            sum += file.size();
        }
        return sum;
    }
}
