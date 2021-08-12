package org.techtown.catsby.retrofit.dto;

public class TownComment {
    private int id;
    private User user;
    private TownCommunity  townCommunity;
    private String content;
    private String date;


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
