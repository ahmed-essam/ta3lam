package yackeen.education.ta3allam.model.dto.request;

import yackeen.education.ta3allam.Capsule.SendMessage;

/**
 * Created by ahmed essam on 19/06/2017.
 */

public class SendMessageRequest {
    private String UserID;
    private SendMessage sendMessage;

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public SendMessage getSendMessage() {
        return sendMessage;
    }

    public void setSendMessage(SendMessage sendMessage) {
        this.sendMessage = sendMessage;
    }
}
