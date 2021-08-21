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

}
