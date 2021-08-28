package org.techtown.catsby.retrofit.dto;

public class BowlCommentUpdatePost {

    private int id;
    private String content;
    private String user;
    private int bowlCommunity;

    public BowlCommentUpdatePost(String content) {
        this.content = content;
    }
}
