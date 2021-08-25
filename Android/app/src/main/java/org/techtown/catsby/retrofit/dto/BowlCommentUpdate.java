package org.techtown.catsby.retrofit.dto;

public class BowlCommentUpdate {

    private int id;
    private String content;
    private String user;
    private int bowlCommunity;

    public BowlCommentUpdate(String context) {
        this.content = context;
    }

}
