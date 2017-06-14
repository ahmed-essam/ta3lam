package com.yackeen.ta3allam.model.dto.request;

/**
 * Created by ahmed essam on 13/06/2017.
 */

public class CommentsRequest {
    private String UserID;
    private int PostID;

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public int getPostID() {
        return PostID;
    }

    public void setPostID(int postID) {
        PostID = postID;
    }
}
