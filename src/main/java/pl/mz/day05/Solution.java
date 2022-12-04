package pl.mz.day05;

import pl.mz.tools.FileProcessor;

import java.util.List;

public class Solution {

    public static void main(String[] args) {
        List<String> fileList = FileProcessor.readFile("src/main/java/pl/mz/day05/input.txt");
        System.out.println(fileList);
    }
}
