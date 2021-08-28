package org.techtown.catsby.retrofit.dto;

import java.io.Serializable;

public class BowlCommentUsingComment implements Serializable {

    private int id;
    private int userId;
    private String nickname;
    private String content;
    private String createDate;

    public BowlCommentUsingComment(int id, String nickname, String content, String createDate, int userId) {
        this.id = id;
        this.nickname = nickname;
        this.content = content;
        this.createDate = createDate;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getNickname() {
        return nickname;
    }

    public String getContent() {
        return content;
    }

    public String getCreateDate() {
        return createDate;
    }
}
