package yackeen.education.ta3allam.model.dto.response;

/**
 * Created by ahmed essam on 10/07/2017.
 */

public class PostCommentNotificationBody {
    private String Content;
    private int PostID;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public int getPostID() {
        return PostID;
    }

    public void setPostID(int postID) {
        PostID = postID;
    }
}
