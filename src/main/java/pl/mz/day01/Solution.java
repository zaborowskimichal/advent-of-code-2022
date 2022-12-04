package pl.mz.day01;

import pl.mz.tools.FileProcessor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Solution {

    public static void main(String[] args) {
        ArrayList<Integer> caloriesList = solve("src/main/java/pl/mz/day01/input.txt");
        System.out.println("part 1: " + Collections.max(caloriesList));
        int totalCount = 0;
        for (int i = 0; i < 3; i++) {
            totalCount += Collections.max(caloriesList);
            caloriesList.remove(Collections.max(caloriesList));
        }
        System.out.println("part 2: " + totalCount);
    }

    private static ArrayList<Integer> solve(String file) {
        int tempSum = 0;
        ArrayList<Integer> outputList = new ArrayList<>();
        List<String> fileList = FileProcessor.readFile(file);
        for (String e : fileList) {
            if (!e.equals(""))
                tempSum += Integer.parseInt(e);
            else {
                outputList.add(tempSum);
                tempSum = 0;
            }
        }

        return outputList;
    }


}