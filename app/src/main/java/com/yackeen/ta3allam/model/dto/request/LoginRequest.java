package com.yackeen.ta3allam.model.dto.request;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {

    /**
     * This class is responsible for holding the login request body
     * */
    @SerializedName("IsFB")
    public boolean isByFacebook;

    public String Email;

    public String Password;

    public String FacebookID;

    public String DeviceToken;
}
