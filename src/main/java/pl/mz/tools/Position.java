package pl.mz.tools;

import java.util.Objects;

public class Position<T> {
    private int x;
    private int y;
    private T value;


    public Position(int x, int y, T value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }

    public Position(Position<T> position){
        this.x = position.getX();
        this.y = position.getY();
        this.value = position.getValue();
    }


    public int getX() {
        return x;
    }

    public void moveX(int x){
        this.x += x;
    }

    public void moveY(int y){
        this.y += y;
    }


    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position<?> position = (Position<?>) o;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                ", value=" + value +
                '}';
    }
}
