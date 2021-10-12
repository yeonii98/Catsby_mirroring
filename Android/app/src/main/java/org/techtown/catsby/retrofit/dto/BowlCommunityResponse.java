package org.techtown.catsby.retrofit.dto;

public class BowlCommunityResponse {
    private int id;
    private int userId;
    private String nickName;
    private String userImg;
    private String image;


    public String getUserImg() {
        return userImg;
    }

    private String createDateTime;

    public String getCreateDateTime() {
        return createDateTime;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getNickName() {
        return nickName;
    }

    public String getImage() {
        return image;
    }
}
