package utililties;

import ORM.tables.User;
import ORM.tables.records.CommentRecord;
import ORM.tables.records.UserRecord;

import java.io.Serializable;
import java.util.List;

public class Comments extends Tree<Tuple<UserRecord, CommentRecord>> implements Serializable{
    private int numComments = 0;

    public Comments(Tuple<UserRecord, CommentRecord> rootData) {
        super(rootData);
    }

    public  Comments(){}

    private List<Tuple<UserRecord, CommentRecord>> commentList;

    public void convertListToTree() {
        // Add comment that directly under the article
        for (int i = 0; i < commentList.size(); i++) {
            Tuple<UserRecord, CommentRecord> comment = commentList.get(i);
            if (comment.Val2.getParentComment() == null) {
                this.addChild(new Tree<>(comment));
                commentList.remove(comment);
                numComments++;
                i--;
            }
        }

        // Add children comment's to their parents
        while (commentList.size() > 0) {
            moveCommentsToTree(this, commentList);
        }
    }

    private void moveCommentsToTree(Comments tree, List<Tuple<UserRecord, CommentRecord>> list) {
        for (int i = 0; i < list.size(); i++) {
            Tuple<UserRecord, CommentRecord> comment = list.get(i);
            // Check if there is a comment in the tree can be the parent of the commentList elem
            Comments parent = tree.findParentComment(tree, comment);
            if (parent != null) {
                // Add comment to its parent's children
                parent.addChild(new Comments(list.get(i)));
                list.remove(comment);
                numComments++;
                i--;
            }
        }
    }

    public void setCommentList(List<Tuple<UserRecord, CommentRecord>> commentList) {
        this.commentList = commentList;
    }

    public int getNumComments() {
        return numComments;
    }
}
