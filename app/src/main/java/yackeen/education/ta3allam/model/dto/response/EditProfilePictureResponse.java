package yackeen.education.ta3allam.model.dto.response;

/**
 * Created by ahmed essam on 03/07/2017.
 */

public class EditProfilePictureResponse {
    private boolean IsSuccess;
    private String ErrorMessage;

    public boolean isSuccess() {
        return IsSuccess;
    }

    public void setSuccess(boolean success) {
        IsSuccess = success;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        ErrorMessage = errorMessage;
    }
}
