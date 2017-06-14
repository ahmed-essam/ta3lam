package yackeen.education.ta3allam.Capsule;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ahmed essam on 07/06/2017.
 */

public class Follower {
    @SerializedName("UserID")
    private String id ;
    @SerializedName("UserPictureURL")
    private String photoURL;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }
}
