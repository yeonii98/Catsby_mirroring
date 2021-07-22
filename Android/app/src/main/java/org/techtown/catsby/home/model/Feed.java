package org.techtown.catsby.home.model;

public class Feed {
    private int bowlImg;
    private String userName;
    private int img;
    private String content;

    public Feed(int bowlImg, String userName, int img, String content) {
        this.bowlImg = bowlImg;
        this.userName = userName;
        this.img = img;
        this.content = content;
    }

    public int getBowlImg() {
        return bowlImg;
    }

    public void setBowlImg(int bowlImg) {
        this.bowlImg = bowlImg;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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