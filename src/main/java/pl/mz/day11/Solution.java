package pl.mz.day11;

import pl.mz.tools.FileProcessor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.stream.Collectors;


public class Solution {
    public static void main(String[] args) {
        String fileString = FileProcessor.readFileToString("src/main/java/pl/mz/day11/input.txt");
        ArrayList<Monkey> monkeyList = initMonkeyList(fileString);
        System.out.println("Part 1: " + calculateResult(monkeyList, 20, true));
//        monkeyList = initMonkeyList(fileString);
//        System.out.println("Part 2: " + calculateResult(monkeyList, 10000, false));


    }

    public static long calculateResult(ArrayList<Monkey> list, int roundNumbers, boolean divideByThree) {
        for (int k = 1; k <= roundNumbers; k++) {
            for (int i = 0; i < list.size(); i++) {
                Monkey monkey = list.get(i);
                LinkedList<Long> itemList = monkey.getItems();
                LinkedList<Long> remainingItems = new LinkedList<>();
                while (itemList.size() != 0) {
                    monkey.incrementInspectionCounter();
                    Long item = itemList.pop();
                    Long worryLevel = monkey.calcWorryLevel(item, divideByThree);
                    int toMonkeyId = monkey.checkCondition(worryLevel);
                    if (monkey.getId() != toMonkeyId) {
                        list.get(toMonkeyId).addItem(worryLevel);
                    } else {
                        remainingItems.add(item);
                    }
                }
                monkey.addItems(remainingItems);
            }
        }

        System.out.println(list.stream()
                .map(Monkey::getInspectionCounter)
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList()));

        return list.stream()
                .map(Monkey::getInspectionCounter)
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList())
                .stream().limit(2)
                .reduce((a, b) -> a * b).get();
    }

    public static ArrayList<Monkey> initMonkeyList(String fileString) {
        return (ArrayList<Monkey>) splitToMonkeyStringList(fileString).stream()
                .map(Monkey::new)
                .collect(Collectors.toList());
    }


    public static ArrayList<String> splitToMonkeyStringList(String text) {
        ArrayList<String> result = new ArrayList<>();
        text = text.replaceAll("\n+", "\n");
        ArrayList<String> temp = FileProcessor.splitLStringToList(text, "\n");
        for (int i = 0; i < temp.size(); i = i + 6) {
            result.add(temp.subList(i, i + 6).stream().collect(Collectors.joining("\n")));
        }

        return result;
    }

    public static class Monkey {
        private int id;
        private LinkedList<Long> items;
        private int test;
        private char operationType;
        private int operationNumber;
        private long inspectionCounter;
        private boolean addIt;
        private int toIdIfTrue;
        private int toIdIFalse;

        public Monkey(String text) {
            String[] lines = FileProcessor.splitStringByLine(text);
            this.id = Character.getNumericValue(lines[0].charAt(lines[0].length() - 2));
            this.items = (LinkedList<Long>) FileProcessor.findLongs(lines[1]);
            String[] temp = lines[2].split(" ");
            if (temp[temp.length - 1].contains("old")) {
                this.addIt = true;
                this.operationNumber = 0;
            } else {
                this.operationNumber = FileProcessor.findNumbers(lines[2]).get(0);
                this.addIt = false;
            }
            temp = lines[2].split(" ");
            this.operationType = temp[temp.length - 2].charAt(0);
            this.test = FileProcessor.findNumbers(lines[3]).get(0);
            this.toIdIfTrue = FileProcessor.findNumbers(lines[4]).get(0);
            this.toIdIFalse = FileProcessor.findNumbers(lines[5]).get(0);
            this.inspectionCounter = 0;
        }

        public void addItem(Long item) {
            this.items.add(item);
        }

        public void addItems(LinkedList<Long> newItems) {
            this.items.addAll(newItems);
        }

        public int getId() {
            return id;
        }

        public void incrementInspectionCounter() {
            this.inspectionCounter++;
        }

        public long getInspectionCounter() {
            return this.inspectionCounter;
        }

        public LinkedList<Long> getItems() {
            return items;
        }

        public long calcWorryLevel(long item, boolean divideByThree) {
            long worryLevel = item;
            long secondValue = this.addIt ? item : this.operationNumber;
            switch (this.operationType) {
                case '*' -> worryLevel *= secondValue;
                case '+' -> worryLevel += secondValue;
            }
            if (divideByThree)
                worryLevel /= 3L;
            return worryLevel;
        }

        @Override
        public String toString() {
            return "Monkey{" +
                    "id=" + id +
                    ", items=" + items + '}';
        }

        public int checkCondition(Long worryLevel) {
            return worryLevel % this.test == 0 ? this.toIdIfTrue : this.toIdIFalse;
        }
    }
}
