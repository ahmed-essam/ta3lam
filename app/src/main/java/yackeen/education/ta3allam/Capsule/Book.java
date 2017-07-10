package yackeen.education.ta3allam.Capsule;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by devar on 8/7/16.
 */
public class Book implements Serializable {
    @SerializedName("ID")
    private int id;
    @SerializedName("Name")
    private String name;
    @SerializedName("LevelNumber")
    private int level;
    @SerializedName("TeacherName")
    private String teacher;
    private String CourseName;
    private int ParticipantsNumber;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String courseName) {
        CourseName = courseName;
    }

    public int getParticipantsNumber() {
        return ParticipantsNumber;
    }

    public void setParticipantsNumber(int participantsNumber) {
        ParticipantsNumber = participantsNumber;
    }
}
