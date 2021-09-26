package org.techtown.catsby.retrofit.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User {

    private int id;
    private String nickname;
    private String email;
    private String address;
    private String createDate;
    private String updateDate;
    private String uid;
    private String fcmToken;
    private String image;

    @SerializedName("bowlComments")
    private List<BowlComment> bowlComment;

    public String getUid() {
        return uid;
    }

    public int getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getImage() {
        return image;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getCreateDate() {
        return createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }
}
