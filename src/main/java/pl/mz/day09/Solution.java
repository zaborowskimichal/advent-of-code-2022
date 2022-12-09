package pl.mz.day09;

import pl.mz.tools.FileProcessor;
import pl.mz.tools.Position;

import java.util.LinkedList;

public class Solution {
    public static void main(String[] args) {
        LinkedList<String> commandsList = (LinkedList<String>) FileProcessor.readFileToList("src/main/java/pl/mz/day09/input.txt");
        Position<Integer> startPosition = new Position<>(0, 0, 1);
        LinkedList<Position<Integer>> headPositions = new LinkedList<>();
        LinkedList<Position<Integer>> tailPositions = new LinkedList<>();
        Position<Integer> tail = new Position<>(startPosition.getX(), startPosition.getY(), 0);
        Position<Integer> head = new Position<>(startPosition);

        tailPositions.add(tail);
        headPositions.add(head);
        int counter = 0;
        for (String command : commandsList) {
            String[] splitted = command.split(" ");
            Direction direction = Direction.DOWN.getfromString(splitted[0]);
            int value = Integer.parseInt(splitted[1]);
//            System.out.println("~~~~~~Movement: " + direction + " " + value + "~~~~~~");
            for (int i = 1; i <= value; i++) {
                head = movePosition(headPositions.getLast(), direction, 1);
                tail = calculateTailPosition(tail, headPositions.getLast(), head);
                headPositions.add(head);
                boolean isInList = false;
                if (tail != null) {
                    for (int j = 0; j < tailPositions.size(); j++) {
                        Position<Integer> current = tailPositions.get(j);
                        if (current.getX() == tail.getX() && current.getY() == tail.getY()) {
                            isInList = true;
                            tail = tailPositions.remove(j);
                            tail.setValue(tail.getValue() + 1);
                            tailPositions.add(tail);
                        }
                    }
                    if (!isInList) {
                        tailPositions.add(tail);
                    }
                } else {
                    tail = tailPositions.getLast();
                }
//                printArray(mapToArray(headPositions), head, tail);


            }
            counter++;
            System.out.println(counter);
        }
        System.out.println(tailPositions.size());


    }

    private static Position<Integer> calculateTailPosition(Position<Integer> tail, Position<Integer> before, Position<Integer> after) {
        int xDiff = Math.abs(after.getX() - tail.getX());
        int yDiff = Math.abs(after.getY() - tail.getY());
        if (xDiff > 1 || yDiff > 1) {
            return new Position<>(before);
        } else {
            return null;
        }
    }

    public static Position<Integer> movePosition(Position<Integer> position, Direction direction, int value) {
        value *= direction.isTurn() ? 1 : -1;
        Position<Integer> result = new Position<>(position);
        if (direction.getDirection() == 'Y') {
            result.moveY(value);
        } else if (direction.getDirection() == 'X') {
            result.moveX(value);
        }
        return result;
    }

    public static int[][] mapToArray(LinkedList<Position<Integer>> list) {
        int maxX = list.stream().mapToInt(Position::getX).max().getAsInt();
        int maxY = list.stream().mapToInt(Position::getY).max().getAsInt();

        int[][] result = new int[maxY + 1][maxX + 1];
        for (Position<Integer> p : list) {
            result[p.getY()][p.getX()] = p.getValue();
        }

        return result;
    }

    public static void printArray(int[][] board) {
        for (int i = board.length - 1; i >= 0; i--) {
            for (int j = 0; j < board[0].length; j++) {
                System.out.print(board[i][j] != 0 ? "#" : ".");
            }
            System.out.println();
        }
    }

    public static void printArray(int[][] board, Position<Integer> head, Position<Integer> tail) {
        for (int i = board.length - 1; i >= 0; i--) {
            for (int j = 0; j < board[0].length; j++) {
                if (i == head.getY() && j == head.getX()) {
                    System.out.print("H");
                } else if (i == tail.getY() && j == tail.getX()) {
                    System.out.print("T");
                } else {
                    System.out.print(".");
                }
//                System.out.print(board[i][j] != 0 ? "#" : ".");
            }
            System.out.println();
        }
    }

    private enum Direction {
        UP('U', 'Y', true),
        DOWN('D', 'Y', false),
        RIGHT('R', 'X', true),
        LEFT('L', 'X', false);

        private final char id;
        private final char direction;
        private final boolean turn;

        Direction(char id, char direction, boolean turn) {
            this.id = id;
            this.direction = direction;
            this.turn = turn;
        }

        public Direction getfromString(String text) {
            for (Direction d : Direction.values()) {
                if (text.charAt(0) == d.id)
                    return d;
            }
            return null;
        }

        public char getDirection() {
            return direction;
        }

        public boolean isTurn() {
            return turn;
        }
    }
}
