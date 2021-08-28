package org.techtown.catsby.retrofit.dto;

public class BowlCommunityUpdatePost {

    private String id;
    private String user;
    private byte[] image;
    private String content;
    private int bowl;

    public BowlCommunityUpdatePost(String content) {
        this.content = content;
    }

}
