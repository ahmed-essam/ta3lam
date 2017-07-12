package yackeen.education.ta3allam.model.dto.request;

import com.google.gson.annotations.SerializedName;

import yackeen.education.ta3allam.Capsule.SendMessage;

/**
 * Created by ahmed essam on 19/06/2017.
 */

public class SendMessageRequest {
    @SerializedName("UserID")
    private String UserID;
    @SerializedName("Message")
    private SendMessage Message;

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public SendMessage getMessage() {
        return Message;
    }

    public void setMessage(SendMessage message) {
        this.Message = message;
    }
}
