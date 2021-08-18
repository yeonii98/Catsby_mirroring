package org.techtown.catsby.retrofit.dto;

public class BowlCommentPost {

    private int id;
    private String content;
    private String user;
    private int bowlCommunity;

    public BowlCommentPost(String uid, int id, String context) {
        this.user = uid;
        this.bowlCommunity = id;
        this.content = context;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }


}
