package yackeen.education.ta3allam.model.dto.response;

import com.google.gson.annotations.SerializedName;

public class RegisterResponse {

    @SerializedName("IsSuccess")
    public boolean isSuccessful;

    @SerializedName("ErrorMessage")
    public String errorMessage;
}
