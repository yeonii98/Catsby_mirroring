package org.techtown.catsby.community.data.model;

import org.techtown.catsby.retrofit.dto.User;

import java.util.List;

public class TownCommunity {

    private int id;

    private User user;

    private String image;

    private String content;

    private String title;

    private String date;

    private List<TownLike> townLike;

    public List<TownLike> getTownLike() {
        return townLike;
    }

    public void setTownLike(List<TownLike> townLike) {
        this.townLike = townLike;
    }

    public TownCommunity(){}

    public TownCommunity(String title, String content){
        this.title = title;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
}