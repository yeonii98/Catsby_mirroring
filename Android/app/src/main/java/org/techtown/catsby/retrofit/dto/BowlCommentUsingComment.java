package org.techtown.catsby.retrofit.dto;

import java.io.Serializable;

public class BowlCommentUsingComment implements Serializable {

    private int id;
    private String nickname;
    private String content;
    private String createDate;

    public BowlCommentUsingComment(int id, String nickname, String content, String createDate) {
        this.id = id;
        this.nickname = nickname;
        this.content = content;
        this.createDate = createDate;
    }

    public int getId() {
        return id;
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
