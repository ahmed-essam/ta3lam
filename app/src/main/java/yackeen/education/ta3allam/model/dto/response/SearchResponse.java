package yackeen.education.ta3allam.model.dto.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import yackeen.education.ta3allam.Capsule.SearchProfile;

/**
 * Created by ahmed essam on 28/06/2017.
 */

public class SearchResponse {
    public List<SearchProfile> SearchUsers;
    @SerializedName("IsSuccess")
    private boolean success;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
