package utililties;


import java.util.LinkedList;
import java.util.List;

public class Tree<T> {
    private List<Tree> children;
    private T data;
    private Tree<T> root;

    public Tree(T rootData) {
        root = this;
        root.data = rootData;
        root.children = new LinkedList<>();
    }

    public T getData(){
        return this.data;
    }

    public Tree<T> getRoot() {
        return root;
    }

    public void addChild(Tree<T> child) {
        this.root.children.add(child);
    }

    public List<Tree> getChildren(){
        return this.children;
    }

}

