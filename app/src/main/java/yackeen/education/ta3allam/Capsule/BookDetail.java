package yackeen.education.ta3allam.Capsule;

/**
 * Created by ahmed essam on 13/06/2017.
 */

public class BookDetail {
    private int ID;
    private String Name;
    private String TeacherName;
    private String TeacherPicture;
    private String TeacherTitle;
    private String FromDate;
    private String ToDate;
    private int ParticipantsNumber;
    private int LevelNumber;
    private int FollowersNumber;
    private boolean IsFollower;
    private boolean IsSubscriber;
    private String About;
    private String Link;

    public int getLevelNumber() {
        return LevelNumber;
    }

    public void setLevelNumber(int levelNumber) {
        LevelNumber = levelNumber;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getTeacherName() {
        return TeacherName;
    }

    public void setTeacherName(String teacherName) {
        TeacherName = teacherName;
    }

    public String getTeacherPicture() {
        return TeacherPicture;
    }

    public void setTeacherPicture(String teacherPicture) {
        TeacherPicture = teacherPicture;
    }

    public String getTeacherTitle() {
        return TeacherTitle;
    }

    public void setTeacherTitle(String teacherTitle) {
        TeacherTitle = teacherTitle;
    }

    public String getFromDate() {
        return FromDate;
    }

    public void setFromDate(String fromDate) {
        FromDate = fromDate;
    }

    public String getToDate() {
        return ToDate;
    }

    public void setToDate(String toDate) {
        ToDate = toDate;
    }

    public int getParticipantsNumber() {
        return ParticipantsNumber;
    }

    public void setParticipantsNumber(int participantsNumber) {
        ParticipantsNumber = participantsNumber;
    }

    public int getFollowersNumber() {
        return FollowersNumber;
    }

    public void setFollowersNumber(int followersNumber) {
        FollowersNumber = followersNumber;
    }

    public boolean isFollower() {
        return IsFollower;
    }

    public void setFollower(boolean follower) {
        IsFollower = follower;
    }

    public boolean isSubscriber() {
        return IsSubscriber;
    }

    public void setSubscriber(boolean subscriber) {
        IsSubscriber = subscriber;
    }

    public String getAbout() {
        return About;
    }

    public void setAbout(String about) {
        About = about;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }
}
