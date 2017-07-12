package yackeen.education.ta3allam.model.dto.response;

import com.google.gson.annotations.SerializedName;

public class RegisterResponse {

    @SerializedName("IsSuccess")
    public boolean isSuccessful;

    @SerializedName("ErrorMessage")
    public String errorMessage;
    @SerializedName("IsFirstTime")
    public boolean isFirstTime;
    @SerializedName("UserType")
    public int userType;
    @SerializedName("UserID")
    public String userId;

}
