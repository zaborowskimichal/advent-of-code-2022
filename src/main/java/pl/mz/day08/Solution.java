package pl.mz.day08;

import pl.mz.tools.FileProcessor;

import java.util.ArrayList;
import java.util.Arrays;


public class Solution {
    public static void main(String[] args) {
        String fileString = FileProcessor.readFileToString("src/main/java/pl/mz/day08/input.txt");
        int[][] board = generateBoard(fileString);
        ArrayList<Integer> results = findVisible(board);
        System.out.println("Part 1: " + results.size());
    }

    public static int[][] generateBoard(String input) {
        String[] lines = FileProcessor.splitStringByLine(input);
        int[][] board = new int[lines.length][lines[0].length()];
        for (int i = 0; i < lines.length; i++) {
            board[i] = Arrays.stream(lines[i].split("")).mapToInt(Integer::parseInt).toArray();
        }
        return board;
    }

    public static ArrayList<Integer> findVisible(int[][] board) {
        ArrayList<Integer> results = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            if (i == 0 || i == board.length - 1) {
                results.addAll(Arrays.stream(board[i]).boxed().toList());
            } else {
                results.add(board[i][0]);
                results.add(board[i][board.length - 1]);
                results.addAll(findVisible(board, i));
            }
        }
        return results;
    }
    public static ArrayList<Integer> findVisible(int[][] board, int rowNo) {
        ArrayList<Integer> results = new ArrayList<>();
        for (int i = 1; i < board.length - 1; i++) {
            int current = board[rowNo][i];
            int[] leftSide = Arrays.copyOfRange(board[rowNo], 0, i);
            int[] rightSide = Arrays.copyOfRange(board[rowNo], i + 1, board[rowNo].length);
            int leftMax = Arrays.stream(leftSide).max().getAsInt();
            int rightMax = Arrays.stream(rightSide).max().getAsInt();
            if (current > leftMax || current > rightMax) {
                results.add(current);
            } else {
                int[] column = new int[board.length];
                for (int j = 0; j < board.length; j++) {
                    column[j] = board[j][i];
                }
                int[] upperSide = Arrays.copyOfRange(column, 0, rowNo);
                int[] bottomSide = Arrays.copyOfRange(column, rowNo + 1, column.length);
                int upperMax = Arrays.stream(upperSide).max().getAsInt();
                int bottomMax = Arrays.stream(bottomSide).max().getAsInt();
                if (current > upperMax || current > bottomMax) {
                    results.add(current);
                }
            }
        }

        return results;
    }

}
