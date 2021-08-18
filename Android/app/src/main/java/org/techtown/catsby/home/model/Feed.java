package org.techtown.catsby.home.model;

public class Feed {
    private int id;
    private int bowlImg;
    private String nickName;
    private int img;
    private String content;

    public Feed(int id, int bowlImg, String nickName, int img, String content) {
        this.id = id;
        this.bowlImg = bowlImg;
        this.nickName= nickName;
        this.img = img;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBowlImg() {
        return bowlImg;
    }

    public void setBowlImg(int bowlImg) {
        this.bowlImg = bowlImg;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}