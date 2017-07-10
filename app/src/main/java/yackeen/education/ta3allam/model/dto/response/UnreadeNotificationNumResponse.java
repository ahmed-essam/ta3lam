package yackeen.education.ta3allam.model.dto.response;

/**
 * Created by ahmed essam on 08/07/2017.
 */

public class UnreadeNotificationNumResponse {
    private int NotificationNumber;
    private boolean IsSuccess;
    private String ErrorMessage;

    public int getNotificationNumber() {
        return NotificationNumber;
    }

    public boolean isSuccess() {
        return IsSuccess;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }
}
