package pl.mz.day02;

import pl.mz.tools.FileReader;

import java.util.LinkedList;

public class Day2 {

    public static void main(String[] args) {
        LinkedList<String> fileList = FileReader.readFile("src/main/java/pl/mz/day02/input.txt");
        int totalScore = 0;
        for (String e : fileList) {
            String[] turns = e.split(" ");
            totalScore += calcScore(Game.fromInput(turns[0]), Game.fromInput(turns[1]));
        }
        System.out.println("Part 1: " + totalScore);
        totalScore = 0;
        for (String e : fileList) {
            String[] turns = e.split(" ");
            totalScore += calcScore(Game.fromInput(turns[0]), findAnswer(Game.fromInput(turns[0]),turns[1]));
        }
        System.out.println("Part 2: " + totalScore);
    }

    private static int calcScore(Game elf, Game me) {
        if (elf == me)
            return 3 + me.getScore();
        else if ((elf == Game.ROCK && me == Game.SCISSORS) ||
                (elf == Game.PAPER && me == Game.ROCK) ||
                (elf == Game.SCISSORS && me == Game.PAPER))
            return me.getScore();
        else
            return 6 + me.getScore();
    }

    private static Game findAnswer(Game elf, String resultType){
       return switch (resultType){
           case "Y"  -> elf;
           case "X" -> Game.findWeaker(elf);
           case "Z" -> Game.findStronger(elf);
           default -> null;
       };
    }

    private enum Game {
        ROCK("A", "X", 1),
        PAPER("B", "Y", 2),
        SCISSORS("C", "Z", 3);

        private final String elfTurn;
        private final String myTurn;
        private final int score;

        Game(String elfTurn, String myTurn, int score) {
            this.elfTurn = elfTurn;
            this.myTurn = myTurn;
            this.score = score;
        }

        public static Game fromInput(String text) {
            for (Game g : Game.values()) {
                if (g.elfTurn.equals(text))
                    return g;
                else if (g.myTurn.equals(text))
                    return g;
            }
            return null;
        }
        public static Game findStronger(Game input){
           return switch (input){
                case PAPER -> SCISSORS;
                case SCISSORS -> ROCK;
               case ROCK  -> PAPER;
            };
        }

        public static Game findWeaker(Game input){
            return switch (input){
                case PAPER -> ROCK;
                case SCISSORS -> PAPER;
                case ROCK  -> SCISSORS;
            };
        }

        public int getScore() {
            return score;
        }
    }
}
