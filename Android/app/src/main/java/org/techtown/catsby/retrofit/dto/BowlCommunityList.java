package org.techtown.catsby.retrofit.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BowlCommunityList {

    @SerializedName("data")
    private List<BowlCommunity> bowlCommunities;

    public List<BowlCommunity> getBowlCommunities() {
        return bowlCommunities;
    }

}
