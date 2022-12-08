package pl.mz.day07;

import pl.mz.tools.FileProcessor;
import pl.mz.tools.Tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;


public class Solution {

    public static ArrayList<Tree<Integer>> countedList = new ArrayList<>();
    public static ArrayList<Tree<Integer>> directoriesList = new ArrayList<>();

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
        findTreesBelowMax(rootDirectory, 100000, countedList);
        System.out.println("Part 1: " + countedList.stream().mapToInt(Tree::getValue).sum());

        findDirectories(rootDirectory);
        directoriesList.remove(0);
        ArrayList<Integer> resultList = (ArrayList<Integer>) directoriesList
                .stream()
                .map(Tree::getValue)
                .collect(Collectors.toList());
        Collections.sort(resultList);
        int toDelete = 30_000_000 - 70_000_000 + rootDirectory.getValue();
        int output = resultList.stream().filter(e -> e > toDelete).findFirst().get();
        System.out.println("Part 2: " + output);
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

    public static void findTreesBelowMax(Tree<Integer> root, int max, ArrayList<Tree<Integer>> list) {
        if (root.getValue() <= max && root.getNodesSize() != 0) {
            list.add(root);
        }
        root.getNodes().forEach(e -> findTreesBelowMax(e, max, list));
    }

    public static void findDirectories(Tree<Integer> root) {
        if (root.getNodesSize() != 0)
            directoriesList.add(root);
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
