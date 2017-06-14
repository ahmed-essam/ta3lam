package yackeen.education.ta3allam.model.dto.request;

/**
 * Created by ahmed essam on 13/06/2017.
 */

public class ForumRequest {
    private String UserID;
    private int BookID;

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public int getBookID() {
        return BookID;
    }

    public void setBookID(int bookID) {
        BookID = bookID;
    }
}
