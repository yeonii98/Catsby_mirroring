package org.techtown.catsby.retrofit.dto;

import okhttp3.MultipartBody;

public class BowlCommunityPost {

    //private MultipartBody.Part image;
    private String id;
    private String user;
    private byte[] image;
    private String path;
    private String content;
    private int bowl;


    /*
    public BowlCommunityPost(MultipartBody.Part body, int id, String uid, String content) {
        this.bowl = id;
        this.user = uid;
        this.image = body;
        this.content = content;
    }*/

    public BowlCommunityPost(String content, String url) {
        this.path = url;
        this.content = content;
    }

}
