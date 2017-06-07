package com.yackeen.ta3allam.model.dto.request;

/**
 * Created by ahmed essam on 07/06/2017.
 */

public class FollowerRequest {
    private String SelectedUserID;
    private String UserID;

    public String getSelectedUserID() {
        return SelectedUserID;
    }

    public void setSelectedUserID(String selectedUserID) {
        SelectedUserID = selectedUserID;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }
}
