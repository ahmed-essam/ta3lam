package yackeen.education.ta3allam.Capsule;

/**
 * Created by ahmed essam on 14/06/2017.
 */

public class Comment {
    private int ID;
    private String UserID;
    private String DateTime;
    private String UserName;
    private String UserPictureURL;
    private String Body;

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

    public String getDateTime() {
        return DateTime;
    }

    public void setDateTime(String dateTime) {
        DateTime = dateTime;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserPictureURL() {
        return UserPictureURL;
    }

    public void setUserPictureURL(String userPictureURL) {
        UserPictureURL = userPictureURL;
    }

    public String getBody() {
        return Body;
    }

    public void setBody(String body) {
        Body = body;
    }
}
