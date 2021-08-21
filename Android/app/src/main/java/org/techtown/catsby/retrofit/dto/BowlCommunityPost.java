package org.techtown.catsby.retrofit.dto;

public class BowlCommunityPost {

    private String id;
    private String user;
    private byte[] image;
    private String content;
    private int bowl;


    public BowlCommunityPost(int id, String uid, String content) {
        this.bowl = id;
        this.user = uid;
        //this.image = image;
        this.content = content;
    }

}
