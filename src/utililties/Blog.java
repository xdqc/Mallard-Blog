package utililties;

import ORM.tables.records.ArticleRecord;
import ORM.tables.records.CommentRecord;
import ORM.tables.records.UserRecord;
import db_connector.DbConnector;

import java.util.List;

/* The reason of creating this class is to minimize total db queries, thus enhance the performance of the web app */
/* This is also helpful to integrate our data model */
public class Blog {
    private UserRecord author;
    private ArticleRecord article;
    private Tree<CommentRecord> commentTree;

    public Blog(ArticleRecord article) {
        this.article = article;
        this.author = DbConnector.getAuthorByArticleId(String.valueOf(article.getId()));
        List<CommentRecord> commentList = DbConnector.getCommentsByArticleId(String.valueOf(article.getId()));

        commentTree = new Tree<>(new CommentRecord(null, null, null, null, null, null, null, this.article.getId(), null));

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

    public void setAuthor(UserRecord author) {
        this.author = author;
    }

    public ArticleRecord getArticle() {
        return article;
    }

    public void setArticle(ArticleRecord article) {
        this.article = article;
    }

    public Tree<CommentRecord> getCommentTree() {
        return commentTree;
    }

    public void setCommentTree(Tree<CommentRecord> commentTree) {
        this.commentTree = commentTree;
    }

}
