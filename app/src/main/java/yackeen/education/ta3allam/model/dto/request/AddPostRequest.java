package yackeen.education.ta3allam.model.dto.request;

import yackeen.education.ta3allam.Capsule.Post;

/**
 * Created by ahmed essam on 07/07/2017.
 */

public class AddPostRequest {
    private String UserID;
    private Post Post;

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public yackeen.education.ta3allam.Capsule.Post getPost() {
        return Post;
    }

    public void setPost(yackeen.education.ta3allam.Capsule.Post post) {
        Post = post;
    }
}
