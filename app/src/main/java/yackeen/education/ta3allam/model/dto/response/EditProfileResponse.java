package yackeen.education.ta3allam.model.dto.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ahmed essam on 03/07/2017.
 */

public class EditProfileResponse {
    @SerializedName("IsSuccess")
    private boolean success;
    private  String ErrorMessage;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        ErrorMessage = errorMessage;
    }
}
