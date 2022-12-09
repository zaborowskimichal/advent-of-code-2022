package pl.mz.day09;

import pl.mz.tools.FileProcessor;
import pl.mz.tools.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class Solution {
    public static void main(String[] args) {
        LinkedList<String> fileList = (LinkedList<String>) FileProcessor.readFileToList("src/main/java/pl/mz/day09/input.txt");
        ArrayList<Command> commandsList = (ArrayList<Command>) fileList.stream().map(Command::new).collect(Collectors.toList());

        System.out.println("Part 1: " + calcVisitedByLastKnot(commandsList, 2));
        System.out.println("Part 2: " + calcVisitedByLastKnot(commandsList, 10));
    }

    private static int calcVisitedByLastKnot(ArrayList<Command> commands, int knotsQuantity) {
        HashSet<Position<Integer>> visitedByLast = new HashSet<>();
        Position<Integer>[] knots = new Position[knotsQuantity];
        Arrays.fill(knots, new Position<>(0, 0, 0));

        for (Command command : commands) {
            for (int i = 0; i < command.getValue(); i++) {
                knots[0] = movePosition(knots[0], command.getDirection());

                Position<Integer> last = knots[0];
                for (int j = 1; j < knots.length; j++) {
                    knots[j] = moveToLast(knots[j], last);
                    last = knots[j];
                }
                visitedByLast.add(knots[knotsQuantity - 1]);
            }
        }
        return visitedByLast.size();
    }

    private static Position<Integer> moveToLast(Position<Integer> tail, Position<Integer> head) {
        int xDiff = head.getX() - tail.getX();
        int yDiff = head.getY() - tail.getY();
        if (Math.abs(xDiff) > 1 || Math.abs(yDiff) > 1) {
            Position<Integer> position = new Position<>(tail);
            if (xDiff > 0) {
                position.moveX(1);
            } else if (xDiff < 0) {
                position.moveX(-1);
            }

            if (yDiff > 0) {
                position.moveY(1);
            } else if (yDiff < 0) {
                position.moveY(-1);
            }

            return position;
        } else {
            return tail;
        }
    }

    public static Position<Integer> movePosition(Position<Integer> position, Direction direction) {
        int value = direction.isTurn() ? 1 : -1;
        Position<Integer> result = new Position<>(position);
        if (direction.getDirection() == 'Y') {
            result.moveY(value);
        } else if (direction.getDirection() == 'X') {
            result.moveX(value);
        }
        return result;
    }

    private static class Command {
        private Direction direction;
        private int value;

        public Command(String text) {
            String[] divided = text.split(" ");
            this.direction = Direction.getFromString(divided[0]);
            this.value = Integer.parseInt(divided[1]);
        }

        public Direction getDirection() {
            return direction;
        }

        public int getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "Command{" +
                    "direction=" + direction +
                    ", value=" + value +
                    '}';
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

        public static Direction getFromString(String text) {
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
