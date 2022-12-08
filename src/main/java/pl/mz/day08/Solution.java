package pl.mz.day08;

import pl.mz.tools.FileProcessor;
import pl.mz.tools.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;


public class Solution {
    public static void main(String[] args) {
        String fileString = FileProcessor.readFileToString("src/main/java/pl/mz/day08/input.txt");
        int[][] board = generateBoard(fileString);
        ArrayList<Position<Integer>> results = findVisibleRow(board);
        System.out.println("Part 1: " + results.size());

        ArrayList<Position<Integer>> insideResults = (ArrayList<Position<Integer>>) results.stream()
                .filter(e -> e.getX() != 0 && e.getX() != board.length - 1 && e.getY() != 0 && e.getY() != board[0].length - 1)
                .collect(Collectors.toList());
        int maxVisibility = insideResults.stream().mapToInt(e -> calcVisibility(e,board)).max().getAsInt();
        System.out.println("Part 2: " + maxVisibility);
    }

    public static int[][] generateBoard(String input) {
        String[] lines = FileProcessor.splitStringByLine(input);
        int[][] board = new int[lines.length][lines[0].length()];
        for (int i = 0; i < lines.length; i++) {
            board[i] = Arrays.stream(lines[i].split("")).mapToInt(Integer::parseInt).toArray();
        }
        return board;
    }

    public static ArrayList<Position<Integer>> findVisibleRow(int[][] board) {
        ArrayList<Position<Integer>> results = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            if (i == 0 || i == board.length - 1) {
                for (int k = 0; k < board[i].length; k++) {
                    results.add(new Position<>(i, k, board[i][k]));
                }
            } else {
                results.add(new Position<>(i, 0, board[i][0]));
                results.add(new Position<>(i, board.length - 1, board[i][board.length - 1]));
                results.addAll(findVisibleRow(board, i));
            }
        }
        return results;
    }

    public static ArrayList<Position<Integer>> findVisibleRow(int[][] board, int rowNo) {
        ArrayList<Position<Integer>> results = new ArrayList<>();
        for (int i = 1; i < board.length - 1; i++) {
            int current = board[rowNo][i];
            int[][] sides = splitArr(board[rowNo], i);
            int[] leftSide = sides[0];
            int[] rightSide = sides[1];
            int leftMax = Arrays.stream(leftSide).max().getAsInt();
            int rightMax = Arrays.stream(rightSide).max().getAsInt();
            if (current > leftMax || current > rightMax) {
                results.add(new Position<>(rowNo, i, current));
            } else {
                int[] column = mapColumn(board, i);
                sides = splitArr(column, rowNo);
                int[] upperSide = sides[0];
                int[] bottomSide = sides[1];
                int upperMax = Arrays.stream(upperSide).max().getAsInt();
                int bottomMax = Arrays.stream(bottomSide).max().getAsInt();
                if (current > upperMax || current > bottomMax) {
                    results.add(new Position<>(rowNo, i, current));
                }
            }
        }
        return results;
    }

    public static int[][] splitArr(int[] arr, int split) {
        return new int[][]{Arrays.copyOfRange(arr, 0, split), Arrays.copyOfRange(arr, split + 1, arr.length)};
    }

    public static int[] mapColumn(int[][] arr, int columnNo) {
        int[] column = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            column[i] = arr[i][columnNo];
        }
        return column;
    }

    public static int calcVisibility(Position<Integer> position, int[][] board) {
        int[][] columnSides = splitArr(mapColumn(board, position.getY()), position.getX());
        int[][] rowSides = splitArr(board[position.getX()], position.getY());

        int upper = iterateOverArr(columnSides[0], true, position.getValue());
        int bottom = iterateOverArr(columnSides[1], false, position.getValue());
        int left = iterateOverArr(rowSides[0], true, position.getValue());
        int right = iterateOverArr(rowSides[1], false, position.getValue());

        return upper * bottom * left * right;
    }

    public static int iterateOverArr(int[] arr, boolean beginIndex, int compareValue) {
        int howMany = 0;
        for (int i = 0; i < arr.length; i++) {
            howMany++;
            if (beginIndex) {
                if (compareValue <= arr[arr.length - 1 - i]) {
                    return howMany;
                }
            } else {
                if (compareValue <= arr[i]) {
                    return howMany;
                }
            }
        }
        return howMany;
    }

}
