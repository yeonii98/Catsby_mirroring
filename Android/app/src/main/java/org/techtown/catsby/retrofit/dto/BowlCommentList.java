package org.techtown.catsby.retrofit.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BowlCommentList {

    @SerializedName("data")
    private List<BowlComment> bowlComments;

    public List<BowlComment> getBowlComments() {
        return bowlComments;
    }
}
