package com.yackeen.ta3allam.Capsule;

import com.google.gson.annotations.SerializedName;

/**
 * Created by devar on 8/22/16.
 */
public class News {
    @SerializedName("BookID")
    private int bookId;
    @SerializedName("PostID")
    private int PostId;
    @SerializedName("TeacherPictureUR")
    private String image;
    @SerializedName("TeacherName")
    private String name;
    @SerializedName("Datetime")
    private String time;
    @SerializedName("Body")
    private String description;
    @SerializedName("SharesNumber")
    private int share;
    @SerializedName("CommentsNumber")
    private int comment;
    @SerializedName("LikesNumber")
    private int like;
    @SerializedName("IsLiked")
    private boolean liked;

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getPostId() {
        return PostId;
    }

    public void setPostId(int postId) {
        PostId = postId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getShare() {
        return share;
    }

    public void setShare(int share) {
        this.share = share;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }
}
