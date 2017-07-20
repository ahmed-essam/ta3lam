package yackeen.education.ta3allam.model.dto.request;

/**
 * Created by ahmed essam on 16/07/2017.
 */

public class LogOutRequest {
    private String UserID;
    private String DeviceToken;

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getDeviceToken() {
        return DeviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        DeviceToken = deviceToken;
    }
}
