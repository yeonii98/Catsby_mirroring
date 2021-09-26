package org.techtown.catsby.home.model;

import org.techtown.catsby.retrofit.dto.BowlComment;

import java.util.ArrayList;
import java.util.List;

public class Feed {
    private int id;
    private int userId;
    private String nickName;
    private byte[] img;

    private String userImg;

    private String content;
    private String uid;
    private String createDate;

    public String getCreateDate() {
        return createDate;
    }

    public Feed(int id, int userId, String userImg, String nickName, byte[] img, String content, String uid, String createDate) {
        this.id = id;
        this.userId = userId;
        this.userImg = userImg;
        this.nickName= nickName;
        this.img = img;
        this.content = content;
        this.uid = uid;
        this.createDate=createDate;
    }

    public String getUid() {
        return uid;
    }

    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}