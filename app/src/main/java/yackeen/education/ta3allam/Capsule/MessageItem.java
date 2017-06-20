package yackeen.education.ta3allam.Capsule;

/**
 * Created by ahmed essam on 16/06/2017.
 */

public class MessageItem {
    private String ConversationUserID;
    private String ConversationUserName;
    private String ConversationUserImageUrl;
    private String LastMessage;
    private String LastMessageDate;
    private boolean IsSeen;

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

    public String getLastMessage() {
        return LastMessage;
    }

    public void setLastMessage(String lastMessage) {
        LastMessage = lastMessage;
    }

    public String getLastMessageDate() {
        return LastMessageDate;
    }

    public void setLastMessageDate(String lastMessageDate) {
        LastMessageDate = lastMessageDate;
    }

    public boolean isSeen() {
        return IsSeen;
    }

    public void setSeen(boolean seen) {
        IsSeen = seen;
    }
}
