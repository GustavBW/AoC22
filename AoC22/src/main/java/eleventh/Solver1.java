package eleventh;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Solver1 {

    private static final String filepath = "src/main/java/eleventh/input.txt";
    @FunctionalInterface
    private interface BoolFunc{
        boolean throwToAorB(int item);
    }
    @FunctionalInterface
    private interface IntFunc{
        int modifyWorryLevelOfItem(int item);
    }

    private static class Monke{
        private List<Integer> items;
        private IntFunc operation;
        private BoolFunc throwTest;
        private int recipientA, recipientB,number,businessScore = 0;
        public Monke(List<Integer> items, IntFunc operation, BoolFunc throwTest, int recipientA, int recipientB) {
            this.items = items;
            this.operation = operation;
            this.throwTest = throwTest;
            this.recipientA = recipientA;
            this.recipientB = recipientB;
        }
        public Monke(){}
        public List<Integer> items() {
            return items;
        }
        public int number(){
            return number;
        }
        public void setNumber(int number){
            this.number = number;
        }
        public void setItems(List<Integer> items) {
            this.items = items;
        }
        public IntFunc operation() {
            return operation;
        }
        public void setOperation(IntFunc operation) {
            this.operation = operation;
        }
        public BoolFunc throwTest() {
            return throwTest;
        }
        public void setThrowTest(BoolFunc throwTest) {
            this.throwTest = throwTest;
        }
        public int recipientA() {
            return recipientA;
        }
        public void setRecipientA(int recipientA) {
            this.recipientA = recipientA;
        }
        public int recipientB() {
            return recipientB;
        }
        public void setRecipientB(int recipientB) {
            this.recipientB = recipientB;
        }
        public void setBusinessScore(int num){
            this.businessScore = num;
        }
        public int businessScore(){
            return businessScore;
        }
        @Override
        public String toString(){
            return "Monke "+number+", score: "+businessScore+" items: " + items + " a: " + recipientA + " b: " + recipientB + "\n";
        }
    }

    public static void main(String[] args) throws IOException {
        long totalMS = 0;
        long timeA = System.nanoTime();
        /*for(int i = 0; i < 1_000_000; i++){
            run20RoundsAndGetScore();
        }
        totalMS += (System.nanoTime() - timeA) / 1_000_000;

         */
        //8531049600,251959533040215000 is too high
        //52437 is not right    
        //39725 is too low
        System.out.println("Monke business score after 20 rounds: " + run20RoundsAndGetScore());
        System.out.println("Solve time: " + totalMS + "ns");
    }

    private static long run20RoundsAndGetScore() throws IOException
    {
        List<Monke> monkes = loadMonkeys();
        System.out.println("Monkes: \n" + monkes);
        runMonkeRounds(20,monkes);
        System.out.println("Monkes: \n" + monkes);
        return product(getTheNHighestOf(extractMonkeBusinessScores(monkes),2));
    }

    private static int[] getTheNHighestOf(int[] array, int num)
    {
        List<Integer> asList = new ArrayList<>();
        for(int i : array){
            asList.add(i);
        }
        asList.sort(Integer::compare);
        Collections.reverse(asList);
        return asList.subList(0,num)
                .stream().mapToInt(Integer::intValue)
                .toArray();
    }

    private static long product(int[] array)
    {
        long toReturn = 1;
        for(int i: array){
            toReturn *= i;
        }
        return toReturn;
    }

    private static int[] extractMonkeBusinessScores(List<Monke> monkeList)
    {
        int[] toReturn = new int[monkeList.size()];
        for(int i = 0; i < monkeList.size(); i++){
            toReturn[i] = monkeList.get(i).businessScore();
        }
        return toReturn;
    }

    private static long getTotalBusinessScore(List<Monke> monkeList)
    {
        long score = 1;
        for(Monke monke: monkeList){
            score *= monke.businessScore();
        }
        return score;
    }

    private static void runMonkeRounds(int rounds, List<Monke> monkeList)
    {
        monkeList.sort(Comparator.comparingInt(Monke::number));
        for(int round = 0; round < rounds; round++){
            for(Monke monke: monkeList){
                takeMonkeTurn(monke, monkeList);
            }
        }
    }

    private static void takeMonkeTurn(Monke monke,List<Monke> allMonkes)
    {
        for(int i = 0; i < monke.items().size(); i++){
            int currentItem = monke.items().get(i);
            //Inspect - Apply operation
            monke.items().set(i,
                    monke.operation().modifyWorryLevelOfItem(currentItem)
            );
            //Apply relief
            monke.items().set(i,
                    applyRelief(currentItem)
            );
            //throw
            if(monke.throwTest().throwToAorB(currentItem)){
                throwItemToMonkeX(
                        monke.recipientA(),
                        currentItem,
                        allMonkes);
            }else{
                throwItemToMonkeX(
                        monke.recipientB(),
                        currentItem,
                        allMonkes);
            }
        }
        monke.setBusinessScore(monke.businessScore() + monke.items().size());
        //Each monke throws all items always
        monke.items().clear();
    }


    private static List<Monke> loadMonkeys() throws IOException
    {
        BufferedReader br = new BufferedReader(new FileReader(filepath));
        String currentLine;
        List<Monke> monkeList = new ArrayList<>();
        Monke currentMonke = new Monke();

        while((currentLine = br.readLine()) != null){
            String[] split = currentLine.split(" ");

            if(currentLine.contains("Monkey")){
                monkeList.add(currentMonke);
                currentMonke = new Monke();
                currentMonke.setNumber(
                        Integer.parseInt(
                                split[1].replace(":","")));
                continue;
            }
            if(currentLine.contains("Starting items")){
                currentMonke.setItems(parseMonkeItems(currentLine,split));
                continue;
            }
            if(currentLine.contains("Operation")){
                currentMonke.setOperation(parseMonkeOperation(currentLine,split));
                continue;
            }
            if(currentLine.contains("Test")){
                currentMonke.setThrowTest(
                        e -> e % Integer.parseInt(split[split.length - 1]) == 0
                );
                continue;
            }
            if(currentLine.contains("true")){
                currentMonke.setRecipientA(
                        Integer.parseInt(
                                split[split.length - 1]));
                continue;
            }
            if(currentLine.contains("false")){
                currentMonke.setRecipientB(
                        Integer.parseInt(
                                split[split.length - 1]));
                continue;
            }
        }
        //Remove first monke as it is the null monke
        monkeList.remove(0);
        monkeList.add(currentMonke);
        return monkeList;
    }

    private static void throwItemToMonkeX(int monkeNum, int item, List<Monke> allMonkes)
    {
        for(Monke monke: allMonkes){
            if(monke.number() == monkeNum){
                monke.items().add(item);
            }
        }
    }

    private static int applyRelief(int base)
    {
        return (int) Math.floor(base/3.0);
    }

    private static IntFunc parseMonkeOperation(String line, String[] splitOnSpace)
    {
        if(containsDigit(line)){
            if(line.contains("*")){
                return e -> e * Integer.parseInt(splitOnSpace[splitOnSpace.length - 1]);
            }
            if(line.contains("+")){
                return e -> e + Integer.parseInt(splitOnSpace[splitOnSpace.length - 1]);
            }
        }else{
            if(line.contains("*")){
                return e -> e * e;
            }
            if(line.contains("+")){
                return e -> e + e;
            }
        }

        return e -> e;
    }
    private static List<Integer> parseMonkeItems(String line, String[] splitOnSpace)
    {
        String[] splitOnColon = line.split(":");
        List<Integer> items = new ArrayList<>();
        for(String s : splitOnColon[1].split(",")){
            items.add(Integer.parseInt(s.trim()));
        }
        return items;
    }

    private static boolean containsDigit(String s){
        char[] asCharArray = s.toCharArray();
        for(char c : asCharArray){
            if(Character.isDigit(c)){
                return true;
            }
        }
        return false;
    }

    private static String arrayToString(String[] array)
    {
        String asString = "";
        for(String s : array){
            asString += s + ",";
        }
        return asString;
    }

}
