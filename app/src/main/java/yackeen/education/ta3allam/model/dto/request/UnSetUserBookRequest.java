package yackeen.education.ta3allam.model.dto.request;

/**
 * Created by ahmed essam on 21/06/2017.
 */

public class UnSetUserBookRequest {
    private String UserID;
    private int BooksID;

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public int getBooksID() {
        return BooksID;
    }

    public void setBooksID(int booksID) {
        BooksID = booksID;
    }
}
