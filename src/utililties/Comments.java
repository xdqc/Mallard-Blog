package utililties;

import ORM.tables.User;
import ORM.tables.records.CommentRecord;
import ORM.tables.records.UserRecord;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Comments extends Tree<Tuple3<UserRecord, CommentRecord, UserRecord>> implements Serializable{
    private int numComments = 0;
    private Tree<Tuple3<UserRecord, CommentRecord, UserRecord>> commentTree = new Tree<>(new Tuple3<>(new UserRecord(), new CommentRecord(), new UserRecord()));

    public Comments(Tuple3<UserRecord, CommentRecord, UserRecord> rootData) {
        super(rootData);
    }

    public Comments(){ }

    private List<Tuple3<UserRecord, CommentRecord, UserRecord>> commentList = new ArrayList<>();

    public void convertListToTree() {
        // Add comment that directly under the article
        for (int i = 0; i < commentList.size(); i++) {
            Tuple3<UserRecord, CommentRecord, UserRecord> comment = commentList.get(i);
            if (comment.Val2.getParentComment() == null) {
                this.commentTree.addChild(new Tree<>(comment));
                commentList.remove(comment);
                numComments++;
                i--;
            }
        }

        // Add children comment's to their parents
        while (commentList.size() > 0) {
        moveCommentsToTree(this.getCommentTree(), commentList);
    }
}

    private void moveCommentsToTree(Tree<Tuple3<UserRecord, CommentRecord, UserRecord>> tree, List<Tuple3<UserRecord, CommentRecord, UserRecord>> list) {
        for (int i = 0; i < list.size(); i++) {
            Tuple3<UserRecord, CommentRecord, UserRecord> comment = list.get(i);
            // Check if there is a comment in the tree can be the parent of the commentList elem
            Tree<Tuple3<UserRecord, CommentRecord, UserRecord>> parent = tree.findParentComment(tree, comment);
            if (parent != null) {
                // Add comment to its parent's children
                parent.addChild(new Comments(list.get(i)));
                list.remove(comment);
                numComments++;
                i--;
            }
        }
    }

    public void setCommentList(List<Tuple3<UserRecord, CommentRecord, UserRecord>> commentList) {
        this.commentList = commentList;
    }

    public int getNumComments() {
        return numComments;
    }

    public Tree<Tuple3<UserRecord, CommentRecord, UserRecord>> getCommentTree() {
        return commentTree;
    }
}
