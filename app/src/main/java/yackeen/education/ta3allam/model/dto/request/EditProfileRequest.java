package yackeen.education.ta3allam.model.dto.request;

/**
 * Created by ahmed essam on 03/07/2017.
 */

public class EditProfileRequest {
    private String UserID;
    private String Name;
    private String About;

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAbout() {
        return About;
    }

    public void setAbout(String about) {
        About = about;
    }
}
