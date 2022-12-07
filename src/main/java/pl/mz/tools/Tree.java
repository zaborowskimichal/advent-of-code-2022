package pl.mz.tools;

import java.util.ArrayList;
import java.util.List;

public class Tree<V> {
    private String name;
    private V value;
    private List<Tree<V>> nodes;
    private Tree<V> parent;

    public Tree(String name, V value) {
        this.name = name;
        this.value = value;
        this.nodes = new ArrayList<>();
        this.parent = null;
    }

    public Tree(String name, V value, Tree<V> parent) {
        this.name = name;
        this.value = value;
        this.parent = parent;
        this.nodes = new ArrayList<>();
    }

    public Tree<V> getNode(String name) {
        for (Tree<V> tree : this.nodes) {
            if (tree.getName().equals(name))
                return tree;
        }
        return null;
    }

    public void addNode(Tree<V> tree) {
        tree.setParent(this);
        this.nodes.add(tree);
    }

    public int getNodesSize() {
        return this.nodes.size();
    }

    public String getName() {
        return name;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public List<Tree<V>> getNodes() {
        return nodes;
    }

    public Tree<V> getParent() {
        return parent;
    }

    public void setParent(Tree<V> parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "Tree{" +
                "name='" + name + '\'' +
                ", parent=" + (parent != null ? parent.getName() : "null") +
                ", value=" + value +
                ", nodes=" + (this.nodes.size() != 0 ? "\n" : "") + nodes +
                '}' + "\n";
    }
}
