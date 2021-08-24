package org.techtown.catsby.community.data.model;

import org.techtown.catsby.retrofit.dto.User;

public class TownComment {
    private int id;
    private User user;
    private TownCommunity  townCommunity;
    private String content;
    private String date;

    TownComment(){

    }
    public TownComment(String content){
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public TownCommunity getTownCommunity() {
        return townCommunity;
    }

    public String getContent() {
        return content;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setTownCommunity(TownCommunity townCommunity) {
        this.townCommunity = townCommunity;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
