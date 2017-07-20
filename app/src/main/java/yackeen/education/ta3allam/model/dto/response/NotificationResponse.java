package yackeen.education.ta3allam.model.dto.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ahmed essam on 05/07/2017.
 */

public class NotificationResponse<T> {
    public int type;
    @SerializedName("aps")
    public aps aps;
    public long id;
    public String body;
    public int badge;
    public String title;

    public NotificationResponse() {
    }

    public NotificationResponse(int type, yackeen.education.ta3allam.model.dto.response.aps aps, long id, String body, int badge) {
        this.type = type;
        this.aps = aps;
        this.id = id;
        this.body = body;
        this.badge = badge;
    }

    public String getBodyDisplayData() {
        return body;
    }
//    public String getBody() {
//        return body;
//    }
//
//    public void setBody(String body) {
//        this.body = body;
//    }
}
