package pl.mz.day03;

import pl.mz.tools.FileProcessor;

import java.util.LinkedList;

public class Solution {

    public static void main(String[] args) {
        LinkedList<String> fileList = FileProcessor.readFile("src/main/java/pl/mz/day03/input.txt");
        System.out.println(fileList);

    }
}
