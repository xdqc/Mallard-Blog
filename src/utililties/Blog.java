package utililties;

import ORM.tables.records.ArticleRecord;
import ORM.tables.records.CommentRecord;
import ORM.tables.records.UserRecord;
import org.json.simple.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* The reason of creating this class is to minimize total db queries, thus enhance the performance of the web app */
/* This is also helpful to integrate our data model */
public class Blog implements Map.Entry<Tuple<UserRecord, ArticleRecord>, List<CommentRecord>>, Serializable {
    private UserRecord author;
    private ArticleRecord article;
    private Tree<CommentRecord> commentTree = new Tree<>(new CommentRecord());
    private List<CommentRecord> commentList = new ArrayList<>();
    private int numComments = 0;
    private int validComments = 0;

    public Blog() {
    }


    /**
     * Put a list of comments in comment tree where each node should be
     * First step: put comment directly to article under the root's children
     */
    public void convertListToTree() {
        // Add comment that directly under the article
        for (int i = 0; i < commentList.size(); i++) {
            CommentRecord comment = commentList.get(i);
            if (comment.getParentComment() == null) {
                commentTree.addChild(new Tree<>(comment));
                commentList.remove(comment);
                numComments++;
                i--;
            }
        }

        // Add children comment's to their parents
        while (commentList.size() > 0) {
            moveCommentsToTree(commentTree, commentList);
        }
    }

    /**
     * Put a list of comments in comment tree where each node should be
     * Second step: put children and grandchildren, grand grand ... to tree
     *
     * @param tree comment tree
     * @param list list of comments to put into tree
     */
    private void moveCommentsToTree(Tree<CommentRecord> tree, List<CommentRecord> list) {
        for (int i = 0; i < list.size(); i++) {
            CommentRecord comment = list.get(i);
            // Check if there is a comment in the tree can be the parent of the commentList elem
            Tree<CommentRecord> parent = tree.findParentComment(tree, comment);
            if (parent != null) {
                // Add comment to its parent's children
                parent.addChild(new Tree<>(comment));
                list.remove(comment);
                numComments++;
                i--;
            }
        }
    }

    public UserRecord getAuthor() {
        return author;
    }

    public ArticleRecord getArticle() {
        return article;
    }

    public Tree<CommentRecord> getCommentTree() {
        return commentTree;
    }

    /*Used in jsp, do not delete*/
    public int getNumComments() {
        return numComments;
    }

    public int getNumValidComments() {
        this.validComments = 0;
        countValidComment(this.commentTree);
        return this.validComments;
    }

    private void countValidComment(Tree<CommentRecord> tree) {
        for (Tree<CommentRecord> commentTree : tree.getChildren()) {
            CommentRecord comment = commentTree.getData();
            //UserRecord commenter = commentTree.getData().Val2;
            // create json obj only for valid comments to show
            if (comment.getShowHideStatus() == 1) {
                this.validComments++;
                if (!commentTree.getChildren().isEmpty()) {
                    countValidComment(commentTree);
                }
            }
        }
    }

    public void addValue(List<CommentRecord> c) {
        if (!c.isEmpty()) {
            this.commentList.add(c.get(0));
        }
        convertListToTree();
    }

    public void setKey(Tuple<UserRecord, ArticleRecord> t) {
        this.author = t.Val1;
        this.article = t.Val2;
    }

    public void addComment(CommentRecord comment) {
        this.commentList.add(comment);
    }

    /**
     * Returns the key corresponding to this entry.
     *
     * @return the key corresponding to this entry
     * @throws IllegalStateException implementations may, but are not
     *                               required to, throw this exception if the entry has been
     *                               removed from the backing map.
     */
    @Override
    public Tuple<UserRecord, ArticleRecord> getKey() {
        return new Tuple<>(author, article);
    }

    /**
     * Returns the value corresponding to this entry.  If the mapping
     * has been removed from the backing map (by the iterator's
     * <tt>remove</tt> operation), the results of this call are undefined.
     *
     * @return the value corresponding to this entry
     * @throws IllegalStateException implementations may, but are not
     *                               required to, throw this exception if the entry has been
     *                               removed from the backing map.
     */
    @Override
    public List<CommentRecord> getValue() {
        return commentList;
    }

    /**
     * Replaces the value corresponding to this entry with the specified
     * value (optional operation).  (Writes through to the map.)  The
     * behavior of this call is undefined if the mapping has already been
     * removed from the map (by the iterator's <tt>remove</tt> operation).
     *
     * @param value new value to be stored in this entry
     * @return old value corresponding to the entry
     * @throws UnsupportedOperationException if the <tt>put</tt> operation
     *                                       is not supported by the backing map
     * @throws ClassCastException            if the class of the specified value
     *                                       prevents it from being stored in the backing map
     * @throws NullPointerException          if the backing map does not permit
     *                                       null values, and the specified value is null
     * @throws IllegalArgumentException      if some property of this value
     *                                       prevents it from being stored in the backing map
     * @throws IllegalStateException         implementations may, but are not
     *                                       required to, throw this exception if the entry has been
     *                                       removed from the backing map.
     */
    @Override
    public List<CommentRecord> setValue(List<CommentRecord> value) {
        return value;
    }

}
