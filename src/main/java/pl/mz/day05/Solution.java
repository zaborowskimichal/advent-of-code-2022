package pl.mz.day05;

import pl.mz.tools.FileProcessor;
import pl.mz.tools.MyStack;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Solution {

    public static void main(String[] args) {
        String fileString = FileProcessor.readFileToString("src/main/java/pl/mz/day05/input.txt");
        String[] fileList = fileString.split("\\r\\n[\\r\\n]+");

        ArrayList<MyStack<Character>> stacks = generateStack(fileList[0]);
        ArrayList<int[]> commandsList = generateCommands(fileList[1]);
        for (int[] commands : commandsList) {
            int movesQuantity = commands[0];
            int fromIndex = commands[1] - 1;
            int toIndex = commands[2] - 1;
            for (int i = 1; i <= movesQuantity; i++) {
                stacks.get(toIndex).push(stacks.get(fromIndex).pop());
            }
        }
        System.out.print("Part 1: ");
        stacks.forEach(e -> System.out.print(e.top()));
        System.out.println();

        stacks = generateStack(fileList[0]);
        for (int[] commands : commandsList) {
            int movesQuantity = commands[0];
            int fromIndex = commands[1] - 1;
            int toIndex = commands[2] - 1;
            stacks.get(toIndex).addReversedList(stacks.get(fromIndex).removeSublist(movesQuantity));
        }
        System.out.print("Part 2: ");
        stacks.forEach(e -> System.out.print(e.top()));

    }

    private static ArrayList<int[]> generateCommands(String input) {
        ArrayList<int[]> results = new ArrayList<>();
        ArrayList<String> commandsStrings = FileProcessor.splitLines(input, "\\r\\n");
        for (String command : commandsStrings) {
            Pattern p = Pattern.compile("\\d+");
            Matcher m = p.matcher(command);
            int index = 0;
            int[] temp = new int[3];
            while (m.find()) {
                temp[index] = Integer.parseInt(m.group());
                index++;
            }
            results.add(temp);
        }
        return results;
    }


    public static ArrayList<MyStack<Character>> generateStack(String input) {
        ArrayList<MyStack<Character>> output = new ArrayList<>();
        ArrayList<LinkedList<Character>> tempList = new ArrayList<>();
        ArrayList<String> inputList = FileProcessor.splitLines(input, "\\r\\n");
        HashMap<Integer, Integer> indexes = findValueIndexes(inputList.remove(inputList.size() - 1));
        indexes.entrySet().forEach(e -> tempList.add(new LinkedList<>()));
        for (String line : inputList) {
            for (Map.Entry<Integer, Integer> entry : indexes.entrySet()) {
                int key = entry.getKey();
                int value = entry.getValue();
                if (line.length() > value) {
                    char c = line.charAt(value);
                    if (c != ' ')
                        tempList.get(key - 1).push(c);
                }
            }
        }

        for (LinkedList<Character> list : tempList) {
            output.add(new MyStack<>(list));
        }
        return output;
    }

    public static HashMap<Integer, Integer> findValueIndexes(String line) {
        String[] trimmed = line.trim().replaceAll(" +", " ").split(" ");
        return (HashMap<Integer, Integer>) Arrays.stream(trimmed)
                .map(Integer::parseInt)
                .collect(Collectors.toMap(e -> e, e -> e != 1 ? (e - 1) * 4 + 1 : 1));
    }
}
