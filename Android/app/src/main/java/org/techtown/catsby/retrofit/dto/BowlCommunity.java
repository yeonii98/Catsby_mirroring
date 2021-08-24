package org.techtown.catsby.retrofit.dto;

public class BowlCommunity {
    private int id;
    private User user;
    private String image;
    private String content;
    private String createDate;
    private String updateDate;

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getImage() {
        return image;
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
