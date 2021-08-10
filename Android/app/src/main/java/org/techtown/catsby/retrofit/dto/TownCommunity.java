package org.techtown.catsby.retrofit.dto;

public class TownCommunity {

    private int id;

    private User user;

    private String image;

    private String content;

    private String title;

    private String date;

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }
}
