package utililties;

import ORM.tables.records.ArticleRecord;
import ORM.tables.records.CommentRecord;
import ORM.tables.records.UserRecord;
import db_connector.DbConnector;

import java.util.*;

/* The reason of creating this class is to minimize total db queries, thus enhance the performance of the web app */
/* This is also helpful to integrate our data model */
public class Blog implements Map.Entry<Tuple<UserRecord, ArticleRecord>, List<CommentRecord>> {
    private UserRecord author;
    private ArticleRecord article;
    private int numComments;
    private Tree<CommentRecord> commentTree = new Tree<>(new CommentRecord());
    private List<CommentRecord> commentList = new ArrayList<>();

    public Blog(ArticleRecord article) {
        this.article = article;
        this.author = DbConnector.getAuthorByArticleId(String.valueOf(article.getId()));
        this.numComments = DbConnector.getCommentNumberByArticle(String.valueOf(article.getId()));
        this.commentList = DbConnector.getCommentsByArticleId(String.valueOf(article.getId()));
        this.commentTree = new Tree<>(new CommentRecord(null, null, null, null, null, null, null, this.article.getId(), null));
        addListToTree();
    }

    public Blog(Tuple<UserRecord, ArticleRecord> tuple, List<CommentRecord> commentList){
        this.author = tuple.Val1;
        this.article = tuple.Val2;
        this.commentList = commentList;
        this.numComments = commentList.size();
        this.commentTree = new Tree<>(new CommentRecord(null, null, null, null, null, null, null, this.article.getId(), null));

        addListToTree();
    }

    public Blog() {}

    private void addListToTree() {
        // Add comment that directly under the article
        for (CommentRecord comment : commentList) {
            if (comment.getParentComment() == null) {
                commentTree.addChild(new Tree<>(comment));
            }
        }

        // Add children comment's to their parents
        while (commentList.size() > 0) {
            addCommentsToTree(commentTree, commentList);
        }
    }

    private void addCommentsToTree(Tree<CommentRecord> tree, List<CommentRecord> list) {
        for (int i = 0; i < list.size(); i++) {
            CommentRecord comment = list.get(i);
            // Check if there is a comment in the tree can be the parent of the commentList elem
            Tree<CommentRecord> parent = tree.findParentComment(tree, comment);
            if (parent != null) {
                // Add comment to its parent's children
                parent.addChild(new Tree<>(comment));
                list.remove(comment);
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

    public int getNumComments() {
        return numComments;
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

    public void addValue(List<CommentRecord> c) {
        if (!c.isEmpty()){
            this.commentList.add(c.get(0));
        }
        addListToTree();
    }

    public void setKey(Tuple<UserRecord, ArticleRecord> t) {
        this.author = t.Val1;
        this.article = t.Val2;
    }
}
