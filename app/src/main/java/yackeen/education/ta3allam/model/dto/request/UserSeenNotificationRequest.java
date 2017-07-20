package yackeen.education.ta3allam.model.dto.request;

/**
 * Created by ahmed essam on 16/07/2017.
 */

public class UserSeenNotificationRequest {
    private String UserID;
    private int NotificationID;

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public int getNotificationID() {
        return NotificationID;
    }

    public void setNotificationID(int notificationID) {
        NotificationID = notificationID;
    }
}
