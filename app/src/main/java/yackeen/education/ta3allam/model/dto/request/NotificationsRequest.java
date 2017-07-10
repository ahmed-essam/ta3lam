package yackeen.education.ta3allam.model.dto.request;

/**
 * Created by ahmed essam on 11/06/2017.
 */

public class NotificationsRequest {
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
