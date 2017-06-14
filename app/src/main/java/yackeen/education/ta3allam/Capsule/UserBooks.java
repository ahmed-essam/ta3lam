package yackeen.education.ta3allam.Capsule;

/**
 * Created by ahmed essam on 07/06/2017.
 */

public class UserBooks {
    private int BookID;
    private String BookName;
    private double Percentage;
    private int ParticipantsNumber;
    private String TeacherName;

    public int getBookID() {
        return BookID;
    }

    public void setBookID(int bookID) {
        BookID = bookID;
    }

    public String getBookName() {
        return BookName;
    }

    public void setBookName(String bookName) {
        BookName = bookName;
    }

    public double getPercentage() {
        return Percentage;
    }

    public void setPercentage(double percentage) {
        Percentage = percentage;
    }

    public int getParticipantsNumber() {
        return ParticipantsNumber;
    }

    public void setParticipantsNumber(int participantsNumber) {
        ParticipantsNumber = participantsNumber;
    }

    public String getTeacherName() {
        return TeacherName;
    }

    public void setTeacherName(String teacherName) {
        TeacherName = teacherName;
    }
}