package pl.mz.tools;

import java.util.Collections;
import java.util.LinkedList;

public class MyStack<T> {
    private LinkedList<T> list;

    public MyStack(LinkedList<T> list) {
        this.list = list;
    }

    public MyStack() {
        this.list = new LinkedList<>();
    }

    public void push(T t) {
        this.list.addLast(t);
    }

    public T pop() {
        return this.list.removeLast();
    }

    public T top() {
        if (this.list.size() > 0) {
            return this.list.getLast();
        }
        return null;
    }

    public LinkedList<T> removeSublist(int number) {
        LinkedList<T> temp = new LinkedList<>();
        for (int i = 1; i <= number; i++) {
            temp.add(this.list.removeLast());
        }

        return temp;
    }

    public void addReversedList(LinkedList<T> newPart) {
        Collections.reverse(newPart);
        this.list.addAll(newPart);
    }

    @Override
    public String toString() {
        return "MyStack{" +
                "list=" + list +
                '}';
    }
}
