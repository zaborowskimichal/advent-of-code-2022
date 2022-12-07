package pl.mz.day07;

import pl.mz.tools.FileProcessor;
import pl.mz.tools.Tree;

import java.util.LinkedList;


public class Solution {

    public static LinkedList<Tree<Integer>> countedList = new LinkedList<>();
    public static LinkedList<Tree<Integer>> directoryList = new LinkedList<>();

    public static void main(String[] args) {
        String fileString = FileProcessor.readFileToString("src/main/java/pl/mz/day07/input.txt");
        String[] fileLines = FileProcessor.splitStringByLine(fileString);
        Tree<Integer> rootDirectory = new Tree<>("/", 0);
        Tree<Integer> currentDirectory = rootDirectory;

        for (int i = 1; i < fileLines.length; i++) {
            String currentLine = fileLines[i];
            if (checkIsCommand(currentLine)) {
                if (isChangingDirectory(currentLine)) {
                    if (getChangeDirectoryPath(currentLine).equals("..")) {
                        currentDirectory = currentDirectory.getParent();
                    } else {
                        currentDirectory = currentDirectory.getNode(getChangeDirectoryPath(currentLine));
                    }
                }
            } else {
                currentDirectory.addNode(generateNode(currentLine));
            }
        }
        calculateDirectoriesMemory(rootDirectory);
        findTreesBelowMax(rootDirectory, 100000);
        System.out.println("Part 1: " + countedList.stream().mapToInt(Tree::getValue).sum());

        findDirectories(rootDirectory);
        directoryList.removeFirst();
        System.out.println(rootDirectory.getValue());
        directoryList.forEach(e -> System.out.println(e.getName() + " " + e.getValue()));
    }


    public static boolean checkIsCommand(String line) {
        return line.charAt(0) == '$';
    }

    public static boolean isChangingDirectory(String command) {
        return command.contains("cd");
    }

    public static String getChangeDirectoryPath(String text) {
        String[] result = text.split(" ");
        return result[2];
    }

    public static Tree<Integer> generateNode(String input) {
        String[] params = input.split(" ");
        String name = params[1];
        int value = 0;
        if (!params[0].contains("dir"))
            value = Integer.parseInt(params[0]);
        return new Tree<>(name, value);
    }

    public static void findTreesBelowMax(Tree<Integer> root, int max) {
        if (root.getValue() <= max && root.getNodesSize() != 0)
            countedList.add(root);
        root.getNodes().forEach(e -> findTreesBelowMax(e, max));
    }

    public static void findDirectories(Tree<Integer> root) {
        if (root.getNodesSize() != 0)
            directoryList.add(root);
        root.getNodes().forEach(Solution::findDirectories);
    }


    public static void calculateDirectoriesMemory(Tree<Integer> root) {
        root.getNodes().forEach(Solution::calculateDirectoriesMemory);
        root.setValue(sumInDirectory(root));
    }

    public static int sumInDirectory(Tree<Integer> tree) {
        if (tree.getNodesSize() != 0)
            return tree.getNodes().
                    stream()
                    .mapToInt(Tree::getValue)
                    .sum();
        else
            return tree.getValue();
    }
}
