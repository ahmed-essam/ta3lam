package yackeen.education.ta3allam.model.dto.request;

import yackeen.education.ta3allam.Capsule.AddedComment;

/**
 * Created by ahmed essam on 20/06/2017.
 */

public class AddCommentRequest {
    private String UserID;
    private AddedComment Comment;

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public AddedComment getComment() {
        return Comment;
    }

    public void setComment(AddedComment comment) {
        Comment = comment;
    }
}
