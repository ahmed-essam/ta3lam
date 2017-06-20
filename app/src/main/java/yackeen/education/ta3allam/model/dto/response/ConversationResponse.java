package yackeen.education.ta3allam.model.dto.response;

import java.util.List;

import yackeen.education.ta3allam.Capsule.Message;

/**
 * Created by ahmed essam on 18/06/2017.
 */

public class ConversationResponse {
    private String ConversationUserID;
    private String ConversationUserName;
    private String ConversationUserImageUrl;
   public List<Message> ConversationMessages;

    public String getConversationUserID() {
        return ConversationUserID;
    }

    public void setConversationUserID(String conversationUserID) {
        ConversationUserID = conversationUserID;
    }

    public String getConversationUserName() {
        return ConversationUserName;
    }

    public void setConversationUserName(String conversationUserName) {
        ConversationUserName = conversationUserName;
    }

    public String getConversationUserImageUrl() {
        return ConversationUserImageUrl;
    }

    public void setConversationUserImageUrl(String conversationUserImageUrl) {
        ConversationUserImageUrl = conversationUserImageUrl;
    }
}
