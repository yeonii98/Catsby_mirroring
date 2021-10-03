package org.techtown.catsby.retrofit.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class BowlCommunity {
    private int id;
    private User user;
    private String image;
    private String content;
    private String uid;
    private String createdDate;
    private int likeCount;

    public int getId() {
        return id;
    }

    public String getUid() {
        return uid;
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

    public String getCreatedDate() {
        return createdDate;
    }

    public int getLikeCount() {
        return likeCount;
    }
}
