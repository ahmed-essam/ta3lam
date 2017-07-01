package yackeen.education.ta3allam.model.dto.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import yackeen.education.ta3allam.Capsule.FollowerDetail;

/**
 * Created by ahmed essam on 22/06/2017.
 */

public class FriendsResponse {
    @SerializedName("AllFollowersDetails")
    public List<FollowerDetail> followersDetails;
}
