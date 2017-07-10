package yackeen.education.ta3allam.model.dto.request;

/**
 * Created by ahmed essam on 05/07/2017.
 */

public class ResetPasswordRequest2 {
    private String Email;
    private String VerifyCode;
    private String Password;
    private String ConfirmPassword;

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getVerifyCode() {
        return VerifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        VerifyCode = verifyCode;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getConfirmPassword() {
        return ConfirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        ConfirmPassword = confirmPassword;
    }
}
