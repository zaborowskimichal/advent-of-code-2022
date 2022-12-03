package pl.mz.day04;

import pl.mz.tools.FileProcessor;

import java.util.LinkedList;

public class Solution {
    public static void main(String[] args) {
        LinkedList<String> fileList = FileProcessor.readFile("src/main/java/pl/mz/day04/input.txt");
        System.out.println(fileList);

    }


}
