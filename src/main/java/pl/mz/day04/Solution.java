package pl.mz.day04;

import pl.mz.tools.FileProcessor;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class Solution {
    public static void main(String[] args) {
        LinkedList<String> fileList = FileProcessor.readFile("src/main/java/pl/mz/day04/input.txt");
        List<String[]> elfPair = fileList.stream().map(e -> e.split(",")).toList();
        List<Range[]> rangePairs = elfPair.stream()
                .map(pair -> new Range[]{new Range(rangeSplit(pair[0])), new Range(rangeSplit(pair[1]))})
                .toList();
        long subsets = rangePairs.stream().filter(Solution::findSubset).count();
        System.out.println("Part 1: " + subsets);

        subsets = rangePairs.stream().filter(Solution::checkIfContains).count();
        System.out.println("Part 2: " + subsets);
    }


    public static String[] rangeSplit(String text) {
        return text.split("-");
    }

    private static boolean findSubset(Range[] ranges) {
        return (ranges[0].getMin() >= ranges[1].getMin() && ranges[0].getMax() <= ranges[1].getMax()) ||
                (ranges[1].getMin() >= ranges[0].getMin() && ranges[1].getMax() <= ranges[0].getMax());
    }

    private static boolean checkIfContains(Range[] ranges) {
        List<Integer> firstRange = ranges[0].toList();
        List<Integer> secondRange = ranges[1].toList();
        return firstRange.stream().anyMatch(secondRange::contains);
    }

    private static class Range {
        private final int min;
        private final int max;

        public Range(String[] input) {
            this.min = Integer.parseInt(input[0]);
            this.max = Integer.parseInt(input[1]);
        }

        public int getMin() {
            return min;
        }

        public int getMax() {
            return max;
        }

        public  List<Integer> toList(){
            return IntStream.range(this.min, this.max+1).boxed().toList();
        }
    }


}
