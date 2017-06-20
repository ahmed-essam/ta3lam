package yackeen.education.ta3allam.Capsule;

/**
 * Created by ahmed essam on 18/06/2017.
 */

public class Message {
    private int MessageID;
    private boolean IsMine;
    private String Body;
    private String DateTime;

    public int getMessageID() {
        return MessageID;
    }

    public void setMessageID(int messageID) {
        MessageID = messageID;
    }

    public boolean isMine() {
        return IsMine;
    }

    public void setMine(boolean mine) {
        IsMine = mine;
    }

    public String getBody() {
        return Body;
    }

    public void setBody(String body) {
        Body = body;
    }

    public String getDateTime() {
        return DateTime;
    }

    public void setDateTime(String dateTime) {
        DateTime = dateTime;
    }
}
