package yackeen.education.ta3allam.model.dto.request;

/**
 * Created by ahmed essam on 07/06/2017.
 */

public class NewsRequest {
    private String UserID;
    private int PostId;

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public int getPostID() {
        return PostId;
    }

    public void setPostID(int postId) {
        PostId = postId;
    }
}
