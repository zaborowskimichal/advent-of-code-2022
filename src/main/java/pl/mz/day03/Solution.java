package pl.mz.day03;

import pl.mz.tools.FileProcessor;

import java.util.*;
import java.util.stream.Collectors;

public class Solution {

    public static void main(String[] args) {
        List<String> fileList = FileProcessor.readFileToList("src/main/java/pl/mz/day03/input.txt");
        for (int part = 1; part < 3; part++) {
            ArrayList<String[]> backpacks = convertFileList(fileList, part);
            List<Character> letters = compareBakcpacks(backpacks);
            System.out.println("Part " + part + ": " + letters.stream().mapToInt(Solution::calculateCharValue).sum());
        }
    }

    public static ArrayList<String[]> convertFileList(List<String> fileList, int part) {
        ArrayList<String[]> result;
        if (part == 1)
            result = (ArrayList<String[]>) fileList.stream().map(Solution::splitBackpack).collect(Collectors.toList());
        else {
            result = new ArrayList<>();
            fileList.stream()
                    .filter(e -> fileList.indexOf(e) % 3 == 0)
                    .forEach(e -> result.add(fileList.subList(fileList.indexOf(e), fileList.indexOf(e) + 3)
                            .toArray(new String[0])));
        }
        return result;
    }

    public static String[] splitBackpack(String text) {
        return new String[]{text.substring(0, text.length() / 2), text.substring(text.length() / 2)};
    }

    public static HashSet<Character> findCommonCharacters(String[] backpacks) {
        ArrayList<HashSet<Character>> commonList = new ArrayList<>();
        Arrays.stream(backpacks).forEach(e -> commonList.add(new HashSet<>()));
        for (int i = 0; i < commonList.size(); i++) {
            for (char c : backpacks[i].toCharArray()) {
                if (i > 0) {
                    if (commonList.get(i - 1).contains(c)) commonList.get(i).add(c);
                } else
                    commonList.get(i).add(c);
            }
        }

        return commonList.get(commonList.size() - 1);
    }

    public static List<Character> compareBakcpacks(List<String[]> backpacks) {
        return backpacks.stream().map(Solution::findCommonCharacters)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public static int calculateCharValue(Character c) {
        return (int) c - 'a' < 0 ? (int) c - 'A' + 27 : (int) c - 'a' + 1;
    }
}
