package pl.mz.day06;

import pl.mz.tools.FileProcessor;

import java.util.HashSet;


public class Solution {

    public static void main(String[] args) {
        String fileString = FileProcessor.readFileToString("src/main/java/pl/mz/day06/input.txt");
        System.out.println("Part 1: " + findIndex(fileString,4));
        System.out.println("Part 2: " + findIndex(fileString,14));
    }

    public static int findIndex(String input, int markerSize) {
        for (int i = 0; i < input.length() - markerSize; i++) {
            HashSet<Character> markerSet = new HashSet<>();
            for (int j = i; j < i + markerSize; j++) markerSet.add(input.charAt(j));

            if (markerSet.size() == markerSize) return i + markerSize;
        }
        return -1;
    }

}
