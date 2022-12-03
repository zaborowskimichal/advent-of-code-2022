package pl.mz.day03;

import pl.mz.tools.FileProcessor;

import java.util.*;
import java.util.stream.Collectors;

public class Solution {

    public static void main(String[] args) {
        LinkedList<String> fileList = FileProcessor.readFile("src/main/java/pl/mz/day03/input.txt");
        ArrayList<String[]> backpacks =  convertFileList(fileList);
        List<Character> letters = compareBakcpacks(backpacks);
        System.out.println("Part 1: " + letters.stream().mapToInt(Solution::calculateCharValue).sum());
    }

    public static  ArrayList<String[]> convertFileList(LinkedList<String> fileList) {
        return (ArrayList<String[]>) fileList.stream().map(Solution::splitBackpack).collect(Collectors.toList());
    }


    public static String[] splitBackpack(String text) {
        return new String[]{text.substring(0, text.length() / 2), text.substring(text.length() / 2)};
    }

    public static HashSet<Character> findCommonCharacters(String first, String second){
        HashSet<Character> result = new HashSet<>();
        HashSet<Character> charSet = new HashSet<>();
        for(char c : first.toCharArray()) charSet.add(c);
        for(char c : second.toCharArray()) if(charSet.contains(c)) result.add(c);

        return result;
    }

    public static List<Character> compareBakcpacks(List<String[]> backpacks){
        return backpacks.stream().map(e -> findCommonCharacters(e[0],e[1]))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public static int calculateCharValue(Character c){
        return (int) c - 'a' < 0 ? (int) c - 'A' + 27 : (int) c - 'a' + 1;
    }
}
