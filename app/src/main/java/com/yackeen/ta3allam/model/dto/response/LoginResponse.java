package com.yackeen.ta3allam.model.dto.response;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("IsFirstTime")
    public boolean isFirstTime;

    @SerializedName("UserType")
    public int userType;

    @SerializedName("UserID")
    public String userID;

    @SerializedName("ErrorMessage")
    public String errorMessage;

    @SerializedName("IsSuccess")
    public boolean isSuccessful;
}
