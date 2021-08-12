package org.techtown.catsby.retrofit.dto;

public class BowlComment {
    private int id;
    private User user;
    private BowlCommunity bowlCommunity;
    private String content;
    private String createDate;
    private String updateDate;

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public BowlCommunity getBowlCommunity() {
        return bowlCommunity;
    }

    public String getContent() {
        return content;
    }

    public String getCreateDate() {
        return createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }
}
