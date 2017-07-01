package yackeen.education.ta3allam.Capsule;

/**
 * Created by ahmed essam on 22/06/2017.
 */

public class FollowerDetail {
    private String UserID;
    private String Name;
    private String CategoryName;
    private String FollowerPictureUR;
    private int Percentage;
    private int UserType;


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

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getFollowerPictureUR() {
        return FollowerPictureUR;
    }

    public void setFollowerPictureUR(String followerPictureUR) {
        FollowerPictureUR = followerPictureUR;
    }

    public int getPercentage() {
        return Percentage;
    }

    public void setPercentage(int percentage) {
        Percentage = percentage;
    }

    public int getUserType() {
        return UserType;
    }

    public void setUserType(int userType) {
        UserType = userType;
    }
}
