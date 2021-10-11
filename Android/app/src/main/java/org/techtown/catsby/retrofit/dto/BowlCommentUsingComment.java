package org.techtown.catsby.retrofit.dto;

import java.io.Serializable;

public class BowlCommentUsingComment implements Serializable {
    private int id;
    private String content;
    private String createDate;
    private int communityId;
    private User user;

    public BowlCommentUsingComment(int id, String content, String createDate, int communityId, User user) {
        this.id = id;
        this.content = content;
        this.createDate = createDate;
        this.communityId = communityId;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getCreateDate() {
        return createDate;
    }

    public int getCommunityId() {
        return communityId;
    }

    public User getUser() {
        return user;
    }
}
