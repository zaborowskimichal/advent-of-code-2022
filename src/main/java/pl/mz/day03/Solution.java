package pl.mz.day03;

import pl.mz.tools.FileProcessor;

import java.util.*;
import java.util.stream.Collectors;

public class Solution {

    public static void main(String[] args) {
        LinkedList<String> fileList = FileProcessor.readFile("src/main/java/pl/mz/day03/input.txt");
        for (int i = 1; i < 3; i++) {
            ArrayList<String[]> backpacks = convertFileList(fileList, i);
            List<Character> letters = compareBakcpacks(backpacks, i);
            System.out.println("Part " + i + ": " + letters.stream().mapToInt(Solution::calculateCharValue).sum());
        }
    }

    public static ArrayList<String[]> convertFileList(LinkedList<String> fileList, int part) {
        if (part == 1)
            return (ArrayList<String[]>) fileList.stream().map(Solution::splitBackpack).collect(Collectors.toList());
        int counter = 0;
        ArrayList<String[]> result = new ArrayList<>();
        String[] temp = new String[3];
        for (String s : fileList) {
            temp[counter] = s;
            if (counter < 2) {
                counter++;
            } else {
                result.add(temp);
                temp = new String[3];
                counter = 0;
            }
        }
        if (counter != 0) result.add(temp);
        return result;
    }


    public static String[] splitBackpack(String text) {
        return new String[]{text.substring(0, text.length() / 2), text.substring(text.length() / 2)};
    }

    public static HashSet<Character> findCommonCharacters(String first, String second) {
        HashSet<Character> result = new HashSet<>();
        HashSet<Character> charSet = new HashSet<>();
        for (char c : first.toCharArray()) charSet.add(c);
        for (char c : second.toCharArray()) if (charSet.contains(c)) result.add(c);

        return result;
    }

    public static HashSet<Character> findCommonCharacters(String first, String second, String third) {
        HashSet<Character> commonPart = new HashSet<>();
        HashSet<Character> charSet = new HashSet<>();
        HashSet<Character> result = new HashSet<>();
        for (char c : first.toCharArray()) charSet.add(c);
        for (char c : second.toCharArray()) if (charSet.contains(c)) commonPart.add(c);
        for (char c : third.toCharArray()) if (commonPart.contains(c)) result.add(c);

        return result;
    }

    public static List<Character> compareBakcpacks(List<String[]> backpacks, int part) {
        if (part == 1) return backpacks.stream().map(e -> findCommonCharacters(e[0], e[1]))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        else return backpacks.stream().map(e -> findCommonCharacters(e[0], e[1], e[2]))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public static int calculateCharValue(Character c) {
        return (int) c - 'a' < 0 ? (int) c - 'A' + 27 : (int) c - 'a' + 1;
    }
}
