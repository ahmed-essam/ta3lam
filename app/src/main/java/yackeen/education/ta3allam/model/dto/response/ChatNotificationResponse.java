package yackeen.education.ta3allam.model.dto.response;

import com.google.gson.Gson;

/**
 * Created by ahmed essam on 05/07/2017.
 */

public class ChatNotificationResponse extends NotificationResponse {
    public ChatNotificationResponse(int type, yackeen.education.ta3allam.model.dto.response.aps aps, long id, String body, int badge) {
        super(type, aps, id, body, badge);
    }

    public ChatNotificationResponse(NotificationResponse notificationResponse) {
        super();
        this.type = notificationResponse.type;
        this.aps = notificationResponse.aps;
        this.id = notificationResponse.id;
        this.badge = notificationResponse.badge;
        this.body = (notificationResponse.body);
        this.title = notificationResponse.title;
    }

    public body getMessageBody() {
        return new Gson().fromJson(this.body, yackeen.education.ta3allam.model.dto.response.body.class);
    }

    @Override
    public String getBodyDisplayData() {
        body body = getMessageBody();
        return body.Content;
    }
    public String getUserName(){
        yackeen.education.ta3allam.model.dto.response.body body = getMessageBody();
        return body.UserName;
    }
}
