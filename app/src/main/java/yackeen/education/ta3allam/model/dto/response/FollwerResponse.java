package yackeen.education.ta3allam.model.dto.response;

import com.google.gson.annotations.SerializedName;
import yackeen.education.ta3allam.Capsule.Follower;
import yackeen.education.ta3allam.Capsule.UserBooks;

import java.util.List;

/**
 * Created by ahmed essam on 07/06/2017.
 */

public class FollwerResponse {

    private String UserID;
    private String UserName;
    private String UserPictureURL;
    private String about;
    private int FollowersNumber;
    @SerializedName("SixFollowers")
    public List<Follower> followers;
    @SerializedName("FourBooks")
    public List<UserBooks> userBooksList;
    private boolean IsFollowing;
    private int UserType;




    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserPictureURL() {
        return UserPictureURL;
    }

    public void setUserPictureURL(String userPictureURL) {
        UserPictureURL = userPictureURL;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public int getFollowersNumber() {
        return FollowersNumber;
    }

    public void setFollowersNumber(int followersNumber) {
        FollowersNumber = followersNumber;
    }

    public boolean isFollowing() {
        return IsFollowing;
    }

    public void setFollowing(boolean following) {
        IsFollowing = following;
    }

    public int getUserType() {
        return UserType;
    }

    public void setUserType(int userType) {
        UserType = userType;
    }
}
