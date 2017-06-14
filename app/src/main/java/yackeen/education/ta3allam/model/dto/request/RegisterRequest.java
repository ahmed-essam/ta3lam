package yackeen.education.ta3allam.model.dto.request;

import com.google.gson.annotations.SerializedName;

public class RegisterRequest {

    /**
     * This class is responsible for holding the register request body
     * */
    @SerializedName("isByFacebook")
    public boolean isByFacebook;

    @SerializedName("Name")
    public String name;

    @SerializedName("Email")
    public String email;

    @SerializedName("Password")
    public String password;

    @SerializedName("ConfirmPassword")
    public String confirmPassword;

    @SerializedName("FacebookID")
    public String facebookID;
}
