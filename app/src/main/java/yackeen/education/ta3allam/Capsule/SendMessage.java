package yackeen.education.ta3allam.Capsule;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ahmed essam on 19/06/2017.
 */

public class SendMessage {
    @SerializedName("ToUserID")
    private String ToUserID;
    @SerializedName("Body")
    private String Body;

    public String getToUserID() {
        return ToUserID;
    }

    public void setToUserID(String toUserID) {
        ToUserID = toUserID;
    }

    public String getBody() {
        return Body;
    }

    public void setBody(String body) {
        Body = body;
    }
}
