package yackeen.education.ta3allam.model.dto.request;

/**
 * Created by ahmed essam on 20/06/2017.
 */

public class LikeAndShareRequest {
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
