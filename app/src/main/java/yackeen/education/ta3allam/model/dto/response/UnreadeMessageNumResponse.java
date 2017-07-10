package yackeen.education.ta3allam.model.dto.response;

/**
 * Created by ahmed essam on 08/07/2017.
 */

public class UnreadeMessageNumResponse {
    private int Count;
    private boolean IsSuccess;
    private String ErrorMessage;

    public int getCount() {
        return Count;
    }

    public boolean isSuccess() {
        return IsSuccess;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }
}
