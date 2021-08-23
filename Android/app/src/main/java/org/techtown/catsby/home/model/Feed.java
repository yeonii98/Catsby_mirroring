package org.techtown.catsby.home.model;

import org.techtown.catsby.retrofit.dto.BowlComment;

import java.util.ArrayList;
import java.util.List;

public class Feed {
    private int id;
    private int userId;
    private int bowlImg;
    private String nickName;
    private int img;
    private String content;
    private Long like;
    private ArrayList<List<BowlComment>> bowlComments;

    public ArrayList<List<BowlComment>> getBowlComments() {
        return bowlComments;
    }

    public Feed(int id, int bowlImg, int userId, String nickName, int img, String content, ArrayList<List<BowlComment>> bowlComments) {
        this.id = id;
        this.userId = userId;
        this.bowlImg = bowlImg;
        this.nickName= nickName;
        this.img = img;
        this.content = content;
        this.bowlComments = bowlComments;
    }

    public int getUserId() {
        return userId;
    }

    public long getLike(){
        return like;
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