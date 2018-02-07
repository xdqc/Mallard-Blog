package utililties;


import ORM.tables.records.CommentRecord;
import ORM.tables.records.UserRecord;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Tree<T> {
    private List<Tree<T>> children;
    private T data;
    private Tree<T> root;

    Tree(){}

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

    /**
     * Recursively flat a tree to list
     * @param node root
     * @param result a list to hold the flatten tree
     */
    private void traverse(Tree<T> node, List<T> result){       // pre order traversal
        if (node.data!=null)
            result.add(node.data);
        for(Tree<T> each : node.children) {
            traverse(each, result);
        }
    }

    /**
     * Recursively find node of tree
     * @param n tree
     * @param s target node
     * @return found node or null
     */
    private Tree<T> findNode(Tree<T> n, T s) {
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

    /**
     * Recursively find a comment in comment tree with the same id
     * @param n comment tree
     * @param s target comment
     * @return found comment or null
     */
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

    /**
     * Recursively find a comment's parent comment
     * @param n comment tree
     * @param s target comment's child
     * @return the parent or null
     */
    Tree<Tuple3<UserRecord, CommentRecord, UserRecord>> findParentComment(Tree<Tuple3<UserRecord, CommentRecord, UserRecord>> n, Tuple3<UserRecord, CommentRecord, UserRecord> s) {
        if (Objects.equals(n.getData().Val2.getId(), s.Val2.getParentComment())) {
            return n;
        } else {
            for (Tree<Tuple3<UserRecord, CommentRecord, UserRecord>> child: n.getChildren()) {
                Tree<Tuple3<UserRecord, CommentRecord, UserRecord>> result = findParentComment(child, s);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

}

