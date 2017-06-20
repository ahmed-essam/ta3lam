package yackeen.education.ta3allam.model.dto.request;

/**
 * Created by ahmed essam on 20/06/2017.
 */

public class UserFollowRequest {
    private String UserID;
    private String SelectedUserID;

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getSelectedUserID() {
        return SelectedUserID;
    }

    public void setSelectedUserID(String selectedUserID) {
        SelectedUserID = selectedUserID;
    }
}
