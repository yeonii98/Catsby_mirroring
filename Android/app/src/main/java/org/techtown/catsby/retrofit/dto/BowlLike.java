package org.techtown.catsby.retrofit.dto;

public class BowlLike {

    private int id;
    private User user;
    private BowlCommunity bowlCommunity;
    private String uid;
    private String createdDate;

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public BowlCommunity getBowlCommunity() {
        return bowlCommunity;
    }

    public String getUid() {
        return uid;
    }

    public String getCreatedDate() {
        return createdDate;
    }
}
