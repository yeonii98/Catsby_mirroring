package org.techtown.catsby.retrofit.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BowlLikeList {

    @SerializedName("data")
    private List<BowlLike> bowlLikes;

    public List<BowlLike> getBowlLikes() {
        return bowlLikes;
    }
}
