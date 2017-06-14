package yackeen.education.ta3allam.model.dto.request;

/**
 * Created by ahmed essam on 14/06/2017.
 */

public class SetUserBookRequest {
    private String UserID;
    private int[] BooksIDs;

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public int[] getBooksIDs() {
        return BooksIDs;
    }

    public void setBooksIDs(int[] booksIDs) {
        BooksIDs = booksIDs;
    }
}
