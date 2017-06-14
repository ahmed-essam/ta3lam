package com.yackeen.ta3allam.model.dto.response;

import com.yackeen.ta3allam.Capsule.News;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ahmed essam on 13/06/2017.
 */

public class ForumResponse implements Serializable{
    private String TeacherName;
    private String TeacherPictureURL;
    private String BookName;
    private String CourseName;
    public List<News> BookPosts;

    public String getTeacherName() {
        return TeacherName;
    }

    public void setTeacherName(String teacherName) {
        TeacherName = teacherName;
    }

    public String getTeacherPictureURL() {
        return TeacherPictureURL;
    }

    public void setTeacherPictureURL(String teacherPictureURL) {
        TeacherPictureURL = teacherPictureURL;
    }

    public String getBookName() {
        return BookName;
    }

    public void setBookName(String bookName) {
        BookName = bookName;
    }

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String courseName) {
        CourseName = courseName;
    }
}
