package org.techtown.catsby.community.data.model;

import org.techtown.catsby.retrofit.dto.User;

import java.util.List;

public class TownCommunity {

    private int id;

    private User user;

    private String content;

    private String title;

    private String date;

    private String image;

    private List<TownLike> townLike;

    private List<TownComment> townComment;

    public List<TownLike> getTownLike() {
        return townLike;
    }

    public void setTownLike(List<TownLike> townLike) {
        this.townLike = townLike;
    }

    public List<TownComment> getTownComments() {
        return townComment;
    }

    public void setTownComments(List<TownComment> townComments) {
        this.townComment = townComments;
    }

    public boolean anonymous;

    public TownCommunity(){}

    public TownCommunity(String title, String content, boolean anonymous){
        this.title = title;
        this.content = content;
        this.anonymous = anonymous;
    }

    public TownCommunity(String title, String content, String image, boolean anonymous){
        this.title = title;
        this.content = content;
        this.image = image;
        this.anonymous = anonymous;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }
}