package yackeen.education.ta3allam.model.dto.request;

import java.util.List;

import yackeen.education.ta3allam.Capsule.Message;

/**
 * Created by ahmed essam on 18/06/2017.
 */

public class ConversationRequest {
    private String UserID;
    private String ConversationUserID;
    private int LastMessageID;

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getConversationUserID() {
        return ConversationUserID;
    }

    public void setConversationUserID(String conversationUserID) {
        ConversationUserID = conversationUserID;
    }

    public int getLastMessageID() {
        return LastMessageID;
    }

    public void setLastMessageID(int lastMessageID) {
        LastMessageID = lastMessageID;
    }
}
