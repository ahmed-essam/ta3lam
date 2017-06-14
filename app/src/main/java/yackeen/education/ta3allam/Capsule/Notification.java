package yackeen.education.ta3allam.Capsule;

/**
 * Created by ahmed essam on 11/06/2017.
 */

public class Notification {
    private String UserPictureURL;
    private int ID;
    private String UserID;
    private int Type;
    private String NotificationBody;
    private boolean IsSeen;
    private String FromUserID;
    private String User;
    private String User1;

    public String getUserPictureURL() {
        return UserPictureURL;
    }

    public void setUserPictureURL(String userPictureURL) {
        UserPictureURL = userPictureURL;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public String getNotificationBody() {
        return NotificationBody;
    }

    public void setNotificationBody(String notificationBody) {
        NotificationBody = notificationBody;
    }

    public boolean isSeen() {
        return IsSeen;
    }

    public void setSeen(boolean seen) {
        IsSeen = seen;
    }

    public String getFromUserID() {
        return FromUserID;
    }

    public void setFromUserID(String fromUserID) {
        FromUserID = fromUserID;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public String getUser1() {
        return User1;
    }

    public void setUser1(String user1) {
        User1 = user1;
    }
}
