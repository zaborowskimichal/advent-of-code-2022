package pl.mz.day10;

import pl.mz.tools.FileProcessor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Solution {
    public static void main(String[] args) {
        LinkedList<String> fileList = (LinkedList<String>) FileProcessor.readFileToList("src/main/java/pl/mz/day10/input.txt");
        ArrayList<Integer> cycleList = new ArrayList<>();
        ArrayList<Integer> resultsList = new ArrayList<>(Arrays.asList(20, 60, 100, 140, 180, 220));
        int totalValue = 1;

        for (String command : fileList) {
            if (command.contains("noop"))
                cycleList.add(totalValue);
            else {
                cycleList.add(totalValue);
                int addValue = calculateAddValue(command);
                totalValue += addValue;
                cycleList.add(totalValue);
            }
        }

        System.out.println("Part 1: " + resultsList.stream().mapToInt(e -> e * cycleList.get(e - 1)).sum());
    }


    public static int calculateAddValue(String command) {
        String[] divided = command.split(" ");
        return Integer.parseInt(divided[1]);
    }
}
