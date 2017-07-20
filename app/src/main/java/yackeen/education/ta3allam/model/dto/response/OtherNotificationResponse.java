package yackeen.education.ta3allam.model.dto.response;

import com.google.gson.Gson;

/**
 * Created by ahmed essam on 05/07/2017.
 */

public class OtherNotificationResponse extends NotificationResponse {
    public OtherNotificationResponse(int type, yackeen.education.ta3allam.model.dto.response.aps aps, long id, String body, int badge) {
        super(type, aps, id, body, badge);
    }

    public OtherNotificationResponse(NotificationResponse notificationResponse) {
        super();
        this.type = notificationResponse.type;
        this.aps = notificationResponse.aps;
        this.id = notificationResponse.id;
        this.badge = notificationResponse.badge;
        this.body = (notificationResponse.body);
        this.title = notificationResponse.title;
    }

    public PostCommentNotificationBody getMessageBody() {
        return new Gson().fromJson(this.body, PostCommentNotificationBody.class);
    }

    @Override
    public String getBodyDisplayData() {
        PostCommentNotificationBody postCommentNotificationBody = getMessageBody();
        return postCommentNotificationBody.getContent();
    }

    public int getPostId() {
        PostCommentNotificationBody postCommentNotificationBody = getMessageBody();
        return postCommentNotificationBody.getPostID();
    }
}
