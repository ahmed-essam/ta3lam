package com.yackeen.ta3allam.Capsule;

import com.google.gson.annotations.SerializedName;

/**
 * Created by devar on 8/7/16.
 */
public class Book {
    @SerializedName("ID")
    private int id;
    @SerializedName("Name")
    private String name;
    @SerializedName("LevelNumber")
    private int level;
    @SerializedName("TeacherName")
    private String teacher;

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

}
