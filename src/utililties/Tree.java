package utililties;


import ORM.tables.records.CommentRecord;
import ORM.tables.records.UserRecord;

import java.rmi.activation.ActivationGroup_Stub;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Tree<T> {
    private List<Tree<T>> children;
    private T data;
    private Tree<T> root;

    public Tree(){}

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

    public List<Tree<T>> getChildren(){
        return this.children;
    }

    public void traverse(Tree<T> node, List<T> result){       // pre order traversal
        if (node.data!=null)
            result.add(node.data);
        for(Tree<T> each : node.children) {
            traverse(each, result);
        }
    }

    public Tree<T> findNode(Tree<T> n, T s) {
        if (n.data == s) {
            return n;
        } else {
            for (Tree<T> child: n.children) {
                Tree<T> result = findNode(child, s);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

    public Tree<CommentRecord> findParentComment(Tree<CommentRecord> n, CommentRecord s) {
        if (Objects.equals(n.data.getId(), s.getParentComment())) {
            return n;
        } else {
            for (Tree<CommentRecord> child: n.children) {
                Tree<CommentRecord> result = findParentComment(child, s);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

    public Tree<Tuple<UserRecord, CommentRecord>> findParentComment(Tree<Tuple<UserRecord, CommentRecord>> n, Tuple<UserRecord, CommentRecord> s) {
        if (Objects.equals(n.getData().Val2.getId(), s.Val2.getParentComment())) {
            return n;
        } else {
            for (Tree<Tuple<UserRecord, CommentRecord>> child: n.getChildren()) {
                Tree<Tuple<UserRecord, CommentRecord>> result = findParentComment(child, s);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

}

