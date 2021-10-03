package org.techtown.catsby.home.model;

public class Feed {
    private int id;
    private int userId;
    private String nickName;
    private String img;
    private String userImg;
    private String content;
    private String uid;
    private String createDate;
    private int likeCount;

    public String getCreateDate() {
        return createDate;
    }

    public Feed(int id, int userId, String userImg, String nickName, String img, String content, String uid, String createDate, int likeCount) {
        this.id = id;
        this.userId = userId;
        this.userImg = userImg;
        this.nickName= nickName;
        this.img = img;
        this.content = content;
        this.uid = uid;
        this.createDate=createDate;
        this.likeCount = likeCount;
    }

    public int getLikeCount() {
        return likeCount;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}