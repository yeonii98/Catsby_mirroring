package org.techtown.catsby.retrofit.dto;

public class BowlCommentResponse {

    private int id;
    private String nickname;
    private String date;
    private int userId;

    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getDate() {
        return date;
    }
}
