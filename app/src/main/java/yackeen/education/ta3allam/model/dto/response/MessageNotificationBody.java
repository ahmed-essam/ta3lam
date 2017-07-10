package yackeen.education.ta3allam.model.dto.response;

/**
 * Created by ahmed essam on 10/07/2017.
 */

public class MessageNotificationBody {
    private String Content;
    private String FromUserID;
    private String UserName;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getFromUserID() {
        return FromUserID;
    }

    public void setFromUserID(String fromUserID) {
        FromUserID = fromUserID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }
}
