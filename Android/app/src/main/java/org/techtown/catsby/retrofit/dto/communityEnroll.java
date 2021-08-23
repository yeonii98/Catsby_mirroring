package org.techtown.catsby.retrofit.dto;

import com.google.gson.annotations.SerializedName;

public class communityEnroll {

    @SerializedName("bowlCommunity_id") private int id;
    @SerializedName("user_id") private String userId;
    @SerializedName("image") private byte[] image;
    @SerializedName("content") private String content;
    @SerializedName("created_time") private String createDate;
    @SerializedName("updated_time") private String updateDate;

    public communityEnroll(String userId) {
        this.userId = userId;
    }


}
